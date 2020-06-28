1.属性:
baselineAligned				//baseline对齐 https://www.jianshu.com/p/07ba80fdd86a
baselineAlignedChildIndex
gravity						//child位置, 默认左上角
measureWithLargestChild
orientation					//子元素排列方向, 水平/垂直
weightSum					//权重总数
divider="@drawable/divider"	//元素间的间隔, color/drawable
showDividers="middle"		//middle(子元素间),beginning(第一个元素左边),end(最后一个元素右边),none(默认);【垂直方向类似】
dividerPadding="3dp"		//设置绘制间隔元素的上下padding(水平方向); 【垂直方向类似】

2.布局参数属性
LinearLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
layout_weight		//child所占宽/高权重
layout_gravity		//子类位置, 当orientation=horizontal时, layout_gravity设置垂直方向有用, 比如: top/bottom/center_vertical
					//			当orientation=vertical时, layout_gravity设置水平方向有用, 比如: left/right/start/end/center_horizontal
					//有一个比较特殊的是center，不管是横向还是纵向的时候，它总有一个方向起作用

