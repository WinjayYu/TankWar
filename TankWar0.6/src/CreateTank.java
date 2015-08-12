import java.awt.*;
import java.awt.event.*;


public class CreateTank {
	int x, y;
	Color co;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	enum Direction  {L, LU, U, RU, R, RD, D, LD, STOP};
	public static boolean bL=false, bU=false, bR=false, bD=false;

	Direction dir = Direction.STOP;
	
	public CreateTank(int x, int y, Color co) {
		this.x = x;
		this.y = y;
		this.co = co;
	}
	
	public void draw(Graphics g ) {	
		Color c = g.getColor();//拿到前景色
		g.setColor(co);
		g.fillOval(x,y,30,30);//画一个实心圆并用当前颜色填充
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
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
	}
}
