import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BottomSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

    }

    public void showDialog(View view){
        public void showDialog(View view){
        final BottomSheetDialog dialog=new BottomSheetDialog(BottomSheetActivity.this);
        View dialogView= LayoutInflater.from(BottomSheetActivity.this)
                .inflate(R.layout.dialog_layout,null);
        ListView listView= (ListView) dialogView.findViewById(R.id.listview);
        String[] array=new String[]{"item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9"};
        ArrayAdapter adapter=new ArrayAdapter(BottomSheetActivity.this,android.R.layout.simple_list_item_1,array);
        listView.setAdapter(adapter);

        dialog.setContentView(dialogView);
        dialog.show();
    }
}
