package games.khoks.LR2;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

public class RunMoveBox implements Runnable {
	LRIndependent jFrame = null;
	KeyEvent keyEvent = null;
	ArrayList<KeyEvent> keyEventArr = new ArrayList<KeyEvent>();
	boolean skipToWait = false;
	
	public RunMoveBox(LRIndependent jFrame, KeyEvent keyEvent) {
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
			long renderTime = 0;
			int milliSecsPerMovement = 1;
			float howManyMilliSecs = 0;
			float howManyMovements = 1;
			float leftOverMovement = 0;
			while(this.jFrame.getMoveBox()) {
				while(this.jFrame.getMoveBox() && howManyMovements > 0f) {
					if(!skipToWait) {
						for(int i = 0 ; i < keyEventArr.size() ; i++) {
							if(i <= (keyEventArr.size() - 1)) {
								collidingdistance = 0;
								keyCode = keyEventArr.get(i).getKeyCode();
								for(int j = 0 ; j < this.jFrame.arrBoxes.size() ; j++) {
									if(this.jFrame.arrBoxes.get(j).isActive()) {
										renderTime = this.jFrame.arrBoxes.get(j).getRenderTime();
										milliSecsPerMovement = this.jFrame.arrBoxes.get(j).getMilliSecsPerMovement();
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
					howManyMovements--;
				}
				howManyMilliSecs = testWait2(renderTime); // keeping wait time between subsequent movements as 5 milli secs, default renderTime of box
				howManyMovements = (howManyMilliSecs / milliSecsPerMovement) + leftOverMovement; //keeping the movements, i.e., renders to 1 per 5 milli secs, default for system
				leftOverMovement = (float) (howManyMovements - Math.floor(howManyMovements));
				howManyMovements = (float) (Math.floor(howManyMovements));
				/*((JLabel)this.jFrame.getMeter().getContentPane().getComponent(0)).setText("Time:" + totalTimeMoved);*/
				((JLabel)this.jFrame.getMeter().getContentPane().getComponent(1)).setText("Pace:" + howManyMovements);
			}
			/*((JLabel)this.jFrame.getMeter().getContentPane().getComponent(0)).setText("Time:" + totalTimeMoved);
			((JLabel)this.jFrame.getMeter().getContentPane().getComponent(1)).setText("Pace:" + totalPixelsMoved);*/
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
	
	public float testWait2(long interval){
		
	    final long INTERVAL = interval; // AKA, inversely proportional to the frequency with which we want to update the view
	    long start = System.nanoTime();
	    long end = 0;
	    do{
	        end = System.nanoTime();
	    }while(start + INTERVAL >= end);
	    return (float) ((end - start) / 1000000l);
	}
}