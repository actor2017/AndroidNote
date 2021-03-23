�����á�������д���ⷽ����

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%8D%E5%90%8C%E7%9A%84item%E7%B1%BB%E5%9E%8B

ʵ�������ʵ��MultiItemEntity�����������ݵ�ʱ����Ҫ��ÿһ����������itemType
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

�ڹ�������addItemType��type��layout�Ĺ�ϵ
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.text_view);//Type����д��, ��������û�е�Typeʱ�����
        addItemType(MultipleItem.IMG, R.layout.image_view);//layout�����ظ�
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

���ж಼�������в��ֵ�item����һ���ģ���ͼ"7�Զ��岻ͬ��item����2"��

ͨ������ͼƬ�����ǿ��Կ������ڶ��л������е�item����ImageView�����ʱ��ImageView�ǿ��Ա����õģ����ǿ���ʹ�� GridLayoutManager
  multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });

ͨ����д����SapnSize������ImageView�� ���� GridLayoutManager �� spanCount ��3��
��ô��һ�����ڶ�����item��spanSize����3����ΪҪռ��һ�У���Ȩ�ز�����˼��,
Ȼ������������ĸ����������item��spanSize����1��