package mazepainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;

public class PaintPanel extends javax.swing.JPanel implements MouseInputListener{

    BufferedImage image = new BufferedImage(960,720, BufferedImage.TYPE_INT_ARGB);
    public BufferedImage brush = new BufferedImage(40,40, BufferedImage.TYPE_INT_ARGB);
    
    
    Point firstPointOfSelection;
    Point lastPointOfSelection;
    
    Point selectedPoint;
    DirectionPack currentTile;
    public boolean showOverlay =true;
    
    ArrayList<Animation> animationsOnScreen;
    public ArrayList<Animation> getBrushes()
    {
        return animationsOnScreen;
    }
    Animation currentBrush;
    public int brushSize=1;
    
    Color overlayColor;
    Color backgroundColor;
    Color overlayTest;
    
    Point onScreenCursorPosition;    
    public int gridDim;
    boolean showCursor = false;        
    
    float blockW;
    float blockH;
    
    int[][] animationIndex;    
    int[][] overlayIndex;
    
    int OVERLAY_FILLED = -1;
    int OVERLAY_EMPTY =-2;
    
    public int currentMode = MazePainterMainForm.MODE_DRAW;
    public Image selection;
    
    public PaintPanel() {
        initComponents();
        animationIndex = new int[24][18];
        overlayIndex = new int[24][18];
        overlayColor = Color.DARK_GRAY;
        backgroundColor = Color.BLUE;
        overlayTest = Color.GREEN;   
        animationsOnScreen = new ArrayList<Animation>();
    }


    public MazeSection getScreen()
    {
        MazeSection s = new MazeSection();
        s.animations = animationsOnScreen;
        s.animationIndex = animationIndex;        
        return s;
    }
    
    public void addImageToScreen(Animation i)
    {
        if (animationsOnScreen==null)
        {
            animationsOnScreen=new ArrayList<Animation>();
        }
        if(!animationsOnScreen.contains(i)){
        animationsOnScreen.add(i);}
        currentBrush = i;
    }
    
    private void setImageInIndex(int x, int y, int picID)
    {
        animationIndex[x][y] = picID;        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(960, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void setCurrentTile(DirectionPack p)
    {
        currentTile = p;
        if (showOverlay)
        {
            updateOverlay();
        }
    }
    
     public void picArrayInit()
    {
        for (int y=0; y<18; y++)
        {
            for (int x=0; x<24; x++)
            {                
                animationIndex[x][y]=-1;
            }
        }
    }
    
    public void initialize()
    {
        registerEvents();
        gridDim = 5;
        picArrayInit();
        onScreenCursorPosition = new Point();  
        animationsOnScreen = new ArrayList<Animation>();
        drawGridArea();
    }
    
    private void registerEvents()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void clearAll()
    {
    }
    
    @Override
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }

    }
    
    
    public void drawGridArea()
    {
        
        Graphics g = image.getGraphics();
        g.setColor(Color.blue);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.black);
        
        if (showOverlay)
        {
            drawOverlay(g);
        }
        
        drawPicArray(g);
        drawGridLines(g);
        
        if (showCursor)
        {           
           g.setColor(Color.WHITE);
           g.fillRect((int)(onScreenCursorPosition.x*blockW), (int)(onScreenCursorPosition.y*blockH), (int)blockW, (int)blockH);  
        }
        
        if ((currentMode==MazePainterMainForm.MODE_SELECT)&&(firstPointOfSelection!=null)&&(lastPointOfSelection!=null))
        {
            int startY = Math.min(firstPointOfSelection.y, lastPointOfSelection.y);
            int startX = Math.min(firstPointOfSelection.x, lastPointOfSelection.x);
            int endY = Math.max(firstPointOfSelection.y, lastPointOfSelection.y);
            int endX = Math.max(firstPointOfSelection.x, lastPointOfSelection.x);
            
            g.drawImage(selection, startX*40, startY*40, (((endX+1)*40)-(startX*40)),(((endY+1)*40)-(startY*40)),this);
            
            
        }
        
        g.dispose();
        
