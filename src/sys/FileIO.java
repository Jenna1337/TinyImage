package sys;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;

public class FileIO
{
	//TODO fix file IO
	static String fileext=".timg";
	static ExtensionFileFilter filter = new ExtensionFileFilter("Tiny Image File", fileext);
	public static void save(Component parent, byte[] data)
	{
		System.out.println(java.util.Arrays.toString(data));
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		int returnVal = fc.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path=file.getPath();
			if(!file.exists() && !path.endsWith(fileext)){
				path+=fileext;
				file = new File(path);;}
			try
			{
				FileOutputStream out = new FileOutputStream(file);
				out.write(data);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static String open(Component parent)
	{
		String data = "";
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try
			{
				BufferedReader in = new BufferedReader(new java.io.FileReader(file));
				try
				{
					int c=0;
					while (c!=-1)
						data+=(char)(c=in.read());
				} catch(IOException ioe) {}
				in.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
/**Modified from http://www.java2s.com/Code/JavaAPI/javax.swing/JFileChoosersetFileFilterFileFilterfilter.htm **/
class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {
	String description;
	String extensions[];
	public ExtensionFileFilter(String description, String extension) {
		this(description, new String[] { extension });
	}
	public ExtensionFileFilter(String description, String extensions[]) {
		if (description == null)
			this.description = extensions[0];
		else
			this.description = description;
		this.extensions = (String[]) extensions.clone();
		toLower(this.extensions);
	}
	private void toLower(String array[]) {
		for (int i = 0, n = array.length; i < n; i++)
			array[i] = array[i].toLowerCase();
	}
	public String getDescription() {
		String desc=description+" (";
		for(int i=0; i<extensions.length; ++i)
		{
			desc+="*"+extensions[i];
			if(i!=extensions.length-1)
				desc+=", ";
		}
		return desc+")";
	}
	public boolean accept(File file) {
		if (file.isDirectory())
			return true;
		else
		{
			String path = file.getAbsolutePath().toLowerCase();
			for (int i = 0, n = extensions.length; i < n; i++)
			{
				String extension = extensions[i];
				if (path.endsWith(extension))
					return true;
			}
		}
		return false;
	}
}

