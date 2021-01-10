import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	//Constants
	public static final String TITLE = "Breaker";
	public static final int SIZE = 700;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final Paint BACKGROUND1 = Color.AZURE;
	public static final Paint BACKGROUND2 = Color.AQUAMARINE;

	//Images
	public static final String BALL_IMAGE = "resources/ball.gif";
	public static final String BRICK_IMAGE = "resources/brick4.gif";

	// some things we need to remember during our game
	private Scene levelOne;
	private Scene levelTwo;
	private Ball gameBall;
	private Brick bricks = new Brick();
	private Paddle paddleObject = new Paddle();
	Rectangle paddle;
	private ArrayList<Brick> myBricks;
	private ArrayList <Brick> hitBricks;
	public static int lives = 3;
	public static int score = 0;
	private GameMessage myGameMessage = new GameMessage();
	private Text livesRemainingText;
	private Text winOrLossText;
	public boolean gameOver;
	private int ballSpeedLevel = 0;
	private int sceneNum = 2;



	//Idea for sccore
//	public void updateScore() {
//		score = score + 100;
//	}
//	
//	public void showScore(int score) {
//		Text text = new Text();
//		text.setText("Score: " + score);
//		text.setX(50);
//		text.setY(50);
//	}

	/**
	 * Initialize what will be displayed and how it will be updated.
	 */
	@Override
	public void start (Stage stage) {
		// attach scene to the stage and display it
		levelOne = setupGame(SIZE, SIZE, BACKGROUND1);
		stage.setScene(levelOne);

		stage.setTitle(TITLE);
		stage.show();

		// attach "game loop" to time line to play it (basically just calling step() method repeatedly forever)
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		//make method
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	// Create the game's "scene": what shapes will be in the game and their starting properties
	private Scene setupGame (int width, int height, Paint background) {
		// create one top level collection to organize the things in the scene
		Group root = new Group();
		// Makes ball and Bricks
		try {
			Image ballImage = new Image(new FileInputStream(BALL_IMAGE));
			gameBall = new Ball(ballImage, width, height);
			root.getChildren().add(gameBall.getView());
			Image brickImage = new Image(new FileInputStream(BRICK_IMAGE));			
			myBricks = new ArrayList<>();
			
			Brick bricks = new Brick(brickImage, 0, 0, width, height);
			myBricks = bricks.makeBrickRows(brickImage, root, myBricks);
			
		}
		catch (FileNotFoundException e) {}

		
		//Makes paddle - add constants later
		paddle = paddleObject.makePaddle();
		root.getChildren().add(paddle);
		
		//create message for player
		//make message parameters are (xCoordinate,yCoordinate,font size)
		livesRemainingText = myGameMessage.makeMessage(10,50, "Lives Remaining: " + 3, 20);
        root.getChildren().add(livesRemainingText);
        
        winOrLossText = myGameMessage.makeMessage(250, 400, "", 50);
        root.getChildren().add(winOrLossText);

		// create a place to see the shapes
		Scene scene = new Scene(root, width, height, background);
		
		// respond to input
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	//Movements made each step
	private void step (double elapsedTime) {
		gameBall.move(elapsedTime);
		//Check if ball hits paddle
		if(gameBall.getView().getBoundsInParent().intersects(paddle.getBoundsInParent())) {
			gameBall.hitObject();
		}
		
		//Checks if ball hits a brick and gets rid of it
		ArrayList<Brick> hitBricks = bricks.checkIfBallHit(gameBall, myBricks);
		
		if (ballSpeedLevel < bricks.getNumberOfBricksDestroyed()) {
			//System.out.println(bricks.getNumberOfBricksDestroyed());
			ballSpeedLevel = bricks.getNumberOfBricksDestroyed();
			gameBall.increaseSpeed(ballSpeedLevel);
		}
		
		//Update the score
//		for(int i = 0; i < hitBricks.size(); i++) {
//			updateScore();
//		}
//		
		//Destroys the broken bricks
		bricks.removeBrokenBricks(myBricks, hitBricks);
		
		//check if ball hit floor and subtract a life and display lives remaining to user
		if(gameBall.checkIfBelowPaddle(levelOne.getWidth(), levelOne.getHeight())) {
			if (lives > 0) {
				livesRemainingText.setText(livesRemainingText.getText().substring(0, livesRemainingText.getText().length() - 1));
				livesRemainingText.setText(livesRemainingText.getText() + Integer.toString(lives - 1));
			}
			lives -= 1;
			}
		if (lives == 0){
			winOrLossText.setText("You Lost");
			gameOver = true;
		}
		if (bricks.allBricksDestroyed(myBricks) && gameOver == false) {
			winOrLossText.setText("You Won!");
		}
		
		// bounce off all the walls
		gameBall.bounce(levelOne.getWidth(), levelOne.getHeight());
	}
	// What to do each time a key is pressed
	private void handleKeyInput (KeyCode code) {
		if (code == KeyCode.LEFT) {
			paddleObject.moveLeft(paddle.getX());
		}
		else if (code == KeyCode.RIGHT) {
			paddleObject.moveRight(paddle.getX());
		}
		else if (code == KeyCode.DIGIT2) {
			System.out.println("Going to level 2");
			sceneNum = 2;
		}
		else if (code == KeyCode.DIGIT1) {
			System.out.println("Going to level 1");
			sceneNum = 1;

		}
	}



	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}