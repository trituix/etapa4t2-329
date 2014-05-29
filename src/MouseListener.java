import javax.swing.JPanel;
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MouseListener extends MouseAdapter {
   private MyWorld world;
   private PhysicsElement currentElement;
   private String letra;
   public MouseListener (MyWorld w){
      world = w;
   }
   public void mouseMoved(MouseEvent e) {
	  Scanner sc = new Scanner(System.in);
	  int contador = 1;
      Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
      PhysicsElement newElement = world.find(p.getX(), p.getY());
      ArrayList<PhysicsElement> allElement = world.findAll(p.getX(), p.getY());
      if (newElement == currentElement) return;
      if (currentElement != null) {
         currentElement.setReleased();
         currentElement = null;
      }
      if (newElement != null) {
         currentElement = newElement;
         currentElement.setSelected();
      }
      /*   if (newElement !=null && allElement.size()>1){
    	  System.out.println ("mas de un objeto junto");
    	   letra = sc.next();
    	  if (letra.equalsIgnoreCase("n") == true){
    	  	if (contador==allElement.size()){
    		  currentElement = allElement.get(contador);
    		  currentElement.setSelected();
    		  contador =1;
    		 }
    		else {
    		currentElement = allElement.get(contador);
    		currentElement.setSelected();
    		contador ++;
    		}
    	  } 
    	  
      } */
      world.repaintView();
   }
   public void mouseDragged(MouseEvent e) {
      Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
      PhysicsElement newElement;
      if(world.isRuning()) return;
      if(currentElement == null) {
          newElement = world.find(p.getX(), p.getY());
          currentElement = newElement;
      }
      if(currentElement != null) {
         currentElement.dragTo(p.getX());
      }
      world.repaintView();
   }
   public void mouseReleased(MouseEvent e) {
      if (currentElement == null) return;
      if (currentElement instanceof Spring) {
         Point2D.Double p= new Point2D.Double(0,0);
         MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);

          // we dragged a spring, so we look for and attachable element near by
         SpringAttachable element = world.findAttachableElement(p.getX());
         if (element != null) {
            // we dragged a spring and it is near an attachable element,
            // so we hook it to a spring end.
            Spring spring = (Spring) currentElement;
            double a=spring.getAendPosition();
            if (element.contains(a, 0)) //a==p.getX()
               spring.attachAend(element);
            double b=spring.getBendPosition();
            if (element.contains(b, 0))//b==p.getX()
               spring.attachBend(element);
          }
      }
      currentElement.setReleased();
      currentElement = null;
      world.repaintView();
   }
}