import java.awt.*;

public class Explode {
	
	int x, y;
	private boolean live = true;
	int i = 0;
	
	int [] diameter = {4, 6, 12, 20, 36, 50, 30, 10, 5};
	
	Tank t = null;
	
	public Explode(int x, int y, Tank t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	public void draw(Graphics g) {
		
		
		if(!live) return;
		if(i == diameter.length){
			live = false;
			i = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[i], diameter[i]);
		g.setColor(c);
		
		i ++;
	}
}
