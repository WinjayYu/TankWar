/*
 * 通过update方法减小坦克移动时的闪烁
 */

import java.awt.*;
import java.awt.event.*;

public class Tank {

	public static void main(String[] args) {
		new TankFrame("TankWar");
	}

}

class TankFrame extends Frame {

	Image offScreenImage = null;
	
	int x = 50, y= 50;
	
	TankFrame(String s) {
		super(s);
		this.setLocation(200,200);
		this.setSize(800, 600);		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);  //固定窗口大小
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();//拿到前景色
		g.setColor(Color.RED);
		g.fillOval(x,y,30,30);//画一个实心圆并用当前颜色填充
		g.setColor(c);
		
		x += 5;
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
   
}












