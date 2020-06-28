https://github.com/googlecodelabs/constraint-layout

compile 'com.android.support.constraint:constraint-layout:1.1.2'

ConstraintLayout ����Ϊ Լ�����֣�Ҳ���˰�������"��ǿ�͵���Բ���"���� 2016 �� Google I/O �Ƴ���
��ƽʽ�Ĳ��ַ�ʽ�����κ�Ƕ�ף����ٲ��ֵĲ㼶���Ż���Ⱦ���ܡ���֧�����ȶ��ԣ�����Ϊ����������ʽ����ȫ�����������֡�
�и������õķǳ��ã�����ǧ�谮��һ���õ�����ǳ����ʣ�Լ���� LinearLayout�����Բ��֣���RelativeLayout����Բ��֣���
�ٷֱȲ��ֵȵĹ�����һ������ǿ��ʹ����

    <!--���漸���������û�û�����-->
    <attr format="boolean" name="barrierAllowsGoneWidgets"/>
    <attr format="boolean" name="chainUseRtl"/>
    <attr format="reference" name="constraintSet"/>
	
    <android.support.constraint.Constraints
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
    </android.support.constraint.Constraints>

    <android.support.constraint.ConstraintHelper/>

	
	
	
	<!--���漸�������� UI �༭����ʹ�õģ����˸�����ק���ֵģ���ʵ��ʹ�ù����У����Բ��ù�����Щ����-->
	<attr format="dimension" name="layout_editor_absoluteX"/>
    <attr format="dimension" name="layout_editor_absoluteY"/>
    <attr format="integer" name="layout_constraintLeft_creator"/>
    <attr format="integer" name="layout_constraintRight_creator"/>
    <attr format="integer" name="layout_constraintTop_creator"/>
    <attr format="integer" name="layout_constraintBottom_creator"/>
	<attr format="integer" name="layout_constraintBaseline_creator"/>
	<attr name="layout_optimizationLevel">
        <flag name="none" value="0"/>
        <flag name="standard" value="3"/> <!-- for now only direct & barriers -->
        <flag name="direct" value="1"/>
        <flag name="barrier" value="2"/>
        <flag name="chains" value="4"/>
        <flag name="dimensions" value="8"/>
    </attr>
    

3.��������...
<declare-styleable name="ConstraintSet">
	<attr name="android:orientation"/>
	<attr name="android:id"/>
	<attr name="android:visibility"/>
	<attr name="android:alpha"/>
	<attr name="android:elevation"/>
	<attr name="android:rotation"/>
	<attr name="android:rotationX"/>
	<attr name="android:rotationY"/>
	<attr name="android:scaleX"/>
	<attr name="android:scaleY"/>
	<attr name="android:transformPivotX"/>
	<attr name="android:transformPivotY"/>
	<attr name="android:translationX"/>
	<attr name="android:translationY"/>
	<attr name="android:translationZ"/>
	<attr name="android:layout_width"/>
	<attr name="android:layout_height"/>
	<attr name="android:layout_marginStart"/>
	<attr name="android:layout_marginBottom"/>
	<attr name="android:layout_marginTop"/>
	<attr name="android:layout_marginEnd"/>
	<attr name="android:layout_marginLeft"/>
	<attr name="android:layout_marginRight"/>
	<attr name="layout_constraintCircle"/>
	<attr name="layout_constraintCircleRadius"/>
	<attr name="layout_constraintCircleAngle"/>
	<attr name="layout_constraintGuide_begin"/>
	<attr name="layout_constraintGuide_end"/>
	<attr name="layout_constraintGuide_percent"/>
	<attr name="layout_constraintLeft_toLeftOf"/>
	<attr name="layout_constraintLeft_toRightOf"/>
	<attr name="layout_constraintRight_toLeftOf"/>
	<attr name="layout_constraintRight_toRightOf"/>
	<attr name="layout_constraintTop_toTopOf"/>
	<attr name="layout_constraintTop_toBottomOf"/>
	<attr name="layout_constraintBottom_toTopOf"/>
	<attr name="layout_constraintBottom_toBottomOf"/>
	<attr name="layout_constraintBaseline_toBaselineOf"/>
	<attr name="layout_constraintStart_toEndOf"/>
	<attr name="layout_constraintStart_toStartOf"/>
	<attr name="layout_constraintEnd_toStartOf"/>
	<attr name="layout_constraintEnd_toEndOf"/>
	<attr name="layout_goneMarginLeft"/>
	<attr name="layout_goneMarginTop"/>
	<attr name="layout_goneMarginRight"/>
	<attr name="layout_goneMarginBottom"/>
	<attr name="layout_goneMarginStart"/>
	<attr name="layout_goneMarginEnd"/>
	<attr name="layout_constrainedWidth"/>
	<attr name="layout_constrainedHeight"/>
	<attr name="layout_constraintHorizontal_bias"/>
	<attr name="layout_constraintVertical_bias"/>
	<attr name="layout_constraintWidth_default"/>
	<attr name="layout_constraintHeight_default"/>
	<attr name="layout_constraintWidth_min"/>
	<attr name="layout_constraintWidth_max"/>
	<attr name="layout_constraintWidth_percent"/>
	<attr name="layout_constraintHeight_min"/>
	<attr name="layout_constraintHeight_max"/>
	<attr name="layout_constraintHeight_percent"/>
	<attr name="layout_constraintLeft_creator"/>
	<attr name="layout_constraintTop_creator"/>
	<attr name="layout_constraintRight_creator"/>
	<attr name="layout_constraintBottom_creator"/>
	<attr name="layout_constraintBaseline_creator"/>
	<attr name="layout_constraintDimensionRatio"/>
	<attr name="layout_constraintHorizontal_weight"/>
	<attr name="layout_constraintVertical_weight"/>
	<attr name="layout_constraintHorizontal_chainStyle"/>
	<attr name="layout_constraintVertical_chainStyle"/>
	<attr name="layout_editor_absoluteX"/>
	<attr name="layout_editor_absoluteY"/>
</declare-styleable>

4.δ֪
<declare-styleable name="LinearConstraintLayout">
    <attr name="android:orientation"/>
</declare-styleable>
