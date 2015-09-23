import java.awt.*;

public class Blood {
	int x, y, w, h;
	int step = 0;
	Tank t;
	boolean live = true; 
	int i = 0;
	private boolean order;
	
	int [][] pos = {
			{200,400}, {220,400}, {240,400}, {260,400}, {280,400}, 
			{300,400}, {320,400}, {340,400}, {360,400}, {380,400},
			{400,400}, {420,400}, {440,400}, {460,400}, {480,400},
			{500,400}, {520,400}, {540,400}, {560,400}, {580,400}
	};
	
	Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = 10;
		h = 10;
	}
	
	public void draw(Graphics g){
		i ++;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		if(live && i>=400) g.fillOval(x, y, w, h);
		g.setColor(c);
		
		if(i == 2000) live = false;
		
		move();
	}
	
	private void move() {
		if(step == 0) order = true;
		if(order) step ++;
		if(step == pos.length) order = false;
		if(!order) step --;
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
