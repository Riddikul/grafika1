package rasterize;


public class DashedLineRasterizer extends LineRasterizer {
    public DashedLineRasterizer(Raster raster) {
        super(raster);
    }
public void drawLine(int x1,int y1, int x2, int y2)
{

        float dx, dy, len, x, y;
        int i;

        dx = Math.abs(x2 - x1);
        dy = Math.abs(y2 - y1);


        if (dx >= dy)

            len = dx;

        else

            len = dy;


        dx = (x2 - x1) / len;
        dy = (y2 - y1) / len;


        x = (float) (x1 + 1.5);
        y = (float) (y1 + 1.5);

        raster.setPixel((int)x, (int)y, color.getRGB());
        raster.setPixel((int)x,(int) y, color.getRGB());

        for (i = 0; i <= len; i++) {
            if (i % 9 < 2) {
            } else if (i % 9 < 6)

                raster.setPixel((int)x, (int)y, color.getRGB());

            else if (i % 9 == 7) {
            } else

                raster.setPixel((int)x, (int)y, color.getRGB());

            x += dx;
            y += dy;

        }

    }
}
