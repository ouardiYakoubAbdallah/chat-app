package ServerSide;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatDatabase {
	private Connection con=null;
	private PreparedStatement pst=null;
	private ResultSet rs=null;
	
	void createConnetion(String db) throws SQLException {
			con = DriverManager.getConnection("jdbc:mysql://localhost/"+db+"?user=root&password=123456&useSSL=false");
	}
	
	private void query(String sql) throws SQLException {
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
	}
	
	void clearHistoryMsgs() throws SQLException {
		pst=con.prepareStatement("DELETE FROM message");
		pst.executeUpdate();
	}
	
	void saveMessage(int id,String msg) throws SQLException {
		//INSERT INTO message VALUES ("+id+",'"+msg+"');
		pst=con.prepareStatement("INSERT INTO message VALUES(?,?)");
		pst.setInt(1, id);
		pst.setString(2, msg);
		
		pst.executeUpdate();
	}
	
	ArrayList<String> getMessages() throws SQLException {
		ArrayList<String> messages = new ArrayList<String>();
		query("SELECT contenu FROM message");
		while (rs.next()) {
			messages.add(rs.getString(1));
		}
		return messages;
	}
	
	void insertUser(String nom,String prenom,String dateNaissance,String email,String pseudo,String password) throws SQLException, ParseException {
		String date = dateNaissance;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
		java.util.Date dateStr = formatter.parse(date);
		Date bDate = new Date(dateStr.getTime());
		if(dateNaissance.equals(bDate.toString())) {
		//INSERT INTO utilisateur (`nom`, `prenom`, `dateNaissance`, `email`, `pseudo`, `password`) VALUES ('"+nom+"', '"+prenom+"', '"+dateDB+"', '"+email+"', '"+pseudo+"', '"+password+"');"
			pst=con.prepareStatement("INSERT INTO utilisateur (`nom`, `prenom`, `dateNaissance`, `email`, `pseudo`, `password`) VALUES (?,?,?,?,?,?);");
			pst.setString(1, nom);
			pst.setString(2, prenom);
			pst.setDate(3,bDate);
			pst.setString(4, email);
			pst.setString(5, pseudo);
			pst.setString(6, password);
			pst.executeUpdate();
		}else throw new SQLException("Date not valid");
	}
	
	 int getId(String pseudo) throws SQLException {
		//rs=pst.executeQuery("SELECT idutilisateur FROM utilisateur WHERE pseudo='"+pseudo+"';");
		pst=con.prepareStatement("SELECT idutilisateur FROM utilisateur WHERE pseudo= ?;");
		pst.setString(1, pseudo);
		
		rs = pst.executeQuery();
		if(rs.next()) {
			return(rs.getInt(1));
		}
		else {
			throw new SQLException();
		}
	}
	
	boolean checkUser(String login) throws SQLException {
		String[] loginfo = login.split(",");
		//query("SELECT * FROM utilisateur WHERE pseudo='"+loginfo[0]+"' AND password='"+loginfo[1]+"';");
		pst=con.prepareStatement("SELECT * FROM utilisateur WHERE pseudo=? AND password=?;");
		pst.setString(1, loginfo[0]);
		pst.setString(2, loginfo[1]);
		
		rs = pst.executeQuery();
		return rs.next();
	}
	
	boolean pseudoTaken(String pseudo) throws SQLException {
		//query("SELECT * FROM utilisateur WHERE pseudo='"+pseudo+"';");
		pst=con.prepareStatement("SELECT * FROM utilisateur WHERE pseudo=?;");
		pst.setString(1, pseudo);
		
		rs = pst.executeQuery();
		return (rs.next());
	}
	
	boolean emailTaken(String email) throws SQLException {
		//query("SELECT * FROM utilisateur WHERE email='"+email+"';");
		pst=con.prepareStatement("SELECT * FROM utilisateur WHERE email=?;");
		pst.setString(1, email);
		
		rs = pst.executeQuery();
		return (rs.next());
	}
	
}
