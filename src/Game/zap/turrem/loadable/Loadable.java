package zap.turrem.loadable;

public abstract class Loadable
{
	public final int pass;

	public Loadable(int pass)
	{
		this.pass = pass;
	}
	
	public final String getIdentifier()
	{
		return this.getIdentifier(this.pass);
	}
	
	public final String getIdentifier(int pass)
	{
		return JarLoader.getStaticIdentifier(this.getClass(), pass);
	}
	
	public final int getPassNum()
	{
		return JarLoader.getStaticPassNum(this.getClass());
	}
}
