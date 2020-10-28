package rasterize;

import model.Line;
import model.Point;
import model.Polygon;

import java.util.List;

public class PolygonRasterizer {
    private LineRasterizer rasterizer;

    public PolygonRasterizer(LineRasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }

    public void rasterize(Polygon polygon)
    {
        List<Line> lines= polygon.getLines();
        for(Line line:lines) {
            rasterizer.rasterize(line);
        }
    }
}
