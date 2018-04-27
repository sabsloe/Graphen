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
    boolean ausgewaehlt = false;

    Controller controller;
    private double initX;
    private double initY;
    private boolean wirdGezogen;

    private Color color;
    private Color colorMarkiert;

    private Point2D dragAnchor;

    public KnotenV(Knoten knoten_, Controller controller_) {
        knoten = knoten_;
        controller = controller_;
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
                //System.out.println("Clicked on " + id + ", " + me.getClickCount() + "times");
                controller.knotenGeklickt(this);
                //the event will be passed only to the circle which is on front
                me.consume();
            });

        setOnMousePressed((MouseEvent me)-> {
                if (me.getEventType()==MouseEvent.MOUSE_PRESSED){

                    //when mouse is pressed, store initial position

                    initX = getTranslateX();
                    initY = getTranslateY();
                    dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());

                }
            }
        );

        setOnMouseDragged((MouseEvent me) -> {
                //System.out.println("mousedragged");
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();

                //calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;

                setTranslateX(newXPosition);
                setTranslateY(newYPosition);
                controller.knotenGezogen();
            });

        getChildren().addAll(circ, label);
    }

    public void markeSetzen()
    {
        if (ausgewaehlt == false)
        {
            circ.setFill(new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                        new Stop(0, Color.rgb(250, 250, 255)),
                        new Stop(1, colorMarkiert)
                    }));
            ausgewaehlt = true;        
        }
        else
        {
            ausgewaehlt = false;
            markeLoeschen();
        }

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
}
