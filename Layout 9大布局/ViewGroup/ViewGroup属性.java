clipChildren
clipToPadding
layoutAnimation
animationCache
persistentDrawingCache
alwaysDrawnWithCache
addStatesFromChildren
descendantFocusability
animateLayoutChanges
splitMotionEvents
layoutMode


LayoutParams:
	layout_height	child的高度
	layout_width	child的宽度

MarginLayoutParams extends ViewGroup.LayoutParams:
	margin				marginLeft&Top&Right&Bottom, 优先值高于这4个属性
	marginHorizontal	layout_marginLeft & layout_marginRight, 优先值高于这2个属性
	marginVertical		layout_marginTop & layout_marginBottom, 优先值高于这2个属性
	marginLeft
	marginTop
	marginRight
	marginBottom
	marginStart			适配RTL布局, 替代marginLeft
	marginEnd			适配RTL布局, 替代marginRight
