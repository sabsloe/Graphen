package controller;

import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.util.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.value.*;

import javafx.scene.paint.*;

// Eigene Importe
import model.*;
import view.*;

public class Controller
{
    private Stage stage;
    
    //------------------ Graphische Ausgabe des Graphen
    @FXML
    private StackPane stackpane;

    //--------------- Menu
    
    @FXML
    private TabPane tabPane;
    
    //--------------------- Tab Erstellen

    @FXML
    private Tab tabErstellen;
   
    
    @FXML
    private TextField txtNeuerKnoten;


    //--------------------- Tab Darstellung    
    
    @FXML
    private Tab tabDarstellung;
      
    @FXML
    private Label lblMatrix;
    @FXML
    private Label lblKnotenliste;
    
    
    
    @FXML
    private Tab tabAlgo;
    
    @FXML
    private TextField txtVon;
    @FXML
    private TextField txtNach;

    //-------------------- Tab Breitensuche

    @FXML
    private TextField txtbreiteStart;
       

    
    //----------------------------------------------------
    
    private GraphV graphv;
    
    private Ablauf ablauf;
    
    private KnotenV ausgewaehlterKnoten;
    private boolean knotenGezogen = false;

    private Graph graph;

    public Controller()
    {
        graph = new Graph(20);
    }

    public void setStage(Stage stage_)
    {
        stage = stage_;
    }

    @FXML
    private void initialize()   {
        graphv = new GraphV(400,400, graph);
        stackpane.getChildren().add(graphv);
        graphv.knotenEinfuegen("a");
        graphv.knotenEinfuegen("b");
        graphv.kanteEinfuegen("a","b");  
        tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    tabGewechselt(t1);
                }
            }
        );
    }

    /*
     * Beispielgraphen einfügen / Landkarte
     */
    @FXML
    public void karte(ActionEvent event)
    {
        neu(event);
        graphv.knotenEinfuegen("DAH",180,200);
        graphv.knotenEinfuegen("M", 200, 300);
        graphv.knotenEinfuegen("A", 100, 200);
        graphv.knotenEinfuegen("IN", 200, 150);
        graphv.knotenEinfuegen("N", 200, 50);
        graphv.knotenEinfuegen("R",300,200);
        graphv.knotenEinfuegen("LA", 250,220);
        graphv.kanteEinfuegen("M", "DAH");
        graphv.kanteEinfuegen("DAH", "IN");
        graphv.kanteEinfuegen("IN", "N");
        graphv.kanteEinfuegen("M", "LA");
        graphv.kanteEinfuegen("LA","R");
        graphv.kanteEinfuegen("R","IN");
        graphv.kanteEinfuegen("A","IN");
        graphv.kanteEinfuegen("R", "N");
        graphv.kanteEinfuegen("M", "A");
        graphv.kanteEinfuegen("A", "N");
    }
       
    @FXML
    public void freunde(ActionEvent event)
    {
        neu(event);
        graphv.knotenEinfuegen("Alexej");
        graphv.knotenEinfuegen("Bert");
        graphv.knotenEinfuegen("Dagmar");
        graphv.knotenEinfuegen("Holger");
        graphv.knotenEinfuegen("Jörg");
        graphv.knotenEinfuegen("Karla");
        graphv.knotenEinfuegen("Paula");
  
        graphv.kanteEinfuegen("Karla", "Alexej");
        graphv.kanteEinfuegen("Karla", "Bert");
        graphv.kanteEinfuegen("Karla", "Paula");
        graphv.kanteEinfuegen("Paula", "Dagmar");
        graphv.kanteEinfuegen("Paula","Jörg");
        graphv.kanteEinfuegen("Jörg","Bert");
        graphv.kanteEinfuegen("Jörg","Holger");
      
    }
    

    /**
     *    Liest den Namen des neuen Knotens ein, erzeugt einen Knoten und fügt ihn in den Graphen ein.
     *    Falls es einen Knoten dieses Namens bereits gibt, wird ein Fehler angezeigt
     */

    @FXML
    void einfuegen(ActionEvent event) {
        String name = txtNeuerKnoten.getText();
        if (graph.knotennummerGeben(name) == -1) // Es darf keine zwei Knoten mit dem gleichen Namen geben!
        {
            graphv.knotenEinfuegen(name);
        }
        else
        {
            createAlert("Diesen Knoten gibt es schon!");
        }
        txtNeuerKnoten.clear();   
    }

    public void tabGewechselt(Tab tab) {

        if(tab == tabDarstellung)
        {
            lblKnotenliste.setText(graph.alleKnoten());
            String a = graph.adjazentMatrixGeben();
            lblMatrix.setText(a);
        }

        if (tab == tabErstellen)
        {
            graphv.setWirdErstellt(true);
        }
        else
        {
            graphv.setWirdErstellt(false);
        }
        
        if (tab == tabAlgo)
        {
            ablauf = new Ablauf(graphv);
            ablauf.ablaufEinlesen("");
        }

    }

    public Graph getGraph()
    {
        return graph;
    }

    @FXML
    public void neu(ActionEvent event)
    {
        stackpane.getChildren().clear();
        graph = new Graph(20);
        graphv = new GraphV(stackpane.getWidth(),stackpane.getHeight(), graph);
        stackpane.getChildren().add(graphv);
    }

    public void tiefensuche(ActionEvent event)
    {
        graphv.markierungenEntfernen();
        String start = txtVon.getText();
        KnotenV kv = graphv.sucheKnoten(start);
        kv.markeSetzen(Color.YELLOW);
        ArrayList<String> reihenfolge = graph.tiefenSuche(start);
        String s = "";
        for (String i : reihenfolge)
        {
            s = s + " " + i;
        }
 
        ablauf.ablaufEinlesen(s);
        
        
        //ablauf.naechsterSchritt(); // Der Startknoten sollte schon markiert sein!
    }

    protected Alert createAlert(String text) {
        AlertType type = AlertType.ERROR;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText(text);
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
        .filter(response -> response == ButtonType.OK)
        .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
    }

    @FXML
    public void markenLoeschen(ActionEvent event){
        graphv.markierungenEntfernen();
    }

    
    @FXML 
    public void naechsterSchritt(ActionEvent event)
    {
        ablauf.naechsterSchritt();
    }
    
    @FXML
    public void nachbarKnotenAusgeben(ActionEvent event)
    {
        String start = txtbreiteStart.getText();
        KnotenV kv = graphv.sucheKnoten(start);
        kv.markeSetzen(Color.YELLOW);
        ArrayList<Knoten> liste = graph.nachbarnGeben(start);
        for (Knoten k : liste)
        {
            String inhalt = k.getInhalt();
            graphv.sucheKnoten(inhalt).markeSetzen(Color.ORANGE);
                        
        }
    }
    
    @FXML
    public void breitenSucheStartknotenSetzen(ActionEvent event)
    {
        graphv.markierungenEntfernen();
        String start = txtbreiteStart.getText();
        KnotenV kv = graphv.sucheKnoten(start);
        kv.markeSetzen(Color.YELLOW);
       
        
    }
    
}