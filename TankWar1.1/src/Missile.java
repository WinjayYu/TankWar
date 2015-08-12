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
	
	private boolean isLive = true; 
	
	public boolean isLive() {
		if(x<0 || y<0 || x>Tank.GAME_WIDTH || y>Tank.GAME_HEIGHT)
			isLive = false;
		return isLive;
	}

	Missile(int x, int y, CreateTank.Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g ) {	
		Color c = g.getColor();//拿到前景色
		g.setColor(Color.BLACK);
		g.fillOval(x,y,WIDTH,HEIGHT);//画一个实心圆并用当前颜色填充
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
	}
}
