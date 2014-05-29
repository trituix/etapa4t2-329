import java.util.*;
import java.io.*;

import javax.swing.Timer;

import java.awt.event.*;

public class MyWorld implements ActionListener {
    /**
     * Salida a pantalla
     */
    private PrintStream out;

    /**
     * Conjunto de elementos fisicos
     */
    private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
    /**
     * Vista que representara graficamente a este mundo
     */
    private MyWorldView view;   // NEW
    /**
     * 
     */
    private Timer passingTime;   // NEW
    /**
     * Tiempo de simulacion
     */
    private double t;        // simulation time
    /**
     * Delta tiempo
     */
    private double delta_t;        // in seconds
    /**
     * Periodo de actualizacion de la pantalla
     */
    private double refreshPeriod;  // in seconds

    /**
     * Constructor por defecto
     */
    public MyWorld(){
        this(System.out);  // delta_t= 0.1[ms] and refreshPeriod=200 [ms]
    }
    /**
     * Constructor que acepta parametros
     * @param output Printstream
     */
    public MyWorld(PrintStream output){
        out = output;
        t = 0;
        refreshPeriod = 0.0083;      // 60 [ms]
        delta_t = 0.00001;          // 0.01 [ms]
        elements = new ArrayList<PhysicsElement>();
        view = null;
        passingTime = new Timer((int)(refreshPeriod*1000), this);
    }

    /**
     * @param e se añade a conjunto de elementos fisicos
     */
    public void addElement(PhysicsElement e) {
        elements.add(e);
        view.repaintView();
    }
    /**
     * @param view vista que representara graficamente al mundo
     */
    public void setView(MyWorldView view) {
        this.view = view;
    }
    /**
     * @param delta Delta de tiempo para la simulacion
     */
    public void setDelta_t(double delta) {
        delta_t = delta;
    }
    /**
     * @param rp Periodo de actualizacion de la pantalla
     */
    public void setRefreshPeriod (double rp) {
        refreshPeriod = rp;
        passingTime.setDelay((int)(refreshPeriod*1000)); // convert from [s] to [ms]
    }

    /**
     * @return Periodo de refresco de la pantalla
     */
    public double getRefreshPeriod() {
        return this.refreshPeriod;
    }

    /**
     * Comienza la simulacion
     */
    public void start() {
        if(passingTime.isRunning()) return;
        passingTime.start();
    }
    /**
     * Detiene la simulacion
     */
    public void stop(){
        if(!passingTime.isRunning()) return;
        passingTime.stop();
    }

    /**
     * @return true si la simulacion esta corriendo, false en otro caso
     */
    public boolean isRuning() {
        if(passingTime.isRunning()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param  event Evento
     */
    public void actionPerformed (ActionEvent event) {
        double nextStop=t+refreshPeriod;                // the arguments are attributes here.
        for (; t<nextStop; t+=delta_t){
            for (PhysicsElement e: elements) {
                if (e instanceof Simulateable) {
                    Simulateable s = (Simulateable) e;
                    s.computeNextState(delta_t,this); // compute each element next state based on current global state
                }
            }
            for (PhysicsElement e: elements) { // for each element update its state.
                if (e instanceof Simulateable) {
                    Simulateable s = (Simulateable) e;
                    s.updateState();            // update its state
                }
            }
        }
        this.repaintView();
    }

    /**
     * Llama a su vista para que se vuelva a dibujar
     */
    public void repaintView(){
        view.repaintView();
    }

    /**
     * @param me Bola con la cual podrian colisionar las otras bolas
     * @return la bola con la que colisiona
     */
    public Ball findCollidingBall(Ball me) {
        for (PhysicsElement e: elements)
            if ( e instanceof Ball) {
                Ball b = (Ball) e;
                if ((b!=me) && b.collide(me)) return b;
            }
        return null;
    }
    public boolean key(){
    	KeyHandler key = new KeyHandler();
    	return key.getRelease();
    }
    
 
    /**
     * @return Conjunto de elementos fisicos
     */
    public ArrayList<PhysicsElement> getPhysicsElements(){
        return elements;
    }

    /**
     * @param x Coordenada x
     * @param y Coordenada y
     * @return true si algun elemento fisico del mundo contiene al punto (x, y), false en otro caso
     */
    public PhysicsElement find(double x, double y) {
        for (PhysicsElement e: elements)
            if (e.contains(x,y)) return e;
        return null;
    }
    
    /**
     * @param x Coordenada x
     * @param y Coordenada y
     * @return Conjunto de elementos que contienen al punto (x, y)
     */
    public  ArrayList<PhysicsElement> findAll(double x, double y) {
    	ArrayList<PhysicsElement> elementscontains;
    	elementscontains =new ArrayList<PhysicsElement>();
        for (PhysicsElement e: elements){
            if (e.contains(x,y)){
            	elementscontains.add(e);
            }
        }
        return elementscontains;
    }

    /**
     * Limpia el mundo de todos sus elementos
     */
    public void clean() {
        elements.clear();
        view.repaintView();
    }

    /**
     * @param x coordenada x
     * @return Elemento fisico al cual se le puede unir un Spring
     */
    public SpringAttachable findAttachableElement(double x)
    {
        for (PhysicsElement e: elements) {
            if((!(e instanceof Spring)) && e.contains(x,0))  {
                return (SpringAttachable) e;
            }
        }
        return null;
    }
}