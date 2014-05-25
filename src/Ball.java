import java.util.*;
import java.awt.*;

public class Ball extends PhysicsElement implements Simulateable, SpringAttachable {
     private static int id=0;  // Ball identification number
     private final double mass;
     private final double radius;
     private double pos_t;     // current position at time t
     private double pos_tPlusDelta;  // next position in delta time in future
     private double speed_t;   // speed at time t
     private double speed_tPlusDelta;   // speed in delta time in future
     private double a_t;
     private double a_tMinusDelta;
     private BallView view;  // Ball view of Model-View-Controller design pattern
     private ArrayList<Spring> springs;

     private Ball(){   // nobody can create a block without state
         this(1.0,0.1,0,0);
     }
     public Ball(double mass, double radius, double position, double speed){
            super(id++);
            this.mass = mass;
            this.radius = radius;
            pos_t = position;
            speed_t = speed;
            view = new BallView(this);
            this.springs = new ArrayList<Spring>();
     }
     public double getMass() {
            return mass;
     }
     public double getRadius() {
            return radius;
     }
     public double getPosition() {
            return pos_t;
     }
     public double getSpeed() {
            return speed_t;
     }

     private double getNetForce() {
         double sum = 0;
         for (Spring s: springs) {
             sum += s.getForce(this);
         }
         return sum;
     }

     public void computeNextState(double delta_t, MyWorld world) {
         Ball b;  // Assumption: on collision we only change speed.
         if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
                speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
                a_t= getNetForce()/mass;
                pos_tPlusDelta = pos_t + speed_tPlusDelta*delta_t + 0.5*a_t*delta_t*delta_t;
         } else {
                a_tMinusDelta = a_t;
                a_t= getNetForce()/mass;
                speed_tPlusDelta = speed_t + 0.5*(3*a_t - a_tMinusDelta)*delta_t;
                pos_tPlusDelta = pos_t + speed_t*delta_t + (1/6)*(4*a_t - a_tMinusDelta)*delta_t*delta_t;
         }
     }
     public boolean collide(Ball b) {
         if (this == b) return false;
         boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getRadius());
         boolean approaching = getSpeed() > b.getSpeed();
         if (b.getPosition() < getPosition())
                approaching = getSpeed() < b.getSpeed();
         return closeEnougth && approaching;
     }
     public void updateState(){
         pos_t = pos_tPlusDelta;
         speed_t = speed_tPlusDelta;
         a_tMinusDelta = a_t;
     }
     public void updateView (Graphics2D g) {   // NEW
         view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern
     }
     public boolean contains(double x, double y) {
            return view.contains(x,y);
     }
     public void setSelected(){
            view.setSelected();
     }
     public void setReleased(){
            view.setReleased();
     }
     public void dragTo(double x){
            pos_t=x;
     }

     public String getDescription() {
         return "Ball_" + getId()+":x";
     }
     public String getState() {
         return getPosition()+"";
     }

    public void attachSpring(Spring s)
    {
        this.springs.add(s);
    }

    public void detachSpring(Spring s)
    {
    	this.springs.remove(s);
    }
}