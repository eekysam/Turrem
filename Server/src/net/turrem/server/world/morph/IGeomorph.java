package net.turrem.server.world.morph;

import net.turrem.server.world.mesh.VertexGenData;
import net.turrem.server.world.mesh.VertexGenDataWork;
import net.turrem.server.world.mesh.WorldVertex;

public interface IGeomorph
{
	public void generateUpgrade(VertexGenDataWork newData, VertexGenData oldData, WorldVertex vertex);
}