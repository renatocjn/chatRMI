package chatRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
	public boolean registerClient (ChatClient c) throws RemoteException;
	public boolean unregisterClient (ChatClient c) throws RemoteException;
	
	public boolean sendBroadcastMessage (ChatClient sender, String message) throws RemoteException;
}
