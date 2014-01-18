package zap.turrem.utils.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFFile
{
	/**
	 * Magic number
	 */
	public byte[] magic;

	/**
	 * The format's magic number
	 */
	public static final byte[] themagic = ("VoxFace").getBytes();

	/**
	 * Turem verion numbers
	 */
	public byte[] turremVersion;

	/**
	 * File verion number
	 */
	public short fileVersion;

	/**
	 * Format version number
	 */
	public static final int theFileVersion = 3;

	/**
	 * Number of unique colors
	 */
	public short colorNum;

	/**
	 * List of colors
	 */
	public TVFColor[] colors;

	public static class TVFColor
	{
		public byte id;
		public byte r;
		public byte g;
		public byte b;
	}

	/**
	 * Number of dynamic colors
	 */
	public short dynamicColorNum;

	/**
	 * List of dynamic colors
	 */
	public TVFDynamicColor[] dynamicColors;

	public static class TVFDynamicColor
	{
		public byte id;
		public String name;
	}

	/**
	 * Number of faces
	 */
	public int faceNum;

	/**
	 * List of faces
	 */
	public TVFFace[] faces;

	public static class TVFFace
	{
		public byte x;
		public byte y;
		public byte z;
		public byte dir;
		public byte color;

		public static byte Dir_XUp = (byte) 0x01;
		public static byte Dir_XDown = (byte) 0x02;
		public static byte Dir_YUp = (byte) 0x03;
		public static byte Dir_YDown = (byte) 0x04;
		public static byte Dir_ZUp = (byte) 0x05;
		public static byte Dir_ZDown = (byte) 0x06;
	}

	private TVFFile()
	{

	}

	/**
	 * Construct TVF file from VOX file
	 * 
	 * @param vox The VOX file
	 */
	public TVFFile(VOXFile vox)
	{
		this();
		VoxToTvf con = new VoxToTvf(this, vox);
		con.make();
	}

	/**
	 * Write file
	 * 
	 * @param stream The stream to write too (Should use GZIP compression)
	 * @throws IOException
	 */
	public void write(DataOutputStream stream) throws IOException
	{
		stream.write(themagic);
		stream.write(0);
		stream.write(0);
		stream.write(0);
		stream.writeShort(theFileVersion);

		stream.writeShort(this.colorNum);

		for (int i = 0; i < this.colorNum; i++)
		{
			TVFColor c = this.colors[i];
			stream.write(c.id & 0xFF);
			stream.write(c.r & 0xFF);
			stream.write(c.g & 0xFF);
			stream.write(c.b & 0xFF);
		}

		if (this.dynamicColors != null)
		{
			stream.writeShort(this.dynamicColorNum);

			for (int i = 0; i < this.dynamicColorNum; i++)
			{
				TVFDynamicColor c = this.dynamicColors[i];
				stream.write(c.id);
				stream.writeUTF(c.name);
			}
		}
		else
		{
			stream.writeShort(0);
		}

		stream.writeInt(this.faceNum);

		for (int i = 0; i < this.faceNum; i++)
		{
			TVFFace f = this.faces[i];
			stream.write(f.x & 0xFF);
			stream.write(f.y & 0xFF);
			stream.write(f.z & 0xFF);
			stream.write(f.dir & 0xFF);
			stream.write(f.color & 0xFF);
		}
	}

	/**
	 * Reads a new TVF file
	 * 
	 * @param stream The stream to read from (Should use GZIP compression)
	 * @return The TVF file that was read (null if file is wrong or is wrong
	 *         version)
	 * @throws IOException
	 */
	public static TVFFile read(DataInputStream stream) throws IOException
	{
		TVFFile tvf = new TVFFile();

		tvf.magic = new byte[themagic.length];

		for (int i = 0; i < tvf.magic.length; i++)
		{
			tvf.magic[i] = stream.readByte();
		}

		tvf.turremVersion = new byte[3];

		for (int i = 0; i < tvf.turremVersion.length; i++)
		{
			tvf.turremVersion[i] = stream.readByte();
		}

		tvf.fileVersion = stream.readShort();

		if (tvf.fileVersion != theFileVersion)
		{
			return null;
		}

		tvf.colorNum = stream.readShort();
		tvf.colors = new TVFColor[tvf.colorNum];

		for (int i = 0; i < tvf.colorNum; i++)
		{
			TVFColor c = new TVFColor();

			c.id = stream.readByte();
			c.r = stream.readByte();
			c.g = stream.readByte();
			c.b = stream.readByte();

			tvf.colors[i] = c;
		}

		tvf.dynamicColorNum = stream.readShort();
		if (tvf.dynamicColorNum > 0)
		{
			tvf.dynamicColors = new TVFDynamicColor[tvf.dynamicColorNum];

			for (int i = 0; i < tvf.dynamicColorNum; i++)
			{
				TVFDynamicColor c = new TVFDynamicColor();
				c.id = stream.readByte();
				c.name = stream.readUTF();

				tvf.dynamicColors[i] = c;
			}
		}

		tvf.faceNum = stream.readInt();
		tvf.faces = new TVFFace[tvf.faceNum];

		for (int i = 0; i < tvf.faceNum; i++)
		{
			TVFFace f = new TVFFace();

			f.x = stream.readByte();
			f.y = stream.readByte();
			f.z = stream.readByte();
			f.dir = stream.readByte();
			f.color = stream.readByte();

			tvf.faces[i] = f;
		}

		return tvf;
	}
}
