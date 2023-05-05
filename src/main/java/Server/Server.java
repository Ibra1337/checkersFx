package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public
class Server {


    public static void main(String[] args) {

        List<Game> games = new LinkedList<>();



        try {
            IMatchmaking mm = new Matchmaking(games);
            IMatchmaking mmstub = (IMatchmaking) UnicastRemoteObject.exportObject(mm,0);
            Registry reg = LocateRegistry.createRegistry(1099);

            reg.rebind("mm",mmstub);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
