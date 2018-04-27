import java.util.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.layout.*;

public class GraphV extends Pane
{

    Graph graph;
    
    ArrayList<KnotenV> knotenliste;
    ArrayList<KanteV> kantenliste;

    Controller controller;

    public GraphV(double breite, double hoehe, Controller controller_, Graph graph_){
        graph = graph_;
        knotenliste = new ArrayList<KnotenV>();
        kantenliste = new ArrayList<KanteV>();
        setWidth(breite);
        setHeight(hoehe);
        controller = controller_;
        setStyle("-fx-background-color: white");

    }

    public void knotenEinfuegen(String a)
    {
        graph.knotenEinfuegen(a);
        Knoten k = graph.getKnoten(a);
        KnotenV knotenV = new KnotenV(k,controller);
        knotenV.setTranslateX(100 + Math.random()*50);
        knotenV.setTranslateY(100 + Math.random()*50);
        knotenliste.add(knotenV);
        getChildren().add(knotenV);
    }
    public void knotenEinfuegen(String a, double x, double y)
    {
        graph.knotenEinfuegen(a);
        Knoten k = graph.getKnoten(a);
        KnotenV knotenV = new KnotenV(k,controller);
        knotenV.setTranslateX(x);
        knotenV.setTranslateY(y);
        knotenliste.add(knotenV);
        getChildren().add(knotenV);
    }

    public KnotenV sucheKnoten(String inhalt)
    {
        for (KnotenV knotenV : knotenliste)
        {
            if (inhalt.equals(knotenV.getInhalt()))
            {
                return knotenV;
            }
        }
        return null;
    }

    public void kanteEinfuegen(String a, String b)
    {
        KnotenV k1 = sucheKnoten(a);
        KnotenV k2 = sucheKnoten(b);
        if (a != null && b != null)
        {
            graph.kanteEinfuegen(a,b);
            graph.kanteEinfuegen(b,a);
            KanteV kante = new KanteV(k1,k2, this);
            kantenliste.add(kante);
            getChildren().add(kante); 
            k1.toFront();
            k2.toFront();

        }
        controller.optimiere();
    }

    public void kanteLoeschen(KanteV kante)
    {
        graph.kanteEntfernen(kante.getStart().getInhalt(), kante.getZiel().getInhalt());
        graph.kanteEntfernen( kante.getZiel().getInhalt(),kante.getStart().getInhalt());
        getChildren().remove(kante);
        kantenliste.remove(kante);
        controller.optimiere();
    }
    public ArrayList<KnotenV> getKnotenliste()
    {
        return knotenliste;
    }
    
    public boolean istKante(KnotenV k1, KnotenV k2)
    {
        return graph.istKante(k1.getInhalt(), k2.getInhalt());
    }
    
    public int knotenAnzahl()
    {
        return knotenliste.size();
    }
}
