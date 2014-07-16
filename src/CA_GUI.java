//package pixelHero;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class CA_GUI extends JPanel
        implements ActionListener {
	
	private static final long serialVersionUID = 7832885560545657000L;
	
	
    CA_GUI_GridContainer gridArea;
    static PixelHero model;
    static CA_GUI gui_instance;
    //JTextArea textArea;
    static final String NEWLINE = System.getProperty("line.separator");
    Button downButton;
    Button leftButton;
    Button rightButton;
    Button moveUpButton;
    Button loadButton;
    JSpinner delaySpinner, sizeSpinner;
    Label currentStepLabel;
    
    boolean started = false;
    int millisecondsBetweenFrames = 1000;
    ActionListener listener = null;
    Timer displayTimer = null;
    static GameObject initialState[][];
    
    final JFileChooser fc = new JFileChooser();


	private JTextArea textArea;
     
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
           
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        /*
        int initialState[][] = new int[][]{
				  { 0, 1, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 1, 0, 0, 0, 0, 0 ,0, 0 },
				  { 1, 1, 1, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				  { 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0 },
				};
		*/
        initialState = new GameObject[1][1];  //TODO: fix this!!!
        initialState[0][0] = new GameObject(); 
        model = new PixelHero(initialState);
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("PixelHero2: The Sequel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        JComponent newContentPane = new CA_GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
         
        //Display the window.
        frame.pack();

        frame.setVisible(true);
    }
     
    private void tick()
    {
    	//update the CA
    	model.computeNextState();
    	//update the GUI
    	gridArea.repaint();
    	if(displayTimer != null)
    	{
    		displayTimer.setDelay((int)delaySpinner.getModel().getValue());
    	}
    	currentStepLabel.setText("Current Step: " + model.getCurrentStep());
    	currentStepLabel.repaint();

    	System.out.println("tick() was called !!!");
    	
    	
    }
    
    
    public CA_GUI() {
        super(new BorderLayout(0,1));
        gridArea = new CA_GUI_GridContainer(model.getRows(),model.getColumns(), model);
        add(gridArea, BorderLayout.CENTER);
        /*
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        add(scrollPane);gridArea = new CA_GUI_GridContainer(model.getRows(),model.getColumns(), model);

         */
        JPanel controlsArea = new JPanel(new GridLayout(0,3));
        controlsArea.setPreferredSize(new Dimension(150, 150));
        moveUpButton = new Button("Up");
        moveUpButton.addActionListener(this);
        moveUpButton.setActionCommand("move_up");
        controlsArea.add(moveUpButton);
        downButton= new Button("Down");
        downButton.addActionListener(this);
        downButton.setActionCommand("move_down");        
        controlsArea.add(downButton);
        leftButton = new Button("Left");
        leftButton.addActionListener(this);
        leftButton.setActionCommand("move_left");
        controlsArea.add(leftButton);
        rightButton = new Button("Right");
        rightButton.setActionCommand("move_right");  
        rightButton.addActionListener(this);
        controlsArea.add(rightButton);
        
        //controlsArea.add(new Label("Size:", java.awt.Label.RIGHT));
        SpinnerModel sizeModel =
                new SpinnerNumberModel(model.getRows(), //initial value
                                       1, //min
                                       1000, //max
                                       1);//step
        sizeSpinner = new JSpinner(sizeModel);
        controlsArea.add(sizeSpinner);
        
        
        controlsArea.add(new Label("Delay:", java.awt.Label.RIGHT));        
        SpinnerModel delayModel =
                new SpinnerNumberModel(50, //initial value
                                       0, //min
                                       5000, //max
                                       1);//step
        delaySpinner = new JSpinner(delayModel);
        controlsArea.add(delaySpinner);
        
        currentStepLabel = new Label("Current step: 0");
        controlsArea.add(currentStepLabel);  
        
        loadButton = new Button("Load from file");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        controlsArea.add(loadButton);
        add(controlsArea, BorderLayout.SOUTH);
                  
        
        textArea = new JTextArea();
        textArea.setEditable(false);
         
        JScrollPane textPane = new JScrollPane(textArea);
        textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setPreferredSize(new Dimension(200, 75));
        this.add(textPane, BorderLayout.NORTH);
         
        textArea.append("Welcome to dragon land. You are a cool dude that is here to slay dragons.\n");
        
        
        //Register for mouse events on blankArea and the panel.
        //gridArea.addMouseListener(this);
        //addMouseListener(this);
        setPreferredSize(new Dimension(450, 450));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        gui_instance = this;
        
        listener = new ActionListener(){
        	  public void actionPerformed(ActionEvent event){
        		if(!started){
        			displayTimer.stop();
        		}

        	    tick();
        	  }
        	};
        
        if(started)
        {
        	int temp = (int)delayModel.getValue();
        	displayTimer = new Timer(temp, listener);
        	displayTimer.start();
        }
        
    }
    
    private void resolveCombat(Hero theHero, Dragon theDragon)
    {
    	double hero_chance_to_hit_dragon = 0.80;
    	double dragon_chance_to_hit_hero = 0.80;
    	
    	if(Math.random() < hero_chance_to_hit_dragon)
    	{
    		theDragon.health = theDragon.health - theHero.weapon.damage;
    		
    		String temp = "Thou hast dealt: " + theHero.weapon.damage + " to the dragon!!! \n";
    		textArea.append(temp);

    	}
    	else
    	{
    		String temp = "the dragon dodged the attack!! \n";
    		textArea.append(temp);
    	}
    	
    	if(theDragon.health <=0)
    	{
    		String temp = "the dragon was slain!!!! \n";
    		textArea.append(temp);
    	}
    	else
    	{
    		if(Math.random() < dragon_chance_to_hit_hero)
        	{
        		int damageReduction = 0;
        		//TODO: test if this works
    			if(theHero.armor.durability > 0){
        			damageReduction = theHero.armor.damageReduction;
        			theHero.armor.durability -= theDragon.damage;
        		}
    			int tempDamage = theDragon.damage - damageReduction;
    			theHero.health = theHero.health - tempDamage;
        		
        		String temp = "The dragon hast hit thou for: " + tempDamage + " life!!! \n";
        		textArea.append(temp);

        		temp = "Thy remianing hit points are: " + theHero.health + "\n";

        		textArea.append(temp);

        	}
        	else
        	{
        		String temp = "thou hast dodged the attack!! \n";
        		textArea.append(temp);
        	}
    		if(theHero.health <=0)
        	{
        		String temp = "thou art slain!!!!! \n";
        		textArea.append(temp);
        	}
    	}
    	
    	
    }
    
    private boolean isTheDragonIn(int rowIdx, int colIdx)
    {
    	
    	GameObject tempObject = model.getStateAt(rowIdx, colIdx);
    	if(tempObject != null)
    	{
    		if(tempObject.getTypeId() == 'D')
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "move_up")
		{
			Hero tempHero = model.getHero();
			int hero_row = tempHero.getRowIndex();
			int hero_column = tempHero.getColumnIndex();
			
			int newHeroRow = hero_row - 1;
			if(hero_row > 0 && !isTheDragonIn(newHeroRow, hero_column))
			{
				//move the hero
				model.removeGameObject(hero_row, hero_column);
				model.placeGameObject(newHeroRow, hero_column, tempHero);
				tempHero.setRowIndex(newHeroRow);
				textArea.append("hero is now in row: " + newHeroRow + " new hero column: " + hero_column + "\n");
			}
			else if(isTheDragonIn(newHeroRow, hero_column))
			{
				Dragon theDragon = (Dragon) model.getStateAt(newHeroRow, hero_column);
				resolveCombat(tempHero, theDragon);
			}
			else
			{
				textArea.append("You can't move up any further\n");
			}
	    	gridArea.repaint();

			
		}
		else if(e.getActionCommand() == "move_down")
		{
			Hero tempHero = model.getHero();
			int hero_row = tempHero.getRowIndex();
			int hero_column = tempHero.getColumnIndex();
					
			if(hero_row >= model.getColumns() - 1)
			{
				//move the hero
				model.removeGameObject(hero_row, hero_column);
				model.placeGameObject(hero_row + 1, hero_column, tempHero);
				tempHero.setRowIndex(hero_row + 1);

			}
			else
			{
				textArea.append("You can't move down any further\n");
			}
	    	gridArea.repaint();

			
		}
		else if(e.getActionCommand() == "move_left")
		{
			Hero tempHero = model.getHero();
			int hero_row = tempHero.getRowIndex();
			int hero_column = tempHero.getColumnIndex();
					
			if(hero_row >= 0)
			{
				//move the hero
				model.removeGameObject(hero_row, hero_column);
				model.placeGameObject(hero_row, hero_column - 1, tempHero);
				tempHero.setColumnIndex(hero_column - 1);
			}
			else
			{
				textArea.append("You can't move left any further\n");
			}
	    	gridArea.repaint();

		}
		
		else if(e.getActionCommand() == "move_right")
		{
			Hero tempHero = model.getHero();
			int hero_row = tempHero.getRowIndex();
			int hero_column = tempHero.getColumnIndex();
					
			if(hero_row <= model.getColumns() - 1)
			{
				//move the hero
				model.removeGameObject(hero_row, hero_column);
				model.placeGameObject(hero_row, hero_column + 1, tempHero);
				tempHero.setColumnIndex(hero_column + 1);

			}
			else
			{
				textArea.append("You can't move right any further\n");
			}
	    	gridArea.repaint();

		}
	
		else if(e.getActionCommand() == "load")
		{
			
			if(displayTimer != null)
			{
				displayTimer.stop();
			}			
			started= false;
			
			//TODO: get this working and resize the grid beforehand!!!
			//resetGrid();
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Pixel Hero map files", "map");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this);
			
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            
	            //int temp = (int)(sizeSpinner.getModel().getValue());
	            int temp = 10;
	    		model = new PixelHero(temp, temp);
	            
	            model.loadStateFromFile(file);            
	            
	            currentStepLabel.setText("Current Step: " + model.getCurrentStep());
	        	currentStepLabel.repaint();
	        	gridArea.reset(temp, temp, model);

	        } else {
	            //user cancelled or something
	        }
		}
	}

	private void resetGrid(){
		
	
		//invalidate();		
		//this.remove(gridArea);
		
		int temp = (int)(sizeSpinner.getModel().getValue());
		model = new PixelHero(temp, temp);
		//gridArea = new CA_GUI_GridContainer(model.getRows(),model.getColumns(), model);
		//this.add(gridArea);
		gridArea.reset(temp, temp, model);
		
		//revalidate();
		//repaint();
		
	}
	
     
}