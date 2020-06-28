分组的伸缩栏
  比ExpandableListView还要强大，支持两级。

adapter.expandAll();//全部展开


if you don't want to extent a class, you can also use the interface IExpandable.
如果不想扩展类，也可以使用IExpandable接口。
AbstractExpandableItem is just a helper class.
public class Level0Item extends AbstractExpandableItem<Level1Item> {...}
public class Level1Item extends AbstractExpandableItem<Person> {...}
public class Person {...}

adapter需要继承BaseMultiItemQuickAdapter
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> { 
    public ExpandableItemAdapter(List<MultiItemEntity> data) {    
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);   
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);    
        addItemType(TYPE_PERSON, R.layout.item_text_view);
    }
    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
        case TYPE_LEVEL_0:
            ....
            //set view content
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int pos = holder.getAdapterPosition();
                   if (lv0.isExpanded()) { 
                       collapse(pos);
                   } else {
                       expand(pos);
                   }
           }});
           break;
        case TYPE_LEVEL_1:
           // similar with level 0
           break;
        case TYPE_PERSON:
           //just set the content
           break;
    }
}


Activity使用代码
public class ExpandableUseActivity extends Activity {
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        ...
        ArrayList<MultiItemEntity> list = generateData();
        ExpandableItemAdapter adapter = new ExpandableItemAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    private ArrayList<MultiItemEntity> generateData() {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item(...);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item(...);
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person());
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }
}

