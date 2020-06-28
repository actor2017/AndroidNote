package cn.itcast.googleplay12.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.itcast.googleplay12.utils.UiUtils;

/**
 * Created by zhengping on 2017/4/7,14:11.
 * 1、决定哪些元素属于第一行，哪些元素属于第二行...
 *      手动测量childView的时候，measureSpec需要重新考量
 * 2、根据lineList，将每一行的高度进行累加，再加上每一行的竖直间距，从而得到MyFlowLayout真实的高度
 * 3、确定每一个行对象中每一个view的摆放，然后再onLayout方法中调用每一个行对象的layoutView方法
 *      onMeasure会被执行多次，我们需要在onMeasure刚刚开始的时候，重置一些数据
 * 4、将每一行的剩余空间平均分配给这一行中的所有元素，让这一行中的所有view对象的宽度增加
 * 5、修改瑕疵
 *      a、别忘了最后一行
 *      b、padding的考虑
 *      c、如果一行中的元素高度不一致的情况之下，这一行的高度以最高的元素为主，其他的元素居中显示
 */

public class MyFlowLayout extends ViewGroup {

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            line.reMeasure();
            line.layoutViews(left,top);
            top = top + line.lineHeight + mVerticalSpacing;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        reset();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取FlowLayout的宽度
        int innerWidthSize = widthSize - getPaddingLeft() - getPaddingRight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();//获取一共有多少个元素
        mCurrentLine = new Line();//当前行

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.measure(0, 0);//把测量的工作交给系统完成，开发者不参与任何的限制意见
            //int childWidth = childView.getWidth();
            //需要对childView的宽度进行限制
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
            int childHeightMesureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            childView.measure(childWidthMeasureSpec, childHeightMesureSpec);
            int childWidth = childView.getMeasuredWidth();//只有经历过测量之后，才能获取到控件真实的大小信息

            mUsedWidth = mUsedWidth + childWidth + mHorizontalSpacing;
            if(mUsedWidth < innerWidthSize) {
                //剩余空间可以放得下这个元素
                mCurrentLine.addLineView(childView);
            }else {
                //剩余空间放不下这个元素,新起一行
                newLine();
                mCurrentLine.addLineView(childView);
                mUsedWidth = mUsedWidth + childWidth + mHorizontalSpacing;

            }
        }

        //千万别忘了最后一行
        lineList.add(mCurrentLine);

        //循环结束之后，咱们拥有的一个lineList
        int totalHeight = 0;
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            totalHeight = totalHeight + line.lineHeight + mVerticalSpacing;
        }
        //Toast.makeText(getContext(),"lineList.size=" + lineList.size(),Toast.LENGTH_SHORT).show();
        //别忘了把最后一个mVerticalSpacing去除
        totalHeight = totalHeight - mVerticalSpacing;
        totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//得出这个控件本身的宽度和高度信息

    }

    //重置状态
    private void reset() {
        lineList.clear();
        mUsedWidth = 0;
        mCurrentLine = new Line();
    }

    private void newLine() {
        lineList.add(mCurrentLine);
        mCurrentLine = new Line();//此时没CurrentLine就是全新的一行
        mUsedWidth = 0;
    }

    private int mUsedWidth = 0;
    private int mHorizontalSpacing = UiUtils.dp2px(5);
    private int mVerticalSpacing = UiUtils.dp2px(10);



    private Line mCurrentLine;

    //需要一个数据集合，这个数据集合把所有的行对象存储起来
    private ArrayList<Line> lineList = new ArrayList<>();

    //定义一个数据结构，代表的是每一行的具体元素的封装
    class Line {
        private ArrayList<View> lineViews = new ArrayList<>();//用来存储这一行中所有的view对象

        public int lineHeight;

        public void addLineView(View view){
            lineViews.add(view);
            int childHeight = view.getMeasuredHeight();
            if(lineHeight < childHeight) {//这一行的高度由这一行中最高的那个元素决定
                lineHeight = childHeight;//由元素的高度决定行对象的高度
            }

        }
        //把行对象中的所有view对象，进行从左往右的摆放
        public void layoutViews(int left,int top) {
            for (int i = 0; i < lineViews.size(); i++) {
                View childView = lineViews.get(i);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                int topOffset = lineHeight/2 - childHeight/2;
                childView.layout(left, top + topOffset, left + childWidth, top + topOffset+ childHeight);
                left = left + childWidth + mHorizontalSpacing;
            }
        }

        public void reMeasure() {
            //得到剩余空间
            int lineWidth = 0;
            for (int i = 0; i < lineViews.size(); i++) {
                View childView = lineViews.get(i);
                int childWidth = childView.getMeasuredWidth();
                lineWidth = lineWidth + childWidth + mHorizontalSpacing;
            }
            lineWidth = lineWidth - mHorizontalSpacing;
            int remainSpacing = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth;//剩余空间
            if(remainSpacing > 0) {
                int splitSpacing = remainSpacing / lineViews.size();
                //把这一行中每一个元素的宽度增加
                for (int i = 0; i < lineViews.size(); i++) {
                    View childView = lineViews.get(i);
                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    childWidth = childWidth + splitSpacing;
                    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,
                            MeasureSpec.EXACTLY);
                    int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,
                            MeasureSpec.EXACTLY);
                    childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);
                }
            }
        }
    }
}
