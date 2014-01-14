package zap.tvfbuilder.tvf;

public class Face
{
	public static enum EnumDir
	{
		XUp((byte) 1, (byte) 1, (byte) 0, (byte) 0),
		XDown((byte) 2, (byte) -1, (byte) 0, (byte) 0),
		YUp((byte) 3, (byte) 0, (byte) 1, (byte) 0),
		YDown((byte) 4, (byte) 0, (byte) -1, (byte) 0),
		ZUp((byte) 5, (byte) 0, (byte) 0, (byte) 1),
		ZDown((byte) 6, (byte) 0, (byte) 0, (byte) -1);
		
		public byte ind;
		
		public byte xoff;
		public byte yoff;
		public byte zoff;
		
		EnumDir(byte ind, byte x, byte y, byte z )
		{
			this.ind = ind;
			this.xoff = x;
			this.yoff = y;
			this.zoff = z;
		}
	}
	
	public short x;
	public short y;
	public short z;
	public EnumDir dir;
	public short color;
	
	public Face(short x, short y, short z, EnumDir dir, short color)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.dir = dir;
		this.color = color;
	}
}