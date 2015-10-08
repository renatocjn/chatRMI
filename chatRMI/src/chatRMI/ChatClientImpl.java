package chatRMI;

import java.awt.EventQueue;

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

public class ChatClientImpl implements Serializable, ChatClient{

	private JFrame frame;
	private JTextField inputTextField;
	private JTextPane chatField;
	private final Action action = new SwingAction();
	private JButton submitBttn;
	private String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
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
		ChatClient client = null;
		try { 
			server = (ChatServer) reg.lookup("chatserver");
			client = new ChatClientImpl(JOptionPane.showInputDialog("por favor, escolha um apelido"));
			server.registerClient(client);
		} catch (Exception rex) {
			rex.printStackTrace();
			System.exit(1);
		}
		
		client.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 * @param string 
	 */
	public ChatClientImpl(String string) {
		this.username = string;
		initialize();
	}

	public void show() {
			this.frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 325, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Bem vindo ao aplicativo de chat\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Aluno: Renato Caminha Jua\u00E7aba Neto\r\n");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\r\nAs mensagens est\u00E3o no formato  \"(usu\u00E1rio) bl\u00E1 bl\u00E1 bl\u00E1\"\r\n");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Para se comunicar utilize a caixa de texto no canto inferior");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		inputTextField = new JTextField();
		inputTextField.setColumns(20);
		inputTextField.setDropMode(DropMode.INSERT);
		panel_1.add(inputTextField);
		
		submitBttn = new JButton("Enviar");
		submitBttn.setAction(action);
		panel_1.add(submitBttn);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		chatField = new JTextPane();
		scrollPane.setViewportView(chatField);
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Enviar");
			putValue(SHORT_DESCRIPTION, "Enviar String para servidor");
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("Action");
		}
	}

	@Override
	public String getUsername() throws RemoteException {
		return uname;
	}

	@Override
	public boolean registerBroadcastMessage(ChatClient sender, String message) throws RemoteException {
		String unameSender = null;
		try {
			unameSender = sender.getUsername().trim();
		} catch (RemoteException rex) {
			unameSender = "Failed to retrieve username";
		}
	
		chatField.setText(chatField.getText()+ "\n ( " + unameSender + " ) " + message);
		return true;
	}
}
