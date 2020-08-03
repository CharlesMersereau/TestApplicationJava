package com.waysuninc.testapplicationjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.output);
        tv.setText("Is this device China Mobile? " + isDeviceChinaMobileModel().toString());

        Log.d(LOG_TAG, isDeviceChinaMobileModel().toString());

        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo("com.waysuninc.testapplication", PackageManager.GET_META_DATA);
            Log.d(LOG_TAG, "App name: " + getPackageManager().getApplicationLabel(appInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Button firstButton = findViewById(R.id.firstButton);
        Button secondButton = findViewById(R.id.secondButton);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("First button clicked");
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Second button clicked");
            }
        });

        toggleHideyBar();
    }

    public void toggleHideyBar() {

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(LOG_TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(LOG_TAG, "Turning immersive mode mode on.");
        }

        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public Boolean isDeviceChinaMobileModel() {

        String waysunModel = "MGV2000";
        String waysunModel2 = "UNT401H";
        String waysunModel3 = "CM201-2";

        String model = Build.MODEL;

        return model.contains(waysunModel) || model.contains(waysunModel2) || model.contains(waysunModel3);
    }

    public Boolean isDeviceChinaMobile() {

        String chinaMobile = "CMDC";

        String model = Build.MANUFACTURER;

        return model.equals(chinaMobile);
    }
}