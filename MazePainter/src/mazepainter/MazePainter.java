package mazepainter;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class MazePainter {

    public static void main(String[] args) {
        MazePainterMainForm form = new MazePainterMainForm();
        form.init();
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int windowWidth = 1400;
        int windowHeight = 850;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowX = (int) Math.max(0, (screenSize.width  - windowWidth) / 2);
	int windowY = (int) Math.max(0, (screenSize.height - windowHeight) / 2);
                
        form.pack();
	form.setLocation(windowX, windowY);
        form.setVisible(true);
        
    }

}
