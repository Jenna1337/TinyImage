package sys;

public class BitStorage
{
	private boolean[] bits;
	public BitStorage(boolean... bitArray)
	{
		bits = bitArray;
	}
	public BitStorage(byte[] bytes)
	{
		this.bits = byte2bit(bytes);
	}
	public BitStorage(int length)
	{
		this.bits = new boolean[length];
		for(int i=0; i<length; ++i)
			this.bits[i]=true;
	}
	public void set(int index, boolean value)
	{
		this.bits[index] = value;
	}
	public byte[] toByteArray()
	{
		byte[] bytes=new byte[bits.length/8+(bits.length%8!=0?1:0)];
		String bitString = this.toString();
		while(bytes.length*8>bitString.length())
			bitString+='0';
		for(int i=0; i<bytes.length; ++i)
		{
			String con = bitString.substring(8*i, 8+8*i);
			bytes[i] = (byte)Integer.parseInt(con, 2);
		}
		return bytes;
	}
	/*public char[] toCharArray()
	{
		return new String(this.toByteArray()).toCharArray();
	}*/
	public String toString()
	{
		String out = "";
		for(boolean b : bits)
			out += b?'1':'0';
		return out;
	}
	/*public String toCharString()
	{
		return new String(this.toByteArray());
	}*/
	/*public static String toCharString(BitStorage... bitStorages)
	{
		String str="";
		for(BitStorage bs : bitStorages)
			str+=bs.toCharString();
		return str;
	}*/
	public static byte[] concatenate (byte[] a, byte[]... bc)
	{
		byte[] n = java.util.Arrays.copyOf(a, a.length);
		for(byte[] b : bc)
			n = concatenate(n, b);
		return n;
	}
	private static byte[] concatenate (byte[] a, byte[] b)
	{
		byte[] c = new byte[a.length+b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	@SuppressWarnings("unused")
	private static byte[] arraycopy(byte[] src, int srcPos, byte[] dest, int destPos, int length)
	{
		for(int i=srcPos,d=destPos;i<srcPos+length&&d<destPos+length;++i,++d)
			dest[d]=((Byte)src[i]).byteValue();
		return dest;
	}
	private static boolean[] byte2bit(byte[] bytes) {
		boolean[] bits = new boolean[bytes.length * 8];
		for (int i=0; i<bytes.length*8; ++i)
		{
			byte curbyte = bytes[i/8];
			bits[i] = isBitSet(curbyte,i);
		}
		return bits;
	}
	private static boolean isBitSet(byte b, int bit)
	{
	    return (b & (1 << bit)) != 0;
	}
	public boolean get(int i)
	{
		return this.bits[i];
	}
}

