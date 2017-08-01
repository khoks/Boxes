package games.khoks.LR2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Rectangle {
	
	int x;
	int y;
	int width;
	int height;
	int MOVEMENT = 1;
	long renderTime = 5000000; // default wait time in nanoseconds between subsequent renders of box
	int milliSecsPerMovement = 5;
	Color color = Color.BLUE;
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
	public int getMilliSecsPerMovement() {
		return milliSecsPerMovement;
	}

	public void setMilliSecsPerMovement(int milliSecsPerMovement) {
		this.milliSecsPerMovement = milliSecsPerMovement;
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
	public long getRenderTime() {
		return this.renderTime;
	}
	public int getMovement() {
		return this.MOVEMENT;
	}
	public void addRenderTime(long addition) {
		this.renderTime += addition;
	}
	public void reduceRenderTime(long addition) {
		this.renderTime -= addition;
	}
	public void increaseMovement() {
		MOVEMENT = MOVEMENT + 1;
	}
	public void decreaseMovement() {
		if(MOVEMENT > 2)
			MOVEMENT = MOVEMENT - 1;
	}
	public void increaseMilliSecsPerMovement() {
		milliSecsPerMovement++;
	}
	public void decreaseMilliSecsPerMovement() {
		if(milliSecsPerMovement > 1)
			milliSecsPerMovement--;
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
	public void move(Graphics g, int keyCode, LRIndependent jFrame, int collidingMovement) {
		int tmpMovement = MOVEMENT;
		MOVEMENT = collidingMovement;
		move(g, keyCode, jFrame);
		MOVEMENT = tmpMovement;
	}
	public void move(Graphics g, int keyCode, LRIndependent jFrame) {
		int tmpMovement = MOVEMENT;
		MOVEMENT = doNotCross(jFrame, keyCode);
		if(MOVEMENT > 0) {
			if(keyCode == KeyEvent.VK_LEFT) {
				//jFrame.gr 
				//g.clearRect(this.getX() + this.getWidth() - MOVEMENT, this.getY(), MOVEMENT, this.getHeight());
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
	
	private int doNotCross(LRIndependent jFrame, int keyCode) {
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
	
	public boolean doesItLieOutside(int x, int y, int width, int height) {
		
		if(this.getX() < x || ((this.getX()+this.getWidth() - 1) > (x + width - 1)) ||
				this.getY() < y || ((this.getY()+this.getHeight() - 1) > (y + height - 1))) {
			return true;
		}
		return false;
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

	public boolean overlapsBox(Rectangle rectangle) {
		if((this.getX() <= (rectangle.getX() + rectangle.getWidth() - 1) && this.getX() >= rectangle.getX()) ||
					((this.getX() + this.getWidth() - 1) <= (rectangle.getX() + rectangle.getWidth() - 1) && (this.getX() + this.getWidth() - 1) >= rectangle.getX())) {
			if((this.getY() <= (rectangle.getY() + rectangle.getHeight() - 1) && this.getY() >= rectangle.getY()) ||
					((this.getY() + this.getHeight() - 1) <= (rectangle.getY() + rectangle.getHeight() - 1) && (this.getY() + this.getHeight() - 1) >= rectangle.getY())) {
				return true;
			}
		}
		return false;
	}
}