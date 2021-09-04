package com.econet.app.QRCode;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.econet.app.homepage.LoginActivity;
import com.econet.app.solwallet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("total", 5);
        i.putExtra("mark", 10);
        i.putExtra("name", "Aaron");
        i.putExtra("id", "020015743");
        startActivity(i);
    }
}
