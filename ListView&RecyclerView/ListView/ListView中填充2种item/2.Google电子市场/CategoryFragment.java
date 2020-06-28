package cn.itcast.googleplay12.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.itcast.googleplay12.R;
import cn.itcast.googleplay12.bean.CategoryInfo;
import cn.itcast.googleplay12.http.HttpHelper;
import cn.itcast.googleplay12.http.protocol.CategoryProtocol;
import cn.itcast.googleplay12.utils.UiUtils;
import cn.itcast.googleplay12.widget.LoadingPage;

/**
 * Created by zhengping on 2017/4/2,11:24.
 */

public class CategoryFragment extends BaseFragment {

    /**
     * [{title:'游戏',infos:[{url1:'image/category_game_0.jpg',url2:'image/category_game_1.jpg',
     * url3:'image/category_game_2.jpg',name1:'休闲',name2:'棋牌',name3:'益智'},
     * {url1:'image/category_game_3.jpg',url2:'image/category_game_4.jpg',
     * url3:'image/category_game_5.jpg',name1:'射击',name2:'体育',name3:'儿童'},
     * {url1:'image/category_game_6.jpg',url2:'image/category_game_7.jpg',
     * url3:'image/category_game_8.jpg',name1:'网游',name2:'角色',name3:'策略'},
     * {url1:'image/category_game_9.jpg',url2:'image/category_game_10.jpg',
     * url3:'',name1:'经营',name2:'竞速',name3:''}]},
     * {title:'应用',infos:[{url1:'image/category_app_0.jpg',url2:'image/category_app_1.jpg',
     * url3:'image/category_app_2.jpg',name1:'浏览器',name2:'输入法',name3:'健康'},
     * {url1:'image/category_app_3.jpg',url2:'image/category_app_4.jpg',
     * url3:'image/category_app_5.jpg',name1:'效率',name2:'教育',name3:'理财'},
     * {url1:'image/category_app_6.jpg',url2:'image/category_app_7.jpg',
     * url3:'image/category_app_8.jpg',name1:'阅读',name2:'个性化',name3:'购物'},
     * {url1:'image/category_app_9.jpg',url2:'image/category_app_10.jpg',
     * url3:'image/category_app_11.jpg',name1:'资讯',name2:'生活',name3:'工具'},
     * {url1:'image/category_app_12.jpg',url2:'image/category_app_13.jpg',
     * url3:'image/category_app_14.jpg',name1:'出行',name2:'通讯',name3:'拍照'},
     * {url1:'image/category_app_15.jpg',url2:'image/category_app_16.jpg',
     * url3:'image/category_app_17.jpg',name1:'社交',name2:'影音',name3:'安全'}]}]
     */
    private ArrayList<CategoryInfo> data;

    @Override
    public View fragmentCreateSuccessView() {
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new MyAdapter());
        return listView;
    }

    @Override
    public LoadingPage.ResultState fragmentLoadData() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        data = categoryProtocol.getData();
        return checkData(data);
    }

    /**
     * ListView展示多种布局类型
     * 1、告诉系统，你有几种布局类型getViewTypeCount
     * 2、定义规则，在哪个位置上，显示哪种布局类型  getItemViewType(int position)
     * 3、根据定义的规则，加载对应的布局文件
     */
    class MyAdapter extends BaseAdapter {

        //所返回布局类型值的定义必须从0开始，依次递增
        public static final int TYPE_TITLE = 0;
        public static final int TYPE_NORMAL = 1;

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CategoryInfo getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            CategoryInfo categoryInfo = data.get(position);
            if(categoryInfo.isTitle) {
                return TYPE_TITLE;
            } else return TYPE_NORMAL;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    CategoryTitleHolder titleHolder;
                    if (convertView == null) {
                        convertView = View.inflate(UiUtils.getContext(), R.layout.layout_category_title, null);
                        titleHolder = new CategoryTitleHolder(convertView);
                        convertView.setTag(titleHolder);
                    } else titleHolder = (CategoryTitleHolder) convertView.getTag();
                    titleHolder.tv.setText(data.get(position).title);
                    break;
                case TYPE_NORMAL:
                    CategoryNormalHolder normalHolder;
                    if (convertView == null) {
                        convertView = View.inflate(UiUtils.getContext(), R.layout.layout_category_item, null);
                        normalHolder = new CategoryNormalHolder(convertView);
                        convertView.setTag(normalHolder);
                    } else normalHolder = (CategoryNormalHolder) convertView.getTag();
                    normalHolder.text1.setText(data.get(position).name1);
                    normalHolder.text2.setText(data.get(position).name2);
                    normalHolder.text3.setText(data.get(position).name3);
                    String url1 = HttpHelper.URL + "image?name=" + data.get(position).url1;
                    String url2 = HttpHelper.URL + "image?name=" + data.get(position).url2;
                    String url3 = HttpHelper.URL + "image?name=" + data.get(position).url3;
                    ImageLoader.getInstance().displayImage(url1, normalHolder.image1);
                    ImageLoader.getInstance().displayImage(url2, normalHolder.image2);
                    ImageLoader.getInstance().displayImage(url3, normalHolder.image3);
                    break;
            }
            return convertView;
        }
    }

    static class CategoryTitleHolder {

        private TextView tv;

        public CategoryTitleHolder(View convertView) {
            tv = (TextView) convertView.findViewById(R.id.tv);
        }
    }

    static class CategoryNormalHolder {

        private ImageView image1;
        private TextView text1;
        private ImageView image2;
        private TextView text2;
        private ImageView image3;
        private TextView text3;

        public CategoryNormalHolder(View convertView) {
            image1 = (ImageView) convertView.findViewById(R.id.image1);
            text1 = (TextView) convertView.findViewById(R.id.text1);
            image2 = (ImageView) convertView.findViewById(R.id.image2);
            text2 = (TextView) convertView.findViewById(R.id.text2);
            image3 = (ImageView) convertView.findViewById(R.id.image3);
            text3 = (TextView) convertView.findViewById(R.id.text3);
        }
    }
}
