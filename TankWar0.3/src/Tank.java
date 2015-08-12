/*
 * 画出一个tank（实心圆）
 * 让坦克动起来
 */

import java.awt.*;
import java.awt.event.*;

public class Tank {

	public static void main(String[] args) {
		new TankFrame("TankWar");
	}

}

class TankFrame extends Frame {

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
