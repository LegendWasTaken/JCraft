package me.legend.JCraft.Source.Entity;

import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.Util.Vector3d;

import java.util.HashMap;

public class Entity {
    private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

    // Todo move the entity list to somewhere that isn't here. as it makes 0 fucking sense, not to mention with multiple bots it could cause a potential desync so....

    private int entityId;

    private String type;

    public EntityPosition location;

    public Entity(int i, String type, double x, double y, double z, float yaw, float pitch) {
        this.entityId = i;
        this.type = type;
        this.location = new EntityPosition(x, y, z, yaw, pitch);
        entities.put(entityId, this);
    }

    public Entity(int i, String type, double x, double y, double z) { this(i, type, x, y, z, 0, 0); }

    public void remove() { entities.remove(this.entityId); }

    public static Entity byId(int id) { return entities.get(id); }

    public EntityPosition getLocation() { return location; }
}
