package games.khoks.LR2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LRIndependent extends JFrame implements KeyListener, MouseListener{

	static LRIndependent jFrame = null;
	
	private Rectangle theBox = null;
	private boolean moveBox = false;
	private RunMoveBox runObj = null;
	private JPanel jPanel = null;
	private Graphics g = null;
	private JFrame meter = null;
	private final long renderTimeAddition = 500000; //render time change factor set to 0.5 millisecods
	ArrayList<Rectangle> arrBoxes = null;
	ArrayList<Line> arrGreyLines = null;
	
	public LRIndependent(){
		arrBoxes = new ArrayList<Rectangle>();
		theBox = new Rectangle(0,0,20,20);
		theBox.activate();
		arrBoxes.add(theBox);
		this.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	             System.exit(0);
	          }        
	       });
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 500, 500);
		this.setLocation(200, 200);
		this.setBackground(Color.WHITE);
		this.getContentPane().setBackground(Color.WHITE);
		this.setUndecorated(true);
		this.setVisible(true);
		this.getContentPane().setVisible(true);
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		meter = new JFrame();
		JLabel lblTimeMeter = new JLabel("Time:");
		JLabel lblPixelsMeter = new JLabel("Pace:");
		meter.getContentPane().setLayout(new GridLayout(2,1));
		meter.getContentPane().add(lblTimeMeter);
		meter.getContentPane().add(lblPixelsMeter);
		meter.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	             System.exit(0);
	          }        
	       });
		meter.setBounds(0, 0, 100, 40);
		meter.setLocation(700, 200);
		meter.setBackground(Color.WHITE);
		meter.getContentPane().setBackground(Color.WHITE);
		meter.setUndecorated(true);
		meter.setVisible(true);
		meter.getContentPane().setVisible(true);
		
	}
	public Rectangle getTheBox() {
		return this.theBox;
	}
	public void setTheBox(Rectangle theBox) {
		this.theBox = theBox;
	}
	public void setMoveBox(boolean moveBox) {
		this.moveBox = moveBox;
	}
	public boolean getMoveBox() {
		return this.moveBox;
	}
	public JPanel getJPanel() {
		return this.jPanel;
	}
	public Graphics getG() {
		return this.g;
	}
	public JFrame getMeter() {
		return this.meter;
	}
	public static void main(String[] args) {
		jFrame = new LRIndependent();
		
		
		jFrame.g = jFrame.getGraphics();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0 ; i < arrBoxes.size() ; i++) {
			arrBoxes.get(i).draw(g);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(this.getMoveBox() && e.getKeyCode() == this.runObj.keyEvent.getKeyCode())
				return;
			else if(this.getMoveBox() && e.getKeyCode() != this.runObj.keyEvent.getKeyCode()) {
				runObj.keyEvent = e;
				runObj.addKeyEvent(e);
			} else {
				runObj = new RunMoveBox(jFrame, e);
				
			}
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).randomizeColor();
					this.arrBoxes.get(i).draw(this.g);
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.addBox();
		} else if(e.getKeyCode() == KeyEvent.VK_ADD) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).increaseMovement();
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).decreaseMovement();
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).addRenderTime(renderTimeAddition);
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).reduceRenderTime(renderTimeAddition);
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_QUOTE) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).increaseMilliSecsPerMovement();
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).decreaseMilliSecsPerMovement();
					break;
				}
			}
		} else if(e.getKeyCode() == KeyEvent.VK_R) {
			this.randomGame();
		}
	}

	private void randomGame() {
		Random tmpRnd = new Random();
		
		boolean boxOverlap = false;
		int overLapCounter = 0;
		while(overLapCounter < 10) {
			boxOverlap = false;
			Rectangle tmpBox = new Rectangle(tmpRnd.nextInt(this.jFrame.getWidth()),tmpRnd.nextInt(this.jFrame.getHeight()),20,20);
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(tmpBox.overlapsBox(this.arrBoxes.get(i))) {
					boxOverlap = true;
					overLapCounter++;
				}
			}
			if(!boxOverlap && !tmpBox.doesItLieOutside(0, 0, this.jFrame.getWidth(), this.jFrame.getHeight())) {
				tmpBox.randomizeColor();
				tmpBox.draw(this.g);
				this.arrBoxes.add(tmpBox);
			}
		}
	}
	private void addBox() {
		this.theBox = new Rectangle(0,0,20,20);
		for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
			arrBoxes.get(i).deactivate();
		}
		this.theBox.activate();
		this.theBox.draw(this.g);
		this.arrBoxes.add(this.theBox);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(null != runObj) {
			runObj.removeKeyEvent(e);
			if(runObj.keyEventArr.size() < 1)
				this.setMoveBox(false);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle tmpBox = null;
		for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
			if(mouseInBox(arrBoxes.get(i), e.getX(), e.getY())) {
				tmpBox = arrBoxes.get(i); 
				break;
			}
		}
		if(null != tmpBox) {
			if(null != this.runObj)
				this.runObj.skipToWait = true;
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				arrBoxes.get(i).deactivate();
			}
			tmpBox.activate();
			this.theBox = tmpBox;
			if(null != this.runObj)
				this.runObj.skipToWait = false;
		}
	}
	private boolean mouseInBox(Rectangle tmpBox, int x, int y) {
		if(x >= tmpBox.getX() &&
				y >= tmpBox.getY() &&
				x < (tmpBox.getX() + tmpBox.getWidth()) &&
				y< (tmpBox.getY() + tmpBox.getHeight())) {
			return true;
		}
		return false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}


enum RectangleTypeEnum {
    STUBBORN ("STUBBORN");
	
	private final String name;
	
	RectangleTypeEnum(String name) {
		this.name= name;
	}
}