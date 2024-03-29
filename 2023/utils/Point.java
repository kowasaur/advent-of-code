package utils;

import java.util.Objects;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point moved(Direction dir, int amount) {
        if (dir == Direction.UP) return new Point(x, y-amount);
        if (dir == Direction.DOWN) return new Point(x, y+amount);
        if (dir == Direction.LEFT) return new Point(x-amount, y);
        return new Point(x+amount, y);
    }

    public Point moved(Direction dir) {
        return moved(dir, 1);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        var other = (Point)obj;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
