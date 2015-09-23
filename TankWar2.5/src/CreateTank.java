import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class CreateTank {
	int x, y;
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;  
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	public static boolean bL=false, bU=false, bR=false, bD=false;

	private Direction dir = Direction.STOP;
	private Direction barrelDir = Direction.D;
	
	
	public static Random r = new Random();
	private bloodBar bb = new bloodBar();
	
	Tank t = null;
	Missile missile = null;
	
	private boolean live = true;
	private boolean good ;
	
	private int step = r.nextInt(13) + 5; 
	
	int oldX, oldY;
	private int life = 100;
	
	//Blood b = new Blood();
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = { 
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(CreateTank.class.getClassLoader().getResource("images/tankLD.gif"))
	};


	public CreateTank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public CreateTank(int x, int y, boolean good, Tank t){
		this(x, y);
		this.good = good;
		this.t = t;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean isLive) {
		this.live = isLive;
	}
	
	public boolean isGood() {
		return good;
	}

	public void draw(Graphics g ) {	
		if(!live) {
			t.tanks.remove(this);
			return;
		}
		/*Color c = g.getColor();//拿到前景色
		if(good) {
			g.setColor(Color.RED);
			bb.draw(g);
		}
		else {
			g.setColor(Color.BLUE);
		}
		g.fillOval(x,y,WIDTH,HEIGHT);//画一个实心圆并用当前颜色填充
		g.setColor(Color.WHITE);*/
		
		if(good) bb.draw(g);
		
		switch(barrelDir) {
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
		//g.setColor(c);
		
		move();
		eatBlood(t.b);
	}

	public void move(){
		oldX = x;
		oldY = y;
		
		if(good) {
			switch(dir) {
			case L:
				if(x <= 0){}
				else x -= XSPEED*2;
				break;
			case LU:
				if(x <= 0 || y <= 25){}
				else{
					x -= XSPEED*2;
					y -= YSPEED*2;
				}
				break;
			case U:
				if(y <= 25){}
				else
					y -= YSPEED*2;
				break;
			case RU:
				if(x >= Tank.GAME_WIDTH-WIDTH || y <= 25){}
				else {
					x += XSPEED*2;
					y -= YSPEED*2;
				}
				break;
			case R:
				if(x >= Tank.GAME_WIDTH-WIDTH){}
				else
					x += XSPEED*2;
				break;
			case RD:
				if(x >= Tank.GAME_WIDTH-WIDTH || y >= Tank.GAME_HEIGHT-HEIGHT){}
				else {
					x += XSPEED*2;
					y += YSPEED*2;
				}
				break;
			case D:
				if(y >= Tank.GAME_HEIGHT-HEIGHT){}
				else
					y += YSPEED*2;
				break;
			case LD:
				if(x <= 0 || y >= Tank.GAME_HEIGHT-HEIGHT){}
				else {
					x -= XSPEED*2;
					y += YSPEED*2;
				}
				break;
			case STOP:
				break;
			}
		}
		else {
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
		}
		
		if(dir != Direction.STOP)
			barrelDir = dir;

		if(!good) {
			Direction dirs [] = Direction.values();
			if(step == 0) {
				step = r.nextInt(15) + 5;
				int i = r.nextInt(dirs.length);
				dir = dirs[i];
			}
			step --;
			if(r.nextInt(30) > 28) {
				fire();
			}
		}
		/*
		if(x < 0) x = 0;
		if(y < 25) y = 25;
		if(x > (Tank.GAME_WIDTH - WIDTH)) x = Tank.GAME_WIDTH - WIDTH;
		if(y > (Tank.GAME_HEIGHT - HEIGHT)) y = Tank.GAME_HEIGHT - HEIGHT;
		*/   //马士兵解决坦克出界问题的方法
	}
	
	private void stay(){		
			x = oldX;
			y = oldY;
	}
	
	public void keyPressed (KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_F2 :
			if(!this.live) {
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_CONTROL :
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
		case KeyEvent.VK_A :
			superFire();
			break;
		}
		direction();
	}

	public Missile fire() {
		if(isLive()){
			if(barrelDir==Direction.L || barrelDir==Direction.U || barrelDir==Direction.R || barrelDir==Direction.D) {
				int x = this.x + 20;
				int y = this.y + 22;
				missile = new Missile(x, y, barrelDir, good, t);
				t.missiles.add(missile);
				return missile;
			}
			else if(barrelDir==Direction.RU) {
				int x = this.x + 45;
				int y = this.y + 10;
				missile = new Missile(x, y, barrelDir, good, t);
				t.missiles.add(missile);
				return missile;
			}
			else if(barrelDir==Direction.LD) {
				int x = this.x + 12;
				int y = this.y + 45;
				missile = new Missile(x, y, barrelDir, good, t);
				t.missiles.add(missile);
				return missile;
			}
			else if(barrelDir==Direction.RD) {
				int x = this.x + 22;
				int y = this.y + 25;
				missile = new Missile(x, y, barrelDir, good, t);
				t.missiles.add(missile);
				return missile;
			}
            else {
				int x = this.x + CreateTank.WIDTH / 2 - Missile.WIDTH / 2;
				int y = this.y + CreateTank.HEIGHT / 2 - Missile.HEIGHT / 2;
				missile = new Missile(x, y, barrelDir, good, t);
				t.missiles.add(missile);
				return missile;
			}
		}
		return null;
	}
	
	public Missile fire(Direction dir) {
		if(isLive()){
			int x = this.x + CreateTank.WIDTH/2 -Missile.WIDTH/2;
			int y = this.y + CreateTank.HEIGHT/2 - Missile.HEIGHT/2;
			missile = new Missile(x+10, y+10, dir, good, t);
			t.missiles.add(missile);
			return missile;
		}
		return null;
	}
	
	public void superFire() {
		Direction [] dirs = Direction.values();
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}
	
	public boolean collidesWithWall(Wall w) {
		if(this.isLive() && this.getRect().intersects(w.getRect())) {
			stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTank(CreateTank ct) {
		if(this.live && ct.isLive() && this.getRect().intersects(ct.getRect())) {
			this.stay();
			ct.stay();
			return true;
		}
		return false;
	}
	
	public boolean clooidesWithTanks(java.util.List<CreateTank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			CreateTank ct = tanks.get(i);
			if(this != ct) {
				if(collidesWithTank(ct)) {
					return true;
			}
			}
		}
		return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, images[0].getWidth(null),images[0].getWidth(null));
	}
	
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	private class bloodBar {
		private void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.fillRect(x+WIDTH/3, y-10, WIDTH, 10);
			g.setColor(Color.RED);
			g.fillRect(x+WIDTH/3, y-9, (WIDTH*life/100), 8);
			g.setColor(c);
		}
	}
	
private void eatBlood(Blood b) {
		if(good && this.live && b.live && this.getRect().intersects(b.getRect())) {
			if(life<100) {
				life += 20;
				b.live = false;
			}
		}
	}


}

