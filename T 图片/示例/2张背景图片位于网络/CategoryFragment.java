package cn.itcast.myredboyclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import cn.itcast.myredboyclient.R;
import cn.itcast.myredboyclient.activity.CategoryActivity;
import cn.itcast.myredboyclient.bean.CategoryInfo;
import cn.itcast.myredboyclient.utils.MyBitmapUtils;

public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mLV;

    private ArrayList<CategoryInfo.Categories> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLV = new ListView(getActivity());
        mLV.setOnItemClickListener(this);
        return mLV;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestData();
    }


    private void requestData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, "http://www.loghare.com/Shop/product/product_findCatories.html",
                new RequestCallBack<String>() {

                    @Override
                    // 请求失败
                    public void onFailure(HttpException arg0, String arg1) {

                    }

                    @Override
                    // 请求成功
                    public void onSuccess(ResponseInfo<String> arg0) {
                        String json = arg0.result;// 拿到请求结果

                        processJSON(json);
                    }
                });

    }

    // 解析数据
    protected void processJSON(String json) {
        Gson gson = new Gson();
        CategoryInfo classInfo = gson.fromJson(json, CategoryInfo.class);
        arrayList = classInfo.categories;
        mLV.setAdapter(new MyAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        CategoryInfo.Categories category = arrayList.get(position);

        if (category.isLeaf.equals("1")) {
            // 此分类是叶子节点，下面没有分类信息，此时应该跳转到该分类下面的产品列表
            // 跳转产品列表
        } else {
            // 跳转页面
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            int pid = Integer.parseInt(category.id);
            intent.putExtra("pid", pid);
            intent.putExtra("title", category.name);
            Bundle bundle = new Bundle();
            getActivity().startActivity(intent);
        }

    }



    static class ViewHolder {
        public TextView mTitle, mDesc;
        public ImageView mIcon, mArrow;

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public CategoryInfo.Categories getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(),
                        R.layout.item_fragment_category_layout, null);
                viewHolder.mIcon = (ImageView) convertView
                        .findViewById(R.id.iv_right_icon);
                viewHolder.mArrow = (ImageView) convertView
                        .findViewById(R.id.iv_arrow);
                viewHolder.mTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                viewHolder.mDesc = (TextView) convertView
                        .findViewById(R.id.tv_desc);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CategoryInfo.Categories categories = getItem(position);

            if (categories.iconUrl != null) {
                //category/icon_category_mon_red.png
                String url1 = "http://www.loghare.com/Shop/upload/"
                        + categories.iconUrl.split(",")[0];
                //category/icon_category_mon_white.png
                String url2 = "http://www.loghare.com/Shop/upload/"
                        + categories.iconUrl.split(",")[1];
                new MyBitmapUtils().displaySelector(viewHolder.mIcon, url1,			//老师示例用法
                        url2);
            }
            viewHolder.mTitle.setText(categories.name);
            viewHolder.mDesc.setText(categories.desc);

            return convertView;
        }
    }
}
