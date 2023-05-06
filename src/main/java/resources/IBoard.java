package resources;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBoard extends Remote, Serializable {

    int[][] getBoard() throws RemoteException ;
}
