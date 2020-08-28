package ServerSide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;




@SuppressWarnings("serial")
public class ServerAppManager extends UIManager {
	
	private Thread chatService;
	private static ServerTools myserver = new ServerTools();
	private JFileChooser saveFile = new JFileChooser();
	private static ServerWindow serverWin = new ServerWindow();
	
	public void setSystemLookAndFeel() {
		try {
			setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public void launchApplication() throws IOException {
		serverWin.buildServerWindow();
		serverWin.initialize(false);	
		listenToActions();
	}

	 static void print (String msg) {
		serverWin.console.append(myserver.timeNow()+msg+"\n");
	
	}
	 
	 static void initializeServerWindow(boolean started) {
		 serverWin.initialize(started);
	 }
	 
	 private void listenToActions() {
			serverWin.buStart.addActionListener(
					new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							startAction();
							
						}
					}
					);
			
			serverWin.buStop.addActionListener(
					new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							stopAction();
						}

					}
					);

			serverWin.tfPort.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
	        		if(e.getKeyCode() == KeyEvent.VK_ENTER) startAction();
				}
			});
			
			serverWin.buOnlineUsers.addActionListener(
					new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							showOnlineUsersAction();
						}
					}
					);
			
			serverWin.buSaveLogFile.addActionListener(
					new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							saveLogFileAction();
						}
					});
			
			serverWin.buClearHistMsg.addActionListener(
					new ActionListener() {
						@SuppressWarnings("static-access")
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							try {
								myserver.chatDb.clearHistoryMsgs();
								print("The message history has been removed from the database.");
							} catch (SQLException e) {
								print(e.getMessage());
							}
						}
					});
			
			serverWin.buExportHistMsg.addActionListener(
					new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							exportHistMsgAction();
						}
					});
			serverWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    serverWin.addWindowListener(new java.awt.event.WindowAdapter() {
		        @Override
		        public void windowClosing(java.awt.event.WindowEvent e) {
		        		stopAction(); 	
		            e.getWindow().dispose();
		        }
		    });
	 }
	
	private	 void startAction() {
		if (!serverWin.getPort().equals("")) {
			try {
				final int port = Integer.parseInt(serverWin.getPort());
				serverWin.initialize(true);
				print("Server started on:"+myserver.toDay());
				myserver.setPort(port);
				chatService = new Thread(myserver);
				chatService.start();
				}catch(Exception ex) {
					serverWin.initialize(false);
					JOptionPane.showMessageDialog(serverWin, "Error: Can not start the server", "ERROR",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ServerAppManager.class.getResource("/errorMsg.png")));
					ex.printStackTrace();
				}
			
		}else JOptionPane.showMessageDialog(serverWin, "Please specify the port", "WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ServerAppManager.class.getResource("/alertMsg.png")));
	}
	
	@SuppressWarnings("static-access")
	private	 void exportHistMsgAction() {
		try{	
			if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = new File(saveFile.getSelectedFile()+".txt");
					if(!file.exists()) file.createNewFile();
	           
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
	           
					bw.write("History of exported messages on :"+myserver.toDay());
					bw.newLine();
	           
					for(String msg : myserver.chatDb.getMessages()) {
	        	   			bw.write(msg);
	        	   			bw.newLine();	
					}
	           
					bw.newLine();   
					bw.close();     
					fw.close();	   
					print("the message history has been exported from the database on: "+myserver.toDay());
	           
	           }
		}catch(Exception ex){
	        	   ex.printStackTrace();
	           }
		
	}
	
	private	 void saveLogFileAction() {
		try{
			if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = new File(saveFile.getSelectedFile()+".txt");
				if(!file.exists()) file.createNewFile();
           
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
           
				bw.write("Log file saved on :"+myserver.toDay());
				bw.newLine();
				bw.write("-----------Users connected :----------------");
				for(String users : myserver.getCurrentUsers()) {
					bw.write("	-> "+users);
					bw.newLine();
				}
				bw.write("--------------------------------------------");
				bw.newLine();
				bw.write(serverWin.console.getText());
				bw.newLine();
           	               
				bw.close();     
				fw.close();	   
           
				print("Log file exported on: "+myserver.toDay());
			}
           }catch(Exception ex){
        	    	ex.printStackTrace();
           }
	}

	private	 void showOnlineUsersAction() {
		print("---------------------------");
		print("Currently logged in users :");
		for(String users : myserver.getCurrentUsers()) {
			print("	-> "+users);
		}
		print("---------------------------");
	}
	
	
	@SuppressWarnings("deprecation")
	private	 void stopAction() {
		try {
			chatService.stop();
			myserver.stopServer();
			myserver.kickAllUsers();
			serverWin.initialize(false);
			print("Server stopped on:"+myserver.toDay());
			print("All online users will be kicked from this server");
			print(">> Server is OFF !");	
		}catch (Exception ex) {
			System.out.println(ex);
		}
	}

	 static void alert (String msg) {
		print(msg);
		JOptionPane.showMessageDialog(serverWin, msg, "ERROR" ,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ServerAppManager.class.getResource("/errorMsg.png")));
	}
	
	
	
}

