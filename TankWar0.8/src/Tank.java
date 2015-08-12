/*
 * 通过方向键控制坦克的移动
 */ 
import java.awt.*;
import java.awt.event.*;

public class Tank extends Frame {

	public Missile missile = null;
	//public CreateTank myTanks = null;
	Image offScreenImage = null;

	CreateTank myTanks = new CreateTank(50, 50, Color.YELLOW, this);

	public static void main(String[] args) {
		Tank t = new Tank(); 
		t.TankFrame();
	}

	public void TankFrame() {

		this.setTitle("TankWar");
		this.setLocation(200,200);
		this.setSize(800, 600);		
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
		myTanks.draw(g);
		if(missile != null) missile.draw(g);

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















