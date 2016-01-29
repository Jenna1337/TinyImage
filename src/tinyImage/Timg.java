package tinyImage;

import java.util.ArrayList;

import sys.BitStorage;

public class Timg
{
	BitStorage r,g,b;
	public short w,h;//2 bytes each
	/**Creates new Timg<br>
	 * 
	 * @param width - image width as a unsigned short
	 * @param height - image height as a unsigned short
	 */
	public Timg(int width, int height)
	{
		if(width<0 || height<0 || width>65535 || height>65535)
			throw new IllegalArgumentException();
		this.w=(short)(width -32768);
		this.h=(short)(height-32768);
		// TODO set data
	}
	/**sets pixel colors based on binary rgb**/
	private void setRGBPixelData(String[] pixels)
	{
		for(int i=0; i<pixels.length; ++i)
		{
			//TODO
			//ForBitStarage
			
		}
	}
	public static String[] splitAt(String str, int len)
	{
		if(len<=0)
			throw new StringIndexOutOfBoundsException("ERR: len < 0");
		boolean toolong = (str.length()%len)!=0;
		String[] vals = new String[str.length()/len + (toolong ? 1:0)];
		int i=0;
		for(int off=0; len-off>0; ++off)
		{
			try{
				if(!toolong && i==vals.length-1) break;
				for(; i<vals.length; ++i)
				{
					vals[i]=str.substring(i*len, (i+1)*len-off);
				}
				//if toolong then it will never get here
				//if !toolong it will exit the for loop
			}
			//should only be caught if it is too long
			catch(IndexOutOfBoundsException ioobe){}
		}
		return vals;
	}
	public int getHeight()
	{
		return (int)this.h-Short.MIN_VALUE;
	}
	public int getWidth()
	{
		return (int)this.w-Short.MIN_VALUE;
	}
	public static short toShort(byte[] b)
	{
		short retVal;
		if (b.length == 1)
			retVal = b[0];
		else 
			retVal = (short)(b[1] << 8 | b[0]);//for little endian
		return retVal;
	}
	public void setData(byte[] bytes)
	{
		//sets width and height
		int width =toShort(java.util.Arrays.copyOfRange(bytes, 0, 2));//0 and 1
		int height=toShort(java.util.Arrays.copyOfRange(bytes, 2, 4));//2 and 3
		if(width<0 || height<0 || width>65535 || height>65535)
			throw new IllegalArgumentException();
		this.w=(short)(width -32768);
		this.h=(short)(height-32768);
		
		String binary="";
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary+=((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		String[] pixels = splitAt(binary.substring(32), 3);
		//TODO add date and copyright info file metadata
		setRGBPixelData(pixels);
	}
	public byte[] getData()
	{
								// 2,2,w*h*3,64,variable
								// w,h,size,date,copyright
		byte[] outbytes = new byte[2+2+w*h*3+64+0/*TODO*/];
		
		// TODO Implement getData() based from setData(byte[]) above
		
		r.toByteArray();
		g.toByteArray();
		b.toByteArray();
		
		return outbytes;
	}
	@Override
	public String toString()
	{
		return new String(this.getData());
	}
}
