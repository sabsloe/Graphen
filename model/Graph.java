package model; 
import java.util.*;
/**
 * Graph.
 */

public class Graph
{
    //Attribute
    private Knoten[] knotenliste;
    private int maxAnzahl;
    private int anzahl;

    private boolean[][] adjazenzmatrix;

    //Konstruktor
    public Graph(int maxAnzahl)
    {
        this.maxAnzahl = maxAnzahl;
        anzahl = 0;
        knotenliste = new Knoten[maxAnzahl];
        adjazenzmatrix = new boolean[maxAnzahl][maxAnzahl];
    }

    //Methoden
    /*
     * Erzeugt einen neuen Knoten mit dem übergebenen Inhalt und fügt ihn in die Knotenliste ein
     * und liefert diesen Knoten zurück.
     */
    public void  knotenEinfuegen(String inhalt)
    {

        if (anzahl < maxAnzahl)
        {   
            knotenliste[anzahl] = new Knoten(inhalt);
            anzahl++;
        }        
    }

    /*
     * Prüft ob der übergebene Inhalt in einem Knoten gespeichert ist und gibt dessen Nummer zurück.
     */
    public int knotennummerGeben(String inhalt)
    {
        for (int i = 0; i < anzahl; i++)
        {
            if (knotenliste[i].getInhalt().equals(inhalt))
            {
                return i;
            }
        }
        return -1;
    }         

    /*
     * Gibt eine Liste mit allen Knotennamen als String zurücke
     */    
    public String alleKnoten()
    {
        String s = "";
        for ( int i = 0; i < anzahl; i++)
        {
            s = s + i + " " + knotenliste[i].getInhalt() + "\n";
        }

        return s;
    }

    /*
     * Fügt eine Kante zwischen den Knoten mit den Inhalten bez1 und bez2 ein.
     */
    public void kanteEinfuegen(String bez1, String bez2)
    {
        int i = knotennummerGeben(bez1);
        int j = knotennummerGeben(bez2);
        if (j != -1 && i != -1) // Es gibt beide Knoten!
        {
            adjazenzmatrix[i][j] = true;
        }
        else
        {
            System.out.println("Fehler! Einen Knoten gibt es nicht!");
        }
    }

    /*
     * Fügt eine ungerichtete Kante ein
     */
    public void ungerichteteKanteEinfuegen(String bez1, String bez2)
    {
        kanteEinfuegen(bez1,bez2);
        kanteEinfuegen(bez2,bez1);
    }

    /* 
     * Entfernt eine Kante zwischen zwei Knoten
     */
    public void kanteEntfernen(String bez1, String bez2)
    {
        int i = knotennummerGeben(bez1);
        int j = knotennummerGeben(bez2);
        if (j != -1 && i != -1) // Es gibt beide Knoten!
        {
            adjazenzmatrix[i][j] = false;
        }
        else
        {
            System.out.println("Fehler! Einen Knoten gibt es nicht!");
        }
    }
    
    public boolean istKante(String s1, String s2)
    {
        int i = knotennummerGeben(s1);
        int j = knotennummerGeben(s2);
        return adjazenzmatrix[i][j];

    }
  
    public Knoten[] getKnotenliste()
    {
        return knotenliste;
    }

    public boolean[][] getAdjazenzmatrix()
    {
        return adjazenzmatrix;
    }

    public int getKnotenAnzahl()
    {
        return anzahl;
    }

    public Knoten getKnoten(String a)
    {
        for (Knoten knoten: knotenliste)
        {
            if (knoten.getInhalt().equals(a))
            {
                return knoten;
            }
        }
        return null;
    }
    
    public ArrayList<String> tiefenSuche(String a)
    {
        ArrayList<String> reihenfolge = new ArrayList<String>();
        int k1 = knotennummerGeben(a);
        // Alle Knoten als unbesucht markieren
        for (int i = 0; i < anzahl; i++)
        {
            knotenliste[i].setMarkiert(false);
        }
        
        
        //Aufruf der rekursiven Suchmethode
        tiefensucheKnoten(k1,reihenfolge);
        return reihenfolge;
        
    }
    
    public void tiefensucheKnoten(int start, ArrayList<String> reihenfolge)
    {
        //System.out.println("Knoten " + knotenliste[start].getInhalt());
        reihenfolge.add("+ " + knotenliste[start].getInhalt());
        knotenliste[start].setMarkiert(true);
        
        for (int i = 0; i < anzahl; i++)
        {
            if (adjazenzmatrix[start][i] && !knotenliste[i].getMarkiert())
            {
                tiefensucheKnoten(i,reihenfolge);
            }
        }
        reihenfolge.add("-" + knotenliste[start].getInhalt());
    }
        
    
    
}
