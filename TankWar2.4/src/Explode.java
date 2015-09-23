import java.awt.*;

public class Explode {

	int x, y;
	private boolean live = true;

	int step = 0;

	Tank t = null;
	private static boolean init = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = { tk.getImage(Explode.class.getResource("Image/0.gif")),
			tk.getImage(Explode.class.getResource("Image/1.gif")),
			tk.getImage(Explode.class.getResource("Image/2.gif")),
			tk.getImage(Explode.class.getResource("Image/3.gif")),
			tk.getImage(Explode.class.getResource("Image/4.gif")),
			tk.getImage(Explode.class.getResource("Image/5.gif")),
			tk.getImage(Explode.class.getResource("Image/6.gif")),
			tk.getImage(Explode.class.getResource("Image/7.gif")),
			tk.getImage(Explode.class.getResource("Image/8.gif")),
			tk.getImage(Explode.class.getResource("Image/9.gif")),
			tk.getImage(Explode.class.getResource("Image/10.gif")) };

	public Explode(int x, int y, Tank t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {

		if (!live) {
			t.explode.remove(this);
			return;
		}
		if (step == images.length) {
			live = false;
			step = 0;
			return;
		}
		if (!init) {
			for (int j = 0; j < images.length; j++) {
				g.drawImage(images[j], -100, -100, null);
			}
			init = true;
		}
		
		g.drawImage(images[step], x, y, null);

		step++;
	}
}
