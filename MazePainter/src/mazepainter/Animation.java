package mazepainter;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;


public final class Animation implements Externalizable{
    
    public String name;
    
    public SerializableImage spriteStrip;
    public SerializableImage collisionStrip;
    public CollisionType collisionType;
    public Point numberOfCells;
    public Point cellDimension;
    public Point currentCell;
    
    public boolean repeats;
    public boolean terminates;
    public boolean pauseThenRepeat;
    
    public int pauseDelay;
    public boolean visible =true;
  
    public int layer;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Animation other = (Animation) obj;
        if (!Objects.equals(this.spriteStrip, other.spriteStrip)) {
            return false;
        }
        if (!Objects.equals(this.collisionStrip, other.collisionStrip)) {
            return false;
        }
        if (!Objects.equals(this.numberOfCells, other.numberOfCells)) {
            return false;
        }
        if (this.repeats != other.repeats) {
            return false;
        }
        if (this.terminates != other.terminates) {
            return false;
        }
        if (this.visible != other.visible) {
            return false;
        }
        if (this.layer != other.layer) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.spriteStrip);
        hash = 53 * hash + Objects.hashCode(this.collisionStrip);
        hash = 53 * hash + Objects.hashCode(this.numberOfCells);
        hash = 53 * hash + (this.repeats ? 1 : 0);
        hash = 53 * hash + (this.terminates ? 1 : 0);
        hash = 53 * hash + (this.visible ? 1 : 0);
        hash = 53 * hash + this.layer;
        return hash;
    }
    
    public void setCollisionType(CollisionType t)
    {
        collisionType = t;
    }
    
    public Animation(){
        setCollisionType(CollisionType.Solid);
    }
    public Animation(BufferedImage i, Point numberOfCells, boolean repeats, boolean terminates, String name){
        LoadSpriteStrip(i, numberOfCells);
        this.numberOfCells = numberOfCells;
        this.repeats = repeats;
        this.terminates = terminates;
        visible = true;
        setCollisionType(CollisionType.Solid);        
        this.name = name;
    }
    
    public void LoadSpriteStrip(BufferedImage i, Point numberOfCells){
       
        int[] pixArray = new int[i.getWidth()*i.getHeight()];
        int j = 0;
        for(int y=0; y<i.getHeight(); y++){
            for(int x=0; x<i.getWidth(); x++){
                pixArray[j]=i.getRGB(x, y);
                j++;
            }            
        }
        
        spriteStrip = new SerializableImage(i.getWidth(), i.getHeight(), i.getType(), pixArray);
        cellDimension = new Point(
               (int)(spriteStrip.getW()/numberOfCells.x),
               (int)(spriteStrip.getH()/numberOfCells.y)
               );
    }
    
    public void LoadCollisionStrip(SerializableImage i){
        collisionStrip = i;
    }
    
    public BufferedImage getFirstCell(){
        BufferedImage frontCell = new BufferedImage(cellDimension.x,cellDimension.y, BufferedImage.TYPE_INT_ARGB); 
        int[] pixArray = new int[spriteStrip.getPixels().length];
        for (int i=0; i<spriteStrip.getPixels().length; i++){
            pixArray[i] = (int)spriteStrip.getPixels()[i];
        }
        frontCell.setRGB(0, 0, cellDimension.x, cellDimension.y, pixArray, 0, cellDimension.x);
        return frontCell;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        
        out.writeInt(numberOfCells.x);
        out.writeInt(numberOfCells.y);
        out.writeInt(cellDimension.x);
        out.writeInt(cellDimension.y);        
        out.writeBoolean(repeats);
        out.writeBoolean(terminates);
        out.writeBoolean(visible);        
        out.writeInt(layer);   
        out.writeInt(name.length());
        for (int i=0; i<name.length(); i++)
        {
            out.writeChar(name.charAt(i));
        }
        
         if (spriteStrip!=null){spriteStrip.writeExternal(out);}
        out.writeBoolean(collisionStrip!=null);
        if (collisionStrip!=null){collisionStrip.writeExternal(out);}
        
        writeCollisionType(out);
        
        out.writeBoolean(pauseThenRepeat);
        out.writeInt(pauseDelay);
    }
    
    private void writeCollisionType(ObjectOutput out) throws IOException{
        int val = -1;
        switch(collisionType)
        {
            case Solid:
            {
                val =0;
                break;
            }
            case Event:
            {
                val =1;
                break;
            }            
            case Key:
            {
                val =2;
                break;
            }
            case Door:
            {
                val=3;
                break;
            }
            case Item:
            {
                val =4;
                break;
            }
            case Enemy:
            {
                val =5;
                break;
            }
            case Death:
            {
                val =6;
                break;
            }
        }
        out.writeInt(val);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        
        numberOfCells = new Point();
        numberOfCells.x = in.readInt();
        numberOfCells.y = in.readInt();
        
        cellDimension = new Point();
        cellDimension.x = in.readInt(); 
        cellDimension.y = in.readInt();
        
        repeats = in.readBoolean();
        terminates = in.readBoolean();
        visible = in.readBoolean();
        
        layer = in.readInt();
        
        int count = in.readInt();
        char[] c = new char[count];
        for (int i=0; i<count;i++){
            c[i] = in.readChar();
        }
        name = new String(c);
        
        if (spriteStrip == null){
            spriteStrip = new SerializableImage(0, 0, 0, null);
        }
        spriteStrip.readExternal(in);
        
        boolean isThereCollisionStrip = in.readBoolean();        
        if (isThereCollisionStrip){
        if (collisionStrip == null){
            collisionStrip = new SerializableImage(0, 0, 0, null);
        }
        collisionStrip.readExternal(in);
        }                
        readCollisionType(in);    
        
        pauseThenRepeat = in.readBoolean();
        pauseDelay = in.readInt();
    }
    
    private void readCollisionType(ObjectInput in) throws IOException
    {
        int val = in.readInt();
        
        switch(val)
        {
            case 0:
            {
                collisionType = CollisionType.Solid;
                break;
            }
            case 1:
            {                
                collisionType = CollisionType.Event;
                break;
            }            
            case 2:
            {
                collisionType = CollisionType.Key;
                break;
            }
            case 3:
            {
                collisionType = CollisionType.Door;
                break;
            }
            case 4:
            {
                collisionType = CollisionType.Item;
                break;
            }
            case 5:
            {
                collisionType = CollisionType.Enemy;
                break;
            }
            case 6:
            {
                collisionType = CollisionType.Death;
                break;
            }
        }
        
    }
    
    
    
}
