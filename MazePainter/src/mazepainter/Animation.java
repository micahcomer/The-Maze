/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazepainter;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

public class Animation implements Serializable{
    public SimplePic[][] frames;
    public Point cells;
    public String name;
    
    public Animation(){
        cells = new Point(1,1);        
    }
    
    public final void setFrames(SimplePic spriteStrip, Point cells){
        frames = new SimplePic[cells.x][cells.y];
        Point cellDim = new Point(spriteStrip.Width/cells.x, spriteStrip.Height/cells.y);
        for (int y=0; y<cells.y; y++){
            for (int x=0; x<cells.x; x++){
                frames[x][y]=spriteStrip.getRegion(x*cellDim.x, y*cellDim.y, cellDim.x, cellDim.y);
            }
        }
    }
    
    public Animation(SimplePic spriteStrip, Point cells){
        this.cells = cells;
        setFrames(spriteStrip, cells);
    }
    
    public SimplePic getFrame(Point frame){
        return frames[frame.x][frame.y];
    }
    
    @Override
    public boolean equals (Object o){
        
        if (o instanceof Animation){
        return name.equals(((Animation)o).name);
    }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

}
