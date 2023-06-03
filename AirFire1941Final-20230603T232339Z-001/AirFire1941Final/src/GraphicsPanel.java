//class: GraphicsPanel.java
//written by: s015721
//date: Jan 5, 2022
//description: draws graphics to screen
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	private Timer t;
	private KeySystem key;
	private ArrayList<Plane> planes;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private ArrayList<Animation> hitAnimations;
	private ArrayList<Animation> bombAnimations;
	private boolean[] radar;
	int health;
	int hitPlane;
	int hitBomb;
	int angle;
	int wave;
	int kills;
	int waveCool;
	int targetHealth;
	int offsetHealth;
	String gameState;
	private Rectangle r;
	double fuel;
	int ammo;
	int turn;
	int Yaw;
	
	public void init() {
		Sound.reset();
		Sound.playSound("Sources/sounds/bounce.wav", true);
		key = new KeySystem();
		planes = new ArrayList<Plane>();
		bullets = new ArrayList<Bullet>();
		bombs = new ArrayList<Bomb>();
		hitAnimations = new ArrayList<Animation>();
		bombAnimations = new ArrayList<Animation>();
		radar = new boolean[360];
		health = 100;
		hitPlane = -1;
		hitBomb = -1;
		angle = 0;
		wave = 0;
		kills = 0;
		waveCool = 150;
		targetHealth = 0;
		offsetHealth = 0;
		gameState = "startscreen";
		r = new Rectangle(0 , 550, 1200, 150);
		fuel=1000;
		ammo=30;
		turn = 0;
		Yaw = 0;
		
		for (int i=0;i<8;i++) {
        	planes.add(new Plane(new Sprite("Sources/images/final/plane  - Copy.png"), (int)(Math.random()*3590),(int)(Math.random()*700)+300,(int)(Math.random()*6)+4));
        }
	}
	
	public GraphicsPanel() {
		setPreferredSize(new Dimension(1200, 800));
        this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		t = new Timer(23, new ClockListener(this));
		init();
		t.start();
	}
	
	public void clock() {
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		//angle++;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(new Sprite("Sources/images/startscreen.jpg").getImage(), 0, 0, 1200, 800, null);

		if (gameState=="gameplay") {
			Cam.lvx=Cam.vx;
			Cam.lvy=Cam.vy;
			fuel-=0.1;
			Cam.vx-=(key.left+key.right);
			Cam.vy+=(key.up+key.down);
			Cam.vx*=0.99;
			Cam.vy*=0.99;
			Cam.x+=Cam.vx;
			Cam.y+=Cam.vy;
			
			if (Cam.x<0) {
				Cam.x+=3600;
			} else if (Cam.x>3600) {
				Cam.x-=3600;
			}
			if (Math.abs(Cam.vx)>8) {
				Cam.vx=(int) (8*Math.signum(Cam.vx));
			}
			if (Math.abs(Cam.vy)>6) {
				Cam.vy=(int) (6*Math.signum(Cam.vy));
			}
			if (Cam.y>0) {
				Cam.y=0;
				Cam.vy=0;
			}
			if (Cam.y<-1200) {
				Cam.y=-1200;
				Cam.vy=0;
			}
			Bullet.cool();
			if (Bullet.check() && key.isSpace() && ammo>0) {
				Bullet.heat();
				bullets.add(new Bullet(-Cam.x+600,-Cam.y+400,Cam.vx,Cam.vy));
				ammo--;
				Sound.playSound("Sources/sounds/Bewz.wav", false);
			}
			g2d.setColor(new Color(215,230,250));
			g2d.fillRect(0, 0, 1200, 800);
			AffineTransform oldrolltrans = new AffineTransform();
			AffineTransform rolltrans = new AffineTransform();
	
			rolltrans.setToIdentity();
		    rolltrans.scale(2, 2);
		    oldrolltrans.scale(2, 2);
			rolltrans.rotate(Math.toRadians((Cam.vx*Math.abs(Cam.lvx))/15),600,400);
			g2d.setTransform(rolltrans);
			g2d.setColor(new Color(0,255,255));
			g2d.fillRect(0, 0, 1200, 800);
			g2d.translate(Cam.x, Cam.y);
			g2d.drawImage(new Sprite("Sources/images/final/island finished maybe.png").getImage(), 0,0-84, 3600, 2000, this);
			g2d.drawImage(new Sprite("Sources/images/final/island finished maybe.png").getImage(), 3600,0-84, 3600, 2000, this);
			g2d.drawImage(new Sprite("Sources/images/final/island finished maybe.png").getImage(), -3600,0-84, 3600, 2000, this);
			
			for (int b=0; b<bombs.size(); b++) {
				bombs.get(b).advance(g2d);
				if (bombs.get(b).isLive()==false) {
					if (!Task.done("Bomb_Explodes"+b)) {
						hitBomb = b;
						health-=4;
						Sound.playSoundOnce("Sources/sounds/bomb.wav","Bomb_Explodes"+b);
						bombAnimations.add(new Animation("Sources/images/explosion", "png", bombs.get(b).getAngle(), bombs.get(b).getElevation()));
					}
					Task.add("Bomb_Explodes"+b);
					bombs.get(b).setDeathT(bombs.get(b).getDeathT()+1);
					if (bombs.get(b).getDeathT()==16) {
						bombs.remove(b);
						if (hitBomb!=-1) {
							bombAnimations.remove(0);
							hitBomb = -1;
						}
						Task.prime("Bomb_Explodes"+b);
						Sound.prime("Bomb_Explodes"+b);
					}
				}
			}
			for (int j=0; j<bombAnimations.size(); j++) {
				bombAnimations.get(j).drawAnim(g2d, bombAnimations.get(j).getX()-350, bombAnimations.get(j).getY()-350, 800, 800);
				bombAnimations.get(j).drawAnim(g2d, bombAnimations.get(j).getX()-350+3600, bombAnimations.get(j).getY()-350, 800, 800);
				bombAnimations.get(j).drawAnim(g2d, bombAnimations.get(j).getX()-350-3600, bombAnimations.get(j).getY()-350, 800, 800);
				bombAnimations.get(j).updateAnim();
			}
			
			for (int i=0; i<planes.size(); i++) {
				planes.get(i).setAngle(planes.get(i).getAngle()+planes.get(i).getSpeed());
				if (planes.get(i).getAngle()>3600) {
					planes.get(i).setAngle(planes.get(i).getAngle()-3600);
				}
				if (waveCool==150 && planes.get(i).getAngle()>1500 && planes.get(i).getAngle()<1501+planes.get(i).getSpeed()) {
					bombs.add(new Bomb(planes.get(i).getAngle()+50, planes.get(i).getElevation()+50, planes.get(i).getSpeed()));//(int)((-Math.abs(Math.pow(-1.025, -waveCool))+1)*(t-os)+os)
	
				}
				g2d.drawImage(planes.get(i).getSprite().getImage(), planes.get(i).getAngle(), (int)(planes.get(i).getElevation()-(Math.pow(1.03, -waveCool)*1200)),150,150, this);
				g2d.drawImage(planes.get(i).getSprite().getImage(), planes.get(i).getAngle()+3600, (int)(planes.get(i).getElevation()-(Math.pow(1.03, -waveCool)*1200)),150,150, this);
				g2d.drawImage(planes.get(i).getSprite().getImage(), planes.get(i).getAngle()-3600, (int)(planes.get(i).getElevation()-(Math.pow(1.03, -waveCool)*1200)),150,150, this);
				
//				g2d.setColor(new Color(255,0,0,50));
//				g2d.fillRect(planes.get(i).getAngle(), planes.get(i).getElevation()+20, 150, 95);
//				g2d.fillRect(planes.get(i).getAngle()+3600, planes.get(i).getElevation()+20, 150, 95);
//				g2d.fillRect(planes.get(i).getAngle()-3600, planes.get(i).getElevation()+20, 150, 95);
			}
			
			for (int i=0; i<bullets.size(); i++) {
				bullets.get(i).advance(g2d);
				if (bullets.get(i).isLive()==false) {
					for (int j=0; j<planes.size(); j++) {
						if (!Task.done("Bullet_Explodes"+i)) {
							if (new Rectangle(planes.get(j).getAngle(), planes.get(j).getElevation()+20, 150, 95).contains(new Point(bullets.get(i).getAngle(), bullets.get(i).getElevation())) || 
									new Rectangle(planes.get(j).getAngle()+3600, planes.get(j).getElevation()+20, 150, 95).contains(new Point(bullets.get(i).getAngle(), bullets.get(i).getElevation())) || 
									new Rectangle(planes.get(j).getAngle()-3600, planes.get(j).getElevation()+20, 150, 95).contains(new Point(bullets.get(i).getAngle(), bullets.get(i).getElevation()))) {
								hitPlane=j;
								if (planes.get(hitPlane).getHealth()==0) {
									Sound.playSoundOnce("Sources/sounds/Arcade Explo A.wav","Bullet_Explodes"+i);
									hitAnimations.add(new Animation("Sources/images/explosion", "png"));
									kills++;
								}
								//planes.remove(j);
							}
						}
					}
					Task.add("Bullet_Explodes"+i);
					bullets.get(i).setDeathT(bullets.get(i).getDeathT()+1);
					if (bullets.get(i).getDeathT()==16) {
						bullets.remove(i);
						if (hitPlane!=-1) {
							if (planes.get(hitPlane).getHealth()==0) {
								hitAnimations.remove(0);
								planes.remove(hitPlane);
							} else {
								planes.get(hitPlane).setHealth(planes.get(hitPlane).getHealth()-1);
							}
							hitPlane = -1;
						}
						Task.prime("Bullet_Explodes"+i);
						Sound.prime("Bullet_Explodes"+i);
					}
				}
				
			}
			if (hitPlane != -1) {
				for (int j=0; j<hitAnimations.size(); j++) {
					hitAnimations.get(j).drawAnim(g2d, planes.get(hitPlane).getAngle()-100, planes.get(hitPlane).getElevation()-150, 400, 400);
					hitAnimations.get(j).drawAnim(g2d, planes.get(hitPlane).getAngle()-100+3600, planes.get(hitPlane).getElevation()-150, 400, 400);
					hitAnimations.get(j).drawAnim(g2d, planes.get(hitPlane).getAngle()-100-3600, planes.get(hitPlane).getElevation()-150, 400, 400);
					hitAnimations.get(j).updateAnim();
				}
			}
			
			g2d.translate(-Cam.x, -Cam.y);
			
			rolltrans.setToIdentity();
			g2d.setTransform(oldrolltrans);
			
			//Diego's code -----------------------------------------------------------------------------------------------------------
			//Diego's code -----------------------------------------------------------------------------------------------------------
			//Diego's code -----------------------------------------------------------------------------------------------------------
			//Diego's code -----------------------------------------------------------------------------------------------------------
			
			g2d.setColor(new Color (51, 102, 0));
			g2d.fillRect(r.x, r.y, r.width, r.width);
			g2d.fillRect(515, 50, 160, 140);
			
			g2d.setColor(new Color (51, 102, 0));
			final BasicStroke wideStroke = new BasicStroke(60.0f);
			g2d.setStroke(wideStroke);
			g2d.draw(new Arc2D.Double(-50, 50, 1300, 1300, 0, 180, Arc2D.OPEN));
			final BasicStroke medStroke = new BasicStroke(40.0f);
			g2d.setStroke(medStroke);
			g2d.draw(new Arc2D.Double(350, 175, 500, 800, 0, 180, Arc2D.OPEN));
			
			g2d.drawImage(new Sprite("Sources/images/final/cockpit 2.png").getImage(), 0,0, 1200, 800, this);
			
			//Altitude
			g2d.setColor(new Color(96, 96, 96));
			g2d.fillOval(48, 610, 150, 150);
			
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("" + 0, 115, 630);
			g2d.drawString("" + 1, 145, 640);
			g2d.drawString("" + 2, 170, 670);
			g2d.drawString("" + 3, 170, 710);
			g2d.drawString("" + 4, 145, 740);
			g2d.drawString("" + 5, 115, 750);
			g2d.drawString("" + 6, 85, 740);
			g2d.drawString("" + 7, 60, 710);
			g2d.drawString("" + 8, 60, 670);
			g2d.drawString("" + 9, 85, 640);
			//g2d.drawString("" + 1, 115, 690);
			
			double dz = -72*Math.sin(-(double)(Cam.y-70)/202);
			double dv = -72*Math.cos(-(double)(Cam.y-70)/202);
			final BasicStroke smallStroke = new BasicStroke(5.0f);
			g2d.setStroke(smallStroke);
			g2d.setColor(new Color(255, 255, 255));
			g2d.draw(new Line2D.Double(124, 685, dz+124, dv+685));
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("Altitude", 75, 605);
			
			
			//roll
			double dw = -72*Math.sin(-(double)(Cam.vx)/70);
			double de = -72*Math.cos(-(double)(Cam.vx)/70);
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("Roll", 307, 605);
			
			g2d.setColor(new Color(96, 96, 96));
			g2d.fillOval(250, 610, 150, 150);
			g2d.setColor(new Color(255, 255, 255));
			g2d.drawLine(324, 685, (int)dw+324, (int)de+685);
	
			//Fuel
			g2d.translate(400, 0);
			
			g2d.setColor(new Color(96, 96, 96));
			g2d.fillOval(48, 610, 150, 150);
			
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("" + 0, 115, 630);
			g2d.drawString("" + 1, 145, 640);
			g2d.drawString("" + 2, 170, 670);
			g2d.drawString("" + 3, 170, 710);
			g2d.drawString("" + 4, 145, 740);
			g2d.drawString("" + 5, 115, 750);
			g2d.drawString("" + 6, 85, 740);
			g2d.drawString("" + 7, 60, 710);
			g2d.drawString("" + 8, 60, 670);
			g2d.drawString("" + 9, 85, 640);
			//g2d.drawString("" + 1, 115, 690);
			
			dz = -72*Math.sin(-fuel*Math.PI*2/1200);
			dv = -72*Math.cos(-fuel*Math.PI*2/1200);
			g2d.setStroke(smallStroke);
			g2d.setColor(new Color(255, 255, 255));
			g2d.draw(new Line2D.Double(124, 685, dz+124, dv+685));
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("Fuel", 100, 605);
			
			g2d.translate(-400, 0);
			
			
			//Ammo
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("Ammo", 700, 605);
					
			g2d.setColor(new Color(96, 96, 96));
			g2d.fillOval(650, 610, 150, 150);
			
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 10));
			g2d.drawString("R: Reload", 700, 635);
					
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("" + ammo, 702, 690);
				
			//Health Bar
			g2d.setColor(new Color (96, 96, 96));
			g2d.fillRect(870, 720, 300, 50);
			
			g2d.setColor(new Color (255, 0, 0));
			g2d.fillRect(875, 725, 290*health/100, 40);
			
			g2d.setColor(new Color (255, 255, 255));
			g2d.setFont(new Font("Courier", 3, 20));
			g2d.drawString("City Health", 950, 750);
			
			//radar
			
			AffineTransform oldtrans = new AffineTransform();
			AffineTransform trans = new AffineTransform();
	
			trans.setToIdentity();
		    trans.scale(2, 2);
		    oldtrans.scale(2, 2);
			trans.rotate(Math.toRadians(Cam.x/10-45), 945+75, 560+75);
			g2d.setTransform(trans);
			
			//g2d.drawImage(new Sprite("Sources/images/radar-306289_960_720.png").getImage(), 240, 590, 175, 175,null);
	
			g2d.drawImage(new Sprite("Sources/images/Screen Shot 2021-12-16 at 9.23.09 AM.png").getImage(), 945, 560, 150, 150,null);
	
			
			g2d.setStroke(smallStroke);
			g2d.setColor(new Color(51, 255, 51));
			g2d.draw(new Line2D.Double(1020, 635, 72*Math.cos(Math.toRadians(angle/10-97))+1020, 72*Math.sin(Math.toRadians(angle/10-97))+635));
	
			for (int n=0;n<=5;n++) {
				angle+=10;
				if (angle>3600) {
					angle-=3600;
				}
				radar[angle/10-1] = false;
				for (int i=0; i<planes.size(); i++) {
					radar[angle/10-1] = radar[angle/10-1] || planes.get(i).getAngle()/10==angle/10;
				}
			}
			for (int i=0; i<360; i++) {
				if (radar[i]) {
					g2d.fillOval((int)(62*Math.cos(Math.toRadians(i-97))+1015), (int)(62*Math.sin(Math.toRadians(i-97))+630), 10, 10);
				}
			}
			
			g2d.drawOval(945, 560, 150, 150);
			g2d.drawOval(945+26, 560+26, 150-52, 150-52);
			g2d.drawOval(945+50, 560+50, 150-100, 150-100);
			
			for (int i=0; i<360; i+=15) {
				g2d.setStroke(new BasicStroke());
				g2d.drawLine(1020, 635, (int)(72*Math.cos(Math.toRadians(i))+1020), (int)(72*Math.sin(Math.toRadians(i))+635));
			}
			
			trans.setToIdentity();
			g2d.setTransform(oldtrans);
			
			g2d.setColor(new Color (250, 250, 250));
			g2d.setFont(new Font("Courier", 1, 20));
			g2d.drawString("█", (int)(82*Math.cos(Math.toRadians(Cam.x/10))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10))+635+5));
			g2d.drawString("█", (int)(82*Math.cos(Math.toRadians(Cam.x/10-90))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-90))+635+5));
			g2d.drawString("█", (int)(82*Math.cos(Math.toRadians(Cam.x/10-180))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-180))+635+5));
			g2d.drawString("█", (int)(82*Math.cos(Math.toRadians(Cam.x/10-270))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-270))+635+5));
			
			g2d.setColor(new Color (250, 250, 250));
			g2d.setFont(new Font("Courier", 1, 15));
			g2d.drawString("██", (int)(82*Math.cos(Math.toRadians(Cam.x/10-135))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-135))+635+5));
			g2d.drawString("██", (int)(82*Math.cos(Math.toRadians(Cam.x/10-45))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-45))+635+5));
			g2d.drawString("██", (int)(82*Math.cos(Math.toRadians(Cam.x/10-225))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-225))+635+5));
			g2d.drawString("██", (int)(82*Math.cos(Math.toRadians(Cam.x/10-315))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-315))+635+5));
	
			g2d.setColor(new Color (50, 0, 20));
			g2d.setFont(new Font("Courier", 1, 20));
			g2d.drawString("E", (int)(82*Math.cos(Math.toRadians(Cam.x/10))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10))+635+5));
			g2d.drawString("N", (int)(82*Math.cos(Math.toRadians(Cam.x/10-90))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-90))+635+5));
			g2d.drawString("W", (int)(82*Math.cos(Math.toRadians(Cam.x/10-180))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-180))+635+5));
			g2d.drawString("S", (int)(82*Math.cos(Math.toRadians(Cam.x/10-270))+1020-5), (int)(82*Math.sin(Math.toRadians(Cam.x/10-270))+635+5));
			
			g2d.setColor(new Color (50, 0, 20));
			g2d.setFont(new Font("Courier", 1, 15));
			g2d.drawString("NE", (int)(82*Math.cos(Math.toRadians(Cam.x/10-45))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-45))+635+5));
			g2d.drawString("NW", (int)(82*Math.cos(Math.toRadians(Cam.x/10-135))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-135))+635+5));
			g2d.drawString("SW", (int)(82*Math.cos(Math.toRadians(Cam.x/10-225))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-225))+635+5));
			g2d.drawString("SE", (int)(82*Math.cos(Math.toRadians(Cam.x/10-315))+1020-10), (int)(82*Math.sin(Math.toRadians(Cam.x/10-315))+635+5));
	
			//g2d.drawImage(new Sprite("Sources/images/Hawker Hurricane.png").getImage(), 2013, 1245, 60, 60,null);
			
			
			g2d.setColor(new Color (50, 0, 20));
			g2d.setFont(new Font("Courier", 1, 20));
			g2d.drawString("Score: " + kills*100, 10, 20);

			
			//crosshair
			g2d.drawImage(new Sprite("Sources/images/crosshair.png").getImage(), 388+50, 220+50, 425-100, 350-100, null);
			
			//win/lose condition
			if (fuel<=0) {
				Cam.vy-=5;
			}
			if (fuel<=0 && Cam.y<-1100) {
				gameState="win";
			}
			if (health<=0) {
				gameState="death";
			}
			//beat wave
			if (planes.size()==0 || waveCool<150) {
				if (!Task.done("targetHealth")) {
					Task.add("targetHealth");
					targetHealth=health+(100-health)*7/10;
					offsetHealth=health;
					wave++;
					waveCool=0;
					for (int i=0;i<8+wave;i++) {
			        	planes.add(new Plane(new Sprite("Sources/images/final/plane  - Copy.png"), (int)(Math.random()*3590), (int)(Math.random()*700)+300, (int)(Math.random()*6-wave/2)+4+wave/2));
			        }
				}
				waveCool++;
				health=(int)((-Math.abs(Math.pow(-1.025, -waveCool))+1)*(targetHealth-offsetHealth)+offsetHealth);
				g2d.setColor(new Color (50, 0, 20));
				g2d.setFont(new Font("Courier", 1, 100));
				g2d.drawString("Wave " + (wave+1), (int) Math.pow(2*waveCool-150,3)/2000+600, 500);
				
				if (waveCool==150) {
					Task.prime("targetHealth");
					health=targetHealth;
				}
			}
			
		} else if (gameState=="win"){
			g2d.drawImage(new Sprite("Sources/images/final/Untitled.jpg").getImage(), 0, 0, 1200, 800,null);
			g2d.setFont(new Font("Courier", 1, 40));
			g2d.drawString("Score: " + kills*100, 450, 680);
		} else if (gameState=="death") {
			Sound.playSoundOnce("Sources/sounds/DeathSound.wav", "hi");
			Sound.stop("Sources/sounds/bounce.wav");
			g2d.setColor(new Color(255, 0, 0));
			g2d.setFont(new Font("Courier", 50, 100));
			g2d.drawImage(new Sprite("Sources/images/untitled5.jpg").getImage(), 0, 0, 1200, 800,null);
			g2d.drawString("MISSION FAILED!", 150, 225);
			g2d.setFont(new Font("Courier", 50, 50));
			g2d.setColor(new Color(255, 255, 255));
			g2d.drawString("Press 2 to exit", 300, 775);
			g2d.setFont(new Font("Courier", 1, 40));
			g2d.drawString("Score: " + kills*100, 450, 80);

		} else if (gameState=="pause") {
			Sound.stop("Sources/sounds/Project - 1_10_22, 8.20 AM-1.wav");
			g2d.drawImage(new Sprite("Sources/images/final/pausemenu.001.png").getImage(), 0, 0, 1200, 800, null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
				key.right=1;
				break;
			case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
				key.left=-1;
				break;
			case KeyEvent.VK_UP: case KeyEvent.VK_W:
				key.up=-1;
				break;
			case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
				key.down=1;
				//Sound.playSound("Sources/sounds/piano2.wav");
				break;
			case KeyEvent.VK_SPACE:
				key.space=1;
				break;
			case KeyEvent.VK_1:
				if (gameState=="startscreen" || gameState=="pause" || gameState=="death") {
					gameState = "gameplay";
					Sound.playSound("Sources/sounds/Project - 1_10_22, 8.20 AM-1.wav", true, -10);
				}
				break;
			case KeyEvent.VK_2:
				if (gameState=="pause" || gameState=="death" || gameState=="win")
					init();
				break;
			case KeyEvent.VK_ESCAPE:
				if (gameState=="gameplay") {
					gameState="pause";
				} else if (gameState=="pause") {
					gameState="gameplay";
					Sound.playSound("Sources/sounds/Project - 1_10_22, 8.20 AM-1.wav", true, -10);

				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
				key.right=0;
				Yaw++;
				break;
			case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
				key.left=0;
				Yaw--;
				break;
			case KeyEvent.VK_UP: case KeyEvent.VK_W:
				key.up=0;
				break;
			case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
				key.down=0;
				break;
			case KeyEvent.VK_SPACE:
				key.space=0;
				break;
			case KeyEvent.VK_R:
				if (gameState=="gameplay")
					ammo+=Math.floor(Math.random()*5+1);
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}