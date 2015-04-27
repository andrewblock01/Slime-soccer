import java.awt.*;

public class Slime extends GameObj {

	private static final int INIT_VEL_X = 0;
	private static final int INIT_VEL_Y = 0;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 40;
	private Color color;
	private boolean jumping = false;

	/**
	 * 
	 * @param courtWidth
	 * @param courtHeight
	 * @param timeStep
	 * @param init_x : initial lower left corner of the slime
	 * @param init_y : initial lower left corner of the slime
	 * @param color
	 */
	public Slime(int courtWidth, int courtHeight, int timeStep, int init_x,
			int init_y, Color color) {
		super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y - HEIGHT, WIDTH, HEIGHT,
				courtWidth, courtHeight, timeStep);
		this.max_y = courtHeight - height / 2;
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillArc(pos_x, pos_y, width, height, 0, 180);
	}

	public void slimeBounce(int angle) {

	}

	public double slimeAngle (GameObj other) {
		if (this.willIntersect(other)) {
			double dx = other.pos_x + other.width /2 - (pos_x + width /2);
			double dy = other.pos_y + other.height/2 - (pos_y + height/2);

			double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
			double diagTheta = Math.atan2(height / 2, width / 2);
			return diagTheta;
		} else {
			return 5000;
		}
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
}
