package view;
import java.util.*;
import javafx.scene.paint.*;

public class Ablauf
{
    private ArrayList<Schritt> ablauf;
    private int zaehler = 0;
    private GraphV graphV;

    public Ablauf(GraphV graphV_)
    {
        graphV = graphV_;
        ablauf = new ArrayList<Schritt>();
    }

    public void ablaufEinlesen(String s)
    {
        System.out.println("Beim Einlesen: " + s);

        // String parsen

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("+"))
            {
                KnotenV k = graphV.sucheKnoten(st.nextToken());
                ablauf.add(new Schritt(k,Color.GREEN));
            }
             if (token.equals("-"))
            {
                KnotenV k = graphV.sucheKnoten(st.nextToken());
                ablauf.add(new Schritt(k,Color.YELLOW));
            }
        }

    }

    public void naechsterSchritt()
    {
        if (zaehler < ablauf.size())
        {
            ablauf.get(zaehler).ausfuehren();
            zaehler++;
        }
    }

}
