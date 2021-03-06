package net.turrem.app.entity;

import net.turrem.app.EnumSide;
import net.turrem.app.mod.ModInstance;

abstract class EntityArticle
{
	EnumSide side = null;
	private final String id;
	ModInstance mod = null;
	
	public EntityArticle(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.mod.identifier + ":" + this.id;
	}
	
	public String getInternalId()
	{
		return this.id;
	}
	
	public ModInstance getMod()
	{
		return this.mod;
	}
	
	public EnumSide getSide()
	{
		return this.side;
	}
}
