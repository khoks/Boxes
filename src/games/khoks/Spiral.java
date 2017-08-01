package games.khoks;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Spiral {

	public static void main(String[] args) throws InterruptedException {
		
		JFrame frmMain = new JFrame("Spiral");
		frmMain.setLayout(null);
		frmMain.setBounds(100, 100, 410, 430);
		
		Container cntFrmMain = frmMain.getContentPane();
		cntFrmMain.setLayout(null);
		
		GPanel pnlMain = new GPanel();
		pnlMain.setBackground(Color.WHITE);
		
		cntFrmMain.add(pnlMain);
		
		frmMain.setVisible(true);
		
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setBackground(Color.WHITE);
		
		
		spiral(pnlMain, frmMain);
	}
	
	private static void spiral(GPanel pnlMain, JFrame frmMain) throws InterruptedException {

		float x = 0, y = 0, w = 400, h = 400;
		int sleep = 10;
		int sleep_min = 1;
		int sleep_max = 2;
		float ratio = 1;
		float hue = 0, sat = 0, base = 0;
		boolean decrease = true;
		boolean shrink = true;
		while(true){
			
			for(int i = 0 ; i < 360 ; i++){
				//Thread.sleep(0,10);
				testWait();
				pnlMain.getGraphics().drawArc(new Float(x).intValue(),new Float(y).intValue(),new Float(w).intValue(),new Float(h).intValue(),i,120);
				if(i%6 == 0) {
					if(shrink) {
						x = x + 1;
						y = y + 1;
						w = w - 2;
						h = h - 2;
						frmMain.setForeground(Color.getHSBColor(hue, sat, base));
					} else {
						x = x - 1;
						y = y - 1;
						w = w + 2;
						h = h + 2;
						frmMain.setForeground(Color.getHSBColor(hue, sat, base));
					}
					
					if(sleep == sleep_min)
						decrease = false;
					if(sleep == sleep_max)
						decrease = true;
					if(decrease)
						sleep = sleep - 1;
					else
						sleep = sleep + 1;
					
				}
				if( (w < 2 && h < 2) || (x > 199 && y > 199) ){
					shrink = false;
					hue = new Double(Math.random()).floatValue();
					sat = new Double(Math.random()).floatValue();
					base = new Double(Math.random()).floatValue();
					//pnlMain.getGraphics().clearRect(0, 0, 410, 410);
				}
				if( (w > 399 && h > 399) || (x < 1 && y < 1) ){
					shrink = true;
					hue = new Double(Math.random()).floatValue();
					sat = new Double(Math.random()).floatValue();
					base = new Double(Math.random()).floatValue();
					//pnlMain.getGraphics().clearRect(0, 0, 410, 410);
				}
			}
			
			
		}
	}
	public static void testWait(){
	    final long INTERVAL = 600000;
	    long start = System.nanoTime();
	    long end=0;
	    do{
	        end = System.nanoTime();
	    }while(start + INTERVAL >= end);
	    System.out.println(end - start);
	}

}
class GPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GPanel() {
    	setBounds(0, 0, 401, 401);
    	setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
