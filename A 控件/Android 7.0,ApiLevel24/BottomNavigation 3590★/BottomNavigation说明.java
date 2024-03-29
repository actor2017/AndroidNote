https://github.com/Ashok-Varma/BottomNavigation

compile 'com.ashokvarma.android:bottom-navigation-bar:1.4.1'

	   如果发现不能运行,可以下载文件,也可以把它该成低版本,例:1.3.0'

2.5	allowBackUp="false"如果出错后，可以在<application>标签中加入；tools:replace="android:allowBackup"

//----------------------------------

3.布局实现
 <com.ashokvarma.bottomnavigation.BottomNavigationBar
        android:id="@+id/bottom_navigation_bar"
        android:layout_gravity="bottom"		//线性布局
	android:layout_alignParentBottom="true"	//相对布局
	app:bnbMode="mode_default | mode_fixed | mode_shifting"//(能否局部左右位移,具体意思看下面,可不设置)
	app:bnbBackgroundStyle="background_style_default | background_style_static | background_style_riple"//(背景能否扩散,具体意思看下面,可不设置)
        app:bnbActiveColor="#FF7F24"		//已选中item中图片和文字的颜色(具体意思看下面,可不设置)
	app:bnbInactiveColor="#ECECEC"		//未选中item中图片的颜色(可不设置)
        app:bnbBackgroundColor="#000"		//item背景颜色(具体意思看下面,可不设置)
	app:bnbElevation="8dp"			//海拔,用于设置阴影,默认8dp(可不设置)
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>


 *注意,如果在代码中添加mode & background需要在添加BottomNavigationItem之前进行设置，否则设置无效★★★★★

//---------------------------------MODE-----------------------------

3.5设置导航栏模式：(2中方式)
java: setMode():MODE_DEFAULT,MODE_FIXED(不能局部左右位移),MODE_SHIFTING(能局部左右位移)
		MODE_DEFAULT:如果item个数<=3,MODE=FIXED,否则MODE=SHIFTING★★★★

xml属性:bnbMode:mode_default,mode_fixed,mode_shifting(看上面)
		mode_default:如果item个数<=3,MODE=FIXED,否则MODE=SHIFTING★★★★

//---------------------------setBackgroundStyle---------------------

3.6设置导航栏背景样式(2种方式)
java:setBackgroundStyle():BACKGROUND_STYLE_DEFAULT,BACKGROUND_STYLE_STATIC(背景不扩散),BACKGROUND_STYLE_RIPPLE(背景扩散)
			  DEFAULT的意思:如果MODE=FIXED,backgroundstyle=style_static会被使用,否则
					如果MODE=SHIFTING,backgroundstyle=style_ripple会被使用.★★★★

xml属性:bnbBackgroundStyle:background_style_default, background_style_static,background_style_riple
			   default的意思:如果mode=fixed,backgroundstyle=style_static会被使用,否则
					 如果mode=shifting,backgroundstyle=style_ripple会被使用.★★★★

//---------------------------------------Color---------------------

3.7 BottomNavigationBar的颜色
  java:
  bottomNavigationBar
    .setActiveColor(R.color.primary)		//如果backgroundstyle=static,是已选中item中图片和文字的颜色(可不设置)
						//如果backgroundstyle=ripple,是底部导航栏背景色

    .setInActiveColor("#FFFFFF")		//未选中item中图片的颜色(可不设置)

    .setBarBackgroundColor("#ECECEC")		//如果backgroundstyle=static,是底部导航栏背景色
						//如果backgroundstyle=ripple,是已选中item中图片和文字的颜色(可不设置)
//---------------------------------------
  xml属性： 
        app:bnbActiveColor="#FF7F24"		//如果backgroundstyle=static,是已选中item中图片和文字的颜色(可不设置)
						//如果backgroundstyle=ripple,是底部导航栏背景色

        app:bnbInactiveColor="#ECECEC"		//未选中item中图片的颜色(可不设置)

        app:bnbBackgroundColor="#000"		//如果backgroundstyle=static,是底部导航栏背景色
						//如果backgroundstyle=ripple,是已选中item中图片和文字的颜色(可不设置)

//=================================================================

和FloatingActionButton的联动:
FloatingActionButton fabHome = (FloatingActionButton) findViewById(R.id.fab_home);
BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bnb);
bottomNavigationBar.setFab(fabHome);

//------------------------------------
4.类中Activity中添加BottomNavigationItem:
    @Override
    protected void onCreate(Bundle savedInstanceState) {		//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


BadgeItem numberBadgeItem = new BadgeItem()				//徽章；证章；标记(item右上角的小圆点)
                .setBorderWidth(4)	//Badge内容和边界的边距 类似于内边距
		.setBorderColor()	//边框颜色
                .setBackgroundColorResource(R.color.red)	//Badge的背景色
                .setText("5")					//设置Badge的文字
                .setTextColor(R.color.white)
		.setGravity()		//标记在item的位置Gravity.TOP/BOTTOM/LEFT/RIGHT (any combination of these)
		.setAnimationDuration()	//显示/隐藏标记的动画时间
                .setHideOnSelect(true); //当点击这个BottomNavigationItem时，隐藏它身上的Badge
		.hide(boolean)		//hides with/without animation,true->animate
		.show(boolean)		//un-hides with/without ,true->animate
		.toggle(boolean)	//toggles badge between hide/show with animation,rue->animate
		.isHidden();		//returns if the badge is hidden

/*
简写:BadgeItem numberBadgeItem = new BadgeItem().setText("5");
*/

BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//xml中设置了,这儿可不设置
bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);//xml中设置了,这儿可不设置

bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))//添加下方的item,注意item)位置,最后2个),注意前面括号
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setBadgeItem(numberBadgeItem))//可单独设置小圆点(可不设置)
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.orange))//可单独设置颜色(可不设置)
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games"))
		.setFirstSelectedPosition(0)			//默认选择第0个item
                .initialise();


bottomNavigationBar.setAutoHideEnabled(false);//自动隐藏,用户滑动改变,就一句
bottomNavigationBar.hide(boolean);//hides with/without animation,true->animate
bottomNavigationBar.show(boolean);//un-hides with/without animation,true->animate


5.设置事件监听选项点击事件TabChangeListener
bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
	// 当第position个tab被选中时，调用此方法
        // 这里可以完成对Fragment的切换
            }
            @Override
            public void onTabUnselected(int position) {
	// 对未被选择的tab进行处理，其中pisition仍然是被选中的tab
            }
            @Override
            public void onTabReselected(int position) {
	// 当被选中的tab 再一次被点击时调用此方法
            }
        });


6.

//-----------------------------------------------------fragment的代码,来自网上,没检查----------
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidong.demo.R;

public class LocationFragment extends Fragment {


    public static LocationFragment newInstance(String param1) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        TextView tv = (TextView)view.findViewById(R.id.tv_location);
        tv.setText(agrs1);
        return view;
    }
}