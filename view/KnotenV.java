package view;

import model.*;
import controller.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;
import javafx.event.*;

import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.InnerShadow;

public class KnotenV extends StackPane {
    public final static int RADIUS = 40;
    
    Knoten knoten;
    Circle circ;

    Controller controller;
    private double initX;
    private double initY;
    private boolean wirdGezogen = false;

    private Color color;
    private Color colorMarkiert;

    private GraphV graphV;

    private Point2D dragAnchor;

    public KnotenV(Knoten knoten_, Controller controller_, GraphV graphV_) {
        knoten = knoten_;
        controller = controller_;
        graphV = graphV_;
        
        Label label = new Label(knoten.getInhalt());

        color = Color.CORAL;
        colorMarkiert = Color.DODGERBLUE;

        circ = new Circle(RADIUS);
        markeLoeschen();
        circ.setEffect(new InnerShadow(7, color.darker().darker()));

        //change a cursor when it is over circle
        setCursor(Cursor.HAND);

        // Wenn der Knoten mit der Maus angeklickt oder gezogen wird
        setOnMouseClicked((MouseEvent me) -> { 
                if (wirdGezogen == false) // Der Knoten wurde nur angeklickt, nicht verschoben
                {
                    if (graphV.wirdErstellt()) // Wenn der Graph gerade im Zustand wirdErstellt ist 
                    {
                        if (graphV.getAusgewaehlterKnoten() == null)
                        {
                            graphV.setAusgewaehlterKnoten(this);
                            markeSetzen();
                        }
                        else
                        {
                            graphV.getAusgewaehlterKnoten().markeLoeschen();
                            if (!this.equals(graphV.getAusgewaehlterKnoten()))
                            {
                                graphV.kanteEinfuegen(graphV.getAusgewaehlterKnoten().getInhalt(), this.getInhalt()); 
                            }
                            graphV.setAusgewaehlterKnoten(null);
                        }
                    }
                    else
                    {
                        markeSetzen();
                    }

                }
                wirdGezogen = false;

                me.consume();
            });

        // wenn die Maus gedrückt wurde, wird die Anfangsposition gespeichert
        setOnMousePressed((MouseEvent me)-> {
                if (me.getEventType()==MouseEvent.MOUSE_PRESSED){
                    initX = getTranslateX();
                    initY = getTranslateY();
                    dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
                }
            }
        );

        setOnMouseDragged((MouseEvent me) -> {
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();

                //calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;

                setTranslateX(newXPosition);
                setTranslateY(newYPosition);
                wirdGezogen = true;
            });

        getChildren().addAll(circ, label);
    }

    public void markeSetzen()
    {
        circ.setFill(new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.rgb(250, 250, 255)),
                    new Stop(1, colorMarkiert)
                }));       
    }

    public void markeLoeschen()
    {
        circ.setFill(new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.rgb(250, 250, 255)),
                    new Stop(1, color)
                }));
    }

    public String getInhalt()
    {
        return knoten.getInhalt();
    }

    public void aktualisieren()
    {
        if (knoten.getMarkiert())
        {
            markeSetzen();
        }
        else
        {
            markeLoeschen();
        }
    }

}
