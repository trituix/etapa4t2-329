import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class MyWorldView extends JPanel {

   public static int WIDTH = 900;
   public static int HEIGHT = 150;
   public static int X_ORIGEN = (int)(WIDTH*0.1);
   public static int Y_ORIGEN = (int)(HEIGHT*0.9);
   public static AffineTransform SPACE_TRANSFORM;
   public static AffineTransform SPACE_INVERSE_TRANSFORM;
   public static Line2D.Double X_AXIS;
   public static Line2D.Double Y_AXIS;
   private static double AXES_SCALE = 200.0;
   private MyWorld world;
   private MouseListener mListener;

   static {
        SPACE_TRANSFORM = AffineTransform.getTranslateInstance(X_ORIGEN, Y_ORIGEN);
        SPACE_TRANSFORM.scale(AXES_SCALE,-AXES_SCALE);
        try {
            SPACE_INVERSE_TRANSFORM = SPACE_TRANSFORM.createInverse();
        }
        catch (NoninvertibleTransformException e){
            System.exit(0);
        }
        X_AXIS = new Line2D.Double(-0.2,0, 4.0,0);
        Y_AXIS = new Line2D.Double(0, -0.1, 0, 0.1);
    }

    public MyWorldView(MyWorld w){
        world = w;
        mListener = new MouseListener(w);
        addMouseMotionListener(mListener);
        addMouseListener(mListener);
    }
    public void repaintView(){
        repaint();
    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);
        g2.drawString("ELO329 1er.Sem. 2014,   1 [m] = "+AXES_SCALE+" [pixels]", WIDTH/4, 30);
        g2.transform(SPACE_TRANSFORM);
        g2.setStroke(new BasicStroke(0.02f));
        g2.draw(X_AXIS);
        g2.draw(Y_AXIS);
        for (PhysicsElement e: this.world.getPhysicsElements()) {
            e.updateView(g2);
        }
    }

    public void enableMouseListener(){
      addMouseMotionListener(mListener);
      addMouseListener(mListener);
   }
   public void desableMouseListener(){
      removeMouseMotionListener(mListener);
      removeMouseListener(mListener);
   }
}