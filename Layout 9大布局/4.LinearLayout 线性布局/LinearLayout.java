1.����:
baselineAligned				//baseline���� https://www.jianshu.com/p/07ba80fdd86a
baselineAlignedChildIndex
gravity						//childλ��, Ĭ�����Ͻ�
measureWithLargestChild
orientation					//��Ԫ�����з���, ˮƽ/��ֱ
weightSum					//Ȩ������
divider="@drawable/divider"	//Ԫ�ؼ�ļ��, color/drawable
showDividers="middle"		//middle(��Ԫ�ؼ�),beginning(��һ��Ԫ�����),end(���һ��Ԫ���ұ�),none(Ĭ��);����ֱ�������ơ�
dividerPadding="3dp"		//���û��Ƽ��Ԫ�ص�����padding(ˮƽ����); ����ֱ�������ơ�

2.���ֲ�������
LinearLayout.LayoutParams extends ViewGroup.MarginLayoutParams:
layout_weight		//child��ռ��/��Ȩ��
layout_gravity		//����λ��, ��orientation=horizontalʱ, layout_gravity���ô�ֱ��������, ����: top/bottom/center_vertical
					//			��orientation=verticalʱ, layout_gravity����ˮƽ��������, ����: left/right/start/end/center_horizontal
					//��һ���Ƚ��������center�������Ǻ����������ʱ��������һ������������

