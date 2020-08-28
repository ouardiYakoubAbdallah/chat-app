package ServerSide;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;


public class ServerTools implements Runnable {
	static Vector<Socket> ConnectionArray = new Vector<Socket>();
	static Vector<String> CurrentUsers = new Vector<String>();
	Scanner input;
	
	int port;
	
	static ChatDatabase chatDb = new ChatDatabase();
	
	private ServerSocket myserver;
	
	void setPort(int port) {
		this.port=port;
	}
	
	public void run() {	
		startServer();
	}
		
	void startServer() {
			try {
				myserver = new ServerSocket(port);
				chatDb.createConnetion("chatdb");
				ServerAppManager.print(">> Server is ON ! \nWaiting for clients...");
			}catch(IOException ex) {
				ex.printStackTrace();
				ServerAppManager.initializeServerWindow(false);
				ServerAppManager.alert("Cannot start the server,this port is already in use (RESERVED)");
			}catch(SQLException e) {
				e.printStackTrace();
				ServerAppManager.initializeServerWindow(false);
				ServerAppManager.alert("Cannot start the server,The connection to the database failed");
			}
				
		while(true) {
			try {
						Socket sock = myserver.accept();
						input = new Scanner(sock.getInputStream());
						ConnectionArray.add(sock);
						checkUsers(sock);
						
						ServerReturnTool chat = new ServerReturnTool(sock);
						Thread ReturnService = new Thread(chat);
						ReturnService.start();
			}catch(Exception ex) {
			System.out.println(ex.getMessage());
			}		
				}
		
	}
	
	private void refreshOnlineUsers(String msgServer) throws IOException {
		for(int i=1;i<=ServerTools.ConnectionArray.size();i++) {
			Socket sockeTmp = (Socket) ServerTools.ConnectionArray.get(i-1);
			PrintWriter out = new PrintWriter(sockeTmp.getOutputStream());
			out.println("$2y$10$3QDm04/GKqY9sjpv1ZRavOmHH99O8540/pJ9ua8FTsQmcu2Vzbdoi"+ CurrentUsers);
			out.println(msgServer);
			out.flush();
			}
	}
	
	private void addNewUsers(Socket sock,String infoNewUser) throws Exception {
		String temp = infoNewUser.substring(60);
		String info[] = temp.split(",");
		PrintWriter out = new PrintWriter(sock.getOutputStream());
		if(chatDb.pseudoTaken(info[4]) || chatDb.emailTaken(info[3])) {
			out.println("$2y$10$aRWPPk2zyyDuSWd9Ig1XJeawcrwNgf9nI35xLYAX8NOnPjLGolS5O"+"Another user is already registered with this username / email");
			out.flush();
			checkUsers(sock);
		}
		else {
			try {
				chatDb.insertUser(info[0], info[1], info[2], info[3], info[4], info[5]);
				ServerAppManager.print("A new user has registered with pseudo : "+info[4]+" ,from : "+sock.getRemoteSocketAddress());
				out.println("$2y$10$BTXxTHBcyLVHYwZV6mIawOiiRxS9ZspHjaXyqU8GOmLCgVxve18nG"+"Successful registration");
				out.flush();
				checkUsers(sock);
			}catch(SQLException e) {
				out.println("$2y$10$aRWPPk2zyyDuSWd9Ig1XJeawcrwNgf9nI35xLYAX8NOnPjLGolS52");
				out.flush();
				checkUsers(sock);
			}
		}
	}

	

	void stopServer() throws IOException {
		myserver.close();
	}
	
	Vector<String> getCurrentUsers() {
		return CurrentUsers;
	}
	
	void kickAllUsers() throws IOException {
		CurrentUsers.removeAllElements();
		for (int i=0;i<ServerTools.ConnectionArray.size();i++){
				PrintWriter out = new PrintWriter(ConnectionArray.get(i).getOutputStream());
				out.println("$2y$10$pk10LKk/uNMgIffnXzVNpO8NpqcMHJMHVCBjMX3aPvC3lkfBNLwTi"+"Connection lost. Reason :the server is down!");
				out.flush();
			}
		for (int i=0;i<ServerTools.ConnectionArray.size();i++){
				ConnectionArray.get(i).close();
				ConnectionArray.remove(i);
			}
		
	}
	
	 String timeNow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return("["+sdf.format(cal.getTime())+"] ");
		
	}
	
	String toDay() {
		Date today = new Date();
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
		DateFormat.MEDIUM,
		DateFormat.MEDIUM);
		return(mediumDateFormat.format(today));
	}
	
	private void checkUsers(Socket sock) throws IOException, SQLException {
		// TODO Auto-generated method stub
		String userName = input.nextLine();
		String msgServer=null;
		
		if(CurrentUsers.contains(userName.split(",")[0])) {
				for (int i=0;i<ServerTools.ConnectionArray.size();i++){
					if (ConnectionArray.get(i)==sock) {
						PrintWriter out = new PrintWriter(ConnectionArray.get(i).getOutputStream());
						out.println("$2y$10$pk10LKk/uNMgIffnXzVNpO8NpqcMHJMHVCBjMX3aPvC3lkfBNLwTi"+"A user is already logged in with this account");
						out.flush();
						ConnectionArray.get(i).close();
						ConnectionArray.remove(i);
					}
				}
			}else if(userName.contains("$2y$10$HjuLbDpE/3W1XZnYBx22x.84fY5P8LPwFfNG1t8Evyi9PRfMDaGsO")) {
				try {
					addNewUsers(sock,userName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(chatDb.checkUser(userName)) {
					String[] login = userName.split(",");
					CurrentUsers.add(login[0]); 
					msgServer=login[0]+" has joined the conversation !";
					ServerAppManager.print(login[0]+" connected to the server from: "+sock.getRemoteSocketAddress());
					PrintWriter out = new PrintWriter(sock.getOutputStream());
					out.println("Successful connection ");
					for(String msg : chatDb.getMessages()) {
						out.println(msg);
						out.flush();
						}
					refreshOnlineUsers(msgServer);
			}else {
							PrintWriter out = new PrintWriter(sock.getOutputStream());
							out.println("$2y$10$pk10LKk/uNMgIffnXzVNpO8NpqcMHJMHVCBjMX3aPvC3lkfBNL2II"+"There's no user with this pseudo and password !");
							out.flush();
							ConnectionArray.removeElement(sock);
							
			}
			
		
		}
	}

