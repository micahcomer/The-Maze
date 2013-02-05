package mazepainter;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import sun.rmi.runtime.Log;

public class MazePainterMainForm extends javax.swing.JFrame {

    DirectionPack[][] tiles;    
    public String currentFileName;
    public String saveFilePath;
    Action leftAction;
    Action rightAction;
    Action upAction;
    Action downAction;
    public MazeSection currentSection;
    ArrayList<Animation> loadedBrushes;
    public static final int MODE_DRAW = 0;
    public static final int MODE_ERASE = 1;
    public static final int MODE_SELECT = 2;
    int currentMode = MODE_DRAW;

    public MazePainterMainForm() {
        initComponents();
        
        paintPanel1.initialize();
        mazeWorldMapPanel1.addMouseListener(mazeWorldMapPanel1);
        mazeWorldMapPanel1.addMouseMotionListener(mazeWorldMapPanel1);

        mazeWorldMapPanel1.setMainForm(this);
        mazeWorldMapPanel1.setScrollBars(jScrollBar1, jScrollBar2);
        loadedBrushes = new ArrayList<Animation>();
        if (!new java.io.File("Saved Screens").exists()) {
            new java.io.File("Saved Screens").mkdir();
        }
        saveFilePath = new java.io.File("").getAbsolutePath() + "\\Saved Screens";
        jLabel9.setText(saveFilePath);
        currentFileName = "";

    }

    public PaintPanel getPaintPanel() {
        return paintPanel1;
    }

    public void init() {
        bindKeys();
        paintPanel1.setMainForm(this);
    }

