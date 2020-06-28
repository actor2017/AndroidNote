QuickContactBadge extends ImageView implements OnClickListener

<QuickContactBadge
    android:id="@+id/quick_contact_badge"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/logo" />

/**
 * 用户点击时:
 * 如果有这个该联系人, 会打开该联系人对应的联系方式界面.
 * 如果没有该联系人, 会提示将号码加入到通讯录
 * @params lazyLookup 是否延迟加载(点击的时候才查询)
 */
quickContactBadge.assignContactFromPhone("12345678901", false);
//quickContactBadge.assignContactFromEmail("12345678@qq.com", false);

//都没反应, 暂时不知道怎么用
//Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//Uri uri = Uri.parse("https://www.baidu.com");
//quickContactBadge.assignContactUri(uri);

//??
//quickContactBadge.setMode(ContactsContract.QuickContact.MODE_LARGE);//MODE_MEDIUM
