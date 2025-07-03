package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        final int previousColor = paint.getColor();
        paint.setColor(c.getColor());
        c.getShape().accept(this);
        paint.setColor(previousColor);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        final Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Style.FILL);
        f.getShape().accept(this);
        paint.setStyle(previousStyle);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        final java.util.List<?extends Shape> shapes = g.getShapes();
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        //saves the canvas' current transform
        canvas.save();
        //moves the drawing origin
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        //resets to the saved transform so other shapes arent affected
        canvas.restore();
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        final Paint.Style previousStyle = paint.getStyle();

        //fill first
        paint.setStyle(Style.FILL);
        o.getShape().accept(this);

        //stroke
        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);

        // restore previous style
        paint.setStyle(previousStyle);

        return null;
    }

    @Override
    public Void onPolygon(final Polygon p) {
        final java.util.List<? extends Point> points = p.getPoints();
        if (points.size() < 2) return null;

        for (int i = 0; i < points.size(); i++) {
            final Point p1 = points.get(i);
            final Point p2 = points.get((i + 1) % points.size()); // wrap around to the first
            canvas.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), paint);
        }

        return null;
    }
}
//    @Override
//    public Void onPolygon(final Polygon s) {
//
//        final float[] pts = null;
//
//        canvas.drawLines(pts, paint);
//        return null;
//    }
//}
