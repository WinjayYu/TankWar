/*
 * 产生一个窗口
 */

import java.awt.*;
import java.awt.event.*;

public class Tank {

	public static void main(String[] args) {
		new TankFrame("tank");
	}

}

class TankFrame extends Frame {
	TankFrame(String s) {
		super(s);
		this.setLocation(300,300);
		this.setSize(400, 400);		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setVisible(true);
	}
}
