package ClientSide;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ConnectWindow extends JFrame {

	
	JTextField tfHost = new JTextField();
	JTextField tfPort = new JTextField();
	
	private JLabel lHost = new JLabel("HOST :");
	private JLabel lPort = new JLabel("PORT :");
	private JLabel Image = new JLabel("");
	private JLabel lIndic = new JLabel("<html><b>Indicate the host and the port of the<br>  chat server :</b></html>");
	
	private JButton buClose = new JButton("Close");
	JButton buNext = new JButton("Next >");
	
	
	public ConnectWindow() throws IOException {
		buildConnectWindow();
	}
	
	private void buildConnectWindow () throws IOException {
		this.setTitle("Setting the Host & the Port");
		this.setBounds(100, 100, 580, 291);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		configureConnectWindow();
		this.setVisible(true);
	
	}

	
	
	public int getPort() {
		return Integer.parseInt(tfPort.getText());
	}
	
	public String getHost() {
		return tfHost.getText();
	}

	private void configureConnectWindow() throws IOException {
		Image.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/hostServ.png"))));
		Image.setBounds(6, 6, 261, 245);
		Image.setVisible(true);
		this.getContentPane().add(Image);
		
		
		buClose.setBounds(292, 197, 117, 29);
		buClose.setVisible(true);
		buClose.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.exit(0);
					}
				}
				);
		this.getContentPane().add(buClose);
		
		
		buNext.setBounds(435, 197, 117, 29);
		buNext.setVisible(true);
		this.getContentPane().add(buNext);
		tfHost.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfHost.setBounds(377, 95, 161, 26);
		tfHost.setVisible(true);
		this.getContentPane().add(tfHost);
		tfHost.setColumns(10);
		
		
		lHost.setBounds(320, 100, 61, 16);
		lHost.setVisible(true);
		this.getContentPane().add(lHost);
		tfPort.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfPort.setColumns(10);
		tfPort.setBounds(423, 135, 66, 26);
		tfPort.setVisible(true);
		this.getContentPane().add(tfPort);
		
		
		lPort.setBounds(368, 140, 41, 16);
		lPort.setVisible(true);
		this.getContentPane().add(lPort);
		
		
		lIndic.setBounds(279, 29, 254, 43);
		lIndic.setVisible(true);
		this.getContentPane().add(lIndic);

	}
	

}
