http://blog.csdn.net/journeyx/article/details/52981940学习menu

关于Menu的几个重要的方法，都是Activity的方法；


onCreateOptionsMenu(Menu menu)
每次Activity一创建就会执行，一般只执行一次；

onPrepareOptionsMenu(Menu menu)
每次menu被打开时，该方法就会执行一次；

onOptionsItemSelected(MenuItem item)
每次menu菜单项被点击时，该方法就会执行一次；

invalidateOptionsMenu()
刷新menu里的选项里内容，它会调用onCreateOptionsMenu(Menu menu)方法

onCreateContextMenu()
创建控件绑定的上下文菜单menu，根据方法里的View参数识别是哪个控件绑定

onContextItemSelected(MenuItem item)
点击控件绑定的上下菜单menu的内容项    

作者：dayang
链接：http://www.jianshu.com/p/0d2fc4302bf7
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


res/menu目录下,再右击新建的menu新建main123_menu.xml文件

<menu>：代表菜单资源
<item>：菜单项
         android:id  // 菜单项的id
         android:icon  // 菜单项的图标
         android:title  // 菜单项的标题
         android:orderInCategory // 排序1,2,3...
         android:showAsAction // 在ActionBar上的显示参数（API 11）
                   never：不将MenuItem显示在ActionBar上（是默认值）
                   always：总是将该MenuItem显示在ActionBar上
                   ifRoom：当AcitonBar上有空间时将该MenuItem显示在ActionBar上，没有空间就放入溢出菜单中
                   withText：将该MenuItem显示在ActionBar上，并显示该菜单项的文本
                   显示自定义ActionBar的View，需要和actionViewClass这组参数结合使用（API14）

<group>：菜单组
二级菜单（子菜单的创建）
         概念：在menu Item中嵌套menu元素，可以实现多级菜单，嵌套的菜单叫做子菜单，一般只会使用二级菜单，
	       如果菜单层次太深，会严重影响用户体验。
         二级可选菜单
                   android:checkableBehavior
                   有三个属性值可选
                            all（多选）
                            single（单选）
                            none（不可选）


接着是MainActivity种要重写一下代码:
@Override
 public boolean onCreateOptionsMenu(Menu menu)
 {
     //新建的xml文件
     getMenuInflater().inflate(R.menu.main, menu);
     return super.onCreateOptionsMenu(menu);
 }
 
代码创建Menu
    private String[] menuTitles;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuTitles == null) {
            menuTitles = getResources().getStringArray(R.array.style_names);
        }
        for (String name : menuTitles){
            MenuItem menuItem = menu.add(name);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menuItem.setOnMenuItemClickListener(this);
        }
        return true;
    }

 @Override
 public boolean onOptionsItemSelected(MenuItem item)
 {
      //根据不同的id点击不同按钮控制activity需要做的事件
     switch (item.getItemId())
     {
         case R.id. id_action_add:
            //事件
             break;
         case R.id. id_action_delete:
            //事件
             break;
     }
     return true;
 }


也可以通过toolbar.setOnMenuItemClickListener实现点击MenuItem的回调。

  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });