package ClientSide;

import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	String userName = "Anonymous";
	
	JButton buConnect = new JButton("CONNECT");
	JButton buDisconnect = new JButton("DISCONNECT");
	JButton buHelp = new JButton("");
	JButton buSend = new JButton("SEND");
	
	private JLabel lMessage = new JLabel("Message: ");
	private JLabel lConversation = new JLabel("Conversation :");
	private JLabel lLoggedInAs = new JLabel("Currently Logged In As");
	private JLabel lLoggedInAsBox = new JLabel();
	
	JTextField tfMessage=new JTextField(20);
	
	private JTextArea taConversation = new JTextArea();
	private DefaultCaret caret = (DefaultCaret)taConversation.getCaret();
	
	private JScrollPane spOnline = new JScrollPane();
	private JScrollPane spConversation = new JScrollPane();
	
	JTable jlOnline = new JTable();
	
	
	public MainWindow() {
		buildMainWindow();
		initialize(false);
	}
	
	void initialize(boolean connected) {
		if(connected) {
			taConversation.setText("Connected\n");
			buSend.setEnabled(true);
			buDisconnect.setEnabled(true);
			buConnect.setEnabled(false);	
			lConversation.setEnabled(true);
			lLoggedInAs.setEnabled(true);
			lLoggedInAsBox.setEnabled(true);
			lMessage.setEnabled(true);
			taConversation.setEnabled(true);
			tfMessage.setEnabled(true);
			jlOnline.setEnabled(true);
			userName=ClientAppManager.userName;
			lLoggedInAsBox.setText(userName);
			this.setTitle(userName+"'s chat box");
			
		}else {
			buSend.setEnabled(false);
			buDisconnect.setEnabled(false);
			lConversation.setEnabled(false);
			lLoggedInAs.setEnabled(false);
			lLoggedInAsBox.setEnabled(false);
			lMessage.setEnabled(false);
			taConversation.setEnabled(false);
			tfMessage.setEnabled(false);
			jlOnline.setEnabled(false);
			buConnect.setEnabled(true);
		}
	}
	

	private void configureMainWindow() {
		
		this.getContentPane().setLayout(null);
		
		this.getContentPane().add(buSend);
		buSend.setBounds(327, 253, 93, 30);
		
		this.getContentPane().add(buDisconnect);
		buDisconnect.setBounds(5, 9, 120, 25);
		
		this.getContentPane().add(buConnect);
		buConnect.setBounds(137, 9, 110, 25);
		
		this.getContentPane().add(buHelp);
		buHelp.setBounds(578, 6, 37, 25);
		
		

		this.getContentPane().add(lMessage);
		lMessage.setBounds(5, 259, 100, 20);
		
		tfMessage.requestFocus();
		this.getContentPane().add(tfMessage);
		tfMessage.setBounds(65, 253, 260, 30);
		
		lConversation.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lConversation);
		lConversation.setBounds(15, 40, 405, 16);
		
		taConversation.setColumns(20);
		taConversation.setFont(new java.awt.Font("Tahoma",0,12));
		taConversation.setLineWrap(true);
		taConversation.setRows(5);
		taConversation.setEditable(false);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		jlOnline.setDefaultEditor(Object.class, null);
		
		spConversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spConversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spConversation.setViewportView(taConversation);
		this.getContentPane().add(spConversation);
		spConversation.setBounds(15, 60, 405, 180);
		
		spOnline.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spOnline.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spOnline.setViewportView(jlOnline);
		this.getContentPane().add(spOnline);
		spOnline.setBounds(436, 59, 168, 180);
		lLoggedInAs.setHorizontalAlignment(SwingConstants.CENTER);
		
		lLoggedInAs.setFont(new java.awt.Font("Tahoma",0,12));
		this.getContentPane().add(lLoggedInAs);
		lLoggedInAs.setBounds(446, 253, 148, 15);
		
		lLoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
		lLoggedInAsBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.getContentPane().add(lLoggedInAsBox);
		lLoggedInAsBox.setBounds(446, 270, 150, 20);
		
		try {
			buSend.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/send.png"))));
			buConnect.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/connect.png"))));
			buDisconnect.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/disconnect.png"))));
			buHelp.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/help.png"))));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}

	void resetMainWindow() {
		taConversation.setText("You are disconnected \n");
		lLoggedInAsBox.setText("");
		userName="Anonymous";
		this.setTitle(userName+"'s chat box");
		jlOnline.setModel(new DefaultTableModel());
		initialize(false);
	}
	
	void buildMainWindow() {
		this.setTitle(userName+"'s chat box");
		this.setSize(621,329);
		this.setLocation(220,180);
		this.setResizable(false);
		configureMainWindow();
		this.setVisible(true);
	}
	
	void print(String msg) {
		taConversation.append(msg);
	};
	
}
