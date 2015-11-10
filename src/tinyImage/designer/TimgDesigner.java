package tinyImage.designer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import sys.FileIO;

public class TimgDesigner
{
	JFrame frame = new JFrame("Tiny Image Editor");
	TimgDesignerPanel panel = new TimgDesignerPanel();
	JMenuBar menubar = new JMenuBar();
	public TimgDesigner()
	{
		JMenu file = new JMenu("File");
		JMenuItem save = new ActionMenuItem("Save As...", new Thread(
				new Runnable(){
					public void run(){
						FileIO.save(frame, new String(panel.getData()));}}));
		JMenuItem load = new ActionMenuItem("Open File...", new Thread(
				new Runnable(){
					public void run(){
						byte[] bs = sys.FileIO.open(frame).getBytes();
						if(bs.length>=4)
							panel.setData(bs);}}));
		JMenuItem NEW = new ActionMenuItem("New", new Thread(
				new Runnable(){
					public void run(){
						TimgSizePopUp szpop = new TimgSizePopUp();
						szpop.waitfor();;
						//do nothing
						short[] wh = szpop.getSizeShorts();
						szpop.dispose();
						panel.newImg(wh);}}));
		file.add(NEW);
		file.add(load);
		file.add(save);
		menubar.add(file);
		frame.setContentPane(panel);
		frame.setJMenuBar(menubar);
	}
	public void setVisible(boolean visibility)
	{
		frame.setVisible(visibility);
	}
}