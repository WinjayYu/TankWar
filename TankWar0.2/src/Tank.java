/*
 * 固定窗口大小
 */

import java.awt.*;
import java.awt.event.*;

public class Tank {

	public static void main(String[] args) {
		new TankFrame("TankWar");
	}

}

class TankFrame extends Frame {
	TankFrame(String s) {
		super(s);
		this.setLocation(300,300);
		this.setSize(800, 600);		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);  //固定窗口大小
		this.setVisible(true);
	}
}
