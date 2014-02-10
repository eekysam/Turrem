package zap.turrem.client.render.texture;

import zap.turrem.client.render.engine.RenderManager;

public class TextureIcon
{
	public final String source;
	private ITextureObject object;
	
	public TextureIcon(String texture)
	{
		this.source = texture;
	}
	
	public void load(RenderManager manager)
	{
		this.object = manager.addTexture(this.source);
		this.object.bind(manager.assets);
	}
	
	public void unload()
	{
		this.object.unbind();
	}
	
	public void start()
	{
		if (this.object != null)
		{
			this.object.start();
		}
	}
	
	public void end()
	{
		if (this.object != null)
		{
			this.object.end();
		}
	}
}