import java.awt.*;
import java.awt.geom.*;

public class BallView {
    /**
     * Color de la bola cuando no esta seleccionada
     */
    private Color color = Color.BLUE;
    /**
     * Shape que dibujara la bola
     */
    private Ellipse2D.Double shape = new Ellipse2D.Double();
    /**
     * Modelo de bola que representara esta vista
     */
    private Ball ball;

    /**
     * @param b Bola a la cual representara esta vista
     */
    public BallView (Ball b){
        this.ball = b;
    }
    /**
     * @param x Coordenada x
     * @param y Coordenada y
     * @return true si contiene al punto (x,y), false en otro caso
     */
    public boolean contains (double x, double y){
        return shape.getBounds2D().contains(x,y);
    }
    /**
     * Actualiza el color de la bola cuando esta seleccionada
     */
    public void setSelected (){
        color = Color.RED;
    }
    /**
     * Actualiza el color de la bola cuando no esta seleccionada
     */
    public void setReleased() {
        color = Color.BLUE;
    }
    /**
     * Actualiza la vista
     * @param g
     */
    void updateView(Graphics2D g) {
        double radius = ball.getRadius();
        shape.setFrame(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
        g.setColor(color);
        g.fill(shape);
    }
}