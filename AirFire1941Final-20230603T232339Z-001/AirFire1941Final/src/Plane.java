//class: Plane.java
//written by: s015721
//date: Jan 5, 2022
//description: planes
public class Plane {
	private Sprite sprite;
	private int angle;
	private int elevation;
	private int speed;
	private boolean isLive = true;
	private int deathT = 0;
	private int health = 2;
	//constructor
	public Plane(Sprite sprite, int angle, int elevation, int speed) {
		this.sprite = sprite;
		this.angle = angle;
		this.elevation = elevation;
		this.speed = speed;
	}
	public Plane(Sprite sprite, int angle, int elevation) {
		this.sprite = sprite;
		this.angle = angle;
		this.elevation = elevation;
	}
	
	//method name: getsprite
	//description: gets the sprite
	//parameters: none
	//return value: Sprite sprite
	public Sprite getSprite() {
		return sprite;
	}

	//method name: setsprite
	//description: sets the sprite
	//parameters: Sprite sprite
	//return value: void/none
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	//method name: getangle
	//description: gets the angle
	//parameters: none
	//return value: double angle
	public int getAngle() {
		return angle;
	}
	//method name: setangle
	//description: sets the angle
	//parameters: double angle
	//return value: void/none
	public void setAngle(int angle) {
		this.angle = angle;
	}
	//method name: getelevation
	//description: gets the elevation
	//parameters: none
	//return value: double elevation
	public int getElevation() {
		return elevation;
	}
	//method name: setelevation
	//description: sets the elevation
	//parameters: double elevation
	//return value: void/none
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	//method name: getdistance
	//description: gets the distance
	//parameters: none
	//return value: double distance
	public int getSpeed() {
		return speed;
	}
	//method name: setdistance
	//description: sets the distance
	//parameters: double distance
	//return value: void/none
	public void setSpeed(int speed) {
		this.speed = speed;
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
	//method name: gethealth
	//description: gets the health
	//parameters: none
	//return value: int health
	public int getHealth() {
		return health;
	}
	//method name: sethealth
	//description: sets the health
	//parameters: int health
	//return value: void/none
	public void setHealth(int health) {
		this.health = health;
	}
	@Override
	public String toString() {
		return "Plane [angle=" + angle + ", elevation=" + elevation + ", speed=" + speed + ", health=" + health + "]";
	}
}
