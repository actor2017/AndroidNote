https://github.com/googlecodelabs/constraint-layout

compile 'com.android.support.constraint:constraint-layout:1.1.2'

ConstraintLayout 翻译为 约束布局，也有人把它称作"增强型的相对布局"，由 2016 年 Google I/O 推出。
扁平式的布局方式，无任何嵌套，减少布局的层级，优化渲染性能。从支持力度而言，将成为主流布局样式，完全代替其他布局。
有个成语用的非常好，集万千宠爱于一身，用到这里非常合适，约束集 LinearLayout（线性布局），RelativeLayout（相对布局），
百分比布局等的功能于一身，功能强大，使用灵活。

    <!--下面几个属性作用还没搞清楚-->
    <attr format="boolean" name="barrierAllowsGoneWidgets"/>
    <attr format="boolean" name="chainUseRtl"/>
    <attr format="reference" name="constraintSet"/>
	
    <android.support.constraint.Constraints
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
    </android.support.constraint.Constraints>

    <android.support.constraint.ConstraintHelper/>

	
	
	
	<!--下面几个属性是 UI 编辑器所使用的，用了辅助拖拽布局的，在实际使用过程中，可以不用关心这些属性-->
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
    

3.动画属性...
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

4.未知
<declare-styleable name="LinearConstraintLayout">
    <attr name="android:orientation"/>
</declare-styleable>
