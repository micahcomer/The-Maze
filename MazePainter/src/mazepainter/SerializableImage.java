package mazepainter;


import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SerializableImage implements Serializable {

static final long serialVersionUID = 0x17E30096AFA13BE7L;

private int w;
private int h;
private int imageType;
private int[] pixels;

public SerializableImage(

final int w,
final int h,
final int imageType,
final int[] pixels
) {
this.w = w;
this.h = h;
this.imageType = imageType;
this.pixels = pixels;
}

public int getW() {
return w;
}

public int getH() {
return h;
}

public int getImageType() {
return imageType;
}

public int[] getPixels() {
return pixels;
}

public void setPixel(int pixel, Color c)
{
    pixels[pixel]= (byte)c.getRGB();
}

public void writeExternal(ObjectOutput oos)
{
        try {
            oos.writeInt(w);
            oos.writeInt(h);
            oos.writeInt(imageType);
            
            oos.writeInt(pixels.length);
            for (int i=0; i<pixels.length; i++)
            {
                oos.writeInt(pixels[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(SerializableImage.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void readExternal(ObjectInput in)
{
        try {
            w = in.readInt();
            h = in.readInt();
            imageType = in.readInt();
            int count = in.readInt();
            
            int[] p = new int[count];
            for (int i=0; i<count; i++)
            {
                p[i]=in.readInt();
            }
            pixels = p;
        } catch (IOException ex) {
            Logger.getLogger(SerializableImage.class.getName()).log(Level.SEVERE, null, ex);
        }
}

}