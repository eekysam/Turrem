package zap.turrem.tech.branch;

import zap.turrem.tech.TechBase;

public class BranchActive extends Branch
{
	public BranchActive(int tech)
	{
		super(tech);
	}

	public BranchActive(String tech)
	{
		super(tech);
	}

	public BranchActive(TechBase tech)
	{
		super(tech);
	}

	public BranchActive(Class<? extends TechBase> tech, int pass)
	{
		super(tech, pass);
	}
}
