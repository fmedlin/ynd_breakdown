package com.fmedlin.digest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathMeasure;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class OrbitWidget extends View {

    Paint paint;
    Path path;
    PathMeasure pathMeasure;
    List<Satellite> satellites;
    float[] coord = new float[2];

    public OrbitWidget(Context context) {
        this(context, null);
    }

    public OrbitWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrbitWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setARGB(255, 0, 0, 0);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setPathEffect(new DashPathEffect(new float[] {3f, 9f}, 0));
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);

        float w2 = getWidth() / 2f;
        float h2 = getHeight() / 2f;
        c.drawCircle(w2, h2, w2, paint);

        if (path == null) {
            path = setupPath(w2, h2, w2);
        }

        for (Satellite satellite : getSatellites()) {
            float position = satellite.getPosition();
            if (position > pathMeasure.getLength()) {
                position = 0f;
            }

            pathMeasure.getPosTan(position, coord, null);
            c.drawBitmap(satellite.bitmap,
                    coord[0] - satellite.bitmap.getWidth() / 2,
                    coord[1] - satellite.bitmap.getHeight() / 2,
                    null);

            satellite.position = position;
        }

    }

    private Path setupPath(float x, float y, float radius) {
        path = new Path();
        path.addCircle(x, y, radius, Direction.CW);
        pathMeasure = new PathMeasure(path, true);
        return path;
    }

    public void addSatellite(@DrawableRes int res, float pct) {
        Satellite satellite = new Satellite();
        satellite.bitmap = BitmapFactory.decodeResource(getContext().getResources(), res);
        satellite.startPercentage = pct;
        satellite.position = -1f;
        getSatellites().add(satellite);
    }

    public List<Satellite> getSatellites() {
        if (satellites == null) {
            satellites = new ArrayList<>();
        }

        return satellites;
    }

    class Satellite {
        Bitmap bitmap;
        float startPercentage;
        float position;

        float getPosition() {
            if (position < 0f) {
                position = startPercentage * pathMeasure.getLength();
            }

            return position;
        }
    }
}
