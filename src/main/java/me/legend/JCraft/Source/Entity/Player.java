package me.legend.JCraft.Source.Entity;

import java.util.ArrayList;

public class Player extends Entity {
    public static ArrayList<Player> players = new ArrayList<Player>();

    // Todo Move the players arraylist into the world class or somewhere that isn't inside of the fucking player class

    private String name;

    public Player(String name, int i, double x, double y, double z) {
        this(name, i, x, y, z, 0, 0);
    }

    public Player(String name, int i, double x, double y, double z, float yaw, float pitch) {
        super(i, "SUPER", x, y, z);
        players.add(this);
    }
}