    private void bindKeys() {
        leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveLeft();
            }
        };

        rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveRight();
            }
        };

        upAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveUp();
            }
        };

        downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveDown();
            }
        };

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;

        rootPane.getInputMap(condition).put(KeyStroke.getKeyStroke("LEFT"), "Left");
        rootPane.getActionMap().put("Left", leftAction);

        rootPane.getInputMap(condition).put(KeyStroke.getKeyStroke("RIGHT"), "Right");
        rootPane.getActionMap().put("Right", rightAction);

        rootPane.getInputMap(condition).put(KeyStroke.getKeyStroke("UP"), "Up");
        rootPane.getActionMap().put("Up", upAction);

        rootPane.getInputMap(condition).put(KeyStroke.getKeyStroke("DOWN"), "Down");
        rootPane.getActionMap().put("Down", downAction);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        paintPanel1 = new mazepainter.PaintPanel();
        jPanel1 = new javax.swing.JPanel();
        brushPreviewPanel1 = new mazepainter.BrushPreviewPanel();
        jLabel1 = new javax.swing.JLabel();
        button_loadBrush = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        button_loadNewMaze = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollBar2 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        mazeWorldMapPanel1 = new mazepainter.MazeWorldMapPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tileInfoTextArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        button_autoFill = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        loadedBrushList = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        button_changeDefaultDirectory = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Maze Painter");
        setPreferredSize(new java.awt.Dimension(1400, 850));

        paintPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout paintPanel1Layout = new javax.swing.GroupLayout(paintPanel1);
        paintPanel1.setLayout(paintPanel1Layout);
        paintPanel1Layout.setHorizontalGroup(
            paintPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 958, Short.MAX_VALUE)
        );
        paintPanel1Layout.setVerticalGroup(
            paintPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 718, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        brushPreviewPanel1.setBackground(new java.awt.Color(255, 255, 255));
        brushPreviewPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        brushPreviewPanel1.setPreferredSize(new java.awt.Dimension(40, 40));

        javax.swing.GroupLayout brushPreviewPanel1Layout = new javax.swing.GroupLayout(brushPreviewPanel1);
        brushPreviewPanel1.setLayout(brushPreviewPanel1Layout);
        brushPreviewPanel1Layout.setHorizontalGroup(
            brushPreviewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        brushPreviewPanel1Layout.setVerticalGroup(
            brushPreviewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Current Brush:");

        button_loadBrush.setText("Load Brush");
        button_loadBrush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_loadBrushActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Brush Size");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1", "2", "3", "4" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setAutoscrolls(false);
        jList1.setSelectedIndex(0);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel5.setText("No Brush Loaded");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(brushPreviewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(button_loadBrush))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(brushPreviewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_loadBrush))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        button_loadNewMaze.setText("Load New Maze");
        button_loadNewMaze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_loadNewMazeActionPerformed(evt);
            }
        });

        jScrollBar2.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        jScrollBar2.setEnabled(false);

        javax.swing.GroupLayout mazeWorldMapPanel1Layout = new javax.swing.GroupLayout(mazeWorldMapPanel1);
        mazeWorldMapPanel1.setLayout(mazeWorldMapPanel1Layout);
        mazeWorldMapPanel1Layout.setHorizontalGroup(
            mazeWorldMapPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        mazeWorldMapPanel1Layout.setVerticalGroup(
            mazeWorldMapPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(mazeWorldMapPanel1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("WORLD MAP:");

        jScrollBar1.setEnabled(false);

        tileInfoTextArea.setEditable(false);
        tileInfoTextArea.setColumns(20);
        tileInfoTextArea.setRows(5);
        tileInfoTextArea.setText("No tiles loaded.");
        jScrollPane3.setViewportView(tileInfoTextArea);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Current Tile Info:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jScrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Show Overlay");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        button_autoFill.setText("AutoFill");
        button_autoFill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_autoFillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_autoFill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_autoFill)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Loaded Brushes:");

        loadedBrushList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "None loaded" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        loadedBrushList.setEnabled(false);
        loadedBrushList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                loadedBrushListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(loadedBrushList);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Editor Mode:");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Erase Mode");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Select Mode");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Draw Mode");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        button_changeDefaultDirectory.setText("Change Default Directory");
        button_changeDefaultDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_changeDefaultDirectoryActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Current Working Directory:");

        jLabel9.setText("jLabel9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(paintPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(button_loadNewMaze)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button_changeDefaultDirectory))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paintPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_loadNewMaze)
                    .addComponent(button_changeDefaultDirectory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private CollisionType showCollisionChoiceDialog()
    {
        
        Object[] possibilities = {"Solid", "Item", "Event", "Enemy", "Key", "Door", "Death"};
        String s = (String)JOptionPane.showInputDialog(this,
                    "Please select the collision type for this brush.", "Please Choose",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "Solid");
            //If a string was returned, say so.
            
            switch (s)
            {
                case "Solid":
                {return CollisionType.Solid;}
                case "Item":
                {return CollisionType.Item;}
                case "Event":
                {return CollisionType.Event;}
                case "Enemy":
                {return CollisionType.Enemy;}
                case "Key":
                {return CollisionType.Key;}
                case "Door":
                {return CollisionType.Door;}
                case "Death":
                {return CollisionType.Death;}
                default:
                {return CollisionType.Solid;}
            }
    }
    
    private void button_loadBrushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_loadBrushActionPerformed
        
            JFileChooser chooser = new JFileChooser();
            int val = chooser.showOpenDialog(jLabel1);
            if (val == JFileChooser.APPROVE_OPTION) {
            try {
                File brushFile = chooser.getSelectedFile();                
                BufferedImage img = ImageIO.read(brushFile);
                SimplePic s = new SimplePic();
                s.setImage(img);
                String val3 = JOptionPane.showInputDialog("Please enter the number of rows in this animation strip.");
                int rows = Integer.parseInt(val3);
                String val4 = JOptionPane.showInputDialog("Please enter the number of columns in this animation strip.");
                int columns = Integer.parseInt(val4);
                
                Animation a = new Animation(s, new Point(columns, rows));                
                a.name = brushFile.getName();                
                paintPanel1.currentBrush = a;
                paintPanel1.addImageToScreen(a);
                brushPreviewPanel1.setNewBrush(a.getFrame(new Point(0,0)).getImage());
                if (!loadedBrushes.contains(a)){
                loadedBrushes.add(a);
                }
                jLabel5.setVisible(false);
                updateBrushList();
                            
                
                
            } catch (IOException ex) {
                Logger.getLogger(MazePainterMainForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "You did not enter a valid number of rows.  Please try again.");
                    }
                
            }
    }//GEN-LAST:event_button_loadBrushActionPerformed

    public void updateBrushList(){

        //Enable the list if needed...
        if (!loadedBrushList.isEnabled()){
            loadedBrushList.setEnabled(true);
            }
                
        //Create a new model for the list.
        DefaultListModel listModel = new DefaultListModel();
            
        //Add all of the currently loaded brushes back into the new list.
        for (int j=0; j<loadedBrushes.size(); j++){
            listModel.addElement(loadedBrushes.get(j).name);
        }
                
        //Remove any duplicate items.
        listModel = removeDuplicates(listModel);
        
        //Assign the new model to the loadedBrushList control.
        loadedBrushList.setModel(listModel);
        
        
        if (loadedBrushList.getSelectedIndex()>=0){
            brushPreviewPanel1.setNewBrush(loadedBrushes.get(loadedBrushList.getSelectedIndex()).getFrame(new Point(0,0)).getImage());
            paintPanel1.addImageToScreen(loadedBrushes.get(loadedBrushList.getSelectedIndex()));
        }else{
            brushPreviewPanel1.setNewBrush(loadedBrushes.get(0).getFrame(new Point(0,0)).getImage());
            paintPanel1.addImageToScreen(loadedBrushes.get(0));
        }
            
    }
    
    private DefaultListModel removeDuplicates(DefaultListModel list){
        
        if (list.size()>1){
            
            boolean [] remove = new boolean[list.getSize()];
            
            for (int i=0; i<list.getSize()-1; i++)
            {
                for (int j=i+1; j<list.getSize(); j++){
                    if (list.get(i).equals(list.get(j))){
                        remove[j]=true;
                    }
                    else
                    {
                        remove[j]=false;
                    }
                }
            }
            
            for (int k=list.getSize()-1; k>=0; k--){
            if (remove[k]){
                list.remove(k);
            }       
        }
        
        
        }
        
        return list;
        
    }
    
    /*
    
    private void old_loadbrushActionMethod(java.awt.event.ActionEvent evt){
        
                boolean pauseThenLoop = false;
        int pauseDelay=0;
        try {            
            
            JFileChooser chooser = new JFileChooser();
            int val = chooser.showOpenDialog(jLabel1);
            if (val == JFileChooser.APPROVE_OPTION) {
                File brushFile = chooser.getSelectedFile();
                int val2 = JOptionPane.showConfirmDialog(this, "Is this brush an animation?");
                if (val2 == JOptionPane.YES_OPTION) {
                    try {
                        String val3 = JOptionPane.showInputDialog("Please enter the number of rows in this animation strip.");
                        int rows = Integer.parseInt(val3);
                        String val4 = JOptionPane.showInputDialog("Please enter the number of columns in this animation strip.");
                        int columns = Integer.parseInt(val4);
                        int val5 = JOptionPane.showConfirmDialog(this, "Should this animation loop repeatedly?");
                        if (val5 != JOptionPane.CANCEL_OPTION) {
                            boolean repeats;
                            boolean terminates;
                            if (val5 == JOptionPane.YES_OPTION) {
                                repeats = true;
                            } else {
                                repeats = false;
                            }
                            int val6 = JOptionPane.showConfirmDialog(this, "Should this animation disappear after playing all the way through?");
                            if (val6 != JOptionPane.CANCEL_OPTION) {
                                if (val6 == JOptionPane.YES_OPTION) {
                                    terminates = true;
                                } else {
                                    terminates = false;
                                }
                                
                                int val7 = JOptionPane.showConfirmDialog(this, "Should this animation delay before looping again?");
                                
                                if (val7 != JOptionPane.CANCEL_OPTION){                                                                        
                                    if (val7 == JOptionPane.YES_OPTION){
                                        pauseThenLoop = true;                                        
                                        String val8 = JOptionPane.showInputDialog("Please enter the number of seconds to pause this animation before looping.");{
                                        try{                                            
                                            pauseDelay = Integer.parseInt(val8);
                                        }
                                        catch(NumberFormatException e){
                                            pauseDelay = 0;
                                        }
                                     
                                    }
                                    }else{
                                        pauseThenLoop = false;
                                    }
                                    
                                }
                            
                                
                                CollisionType type = showCollisionChoiceDialog();
                                
                                BufferedImage bi = ImageIO.read(brushFile);
                                Animation a = new Animation(bi, new Point(columns, rows), repeats, terminates, brushFile.getName());
                                a.pauseThenRepeat = pauseThenLoop;
                                a.pauseDelay = pauseDelay;
                                a.setCollisionType(type);
                                a.LoadCollisionStrip(createCollisionMap(a.spriteStrip, type));
                                paintPanel1.currentBrush = a;
                                paintPanel1.addImageToScreen(a);
                                brushPreviewPanel1.setNewBrush(a.getFirstCell());
                                jLabel5.setVisible(false);
                                currentBrushes.add(a);
                                
                                if (!loadedBrushList.isEnabled()){
                                    loadedBrushList.setEnabled(true);
                                }
                                DefaultListModel listModel = new DefaultListModel();
                            for (int i = 0; i < loadedBrushList.getModel().getSize(); i++) {
                                listModel.addElement(loadedBrushList.getModel().getElementAt(i));
                            }
                            listModel.addElement(brushFile.getName());
                            loadedBrushList.setModel(listModel);
                            loadedBrushList.setSelectedIndex(loadedBrushList.getModel().getSize() - 1);
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "You did not enter a valid number of rows.  Please try again.");
                    }
                }
                //If the brush is NOT an animation...
                else {

                    BufferedImage newBrushImage = ImageIO.read(brushFile);
                    if (newBrushImage == null) {
                        String msg = "Not a valid graphics file.  Please try again.";
                        JOptionPane.showMessageDialog(paintPanel1, msg);
                    } else {                        
                        Animation a = new Animation(newBrushImage, new Point(1, 1), true, false, brushFile.getName());
                        CollisionType type = showCollisionChoiceDialog();                        
                        a.setCollisionType(type);
                        a.LoadCollisionStrip(createCollisionMap(a.spriteStrip, type));
                        paintPanel1.currentBrush = a;
                        paintPanel1.addImageToScreen(a);
                        brushPreviewPanel1.setNewBrush(a.getFirstCell());
                        jLabel5.setVisible(false);
                        currentBrushes.add(a);
                        if (!loadedBrushList.isEnabled()) {
                            loadedBrushList.setEnabled(true);
                            DefaultListModel listModel = new DefaultListModel();
                            listModel.addElement(brushFile.getName());
                            loadedBrushList.setModel(listModel);
                            loadedBrushList.setSelectedIndex(loadedBrushList.getModel().getSize() - 1);
                        } else {
                            DefaultListModel listModel = new DefaultListModel();
                            for (int i = 0; i < loadedBrushList.getModel().getSize(); i++) {
                                listModel.addElement(loadedBrushList.getModel().getElementAt(i));
                            }
                            listModel.addElement(brushFile.getName());
                            loadedBrushList.setModel(listModel);
                            loadedBrushList.setSelectedIndex(loadedBrushList.getModel().getSize() - 1);
                        }
                    }
                }
            }
        } catch (IOException ex) {
        
        
        }
        
    }
    
    */
    
    private SerializableImage createCollisionMap(SerializableImage si, CollisionType type) {
        int[] collisionColors = new int[si.getPixels().length];
        System.arraycopy(si.getPixels(), 0, collisionColors, 0, si.getPixels().length);
        
        SerializableImage collisionImage = new SerializableImage(si.getW(), si.getH(), si.getImageType(), collisionColors);
        Color c;
        boolean[] pixelsToColor = new boolean[collisionImage.getPixels().length];

        switch (type) {
            case Solid: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = false;
                    } else {
                        pixelsToColor[i] = true;
                    }
                }
                for (int j = 0; j < pixelsToColor.length; j++) {
                    if (pixelsToColor[j]) {
                        collisionImage.setPixel(j, Color.BLACK);
                    }
                }
                break;
            }


            case Enemy: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.GREEN);
                        }
                    }
                }
                break;
            }
            case Death: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.RED);
                        }
                    }
                }
                break;
            }
            case Key: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.YELLOW);
                        }
                    }
                }
                break;
            }
            case Door: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.MAGENTA);
                        }
                    }
                }
                break;
            }
            case Item: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.BLUE);
                        }
                    }
                }
                break;
            }
            case Event: {
                for (int i = 0; i < collisionImage.getPixels().length; i++) {
                    c = new Color(collisionImage.getPixels()[i], true);
                    if (c.getAlpha() < 128) {
                        pixelsToColor[i] = true;
                    } else {
                        pixelsToColor[i] = false;
                    }
                    for (int j = 0; j < pixelsToColor.length; j++) {
                        if (pixelsToColor[j]) {
                            collisionImage.setPixel(j, Color.WHITE);
                        }
                    }
                }
                break;
            }
        }
        return collisionImage;
    }

    private void button_loadNewMazeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_loadNewMazeActionPerformed
        JFileChooser chooser = new JFileChooser();
        int val = chooser.showOpenDialog(jLabel1);
        if (val == JFileChooser.APPROVE_OPTION) {
            File tileFile = chooser.getSelectedFile();
            LoadNewMaze(tileFile);
        }
    }//GEN-LAST:event_button_loadNewMazeActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

        paintPanel1.showOverlay = jCheckBox1.isSelected();
        if (jCheckBox1.isSelected()) {
            paintPanel1.updateOverlay();
        }
        paintPanel1.drawGridArea();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void button_autoFillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_autoFillActionPerformed
        paintPanel1.AutoFill();
    }//GEN-LAST:event_button_autoFillActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        paintPanel1.brushSize = Integer.valueOf((String) jList1.getSelectedValue());
    }//GEN-LAST:event_jList1ValueChanged

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        if (jRadioButton1.isSelected()) {
            currentMode = MODE_DRAW;
            paintPanel1.currentMode = MODE_DRAW;
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        if (jRadioButton2.isSelected()) {
            currentMode = MODE_ERASE;
            paintPanel1.currentMode = MODE_ERASE;
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        if (jRadioButton3.isSelected()) {
            currentMode = MODE_SELECT;
            paintPanel1.currentMode = MODE_SELECT;
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void loadedBrushListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_loadedBrushListValueChanged

        if ((loadedBrushes.size()>loadedBrushList.getSelectedIndex()&& (loadedBrushes.size()>0))){
            try{
                if (loadedBrushList.getSelectedIndex()>=0){
                    paintPanel1.addImageToScreen(loadedBrushes.get(loadedBrushList.getSelectedIndex()));
                    brushPreviewPanel1.setNewBrush(loadedBrushes.get(loadedBrushList.getSelectedIndex()).getFrame(new Point(0,0)).getImage());
                }
                else{
                    paintPanel1.addImageToScreen(loadedBrushes.get(0));
                    brushPreviewPanel1.setNewBrush(loadedBrushes.get(0).getFrame(new Point(0,0)).getImage());
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                Log.getLog(e.getMessage(), e.toString(), true);
            }
        }
        
    }//GEN-LAST:event_loadedBrushListValueChanged

    private void button_changeDefaultDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_changeDefaultDirectoryActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int val = chooser.showOpenDialog(jLabel1);
        if (val == JFileChooser.APPROVE_OPTION) {
            File newDir = chooser.getSelectedFile();
            saveFilePath = newDir.getAbsolutePath();
            jLabel9.setText(saveFilePath);
        }
    }//GEN-LAST:event_button_changeDefaultDirectoryActionPerformed

    public void updateTextArea(String msg) {
        tileInfoTextArea.setText(msg);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MazePainterMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MazePainterMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MazePainterMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MazePainterMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MazePainterMainForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mazepainter.BrushPreviewPanel brushPreviewPanel1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton button_autoFill;
    private javax.swing.JButton button_changeDefaultDirectory;
    private javax.swing.JButton button_loadBrush;
    private javax.swing.JButton button_loadNewMaze;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList loadedBrushList;
    private mazepainter.MazeWorldMapPanel mazeWorldMapPanel1;
    private mazepainter.PaintPanel paintPanel1;
    private javax.swing.JTextArea tileInfoTextArea;
    // End of variables declaration//GEN-END:variables

    private void MoveLeft() {
        mazeWorldMapPanel1.MoveLeft();
    }

    private void MoveRight() {
        mazeWorldMapPanel1.MoveRight();
    }

    private void MoveUp() {
        mazeWorldMapPanel1.MoveUp();
    }

    private void MoveDown() {
        mazeWorldMapPanel1.MoveDown();
    }

    public void LoadNewMaze(File file) {

        
        //Load a boolean array to determine where the maze has directionPack tiles.
        boolean[][] hasFileMap = new boolean[100][100];
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            DirectionPack[][] newTiles = new DirectionPack[100][100];
            for (int y = 0; y < 100; y++) {
                for (int x = 0; x < 100; x++) {
                    hasFileMap[x][y] = ois.readBoolean();
                }
            }

            //For every "true" value in the boolean array, load the corresponding directionPack.
            for (int y = 0; y < 100; y++) {
                for (int x = 0; x < 100; x++) {
                    if (hasFileMap[x][y]) {
                        newTiles[x][y] = new DirectionPack(new Theme());
                        newTiles[x][y].readExternal(ois);
                    }
                }
            }
            //ois.close();
            //fis.close();

            //Set the label to reflect the new maze name.
            currentFileName = file.getName();
            currentFileName = currentFileName.replace(".dat", "");            
            jLabel3.setText("WORLD MAP: " + currentFileName);

            //Set the info in the mazeWorldMapPanel
            tiles = newTiles;
            mazeWorldMapPanel1.setTiles(newTiles);
            mazeWorldMapPanel1.drawGrid();
            jScrollBar1.setEnabled(true);
            jScrollBar2.setEnabled(true);



        } catch (ClassNotFoundException ex) {
            String msg = "Not a valid maze file.  Please try again.";
            JOptionPane.showMessageDialog(this, msg);
        } catch (IOException ex) {
            String msg = "Not a valid maze file.  Please try again.";
            JOptionPane.showMessageDialog(this, msg);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                String msg = "Not a valid maze file.  Please try again.";
                JOptionPane.showMessageDialog(this, msg);                
            }
            catch (NullPointerException ex){
                
            }
            try {
                fis.close();
            } catch (IOException ex) {
                String msg = "Not a valid maze file.  Please try again.";
                JOptionPane.showMessageDialog(this, msg);
            }
        }
        

    }

    
    
     public void LoadScreen(){
        if (!currentFileName.equals("")){
        String path = saveFilePath;
        path += "\\"+"x"+String.valueOf(mazeWorldMapPanel1.selectedPositionInMaze.x) + "x"+ String.valueOf(mazeWorldMapPanel1.selectedPositionInMaze.y)+".dat";
        File f = new File(path);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try{
            if (f.exists())
            {                
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);                  
                MazeSection screen = new MazeSection();
                screen.readExternal(ois);
                currentSection = screen;
                //Update paint panel, brush list, and brush preview panel.
                getPaintPanel().loadScreen(screen);
                for (int i=0; i<screen.animations.size(); i++){
                    if (!(loadedBrushes.contains(screen.animations.get(i))))
                    loadedBrushes.add(screen.animations.get(i));
                        }
                updateBrushList();                
                
                //selectedPositionOnGrid = new Point(0,0);
            }
        } catch (IOException ex) {
            Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally
        {
            try {
                if (ois!=null)
                {
                    ois.close();
                }
                if (fis!=null)
                {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MazeWorldMapPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
    }

}
