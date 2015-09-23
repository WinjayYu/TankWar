
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Tank extends Frame {

	//public Missile missile = null;
	//public CreateTank myTanks = null;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	Image offScreenImage = null;

	CreateTank myTanks = new CreateTank(300, 300, true, this);
	//CreateTank enemyTanks = new CreateTank(300, 300, Color.BLUE, this);
	
	List<Missile> missiles = new ArrayList<Missile>();//注意小括号不要写掉
	List<Explode> explode = new ArrayList<Explode>();
	List<CreateTank> tanks = new ArrayList<CreateTank>();
	
	Wall w1 = new Wall(300, 150, 300, 20, this);
	Wall w2 = new Wall(100, 300, 20, 200, this);
	Blood b = new Blood();

	public static void main(String[] args) {
		Tank t = new Tank(); 
		t.TankFrame();
	}

	public void TankFrame() {
		
		for(int i=0; i<10; i++) {
			tanks.add(new CreateTank(50+(i+1)*40, 50, false, this));
		}

		this.setTitle("TankWar");
		this.setLocation(200,120);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		this.setResizable(false);  //固定窗口大小
		this.setBackground(Color.DARK_GRAY);
		this.addKeyListener(new KeyMoniter());
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.drawString("missiles number："+ missiles.size(), 10, 40);
		g.drawString("explode number："+ explode.size(), 10, 60);
		g.drawString("tanks number："+ tanks.size(), 10, 80);
		g.drawString("myTanks life："+ myTanks.getLife(), 10, 100);
		if(!myTanks.isLive()){
			//Font f = g.getFont();
			//g.setFont(new Font("", Font.BOLD, 30));
			g.drawString("GAMEOVER!", 300, GAME_HEIGHT/2);
			//g.setFont(f);
		}
		if(tanks.size() == 0) {
			g.drawString("YOUWIN!", 300, GAME_HEIGHT/2);
		}
		g.setColor(c);
		for(int i=0; i<missiles.size(); i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTanks);
			m.hitWall(w1);
			m.hitWall(w2);
			if(!m.isLive()) missiles.remove(i);
			else m.draw(g);

		}
		
		for(int i=0; i<explode.size(); i++){
			Explode e = explode.get(i);
			if(!e.isLive()) explode.remove(i);
			else 
				e.draw(g);
		}
		
		for(int i=0; i<tanks.size(); i++) {
			CreateTank ct = tanks.get(i);
			ct.collidesWithWall(w1);
			ct.collidesWithWall(w2);
			ct.clooidesWithTanks(tanks);
			ct.collidesWithTank(myTanks);
			ct.draw(g);
		}
		myTanks.draw(g);
		myTanks.collidesWithWall(w1);
		myTanks.collidesWithWall(w2);
		
		w1.draw(g);
		w2.draw(g);
		
		if(b.live) b.draw(g);
		
		if(tanks.size() == 0) {
			for(int i=0; i<5; i++) {
				tanks.add(new CreateTank(50+(i+1)*40, 50, false, this));
			}
		}
		
	}

	public void update(Graphics g) {
		if(null == offScreenImage) {
			offScreenImage = this.createImage(800, 600);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.DARK_GRAY);
		gOffScreen.fillRect(0, 0, 800, 600);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class PaintThread implements Runnable {

		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	

	private class KeyMoniter extends KeyAdapter {  //从KeyAdapter继承可以避免实现KeyListener接口时必须实现接口内所有方法的弊端；

		public void keyReleased(KeyEvent e) {
			myTanks.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTanks.keyPressed(e);
		}
	}
	
	
}















