package AirFireCockpit;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class HealthBox {

	private int length;
	private int width;
	private int yValue;
		
	public HealthBox() {
		this(20, 20, 20);
	}
	
	public HealthBox(int length, int width, int yValue) {
		this.length = length;
		this.width = width;
		this.yValue = yValue;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getyValue() {
		return yValue;
	}

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}
	
}
