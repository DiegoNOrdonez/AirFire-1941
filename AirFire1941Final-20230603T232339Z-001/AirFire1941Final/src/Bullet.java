//class: Bullet.java
//written by: s015721
//date: Jan 5, 2022
//description: bullets
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
	private int t = 1;
	private int deathT=1;
	private boolean isLive = true;
	private int angle;
	private int elevation;
	private double vx;
	private double vy;
	private int size;
	static int cooldown = 4;
	
	//constructor
	public Bullet(int angle, int elevation, double vx, double vy) {
		this.angle = angle;
		this.elevation = elevation-10;
		this.vx = -vx;
		this.vy = -vy;
	}

	public void advance(Graphics2D g2d) {
		//g2d.drawImage();
		if (t==10) {
			isLive=false;
		} else {
			g2d.setColor(new Color(0,0,0));
			angle+=vx/2-Cam.vx/2;
			elevation+=vy/2-Cam.vy/2;
			size=30/(2*t)+2;
			g2d.fillOval(angle, elevation, size, size);
			g2d.fillOval(angle+3600, elevation, size, size);
			g2d.fillOval(angle-3600, elevation, size, size);
			t++;
		}
	}

	public static void cool() {
		if (cooldown>0) {
			cooldown--;
		}
	}
	public static void heat() {
		cooldown=8;
	}
	public static boolean check() {
		if (cooldown>0) {
			return false;
		}
		return true;
	}
	//method name: getisLive
	//description: gets the isLive
	//parameters: none
	//return value: boolean isLive
	public boolean isLive() {
		return isLive;
	}

	//method name: setisLive
	//description: sets the isLive
	//parameters: boolean isLive
	//return value: void/none
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	//method name: getangle
	//description: gets the angle
	//parameters: none
	//return value: int angle
	public int getAngle() {
		return angle;
	}

	//method name: setangle
	//description: sets the angle
	//parameters: int angle
	//return value: void/none
	public void setAngle(int angle) {
		this.angle = angle;
	}

	//method name: getelevation
	//description: gets the elevation
	//parameters: none
	//return value: int elevation
	public int getElevation() {
		return elevation;
	}

	//method name: setelevation
	//description: sets the elevation
	//parameters: int elevation
	//return value: void/none
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	//method name: getvx
	//description: gets the vx
	//parameters: none
	//return value: int vx
	public double getVx() {
		return vx;
	}

	//method name: setvx
	//description: sets the vx
	//parameters: int vx
	//return value: void/none
	public void setVx(double vx) {
		this.vx = vx;
	}

	//method name: getvy
	//description: gets the vy
	//parameters: none
	//return value: int vy
	public double getVy() {
		return vy;
	}

	//method name: setvy
	//description: sets the vy
	//parameters: int vy
	//return value: void/none
	public void setVy(double vy) {
		this.vy = vy;
	}

	//method name: getsize
	//description: gets the size
	//parameters: none
	//return value: int size
	public int getSize() {
		return size;
	}

	//method name: setsize
	//description: sets the size
	//parameters: int size
	//return value: void/none
	public void setSize(int size) {
		this.size = size;
	}

	//method name: getdeathT
	//description: gets the deathT
	//parameters: none
	//return value: int deathT
	public int getDeathT() {
		return deathT;
	}

	//method name: setdeathT
	//description: sets the deathT
	//parameters: int deathT
	//return value: void/none
	public void setDeathT(int deathT) {
		this.deathT = deathT;
	}
	
}
