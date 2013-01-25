/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

/**
 *
 * @author Micah
 */
public class DirectionPack implements Externalizable{
    public boolean Up;
    public boolean Down;
    public boolean Left;
    public boolean Right;
    
    public Key key;
    public Door door;
    public ArrayList<Item> items;
    public ArrayList<Enemy> enemies;
    
    public Theme theme;
    
    public String info;
    
    public DirectionPack(Theme theme)
    {
        Up = false;
        Down = false;
        Left = false;
        Right = false;
        
        items = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        
        this.theme = theme;
        
    }
    
    public void Clear()
    {
        Up = false;
        Down = false;
        Left = false;
        Right = false;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(Up);
        out.writeBoolean(Down);
        out.writeBoolean(Left);
        out.writeBoolean(Right);        
        
        theme.writeExternal(out);
        
        if (key==null)
        {
            out.writeBoolean(false);
        }
        else
        {
            out.writeBoolean(true);
            key.writeExternal(out);
        }        
        
        
        if (door==null)
        {
            out.writeBoolean(false);
        }
        else
        {
            out.writeBoolean(true);
            door.writeExternal(out);
        }       
        
        if (items==null)
        {
            out.writeBoolean(false);
        }
        else
        {
            out.writeBoolean(true);
            out.writeObject(items);
        }
                
        if (enemies==null)
        {
            out.writeBoolean(false);
        }
        else
        {
            out.writeBoolean(true);
            out.writeObject(enemies);
        }
        
        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        
        Up = in.readBoolean();
        Down = in.readBoolean();
        Left = in.readBoolean();
        Right = in.readBoolean();        
        
        Theme t = new Theme();
        t.readExternal(in);
        theme =t;
        
        boolean hasKey = in.readBoolean();
        if (hasKey)
        {
            Key k = new Key();
            k.readExternal(in);
            key = k;
        }
        
        boolean hasDoor = in.readBoolean();
        if (hasDoor)
        {
            Door d = new Door();
            d.readExternal(in);
            door = d;
        }
        
       boolean hasItems = in.readBoolean();
       if (hasItems)
       {
           ArrayList<mazemaker.Item> iList = (ArrayList<mazemaker.Item>)in.readObject();
           for (int i=0; i<iList.size(); i++)
           {
               Item it = new Item();
               it.id = iList.get(i).id;
               it.name = iList.get(i).name;
               items.add(it);
           }
       }
        
       boolean hasEnemies = in.readBoolean();
       if (hasEnemies)
       {
           ArrayList<mazemaker.Enemy> eList = (ArrayList<mazemaker.Enemy>)in.readObject();
           for (int i=0; i<eList.size(); i++)
           {
               Enemy e = new Enemy();
               e.id = eList.get(i).id;
               e.name = eList.get(i).name;
               enemies.add(e);
           }
       }
        
    }
    
}
