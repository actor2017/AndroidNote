
<ViewStub
        android:id="@+id/view_stub"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


@BindView(R.id.view_stub)
ViewStub viewStub;

public View initViewStub(@LayoutRes int id) {
	viewStub.setLayoutResource(id);
	return viewStub.inflate();
}
