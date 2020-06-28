package cn.itcast.googleplay12.widget.random;

import android.content.Context;
import android.support.annotation.IntRange;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

import java.util.List;

public class StellarMap extends FrameLayout implements AnimationListener, OnTouchListener, OnGestureListener {

	private RandomLayout mHidenGroup;

	private RandomLayout mShownGroup;

	private StellarAdapter mAdapter;
	private RandomLayout.Adapter mShownGroupAdapter;
	private RandomLayout.Adapter mHidenGroupAdapter;

	private int mShownGroupIndex;// 显示的组
	private int mHidenGroupIndex;// 隐藏的组
	private int mGroupCount;// 组数

	/** 动画 */
	private Animation mZoomInNearAnim;
	private Animation mZoomInAwayAnim;
	private Animation mZoomOutNearAnim;
	private Animation mZoomOutAwayAnim;

	private Animation mPanInAnim;
	private Animation mPanOutAnim;
	/** 手势识别器 */
	private GestureDetector mGestureDetector;

	/** 构造方法 */
	public StellarMap(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public StellarMap(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StellarMap(Context context) {
		super(context);
		init();
	}

	/** 初始化方法 */
	private void init() {
		mGroupCount = 0;
		mHidenGroupIndex = -1;
		mShownGroupIndex = -1;
		mHidenGroup = new RandomLayout(getContext());
		mShownGroup = new RandomLayout(getContext());

		addView(mHidenGroup, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mHidenGroup.setVisibility(View.GONE);
		addView(mShownGroup, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		mGestureDetector = new GestureDetector(this);
		setOnTouchListener(this);
		//设置动画
		mZoomInNearAnim = AnimationUtil.createZoomInNearAnim();
		mZoomInNearAnim.setAnimationListener(this);
		mZoomInAwayAnim = AnimationUtil.createZoomInAwayAnim();
		mZoomInAwayAnim.setAnimationListener(this);
		mZoomOutNearAnim = AnimationUtil.createZoomOutNearAnim();
		mZoomOutNearAnim.setAnimationListener(this);
		mZoomOutAwayAnim = AnimationUtil.createZoomOutAwayAnim();
		mZoomOutAwayAnim.setAnimationListener(this);
	}

	/** 设置隐藏组和显示组的x和y的规则 */
	public void setRegularity(int xRegularity, int yRegularity) {
		mHidenGroup.setRegularity(xRegularity, yRegularity);
		mShownGroup.setRegularity(xRegularity, yRegularity);
	}

	private void setChildAdapter() {
		if (null == mAdapter) {
			return;
		}
		mHidenGroupAdapter = new RandomLayout.Adapter() {
			//取出本Adapter的View对象给HidenGroup的Adapter
			@Override
			public View getView(int positionInGroup, View convertView) {
                int index = 0;
                for (int i = 0; i < mShownGroupIndex; i++) {
                    index += mAdapter.getPositionGroupCount(i);
                }
                index += positionInGroup;
                return mAdapter.getView(mHidenGroupIndex, positionInGroup, index, convertView);
			}

			@Override
			public int getCount() {
				return mAdapter.getPositionGroupCount(mHidenGroupIndex);
			}
		};
		mHidenGroup.setAdapter(mHidenGroupAdapter);

		mShownGroupAdapter = new RandomLayout.Adapter() {
			//取出本Adapter的View对象给ShownGroup的Adapter
			@Override
			public View getView(int positionInGroup, View convertView) {
                int index = 0;
                for (int i = 0; i < mShownGroupIndex; i++) {
                    index += mAdapter.getPositionGroupCount(i);
                }
                index += positionInGroup;
				return mAdapter.getView(mShownGroupIndex, positionInGroup, index, convertView);
			}

			@Override
			public int getCount() {
				return mAdapter.getPositionGroupCount(mShownGroupIndex);
			}
		};
		mShownGroup.setAdapter(mShownGroupAdapter);
	}

	/** 设置本Adapter */
	public void setAdapter(StellarAdapter adapter) {
		mAdapter = adapter;
		mGroupCount = mAdapter.groupCount;
		if (mGroupCount > 0) {
			mShownGroupIndex = 0;
		}
		setChildAdapter();
	}

    /**
     * 设置显示区域内边距
     */
	public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
		mHidenGroup.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		mShownGroup.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

    /**
     * @param groupIndex 切换到指定Group
     * @param playAnimation 切换时,是否显示动画效果
     */
	public void setCurrentGroup(int groupIndex, boolean playAnimation) {
		switchGroup(groupIndex, playAnimation, mZoomInNearAnim, mZoomInAwayAnim);
	}

    /**
     * 显示下一个Group
     * @param playAnimation 切换时,是否显示动画效果
     */
    public void setNextGroup(boolean playAnimation) {
        int currentGroup = getCurrentGroup();
        currentGroup = currentGroup + 1;
        if(currentGroup > mAdapter.getGroupCount() - 1) currentGroup = 0;
        switchGroup(currentGroup, playAnimation, mZoomInNearAnim, mZoomInAwayAnim);
    }

    /**
     * 显示上一个Group
     * @param playAnimation 切换时,是否显示动画效果
     */
    public void setPrevGroup(boolean playAnimation) {
        int currentGroup = getCurrentGroup();
        currentGroup = currentGroup - 1;
        if(currentGroup < 0) currentGroup = mAdapter.getGroupCount() - 1;
        switchGroup(currentGroup, playAnimation, mZoomInNearAnim, mZoomInAwayAnim);
    }

	/** 获取当前显示的group角标 */
	public int getCurrentGroup() {
		return mShownGroupIndex;
	}

	/** 给Group设置动画入 */
	public void zoomIn() {
		final int nextGroupIndex = mAdapter.getNextGroupOnZoom(mShownGroupIndex, true);
		switchGroup(nextGroupIndex, true, mZoomInNearAnim, mZoomInAwayAnim);
	}

	/** 给Group设置出动画 */
	public void zoomOut() {
		final int nextGroupIndex = mAdapter.getNextGroupOnZoom(mShownGroupIndex, false);
		switchGroup(nextGroupIndex, true, mZoomOutNearAnim, mZoomOutAwayAnim);
	}


	/** 给下一个Group设置进出动画 */
	private void switchGroup(int newGroupIndex, boolean playAnimation, Animation inAnim, Animation outAnim) {
		if (newGroupIndex < 0 || newGroupIndex >= mGroupCount) {
			return;
		}
		//把当前显示Group角标设置为隐藏的
		mHidenGroupIndex = mShownGroupIndex;
		//把下一个Group角标设置为显示的
		mShownGroupIndex = newGroupIndex;
		// 交换两个Group
		RandomLayout temp = mShownGroup;
		mShownGroup = mHidenGroup;
		mShownGroup.setAdapter(mShownGroupAdapter);
		mHidenGroup = temp;
		mHidenGroup.setAdapter(mHidenGroupAdapter);
		//刷新显示的Group
		mShownGroup.refresh();
		//显示Group
		mShownGroup.setVisibility(View.VISIBLE);

		//启动动画
		if (playAnimation) {
			if (mShownGroup.hasLayouted()) {
				mShownGroup.startAnimation(inAnim);
			}
			mHidenGroup.startAnimation(outAnim);
		} else {
			mHidenGroup.setVisibility(View.GONE);
		}
	}

	// 重新分配显示区域
	public void redistribute() {
		mShownGroup.redistribute();
	}

	/** 动画监听 */
	@Override
	public void onAnimationStart(Animation animation) {
		// 当动画启动
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// 当动画结束
		if (animation == mZoomInAwayAnim || animation == mZoomOutAwayAnim || animation == mPanOutAnim) {
			mHidenGroup.setVisibility(View.GONE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// 当动画重复
	}

	/** 定位 */
	@Override
	public void onLayout(boolean changed, int l, int t, int r, int b) {
		//用以判断ShownGroup是否onLayout的变量
		boolean hasLayoutedBefore = mShownGroup.hasLayouted();
		super.onLayout(changed, l, t, r, b);
		if (!hasLayoutedBefore && mShownGroup.hasLayouted()) {
			mShownGroup.startAnimation(mZoomInNearAnim);//第一次layout的时候启动动画
		} else {
			mShownGroup.setVisibility(View.VISIBLE);
		}
	}

	/** 重写onTouch事件，把onTouch事件分配给手势识别 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	/** 消费掉onDown事件 */
	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	/** 空实现 */
	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int centerX = getMeasuredWidth() / 2;
		int centerY = getMeasuredWidth() / 2;

		int x1 = (int) e1.getX() - centerX;
		int y1 = (int) e1.getY() - centerY;
		int x2 = (int) e2.getX() - centerX;
		int y2 = (int) e2.getY() - centerY;

		if ((x1 * x1 + y1 * y1) > (x2 * x2 + y2 * y2)) {
			zoomOut();
		} else {
			zoomIn();
		}
		return true;
	}

    /** 内部类、接口 */
    public static abstract class StellarAdapter<T> {

        private List<T> data;
        private int groupCount;

        /**
         * @param data 数据
         * @param groupCount 一共有多少组,即分成多少页显示
         */
        public StellarAdapter(List<T> data, @IntRange(from = 1) int groupCount) {
            this.data = data;
            this.groupCount = groupCount;
        }

        public int getGroupCount() {
            return groupCount;
        }

        /**
         * 在某个group组中有几个元素
         * @param groupPosition
         * @return
         */
        public int getPositionGroupCount(int groupPosition) {
            int singleCount = data.size() / groupCount;
            if(groupPosition == groupCount - 1) {
                singleCount = singleCount + data.size() % groupCount;
            }
            return singleCount;
        }

        /**
         * @param groupPosition 现在是第几个Group,一共有{@link #groupCount}个
         * @param positionInGroup 当前Group的Position,从0开始
         * @param positionInAllData 在所有数据中的Position
         * @param convertView
         * @return
         */
        public abstract View getView(int groupPosition, int positionInGroup, int positionInAllData, View convertView);

        /**
         * 当isZoomIn的时候,获取上一个Group或者下一个Group
         * @param groupPosition 现在是第几个Group,一共有{@link #groupCount}个
         * @param isZoomIn 是否是在缩小
         * @return
         */
        public int getNextGroupOnZoom(int groupPosition, boolean isZoomIn) {
            int nextGroup = isZoomIn ? groupPosition + 1 : groupPosition - 1;
            if(nextGroup > groupCount - 1) nextGroup = 0;
            if(nextGroup < 0) nextGroup = groupCount - 1;
            return nextGroup;
        }
    }
}
