https://www.jianshu.com/p/29465cce1131

setSpanCount();		//设置每行个数
setSpanSizeLookup();//返回的是当前位置的 item 跨度大小, spanCount/spanSize = 每行item数量
		//示例:
        recyclerView = (RecyclerView) findViewById(R.id.my_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 6);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 7 || position > 14) {
                    return 3;
                }
                return 2;
            }
        });
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);

