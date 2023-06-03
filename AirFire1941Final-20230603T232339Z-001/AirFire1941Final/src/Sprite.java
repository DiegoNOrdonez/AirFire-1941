//class: Sprite.java
//written by: s015721
//date: Dec 6, 2021
//description: makes jpg into image
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Sprite {
	private int width;
	private int height;
	private Image image;
	public Sprite(String src) {
		ImageIcon img = new ImageIcon(src);
		this.width = img.getImage().getWidth(null);
		this.height = img.getImage().getHeight(null);
		image = img.getImage();
	}
	//method name: getwidth
	//description: gets the width
	//parameters: none
	//return value: int width
	public int getWidth() {
		return width;
	}
	//method name: setwidth
	//description: sets the width
	//parameters: int width
	//return value: void/none
	public void setWidth(int width) {
		this.width = width;
	}
	//method name: getheight
	//description: gets the height
	//parameters: none
	//return value: int height
	public int getHeight() {
		return height;
	}
	//method name: setheight
	//description: sets the height
	//parameters: int height
	//return value: void/none
	public void setHeight(int height) {
		this.height = height;
	}
	//method name: getimage
	//description: gets the image
	//parameters: none
	//return value: BufferedImage image
	public Image getImage() {
		return image;
	}
	//method name: setimage
	//description: sets the image
	//parameters: BufferedImage image
	//return value: void/none
	public void setImage(Image image) {
		this.image = image;
	}
}
