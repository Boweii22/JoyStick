package com.smartherd.joystick;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        JoystickView joystick = findViewById(R.id.joystick);
        TextView coordinateText = findViewById(R.id.coordinate_text);
        TextView directionText = findViewById(R.id.direction_text);

        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onMove(float xPercent, float yPercent, String direction) {
                // Update X and Y coordinates
                coordinateText.setText(String.format("X: %.2f, Y: %.2f", xPercent, yPercent));

                // Update Direction
                directionText.setText(direction);
            }
        });

    }
}