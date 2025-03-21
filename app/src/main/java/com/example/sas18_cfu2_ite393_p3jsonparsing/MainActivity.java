package com.example.sas18_cfu2_ite393_p3jsonparsing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView jsonCountryText, jsonTemperatureText;
    private Button parseButton;

    private String jsonData = "{ \"sys\": { \"country\":\"GB\" }, " +
            "\"main\": { \"temp\":304.15, \"pressure\":1009 } }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        jsonCountryText = findViewById(R.id.jsonCountryText);
        jsonTemperatureText = findViewById(R.id.jsonTemperatureText);
        parseButton = findViewById(R.id.parseButton);

        parseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temperature = parseJSON(jsonData);
                jsonTemperatureText.setText("Temperature: " + temperature);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private String parseJSON(String json) {
        String country = "N/A", temperature = "N/A";
        try {
            JSONObject reader = new JSONObject(json);
            JSONObject sys = reader.getJSONObject("sys");
            country = sys.getString("country");

            JSONObject main = reader.getJSONObject("main");
            temperature = main.getString("temp") + " K";
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalCountry = country;
        runOnUiThread(() -> jsonCountryText.setText("Country: " + finalCountry));
        return temperature;
    }
}