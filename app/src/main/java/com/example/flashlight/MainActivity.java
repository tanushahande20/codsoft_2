package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Switch switch1;
    TextView textView;
    CameraManager cameraManager;
    String cameraid , result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch1 = findViewById(R.id.switch1);
        textView = findViewById(R.id.textview);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                torch(isChecked);

            }
        });
    }

    private void torch(boolean isChecked) {
        try {
            cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            cameraid = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraid,isChecked);
            }
            result = isChecked? "ON" : "OFF";
            textView.setText(result);
        }catch (CameraAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch1.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            torch(false);
        }


    }
}