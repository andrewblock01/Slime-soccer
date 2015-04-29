/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Circle ball; // the soccer ball, bounces
	private Slime slime1; // the slime for player 1
	private Slime slime2; // the slime for player 2
	private Goal goal1; // the goal that slime1 protects
	private Goal goal2; // the goal that slime2 protects
	private int score1; // the score for player 1
	private int score2; // the score for player 2
	private int numTimesteps; // number of timesteps that have passed
	private ArrayList<Integer> highScores; // list of high scores

	public boolean playing = false; // whether the game is running
	public boolean pointScored = false; // whether a point has been scored
	public boolean pauseOn = false; // whether pause is on
	private JLabel status; // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 300;
	public static final int SLIME_MOVE_VELOCITY = 10;
	public static final int SLIME_JUMP_VELOCITY = 15;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	// Number of goals necessary to win
	public static final int MAX_GOALS = 5;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (!slime1.isJumping()) {
						slime1.v_y = -SLIME_JUMP_VELOCITY;
						slime1.setJumping(true);
					}
					break;
				case KeyEvent.VK_RIGHT:
					slime1.v_x = SLIME_MOVE_VELOCITY;
					break;
				case KeyEvent.VK_LEFT:
					slime1.v_x = -SLIME_MOVE_VELOCITY;
					break;
				case KeyEvent.VK_W:
					if (!slime2.isJumping()) {
						slime2.v_y = -SLIME_JUMP_VELOCITY;
						slime2.setJumping(true);
					}
					break;
				case KeyEvent.VK_D:
					slime2.v_x = SLIME_MOVE_VELOCITY;
					break;
				case KeyEvent.VK_A:
					slime2.v_x = -SLIME_MOVE_VELOCITY;
					break;
				case KeyEvent.VK_SPACE:
					if (pointScored) {
						pointScored = false;
						ball = new Circle(COURT_WIDTH, COURT_HEIGHT);
						slime1 = new Slime(COURT_WIDTH, COURT_HEIGHT,
								COURT_WIDTH - Slime.getWidth(), COURT_HEIGHT,
								Color.green);
						slime2 = new Slime(COURT_WIDTH, COURT_HEIGHT, 0,
								COURT_HEIGHT, Color.blue);
					}
					break;
				case KeyEvent.VK_P:
					pauseOn = !pauseOn;
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_LEFT:
					slime1.v_x = 0;
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_D:
					slime2.v_x = 0;
				}

			}

		});

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

		ball = new Circle(COURT_WIDTH, COURT_HEIGHT);
		slime1 = new Slime(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH
				- Slime.getWidth(), COURT_HEIGHT, Color.green);
		slime2 = new Slime(COURT_WIDTH, COURT_HEIGHT, 0, COURT_HEIGHT,
				Color.blue);
		goal1 = new Goal(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH
				- Goal.getWidth(), COURT_HEIGHT);
		goal2 = new Goal(COURT_WIDTH, COURT_HEIGHT, 0, COURT_HEIGHT);

		playing = true;
		pointScored = false;
		pauseOn = false;
		score1 = 0;
		score2 = 0;
		numTimesteps = 0;
		
		highScores = new ArrayList<Integer>(5);
		
		
		status.setText("Player 2: " + score2 + "  Player 1: " + score1
				+ "   Time: " + timeElapsed());

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing && !pointScored && !pauseOn) {

			// add one more timestep
			numTimesteps++;

			// advance the ball and slimes in their
			// current direction.
			ball.move();
			slime1.move();
			slime2.move();

			// allow slime to jump again if slime is on the ground
			if (slime1.pos_y == slime1.max_y)
				slime1.setJumping(false);
			if (slime2.pos_y == slime2.max_y)
				slime2.setJumping(false);

			// enact gravity on each object in play
			slime1.v_y += 1;
			slime2.v_y += 1;
			ball.v_y += 1;

			// make the ball bounce off walls...
			ball.bounce(ball.hitWall());

			// make the ball bounce off of the slimes
			// ball.bounce(ball.hitObj(slime1));
			// ball.bounce(ball.hitObj(slime2));

			ball.slimeBounce(ball.slimeAngle(slime1), slime1);
			ball.slimeBounce(ball.slimeAngle(slime2), slime2);

			if (ball.v_x > Circle.MAX_VEL_X)
				ball.v_x = Circle.MAX_VEL_X;
			else if (ball.v_y > Circle.MAX_VEL_Y)
				ball.v_y = Circle.MAX_VEL_Y;

			// check for the scoring conditions
			if (ball.intersects(goal1)) {
				pointScored = true;
				score2 += 1;
			} else if (ball.intersects(goal2)) {
				pointScored = true;
				score1 += 1;
			}

			// check for the game end conditions and update status text
			if (score1 >= MAX_GOALS) {
				status.setText("Player 1 wins!!!   Won in " + timeElapsed()
						+ " seconds");
				playing = false;
			} else if (score2 >= MAX_GOALS) {
				status.setText("Player 2 wins!!!   Won in " + timeElapsed()
						+ " seconds");
				playing = false;
			} else
				status.setText("Player 2: " + score2 + "  Player 1: " + score1
						+ "   Time: " + timeElapsed());

			// update the display
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ball.draw(g);
		slime1.draw(g);
		slime2.draw(g);
		goal1.draw(g);
		goal2.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

	private int timeElapsed() {
		return (int) Math.round(numTimesteps * INTERVAL * .001);
	}

}
