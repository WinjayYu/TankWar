import java.awt.*;
import java.awt.event.*;


public class Missile {
	private int x, y;
	private CreateTank.Direction dir;
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	Missile missile = null;
	
	private boolean live = true; 
	
	private CreateTank ct = null;
	private Tank t = null;
	
	public boolean isLive() {
		return live;
	}

	Missile(int x, int y, CreateTank.Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	Missile(int x, int y, CreateTank.Direction dir, Tank t){
		this(x, y, dir);
		this.t = t;
	}
	
	public void draw(Graphics g ) {	
		if(!live) {
//System.out.println("�ӵ�����");
			//t.missiles.remove(this);
			return;
		}
		Color c = g.getColor();//�õ�ǰ��ɫ
		g.setColor(Color.BLACK);
		g.fillOval(x,y,WIDTH,HEIGHT);//��һ��ʵ��Բ���õ�ǰ��ɫ���
		g.setColor(c);
		
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
		if(this.getRect().intersects(ct.getRect()) && ct.isLive()) {
			ct.setLive(false);
			Explode e = new Explode(x, y, t);
			t.explode.add(e);
			this.live = false;
			
			return true;
		}	
		return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH,HEIGHT);
	}
}
