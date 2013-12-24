package tech.turrem.math;

import zap.turrem.tech.branch.BranchAvailable;

public class Zero extends MathmaticalTech
{
	public Zero(int pass)
	{
		super(pass);
	}

	@Override
	public void loadBranches()
	{
		(new BranchAvailable(this)).addRequired(Numerals.class, 0).push();
	}

	@Override
	public String getName()
	{
		return "Zero";
	}

}