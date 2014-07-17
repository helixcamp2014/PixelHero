//package pixelHero;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CA_GUI_Tile extends JLabel implements MouseListener {
    Dimension minSize = new Dimension(1,1);
    PixelHero model;
    int row_id, column_id;

    public CA_GUI_Tile(int row_id, int column_id, PixelHero model) {
    	this.row_id = row_id;
    	this.column_id = column_id;
    	this.model = model;
    	
    	if(model.getObjectAt(row_id, column_id).getTypeId() == 'H')
		{
			setBackground(Color.blue);
		}
    	else if(model.getObjectAt(row_id, column_id).getTypeId() == 'D')
		{
			setBackground(Color.red);
		}
		else
		{
			setBackground(Color.white);
		}
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		//determine color
		
		if(model.getObjectAt(row_id, column_id).getTypeId() == 'H')
		{
			setBackground(Color.blue);
		}
    	else if(model.getObjectAt(row_id, column_id).getTypeId() == 'D')
		{
			setBackground(Color.red);
		}
		else
		{
			setBackground(Color.white);
		}
		
		//System.out.println("grid was painted: " + row_id + ", " + column_id);
		super.paint(g);
	}
	/*
	@Override
	public void update(Graphics g)
	{
		//determine color
		if(model.getStateAt(row_id, column_id) == 1)
		{
			setBackground(Color.black);
		}
		else
		{
			setBackground(Color.white);
		}
		//System.out.println("grid was updated: " + row_id + ", " + column_id);
		super.update(g);
	}
*/
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}