package tinyImage;

import java.io.IOException;
import java.util.Date;

import sys.BitStorage;

public class Timg
{
	static byte revision=0;
	byte fileversion=0;
	BitStorage r,g,b;
	public short w,h;//2 bytes each
	protected Date lastsaved = new Date();
	String ccinfo="";
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
	/**@param b = 2 bytes
	 * @return short**/
	public static short toShort(byte[] b)
	{
		short retVal;
		if (b.length == 1)
			retVal = b[0];
		else 
			retVal = (short)(b[1] << 8 | b[0]);//for little endian
		return retVal;
	}
	/**@param s = short
	 * @return 2 bytes**/
	public static byte[] toBytes(short s)
	{
		byte[] b = new byte[2];
		b[0] = (byte)(s & 0xff);
		b[1] = (byte)((s >> 8) & 0xff);
		return b;
	}
	/**@throws IOException if the data given is a newer version than the program**/
	public void setData(byte[] bytes) throws IOException
	{
		int index=0;
		
		//check version
		if(bytes[index]>Timg.revision)
			throw new java.io.IOException("");
		index+=1;
		
		//sets width and height
		int width =toShort(java.util.Arrays.copyOfRange(bytes, index, 2+index));//0 and 1
		index+=2;
		int height=toShort(java.util.Arrays.copyOfRange(bytes, index, 4+index));//2 and 3
		index+=2;
		if(width<0 || height<0 || width>65535 || height>65535)
			throw new IllegalArgumentException();
		this.w=(short)(width -32768);
		this.h=(short)(height-32768);

		//set date
		this.lastsaved.setTime(bytetolong(java.util.Arrays.copyOfRange(bytes, index, 8+index)));
		index+=8;

		//set colors
		final int bytes_percolor=(int)Math.ceil(w*h/8);
		byte[] rgba = java.util.Arrays.copyOfRange(bytes, index, index+bytes_percolor*3+1);
		index+=bytes_percolor*3+1;

		this.r = new BitStorage(java.util.Arrays.copyOfRange(rgba, 0,                 bytes_percolor+1));
		this.g = new BitStorage(java.util.Arrays.copyOfRange(rgba, bytes_percolor+1,   bytes_percolor*2+1));
		this.b = new BitStorage(java.util.Arrays.copyOfRange(rgba, bytes_percolor*2+1, bytes_percolor*3+1));

		//set copyright info file meta
		this.ccinfo=new String(java.util.Arrays.copyOfRange(bytes, index, bytes.length));
	}
	public byte[] getData()
	{
		byte[] outbytes = BitStorage.concatenate(new byte[]{Timg.revision}, toBytes(w), toBytes(h), 
				longtobyte(new Date().getTime()), 
				r.toByteArray(), g.toByteArray(), b.toByteArray(),
				ccinfo.getBytes());

		return outbytes;
	}
	@Override
	public String toString()
	{
		return new String(this.getData());
	}



	/*Taken from 
	 * https://www.daniweb.com/programming/software-development/code/216874/primitive-types-as-byte-arrays
	 */
	private static byte[] longtobyte(long data) {
		return new byte[] {
				(byte)((data >> 56) & 0xff),
				(byte)((data >> 48) & 0xff),
				(byte)((data >> 40) & 0xff),
				(byte)((data >> 32) & 0xff),
				(byte)((data >> 24) & 0xff),
				(byte)((data >> 16) & 0xff),
				(byte)((data >> 8) & 0xff),
				(byte)((data >> 0) & 0xff),
		};
	}
	/*Taken from 
	 * https://www.daniweb.com/programming/software-development/code/216874/primitive-types-as-byte-arrays
	 */
	private static long bytetolong(byte[] data) {
		if (data == null || data.length != 8) return 0x0;
		// ----------
		return (long)(
				// (Below) convert to longs before shift because digits
				//         are lost with ints beyond the 32-bit limit
				(long)(0xff & data[0]) << 56  |
				(long)(0xff & data[1]) << 48  |
				(long)(0xff & data[2]) << 40  |
				(long)(0xff & data[3]) << 32  |
				(long)(0xff & data[4]) << 24  |
				(long)(0xff & data[5]) << 16  |
				(long)(0xff & data[6]) << 8   |
				(long)(0xff & data[7]) << 0
				);
	}
}
