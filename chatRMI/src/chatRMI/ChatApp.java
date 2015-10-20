package chatRMI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.DropMode;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.Action;

public class ChatApp implements Serializable, ChatClient{
	private static final long serialVersionUID = -5403597055866663378L;
	private String username;
	
	private JFrame frame;
	private JTextField inputTextField;
	private JTextPane chatField;
	private JButton submitBttn;
	
	private ChatServer server;
	private final Action action = new SwingAction(this);

	public ChatApp(String username, ChatServer server) throws RemoteException {
		initialize();
		this.username = username;
		this.server = server;
		server.registerClient(this);
		this.frame.setVisible(true);
	}
	
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 325, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Bem vindo ao aplicativo de chat\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		inputTextField = new JTextField();
		inputTextField.setToolTipText("Digite as mensagens aqui!");
		inputTextField.setColumns(20);
		inputTextField.setDropMode(DropMode.INSERT);
		panel_1.add(inputTextField);
		
		submitBttn = new JButton("Enviar");
		submitBttn.setAction(action);
		panel_1.add(submitBttn);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		chatField = new JTextPane();
		chatField.setEditable(false);
		scrollPane.setViewportView(chatField);
	}
	
	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = 5307367378393118447L;
		private ChatClient c;
		
		public SwingAction(ChatClient chatClient) {
			this.c = chatClient;
			putValue(NAME, "Enviar");
			putValue(SHORT_DESCRIPTION, "Enviar String para servidor");
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				server.sendBroadcastMessage(c, inputTextField.getText());
			} catch (RemoteException e1) {
				System.out.println("Erro ao enviar mensagem");
			}
		}
	}

	private boolean appendMessage(String sender, String message) {
		chatField.setText(chatField.getText()+ "\n ( " + sender + " ) " + message);
		return true;
	}

	
	@Override
	public String getUsername() throws RemoteException {
		return username;
	}

	
	@Override
	public boolean registerBroadcastMessage(ChatClient sender, String message) throws RemoteException {
		System.out.println("Registrando mensagem");
		appendMessage(sender.getUsername(), message);
		return true;
	}
	
	public static void main(String args[]) {
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
		
		ChatServer server = null;
		try { 
			server = (ChatServer) reg.lookup("chatserver");
			
			String apelido = JOptionPane.showInputDialog("Por favor, escolha um apelido").trim();
			if (apelido == null || apelido.equals("")) System.exit(1);
			
			new ChatApp(apelido, server);
		} catch (Exception rex) {
			rex.printStackTrace();
			System.exit(1);
		}
	}
}
