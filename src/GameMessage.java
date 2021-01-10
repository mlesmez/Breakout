import javafx.scene.text.*;

public class GameMessage {
	
	private Text gameMessage; 
	
	public Text makeMessage(int xCoordinate, int yCoordinate, String message, int fontSize) {
		gameMessage = new Text(xCoordinate, yCoordinate, message);
		gameMessage.setFont(new Font (fontSize));
		
		return gameMessage;
	}
}