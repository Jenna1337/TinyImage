import sys.FileIO;
import tinyImage.Timg;
import tinyImage.designer.TimgDesigner;

public class Main
{
	public static void main(String[] args)
	{
		TimgDesigner editor = new TimgDesigner();
		editor.setVisible(true);
	}
	/*
		Timg timg = new Timg(65535, 65535);
		System.out.println(timg.w+" "+timg.h);
		System.out.println(timg.getWidth()+" "+timg.getHeight());
		
		System.exit(0);
		
		FileIO.open(null);
		FileIO.save(null, null);
		
	}*/
}
