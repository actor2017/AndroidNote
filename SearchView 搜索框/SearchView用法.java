
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击"搜索按钮"时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索"内容改变"时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });

/**======================================================================
JAVA中常用的方法================================*/

//设置搜索框默认是否自动缩小为图标。
setIconifiedByDefault(boolean iconified)
// 为搜索框设置监听器
setOnQueryTextListener(SearchView，OnQueryTextListener listener)
 // 设置是否显示搜索按钮
setSubmitButtonEnabled(boolean enabled)
// 设置搜索框内的默认显示的提示文本
setQueryHint(CharSequence hint)
onQueryTextSubmit是点击搜索后的Listener 返回true,键盘不消失，返回false，键盘消失。
onQueryTextChange是搜索框内容变化的Listener

// 手动展开SearchView输入框
onActionViewExpanded

.setOnCloseListener();//设置关闭监听

/**================================================================================
		示例 - 修改SearchView样式
技巧
查看SearchView的布局文件，点击进去是R文件，无法查看布局内容。
解决方法
在AS的全局搜索框中搜索该布局即可查看该布局内容。===================================*/

//需求 —— 修改Searchview字体大小颜色，同时需要重新布局使Text居中

//根据id-search_src_text获取TextView
SearchView.SearchAutoComplete searchText = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
//修改字体大小
searchText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//重新布局，使其居中
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
lp.gravity = Gravity.CENTER_VERTICAL;
searchText.setLayoutParams(lp);
//修改字体颜色
searchText.setTextColor(ContextCompat.getColor(this, R.color.common_text_gray));
searchText.setHintTextColor(ContextCompat.getColor(this, R.color.common_text_gray));


// 由于通过xml属性app:searchIcon来修改图标的样式会导致drawable过大(显示不正常)。
// 经研究是因为SearchView里ImageVIew设置了padding导致的结果。所以只能通过动态布局来修改。

//需求 —— 修改SearchView左边图标

//根据id-search_mag_icon获取ImageView
ImageView searchButton = (ImageView) searchView.findViewById(R.id.search_mag_icon);
//重新设置ImageView的宽高，使其为自适应图片宽高
LinearLayout.LayoutParams lpimg = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
lpimg.gravity = Gravity.CENTER_VERTICAL;
searchButton.setLayoutParams(lpimg);
searchButton.setImageResource(R.drawable.goods_search_icon);

//该项用于测试ImageView的宽高大小
// searchButton.setBackgroundColor(Color.BLUE);