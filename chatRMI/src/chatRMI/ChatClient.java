package chatRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
	public String getUsername() throws RemoteException;
	public boolean registerBroadcastMessage(ChatClient sender, String message) throws RemoteException;
}
