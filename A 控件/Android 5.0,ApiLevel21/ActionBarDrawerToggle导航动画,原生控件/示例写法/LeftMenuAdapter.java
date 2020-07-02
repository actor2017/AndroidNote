package com.zhy.toolbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhy on 15/4/26.
 */
public class LeftMenuAdapter extends ArrayAdapter<MenuItem> {


    private LayoutInflater mInflater;

    private int mSelected;


    public LeftMenuAdapter(Context context, MenuItem[] objects) {
        super(context, -1, objects);

        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_left_menu, parent, false);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.id_item_icon);
        TextView title = (TextView) convertView.findViewById(R.id.id_item_title);
        title.setText(getItem(position).text);
        iv.setImageResource(getItem(position).icon);
        convertView.setBackgroundColor(Color.TRANSPARENT);

        if (position == mSelected) {
            iv.setImageResource(getItem(position).iconSelected);
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.state_menu_item_selected));
        }

        return convertView;
    }

    public void setSelected(int position) {
        this.mSelected = position;
        notifyDataSetChanged();
    }


}


package com.zhy.toolbar;

public class MenuItem {

    public MenuItem(String text, boolean isSelected, int icon, int iconSelected) {
        this.text = text;
        this.isSelected = isSelected;
        this.icon = icon;
        this.iconSelected = iconSelected;
    }

    boolean isSelected;
    String text;
    int icon;
    int iconSelected;
}
//Adapter没撒说的~~提供了一个setSection方法用于设置选中Item的样式什么的。 