package com.fmedlin.digest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathMeasure;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class JuggleWidget extends View {

    private static final Float ADVANCE = 10f;

    Path path;
    PathMeasure pathMeasure;
    List<Satellite> satellites;
    float[] coord = new float[2];
    float offset = 0.0f;

    public JuggleWidget(Context context) {
        this(context, null);
    }

    public JuggleWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JuggleWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w2 = getWidth() / 2f;
        float h2 = getHeight() / 2f;
        if (path == null) {
            path = setupPath(w2, h2, w2);
        }

        if (satellites == null) {
            return;
        }

        for (Satellite satellite : satellites) {
            float pathLength = pathMeasure.getLength();
            float position = satellite.getPosition() + offset;

            if (position > pathLength) {
                position -= pathLength;
            }

            pathMeasure.getPosTan(position, coord, null);
            canvas.drawBitmap(satellite.bitmap,
                    coord[0] - satellite.bitmap.getWidth() / 2,
                    coord[1] - satellite.bitmap.getHeight() / 2,
                    null);
        }
    }

    private Path setupPath(float x, float y, float radius) {
        path = new Path();
        path.addCircle(x, y, radius, Direction.CW);
        pathMeasure = new PathMeasure(path, true);
        return path;
    }

    public JuggleWidget addSatellite(@DrawableRes int res) {
        Satellite satellite = new Satellite();
        satellite.bitmap = BitmapFactory.decodeResource(getContext().getResources(), res);
        satellite.position = -1f;

        getSatellites().add(satellite);
        repositionSatellites();
        return this;
    }

    public List<Satellite> getSatellites() {
        if (satellites == null) {
            satellites = new ArrayList<>();
        }

        return satellites;
    }

    public void advance() {
        offset += ADVANCE;
        if (offset > pathMeasure.getLength()) {
            offset -= pathMeasure.getLength();
        }
        invalidate();
    }

    private void repositionSatellites() {
        if (satellites == null || satellites.isEmpty()) {
            return;
        }

        float delta = 360f / satellites.size();
        float startPercentage = 0;
        for (Satellite satellite : satellites) {
            Log.d("JuggleWidget", "startPercentage = " + startPercentage);
            satellite.startPercentage = startPercentage;
            startPercentage += delta / 360;
        }
        Log.d("JuggleWidget", "done repositioning");
    }

    class Satellite {
        Bitmap bitmap;
        float startPercentage;
        float position;

        float getPosition() {
            position = startPercentage * pathMeasure.getLength();
            return position;
        }
    }

}
