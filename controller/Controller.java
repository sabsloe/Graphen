package controller;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.util.*;
import javafx.stage.Modality;

import javafx.stage.Stage;

// Eigene Importe
import model.*;
import view.*;

public class Controller
{
    private Stage stage;

    @FXML
    private StackPane stackpane;
    @FXML
    private TextField txtNeuerKnoten;
    @FXML 
    private Label ausgabe;
    @FXML
    private Label lblMatrix;
    @FXML
    private Label lblKnotenliste;
    @FXML
    private ToggleButton btnKanten;
    @FXML
    private TextField txtVon;
    @FXML
    private TextField txtNach;
    
    @FXML
    private Tab tabErstellen;
    @FXML
    private Tab tabDarstellung;

    private GraphV graphv;
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
        graphv = new GraphV(400,400, this, graph);
        stackpane.getChildren().add(graphv);
        graphv.knotenEinfuegen("a");
        graphv.knotenEinfuegen("b");
        graphv.kanteEinfuegen("a","b");  
        knotenAusgeben();
        matrixAusgeben();
    }

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
        optimiere();
        knotenAusgeben();
        matrixAusgeben();
    }

    @FXML
    void einfuegen(ActionEvent event) {
        String name = txtNeuerKnoten.getText();
        if (graph.knotennummerGeben(name) == -1) // Es darf keine zwei Knoten mit dem gleichen Namen geben!
        {
            graphv.knotenEinfuegen(name);
            matrixAusgeben();
            knotenAusgeben();
        }
        else
        {
            createAlert("Diesen Knoten gibt es schon!");
            
        }
        txtNeuerKnoten.clear();   
        optimiere();
    }

    void knotenAusgeben() {
        lblKnotenliste.setText(graph.alleKnoten()); 
    }

    public void knotenGeklickt(KnotenV knoten)
    {
        if (knotenGezogen == false) // Der Knoten wurde nur angeklickt, nicht verschoben
        {

            if (tabErstellen.isSelected()) // Wenn btnKanten ausgewählt ist, können Kanten eingefügt werden
            {
                if (ausgewaehlterKnoten == null)
                {
                    ausgewaehlterKnoten = knoten;
                    ausgewaehlterKnoten.markeSetzen();
                }
                else
                {
                    ausgewaehlterKnoten.markeLoeschen();
                    if (!knoten.equals(ausgewaehlterKnoten))
                    {
                        graphv.kanteEinfuegen(ausgewaehlterKnoten.getInhalt(), knoten.getInhalt()); 
                    }
                    matrixAusgeben();
                    ausgewaehlterKnoten = null;
                }
            }
            else
            {
                knoten.markeSetzen();
            }

        }
        knotenGezogen = false;

    }

    public void knotenGezogen()
    {
        knotenGezogen = true;
    }

    public  void matrixAusgeben()
    {
        boolean[][] matrix = graph.getAdjazenzmatrix(); 
        String a = "  ";

        for (int i = 0; i < graph.getKnotenAnzahl(); i++)
        { 
            a = a + i + " ";
        }
        a = a +"\n";
        for (int i = 0; i < graph.getKnotenAnzahl();i++)
        {
            a = a + i + " ";
            for (int j = 0; j < graph.getKnotenAnzahl(); j++) 
            {

                if (matrix[i][j])
                {
                    a = a + "x ";
                }
                else
                {
                    a = a + "o ";
                }

            }
            a = a + "\n";
        }
        lblMatrix.setText(a);

    }

    public void optimiere()
    {
        Layout sl = new Layout(graphv);
        sl.execute();
        matrixAusgeben();
        knotenAusgeben();
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
        graphv = new GraphV(stackpane.getWidth(),stackpane.getHeight(), this, graph);
        stackpane.getChildren().add(graphv);
        matrixAusgeben();
        knotenAusgeben();

    }

    public void tiefensuche(ActionEvent event)
    {

        ArrayList<String> reihenfolge = graph.tiefenSuche(txtVon.getText());
        String s = "";
        for (String i : reihenfolge)
        {
            s = s + " " + i;
        }
        ausgabe.setText(s);

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
    public void tabAusgewaehlt(ActionEvent event){
        System.out.println("Ereignis findet statt");
    }

    @FXML
    public void markenLoeschen(ActionEvent event){
        graphv.markierungenEntfernen();
    }
    
}