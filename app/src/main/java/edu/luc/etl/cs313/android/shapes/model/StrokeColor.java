package edu.luc.etl.cs313.android.shapes.model;

import java.util.StringJoiner;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class StrokeColor implements Shape {

    // TODO entirely your job

    private final int color;
    private final Shape shape;

    public StrokeColor(final int color, final Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    public int getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public <Result> Result accept(Visitor<Result> v) {
        return v.onStrokeColor(this);
    }
}
