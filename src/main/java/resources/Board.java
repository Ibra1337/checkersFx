package resources;

import java.io.Serializable;

public class Board implements IBoard , Serializable {

    int[][] board;

    @Override
    public int[][] getBoard() {
        return board;
    }
}
