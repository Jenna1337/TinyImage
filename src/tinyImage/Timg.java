package tinyImage;

import java.io.IOException;
import java.util.Date;
import sys.BitStorage;
import tinyImage.designer.ThreeBitColor;

public class Timg
{
	private static byte revision=1;
	BitStorage r,g,b;
	public short w,h;//2 bytes each
	protected Date lastsaved = new Date();
	String ccinfo="";
	/**Creates new Timg<br>
	 * 
	 * @param width - image width as a unsigned short
	 * @param height - image height as a unsigned short
	 */
	public Timg(){}
	public Timg(short width, short height)
	{
		this.w = width;
		this.h = height;
		this.r = new BitStorage(this.getWidth()*this.getHeight());
		this.g = new BitStorage(this.getWidth()*this.getHeight());
		this.b = new BitStorage(this.getWidth()*this.getHeight());
	}
	public Timg(byte[] data)
	{
		data[0]=Timg.getRevision();
		try {
			this.setData(data);
		} catch (IOException e) {
			e.printStackTrace();
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
		return -Short.MIN_VALUE+((int)this.h);
	}
	public int getWidth()
	{
		return -Short.MIN_VALUE+((int)this.w);
	}
	/**@param b = 2 bytes
	 * @return short**/
	private static short toShort(byte[] b)
	{
		short retVal;
		retVal = b[1];
		retVal <<= 8;
		retVal |= b[0];
		return retVal;
	}
	/**@param s = short
	 * @return 2 bytes**/
	private static byte[] toBytes(short s)
	{
		byte[] b = new byte[2];
		b[0] = new Short(s).byteValue();//(byte)(s & 0xff);
		b[1] = new Short((short) (s>>8)).byteValue();//(byte)((s >> 8) & 0xff);
		return b;
	}
	/**@throws IOException if the data given is a newer version than the program**/
	public void setData(byte[] bytes) throws IOException
	{
		int index=0;

		//check version
		if(bytes[0]>Timg.getRevision())
			throw new java.io.IOException("");
		index+=1;

		//sets width and height
		this.w=toShort(java.util.Arrays.copyOfRange(bytes, index, 2+index));
		index+=2;
		this.h=toShort(java.util.Arrays.copyOfRange(bytes, index, 4+index));
		index+=2;

		//set date
		this.lastsaved.setTime(bytetolong(java.util.Arrays.copyOfRange(bytes, index, 8+index)));
		index+=8;

		//set colors
		final int bytes_percolor=(int)Math.ceil((int)this.getWidth()*(int)this.getHeight()/8);
		byte[] rgba = java.util.Arrays.copyOfRange(bytes, index, index+bytes_percolor*3+1);
		if(bytes_percolor*3>rgba.length)
			throw new IndexOutOfBoundsException();
		index+=bytes_percolor*3+1;
		this.r = new BitStorage(java.util.Arrays.copyOfRange(rgba, 0,                  bytes_percolor  +1));
		this.g = new BitStorage(java.util.Arrays.copyOfRange(rgba, bytes_percolor+1,   bytes_percolor*2+1));
		this.b = new BitStorage(java.util.Arrays.copyOfRange(rgba, bytes_percolor*2+1, bytes_percolor*3+1));
		
		//set copyright info file meta
		System.out.println(index+" "+bytes.length+" "+java.util.Arrays.toString(bytes));
		this.ccinfo=new String(java.util.Arrays.copyOfRange(bytes, index, bytes.length));
	}
	public byte[] getData()
	{
		System.out.println(Timg.getRevision()
				+"\nw="+java.util.Arrays.toString(toBytes(w))
				+"\nh="+java.util.Arrays.toString(toBytes(h))
				+"\nd="+java.util.Arrays.toString(longtobyte(new Date().getTime()))
				+"\nr="+java.util.Arrays.toString(r.toByteArray())
				+"\ng="+java.util.Arrays.toString(g.toByteArray())
				+"\nb="+java.util.Arrays.toString(b.toByteArray())
				+"\nc="+java.util.Arrays.toString(ccinfo.getBytes()));//*/
		byte[] outbytes = BitStorage.concatenate(new byte[]{Timg.getRevision()}, toBytes(w), toBytes(h), 
				longtobyte(new Date().getTime()), 
				r.toByteArray(), g.toByteArray(), b.toByteArray(),
				ccinfo.getBytes());
		System.out.println(java.util.Arrays.toString(outbytes));
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
	public static byte getRevision() {
		return revision;
	}
	public void setColor(int i, ThreeBitColor color)
	{
		this.r.set(i, color.isR());
		this.g.set(i, color.isG());
		this.b.set(i, color.isB());
	}
	public ThreeBitColor getColor(int i)
	{
		return ThreeBitColor.getThreeBitColor(this.r.get(i), this.g.get(i), this.b.get(i));
	}
}
