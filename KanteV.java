import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;
import javafx.event.*;
import javafx.scene.*;

public class KanteV extends Line{
    KnotenV start;
    KnotenV ziel;
    Color color= Color.DARKGRAY;
    GraphV graphV;
    public KanteV(KnotenV start_, KnotenV ziel_, GraphV graphV_) {
        graphV = graphV_;
        start = start_;
        ziel = ziel_;

        startXProperty().bind( start.layoutXProperty().add(start.translateXProperty()).add(KnotenV.RADIUS));
        startYProperty().bind( start.layoutYProperty().add(start.translateYProperty()).add(KnotenV.RADIUS));

        endXProperty().bind( ziel.layoutXProperty().add( ziel.translateXProperty()).add(KnotenV.RADIUS));
        endYProperty().bind( ziel.layoutYProperty().add( ziel.translateYProperty()).add(KnotenV.RADIUS));

        setStroke(color);
        setStrokeWidth(2);
        setCursor(Cursor.HAND);
        setOnMouseClicked(e -> loeschen());

    }   

    public void loeschen()
    {
        graphV.kanteLoeschen(this);
    }

    public KnotenV getStart()
    {
        return start;
    }

    public KnotenV getZiel()
    {
        return ziel;
    }

}
