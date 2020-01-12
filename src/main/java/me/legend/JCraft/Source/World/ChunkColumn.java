package me.legend.JCraft.Source.World;

import com.github.steveice10.mc.protocol.data.game.Chunk;
import me.legend.JCraft.Source.Exceptions.InvalidChunkSectionException;
import me.legend.JCraft.Source.Exceptions.InvalidSectionSizeException;

public class ChunkColumn {
    public Chunk[] sections = new Chunk[16];
    public int chunkX, chunkZ;

    public ChunkColumn(int x, int z, Chunk[] sections) throws InvalidSectionSizeException {
        // if(sections.length != 16) throw new InvalidSectionSizeException(sections.length, 16);
        this.chunkX = x;
        this.chunkZ = z;
        for(int currentSectionY = 0; currentSectionY < sections.length; currentSectionY++) {
            Chunk currentSection = sections[currentSectionY];
            if (currentSection == null || currentSection.getBlocks() == null) { // LeTs MakE AiR ChuNKs NuLl To SavE SpAcE ~ Mojang
                currentSection = new Chunk(true);
                currentSection.getBlocks().fill(0);
            }
            this.sections[currentSectionY] = currentSection;
        }
    }

    public Chunk getSection(int sectionY) throws InvalidChunkSectionException {
        if(sectionY < 0 || sectionY > sections.length - 1) throw new InvalidChunkSectionException(sectionY, 0, sections.length - 1);
        return this.sections[sectionY];
    }

}
