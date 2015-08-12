import java.awt.*;
import java.awt.event.*;


public class CreateTank {
	int x, y;
	Color co;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	enum Direction  {L, LU, U, RU, R, RD, D, LD, STOP};
	public static boolean bL=false, bU=false, bR=false, bD=false;

	Direction dir = Direction.STOP;
	
	Tank t = null;
	Missile missile = null;
	
	public CreateTank(int x, int y, Color co) {
		this.x = x;
		this.y = y;
		this.co = co;
	}
	
	public CreateTank(int x, int y, Color co, Tank t){
		this(x, y, co);
		this.t = t;
	}
	
	public void draw(Graphics g ) {	
		Color c = g.getColor();//�õ�ǰ��ɫ
		g.setColor(co);
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
		case STOP:
			break;
		}
	}
	
	public void keyPressed (KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_F :
			t.missile = fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;  //ע��дbreak
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		direction();
	}
	
	public void direction(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {//�����ɿ��󣬶�Ӧ��boolean�ָ���ʼֵ
        int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_LEFT :
			bL = false;
			break;  //ע��дbreak
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		}
		direction();
	}

	public Missile fire() {
		int x = this.x + CreateTank.WIDTH/2 -Missile.WIDTH/2;
		int y = this.y + CreateTank.HEIGHT/2 - Missile.HEIGHT/2;
		missile = new Missile(x, y, dir);
		return missile;
	}
}
