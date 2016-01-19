package tinyImage.designer;

import javax.swing.JPanel;

import tinyImage.Timg;

@SuppressWarnings("serial")
public class TimgDesignerPanel extends JPanel
{
	Palette palette;
	Timg img;
	public void repaintCanvas()
	{
		
	}
	public void newImg(short[] wh)
	{
		this.img = new Timg(wh[0], wh[1]);
	}
	public byte[] getData()
	{
		// TODO Auto-generated method stub
		return this.img.getData();
	}
	public void setData(byte[] bytes)
	{
		this.img.setData(bytes);
		this.repaintCanvas();
	}
}
