compile 'com.android.support:gridlayout-v7:25.3.1'//确保和v7包版本保持一致,如果不用v7包里的,会导致宽度分配不均)(左/右边离边界距离不一样)
每个控件的宽高一致!!(示例:计算器布局)

GridLayout是在Android4.0中引进的新布局，使用它的理由有两个：
1，减少布局嵌套层次从而提高性能；
2，对需要复杂对齐的布局，非他莫属。
3.布局方向:分为水平和垂直两种方式，默认是水平布局，一个控件挨着一个控件从左到右依次排列，但是通过指定android:columnCount设置列数的属性后，控件会自动换行进行排列。
如果设置为垂直方向布局，控件则是从上到下依次排列，但是通过指定android:rowCount设置行数的属性后，控件会自动换列进行排列。

在实际使用中，强烈建议每一个子View都明确设置android:layout_row和android:layout_column，不要使用GridLayout的自动确定子View坐标功能。

GridLayout确定的宽高的算法是（以第1行和第1列为例）：


第1行的高：
测量第1行中所有子View的高，取最大值作为该行的高，如果该行没有子View，行高设为0。


第1列的宽：
测量第1列中所有子View的宽，取最大值作为该列的宽，如果该列没有子View，列宽设为0。

GridLayout的子View不需要设置layout_width和layout_height属性，因为GridLayout会把所有的子View的这两个属性设置为WRAP_CONTENT，所以你设置了也没有用。?????子控件可以设置宽高并且有效?????

    <android.support.v7.widget.GridLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"

        //app:useDefaultMargins="true"//用了宽度不会平均分配
        app:columnCount="4"//占用4列★
        app:rowCount="3">//占用3行★


        <TextView
            android:text="First Name:"
            //android:layout_row="0"//指定该单元格在第一行显示★
            //app:layout_column="0"//指定该单元格在第一列显示★
            app:layout_gravity="right" />

        <EditText
            android:ems="10"
            //app:layout_rowSpan="1"//指定该单元格占据的行数(跨度=1)★
            app:layout_columnSpan="2"//指定该单元格占据的列数(跨度=2)★
            //android:layout_gravity="fill_horizontal"//意思是吧这行的剩余空间都分配给我★
            android:layout_rowWeight="1"//（API21(5.0)加入）行权重,把这列的剩余空间都分配给我★
            android:layout_columnWeight="1"/>//（API21(5.0)加入）列权重,把这行的剩余空间都分配给我★
            
    </android.support.v7.widget.GridLayout>