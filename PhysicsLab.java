import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;

public class PhysicsLab {
   public static void main(String[] args) {
      PhysicsLab_GUI lab_gui = new PhysicsLab_GUI();
      lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      lab_gui.setVisible(true);
   }
}

class PhysicsLab_GUI extends JFrame {
   public PhysicsLab_GUI() {
      setTitle("My Small and Nice Physics Laboratory");
      setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+100);  // height+50 to account for menu height
      MyWorld world = new MyWorld();
      MyWorldView  worldView = new MyWorldView(world);
      world.setView(worldView);
      LabMenuListener menuListener = new LabMenuListener(world);
      JMenuBar menuBar = createLabMenuBar(menuListener);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.add(menuBar, BorderLayout.PAGE_START);
      panel.add(worldView, BorderLayout.CENTER);
      add(panel);
   }

   public JMenuBar createLabMenuBar(LabMenuListener menu_l) {
      JMenuBar mb = new JMenuBar();

      JMenu menu = new JMenu ("Configuration");
      mb.add(menu);
      JMenu subMenu = new JMenu("Insert");
      menu.add(subMenu);

      JMenuItem menuItem = new JMenuItem("Ball");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Fixed Hook");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Spring");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("My scenario");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);

      menu = new JMenu("MyWorld");
      mb.add(menu);
      menuItem = new JMenuItem("Start");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
      menuItem = new JMenuItem("Stop");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
      subMenu = new JMenu("Simulator");
      menuItem = new JMenuItem("Refresh time");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("View Refresh time");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Delta time");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menu.add(subMenu);
      return mb;
   }
}