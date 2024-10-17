package com.smartherd.joystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class JoystickView extends View {

    // Joystick base and hat
    private float centerX = 0f;
    private float centerY = 0f;
    private float baseRadius = 0f;
    private float hatRadius = 0f;

    private float hatX = 0f;
    private float hatY = 0f;

    private Paint basePaint;
    private Paint hatPaint;
    private Paint arrowPaint;

    private float thumbX;
    private float thumbY;
    private final float thumbRadius = 100f;

    private OnJoystickMoveListener onJoystickMoveListener;

    public JoystickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFocusable(true);
        setClickable(true);

        basePaint = new Paint();
        basePaint.setColor(Color.parseColor("#111111"));
        basePaint.setStyle(Paint.Style.FILL);

        hatPaint = new Paint();
        hatPaint.setColor(Color.parseColor("#E52F27"));
        hatPaint.setStyle(Paint.Style.FILL);
        hatPaint.setShadowLayer(15f, 10f, 10f, Color.BLACK); // Shadow parameters

        arrowPaint = new Paint();
        arrowPaint.setColor(Color.WHITE);
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setTextSize(50f);

        thumbX = getWidth() / 2f;
        thumbY = getHeight() / 2f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = (getWidth() / 2f);
        centerY = (getHeight() / 2f);
        baseRadius = Math.min(getWidth(), getHeight()) / 3f;
        hatRadius = baseRadius / 2.5f;
        resetHatPosition();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw base circle
        canvas.drawCircle(centerX, centerY, baseRadius, basePaint);
        // Draw joystick hat (movable part)
        canvas.drawCircle(hatX, hatY, hatRadius, hatPaint);
        // Draw arrows around the joystick
        canvas.drawText("↑", centerX - 20, centerY - baseRadius - 50, arrowPaint);  // Up
        canvas.drawText("↓", centerX - 20, centerY + baseRadius + 70, arrowPaint);  // Down
        canvas.drawText("←", centerX - baseRadius - 70, centerY + 20, arrowPaint);  // Left
        canvas.drawText("→", centerX + baseRadius + 50, centerY + 20, arrowPaint);  // Right
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dx = event.getX() - centerX;
        float dy = event.getY() - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (distance < baseRadius) {
                    hatX = event.getX();
                    hatY = event.getY();
                } else {
                    float ratio = baseRadius / (float) distance;
                    hatX = centerX + dx * ratio;
                    hatY = centerY + dy * ratio;
                }
                invalidate();
                updateListener();
                break;
            case MotionEvent.ACTION_UP:
                resetHatPosition();
                invalidate();
                updateListener();
                break;
        }
        return true;
    }

    private void resetHatPosition() {
        hatX = centerX;
        hatY = centerY;
    }



    // Notify listener of joystick position and direction
    private void updateListener() {
        float xPercent = (hatX - centerX) / baseRadius;
        float yPercent = (hatY - centerY) / baseRadius;
        String direction = calculateDirection(xPercent, yPercent);

        if (onJoystickMoveListener != null) {
            onJoystickMoveListener.onMove(xPercent, yPercent, direction);
        }
    }
    // Calculate direction based on x and y percentages
    private String calculateDirection(float xPercent, float yPercent) {
        if (xPercent == 0f && yPercent == 0f) return "Idle";
        if (yPercent < -0.5) return "Up";
        if (yPercent > 0.5) return "Down";
        if (xPercent < -0.5) return "Left";
        if (xPercent > 0.5) return "Right";
        if (xPercent < -0.5 && yPercent < -0.5) return "Top Left";
        if (xPercent > 0.5 && yPercent < -0.5) return "Top Right";
        if (xPercent < -0.5 && yPercent > 0.5) return "Bottom Left";
        if (xPercent > 0.5 && yPercent > 0.5) return "Bottom Right";
        return "Idle";
    }

    // Listener interface
    public interface OnJoystickMoveListener {
        void onMove(float xPercent, float yPercent, String direction);
    }

    // Set the listener
    public void setOnJoystickMoveListener(OnJoystickMoveListener listener) {
        this.onJoystickMoveListener = listener;
    }
}
