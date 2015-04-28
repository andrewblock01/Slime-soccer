import java.awt.*;


public class Goal extends GameObj {
	
	private static final int INIT_VEL_X = 0;
	private static final int INIT_VEL_Y = 0;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 40;

	/**
	 * 
	 * @param courtWidth
	 * @param courtHeight
	 * @param timeStep
	 * @param init_x : initial lower left corner of the slime
	 * @param init_y : initial lower left corner of the slime
	 * @param color
	 */
	public Goal(int courtWidth, int courtHeight, int init_x, int init_y) {
		super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y - HEIGHT / 2, WIDTH, HEIGHT,
				courtWidth, courtHeight);
	}
	
	@Override
	public void draw(Graphics g) {
		System.out.println("I have arrived");
		g.setColor(Color.yellow);
		g.fillRect(pos_x, pos_y, width, height);
	}

	public static int getWidth() {
		return WIDTH;
	}
}
