http://blog.csdn.net/journeyx/article/details/52981940ѧϰmenu

����Menu�ļ�����Ҫ�ķ���������Activity�ķ�����


onCreateOptionsMenu(Menu menu)
ÿ��Activityһ�����ͻ�ִ�У�һ��ִֻ��һ�Σ�

onPrepareOptionsMenu(Menu menu)
ÿ��menu����ʱ���÷����ͻ�ִ��һ�Σ�

onOptionsItemSelected(MenuItem item)
ÿ��menu�˵�����ʱ���÷����ͻ�ִ��һ�Σ�

invalidateOptionsMenu()
ˢ��menu���ѡ�������ݣ��������onCreateOptionsMenu(Menu menu)����

onCreateContextMenu()
�����ؼ��󶨵������Ĳ˵�menu�����ݷ������View����ʶ�����ĸ��ؼ���

onContextItemSelected(MenuItem item)
����ؼ��󶨵����²˵�menu��������    

���ߣ�dayang
���ӣ�http://www.jianshu.com/p/0d2fc4302bf7
��Դ������
����Ȩ���������С���ҵת������ϵ���߻����Ȩ������ҵת����ע��������


res/menuĿ¼��,���һ��½���menu�½�main123_menu.xml�ļ�

<menu>������˵���Դ
<item>���˵���
         android:id  // �˵����id
         android:icon  // �˵����ͼ��
         android:title  // �˵���ı���
         android:orderInCategory // ����1,2,3...
         android:showAsAction // ��ActionBar�ϵ���ʾ������API 11��
                   never������MenuItem��ʾ��ActionBar�ϣ���Ĭ��ֵ��
                   always�����ǽ���MenuItem��ʾ��ActionBar��
                   ifRoom����AcitonBar���пռ�ʱ����MenuItem��ʾ��ActionBar�ϣ�û�пռ�ͷ�������˵���
                   withText������MenuItem��ʾ��ActionBar�ϣ�����ʾ�ò˵�����ı�
                   ��ʾ�Զ���ActionBar��View����Ҫ��actionViewClass����������ʹ�ã�API14��

<group>���˵���
�����˵����Ӳ˵��Ĵ�����
         �����menu Item��Ƕ��menuԪ�أ�����ʵ�ֶ༶�˵���Ƕ�׵Ĳ˵������Ӳ˵���һ��ֻ��ʹ�ö����˵���
	       ����˵����̫�������Ӱ���û����顣
         ������ѡ�˵�
                   android:checkableBehavior
                   ����������ֵ��ѡ
                            all����ѡ��
                            single����ѡ��
                            none������ѡ��


������MainActivity��Ҫ��дһ�´���:
@Override
 public boolean onCreateOptionsMenu(Menu menu)
 {
     //�½���xml�ļ�
     getMenuInflater().inflate(R.menu.main, menu);
     return super.onCreateOptionsMenu(menu);
 }
 
���봴��Menu
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
      //���ݲ�ͬ��id�����ͬ��ť����activity��Ҫ�����¼�
     switch (item.getItemId())
     {
         case R.id. id_action_add:
            //�¼�
             break;
         case R.id. id_action_delete:
            //�¼�
             break;
     }
     return true;
 }


Ҳ����ͨ��toolbar.setOnMenuItemClickListenerʵ�ֵ��MenuItem�Ļص���

  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });