package mazepainter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class MazeSection implements Externalizable{
    
    public int[][] animationIndex;    
    ArrayList<Animation> animations;
    
    public void addAnimation(Animation a)
    {
        if (!animations.contains(a)){
            animations.add(a);}
    }
    
    public MazeSection()
    {
        animationIndex = new int[24][18];          
        animations = new ArrayList<Animation>();        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {           
        animationIndex = (int[][])in.readObject();
        int count = in.readInt();
        for (int i=0; i<count; i++)
        {
            Animation a = new Animation();
            a.readExternal(in);
            addAnimation(a);
        }
    
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {               
        out.writeObject(animationIndex);        
        out.writeInt(animations.size());
        for (int i=0; i<animations.size(); i++){
            animations.get(i).writeExternal(out);
        }            
    }
    
}
