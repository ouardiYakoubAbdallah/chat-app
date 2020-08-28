package ClientSide;

import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;




@SuppressWarnings("serial")
public class ClientAppManager extends UIManager {
	
	ChatClient chatClient;
	
	Socket sock;
	PrintStream outToServ;
	Thread serviceClient;
	
	static MainWindow mainWin;
	LoginWindow loginWin;
	RegisterWindow registerWin;
	ConnectWindow conWin;
	
	static String userName;
	
	public void setSystemLookAndFeel() {
		try {
			setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public void launchApplication() throws SQLException, IOException {
		conWin = new ConnectWindow();
		listenToActions();
	}
	
	
	private void listenToActions() {
		conWin.buNext.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							buildMainWindow();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				);
		
		conWin.tfPort.addKeyListener(new KeyAdapter() {
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
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            		try {
							buildMainWindow();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            }else {
	      	  JTextField textField = (JTextField) e.getSource();
	            try {
	            		@SuppressWarnings("unused")
	            		int x = Integer.parseInt(textField.getText());
	            		}catch (Exception exc) {
	            		textField.setText("");
	            		}
            		}
	       }
	      });
		
		conWin.tfHost.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
						try {
							buildMainWindow();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            }
	        }

	    });
		
	}
	
	private void buildMainWindow() throws Exception {
		if(conWin.tfHost.getText().equals("")) JOptionPane.showMessageDialog(conWin, "Please enter the 'Host'", "WARNING" ,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
		else if(conWin.tfPort.getText().equals("")) JOptionPane.showMessageDialog(conWin, "Please enter the 'Port'", "WARNING" ,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
		else {
			try {
				sock = new Socket(conWin.getHost(),conWin.getPort());
				outToServ= new PrintStream(sock.getOutputStream());
				conWin.setVisible(false);
				mainWin = new MainWindow();
				mainWin.buildMainWindow();
				mainWin.initialize(false);
				loginWin = new LoginWindow(conWin.getHost(), conWin.getPort());
				registerWin = new RegisterWindow(sock,outToServ);
				loginWin.buildLoginWindow();
				loginWin.setLocation(mainWin.getLocation());
				
				mainWin.buConnect.addActionListener(
						new ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								//port frame
									loginWin.setLocation(mainWin.getLocation());
									loginWin.setVisible(true);
									ActionLoginWindow();
								
							}
						}
						);
				
				mainWin.buDisconnect.addActionListener(
						new ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								try {
									disconnectAction();
									mainWin.setVisible(false);
									buildMainWindow();
									mainWin.resetMainWindow();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						);
				
				mainWin.tfMessage.addKeyListener(new KeyAdapter() {
			        @Override
			        public void keyPressed(KeyEvent e) {
			            if(e.getKeyCode() == KeyEvent.VK_ENTER){
			            		try{
			            			if(!mainWin.tfMessage.getText().equals("")) {
			            			 	chatClient.send(mainWin.tfMessage.getText());
			            				mainWin.tfMessage.setText("");
			            				mainWin.tfMessage.requestFocus();
			            			}
							}catch(Exception ex) {
								ex.printStackTrace();
							}
			            }
			        }

			    });
				
				mainWin.buSend.addActionListener(
						new ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								try{
									if(!mainWin.tfMessage.getText().equals("")) {
			            			 	chatClient.send(mainWin.tfMessage.getText());
			            				mainWin.tfMessage.setText("");
			            				mainWin.tfMessage.requestFocus();
			            			}
								}catch(Exception e) {
									e.printStackTrace();
								}
							}
						}
						);
				
				mainWin.buHelp.addActionListener(
						new ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								JOptionPane.showMessageDialog(mainWin, "<html> Chat Application, created by OUARDI Yakoub Abdallah <br> and NEDJAI Sofiane, Students in L3-ISIL (2017/2018) <br> using sockets in JAVA language <br> </html> ","About", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/astuce.png")));
							}
						}
						);
				
				mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    mainWin.addWindowListener(new java.awt.event.WindowAdapter() {
			        @Override
			        public void windowClosing(java.awt.event.WindowEvent e) {
								try {
									if(!mainWin.userName.equals("Anonymous")) {
										disconnectAction();
										System.exit(0);
									}
									else System.exit(0);
								} catch (Exception e1) {
									System.exit(1);
									e1.printStackTrace();
								} 
			        			
			            
			        }
			    });
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(conWin,"Server not responding","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/errServ.png")));
			}
		}
	}

	private void disconnectAction() throws Exception {
		outToServ.println("$2y$10$e5q5KjUb8our0SEkchhnbOC.aYSXrv93Mm6mNj8z4Yj2pvyEj48F2"+userName);
		outToServ.flush();
		chatClient.disconnect();
	}
	
	private void connectAction() {
		if(loginWin.getUserName().equals("")) JOptionPane.showMessageDialog(loginWin,"Please enter your username!","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
		else if(loginWin.getPassword().equals("")) JOptionPane.showMessageDialog(loginWin,"Please enter your password!","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
		else {
					loginWin.setVisible(false);
					try {
						if(sock.isClosed()) {
							sock = new Socket(conWin.getHost(),conWin.getPort());
							outToServ= new PrintStream(sock.getOutputStream());
						}else {
							mainWin.print("Attempt to connect to the chat server : "+conWin.getHost()+"\n");
							String password = loginWin.getPassword();
							userName = loginWin.getUserName();
							chatClient = new ChatClient(sock);
						
							outToServ.println(userName+","+password);
							outToServ.flush();
						
							serviceClient = new Thread(chatClient);
							serviceClient.start();
						}
						
					}catch(Exception ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(mainWin,"Server not responding","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/errServ.png")));
						System.exit(0);
					}
					mainWin.initialize(true);
		}
	}
	
	private void ActionLoginWindow(){
		
		loginWin.buEnter.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						connectAction();
					}
				}
				);
		
		loginWin.tfUserNameBox.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            		connectAction();
	            }
	        }

	    });
		
		loginWin.tfPasswordBox.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            		connectAction();
	            }
	        }

	    });
		
		loginWin.buRegister.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							if(sock.isClosed()) {
								sock = new Socket(conWin.getHost(),conWin.getPort());
								outToServ= new PrintStream(sock.getOutputStream());
								registerWin = new RegisterWindow(sock,outToServ);
							}
							registerWin.setLocation(loginWin.getLocation());
							registerWin.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				);
	}
	
}
