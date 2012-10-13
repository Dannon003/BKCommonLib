package com.bergerkiller.bukkit.common.reflection.classes;

import com.bergerkiller.bukkit.common.reflection.ClassTemplate;
import com.bergerkiller.bukkit.common.reflection.SafeField;

import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.IChunkLoader;

public class ChunkProviderServerRef {
	public static final ClassTemplate<ChunkProviderServer> TEMPLATE = ClassTemplate.create(ChunkProviderServer.class);
	public static final SafeField<IChunkLoader> chunkLoader = TEMPLATE.getField("e");
}