package tinyImage.designer;

public enum ThreeBitColor
{
	//       red  green  blue
	BLACK  (false,false,false),//000 
	BLUE   (false,false, true),//001 
	GREEN  (false, true,false),//010 
	CYAN   (false, true, true),//011 
	RED    (true, false,false),//100 
	MAGENTA( true,false, true),//101 
	YELLOW ( true, true,false),//110 
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
	public static ThreeBitColor getThreeBitColor(boolean red, boolean green, boolean blue)
	{
		return ThreeBitColor.values()[Byte.parseByte((red?"1":"0")+(green?"1":"0")+(blue?"1":"0"), 2)];
		
	}
	public boolean isR()
	{
		return this.r;
	}
	public boolean isG()
	{
		return this.g;
	}
	public boolean isB()
	{
		return this.b;
	}
}