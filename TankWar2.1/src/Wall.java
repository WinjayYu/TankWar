import java.awt.*;

public class Wall {

	int x, y, w, h;
	Tank t;
	
	public Wall(int x, int y, int w, int h, Tank t) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.t = t;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
}
