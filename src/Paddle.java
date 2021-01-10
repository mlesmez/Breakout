import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Paddle {
	private final int HEIGHT = 20;
	private final int WIDTH = 50;
	private Rectangle paddle;
	private Color paddleColor = Color.THISTLE;
	public static final int PADDLE_SPEED = 5;
	private static final int X_COORDINATE = 325;
	private static final int Y_COORDINATE = 600;

	public Rectangle makePaddle() {
		paddle = new Rectangle(X_COORDINATE, Y_COORDINATE, WIDTH, HEIGHT);
		paddle.setFill(paddleColor);

		return paddle;
	}
	
	public void moveLeft(double paddlePosition) {
		if (paddlePosition > 0) {
			paddle.setX(paddlePosition - PADDLE_SPEED);
		}
	}
	
	public void moveRight(double paddlePosition) {
		if (paddlePosition < 650) {
			paddle.setX(paddlePosition + PADDLE_SPEED);
		}
	}
}