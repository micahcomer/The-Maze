package mazepainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JScrollBar;
import javax.swing.event.MouseInputListener;



public class MazeWorldMapPanel extends javax.swing.JPanel implements MouseInputListener, AdjustmentListener{

    MazePainterMainForm mainForm;
    
    boolean newLoadedMaze = true;
    
    Point currentPosition;
    Point cursorPosition;
    Point selectedPositionOnGrid;
    Point selectedPositionInMaze;
    
    int offsetX;
    int offsetY;
    
    DirectionPack[][] tiles;
    BufferedImage image;
    BufferedImage cursorPic;
    BufferedImage selectedSquarePic;
    boolean showCursor = false;
    
    static Image updownleftright;    
    static Image updownleft;
    static Image updownright;
    static Image downleftright;
    static Image upleftright;    
    static Image updown;
    static Image leftright;
    static Image upleft;
    static Image upright;
    static Image downleft;
    static Image downright;    
    static Image up;
    static Image down;
    static Image left;
    static Image right;
    
    JScrollBar vertScroll;
    JScrollBar horizScroll;
    
    public MazeWorldMapPanel(){
        tiles = new DirectionPack[100][100];
        currentPosition = new Point();
        cursorPosition = new Point();
        
        image = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);       
        loadCursor();
        loadTilePics();
        drawGrid();        
    }    
    
    public void setMainForm(MazePainterMainForm form){
        mainForm = form;
        mainForm.getPaintPanel().selection = cursorPic;            
    }
    
    public void setTiles(DirectionPack[][] newTiles){
        tiles = newTiles;
        setSelectedPositionInMaze(0, 0);
        
    }
    
    public void setScrollBars(JScrollBar vert, JScrollBar horiz){
        vertScroll = vert;
        horizScroll = horiz;
        vertScroll.addAdjustmentListener(this);
        horizScroll.addAdjustmentListener(this);               
    }
    
    private void loadCursor(){
        try {
            String path = new java.io.File("").getAbsolutePath();
            File f = new File(path + "\\selection.png");
            File s = new File(path+"\\selected.png");
            cursorPic = ImageIO.read(f);
            selectedSquarePic = ImageIO.read(s);
            
            
        } catch (IOException ex) {
            Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTilePics(){
        String path = new java.io.File("").getAbsolutePath();
        
        File udlr = new File(path + "\\updownleftright.png");
        
        File udr = new File(path + "\\updownright.png");
        File udl = new File(path + "\\updownleft.png");
        File dlr = new File (path + "\\downleftright.png");
        File ulr = new File(path + "\\upleftright.png");
        
        File ud = new File (path + "\\updown.png");
        File lr = new File (path + "\\leftright.png");
        
        File ul = new File (path + "\\upleft.png");
        File ur = new File (path + "\\upright.png");
        File dl = new File (path + "\\downleft.png");
        File dr = new File (path + "\\downright.png");
        
        File u = new File (path + "\\up.png");
        File d = new File (path + "\\down.png");
        File l = new File (path + "\\left.png");
        File r = new File (path + "\\right.png");
        
        File x = new File (path + "\\null.png");
        File k = new File (path + "\\key.png");
        File df = new File (path +"\\door.png");
        File sel = new File (path+"\\selection.png");
        
        File it = new File(path+"\\item.png");
        File en = new File(path+"\\enemy.png");
        try 
        {
            updownleftright = ImageIO.read(udlr);
            updownleft = ImageIO.read(udl);
            updownright = ImageIO.read(udr);
            downleftright = ImageIO.read(dlr);
            upleftright = ImageIO.read(ulr);
            updown = ImageIO.read(ud);
            leftright = ImageIO.read(lr);
            upleft = ImageIO.read(ul);
            upright = ImageIO.read(ur);
            downleft = ImageIO.read(dl);
            downright = ImageIO.read(dr);
            up = ImageIO.read(u);
            down = ImageIO.read(d);
            left = ImageIO.read(l);
            right = ImageIO.read(r);
    }
        catch (IOException ex) {    
            Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void drawGrid(){
        Graphics g = image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,200,200);
        g.setColor(Color.BLACK);
        for (int i=0; i<10; i++)
        {
            g.drawLine(0, i*20, 200, i*20);
            g.drawLine(i*20, 0, i*20, 200);
        }
        
        drawTiles(g);
        
        if (showCursor)
        {            
            drawCursor(g);            
        }
        
        if (selectedPositionOnGrid!=null)
        {
            drawSelectedSquare(g);
        }
        
        g.dispose();
        repaint();
    }
    
    private void drawSelectedSquare(Graphics g){        
        g.drawImage(selectedSquarePic, selectedPositionOnGrid.x*20, selectedPositionOnGrid.y*20, 20, 20, this);
    }
    
    private void drawCursor(Graphics g){
        g.drawImage(cursorPic, cursorPosition.x*20, cursorPosition.y*20, 20, 20, this);
    }
    
    private void drawTiles(Graphics g){
       for (int y=currentPosition.y; y<Math.min(cursorPosition.y+20, 100); y++)
       {
           for (int x=currentPosition.x; x<Math.min(cursorPosition.x+20, 100); x++)
           {              
               if (tiles[x][y]!=null)
               {
                    drawTile(g,x,y);
               }
           }
       }
    }
    
    private void drawTile(Graphics g, int x, int y){
        DirectionPack p = tiles[x][y];
        
        if (p.Up)
        {
            if (p.Down)
            {
                if (p.Left)
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(updownleftright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(updownleft, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
                else
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(updownright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(updown, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
            }
            else
            {
                if (p.Left)
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(upleftright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(upleft, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
                else
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(upright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(up, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
            }
            
            
        }
        else
        {
            if (p.Down)
            {
                if (p.Left)
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(downleftright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(downleft, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
                else
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(downright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(down, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
            }
            else
            {
                if (p.Left)
                {
                    if (p.Right)
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(leftright, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                    else
                    {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(left, x*20+offsetX, y*20+offsetY, 20, 20, this);
                    }
                }
                else
                {
                        g.setColor(tiles[x][y].theme.color);
                        g.fillRect(x*20+offsetX, y*20+offsetY, 20, 20);
                        g.drawImage(right, x*20+offsetX, y*20+offsetY, 20, 20, this);
                }
            }
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
        
        if (selectedPositionOnGrid==null)
        {
            selectedPositionOnGrid = new Point();
        }
        
        
        if (!selectedPositionOnGrid.equals(cursorPosition))
        {
        selectedPositionOnGrid.x = cursorPosition.x;
        selectedPositionOnGrid.y = cursorPosition.y;
        
        if (selectedPositionInMaze==null)
        {
            selectedPositionInMaze=new Point();
        }
        
        setSelectedPositionInMaze(selectedPositionOnGrid.x - (offsetX/20), selectedPositionOnGrid.y - (offsetY/20));                        
        }
        
    }
    
    public void setSelectedPositionInMaze(int x, int y){        
        
        if (selectedPositionOnGrid==null)
        {
            selectedPositionOnGrid = new Point();
        }
        
         if (selectedPositionInMaze==null)
        {
            selectedPositionInMaze = new Point();
        }
        
        if (!newLoadedMaze)
        {
            SaveScreen();
        }
        else
        {
            newLoadedMaze = false;
        }
        
        selectedPositionInMaze.x = x;
        selectedPositionInMaze.y = y;
        
        mainForm.getPaintPanel().currentTile = tiles[x][y];        
        mainForm.getPaintPanel().updateOverlay();
        mainForm.getPaintPanel().picArrayInit();
        this.mainForm.LoadScreen();
        mainForm.updateTextArea(getTextInfo(selectedPositionInMaze));
        mainForm.getPaintPanel().drawGridArea();
        drawGrid();
    }

    private String getTextInfo(Point coord){
        String msg;
        DirectionPack p = tiles[coord.x][coord.y];
        if (p==null)
        {
            msg = "Tile is null.";
        }
        else
        {
            msg = "Current tile: " + String.valueOf(coord.x)+" - "+String.valueOf(coord.y);      
            msg+= "\nTile theme: "+ p.theme.name;
            
            if (p.key!=null)
            {
                msg+= "\nKey " + String.valueOf(p.key.id)+ " located in this tile.";
            }
            
            if (p.door!=null)
            {
                msg+= "\nDoor " + String.valueOf(p.door.id)+" located in this tile.";
            }
            
            if ((p.items!=null)&&(p.items.size()>0))
            {
                msg+= "\nItems:";
                for (int i=0; i<p.items.size(); i++)
                {
                    msg+="\nItem "+String.valueOf(i)+": "+p.items.get(i).name;
                }
            }
            else
            {
                msg+="\nNo items.";
            }
            
            if ((p.enemies!=null)&&(p.enemies.size()>0))
            {
                msg+="\nEnemies:";
                for (int i=0; i<p.enemies.size(); i++)
                {
                    Enemy e = p.enemies.get(i);
                    msg+="\nEnemy "+String.valueOf(i)+": "+ e.name;
                }                
            }
            else
            {
                msg+="\nNo enemies.";
            }
        }
        
        return msg;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        showCursor=true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        showCursor=false;
        drawGrid();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX()/20;
        int y = e.getY()/20;
        
        if ((x!=cursorPosition.x)||(y!=cursorPosition.y))
        {
            cursorPosition.x = x;
            cursorPosition.y = y;
            drawGrid();
        }
        
        
        
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        int val = e.getValue();
        float perc = (float)val/100f;
        
        if (e.getSource()==vertScroll)
        {
            offsetY = -(int)(2000*perc);
        }
        else
        {
            offsetX = -(int)(2000*perc);
        }
        
        
        
        if (selectedPositionOnGrid!=null)
        {

            selectedPositionOnGrid.x = selectedPositionInMaze.x+(offsetX/20);
            selectedPositionOnGrid.y = selectedPositionInMaze.y+(offsetY/20);
        }
        
        drawGrid();
        
    }
    
    public void SaveScreen(){
        if (!mainForm.currentFileName.equals(""))
        {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            String path = mainForm.saveFilePath;
             path += "\\"+ "x"+String.valueOf(selectedPositionInMaze.x) + "x"+ String.valueOf(selectedPositionInMaze.y)+".dat";
            File f = new File(path);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            mainForm.getPaintPanel().getScreen().writeExternal(oos);
        } catch (IOException ex) {
            Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

   
    
    public void MoveUp(){
        if (selectedPositionInMaze.y>0)
        {
            selectedPositionOnGrid.y = selectedPositionInMaze.y-1;
            setSelectedPositionInMaze(selectedPositionInMaze.x, selectedPositionInMaze.y-1);            
            
            drawGrid();
        }
    }
   
    public void MoveDown(){
        if (selectedPositionInMaze.y<tiles[0].length)
        {
            selectedPositionOnGrid.y = selectedPositionInMaze.y+1;
            setSelectedPositionInMaze(selectedPositionInMaze.x, selectedPositionInMaze.y+1);
            
            drawGrid();
        }
    }
    
    public void MoveLeft(){
         if (selectedPositionInMaze.x>0)
        {
            selectedPositionOnGrid.x = selectedPositionInMaze.x-1;
            setSelectedPositionInMaze(selectedPositionInMaze.x-1, selectedPositionInMaze.y);
            
            drawGrid();
        }
    }
    
    public void MoveRight(){
        if (selectedPositionInMaze.y<tiles.length)
        {
            selectedPositionOnGrid.x = selectedPositionInMaze.x+1;
            setSelectedPositionInMaze(selectedPositionInMaze.x+1, selectedPositionInMaze.y);
            
            drawGrid();
        }
    }
}