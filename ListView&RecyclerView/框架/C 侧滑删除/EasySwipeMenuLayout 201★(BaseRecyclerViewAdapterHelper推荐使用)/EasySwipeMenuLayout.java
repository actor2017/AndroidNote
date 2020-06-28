https://github.com/anzaizai/EasySwipeMenuLayout
Recommended
Recommended in conjunction with BaseRecyclerViewAdapterHelper

Feature
1、Two-way sliding
2、Support any View
3、By id binding layout, more freedom


How to use
1.You need to add jitpack repository infomaition to build.gradle in your project.

  allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
  
      }
  }


2.You need to add library dependencies infomation to build.gradle in your module.

compile 'com.github.anzaizai:EasySwipeMenuLayout:1.1.1'

2.5.混淆 https://github.com/anzaizai/EasySwipeMenuLayout/issues/32
##-----Begin: proguard configuration for EasySwipeMenuLayout-----
-keep class com.guanaj.easyswipemenulibrary.State
##------End: proguard configuration for EasySwipeMenuLayout------


3.User EasySwipeMenuLayout as the top-level root layout the needs to be added slide swipe menu the funcation views.

<com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  app:contentView="@+id/content"
                  app:leftMenuView="@+id/left"
                  app:rightMenuView="@+id/right">
                      <LinearLayout
                          android:id="@+id/left"
                          android:layout_width="100dp"
                          android:layout_height="wrap_content"
                          android:background="@android:color/holo_blue_dark"
                          android:orientation="horizontal"
                          android:padding="20dp">
                              <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="分享" />
                        </LinearLayout>
                      <LinearLayout
                          android:id="@+id/content"
                          android:layout_width="match_parent"
                          android:layout_height="wrap_content"
                          android:background="#cccccc"
                          android:orientation="vertical"
                          android:padding="20dp">
                              <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="内容区域" />
                      </LinearLayout>
                      <LinearLayout
                          android:id="@+id/right"
                          android:layout_width="wrap_content"
                          android:layout_height="wrap_content"
                          android:background="@android:color/holo_red_light"
                          android:orientation="horizontal">
                          <TextView
                              android:layout_width="wrap_content"
                              android:layout_height="wrap_content"
                              android:background="@android:color/holo_blue_bright"
                              android:padding="20dp"
                              android:text="删除" />
                          <TextView
                              android:id="@+id/right_menu_2"
                              android:layout_width="wrap_content"
                              android:layout_height="wrap_content"
                              android:background="@android:color/holo_orange_dark"
                              android:padding="20dp"
                              android:text="收藏" />
                      </LinearLayout>
                </com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout>


yes，just one step,use it is so simple
http://www.cherylgood.cn/


2.使用这个配合brvah，无法选取recylceview的item选项
点击事件被拦, setOnItemClickListener 无效
https://github.com/anzaizai/EasySwipeMenuLayout/issues/19

解决方法: helper.addOnClickListener(R.id.content);




