package tinyImage.designer;

import java.awt.Color;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Palette extends JPanel
{
	volatile ThreeBitColor currentcolor;
	public Palette(TimgDesignerPanel parent)
	{
		currentcolor = ThreeBitColor.BLACK;
		for(ThreeBitColor color : ThreeBitColor.values())
			this.add(new ScaleablePixelBox(new Thread(new Runnable(){
				public void run(){
					currentcolor=color;
				}
			}), color, parent));
	}
	public Color getSelectedColor() {
		return this.currentcolor.getColor();
	}
}
