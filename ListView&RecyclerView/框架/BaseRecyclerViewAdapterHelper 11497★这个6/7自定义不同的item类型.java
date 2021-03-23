简单配置、无需重写额外方法。

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%8D%E5%90%8C%E7%9A%84item%E7%B1%BB%E5%9E%8B

实体类必须实现MultiItemEntity，在设置数据的时候，需要给每一个数据设置itemType
public class MultipleItem implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

在构造里面addItemType绑定type和layout的关系
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.text_view);//Type必须写完, 否则遇到没有的Type时会崩溃
        addItemType(MultipleItem.IMG, R.layout.image_view);//layout可以重复
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.IMG:
                helper.setImageUrl(R.id.iv, item.getContent());
                break;
        }
    }

}

如有多布局有两行布局的item都是一样的，如图"7自定义不同的item类型2"：

通过以上图片，我们可以看到，第二行还第三行的item都是ImageView，这个时候ImageView是可以被复用的，我们可以使用 GridLayoutManager
  multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });

通过重写他的SapnSize来重用ImageView。 假设 GridLayoutManager 的 spanCount 是3，
那么第一个、第二个、item的spanSize就是3，因为要占据一行（和权重差不多的意思）,
然后第三个、第四个、第五个、item的spanSize就是1。