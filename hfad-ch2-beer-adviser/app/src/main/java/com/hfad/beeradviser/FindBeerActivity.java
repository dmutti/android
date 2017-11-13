package com.hfad.beeradviser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

public class FindBeerActivity extends Activity {

    private BeerExpert expert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    public void onClickFindBeer(View view) {
        Spinner color = findViewById(R.id.color);
        TextView brands = findViewById(R.id.brands);

        String beerType = String.valueOf(color.getSelectedItem());
        List<String> brandsList = expert.getBrands(beerType);

        StringBuilder sb = new StringBuilder();
        for (String brand : brandsList) {
            sb.append(brand).append("\n");
        }
        brands.setText(sb);
    }
}
