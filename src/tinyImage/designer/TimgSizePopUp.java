package tinyImage.designer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class TimgSizePopUp extends JFrame implements WindowListener
{
	private JSpinner h, w;
	protected short[] wh;
	public TimgSizePopUp()
	{
		this.setTitle("Choose Size");
		this.addWindowListener(this);
		//TODO
		this.setLayout(new FlowLayout());
		this.h=new JSpinner(new SpinnerNumberModel(50,0,9999,1));
		this.w=new JSpinner(new SpinnerNumberModel(50,0,9999,1));
		
		this.getContentPane().add(h, BorderLayout.NORTH);
		this.getContentPane().add(w, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}
	public volatile boolean active=true;
	public byte waitfor()
	{
		try{
			while(active)
				Thread.sleep(1000);
			this.dispose();
			return 0;
		}
		catch(Exception e){
			this.dispose();
			return 1;
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
		short[] sa=new short[2];
		sa[0] = getShort(this.w);
		sa[1] = getShort(this.h);
		return sa;
	}
	public static void main(String args[]) throws Exception
	{
		TimgSizePopUp cc = new TimgSizePopUp();

		System.out.println(cc.waitfor());
		System.out.println(java.util.Arrays.toString(cc.getSizeShorts()));
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		wh = this.getSizeShorts();
		active = false;
	}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}

	public short getShort(JSpinner field)
	{
		return Short.parseShort(""+(Integer)field.getValue());
	}
}

