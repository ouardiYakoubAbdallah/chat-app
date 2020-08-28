package ServerSide;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ServerReturnTool implements Runnable {
	private Socket sock;
	private Scanner input;
	private String msg="";
	
	
	public ServerReturnTool(Socket s) {
		this.sock=s;
		
	
	}	
	
	private void tellEveryOne() {
		try {
			String temp[] = msg.split(":");
			int id = ServerTools.chatDb.getId(temp[0]);
			ServerTools.chatDb.saveMessage(id, msg);
			String servMsg = msg.replace(":", " said: ");
			ServerAppManager.print(servMsg);
			for(int i=1;i<=ServerTools.ConnectionArray.size();i++) {
				Socket sockeTmp = (Socket) ServerTools.ConnectionArray.get(i-1);
				PrintWriter ouTmp = new PrintWriter(sockeTmp.getOutputStream());
				ouTmp.println(msg);
				ouTmp.flush();
			}
		}catch(Exception e) {
			
		}
	}
	
	private void reportDisconnect() throws IOException {
		String temp = msg.substring(60);
		ServerTools.CurrentUsers.removeElement(temp);
		String msgServer=temp+" has disconnected !";
		ServerAppManager.print(temp+" has disconnected from the server");
		ServerTools.ConnectionArray.removeElement(sock);
		for(int i=1;i<=ServerTools.ConnectionArray.size();i++) {
			Socket sockeTmp = (Socket) ServerTools.ConnectionArray.get(i-1);
			PrintWriter out = new PrintWriter(sockeTmp.getOutputStream());
			out.println("$2y$10$3QDm04/GKqY9sjpv1ZRavOmHH99O8540/pJ9ua8FTsQmcu2Vzbdoi"+ ServerTools.CurrentUsers);
			out.println(msgServer);
			out.flush();
			}
		
	}
	
	@Override
	public void run()  {
		// TODO Auto-generated method stub
		try {
				input = new Scanner(sock.getInputStream());
				while(sock.isConnected()) {
					//checkConnection;
					if(!input.hasNext()) return; // Do nothing
					else {
						msg=input.nextLine();
						if(msg.contains("$2y$10$e5q5KjUb8our0SEkchhnbOC.aYSXrv93Mm6mNj8z4Yj2pvyEj48F2")) {
							reportDisconnect();
						}
						else {
							tellEveryOne();
						}
				}
			
			}
		}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

