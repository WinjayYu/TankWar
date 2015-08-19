
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
		this.setBackground(Color.GREEN);
		this.addKeyListener(new KeyMoniter());
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public void paint(Graphics g) {
		g.drawString("missiles number："+ missiles.size(), 10, 40);
		g.drawString("explode number："+ explode.size(), 10, 60);
		g.drawString("tanks number："+ tanks.size(), 10, 80);
		if(!myTanks.isLive()){
			//Font f = g.getFont();
			//g.setFont(new Font("", Font.BOLD, 30));
			g.drawString("GAMEOVER!", 300, GAME_HEIGHT/2);
			//g.setFont(f);
		}
		for(int i=0; i<missiles.size(); i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTanks);
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
			CreateTank c = tanks.get(i);
			c.draw(g);
		}
		myTanks.draw(g);	
		
	}

	public void update(Graphics g) {
		if(null == offScreenImage) {
			offScreenImage = this.createImage(800, 600);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
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















