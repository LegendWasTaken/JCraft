package me.legend.JCraft.Source.Entity;

import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.Util.Vector3d;

import java.util.HashMap;

public class Entity {

    private int entityId;

    private String type;

    private EntityPosition location;

    public Entity(int i, String type, double x, double y, double z, float yaw, float pitch) {
        this.entityId = i;
        this.type = type;
        this.location = new EntityPosition(x, y, z, yaw, pitch);
    }

    public Entity(int i, String type, double x, double y, double z) { this(i, type, x, y, z, 0, 0); }

    public EntityPosition getLocation() { return location; }
    public Integer getEntityID() { return this.entityId; }
}
