package model;

import java.awt.*;

public class Line {

    private Point first;
    private Point last;
    private Color color;

    public Line(int x1, int y1, int x2, int y2, int color) {
        first = new Point(x1, y1);
        last = new Point(x2, y2);
        this.color = new Color(color);
    }

    public Line(Point p1, Point p2, int color) {
        first = p1;
        last = p2;
        this.color = new Color(color);
    }

    public Point getFirst() {
        return first;
    }

    public Point getLast() {
        return last;
    }

    public Color getColor() {
        return color;
    }
}
