import java.awt.*;
import java.awt.geom.*;

public class FixedHookView {
	private final double width;
	private Color color = Color.GREEN;
	private Rectangle2D.Double shape = new Rectangle2D.Double();
	private FixedHook hook;

	public FixedHookView(FixedHook hook)
	{
		this.hook = hook;
		this.width = this.hook.getWidth();
	}

	public boolean contains (double x, double y){
		return shape.getBounds2D().contains(x,y);
	}
	public void setSelected (){
		color = Color.YELLOW;
	}
	public void setReleased() {
		color = Color.GREEN;
	}

	void updateView(Graphics2D g)
	{
		shape.setFrame(hook.getPosition() - (width/2), -width/2, width, width);
		g.setColor(color);
      	g.fill(shape);
	}
}