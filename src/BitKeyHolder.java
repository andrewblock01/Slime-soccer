import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;


public class BitKeyHolder implements KeyListener {

	private BitSet keyBits = new BitSet(256);

	@Override
	public void keyTyped(KeyEvent e) {
		//not needed
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBits.set(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBits.clear(keyCode);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keyBits.get(keyCode);
	}

}
