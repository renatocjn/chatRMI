package tmp;

import java.io.console;
import java.io.serializable;
import java.rmi.remoteexception;
import java.rmi.registry.locateregistry;
import java.rmi.registry.registry;
import java.util.scanner;

public class chatclientimpl implements serializable, chatclient {

	private static final long serialversionuid = -2997499944109900305l;
	string username;
	
	public chatclientimpl(string uname) {
		username = uname;
	}
	
	public static void main(string[] args) throws remoteexception{
		
		// setup registry ///////////////////////////////
		registry reg = null;
		try {
			reg = locateregistry.createregistry(1099);
		} catch (remoteexception rex) {
			try {
				reg = locateregistry.getregistry(1099);
			}catch (exception ex) {
				ex.printstacktrace();
				system.exit(1);
			}
		}
		
		// welcome text and username reading //////////// 
		system.out.println("bem vindo ao client de chat rmi");
		system.out.println("aluno: renato caminha juaçaba neto");
		system.out.println();
		system.out.print("digite um nome de usuário: ");
		scanner reader = new scanner(system.in);
		string uname = reader.nextline();
		system.out.println();
		
		
		// setup client and server //////////////////////
		chatserver server = null;
		chatclient client = null;
		try { 
			server = (chatserver) reg.lookup("chatserver");
			client = new chatclientimpl(uname);
			server.registerclient(client);
		} catch (exception rex) {
			rex.printstacktrace();
			system.exit(1);
		}
		
		
		// instructions //////////////////////////////////
		system.out.println("as mensagens estão no formato \"(usuário) blá blá blá\" ");
		system.out.println("para se comunicar basta digitar a mensagem e pressionar <enter>");
		system.out.println("para finalizar o cliente basta enviar o sinal de interrupção (<ctrl> + c)");
		system.out.println();
		
		
//		 read messages loop and closing stuff //////////
		try {
			while (true) {
				string message = reader.nextline();
				server.sendbroadcastmessage(client, message);
			}
		} catch (exception ex) {
			ex.printstacktrace();
		} finally {
			reader.close();
			server.unregisterclient(client);
		}
	}

	@override
	public boolean registerbroadcastmessage(chatclient sender, string message) {
		string unamesender = null;
		try {
			unamesender = sender.getusername().trim();
		} catch (remoteexception rex) {
			unamesender = "failed to retrieve username";
		}
	
		system.console().writer().println(" ( " + unamesender + " ) " + message.trim());
		return true;
	}

	@override
	public string getusername() {
		return username;
	}

}
