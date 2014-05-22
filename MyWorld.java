import java.util.*;
import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;

public class MyWorld implements ActionListener {
    private PrintStream out;

    private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
    private MyWorldView view;   // NEW
    private Timer passingTime;   // NEW
    private double t;        // simulation time
    private double delta_t;        // in seconds
    private double refreshPeriod;  // in seconds

    public MyWorld(){
        this(System.out);  // delta_t= 0.1[ms] and refreshPeriod=200 [ms]
    }
    public MyWorld(PrintStream output){
        out = output;
        t = 0;
        refreshPeriod = 0.0083;      // 60 [ms]
        delta_t = 0.00001;          // 0.01 [ms]
        elements = new ArrayList<PhysicsElement>();
        view = null;
        passingTime = new Timer((int)(refreshPeriod*1000), this);
    }

    public void addElement(PhysicsElement e) {
        elements.add(e);
        view.repaintView();
    }
    public void setView(MyWorldView view) {
        this.view = view;
    }
    public void setDelta_t(double delta) {
        delta_t = delta;
    }
    public void setRefreshPeriod (double rp) {
        refreshPeriod = rp;
        passingTime.setDelay((int)(refreshPeriod*1000)); // convert from [s] to [ms]
    }

    public double getRefreshPeriod() {
        return this.refreshPeriod;
    }

    public void start() {
        if(passingTime.isRunning()) return;
        passingTime.start();
    }
    public void stop(){
        if(!passingTime.isRunning()) return;
        passingTime.stop();
    }

    public boolean isRuning() {
        if(passingTime.isRunning()) {
            return true;
        }
        else {
            return false;
        }
    }

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

    public void repaintView(){
        view.repaintView();
    }

    public Ball findCollidingBall(Ball me) {
        for (PhysicsElement e: elements)
            if ( e instanceof Ball) {
                Ball b = (Ball) e;
                if ((b!=me) && b.collide(me)) return b;
            }
        return null;
    }

    public ArrayList<PhysicsElement> getPhysicsElements(){
        return elements;
    }

    public PhysicsElement find(double x, double y) {
        for (PhysicsElement e: elements)
            if (e.contains(x,y)) return e;
        return null;
    }

    public void clean() {
        elements.clear();
        view.repaintView();
    }

    public SpringAttachable findAttachableElement(double x)
    {
        for (PhysicsElement e: elements) {
            if(e instanceof SpringAttachable && ((SpringAttachable) e).getPosition() == x) {
                return (SpringAttachable) e;
            }
        }
        return null;
    }
}