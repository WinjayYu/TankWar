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
	Direction barrelDir = Direction.D;
	
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
		Color c = g.getColor();//拿到前景色
		g.setColor(co);
		g.fillOval(x,y,WIDTH,HEIGHT);//画一个实心圆并用当前颜色填充
		g.setColor(c);
		
		switch(barrelDir) {
		case L:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x, y + CreateTank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x + CreateTank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x + CreateTank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x + CreateTank.WIDTH, y + CreateTank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x + CreateTank.WIDTH, y + CreateTank.HEIGHT);
			break;
		case D:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + CreateTank.WIDTH/2, y + CreateTank.HEIGHT/2, x, y + CreateTank.HEIGHT);
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
		case STOP:
			break;
		}
		
		if(dir != Direction.STOP)
			barrelDir = dir;
	}
	
	public void keyPressed (KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_F :
			fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;  //注意写break
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
		else if(!bL && bU && !bR && !bD) dir= Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {//键盘松开后，对应的boolean恢复初始值
        int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_LEFT :
			bL = false;
			break;  //注意写break
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
		missile = new Missile(x, y, barrelDir);
		t.missiles.add(missile);
		return missile;
	}
}
