package tinyImage.designer;

import java.io.IOException;

import javax.swing.JPanel;

import tinyImage.Timg;

@SuppressWarnings("serial")
public class TimgDesignerPanel extends JPanel
{
	Palette palette;
	Timg img=new Timg();
	public void repaintCanvas()
	{
		//TODO
	}
	public void newImg(short[] wh)
	{
		this.img = new Timg(wh[0], wh[1]);
	}
	public byte[] getData()
	{
		return this.img.getData();
	}
	public void setData(byte[] bytes) throws IOException
	{
		this.img.setData(bytes);
		this.repaintCanvas();
	}
}
