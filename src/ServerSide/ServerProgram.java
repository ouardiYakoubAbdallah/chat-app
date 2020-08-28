package ServerSide;

import java.io.IOException;

public class ServerProgram {
		static ServerAppManager serverUI;
	public static void main(String[] args) {
		try {
			serverUI = new ServerAppManager();
			serverUI.setSystemLookAndFeel();
			serverUI.launchApplication();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
