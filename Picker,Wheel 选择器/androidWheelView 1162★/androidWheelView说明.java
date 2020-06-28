https://github.com/weidongjian/androidWheelView
https://www.jianshu.com/p/fa7adfa90c68

仿照iOS的滚轮控件，从请吃饭apk反编译出来的

Attributes	Format	Default	Description
awv_textsize	integer	15	textsize
awv_lineSpace	float	2.0f	line space
awv_centerTextColor	integer	oxff313131	center text color
awv_outerTextColor	integer	0xffafafaf	outer text color
awv_dividerTextColor	integer	oxff313131	center text color
awv_itemsVisibleCount	integer	9	visible item count
awv_isLoop	boolean	true	is loop mode

<declare-styleable name="LoopView">
	<attr format="integer" name="awv_textsize"/>//textsize = density * textSize;//一般16-18
	<attr format="float" name="awv_lineSpace"/>
	<attr format="integer" name="awv_centerTextColor"/>
	<attr format="integer" name="awv_outerTextColor"/>
	<attr format="integer" name="awv_dividerTextColor"/>
	<attr format="integer" name="awv_itemsVisibleCount"/>
	<attr format="boolean" name="awv_isLoop"/>
	<attr format="integer" name="awv_initialPosition"/>
	<attr format="float" name="awv_scaleX"/>
</declare-styleable>

@BindView(R.id.loop_view)
LoopView loopView;
List<String> sdf = new ArrayList<>();

sdf.add("sdfsdfsdf");
sdf.add("sdfsdf123sdf");
sdf.add("sdfsdfs234234df");

loopView.setItems(sdf);
int selectedItem = loopView.getSelectedItem();

