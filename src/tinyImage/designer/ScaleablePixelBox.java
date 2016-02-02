package tinyImage.designer;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class ScaleablePixelBox extends JPanel implements MouseInputListener
{
	//for use in palette and main image design screen
	//TODO add a clickable colorable box that can execute a command
	//might have to be abstract
	private boolean mouseisover=false;
	private boolean mousedown=false;
	protected ThreeBitColor color;
	
	ActionThread thread;
	
	public ScaleablePixelBox(ActionThread thread, ThreeBitColor colorinitss)
	{
		//TODO
	}
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	public void mouseDragged(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}
	public boolean isMouseOver() {
		return mouseisover;
	}
	public boolean isMouseDown() {
		return mousedown;
	}
}
enum ThreeBitColor
{
	//       red  green  blue
	BLACK  (false,false,false),//000 
	RED    (true, false,false),//100 
	GREEN  (false, true,false),//010 
	YELLOW ( true, true,false),//110 
	BLUE   (false,false, true),//001 
	MAGENTA( true,false, true),//101 
	CYAN   (false, true, true),//011 
	WHITE  ( true, true, true);//111 
	boolean r,g,b;
	ThreeBitColor(boolean red, boolean green, boolean blue)
	{
		this.r=red; this.g=green; this.b=blue;
	}
	public boolean[] getRGB()
	{
		return new boolean[]{this.r, this.g, this.b};
	}
	public java.awt.Color getColor()
	{
		return new java.awt.Color(r?1.0f:0.0f, g?1.0f:0.0f, b?1.0f:0.0f);
	}
}