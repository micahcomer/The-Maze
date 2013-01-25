/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import java.awt.Color;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Micah
 */
public class Theme implements Externalizable{
    public Color color;
    public int id;
    public String name;    

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);        
        out.writeObject(name);
        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();        
        name = (String) in.readObject();
        
        
        
    }
}
