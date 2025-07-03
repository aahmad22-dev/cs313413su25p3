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
        paint.setStyle(Style.FILL_AND_STROKE);
        f.getShape().accept(this);
        paint.setStyle(previousStyle);
        return null;
    }


    @Override
    public Void onGroup(final Group g) {
        for (int i = 0; i < g.getShapes().size(); i++) {
            g.getShapes().get(i).accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());  // restore original state
        return null;
    }


    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        final Style previousStyle = paint.getStyle();

        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(previousStyle);
//        //fill first
//        paint.setStyle(Style.FILL);
//        o.getShape().accept(this);
//
//        //stroke
//        paint.setStyle(Style.STROKE);
//        o.getShape().accept(this);
//
//        // restore previous style
//        paint.setStyle(previousStyle);

        return null;
    }

    @Override
    public Void onPolygon(final Polygon p) {
        final java.util.List<? extends Point> points = p.getPoints();
        if (points.size() < 2) return null;

        final float[] pts = new float[points.size() * 4];
        for (int i = 0; i < points.size(); i++) {
            final Point p1 = points.get(i);
            final Point p2 = points.get((i + 1) % points.size()); // wraps to first point
            pts[i * 4] = p1.getX();
            pts[i * 4 + 1] = p1.getY();
            pts[i * 4 + 2] = p2.getX();
            pts[i * 4 + 3] = p2.getY();
        }

        canvas.drawLines(pts, paint);
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
