gravity					childλ��
ignoreGravity="@id/btn"	ָ��ĳһ��child��������gravity����

//���ֲ�������
RelativeLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
alignWithParentIfMissing
toLeftOf
toRightOf
above
below
alignBaseline
alignLeft				//�����
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
android:layout_below="@+id/iv_close"	//��xx���·�

layout_alignBaseline
layout_alignLeft
layout_alignTop
layout_alignRight
layout_alignBottom
layout_alignParentLeft
layout_alignParentTop
layout_alignParentRight
layout_alignParentBottom


cghild�Զ���λ�õȷ���:
addRule(RelativeLayout.ALIGN_RIGHT, int subjectId);			//��ӹ���, id:Ҫ����ê����һ����ͼ��ID
removeRule
getRule
