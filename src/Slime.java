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
	public Slime(int courtWidth, int courtHeight, int init_x,
			int init_y, Color color) {
		super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y - HEIGHT / 2, WIDTH, HEIGHT,
				courtWidth, courtHeight);
		this.max_y = courtHeight - height / 2;
		this.color = color;
	}
	/**
	 * 
	 * @param angle : angle from 3 o'clock
	 * @return the 'radius' of the slime at the given angle
	 */
	public static double radius(double angle) {
		
		double x_comp = Math.cos(angle) / (WIDTH / 2);
		double y_comp = Math.sin(angle) / (HEIGHT / 2);
		double length = Math.sqrt(1 / (x_comp * x_comp + y_comp * y_comp));
//		System.out.println("length: " + length);
		return length;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillArc(pos_x, pos_y, width, height, 0, 180);
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
