import java.awt.*;
import java.awt.geom.*;

public class BallView {
    private Color color = Color.BLUE;
    private Ellipse2D.Double shape = new Ellipse2D.Double();
    private Ball ball;

    public BallView (Ball b){
        this.ball = b;
    }
    public boolean contains (double x, double y){
        return true;
    }
    public void setSelected (){
        color = Color.RED;
    }
    public void setReleased() {
        color = Color.BLUE;
    }
    void updateView(Graphics2D g) {
        double radius = ball.getRadius();
        shape.setFrame(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
        g.setColor(color);
        g.fill(shape);
    }
}