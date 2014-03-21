package zap.turrem.client.render.engine;

import java.util.ArrayList;
import java.util.HashMap;

import zap.turrem.client.render.object.IRenderObject;
import zap.turrem.client.render.object.RenderObject;
import zap.turrem.client.render.object.model.ModelIcon;
import zap.turrem.client.render.object.model.TVFBuffer;
import zap.turrem.utils.models.TVFFile;

public class RenderStore extends RenderEngine
{
	private ArrayList<IRenderObject> objects = new ArrayList<IRenderObject>();
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	private String identifier;

	public RenderStore(String id)
	{
		super();
		this.identifier = id;
	}

	public int getObject(String id)
	{
		Integer i = this.map.get(id);
		if (i == null)
		{
			return -1;
		}
		return (int) i;
	}
	
	public RenderObject makeObject(TVFFile tvf, float scale, float x, float y, float z)
	{
		RenderObject obj = new RenderObject(this.objects.size());
		this.objects.add(obj);
		TVFBuffer buff = new TVFBuffer();
		buff.bindTVF(tvf, obj, scale, x, y, z);
		return obj;
	}

	public RenderObject addObject(ModelIcon ico, TVFFile tvf, float scale, float x, float y, float z)
	{
		int i = this.map.get(ico.getName());
		RenderObject obj = new RenderObject(i);
		this.objects.set(i, obj);
		TVFBuffer buff = new TVFBuffer();
		buff.bindTVF(tvf, obj, scale, x, y, z);
		return obj;
	}

	public void doRenderObject(int index)
	{
		IRenderObject o = this.objects.get(index);
		if (o != null)
		{
			o.doRender();
		}
	}

	public void wipeAll()
	{
		for (int i = 0; i < this.objects.size(); i++)
		{
			this.objects.get(i).doDelete();
		}
		this.objects.clear();
		this.map.clear();
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void push(ModelIcon icon)
	{
		boolean loaded;
		int index = this.getObject(icon.getName());
		if (index == -1)
		{
			index = this.objects.size();
			this.objects.add(null);
			loaded = false;
			this.map.put(icon.getName(), index);
		}
		else
		{
			loaded = this.objects.get(index) != null;
		}
		icon.onpush(index, this, loaded);
	}
}