        repaint();
    }
    
    private void drawGridLines(Graphics g)
    {
        
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        
        int horizontalLineCount = 18;
        int verticalLineCount = 24;
        
        blockW = ((float)image.getWidth()/(float)verticalLineCount);
        blockH = ((float)image.getHeight()/(float)horizontalLineCount);
        
        //Draw vertical lines.
        for (int i=0; i<verticalLineCount; i++)
        {
            g.drawLine((int)(i*blockW), 0, (int)(i*blockW), image.getHeight());
            
        }
        
        //Draw horizontal lines
        for (int i=0; i<horizontalLineCount; i++)
        {
            g.drawLine(0, (int)(i*blockH), image.getWidth(), (int)(i*blockH));
        }
        
        g.setColor(oldColor);
    }

    public void updateOverlay()
    {
        if (currentTile!=null)
        {
            resetOverlay();
            updateOverlayCorners();
            updateOverlaySides();  
            
            drawGridArea();        
        }
    }
    
    private void resetOverlay()
    {
        for (int y=0; y<18; y++)
        {
            for (int x=0; x<24; x++)
            {
                overlayIndex[x][y]=OVERLAY_EMPTY;
            }
        }
    }
    
    private void updateOverlaySides()
    {
        if (currentTile.Up)
        {
            for (int x=8; x<16; x++)
            {
                for (int y=0; y<6; y++)
                {
                    overlayIndex[x][y] = OVERLAY_EMPTY;
                }
            }
        }
        else
        {
            for (int x=8; x<16; x++)
            {
                for (int y=0; y<6; y++)
                {
                    overlayIndex[x][y] = OVERLAY_FILLED;
                }
            }
        }
        ////////////////////////////////
        if (currentTile.Down)
        {
            for (int x=8; x<16; x++)
            {
                for (int y=12; y<18; y++)
                {
                    overlayIndex[x][y] = OVERLAY_EMPTY;
                }
            }
        }
        else
        {
             for (int x=8; x<16; x++)
            {
                for (int y=12; y<18; y++)
                {
                    overlayIndex[x][y] = OVERLAY_FILLED;
                }
            }
        }
        /////////////////////////////////
        if (currentTile.Left)
        {
            for (int x=0; x<8; x++)
            {
                for (int y=6; y<12; y++)
                {
                    overlayIndex[x][y]=OVERLAY_EMPTY;
                }
            }
        }
        else
        {
            for (int x=0; x<8; x++)
            {
                for (int y=6; y<12; y++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
        }
        
        ///////////////////////////////
        if (currentTile.Right)
        {
            for (int x=16; x<24; x++)
            {
                for (int y=6; y<12; y++)
                {
                    overlayIndex[x][y]=OVERLAY_EMPTY;
                }
            }
        }
        else
        {
            for (int x=16; x<24; x++)
            {
                for (int y=6; y<12; y++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
        }
        
    }
    
    private void updateOverlayCorners()
    {
            for (int y=0; y<6; y++)
            {
                for (int x=0; x<8; x++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
            
            for (int y=0; y<6; y++)
            {
                for (int x=16; x<24; x++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
            
            for (int y=12; y<18; y++)
            {
                for (int x=0; x<8; x++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
            
            for (int y=12; y<18; y++)
            {
                for (int x=16; x<24; x++)
                {
                    overlayIndex[x][y]=OVERLAY_FILLED;
                }
            }
    }
    
    public void drawOverlay(Graphics g)
    {
        for (int y=0; y<overlayIndex[0].length; y++)
        {
            for (int x=0; x<overlayIndex.length; x++)
            {
                drawSingleOverlay(g, x, y);
            }
        }
    }
    
    private void drawSingleOverlay(Graphics g, int x, int y)
    {
        if (overlayIndex[x][y]==OVERLAY_EMPTY)
        {

            g.setColor(backgroundColor);
            g.fillRect(x*40, y*40, 40, 40);
        }
        else
        {
            if (overlayIndex[x][y]==OVERLAY_FILLED)
            {
                g.setColor(overlayColor);
                g.fillRect(x*40, y*40, 40, 40);
            }
        }
    }
    
    public void drawPicArray(Graphics g)
    {
        for (int y=0; y<animationIndex[0].length; y++)
        {
            for (int x=0; x<animationIndex.length; x++)
            {
                drawSinglePic(g, x, y);
            }
        }
    }
    
    private void drawSinglePic(Graphics g, int x, int y)
    {       
        if (animationIndex[x][y]>=0)
        {
            g.drawImage(
                    animationsOnScreen.get(animationIndex[Math.min(x,23)][Math.min(y,17)]).getFirstCell(), 
                    x*40, 
                    y*40, 
                    animationsOnScreen.get(animationIndex[Math.min(x,23)][Math.min(y,17)]).getFirstCell().getWidth(this), 
                    animationsOnScreen.get(animationIndex[x][y]).getFirstCell().getHeight(this), 
                    this
                    );
        }
    }
    
    public void loadScreen(MazeSection screen)
    {
        
        this.animationIndex = screen.animationIndex;
        this.animationsOnScreen.clear();
        this.animationsOnScreen = screen.animations;
        
        if (animationsOnScreen.contains(currentBrush))
        {
            currentBrush = animationsOnScreen.get(animationsOnScreen.indexOf(currentBrush));
        }
        else
        {
            if (animationsOnScreen.size()>0)
            {
            currentBrush = animationsOnScreen.get(0);
            }
        }
        
        drawGridArea();
    }
    
    public void AutoFill()
    {
        AutoFillCorners(animationsOnScreen.indexOf(currentBrush));
        AutoFillSides(animationsOnScreen.indexOf(currentBrush));
        drawGridArea();
    }
    
    private void AutoFillCorners(int picID)
    {
        for (int y=0; y<6; y++)
            {
                for (int x=0; x<8; x++)
                {
                    if ((overlayIndex[x][y]==OVERLAY_FILLED)&&(animationIndex[x][y]<0))
                    {
                    animationIndex[x][y]=picID;
                    }
                }
            }
            
            for (int y=0; y<6; y++)
            {
                for (int x=16; x<24; x++)
                {
                    if ((overlayIndex[x][y]==OVERLAY_FILLED)&&(animationIndex[x][y]<0))
                    {
                    animationIndex[x][y]=picID;
                    }
                }
            }
            
            for (int y=12; y<18; y++)
            {
                for (int x=0; x<8; x++)
                {
                    if ((overlayIndex[x][y]==OVERLAY_FILLED)&&(animationIndex[x][y]<0))
                    {
                    animationIndex[x][y]=picID;
                    }
                }
            }
            
            for (int y=12; y<18; y++)
            {
                for (int x=16; x<24; x++)
                {
                    if ((overlayIndex[x][y]==OVERLAY_FILLED)&&(animationIndex[x][y]<0))
                    {
                    animationIndex[x][y]=picID;
                    }
                }
            }
    }
    
    private void AutoFillSides(int picID)
    {
        if (currentTile!=null)
        {
        if (currentTile.Up)
        {
            for (int x=8; x<16; x++)
            {
                for (int y=0; y<6; y++)
                {           
                    animationIndex[x][y] = -2;
                }
            }
        }
        else
        {
            for (int x=8; x<16; x++)
            {
                for (int y=0; y<6; y++)
                {
                    if (overlayIndex[x][y]==OVERLAY_FILLED)
                    {
                        animationIndex[x][y] = picID;
                    }
                }
            }
        }
        ////////////////////////////////
        if (currentTile.Down)
        {
            for (int x=8; x<16; x++)
            {
                for (int y=12; y<18; y++)
                {
                    animationIndex[x][y] = -2;
                }
            }
        }
        else
        {
             for (int x=8; x<16; x++)
            {
                for (int y=12; y<18; y++)
                {
                    if (overlayIndex[x][y]==OVERLAY_FILLED)
                    {
                        animationIndex[x][y] = picID;
                    }
                }
            }
        }
        /////////////////////////////////
        if (currentTile.Left)
        {
            for (int x=0; x<8; x++)
            {
                for (int y=6; y<12; y++)
                {
                    animationIndex[x][y] = -2;
                }
            }
        }
        else
        {
            for (int x=0; x<8; x++)
            {
                for (int y=6; y<12; y++)
                {
                    if (overlayIndex[x][y]==OVERLAY_FILLED)
                    {
                        animationIndex[x][y] = picID;
                    }
                }
            }
        }
        
        ///////////////////////////////
        if (currentTile.Right)
        {
            for (int x=16; x<24; x++)
            {
                for (int y=6; y<12; y++)
                {
                    animationIndex[x][y] = -2;
                }
            }
        }
        else
        {
            for (int x=16; x<24; x++)
            {
                for (int y=6; y<12; y++)
                {
                    if (overlayIndex[x][y]==OVERLAY_FILLED)
                    {
                        animationIndex[x][y] = picID;
                    }
                }
            }
        }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
       int x = (int)(e.getX()/blockW);
       int y = (int)(e.getY()/blockH);
       
       if (currentMode == MazePainterMainForm.MODE_DRAW)
       {
       for (int i=0; i<brushSize; i++)
            {
                for (int j=0; j<brushSize; j++)
                {
                    setImageInIndex(Math.min(x+i, 23),Math.min(y+j, 17),animationsOnScreen.indexOf(currentBrush));
                }
            }
       drawGridArea();
       }
       else
       {
           if (currentMode == MazePainterMainForm.MODE_ERASE)
           {
               for (int i=0; i<brushSize; i++)
            {
                for (int j=0; j<brushSize; j++)
                {
                    setImageInIndex(Math.min(x+i, 23),Math.min(y+j, 17),-2);
                }
            }
       drawGridArea();
           }
           else
           {
               if (currentMode==MazePainterMainForm.MODE_SELECT)
               {
                   if (firstPointOfSelection ==null)
                   {
                       firstPointOfSelection = new Point();                       
                   }
                   if (lastPointOfSelection==null)
                   {
                       lastPointOfSelection = new Point();
                   }
                   
                   firstPointOfSelection.x = onScreenCursorPosition.x;                   
                   firstPointOfSelection.y = onScreenCursorPosition.y;    
                   lastPointOfSelection.x = onScreenCursorPosition.x;
                   lastPointOfSelection.y = onScreenCursorPosition.y;
                           
                   drawGridArea();
               }
           }
       }
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (currentMode == MazePainterMainForm.MODE_SELECT)
        {
            if (lastPointOfSelection == null)
            {
                lastPointOfSelection = new Point();
            }
            lastPointOfSelection.x = onScreenCursorPosition.x;
            lastPointOfSelection.y = onScreenCursorPosition.y;
            drawGridArea();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        showCursor = true;
        drawGridArea();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        showCursor = false;
        drawGridArea();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       int x = (int)(e.getX()/blockW);
       int y = (int)(e.getY()/blockH);
       
       if ((x!= onScreenCursorPosition.x)||(y!=onScreenCursorPosition.y))
       {
            onScreenCursorPosition.setLocation(x, y); 
            if (currentMode == MazePainterMainForm.MODE_DRAW)
            {
                for (int i=0; i<brushSize; i++)
                {
                    for (int j=0; j<brushSize; j++)
                    {
                        setImageInIndex(Math.min(Math.max(0,x+i), 23),Math.min(Math.max(y+j, 0), 17),animationsOnScreen.indexOf(currentBrush));
                    }
            }
                drawGridArea();
            }
            else
            {
                if (currentMode == MazePainterMainForm.MODE_ERASE)
                {
                    for (int i=0; i<brushSize; i++)
                    {
                        for (int j=0; j<brushSize; j++)
                        {
                            setImageInIndex(Math.min(Math.max(0,x+i), 23),Math.min(Math.max(y+j, 0), 17),-2);
                        }
                    }
                drawGridArea();
                }
                else
                {
                    if ((currentMode==MazePainterMainForm.MODE_SELECT)&&(firstPointOfSelection!=null)&&(lastPointOfSelection!=null))
                    {
                        lastPointOfSelection.x = onScreenCursorPosition.x;
                        lastPointOfSelection.y = onScreenCursorPosition.y;
                        drawGridArea();
                    }
                }
                }
            }
       }
    


    @Override
    public void mouseMoved(MouseEvent e) 
    {
        
        int x = (int)(e.getX()/blockW);
        int y = (int)(e.getY()/blockH);
        
        if ((x!= onScreenCursorPosition.x)||(y!=onScreenCursorPosition.y))
        {
            onScreenCursorPosition.setLocation(x, y);  
            drawGridArea();
        }
    }


    


}
