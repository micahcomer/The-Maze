/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Micah
 */
public class Door implements Externalizable{    
    public int id;
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
    }
}
