package sys;



public class BitStorage
{
	boolean[] bits;
	public BitStorage(boolean... bitArray)
	{
		bits = bitArray;
	}
	public void set(int index, boolean value)
	{
		this.bits[index] = value;
	}
	public byte[] toByteArray()
	{
		byte[] bytes=new byte[bits.length/8+1];
		String bitString = this.toString();
		while(bytes.length*8>bitString.length())
			bitString+='0';
		for(int i=0; i<bytes.length; ++i)
		{
			String con = bitString.substring(8*i, 8+8*i);
			if(con.charAt(0)=='1')
				con = '-'+con.substring(1);
			bytes[i] = Byte.parseByte(con, 2);
		}
		return bytes;
	}
	public char[] toCharArray()
	{
		return new String(this.toByteArray()).toCharArray();
	}
	public String toString()
	{
		String out = "";
		for(boolean b : bits)
			out += b?'1':'0';
		return out;
	}
}
