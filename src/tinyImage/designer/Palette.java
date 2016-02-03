package tinyImage.designer;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Palette extends JPanel
{
	volatile ThreeBitColor currentcolor = ThreeBitColor.BLACK;
	public Palette()
	{
		for(ThreeBitColor color : ThreeBitColor.values())
			this.add(new ScaleablePixelBox(new Thread(new Runnable(){
				public void run(){
					currentcolor=color;
				}
			}), color));
	}
	public Color getSelectedColor() {
		return this.currentcolor.getColor();
	}

}
