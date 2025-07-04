package edu.luc.etl.cs313.android.shapes.android;

import edu.luc.etl.cs313.android.shapes.model.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawWidget extends View {

    private final Paint paint = new Paint();

    public DrawWidget(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawWidget(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawWidget(final Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    
    // TODO once BoundingBox and Draw are implemented, change Fixtures.simpleCircle
    // to Fixtures.complexGroup and test the app on an emulator or Android device
    // to make sure the correct figure is drawn (see Project 3 description for link)

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(final Canvas canvas) {
        final Shape original = Fixtures.complexGroup; // or simpleCircle for test
        final Location b = original.accept(new BoundingBox());

        // Wrap the original shape in a location that offsets it
        final Location shifted = new Location(-b.getX(), -b.getY(), original);

        shifted.accept(new Draw(canvas, paint));

//        final var shape = Fixtures.simpleCircle;
//        final var b = shape.accept(new BoundingBox());
//        canvas.translate(-b.getX(), -b.getY());
//        b.accept(new Draw(canvas, paint));
//        shape.accept(new Draw(canvas, paint));
//        canvas.translate(b.getX(), b.getY());
    }
}
