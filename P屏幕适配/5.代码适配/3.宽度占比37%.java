package cn.itcast.screenadapterweight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        TextView tv = (TextView) findViewById(R.id.tv);
        //在代码中动态控制tv的宽度
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

		//宽度占比37%
        int textWidth = (int) (screenWidth * 0.37f + 0.5f);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();//tv父类是LinearLayout
        params.width = textWidth;
        tv.setLayoutParams(params);

    }
}
