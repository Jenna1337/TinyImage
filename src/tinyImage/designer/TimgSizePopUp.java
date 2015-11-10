package tinyImage.designer;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TimgSizePopUp extends JFrame implements WindowListener
{
	private JTextField h, w;
	protected short[] wh;
	public TimgSizePopUp()
	{
		this.setTitle("Double Color Chooser");
		this.addWindowListener(this);
		//TODO
		this.h=new JTextField("65535");
		this.w=new JTextField("65535");

		this.getContentPane().add(h, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}
	public volatile boolean active;
	public void waitfor()
	{
		try{
			while(!active)
				Thread.sleep(1000);
		}
		catch(Exception e){
			this.dispose();
		}
	}
	/**
	 * 
	 * @return An array of shorts containing only width
	 *         and height, in their respective order.
	 **/
	public short[] getSizeShorts()
	{
		//TODO
		short[] sa=new short[4];
		sa[0] = getShort(this.w);
		sa[1] = getShort(this.h);
		return sa;
	}
	public static void main(String args[]) throws Exception
	{
		TimgSizePopUp cc = new TimgSizePopUp();

		cc.waitfor();
		System.out.println(java.util.Arrays.toString(cc.getSizeShorts()));
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		wh = this.getSizeShorts();
		active = true;
	}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}

	public short getShort(JTextField field)
	{
		return Short.parseShort(""+Integer.parseInt(field.getText())+Short.MIN_VALUE);
	}
}

