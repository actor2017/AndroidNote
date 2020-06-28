package com.ly.changyi.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ly.changyi.R;
import com.ly.changyi.dialog.BaseBottomSheetDialog;

import java.util.ArrayList;

public class MenuDialog extends BaseBottomSheetDialog {

    private ListView            listView;
    private TextView tvCancel;

    private String[]            items;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public MenuDialog(Context context, ArrayList<String> items) {
        this(context, (String[])items.toArray(new String[0]));
    }

    public MenuDialog(Context context, String... items) {
        super(context);
        this.context = context;
        this.items = items;
//        List<String> strings = Arrays.asList(items);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_dialog);
        listView = getView(R.id.list_view_for_menu_dialog);
        tvCancel = getView(R.id.tv_cancel_for_menu_dialog);

        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items));
        listView.setOnItemClickListener(onItemClickListener);
        tvCancel.setOnClickListener(v -> dismiss());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
