import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class Missile {
	private int x, y;
	private Direction dir;
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	Missile missile = null;
	
	private boolean live = true; 
	
	private CreateTank ct = null;
	private Tank t = null;
	private boolean good;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = { 
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileL.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileLU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileD.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileRU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileR.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileRD.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/missileLD.gif"))
	};
	
	public boolean isLive() {
		return live;
	}

	Missile(int x, int y, Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	Missile(int x, int y, Direction dir, boolean good, Tank t){
		this(x, y, dir);
		this.good = good;
		this.t = t;
	}
	
	public void draw(Graphics g ) {	
		if(!live) {
//System.out.println("子弹死了");
			//t.missiles.remove(this);
			return;
		}
		/*Color c = g.getColor();//拿到前景色
		if(good) {g.setColor(Color.RED);}
		else {g.setColor(Color.YELLOW);}
		g.fillOval(x,y,WIDTH,HEIGHT);//画一个实心圆并用当前颜色填充
		g.setColor(c);*/
		
		switch(dir) {
		case L:
			g.drawImage(images[0], x, y, null);
			break;
		case LU:
			g.drawImage(images[1], x, y, null);
			break;
		case U:
			g.drawImage(images[2], x, y, null);
			break;
		case RU:
			g.drawImage(images[3], x, y, null);
			break;
		case R:
			g.drawImage(images[4], x, y, null);
			break;
		case RD:
			g.drawImage(images[5], x, y, null);
			break;
		case D:
			g.drawImage(images[6], x, y, null);
			break;
		case LD:
			g.drawImage(images[7], x, y, null);
			break;
		}
		
		move();
	}
	
	public void move(){
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		
		if(x<0 || y<0 || x>Tank.GAME_WIDTH || y>Tank.GAME_HEIGHT)
			live = false;
	}
	
	public boolean hitTank(CreateTank ct) {
		if (ct.isLive() && this.getRect().intersects(ct.getRect()) && ct.isLive() && this.good != ct.isGood()) {
			if (ct.isGood()) {
				ct.setLife(ct.getLife() - 20);
				if (ct.getLife() <= 0) {
					ct.setLive(false);
				}
			} else {
				ct.setLive(false);
			}
			Explode e = new Explode(x, y, t);
			t.explode.add(e);
			this.live = false;

			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<CreateTank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.isLive() && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH,HEIGHT);
	}
}
