package tmp;

import java.io.Console;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClientImpl implements Serializable, ChatClient {

	private static final long serialVersionUID = -2997499944109900305L;
	String username;
	
	public ChatClientImpl(String uname) {
		username = uname;
	}
	
//	public static void main(String[] args) throws RemoteException{
//		
//		// Setup registry ///////////////////////////////
//		Registry reg = null;
//		try {
//			reg = LocateRegistry.createRegistry(1099);
//		} catch (RemoteException rex) {
//			try {
//				reg = LocateRegistry.getRegistry(1099);
//			}catch (Exception ex) {
//				ex.printStackTrace();
//				System.exit(1);
//			}
//		}
		
		// Welcome text and username reading //////////// 
//		System.out.println("Bem vindo ao client de chat RMI");
//		System.out.println("Aluno: Renato Caminha Juaçaba Neto");
//		System.out.println();
//		System.out.print("Digite um nome de usuário: ");
//		Scanner reader = new Scanner(System.in);
//		String uname = reader.nextLine();
//		System.out.println();
		
		
		// Setup Client and server //////////////////////
//		ChatServer server = null;
//		ChatClient client = null;
//		try { 
//			server = (ChatServer) reg.lookup("chatserver");
//			client = new ChatClientImpl(uname);
//			server.registerClient(client);
//		} catch (Exception rex) {
//			rex.printStackTrace();
//			System.exit(1);
//		}
		
		
		// Instructions //////////////////////////////////
//		System.out.println("As mensagens estão no formato \"(usuário) blá blá blá\" ");
//		System.out.println("Para se comunicar basta digitar a mensagem e pressionar <enter>");
//		System.out.println("Para finalizar o cliente basta enviar o sinal de interrupção (<ctrl> + c)");
//		System.out.println();
		
		
		// Read messages loop and closing stuff //////////
//		try {
//			while (true) {
//				String message = reader.nextLine();
//				server.sendBroadcastMessage(client, message);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			reader.close();
//			server.unregisterClient(client);
//		}
//	}

	@Override
	public boolean registerBroadcastMessage(ChatClient sender, String message) {
		String unameSender = null;
		try {
			unameSender = sender.getUsername().trim();
		} catch (RemoteException rex) {
			unameSender = "Failed to retrieve username";
		}
	
		System.console().writer().println(" ( " + unameSender + " ) " + message.trim());
		return true;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
