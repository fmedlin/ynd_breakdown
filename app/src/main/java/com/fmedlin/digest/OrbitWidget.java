package com.fmedlin.digest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class OrbitWidget extends View {

    Paint paint;

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
    }
}
