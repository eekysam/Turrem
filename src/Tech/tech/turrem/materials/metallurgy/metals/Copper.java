package tech.turrem.materials.metallurgy.metals;

public class Copper extends Metal
{
	public Copper(int pass)
	{
		super(pass);
	}

	@Override
	public String getMetalName()
	{
		return "Copper";
	}

	@Override
	public int getLevel()
	{
		return 1;
	}
}
