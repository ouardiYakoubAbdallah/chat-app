package ClientSide;

public class ClientProgram {
		
	private static ClientAppManager clientUI;

	public static void main(String[] args) {
		try {
		clientUI = new ClientAppManager();
		clientUI.setSystemLookAndFeel();
		clientUI.launchApplication();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
