package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        if(g.getShapes().isEmpty()) {
            return new Location(0, 0, new Rectangle(0, 0));
        }
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;


        for (int i = 0; i < g.getShapes().size(); i ++) {
            Location loc = g.getShapes().get(i).accept(this);
            if (loc == null) continue; // skip is not handled
//            int x = loc.getX();
//            int y = loc.getY();
//            Rectangle r = (Rectangle) loc.getShape();

            int x = loc.getX();
            int y = loc.getY();
            int w = ((Rectangle) loc.getShape()).getWidth();
            int h = ((Rectangle) loc.getShape()).getHeight();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x + w);
            maxY = Math.max(maxY, y + h);
//            maxX = Math.max(maxX, x + r.getWidth());
//            maxY = Math.max(maxY, y + r.getHeight());
        }
//        int width = maxX - minX;
//        int height = maxY = minY;
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        // Visit the inner shape to get its bounding box
        Location innerBox = l.getShape().accept(this);

        //Offset the inner bounding box by the location's x/y

        int offsetX = l.getX() + innerBox.getX();
        int offsetY = l.getY() + innerBox.getY();
        Rectangle r = (Rectangle) innerBox.getShape();
        return new Location(offsetX, offsetY, r);

//        int newX = l.getX() + innerBox.getX();
//        int newY = l.getY() + innerBox.getY();
//        return new Location(newX, newY, innerBox.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        //top left corner x = 0, y = 0
        //r is the shape itself
        return new Location(0, 0, r);
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

//        List<Point> points = s.getPoints();
        for(int i = 0; i < s.getPoints().size(); i++) {
            Point p = s.getPoints().get(i);
            int x = p.getX();
            int y = p.getY();

//            minX = Math.min(minX, x);
//            minY = Math.min(minY, y);
//
//            maxX = Math.max(maxX, x);
//            maxY = Math.max(maxY, y);
            if(x < minX) minX = x;
            if(y < minY) minY = y;

            if(x > maxX) maxX = x;
            if(y > maxY) maxY = y;
        }

        int width = maxX - minX;
        int height = maxY - minY;
        return new Location(minX, minY, new Rectangle(width, height));
    }
}
