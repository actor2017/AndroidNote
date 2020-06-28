package com.kuchuanyun.rxjava2sample.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.kuchuanyun.rxjava2sample.R;
import com.kuchuanyun.rxjava2sample.adapter.ItemsAdapter;
import com.kuchuanyun.rxjava2sample.bean.Items;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObservableActivity extends BaseActivity {

    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    private ArrayList<Items> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
        ButterKnife.bind(this);

        Resources res = getResources();
        items.add(new Items(res.getString(R.string.observable_concept), "1.Observable.create()"));
        items.add(new Items(res.getString(R.string.observable_concept_scheduler),"2.Observable.Schedulers()"));
        items.add(new Items(res.getString(R.string.observable_map),"3.Observable.map()"));
        items.add(new Items(res.getString(R.string.observable_flatMap),"4.Observable.flatMap()"));
        items.add(new Items(res.getString(R.string.observable_concatMap),"5.Observable.concatMap()"));
        items.add(new Items(res.getString(R.string.observable_zip),"6.Observable.zip()"));
        items.add(new Items(res.getString(R.string.observable_dasima),"7.Observable中水缸的概念(水缸能装128个事件)"));
        items.add(new Items(res.getString(R.string.Observable_filter),"8.Observable.filter(new Predicate())"));
        items.add(new Items(res.getString(R.string.Observable_sample),"9.Observable.sample(long,time)"));

        rvItems.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvItems.setAdapter(new ItemsAdapter(this, items) {
            @Override
            public void onClicked(int position) {//1......................就是在这儿,抽象方法被重写了.
                switch (position) {
                    case 0:
                        startActivity(new Intent(activity, ObservableCreateActivity.class));//Observable.create()
                        break;
                    case 1:
                        startActivity(new Intent(activity, ObservableSchedulersActivity.class));//Schedulers线程调度
                        break;
                    case 2:
                        startActivity(new Intent(activity, ObservableMapActivity.class));//Observable.map()
                        break;
                    case 3:
                        startActivity(new Intent(activity, ObservableFlatMapActivity.class));//Observable.flatMap()
                        break;
                    case 4:
                        startActivity(new Intent(activity, ObservableConcatMapActivity.class));//Observable.concatMap()
                        break;
                    case 5:
                        startActivity(new Intent(activity, ObservableZipActivity.class));//Observable.zip()
                        break;
                    case 6:
                        startActivity(new Intent(activity, ObservableDaSiMaActivity.class));//水缸的概念
                        break;
                    case 7:
                        startActivity(new Intent(activity, ObservableFilterActivity.class));//Observable.filter
                        break;
                    case 8:
                        startActivity(new Intent(activity, ObservableSampleActivity.class));//Observable.sample
                        break;
                }
            }
        });
    }
}
