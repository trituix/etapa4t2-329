import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

 public class KeyHandler implements KeyListener
   {
   	public boolean release;
   	private boolean click;

   	public boolean getClick(){
   		return click;
   	}

   	public boolean setClick(boolean c){
   		return click=c;
   	}
   	public boolean getRelease(){
   		return release;
   	}
      public void keyPressed(KeyEvent event)
      {
    	  char keyChar = event.getKeyChar();


          if (keyChar == 'n')
         	 setClick(true);
          else setClick(false);
      }

      public void keyReleased(KeyEvent event) {
    	  if (getClick())
    		  release = true;
    	  else release = false;
    	  }


      public void keyTyped(KeyEvent event)
      {
         char keyChar = event.getKeyChar();


         if (keyChar == 'n')
        	 setClick(true);
         else setClick(false);
      }
      }