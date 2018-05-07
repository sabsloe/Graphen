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
        int schritt = 0;
        KnotenV alterKnoten = null;
        String letztesZeichen = "";
        System.out.println(s);
        // String parsen

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {

            String token = st.nextToken();
            
            if (token.equals("+"))
            {
                if (alterKnoten != null && letztesZeichen.equals("+"))
                {
                    ablauf.add(new Schritt(alterKnoten, Color.LIGHTGREEN, schritt));
                }
                if (alterKnoten != null && letztesZeichen.equals("-"))
                {
                    ablauf.add(new Schritt(alterKnoten, Color.LIGHTBLUE, schritt));
                }
                
                letztesZeichen = token;

                KnotenV k = graphV.sucheKnoten(st.nextToken());
                ablauf.add(new Schritt(k,Color.GREEN, schritt));
                schritt++;
                alterKnoten = k;

            }
            
            if (token.equals("-"))
            {
                KnotenV k = graphV.sucheKnoten(st.nextToken());
                if (letztesZeichen.equals("-"))
                {
                    ablauf.add(new Schritt(alterKnoten, Color.LIGHTBLUE, schritt));
                        ablauf.add(new Schritt(k,Color.GREEN, schritt));
                         schritt++;
                }
                if (letztesZeichen.equals("+"))
                {
                    ablauf.add(new Schritt(alterKnoten,Color.LIGHTBLUE, schritt));
                    ablauf.add(new Schritt(k,Color.GREEN, schritt));
                    schritt++;
                }
                    
                
                
            
                
                alterKnoten = k;
                letztesZeichen = token;
               

            }

        }

    }

    public void naechsterSchritt()
    {
        int maxAnzahl = ablauf.size();

        if (zaehler >= maxAnzahl)  // Abbruch, wenn der letzte Schritt erreicht ist.
        {
            return;
        }
        Schritt schritt = ablauf.get(zaehler);
        int nummer = schritt.getNummer();
        schritt.ausfuehren();
        zaehler++;

        if (zaehler < maxAnzahl)
        {
            if (ablauf.get(zaehler).getNummer() == nummer)
            {
                naechsterSchritt(); // Falls es noch einen weiteren Schritt mit dieser Nummer gibt, soll er sofort ausgeführt werden
            }
        }
    }

}
