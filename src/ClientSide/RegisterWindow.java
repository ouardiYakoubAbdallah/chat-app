package ClientSide;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class RegisterWindow extends JFrame{
	
	private JTextField tfFirstName= new JTextField(10);
	private JTextField tfLastName= new JTextField(10);
	private JTextField tfYear= new JTextField(4);
	private JTextField tfMonth= new JTextField(2);
	private JTextField tfDay= new JTextField(2);
	private JTextField tfEmail= new JTextField(10);
	private JTextField tfUserName= new JTextField(10);
	private JPasswordField tfPassword= new JPasswordField(10);
	private JPasswordField tfConfPassword= new JPasswordField(10);
	
	private JLabel lFirstName = new JLabel("First Name:");
	private JLabel errFN = new JLabel("");
	private JLabel errLN = new JLabel("");
	private JLabel lLastName = new JLabel("Last Name:");
	private JLabel errConfPass = new JLabel("");
	private JLabel lBirthDate = new JLabel("Birth's Date:");
	private JLabel errPass = new JLabel("");
	private JLabel lEmail = new JLabel("E-Mail :");
	private JLabel errBD = new JLabel("");
	private JLabel lPseudo = new JLabel("Username :");
	private JLabel errMail = new JLabel("");
	private JLabel lPassword = new JLabel("Password :");
	private JLabel errUser = new JLabel("");
	private JLabel lConfPassword = new JLabel("Confirm Password:");
	
	private JButton buReset = new JButton("RESET");
	private JButton buRegister = new JButton("REGISTER");
	
	private JPanel panel = new JPanel();
	
	Socket sock;
	PrintStream out;
	public RegisterWindow(Socket sock,PrintStream out) throws Exception {
		this.sock = sock;
		this.out = out;
		buildRegisterWindow();
		initialize();
	}
	
	private void initialize() {
		errBD.setVisible(false);
		errBD.setToolTipText("Enter date in the format: YYYY-MM-DD");
		
		errConfPass.setVisible(false);
		errConfPass.setToolTipText("Not identical to password entered ");
		
		errFN.setVisible(false);
		errFN.setToolTipText("Must not contain special characters such as: #, @,. /? ...");
		
		errLN.setVisible(false);
		errLN.setToolTipText("Must not contain special characters such as: #, @,. /? ...");
		
		errMail.setVisible(false);
		errMail.setToolTipText("Please enter a valid email address");
		
		errPass.setVisible(false);
		errPass.setToolTipText("must contain more than 8 characters");
		
		errUser.setVisible(false);
		errUser.setToolTipText("Must not contain special characters such as: #, @,. /? ...");
		
	}
	
	private void buildRegisterWindow() throws Exception{
		setTitle("Create new account");
		setSize(451, 370);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);
		configureRegisterWindow();
		registerWindowActions();
	}
	
	
	private void registerWindowActions() {
		 tfYear.addKeyListener(new KeyAdapter() {
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
		 
		 tfMonth.addKeyListener(new KeyAdapter() {
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
		 
		 tfDay.addKeyListener(new KeyAdapter() {
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
		buReset.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						tfYear.setText("");
						tfMonth.setText("");
						tfDay.setText("");
						tfConfPassword.setText("");
						tfEmail.setText("");
						tfFirstName.setText("");
						tfLastName.setText("");
						tfPassword.setText("");
						tfUserName.setText("");
					}
				}
				);
		
		buRegister.addActionListener(
				new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							buRegisterAction();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				);
	}
	
	private boolean errorProvider(JTextField tf) {
		if(tf.getText().contains("@") || tf.getText().contains("&") || tf.getText().contains("\"") || tf.getText().contains("/") ||
				tf.getText().contains("?") || tf.getText().contains("!") || tf.getText().contains("#") || tf.getText().contains("§") ||
				tf.getText().contains("'") || tf.getText().contains("(") || tf.getText().contains(")") || tf.getText().contains("[") ||
				tf.getText().contains("]") || tf.getText().contains("{") || tf.getText().contains("}") || tf.getText().contains(";") ||
				tf.getText().contains(",") || tf.getText().contains(".") || tf.getText().contains("%") || tf.getText().contains("^") ||
				tf.getText().contains("°") || tf.getText().contains("€") || tf.getText().contains("*") || tf.getText().contains("$") ) return true;
		
		else return false;
	}
	
	private boolean fieldsChecked() {
		return (!errPass.isVisible() && !errConfPass.isVisible() && !errBD.isVisible() && !errLN.isVisible() && !errFN.isVisible() &&
				!errUser.isVisible() && !errMail.isVisible() );
	}
	
	private void buRegisterAction() throws Exception {
		if(!tfYear.getText().equals("") && !tfMonth.getText().equals("") && !tfDay.getText().equals("") && !tfEmail.getText().equals("") && !tfFirstName.getText().equals("") 
				&& !tfLastName.getText().equals("") && !tfUserName.getText().equals("") && !new String(tfPassword.getPassword()).equals("")
				&& !new String(tfConfPassword.getPassword()).equals("") ) 
		{
			initialize();
			if(errorProvider(tfUserName) ) errUser.setVisible(true); 
			
			if(errorProvider(tfFirstName)) errFN.setVisible(true);
			
			if (errorProvider(tfLastName)) errLN.setVisible(true);
			
			if(!tfEmail.getText().contains("@") && !tfEmail.getText().contains(".") && tfEmail.getText().length()<5 ) errMail.setVisible(true);
			
			if(!new String(tfConfPassword.getPassword()).equals(new String(tfPassword.getPassword()))) errConfPass.setVisible(true);
			
			if(Integer.parseInt(tfDay.getText()) > 31 || Integer.parseInt(tfMonth.getText()) > 12)
			
			if(fieldsChecked()) {
				try {
					
					@SuppressWarnings("resource")
					Scanner input = new Scanner(sock.getInputStream());
					String infos=tfLastName.getText()+","+ tfFirstName.getText()+","+tfDay.getText()+"-"+tfYear.getText()+"-"+tfMonth.getText()+","+ tfEmail.getText()+","+ tfUserName.getText()+","+ new String(tfPassword.getPassword());
					out.println("$2y$10$HjuLbDpE/3W1XZnYBx22x.84fY5P8LPwFfNG1t8Evyi9PRfMDaGsO"+infos);
					out.flush();
					Thread.sleep(3000);
					String msg = input.nextLine();
					if(msg.contains("$2y$10$aRWPPk2zyyDuSWd9Ig1XJeawcrwNgf9nI35xLYAX8NOnPjLGolS5O")) {
						String temp = msg.substring(60);
						JOptionPane.showMessageDialog(this, temp,"WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/registerFailed.png")));
						
					}else if (msg.contains("$2y$10$BTXxTHBcyLVHYwZV6mIawOiiRxS9ZspHjaXyqU8GOmLCgVxve18nG")) {
						String temp = msg.substring(60);
						JOptionPane.showMessageDialog(this, temp,"",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/registerSuccess.png")));
						setVisible(false);	
						tfYear.setText("");
						tfMonth.setText("");
						tfDay.setText("");
						tfConfPassword.setText("");
						tfEmail.setText("");
						tfFirstName.setText("");
						tfLastName.setText("");
						tfPassword.setText("");
						tfUserName.setText("");
					}else if (msg.equals("$2y$10$aRWPPk2zyyDuSWd9Ig1XJeawcrwNgf9nI35xLYAX8NOnPjLGolS52")) {
						errBD.setVisible(true);
						JOptionPane.showMessageDialog(this, "Registration Failed", "WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/registerFailed.png")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, "Registration Failed", "WARNING",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/registerFailed.png")));
				}	
			}
		}
		else	 JOptionPane.showMessageDialog(this, "Please complete all fields", "",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ClientAppManager.class.getResource("/infoMsg.png")));
			
	}
	
	private void configureRegisterWindow() throws IOException {
		
		
		panel.setBounds(0, 0, 451, 348);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		lFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lFirstName.setBounds(56, 23, 87, 16);
		panel.add(lFirstName);
		
		tfFirstName.setBounds(155, 18, 230, 26);
		panel.add(tfFirstName);
		
		errFN.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errFN.setBounds(397, 23, 28, 21);
		panel.add(errFN);
		
		errLN.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errLN.setBounds(397, 57, 28, 26);
		panel.add(errLN);
		
		tfLastName.setBounds(155, 57, 230, 26);
		panel.add(tfLastName);
		
		lLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lLastName.setBounds(56, 62, 87, 16);
		panel.add(lLastName);
		
		errConfPass.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errConfPass.setBounds(397, 257, 28, 16);
		panel.add(errConfPass);
		
		tfYear.setBounds(271, 95, 42, 26);
		panel.add(tfYear);
		
		tfMonth.setBounds(343, 95, 42, 26);
		panel.add(tfMonth);
		
		tfDay.setBounds(155, 96, 79, 26);
		panel.add(tfDay);
		
		lBirthDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lBirthDate.setBounds(56, 101, 87, 16);
		panel.add(lBirthDate);
		
		errPass.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errPass.setBounds(397, 218, 28, 16);
		panel.add(errPass);
		
		tfEmail.setBounds(155, 135, 230, 26);
		panel.add(tfEmail);
		
		lEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lEmail.setBounds(56, 140, 87, 16);
		panel.add(lEmail);
		
		errBD.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errBD.setBounds(397, 96, 28, 26);
		panel.add(errBD);
		
		tfUserName.setBounds(155, 174, 230, 26);
		panel.add(tfUserName);
		
		lPseudo.setHorizontalAlignment(SwingConstants.RIGHT);
		lPseudo.setBounds(56, 179, 87, 16);
		panel.add(lPseudo);
		
		errMail.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errMail.setBounds(397, 135, 28, 26);
		panel.add(errMail);
		
		tfPassword.setBounds(155, 213, 230, 26);
		panel.add(tfPassword);
		
		lPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lPassword.setBounds(56, 218, 87, 16);
		panel.add(lPassword);
		
		errUser.setIcon(new ImageIcon(ImageIO.read(ClientAppManager.class.getResource("/err.png"))));
		errUser.setBounds(397, 174, 28, 26);
		panel.add(errUser);
		
		
		tfConfPassword.setBounds(155, 252, 230, 26);
		panel.add(tfConfPassword);
		
		lConfPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lConfPassword.setBounds(20, 257, 123, 16);
		panel.add(lConfPassword);
		
		
		buReset.setBounds(72, 300, 117, 29);
		panel.add(buReset);
		
		buRegister.setBounds(261, 300, 117, 29);
		panel.add(buRegister);
	}
}
