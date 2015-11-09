package tinyImage.designer;

import javax.swing.JPanel;

import tinyImage.Timg;

@SuppressWarnings("serial")
public class TimgDesignerPanel extends JPanel
{
	Timg img;
	public void repaintCanvas()
	{
		
	}
	public byte[] getData()
	{
		// TODO Auto-generated method stub
		return this.img.getData();
	}
	public void setData(byte[] bytes)
	{
		this.img = new Timg(bytes);
		this.repaintCanvas();
	}
}
