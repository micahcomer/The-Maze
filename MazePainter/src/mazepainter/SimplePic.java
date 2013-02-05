/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Micah
 */
public class SimplePic implements Serializable{
    
    public int Height;
    public int Width;    
    public byte[] Pixels;
    
    public void setImage(BufferedImage img){
        Height = img.getHeight();
        Width = img.getWidth();                
        Pixels = new byte[Height*Width*4];
        
        Color c;
        int r, g, b, a;
        int i=0;
        
        for (int y=0; y<Height; y++){
            for (int x=0; x<Width; x++){
                c = new Color(img.getRGB(x, y));
                r = c.getRed();
                g = c.getGreen();
                b = c.getBlue();
                a = c.getAlpha();
            if (y==3 && x==0){
                y=y;
            }    
                Pixels[i]=(byte)(r-128);
                i++;
                Pixels[i]=(byte)(g-128);
                i++;
                Pixels[i]=(byte)(b-128);
                i++;
                Pixels[i]=(byte)(a-128);
                i++;                
            }
        }
        
        i=0;
    }
        
        

    public Pixmap getPixmap(){        
        return new Pixmap(Pixels, 0, Pixels.length);              
    }
    
    public Image getImage(){
        BufferedImage i = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        int j=0;
        
        Color c;
        for (int y=0; y<Height; y++){
            for (int x=0; x<Width; x++){                   
                c = new Color(Pixels[j]+128, Pixels[j+1]+128, Pixels[j+2]+128, Pixels[j+3]+128);
                i.setRGB(x, y, c.getRGB());
                j+=4;
            }
        }
        
        return i;
    }
    
    public SimplePic getRegion(int x, int y, int width, int height){
        SimplePic p = new SimplePic();
        
        p.Width = width;
        p.Height = height;
        p.Pixels = new byte[p.Height*p.Width*4];
        
        for (int i=0; i<p.Pixels.length; i++){
            if (i==40){
                i=40;
            }
            p.Pixels[i] = Pixels[countPixels(i, x, y, width, height)];
        }
                
        return p;
    }
    
    private int countPixels(int i, int x, int y,int h, int w){
        //Start at x,y, and count i pixels forward, jumping to a new row at the end of w
        int hCount = x;
        int vCount = y;
        
        for (int c=0; c<i; c++){
            hCount++;
            if (hCount>=x+w){
                hCount=0;
                vCount++;
            }
        }
        return vCount*w+hCount;
        
        
    }
    
    public void writeExternal(ObjectOutputStream oos){
        try {
            oos.writeInt(Width);
            oos.writeInt(Height);
            for (int i=0; i<Pixels.length; i++){
                oos.writeByte(Pixels[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(SimplePic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readExternal(ObjectInputStream in){
        try {
            Width = in.readInt();
            Height = in.readInt();            
            for (int p=0; p<Height*Width*4; p++){                
                Pixels[p]=in.readByte();                
            }
        } catch (IOException ex) {
            Logger.getLogger(SimplePic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Texture getTexture(){
        return new Texture(getPixmap());
    }
    }