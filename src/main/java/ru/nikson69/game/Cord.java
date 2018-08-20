package ru.nikson69.game;

public class Cord {
    public final int x;
    public final int y;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cord) {
            Cord to = (Cord) o;
            return (to.x == x) && (to.y == y);
        }
        return super.equals(o);
    }
}
