package view;
import java.util.*;

public class Ablauf
{
    private ArrayList<KnotenV> ablauf;
    private int zaehler = 0;
    private GraphV graphV;

    public Ablauf(GraphV graphV_)
    {
        graphV = graphV_;
        ablauf = new ArrayList<KnotenV>();
    }

    public void ablaufEinlesen(String s)
    {
        System.out.println("Beim Einlesen: " + s);

        // String parsen

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            if (st.nextToken().equals("+"))
            {
                KnotenV k = graphV.sucheKnoten(st.nextToken());
                ablauf.add(k);
            }
        }

    }

    public void naechsterSchritt()
    {
        if (zaehler < ablauf.size())
        {
            KnotenV k = ablauf.get(zaehler);
            k.markeSetzen();
            zaehler++;
        }
    }

}
