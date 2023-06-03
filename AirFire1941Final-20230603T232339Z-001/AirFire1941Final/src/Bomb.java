import java.awt.Color;
import java.awt.Graphics2D;

//class: Bomb.java
//written by: s015721
//date: Jan 5, 2022
//description: bombs
public class Bomb {
	private int deathT=1;
	private boolean isLive = true;
	private int angle;
	private int elevation;
	private double speed;
	private double vy = 1;
	static int cooldown = 4;
	
	//constructor
	public Bomb(int angle, int elevation, double speed) {
		this.angle = angle+20;
		this.elevation = elevation+20;
		this.speed = speed;
	}

	public void advance(Graphics2D g2d) {
		//g2d.drawImage();
		if (elevation>1200) {
			isLive=false;
		} else {
			g2d.setColor(new Color(0,0,0));
			vy+=1;
			elevation+=vy;
			angle+=speed;
			g2d.fillOval(angle, elevation, 20, 20);
			g2d.fillOval(angle+3600, elevation, 20, 20);
			g2d.fillOval(angle-3600, elevation, 20, 20);
		}
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

	//method name: getspeed
	//description: gets the speed
	//parameters: none
	//return value: double speed
	public double getSpeed() {
		return speed;
	}

	//method name: setspeed
	//description: sets the speed
	//parameters: double speed
	//return value: void/none
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	//method name: getvy
	//description: gets the vy
	//parameters: none
	//return value: double vy
	public double getVy() {
		return vy;
	}

	//method name: setvy
	//description: sets the vy
	//parameters: double vy
	//return value: void/none
	public void setVy(double vy) {
		this.vy = vy;
	}

	//method name: getcooldown
	//description: gets the cooldown
	//parameters: none
	//return value: int cooldown
	public static int getCooldown() {
		return cooldown;
	}

	//method name: setcooldown
	//description: sets the cooldown
	//parameters: int cooldown
	//return value: void/none
	public static void setCooldown(int cooldown) {
		Bomb.cooldown = cooldown;
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
}
