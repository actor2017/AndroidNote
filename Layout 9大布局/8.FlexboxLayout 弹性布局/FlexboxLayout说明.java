https://github.com/google/flexbox-layout

FlexboxLayout可以理解成一个高级版的LinearLayout，因为两个布局都把子view按顺序排列。两者之间最大的差别在于FlexboxLayout具有换行的特性。

implementation 'com.google.android:flexbox:1.0.0'//非 androidx
implementation 'com.google.android:flexbox:2.0.1'//1.1.0及以上是 androidx

查看源码,有一下属性:
* <li>{@code flexDirection}</li>
* <li>{@code flexWrap}</li>
* <li>{@code justifyContent}</li>
* <li>{@code alignItems}</li>
* <li>{@code alignContent}</li>
* <li>{@code showDivider}</li>
* <li>{@code showDividerHorizontal}</li>
* <li>{@code showDividerVertical}</li>
* <li>{@code dividerDrawable}</li>
* <li>{@code dividerDrawableHorizontal}</li>
* <li>{@code dividerDrawableVertical}</li>
* <li>{@code maxLine}</li>
* </ul>
* for the FlexboxLayout.
*
* And for the children of the FlexboxLayout, you can use:
* <ul>
* <li>{@code layout_order}</li>
* <li>{@code layout_flexGrow}</li>
* <li>{@code layout_flexShrink}</li>
* <li>{@code layout_flexBasisPercent}</li>
* <li>{@code layout_alignSelf}</li>
* <li>{@code layout_minWidth}</li>
* <li>{@code layout_minHeight}</li>
* <li>{@code layout_maxWidth}</li>
* <li>{@code layout_maxHeight}</li>
* <li>{@code layout_wrapBefore}</li>

