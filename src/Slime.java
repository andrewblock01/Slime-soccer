import java.awt.*;

public class Slime extends GameObj {

	private static final int INIT_VEL_X = 0;
	private static final int INIT_VEL_Y = 0;
	private static final int WIDTH = 40;
	private static final int HEIGHT = 30;
	private Color color;

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
		super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y - HEIGHT / 2, WIDTH, HEIGHT,
				courtWidth, courtHeight, timeStep);
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillArc(pos_x, pos_y, width, height, 0, 180);
	}
}
