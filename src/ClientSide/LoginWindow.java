package ClientSide;


import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



@SuppressWarnings("serial")
public class LoginWindow extends JFrame{
	
	
	JButton buEnter = new JButton("CONNECT");
	JButton buRegister = new JButton("REGISTER");
	
	private JLabel userImage = new JLabel();
	private JLabel lEnterUserName = new JLabel("Username: ");
	private JLabel lEnterPassword = new JLabel("Password: ");
	
	JTextField tfUserNameBox=new JTextField(20);
	JPasswordField tfPasswordBox=new JPasswordField(20);
	
	String host;
	int port;

	public LoginWindow(String host,int port) {
		this.host=host;
		this.port=port;
	}

	public String getPassword() {
		return new String(tfPasswordBox.getPassword());
	}
	
	public String getUserName() {
		return tfUserNameBox.getText();
	}
	
	void buildLoginWindow() throws IOException {
			this.setBounds(100, 100, 349, 297);
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			this.getContentPane().setLayout(null);
			this.setTitle("LogIn :");
			this.setResizable(false);
			
			configureLoginWindow();
			
	}
	
	private void configureLoginWindow() throws IOException {
		
		userImage.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/user.png"))));
		userImage.setBounds(142, 52, 64, 82);
		this.getContentPane().add(userImage);
		
		tfUserNameBox.setBounds(127, 146, 198, 26);
		this.getContentPane().add(tfUserNameBox);
		tfUserNameBox.setColumns(10);
		
		tfPasswordBox.setBounds(127, 184, 198, 26);
		this.getContentPane().add(tfPasswordBox);
		
		lEnterUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lEnterUserName.setBounds(23, 151, 81, 16);
		this.getContentPane().add(lEnterUserName);
		
		lEnterPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lEnterPassword.setBounds(23, 189, 81, 16);
		this.getContentPane().add(lEnterPassword);
		
		JLabel lInfo = new JLabel("Connection to the Chat Server  "+host+" : "+port);
		lInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo.setBounds(0, 17, 349, 16);
		this.getContentPane().add(lInfo);
		
		buEnter.setBounds(193, 225, 117, 29);
		buEnter.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/key.png"))));
		this.getContentPane().add(buEnter);
		
		buRegister.setBounds(38, 225, 117, 29);
		buRegister.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/register.png"))));
		this.getContentPane().add(buRegister);
		
		
	}

}
