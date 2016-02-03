package tinyImage.designer;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import sys.FileIO;

public class TimgDesigner
{
	JFrame frame = new JFrame("Tiny Image Editor");
	TimgDesignerPanel panel = new TimgDesignerPanel();
	JMenuBar menubar = new JMenuBar();
	public TimgDesigner()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new java.awt.GridBagLayout());
		JMenu file = new JMenu("File");
		JMenuItem save = new ActionMenuItem("Save As...", new Thread(
				new Runnable(){
					public void run(){
						FileIO.save(frame, panel.getData());}}));
		JMenuItem load = new ActionMenuItem("Open File...", new Thread(
				new Runnable(){
					public void run(){
						try{
							byte[] bs = sys.FileIO.open(frame).getBytes();
							try {
								panel.setData(bs);
							} catch (IOException e) {
								if(JOptionPane.showOptionDialog(frame, "The file chosen is of a different version. \n Click OK to continue and force load", "Warning", 
										JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, 
										new Object[]{"CONTINUE","CANCEL"}, (Object)"CANCEL")
										==1)
								{
									bs[0]=tinyImage.Timg.getRevision();
									panel.setData(bs);
								}
							}
						} catch (IOException e) {
							JOptionPane.showOptionDialog(frame, "Error: could not read the file. \n Click OK to continue", "Warning", 
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,null, 
									new Object[]{"OK"}, (Object)"OK");
						}}}));
		JMenuItem NEW = new ActionMenuItem("New", new Thread(
				new Runnable(){
					public void run(){
						TimgSizePopUp szpop = new TimgSizePopUp(frame);
						szpop.waitfor();
						/*Wait...*/
						short[] wh = szpop.getSizeShorts();
						szpop.dispose();
						if(wh!=null)
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