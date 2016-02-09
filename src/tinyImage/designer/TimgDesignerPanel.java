package tinyImage.designer;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JPanel;

import tinyImage.Timg;

@SuppressWarnings("serial")
public class TimgDesignerPanel extends JPanel
{
	volatile TimgDesignerPanel designer=this;
	public volatile boolean mousedown=false;
	Palette palette = new Palette(this);
	Timg img=new Timg((short)(10+Short.MIN_VALUE),(short)(10+Short.MIN_VALUE));

	public TimgDesignerPanel()
	{
		resizeCanvas();
	}
	public void repaintCanvas()
	{
		//TODO?
	}
	private void resizeCanvas()
	{
		this.removeAll();
		this.setLayout(new GridLayout(this.img.getHeight(), this.img.getWidth()));
		for(int i=0; i<this.img.getHeight()*this.img.getWidth(); ++i)
		{
			//System.out.println(i+" "+this.img.getHeight()+" "+this.img.getWidth());
			final int ci = i;
			this.add(new ScaleablePixelBox(new Thread(new Runnable(){
				public void run()
				{
					designer.getComponent(ci).setBackground(palette.getSelectedColor());;
				}
			}), this.img.getColorAt(i),this));
		}
		this.revalidate();
	}
	public void newImg(short[] wh)
	{
		this.img = new Timg(wh[0], wh[1]);
		Thread.currentThread();
		Thread.yield();
		this.resizeCanvas();
	}
	public byte[] getData()
	{
		return this.img.getData();
	}
	public void setData(byte[] bytes) throws IOException
	{
		this.img.setData(bytes);
		this.resizeCanvas();
		this.repaintCanvas();
	}
}
