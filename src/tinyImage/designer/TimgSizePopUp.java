package tinyImage.designer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class TimgSizePopUp extends JDialog implements WindowListener
{
	private JPanel pane_btn=new JPanel(new FlowLayout()),
					pane_in=new JPanel(new FlowLayout());
	private JSpinner h, w;
	private ActionButton button_ok, button_cancel;
	protected short[] wh;
	private byte choice;
	public TimgSizePopUp(JFrame parent)
	{
		super(parent);
		this.init();
	}
	public TimgSizePopUp()
	{
		this.init();
	}
	private void init()
	{
		this.setTitle("Choose Size");
		this.addWindowListener(this);
		
		this.setLayout(new BorderLayout());
		this.h=new JSpinner(new SpinnerNumberModel(10,0,9999,1));
		this.w=new JSpinner(new SpinnerNumberModel(10,0,9999,1));
		
		pane_in.add(h);
		pane_in.add(w);
		this.getContentPane().add(pane_in, BorderLayout.NORTH);
		
		TimgSizePopUp mywindow=this;
		this.button_ok=new ActionButton("OK", new Thread(new Runnable(){
			public void run(){
				mywindow.choice=0;
				active=false;
				mywindow.dispose();
			}
		}));
		this.button_cancel=new ActionButton("Cancel", new Thread(new Runnable(){
			public void run(){
				mywindow.choice=1;
				active=false;
				mywindow.dispose();
			}
		}));
		
		pane_btn.add(button_ok);
		pane_btn.add(button_cancel);
		this.getContentPane().add(pane_btn, BorderLayout.SOUTH);
		
		this.setModal(true);
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
		if(this.choice!=0)
			return null;
		short[] sa=new short[2];
		sa[0] = getShort(this.w);
		sa[1] = getShort(this.h);
		return sa;
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
		return Short.parseShort(""+((Integer)field.getValue()+Short.MIN_VALUE));
	}
}

