package games.khoks;

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

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LR extends JFrame implements KeyListener, MouseListener{

	static LR jFrame = null;
	
	private Rectangle theBox = null;
	private boolean moveBox = false;
	private RunMoveBox runObj = null;
	private JPanel jPanel = null;
	private Graphics g = null;
	private JFrame meter = null;
	private final long speedAddition = 1000000;
	ArrayList<Rectangle> arrBoxes = null;
	
	public LR(){
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
		this.setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
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
		jFrame = new LR();
		
		
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
		} else if(e.getKeyCode() == 46) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).addSpeed(speedAddition);
					break;
				}
			}
		} else if(e.getKeyCode() == 44) {
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				if(this.arrBoxes.get(i).isActive()) {
					this.arrBoxes.get(i).reduceSpeed(speedAddition);
					break;
				}
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
		runObj.removeKeyEvent(e);
		if(runObj.keyEventArr.size() < 1)
			this.setMoveBox(false);
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
			this.runObj.skipToWait = true;
			for(int i = 0 ; i < this.arrBoxes.size() ; i++) {
				arrBoxes.get(i).deactivate();
			}
			tmpBox.activate();
			this.theBox = tmpBox;
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

class RunMoveBox implements Runnable {
	LR jFrame = null;
	KeyEvent keyEvent = null;
	ArrayList<KeyEvent> keyEventArr = new ArrayList<KeyEvent>();
	boolean skipToWait = false;
	
	public RunMoveBox(LR jFrame, KeyEvent keyEvent) {
		this.jFrame = jFrame;
		this.keyEvent = keyEvent;
		this.keyEventArr = new ArrayList<KeyEvent>();
		this.keyEventArr.add(keyEvent);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		goMoveBox();
	}
	
	private void goMoveBox() {
		Graphics g = this.jFrame.getG();
		int keyCode = 0;
		int collidingdistance = 0;
		if(!this.jFrame.getMoveBox()) {
			this.jFrame.setMoveBox(true);
			long totalTimeMoved = 0;
			int totalPixelsMoved = 0;
			long speed = 0;
			while(this.jFrame.getMoveBox()) {
				if(!skipToWait) {
					for(int i = 0 ; i < keyEventArr.size() ; i++) {
						if(i <= (keyEventArr.size() - 1)) {
							collidingdistance = 0;
							keyCode = keyEventArr.get(i).getKeyCode();
							for(int j = 0 ; j < this.jFrame.arrBoxes.size() ; j++) {
								if(this.jFrame.arrBoxes.get(j).isActive()) {
									speed = this.jFrame.arrBoxes.get(j).getSpeed();
									collidingdistance = objectCollision(this.jFrame.arrBoxes.get(j), keyCode, this.jFrame.arrBoxes);
									if(this.jFrame.arrBoxes.get(j).MOVEMENT == collidingdistance) {
										this.jFrame.arrBoxes.get(j).move(g, keyCode, jFrame);
										break;
									} else if(collidingdistance > 0) {
										this.jFrame.arrBoxes.get(j).move(g, keyCode, jFrame, collidingdistance);
										break;
									} else {
										break;
									}
								}
							}
						}
					}
				}
				totalTimeMoved += testWait(speed);
				totalPixelsMoved++;
			}
			((JLabel)this.jFrame.getMeter().getContentPane().getComponent(0)).setText("Time:" + totalTimeMoved);
			((JLabel)this.jFrame.getMeter().getContentPane().getComponent(1)).setText("Pace:" + totalPixelsMoved);
		}
	}
	
	private int objectCollision(Rectangle movingBox, int keyCode, ArrayList<Rectangle> arrBoxes) {
		int widthIcrement = 0;
		int heightIncrement = 0;
		int xDecrement = 0;
		int yDecrement = 0;
		if(keyCode == KeyEvent.VK_LEFT) {
			xDecrement = (-1)*(movingBox.MOVEMENT);
		} else if(keyCode == KeyEvent.VK_RIGHT) {
			widthIcrement = movingBox.MOVEMENT;
		} else if(keyCode == KeyEvent.VK_UP) {
			yDecrement = (-1)*(movingBox.MOVEMENT);
		} else if(keyCode == KeyEvent.VK_DOWN) {
			heightIncrement = movingBox.MOVEMENT;
		}
		int lowestMovement = movingBox.MOVEMENT;
		for(int i = 0 ; i < arrBoxes.size() ; i++) {
			if(movingBox == arrBoxes.get(i)) {
				continue;
			}
			if((movingBox.getX() + movingBox.getWidth() -1 + xDecrement + widthIcrement) < arrBoxes.get(i).getX()) {
				continue;
			} else if((movingBox.getX() + xDecrement + widthIcrement) > (arrBoxes.get(i).getX() + arrBoxes.get(i).getWidth() - 1)) {
				continue;
			} else if((movingBox.getY() + movingBox.getHeight() - 1 + yDecrement + heightIncrement) < arrBoxes.get(i).getY()) {
				continue;
			} else if((movingBox.getY() + yDecrement + heightIncrement) > (arrBoxes.get(i).getY() + arrBoxes.get(i).getHeight() - 1)) {
				continue;
			} else {
				int collidingMovement = movingBox.doNotCollide(arrBoxes.get(i), keyCode);
				lowestMovement = lowestMovement > collidingMovement ? collidingMovement : lowestMovement;
			}
		}
		return lowestMovement;
	}

	public void addKeyEvent(KeyEvent keyEvent) {
		for(int i = 0 ; i < keyEventArr.size() ; i++) {
			if(keyEventArr.get(i).getKeyCode() == keyEvent.getKeyCode())
				return;
		}
		keyEventArr.add(keyEvent);
	}
	public void removeKeyEvent(KeyEvent keyEvent) {
		for(int i = 0 ; i < keyEventArr.size() ; i++) {
			if(keyEventArr.get(i).getKeyCode() == keyEvent.getKeyCode()) {
				keyEventArr.remove(i);
				if(keyEventArr.size() > 0) {
					this.keyEvent = keyEventArr.get(keyEventArr.size() - 1);
				}
				return;
			}
		}
	}
	
	public long testWait(long speed){
		
	    final long INTERVAL = 18000000 - speed;
	    long start = System.nanoTime();
	    long end=0;
	   /* do{
	        end = System.nanoTime();
	    }while(start + INTERVAL >= end);*/
	    end = start;
	    do{
	    	end++;
	    }while(end < start + INTERVAL);
	    return (end - start);
	}
}
enum RectangleTypeEnum {
    STUBBORN ("STUBBORN");
	
	private final String name;
	
	RectangleTypeEnum(String name) {
		this.name= name;
	}
}
class Rectangle {
	
	int x;
	int y;
	int width;
	int height;
	int MOVEMENT = 1;
	long speed = 1000000;
	Color color = Color.ORANGE;
	RectangleTypeEnum type = RectangleTypeEnum.STUBBORN;
	boolean active = false;
	RunMoveBox runObj = null;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public Rectangle() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	public void addKeyEvent() {
		switch(type) {
		
			case STUBBORN:
				
				
			default:break;
		
		}
	}
	public void removeKeyEvent() {
		switch(type) {
		
			case STUBBORN:
			
			
			default:break;
	
		}
	}
	public RunMoveBox getRunObj() {
		return this.runObj;
	}
	public void setRunObj(RunMoveBox runObj) {
		this.runObj = runObj;
	}
	public void activate() {
		this.active = true;
	}
	public void deactivate() {
		this.active = false;
	}
	public boolean isActive() {
		return this.active;
	}
	public void setMovement(int movement) {
		this.MOVEMENT = movement;
	}
	public long getSpeed() {
		return this.speed;
	}
	public int getMovement() {
		return this.MOVEMENT;
	}
	public void addSpeed(long addition) {
		this.speed += addition;
	}
	public void reduceSpeed(long addition) {
		this.speed -= addition;
	}
	public void increaseMovement() {
		MOVEMENT = MOVEMENT + 1;
	}
	public void decreaseMovement() {
		if(MOVEMENT > 1)
			MOVEMENT = MOVEMENT - 1;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void move(Graphics g, int keyCode, LR jFrame, int collidingMovement) {
		int tmpMovement = MOVEMENT;
		MOVEMENT = collidingMovement;
		move(g, keyCode, jFrame);
		MOVEMENT = tmpMovement;
	}
	public void move(Graphics g, int keyCode, LR jFrame) {
		int tmpMovement = MOVEMENT;
		MOVEMENT = doNotCross(jFrame, keyCode);
		if(MOVEMENT > 0) {
			if(keyCode == KeyEvent.VK_LEFT) {
				g.clearRect(this.getX() + this.getWidth() - MOVEMENT, this.getY(), MOVEMENT, this.getHeight());
				g.setColor(this.color);
				//g.drawLine(this.getX() - MOVEMENT, this.getY(), this.getX() - MOVEMENT, this.getY() + this.getHeight() - 1);
				g.fillRect(this.getX() - MOVEMENT, this.getY(), this.getWidth(), this.getHeight());
				this.setX(this.getX() - MOVEMENT);
			}
			if(keyCode == KeyEvent.VK_RIGHT) {
				g.clearRect(this.getX(), this.getY(), MOVEMENT, this.getHeight());
				g.setColor(this.color);
				//g.drawLine(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight() - 1);
				g.fillRect(this.getX() + MOVEMENT, this.getY(), this.getWidth(), this.getHeight());
				this.setX(this.getX() + MOVEMENT);
			}
			if(keyCode == KeyEvent.VK_UP) {
				g.clearRect(this.getX(), this.getY() + this.getHeight() - MOVEMENT, this.getWidth(), MOVEMENT);
				g.setColor(this.color);
				//g.drawLine(this.getX(), this.getY() - MOVEMENT, this.getX() + this.getWidth() - 1, this.getY() - MOVEMENT);
				g.fillRect(this.getX(), this.getY() - MOVEMENT, this.getWidth(), this.getHeight());
				this.setY(this.getY() - MOVEMENT);
			}
			if(keyCode == KeyEvent.VK_DOWN) {
				g.clearRect(this.getX(), this.getY(), this.getWidth(), MOVEMENT);
				g.setColor(this.color);
				//g.drawLine(this.getX(), this.getY() + this.getHeight(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight());
				g.fillRect(this.getX(), this.getY() + MOVEMENT, this.getWidth(), this.getHeight());
				this.setY(this.getY() + MOVEMENT);
			}
		}
		MOVEMENT = tmpMovement;
	}
	
	public void randomizeColor() {
		this.color = Color.getHSBColor(new Double(Math.random()).floatValue(), new Double(Math.random()).floatValue(), new Double(Math.random()).floatValue());
	}
	
	private int doNotCross(LR jFrame, int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT) {
			if(this.getX() - MOVEMENT < 0)
				return this.getX();
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			if((this.getX()+this.getWidth() - 1 + MOVEMENT) > jFrame.getWidth() - 1)
				return (jFrame.getWidth() - 1) - (this.getX()+this.getWidth() - 1);
		}
		else if(keyCode == KeyEvent.VK_UP) {
			if(this.getY() - MOVEMENT < 0)
				return this.getY();
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			if((this.getY()+this.getHeight() + MOVEMENT - 1) > jFrame.getHeight() - 1)
				return (jFrame.getHeight() - 1) - (this.getY()+this.getHeight() - 1);
		}
		return MOVEMENT;
	}
	
	public int doNotCollide(Rectangle collidingBox, int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT) {
			return (this.getX() - 1) - (collidingBox.getX() + collidingBox.getWidth() - 1);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			return (collidingBox.getX() - 1) - (this.getX()+this.getWidth() - 1);
		}
		else if(keyCode == KeyEvent.VK_UP) {
			return (this.getY() - 1) - (collidingBox.getY() + collidingBox.getHeight() - 1);
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			return (collidingBox.getY() - 1) - (this.getY()+this.getHeight() - 1);
		}
		return MOVEMENT;
	}
}