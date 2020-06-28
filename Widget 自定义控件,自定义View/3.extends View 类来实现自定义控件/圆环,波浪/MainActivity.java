package cn.itcast.wave12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        final View myRing = findViewById(R.id.mr_myRing);
        final View simpleWave = findViewById(R.id.sw_simpleWave);
        final View myWave = findViewById(R.id.mw_myWave);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myRing.setVisibility(checkedId == R.id.rb1 ? View.VISIBLE : View.GONE);
                simpleWave.setVisibility(checkedId == R.id.rb2 ? View.VISIBLE : View.GONE);
                myWave.setVisibility(checkedId == R.id.rb3 ? View.VISIBLE : View.GONE);
            }
        });
    }
}
