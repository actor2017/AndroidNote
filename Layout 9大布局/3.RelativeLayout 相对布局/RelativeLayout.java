gravity					childλ��
ignoreGravity="@id/btn"	ָ��ĳһ��child��������gravity����

//���ֲ�������
RelativeLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
alignWithParentIfMissing

layout_toLeftOf
layout_toRightOf		//��xx���Ҳ�
layout_toStartOf
layout_toEndOf

layout_alignBaseline	//?
layout_alignLeft				//�����
layout_alignTop
layout_alignRight
layout_alignBottom				//�ײ�����
layout_alignStart
layout_alignEnd

layout_alignParentLeft
layout_alignParentTop
layout_alignParentRight
layout_alignParentBottom
layout_alignParentStart
layout_alignParentEnd		//��parent���Ҳ�

layout_centerInParent
layout_centerHorizontal
layout_centerVertical

layout_above
android:layout_below="@+id/iv_close"	//��xx���·�


cghild�Զ���λ�õȷ���:
addRule(RelativeLayout.ALIGN_RIGHT, int subjectId);			//��ӹ���, id:Ҫ����ê����һ����ͼ��ID
removeRule
getRule
