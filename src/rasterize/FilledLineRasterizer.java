package rasterize;
import static java.lang.Math.round;


public class FilledLineRasterizer extends LineRasterizer {
    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }


    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

        /* Pro vykreslení úsečky je použit Bresenhamův algoritmus.
   Tento algoritmus vychází z DDA algoritmu, narozdíl od něho se ale tento pohybuje jen v celých číslech.
   Funguje na principu hledání nejblížších bodů, které leží k úsečce.
 */
       if ((x1 == x2) && (y1 == y2)) {
            raster.setPixel(round(x1), round(y1), color.getRGB());

        } else {
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int rozdil = dx - dy;

            int posun_x, posun_y;

            if (x1 < x2) posun_x = 1;
            else posun_x = -1;
            if (y1 < y2) posun_y = 1;
            else posun_y = -1;

            while ((x1 != x2) || (y1 != y2)) {

                int p = 2 * rozdil;

                if (p > -dy) {
                    rozdil = rozdil - dy;
                    x1 = x1 + posun_x;
                }
                if (p < dx) {
                    rozdil = rozdil + dx;
                    y1 = y1 + posun_y;
                }
                raster.setPixel(round(x1), round(y1), color.getRGB());

            }
        }
    }
}






