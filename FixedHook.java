import java.util.*;
import java.awt.*;

public class FixedHook extends PhysicsElement implements SpringAttachable
{
	private static int id = 0;
	private double pos_t;
	private double width;
	private FixedHookView view;
	private ArrayList<Spring> springs;

	public FixedHook(double pos_t, double width)
	{
		super(id++);
		this.pos_t = pos_t;
		this.width = width;
		this.view = new FixedHookView(this);
		this.springs = new ArrayList<Spring>();
	}

	public double getWidth()
	{
		return this.width;
	}

	/* Implementacion de PhysicsElement */

	public String getDescription()
	{
		return "FixedHook_" + getId()+":x";
	}

	public String getState()
	{
		return getPosition()+"";
	}

	public void updateView(Graphics2D g)
	{
		this.view.updateView(g);
	}

	public boolean contains(double x, double y)
	{
		return true;
	}

	public void setSelected()
	{

	}

	public void setReleased()
	{

	}

	public void dragTo(double x)
	{

	}

	/* Implementacion de SpringAttachable */

	public void attachSpring(Spring s)
	{
		this.springs.add(s);
	}

  	public void detachSpring(Spring s)
  	{

  	}

  	public double getPosition()
	{
		return this.pos_t;
	}
}