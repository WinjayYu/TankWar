import java.awt.*;
import java.awt.event.*;


public class CreateTank {
	int x, y;
	
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
	
	private boolean Live = true;
	public boolean good ;
	
	public boolean isLive() {
		return Live;
	}

	public void setLive(boolean isLive) {
		this.Live = isLive;
	}

	public CreateTank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public CreateTank(int x, int y, boolean good, Tank t){
		this(x, y);
		this.good = good;
		this.t = t;
	}
	
	public void draw(Graphics g ) {	
		if(!Live) {
			t.tanks.remove(this);
			return;
		}
		Color c = g.getColor();//拿到前景色
		if(good) {
			g.setColor(Color.RED);
		}
		else {
			g.setColor(Color.BLUE);
		}
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
			if(x == 0){}
			else x -= XSPEED;
			break;
		case LU:
			if(x == 0 || y == 25){}
			else{
				x -= XSPEED;
				y -= YSPEED;
			}
			break;
		case U:
			if(y == 25){}
			else
				y -= YSPEED;
			break;
		case RU:
			if(x == Tank.GAME_WIDTH-WIDTH || y == 25){}
			else {
				x += XSPEED;
				y -= YSPEED;
			}
			break;
		case R:
			if(x == Tank.GAME_WIDTH-WIDTH){}
			else
				x += XSPEED;
			break;
		case RD:
			if(x == Tank.GAME_WIDTH-WIDTH || y == Tank.GAME_HEIGHT-HEIGHT){}
			else {
				x += XSPEED;
				y += YSPEED;
			}
			break;
		case D:
			if(y == Tank.GAME_HEIGHT-HEIGHT){}
			else
				y += YSPEED;
			break;
		case LD:
			if(x == 0 || y == Tank.GAME_HEIGHT-HEIGHT){}
			else {
				x -= XSPEED;
			    y += YSPEED;
			}
			break;
		case STOP:
			break;
			}
		
		if(dir != Direction.STOP)
			barrelDir = dir;
		/*
		if(x < 0) x = 0;
		if(y < 25) y = 25;
		if(x > (Tank.GAME_WIDTH - WIDTH)) x = Tank.GAME_WIDTH - WIDTH;
		if(y > (Tank.GAME_HEIGHT - HEIGHT)) y = Tank.GAME_HEIGHT - HEIGHT;
		*/   //马老师解决坦克出界问题的方法
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
		missile = new Missile(x, y, barrelDir, t);
		t.missiles.add(missile);
		return missile;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH,HEIGHT);
	}
}

