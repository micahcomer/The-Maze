package mazepainter;

import java.awt.Image;
import java.util.ArrayList;

public class GameWorld {
    
    boolean[][] screenMap;
    MazeSection[][] screens;
    ArrayList<Image>images;
    
    
    public GameWorld()
    {
        screenMap = new boolean[100][100];
        screens = new MazeSection[100][100];   
        images = new ArrayList<Image>();
    }
    
    public void addImage(Image i)
    {
        images.add(i);
    }
    public Image getImage(int index)
    {
        return images.get(index);
    }
}
