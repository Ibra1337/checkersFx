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

        List<IGame> games = new LinkedList<>();



        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            IMatchmaking mm = new Matchmaking(games , reg);
            IMatchmaking mmstub = (IMatchmaking) UnicastRemoteObject.exportObject(mm,0);

            reg.rebind("mm",mmstub);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
