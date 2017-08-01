package games.khoks.LR2;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
	int x;
	int y;
	int height;
	Color color = Color.GRAY;
	
	public Line(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}
	
	public Line(int x, int y, int height, Color color) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
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
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.drawLine(x, y, x, y + height - 1);
	}
}