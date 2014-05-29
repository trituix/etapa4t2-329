import java.util.*;
import java.awt.*;

public class Ball extends PhysicsElement implements Simulateable, SpringAttachable {
     /**
     * Identificador de Ball
     */
    private static int id=0;  // Ball identification number
     /**
     * Masa de una bola
     */
    private final double mass;
     /**
     * Radio de una bola
     */
    private final double radius;
     /**
     * Posicion de la bola en el tiempo t
     */
    private double pos_t;     // current position at time t
     /**
     * Posicion de la bola en un tiempo futuro
     */
    private double pos_tPlusDelta;  // next position in delta time in future
     /**
     * Velocidad de la bola en el tiempo t
     */
    private double speed_t;   // speed at time t
     /**
     * Velocidad de la bola en un tiempo futuro
     */
    private double speed_tPlusDelta;   // speed in delta time in future
     /**
     * Aceleracion de la bola en un tiempo futuro
     */
    private double a_t;
     /**
     * Aceleracion de la bola en un tiempo pasado
     */
    private double a_tMinusDelta;
     /**
     * Vista que represetnara graficamente a la bola
     */
    private BallView view;  // Ball view of Model-View-Controller design pattern
     /**
     * Conjunto de todos los Springs unidos a la bola
     */
    private ArrayList<Spring> springs;

     /**
     * Constructor por defecto
     */
    private Ball(){   // nobody can create a block without state
         this(1.0,0.1,0,0);
     }
     /**
     * Constructor de Ball que acepta parametros
     * @param mass Masa
     * @param radius Radio
     * @param position Posicion inicial
     * @param speed Velocidad inicial
     */
    public Ball(double mass, double radius, double position, double speed){
            super(id++);
            this.mass = mass;
            this.radius = radius;
            pos_t = position;
            speed_t = speed;
            view = new BallView(this);
            this.springs = new ArrayList<Spring>();
     }
     /**
     * @return masa de la bola
     */
    public double getMass() {
            return mass;
     }
     /**
     * @return radio de la bola
     */
    public double getRadius() {
            return radius;
     }
     /**
     * @return  posicion de la bola
     */
    public double getPosition() {
            return pos_t;
     }
     /**
     * @return velocidad de la bola
     */
    public double getSpeed() {
            return speed_t;
     }

     /**
     * @return fuerza neta de la bola
     */
    private double getNetForce() {
         double sum = 0;
         for (Spring s: springs) {
             sum += s.getForce(this);
         }
         return sum;
     }

     /**
     * Funcion que calcula los siguientes estados de la bola
     * @param  delta_t Delta de tiempo
     * @param  world Mundo en donde esta la bola
     */
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
     /**
     * @param b Posible bola con la que colisiona
     * @return true si hay choque, false si no hay choque
     */
    public boolean collide(Ball b) {
         if (this == b) return false;
         boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getRadius());
         boolean approaching = getSpeed() > b.getSpeed();
         if (b.getPosition() < getPosition())
                approaching = getSpeed() < b.getSpeed();
         return closeEnougth && approaching;
     }
     /**
     * Actualiza los estados actuales de Ball
     */
    public void updateState(){
         pos_t = pos_tPlusDelta;
         speed_t = speed_tPlusDelta;
         a_tMinusDelta = a_t;
     }
     /**
     * Llama a su vista para que esta se actualice
     * @param g Graphics2D
     */
    public void updateView (Graphics2D g) {   // NEW
         view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern
     }
     /**
     * @param x Coordenada x del punto
     * @param y  Coordenada y del punto
     * @return  true si contiene el punto, false si no
     */
    public boolean contains(double x, double y) {
            return view.contains(x,y);
     }
     /**
     * Llama a su vista para que quede seleccionada
     */
    public void setSelected(){
            view.setSelected();
     }
     /**
     * Llama a su vista para que no quede selecionada
     */
    public void setReleased(){
            view.setReleased();
     }
     /**
     * Cambio de la posicion de la bola a un punto x
     * @param  x Punto x donde se posocionara la bola
     */
    public void dragTo(double x){
            pos_t=x;
     }

     /**
     * @return Descripcion de la bola con su identificador
     */
    public String getDescription() {
         return "Ball_" + getId()+":x";
     }
     /**
     * @return Posicion de la bola
     */
    public String getState() {
         return getPosition()+"";
     }

    /**
     * Funcion que une la bola con un Spring
     */
    public void attachSpring(Spring s)
    {
        this.springs.add(s);
    }

    /**
     * Funcion que separa la bola de un Spring
     */
    public void detachSpring(Spring s)
    {
    	this.springs.remove(s);
    }
}