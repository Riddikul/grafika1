package view;

import model.Point;
import model.Polygon;

import rasterize.*;

import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Main{
    private JPanel panel;
    private Polygon polygon;

    private FilledLineRasterizer rasterizer;
    private RasterBufferedImage raster;
    private PolygonRasterizer polygonRasterizer;
    ArrayList<Point> points = new ArrayList<>();
    private int x,y, x2, y2;
    private DashedLineRasterizer dashedLineRasterizer;

    int[] poleX = new int[50];
    int[] poleX2 = new int[50];
    int[] poleY = new int[50];
    int[] poleY2 = new int[50];

    int i=0;

/**
@auauthor Martin Ambrož 2020/2021
 - s využítím souboru task1
   */


    public Main(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.setTitle("UHK FIM PGRF : Martin Ambrož 20/21");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        rasterizer = new FilledLineRasterizer(raster);
        dashedLineRasterizer = new DashedLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(rasterizer);
        polygon = new Polygon(points,0x00ff0);
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (panel.getWidth()<1 || panel.getHeight()<1)
                    return;
                if (panel.getWidth()<=raster.getWidth() && panel.getHeight()<=raster.getHeight()) //no resize if new one is smaller
                    return;
                RasterBufferedImage newRaster = new RasterBufferedImage(panel.getWidth(), panel.getHeight());

                newRaster.draw(raster);
                raster = newRaster;
                rasterizer = new FilledLineRasterizer(raster);


            }
        });


        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_C)
                {
                    clear(0x000000);


                    for(int i=0;i<30;i++)
                    {
                        poleX[i] = 0;
                        poleX2[i] = 0;
                        poleY[i] = 0;poleY2[i] = 0;

                    }

                    panel.repaint();

                }
            }
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear(0x000000);

                x2 = e.getX();
                y2 = e.getY();

                drawDashed();

                int j =0;
                while (j<30)
                {
                   rasterizer.rasterize(poleX[j], poleY[j], poleX2[j], poleY2[j],new Color(0x0000FF));
                    panel.repaint();
                    j++;
                }

            }});


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                   x = e.getX();
                    y = e.getY();

                }
            }



            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (e.getButton() == MouseEvent.BUTTON1) {
                clear(0x000000);

                   x2 = e.getX();
                    y2 = e.getY();

                   if(i<30) {
                        poleX[i] = x;
                        poleX2[i] = x2;
                        poleY[i] = y;
                        poleY2[i] = y2;
                        i++;

                    }

                    int j =0;

                   while (j<30)
                    {
                        rasterizer.rasterize(poleX[j], poleY[j], poleX2[j], poleY2[j],new Color(0x0000FF));

                            panel.repaint();
                                j++;
                    }

                }
            }


            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1)
                {

                  int size = 2;
                    int color  = 0xFFFFFF;
                    for(int i=-size; i<=size; i++)
                        for(int j=-size; j<=size; j++)
                            raster.setPixel(e.getX()+i, e.getY()+j, color);
                    points.add(new Point(e.getX(),e.getY()));


                }
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    polygon = new Polygon(points,0x00ff0);
                    polygonRasterizer.rasterize(polygon);
                    points.clear();


                }
                panel.repaint();
            }



          });

    }
public void drawDashed()
{

    dashedLineRasterizer.rasterize(x,y,x2,y2, new Color(0xF9C846));
    panel.repaint();
}

    public void draw() {
        rasterizer.rasterize(x,y,x2,y2, new Color(0x0000FF));
        panel.repaint();
    }


    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();

    }


    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }


    public void start() {
        clear(0xaaaaaa);
        raster.getGraphics().drawString("Stiskne se levé tlačíko a drží, při posunu se bude ukazovat čárkovaná přímka, po povolení tlačíka se zobrazí plná přímka.", 5, 15);
        raster.getGraphics().drawString("Kliknutím na levé tlačítko se vyznačí body pro polygon, když se zmáčkne pravé, polygon se vykreslí.", 5, 40);
        raster.getGraphics().drawString("Stiskem tlačítka C se vše obrazovka vymaže.", 5, 65);
        draw();
        panel.repaint();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main(800, 600).start());


    }
}
