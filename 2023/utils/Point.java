package utils;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point moved(Direction dir) {
        if (dir == Direction.UP) return new Point(x, y-1);
        if (dir == Direction.DOWN) return new Point(x, y+1);
        if (dir == Direction.LEFT) return new Point(x-1, y);
        return new Point(x+1, y);
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
}