https://www.cnblogs.com/huolongluo/p/6607877.html
<com.google.android.flexbox.FlexboxLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	app:alignContent="stretch"
	app:alignItems="stretch"
	app:flexDirection(见图片)="row|row_reverse|column|column_reverse"//主轴的方向,即子Item的排列方向(默认row,水平方向,从左到右)
	app:flexWrap="wrap			//单行还是多行,副轴(与主轴垂直的轴)的方向,wrap:按正常方向换行
				 |noWrap		//不换行，一行显示完子元素
				 |wrap_reverse"	//按反方向换行
				 
	//控制元素主轴方向上的对齐方式.
	app:justifyContent="flex_start	//默认值，左对齐
						flex_end	//右对齐
						center		//居中对齐
						space_between//两端对齐，中间间隔相同
						space_around"//每个元素到两侧的距离相等
						
	//元素在副轴方向的对齐方式. 
	app:alignItems(见图片)="stretch//默认值,如果item没有设置高度,则充满容器高度
					flex_start	//顶端对齐
					flex_end	//底部对齐
					center		//居中对齐
					baseline"	//第一行内容的的基线对齐。
					
	//控制多根轴线的对齐方式(也就是控制多行，如果子元素只有一行，则不起作用)
	app:alignContent="stretch	//默认值，充满交叉轴的高度（测试发现，需要alignItems 的值也为stretch 才有效）
					 flex_start	//与交叉轴起点对齐
					 flex_end	//与交叉轴终点对齐
					 center		//与交叉轴居中对齐
					 space_between// 交叉轴两端对齐，中间间隔相等
					 space_around"//到交叉轴两端的距离相等
					 
	//控制显示水平方向的分割线,值为其中的一个或者多个
	app:showDividerHorizontal="none|beginning|middle|end"
	
	app:dividerDrawableHorizontal=""//设置Flex 轴线之间水平方向的分割线
	
	//控制显示垂直方向的分割线，值为其中的一个或者多个
	app:showDividerVertical="none|beginning|middle|end"
	
	app:dividerDrawableVertical=""//设置子元素垂直方向的分割线
	
	//控制显示水平和垂直方向的分割线，值为其中的一个或者多个
	app:showDivider="none|beginning|middle|end"
	
	//设置水平和垂直方向的分割线，但是注意,如果同时和其他属性使用，比如为 Flex 轴、子元素设置了justifyContent="space_around" 、alignContent="space_between" 等等。可能会看到意料不到的空间，因此应该避免和这些值同时使用。
	app:dividerDrawable=""
	>

	<TextView
		android:id="@+id/textview1"
		android:layout_width="120dp"
		android:layout_height="80dp"
		android:background="@color/red"
		android:text="111"
		
		//子元素的排列顺序,FlexboxLayout子元素默认按照xml文件中出现的顺序。
		app:layout_order="1"//默认值为1，值越小排在越靠前
		
		//子元素的放大比例， 决定如何分配剩余空间（如果存在剩余空间的话），默认值为0,不会分配剩余空间，如果有一个item的 layout_flexGrow 是一个正值，那么会将全部剩余空间分配给这个Item,如果有多个Item这个属性都为正值，那么剩余空间的分配按照layout_flexGrow定义的比例（有点像LinearLayout的layout_weight属性）。
		app:layout_flexGrow="0.0"
		
		//子元素缩小比例，当空间不足时，子元素需要缩小（设置了换行则无效），默认值为1，如果所有子元素的layout_flexShrink 值为1,空间不足时，都等比缩小，如果有一个为0，其他为1，空间不足时，为0的不缩小，负值无效。
		app:layout_flexShrink="1.0"
		
		//给子元素设置对齐方式，上面讲的alignItems属性可以设置对齐，这个属性的功能和alignItems一样，只不过alignItems作用于所有子元素，而layout_alignSelf 作用于单个子元素。默认值为auto, 表示继承alignItems属性，如果为auto以外的值，则会覆盖alignItems属性。
		app:layout_alignSelf="auto|flex_start|flex_end|center|baseline|stretch"
		
		//设置子元素的长度为它父容器长度的百分比，如果设置了这个值，那么通过这个属性计算的值将会覆盖layout_width或者layout_height的值。但是需要注意，这个值只有设置了父容器的长度时才有效（也就是MeasureSpec mode 是 MeasureSpec.EXACTLY）。默认值时-1。
		app:layout_flexBasisPercent="50%"
		
		//强制限制 FlexboxLayout的子元素（宽或高）不会小于最小值，不管layout_flexShrink这个属性的值为多少，子元素不会被缩小到小于设置的这个最小值。
		app:layout_minWidth / layout_minHeight
		
		//强制限制FlexboxLayout子元素不会大于这个最大值, 不管layout_flexGrow的值为多少，子元素不会被放大到超过这个最大值。
		app:layout_maxWidth / layout_maxHeight
		
		//控制强制换行，默认值为false,如果将一个子元素的这个属性设置为true，那么这个子元素将会成为一行的第一个元素。这个属性将忽略flex_wrap 设置的 noWrap值。
		app:layout_wrapBefore="false"
		/>

	<TextView
		android:id="@+id/textview2"
		android:layout_width="80dp"
		android:background="@color/red"
		android:text="222"
		android:layout_height="80dp"
		app:layout_alignSelf="center"
		/>

	<TextView
		android:id="@+id/textview3"
		android:layout_width="160dp"
		android:text="333"
		android:background="@color/red"
		android:layout_height="80dp"
		app:layout_alignSelf="flex_end"
		/>
</com.google.android.flexbox.FlexboxLayout>


与RecyclerView 的结合
在最新的alpha版本，Flexbox能够作为一个LayoutManager(FlexboxlayoutManager) 用在RecyclerView里面，这也就意味着你可以在一个有大量Item的可滚动容器里面使用Flexbox了。

使用FlexboxLayoutManager代替LinearLayoutManager,如下：
FlexboxLayoutManager manager = new FlexboxLayoutManager();
//设置主轴排列方式
manager.setFlexDirection(FlexDirection.ROW);
//设置是否换行
manager.setFlexWrap(FlexWrap.WRAP);
manager.setAlignItems(AlignItems.STRETCH);
 
在Adapter里设置 flexGrow :

复制代码
ImageView mImageView =  rvBaseViewHolder.getImageView(R.id.image_src);
mImageView .setImageResource(mData);

ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
if (lp instanceof FlexboxLayoutManager.LayoutParams) {
	FlexboxLayoutManager.LayoutParams flexboxLp =
			(FlexboxLayoutManager.LayoutParams) mImageView.getLayoutParams();
	flexboxLp.setFlexGrow(1.0f);
}