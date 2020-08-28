package ServerSide;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class ServerWindow extends JFrame {


	private	 JPanel panel = new JPanel();
	
	private	 JScrollPane scrollPane = new JScrollPane();
	
	JTextArea console = new JTextArea();
	
	JButton buStart = new JButton("START");
	JButton buStop = new JButton("STOP");
	JButton buOnlineUsers = new JButton("Show Online Users");
	JButton buClear = new JButton("Clear the console");
	JButton buSaveLogFile = new JButton("Save log file");
	JButton buClearHistMsg = new JButton("Clear message history");
	JButton buExportHistMsg = new JButton("Export message history");
	
	private	 DefaultCaret caret = (DefaultCaret)console.getCaret();
	
	private	JLabel lStatusTitle = new JLabel("Server Status ");
	private	JLabel lStatus = new JLabel();
	private	JLabel lPort = new JLabel("Port :");
	
	
	JTextField tfPort = new JTextField();
	

	void buildServerWindow() throws IOException {
		setTitle("Chat Server");
		setResizable(false);
		setSize(844,354);
		setLocation(300, 200);
		configureServerWindow();
		serverWindowAction();
		setVisible(true);
		
	}
	
	private	 void configureServerWindow() throws IOException {
		getContentPane().setLayout(null);
		
		panel.setBounds(0, 0, 844, 332);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane.setBounds(6, 6, 628, 320);
		panel.add(scrollPane);
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		console.setEditable(false);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollPane.setViewportView(console);
		
		buStart.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/startserver.png"))));
		buStart.setBounds(646, 45, 87, 43);
		panel.add(buStart);
		
		buStop.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/stopserver.png"))));
		buStop.setBounds(745, 45, 87, 43);
		panel.add(buStop);
		
		buOnlineUsers.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/users.png"))));
		buOnlineUsers.setBounds(646, 265, 186, 29);
		panel.add(buOnlineUsers);
		
		buClear.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/empty.png"))));
		buClear.setBounds(646, 297, 186, 29);
		panel.add(buClear);
		
		lStatusTitle.setBounds(695, 100, 87, 16);
		panel.add(lStatusTitle);
		lStatus.setHorizontalAlignment(SwingConstants.CENTER);
		
		lStatus.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lStatus.setBounds(634, 122, 210, 35);
		panel.add(lStatus);
		
		buSaveLogFile.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/savepage.png"))));
		buSaveLogFile.setBounds(646, 233, 186, 29);
		panel.add(buSaveLogFile);
		
		buClearHistMsg.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/clearhist.png"))));
		buClearHistMsg.setBounds(646, 201, 186, 29);
		panel.add(buClearHistMsg);
		
		buExportHistMsg.setIcon(new ImageIcon(ImageIO.read(ServerAppManager.class.getResource("/export.png"))));
		buExportHistMsg.setBounds(646, 169, 186, 29);
		panel.add(buExportHistMsg);
		tfPort.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfPort.setBounds(712, 7, 94, 26);
		panel.add(tfPort);
		
		lPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lPort.setBounds(675, 12, 33, 16);
		panel.add(lPort);
		
	}

	private	 void serverWindowAction() {
		 tfPort.addKeyListener(new KeyAdapter() {
		        public void keyReleased(KeyEvent e) {
		          JTextField textField = (JTextField) e.getSource();
		          try {
		        	  @SuppressWarnings("unused")
		          int x = Integer.parseInt(textField.getText());
		          }catch (Exception exc) {
		          textField.setText("");
		          }
		        }

		        public void keyTyped(KeyEvent e) {
		      	  JTextField textField = (JTextField) e.getSource();
		            try {
		            	@SuppressWarnings("unused")
		            int x = Integer.parseInt(textField.getText());
		            }catch (Exception exc) {
		            textField.setText("");
		            }
		        }

		        public void keyPressed(KeyEvent e) {
		            		JTextField textField = (JTextField) e.getSource();
		            		try {
		            			@SuppressWarnings("unused")
		            			int x = Integer.parseInt(textField.getText());
		            		}catch (Exception exc) {
		            			textField.setText("");
		            		}
		            }
		      });
				
		buClear.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						console.setText("");
						ServerAppManager.print("The text of the console has been removed !");
					}
				}
				);
		
		
	}
	
	String getPort() {
		return tfPort.getText();
	}
	
	 void initialize(boolean started) {
		 if(started) {
			 	console.setText("");
			 	lStatus.setText("RUNNING");
				lStatus.setForeground(new Color(0, 220, 0));
				buStart.setEnabled(false);
				buExportHistMsg.setEnabled(true);
				buClearHistMsg.setEnabled(true);
				buClear.setEnabled(true);
				buOnlineUsers.setEnabled(true);
				buStop.setEnabled(true);
				buSaveLogFile.setEnabled(true);
				tfPort.setEnabled(false);
				lPort.setEnabled(false);
		 }else {
			 	console.setText("");
			 	buStart.setEnabled(true);
			 	buClear.setEnabled(false);
			 	buOnlineUsers.setEnabled(false);
			 	buStop.setEnabled(false);
			 	buSaveLogFile.setEnabled(false);
			 	buExportHistMsg.setEnabled(false);
			 	buClearHistMsg.setEnabled(false);
			 	tfPort.setText(null);
			 	tfPort.setEnabled(true);
			 	lPort.setEnabled(true);
			 	lStatus.setText("STOPPED");
			 	lStatus.setForeground(new Color(255, 0, 0));
		 }
	}

	
}
