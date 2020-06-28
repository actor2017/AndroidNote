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
        final BottomSheetDialog dialog=new BottomSheetDialog(BottomSheetActivity.this);
         View dialogView= LayoutInflater.from(BottomSheetActivity.this)
                .inflate(R.layout.dialog_layout,null);
        TextView tvTakePhoto= (TextView) dialogView.findViewById(R.id.tv_take_photo);
        TextView tvPhotoAlbum= (TextView) dialogView.findViewById(R.id.tv_photo_album);
        TextView tvCancel= (TextView) dialogView.findViewById(R.id.tv_cancel);

        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetActivity.this,"拍照",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        tvPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetActivity.this,"拍照",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

}
