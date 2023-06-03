import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

public class Animation {
	private int i = 0;
	private int len = 17;
	private String type;
	private String imgFolder;
	private int x;
	private int y;
	
	//constructor
	public Animation(String imgFolder, String type) {
		this.imgFolder = imgFolder;
		//this.len = new File(imgFolder).list().length;
		this.type = type;
	}
	public Animation(String imgFolder, String type, int x, int y) {
		this.imgFolder = imgFolder;
		//this.len = new File(imgFolder).list().length;
		this.x=x;
		this.y=y;
		this.type = type;
	}
	public void updateAnim() {
		i++;
	}
	public void drawAnim(Graphics2D g2d, int x, int y, int w, int h) {
		g2d.drawImage(new Sprite(imgFolder+"/"+i+"."+type).getImage(), x, y, w, h, null);
	}
	//method name: geti
	//description: gets the i
	//parameters: none
	//return value: int i
	public int getI() {
		return i;
	}
	//method name: seti
	//description: sets the i
	//parameters: int i
	//return value: void/none
	public void setI(int i) {
		this.i = i;
	}
	//method name: getlen
	//description: gets the len
	//parameters: none
	//return value: int len
	public int getLen() {
		return len;
	}
	//method name: setlen
	//description: sets the len
	//parameters: int len
	//return value: void/none
	public void setLen(int len) {
		this.len = len;
	}
	//method name: gettype
	//description: gets the type
	//parameters: none
	//return value: String type
	public String getType() {
		return type;
	}
	//method name: settype
	//description: sets the type
	//parameters: String type
	//return value: void/none
	public void setType(String type) {
		this.type = type;
	}
	//method name: getimgFolder
	//description: gets the imgFolder
	//parameters: none
	//return value: String imgFolder
	public String getImgFolder() {
		return imgFolder;
	}
	//method name: setimgFolder
	//description: sets the imgFolder
	//parameters: String imgFolder
	//return value: void/none
	public void setImgFolder(String imgFolder) {
		this.imgFolder = imgFolder;
	}
	//method name: getx
	//description: gets the x
	//parameters: none
	//return value: int x
	public int getX() {
		return x;
	}
	//method name: setx
	//description: sets the x
	//parameters: int x
	//return value: void/none
	public void setX(int x) {
		this.x = x;
	}
	//method name: gety
	//description: gets the y
	//parameters: none
	//return value: int y
	public int getY() {
		return y;
	}
	//method name: sety
	//description: sets the y
	//parameters: int y
	//return value: void/none
	public void setY(int y) {
		this.y = y;
	}
	
}
