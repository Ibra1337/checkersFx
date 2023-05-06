package resources;

import java.util.*;

public class BoardLogic  {


    //0 = empty 1 = white 2 = black

    int aliveWhite = 20;
    int aliveBlack = 20;

    int PlayerRound =1;

    int[][] board = new int[8][8];



    public BoardLogic() {
        for (int i = 0; i < 8; i++) {
            for (int j =0; j<8 ; j++)
            {
                if (j <=2 )
                {
                    if ((i+j)%2 == 0 )
                        board[i][j] = 1 ;

                }
                if (j >= 5 )
                {
                    if ((i+j)%2 == 0 )
                    {
                        board[i][j] = -1;
                    }
                }
            }
        }
    }

    public void disp ()
    {
        for (var a : board)
        {
            System.out.println(Arrays.toString(a));
        }
    }

    public List<Integer[]> getAvaliableMoves(int[] movingPiece )
    {
        int x = movingPiece[0];
        int y = movingPiece[1];
        List<Integer[]> avMoves = new LinkedList<>();
        if (Math.abs(board[x][y]) == 1) {

            avMoves = normalCheckerMoves(x,y);
        }else {}
        return avMoves;
    }

    public int[][] putOnBoardAvMoves(int[] movingPiece )
    {
        var avMoves = getAvaliableMoves(movingPiece);
        int[][] moveBoard = board.clone();

        for (var v : avMoves)
            if (v[0] >=0  && v[0] <= 7)
                moveBoard[v[0]][v[1]] = 3 ;

        return moveBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    //adds to avMoves positions of possibleMoves

    private void hop(int i , int j , int nj , int idir , int jdir , List<Integer[]> avMoves)
    {
        int ni = i+idir;
        if (ifOpponent(i,j, ni , nj ))
        {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
            int opi = ni;
            int opj = nj;
            int ibehind = opi+idir;
            int jbehind = opj +jdir;
            if(ifInBoard(jbehind , ibehind) && board[ibehind][jbehind] == 0) {
                ni = ibehind;
                nj = jbehind;


                System.out.println(ni + ":" + nj);
                Integer[] m = {ni, nj};
                avMoves.add(m);
            }
        }else {
            nj = j + jdir;
            System.out.println(ni + ":" + nj);
            Integer[] m = {ni, nj};
            avMoves.add(m);
        }
    }
    public boolean ifCaptureAvailable(int performeri , int performerj )
    {
        int jdir = board[performeri][performerj];
       if(ifOnSideCapture(performeri ,performerj , 1 , jdir ))
           return true;
        return ifOnSideCapture(performeri, performerj, -1, jdir);
    }

    private boolean ifOnSideCapture(int i , int j , int idir , int jdir  )
    {
        int ni =  i +idir;
        int nj = j + jdir;
        if (ifOpponent(i,j, ni , nj )) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
            int opi = ni;
            int opj = nj;
            int ibehind = opi + idir;
            int jbehind = opj + jdir;
            return ifInBoard(jbehind, ibehind) && board[ibehind][jbehind] == 0;

        }
        return false;
    }

    private void nmoveOneStep(int i , int j  , List<Integer[]> avMoves )
    {
        int jdir = board[i][j];
        int nj = j+jdir;
        int up = i+1;
        int down = i-1;

        if(ifInBoard(up,nj) && !isAllay(i,j , up , nj))
        {
            hop(i,j,nj,1,jdir,avMoves);
        }
        nj = j +jdir;
        if (ifInBoard(down,nj) &&! isAllay(i,j , down , nj) )
        {
            hop(i,j,nj,-1,jdir,avMoves);
        }
    }

    public List<Integer[]> normalCheckerMoves(int i , int j )
    {
        List<Integer[]> avMoves = new LinkedList<>();

        nmoveOneStep(i,j, avMoves);

        for (var a : avMoves)
            System.out.println("================="+Arrays.toString(a));
        return avMoves;
    }

    public boolean ifOpponent(int i , int j , int desti , int destj )
    {
        return (board[desti][destj] == board[i][j] * -1 || board[desti][destj] == board[i][j] *-2);
    }

    public void makeMove(int i , int j , int desti , int destj )
    {
        int piece = board[i][j];
        System.out.println(desti+":"+ destj);
        board[i][j] = 0;
        if (Math.abs(i-desti) > 1 && Math.abs(j-destj) >1 ) {
            int idir = (i- desti)/2;
            int jdir = (j -destj)/2 ;
            board[desti + idir][destj + jdir] = 0;
            System.out.println("Bite " + (desti - idir) +":"+(destj - jdir) );
        }
        board[desti][destj] = piece;
    }


    public static void main(String[] args) {
        BoardLogic br = new BoardLogic();
        br.disp();
    }



    public boolean isAllay(int i , int j ,int i2, int j2 )
    {
        return (board[i][j] == board[i2][j2] || board[i][j] == board[i2][j2]*2);

    }


    private boolean ifInBoard(int i , int j )
    {
        if (i>=0 && i<=7 && j>=0 && j<=7)
        {
            System.out.println("iB");
            return true;
        }
        System.out.println("NOIB");
        return false ;
    }



}
