
/**
 * Knoten.
 */

public class Knoten
{
    //Attribute
    private String inhalt;
    private boolean marke;

    //Konstruktor
    public Knoten(String inhalt)
    {
        this.inhalt = inhalt;        
    }

    //Methoden
    public String getInhalt()
    {
        return inhalt;
    }

    public void setMarke(boolean wert)
    {
        marke = wert;
    }
    public boolean getMarke()
    {
        return marke;
    }

}
