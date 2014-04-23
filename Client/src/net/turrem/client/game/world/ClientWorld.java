package net.turrem.client.game.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.turrem.client.Turrem;
import net.turrem.client.game.world.material.Material;
import net.turrem.client.network.server.ServerPacket;
import net.turrem.client.network.server.ServerPacketManager;
import net.turrem.client.network.server.ServerPacketMaterialSync;
import net.turrem.client.network.server.ServerPacketTerrain;
import net.turrem.client.render.engine.RenderEngine;

public class ClientWorld
{
	public HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	public ClientWorld()
	{

	}

	public void render()
	{
		Collection<Chunk> set = this.chunks.values();
		for (Chunk chunk : set)
		{
			chunk.render();
		}
	}
	
	public long getIndex(int chunkx, int chunkz)
	{
		return (((long)chunkx) << 32) | (chunkz & 0xffffffffL);
	}
	
	public Chunk getChunk(int chunkx, int chunkz)
	{
		long l = this.getIndex(chunkx, chunkz);
		return this.chunks.get(l);
	}
	
	public int getHeight(int x, int z, int empty)
	{
		Chunk c = this.getChunk(x >> 4, z >> 4);
		if (c == null)
		{
			return empty;
		}
		return c.getHeight(x, z);
	}
	
	public int getHeight(int x, int z)
	{
		return this.getHeight(x, z, -1);
	}

	public void testNetwork()
	{
		try
		{
			if (Turrem.networkLoc != null)
			{
				File file = new File(Turrem.networkLoc);
				if (!file.exists())
				{
					return;
				}
				FileInputStream in = new FileInputStream(file);
				this.network(in);
				in.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void network(InputStream in) throws IOException
	{
		ArrayList<ServerPacket> packets = new ArrayList<ServerPacket>();
		while (in.available() > 0)
		{
			packets.add(ServerPacketManager.readSinglePacket(in));
		}
		for (ServerPacket pack : packets)
		{
			if (pack instanceof ServerPacketMaterialSync)
			{
				ServerPacketMaterialSync sync = (ServerPacketMaterialSync) pack;
				Material.numidmap.put(sync.num, sync.id);
			}
			if (pack instanceof ServerPacketTerrain)
			{
				ServerPacketTerrain terr = (ServerPacketTerrain) pack;
				Chunk c = terr.buildChunk();
				long l = this.getIndex(c.chunkx, c.chunkz);
				this.chunks.put(l, c);
			}
		}
	}
	
	public void loadChunkRenders(RenderEngine engine)
	{
		Collection<Chunk> set = this.chunks.values();
		for (Chunk chunk : set)
		{
			chunk.buildRender(engine);
		}
	}
}