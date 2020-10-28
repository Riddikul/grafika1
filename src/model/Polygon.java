package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point> points;
    private Color color;

    public Polygon(List<Point> points, int color) {
        this.points = points;
        this.color = new Color(color);
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Line> getLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for(int i=0; i<points.size(); i++) {
            Point start = points.get(i);
            Point end = points.get((i+1)%points.size());
            lines.add(new Line(start, end, color.getRGB()));
        }
        return lines;
    }
}
