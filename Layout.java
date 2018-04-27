import java.util.*;
import javafx.geometry.*;

public class Layout {
    GraphV graph;
    ArrayList<KnotenV> knotenliste;
    double xMax;
    double yMax;
    public Layout(GraphV graph) {
        this.graph = graph;
        xMax = graph.getWidth() - KnotenV.RADIUS;
        yMax = graph.getHeight() - KnotenV.RADIUS;
        knotenliste = graph.getKnotenliste();
    }

    public void execute() {
        //Parameter für die Springs
        double c1 = 2; 
        double c2 = 1;
        double c3 = 400000;
        double c4 = 0.1;

        for (int i = 0; i < 150; i++)
        {
            for (KnotenV knoten : knotenliste) {

                for (KnotenV knoten2 : knotenliste)
                {
                    Point2D a = new Point2D(knoten.getTranslateX(),knoten.getTranslateY());
                    Point2D b = new Point2D(knoten2.getTranslateX(),knoten2.getTranslateY());
                    double d = a.distance(b);

                    if (d != 0)
                    {
                        if (graph.istKante(knoten,knoten2))
                        {
                            c1 = 2;
                        }
                        else 

                        {
                            c1 = 1;
                        }
                        double f1 = c1 * Math.log(d/c2);  // Feder

                        double f2 = c3 / (d*d);             // Zwei Knoten sollen sich nicht zu nahe kommen
                        double faktor = (f1-f2)*c4;
                        Point2D richtung = (b.subtract(a)).normalize();

                        a = a.add(richtung.multiply(faktor));
                        // Im Fenster bleiben!
                        if ( a.getX() > xMax)
                        {

                            a = new Point2D(xMax,a.getY());
                        }
                        if( a.getY() > yMax)
                        {
                            a = new Point2D(a.getX(),yMax);
                        }
                        if ( a.getX() < 0)
                        {

                            a = new Point2D(0,a.getY());
                        }
                        if( a.getY() < 0)
                        {
                            a = new Point2D(a.getX(), 0);
                        }
                        knoten.setTranslateX(a.getX());
                        knoten.setTranslateY(a.getY());

                    }
                }
            }
        }
    }

}
