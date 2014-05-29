import java.awt.event.*;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class LabMenuListener implements ActionListener {
   private MyWorld  world;
   public LabMenuListener (MyWorld  w){
      world = w;
   }
   public void actionPerformed(ActionEvent e) {
      JMenuItem menuItem = (JMenuItem)(e.getSource());
      String text = menuItem.getText();
      Ball b_aux;
      Spring s_aux;
      FixedHook f_aux;
      // Actions associated to main manu options
      if (text.equals("My scenario")) {  // here you define Etapa2's configuration
         world.clean();
         double mass = 1.0;      // 1 [kg]
         double radius = 0.1;    // 10 [cm]
         double position = 0.0;  // 1 [m]
         double speed = 0.5;     // 0.5 [m/s]
         Ball b0 = new Ball(mass, radius, 1, 0);
         Ball b1 = new Ball(mass, radius, 2, 0);
         FixedHook f0 = new FixedHook(0, 0.2);
         Spring s0 = new Spring(1.5, 0.5);
         s0.attachAend(f0);
         s0.attachBend(b0);
         world.addElement(f0);
         world.addElement(s0);
         world.addElement(b0);
         world.addElement(b1);
      }
      if (text.equals("Ball")) {
        b_aux = new Ball(1.0, 0.1, 0, 0);
        world.addElement(b_aux);
      }
      if (text.equals("Fixed Hook")) {
         f_aux = new FixedHook(0, 0.2);
         world.addElement(f_aux);
      }
      if (text.equals("Spring")) {
         s_aux = new Spring(1.5, 0.5);
         world.addElement(s_aux);
      }

      // Actions associated to MyWorld submenu
      if (text.equals("Start")) {
         world.start();
      }
      if (text.equals("Stop")) {
         world.stop();
      }
      if (text.equals("Clear")) {
          world.clean();
       }
      if (text.equals("Delta time")) {
         String data = JOptionPane.showInputDialog("Enter delta t [s]");
         world.setDelta_t(Double.parseDouble(data));
      }
      
      if (text.equals("Refresh time")) {
         String data = JOptionPane.showInputDialog("Enter refresh time [s]");
         world.setRefreshPeriod(Double.parseDouble(data));
      }
      if (text.equals("View Refresh time")) {
         JOptionPane.showMessageDialog(new JFrame("Refresh time"), "Current Refresh time: " + world.getRefreshPeriod() + " [s]");
      }
   }
}