import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    private ImageView myView;
    private Point2D myVelocity;
    private int HEIGHT = 30;
    private int WIDTH = 30;
    private int startingX = 260;
    private int startingY = 485;
    private int ballDX = 100;
    private int ballDY = 100;
    private double speedIncreaser = 0;


    /**
     * Create a bouncer from a given image.
     */
    public Ball (Image image, int screenWidth, int screenHeight) {
        myView = new ImageView(image);

        myView.setFitWidth(WIDTH);
        myView.setFitHeight(HEIGHT);
        // make sure it stays within the bounds
        myView.setX(startingX);
        myView.setY(startingY);
        // turn speed into velocity that can be updated on bounces (dx, dy)
        myVelocity = new Point2D(ballDX, ballDY);
    }

    /**
     * Move by taking one step based on its velocity.
     *
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     */
    public void move (double elapsedTime) {
        myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
        myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }

    /**
     * Bounce off the walls represented by the edges of the screen.
     */
    public void bounce (double screenWidth, double screenHeight) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    public boolean checkIfBelowPaddle(double screenWidth, double screenHeight) {
    	if(myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
    		return true;
    	}
    	return false;
    }
    
    /*Bounce off of brick*/
    public void hitObject() {
    	myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }

	public void increaseSpeed(int bricksDestroyed) {
	speedIncreaser += 5 * bricksDestroyed;
	//myVelocity = new Point2D(myVelocity.getX() + speedIncreaser, myVelocity.getY() + speedIncreaser);
	}
}