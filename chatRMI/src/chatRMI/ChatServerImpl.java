package chatRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

	private static final long serialVersionUID = -8068605665326927055L;
	private Set<ChatClient> clientList = new HashSet<ChatClient>();
	
	protected ChatServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) throws RemoteException {
		Registry reg = null;
		try {
			reg = LocateRegistry.createRegistry(1099);
		} catch (RemoteException rex) {
			try {
				reg = LocateRegistry.getRegistry(1099);
			}catch (Exception ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		}
		
		ChatServer server;
		try {
			server = new ChatServerImpl(); 
			reg.rebind("chatserver", server);
		} catch (RemoteException rex) {
			rex.printStackTrace();
			System.exit(1);
		}
		
		try {
			ChatClientImpl window = new ChatClientImpl();
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean registerClient(ChatClient c) {
		clientList.add(c);
		return true;
	}

	@Override
	public boolean unregisterClient(ChatClient c) {
		clientList.remove(c);
		return true;
	}

	@Override
	public boolean sendBroadcastMessage(ChatClient sender, String message) {
		try {
			for (ChatClient c : clientList) {
				c.registerBroadcastMessage(sender, message);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
