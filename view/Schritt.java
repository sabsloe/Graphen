package view; 

import javafx.scene.paint.*;

public class Schritt
{
    private KnotenV knoten;
    private Color farbe;
    private int nummer;

    public Schritt(KnotenV k, Color f, int i)
    {
        knoten = k;
        farbe = f;
        nummer = i;
    }

    public void ausfuehren()
    {
        knoten.markeSetzen(farbe);
    }

    public int getNummer()
    {
        return nummer;
    }

}
