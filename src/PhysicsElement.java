import java.awt.*;

public abstract class PhysicsElement {
   private final int myId; /* to identify each element within its category */

   protected PhysicsElement( int id){
      myId = id;
   }
   protected int getId() {
      return myId;
   }
   public abstract String getDescription();
   public abstract String getState();
   public abstract void updateView(Graphics2D g);
   public abstract boolean contains(double x, double y);
   public abstract void setSelected();
   public abstract void setReleased();
   public abstract void dragTo(double x);
}