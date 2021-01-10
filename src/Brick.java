import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
public class Brick {
	private ImageView myView;
	private final int HEIGHT = 20;
	private final int WIDTH = 60;
	private int startingX = 150;
	private int startingY = 200;
	
	private final int NUMBER_OF_COLUMNS = 6;
	private final int NUMBER_OF_ROWS = 4;
	
	private final int ROW_SPACE_MULTIPLER = 70;
	private final int COLUMN_SPACE_MULTIPLER = 30;
	private int bricksDestroyed = 0;
	
	/**
	 * Create a brick from a given image.
	 */
	public Brick (Image image, int rowWidthSpace, int columnHeightSpace, int screenWidth, int screenHeight) {
		myView = new ImageView(image);
		myView.setFitWidth(WIDTH);
		myView.setFitHeight(HEIGHT);
		// make sure it stays within the bounds
		myView.setX(startingX + rowWidthSpace);
		myView.setY(startingY + columnHeightSpace);
	}
	
	public Brick() {
		// TODO Auto-generated constructor stub
	}
	//
	public void removeBrokenBricks(ArrayList<Brick> unBrokenBricks, ArrayList<Brick> hitBricks) {
		for(int i = 0; i < hitBricks.size(); i++) {
			unBrokenBricks.remove(hitBricks.get(i));
		}
	}
	
	public ArrayList<Brick> checkIfBallHit(Ball gameBall, ArrayList<Brick> myBricks){
		bricksDestroyed += 1;
		ArrayList<Brick> hitBricks = new ArrayList<>();
		
		//Checks if brick is hit	
				for(int i = 0; i < myBricks.size(); i++) {
					if(gameBall.getView().getBoundsInParent().intersects(myBricks.get(i).getView().getBoundsInParent())) {
						gameBall.hitObject();
						myBricks.get(i).getView().setVisible(false);
						hitBricks.add(myBricks.get(i));				
					}
				}
				
		return hitBricks;
	}

	public boolean allBricksDestroyed(ArrayList<Brick> allBricks) {
		if(allBricks.size() == 0) {
			return(true);
		}
		else {
			return false;
		}
	}

	public ArrayList<Brick> makeBrickRows(Image brickPic, Group root, ArrayList<Brick> myBricks) {
		for(int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			for(int j = 0; j < NUMBER_OF_ROWS; j++) {
				Brick brick = new Brick(brickPic, i*ROW_SPACE_MULTIPLER, j*COLUMN_SPACE_MULTIPLER, WIDTH, HEIGHT);
				myBricks.add(brick);
				root.getChildren().add(brick.getView());
			}
		}
		
		
		return myBricks;
	}
	
	public int getNumberOfBricksDestroyed() {
		return bricksDestroyed;
	}
	/**
	 * Returns internal view of bouncer to interact with other JavaFX methods.
	 */
	public Node getView () {
		return myView;
	}
}