package ua.griddynamics;

public class Coordinate {
    private final int x;
    private final int y;
    private final char mark;

    public Coordinate(int y, int x, char mark) {
        this.x = x;
        this.y = y;
        this.mark = mark;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getMark() {
        return mark;
    }
}
