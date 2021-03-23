gravity					child位置
ignoreGravity="@id/btn"	指定某一个child忽略上面gravity属性

//布局参数属性
RelativeLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
alignWithParentIfMissing

layout_toLeftOf
layout_toRightOf		//在xx的右侧
layout_toStartOf
layout_toEndOf

layout_alignBaseline	//?
layout_alignLeft				//左对齐
layout_alignTop
layout_alignRight
layout_alignBottom				//底部对齐
layout_alignStart
layout_alignEnd

layout_alignParentLeft
layout_alignParentTop
layout_alignParentRight
layout_alignParentBottom
layout_alignParentStart
layout_alignParentEnd		//在parent的右侧

layout_centerInParent
layout_centerHorizontal
layout_centerVertical

layout_above
android:layout_below="@+id/iv_close"	//在xx的下方


cghild自定义位置等方法:
addRule(RelativeLayout.ALIGN_RIGHT, int subjectId);			//添加规则, id:要用作锚的另一个视图的ID
removeRule
getRule
