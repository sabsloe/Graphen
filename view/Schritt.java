package view; 

import javafx.scene.paint.*;


public class Schritt
{
    private KnotenV knoten;
    private Color farbe;
    
    public Schritt(KnotenV k, Color f)
    {
        knoten = k;
        farbe = f;
    }
    public void ausfuehren()
    {
        knoten.markeSetzen(farbe);
    }
    
        
}
