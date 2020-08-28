package ClientSide;
import java.net.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ChatClient implements Runnable {
	
	Scanner input;
	PrintWriter out;
	DefaultTableModel model;
	Socket sock;
	
	public ChatClient(Socket sock) {
		this.sock= sock;
		
	}
	
	void send(String text) {
		// TODO Auto-generated method stub
		out.println(ClientAppManager.userName+": "+text);
		out.flush();
	}

	
	void disconnect() throws IOException {
		sock.close();
		JOptionPane.showMessageDialog(ClientAppManager.mainWin, "You disconnected !", "User disconnected" ,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
	}

	@Override
	public void run() {
		try {
			try {
				input = new Scanner(sock.getInputStream());
				out = new PrintWriter(sock.getOutputStream());
				out.flush();
				checkStream();
			}finally {
				sock.close();
			}
		}catch(Exception ex) { System.out.println(ex);}
	}


	private void checkStream() {
		while(true) recive();
		
	}

	private void refreshUsersList(String message) {
		model = new DefaultTableModel();
		model.addColumn("Users currently online");
		String temp1=message.substring(60);
			   temp1=temp1.replace("[", "");
			   temp1=temp1.replace("]", "");
			   String[] currentUsers = temp1.split(", ");
		for(int i=model.getRowCount();i<currentUsers.length;i++) model.addRow(new Object[] {currentUsers[i]});
		ClientAppManager.mainWin.jlOnline.setModel(model);
	}
	
	
	
	private void recive() {
		try {
			if(input.hasNext()) {
				String message = input.nextLine();
					if(message.contains("$2y$10$3QDm04/GKqY9sjpv1ZRavOmHH99O8540/pJ9ua8FTsQmcu2Vzbdoi")) {
						refreshUsersList(message);
					}
					else {
						if(message.contains("$2y$10$pk10LKk/uNMgIffnXzVNpO8NpqcMHJMHVCBjMX3aPvC3lkfBNLwTi")) {
							ClientAppManager.mainWin.resetMainWindow();
							String temp1=message.substring(60);
							JOptionPane.showMessageDialog(ClientAppManager.mainWin, temp1, "WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/errorMsg.png")));
							System.exit(0);
						}else if(message.contains("$2y$10$pk10LKk/uNMgIffnXzVNpO8NpqcMHJMHVCBjMX3aPvC3lkfBNL2II")){
							ClientAppManager.mainWin.resetMainWindow();
							String temp1=message.substring(60);
							JOptionPane.showMessageDialog(ClientAppManager.mainWin, temp1, "WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/errorMsg.png")));
							sock.close();
						}else 
							ClientAppManager.mainWin.print(message +"\n");
					}
			 	}
		}catch(IOException e) {e.printStackTrace();}
	}	
}

