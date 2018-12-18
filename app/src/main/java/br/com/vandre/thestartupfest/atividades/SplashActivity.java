
package br.com.vandre.thestartupfest.atividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import br.com.vandre.thestartupfest.MainActivity;
import br.com.vandre.thestartupfest.R;

public class SplashActivity extends Activity implements Runnable {
    private static final int DELAY = 2000;
    private Handler handler;

    private void declarar() {
        handler = new Handler();
    }

    private void iniciar() {
        declarar();
        handler.postDelayed(this, DELAY);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        iniciar();

    }

    @Override
    public void run() {
        abrir();


    }

    private void abrir() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
