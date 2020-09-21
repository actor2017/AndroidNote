gravity					child位置
ignoreGravity="@id/btn"	指定某一个child忽略上面gravity属性

//布局参数属性
RelativeLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
alignWithParentIfMissing
toLeftOf
toRightOf
above
below
alignBaseline
alignLeft				//左对齐
alignTop
alignRight
alignBottom
alignParentLeft
alignParentTop
alignParentRight
alignParentBottom
centerInParent
centerHorizontal
centerVertical
toStartOf
toEndOf
alignStart
alignEnd
alignParentStart
alignParentEnd

layout_toLeftOf
layout_toRightOf
layout_above
android:layout_below="@+id/iv_close"	//在xx的下方

layout_alignBaseline
layout_alignLeft
layout_alignTop
layout_alignRight
layout_alignBottom
layout_alignParentLeft
layout_alignParentTop
layout_alignParentRight
layout_alignParentBottom


cghild自定义位置等方法:
addRule(RelativeLayout.ALIGN_RIGHT, int subjectId);			//添加规则, id:要用作锚的另一个视图的ID
removeRule
getRule
