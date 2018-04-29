package model;
/**
 * Knoten.
 */

public class Knoten
{
    //Attribute
    private String inhalt;
    private boolean markiert;

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

    public void setMarkiert(boolean wert)
    {
        markiert = wert;
    }
    
    public boolean getMarkiert()
    {
        return markiert;
    }

}
