/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Micah
 */
public class BrushPreviewPanel extends javax.swing.JPanel implements MouseInputListener{

    Image image = new BufferedImage(40,40,BufferedImage.TYPE_INT_ARGB);
    
    public void setNewBrush(Image newBrush)
    {
        if (newBrush!=null)
        {
            image = newBrush;
            Graphics g = image.getGraphics();
            g.drawImage(image, 40, 40, this);
            g.dispose();
            repaint();
        }
    }
    
     @Override
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
