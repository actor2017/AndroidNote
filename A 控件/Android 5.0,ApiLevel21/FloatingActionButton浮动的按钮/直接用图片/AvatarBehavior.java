package com.dl7.coordinator.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.dl7.coordinator.R;
import com.dl7.coordinator.utils.AnimHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by long on 2016/7/11.
 * ͷ��Behavior
 */
public class AvatarBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    // ���Ŷ����仯��֧��
    private static final float ANIM_CHANGE_POINT = 0.2f;

    private Context mContext;
    // ���������ķ�Χ
    private int mTotalScrollRange;
    // AppBarLayout�߶�
    private int mAppBarHeight;
    // AppBarLayout���
    private int mAppBarWidth;
    // �ؼ�ԭʼ��С
    private int mOriginalSize;
    // �ؼ����մ�С
    private int mFinalSize;
    // �ؼ��������ŵĳߴ�,��������ֵ��Ҫ���ϸ�ֵ
    private float mScaleSize;
    // ԭʼx����
    private float mOriginalX;
    // ����x����
    private float mFinalX;
    // ��ʼy����
    private float mOriginalY;
    // ����y����
    private float mFinalY;
    // ToolBar�߶�
    private int mToolBarHeight;
    // AppBar����ʼY����
    private float mAppBarStartY;
    // ����ִ�аٷֱ�[0~1]
    private float mPercent;
    // Y���ƶ���ֵ��
    private DecelerateInterpolator mMoveYInterpolator;
    // X���ƶ���ֵ��
    private AccelerateInterpolator mMoveXInterpolator;
    // ���ձ任����ͼ����Ϊ��5.0����AppBarLayout������������״̬�Ḳ�Ǳ任�����ͼ���������һ��������ʾ��ͼƬ
    private CircleImageView mFinalView;
    // ���ձ任����ͼ��ײ��Ĵ�С
    private int mFinalViewMarginBottom;


    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mMoveYInterpolator = new DecelerateInterpolator();
        mMoveXInterpolator = new AccelerateInterpolator();
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
            mFinalSize = (int) a.getDimension(R.styleable.AvatarImageBehavior_finalSize, 0);
            mFinalX = a.getDimension(R.styleable.AvatarImageBehavior_finalX, 0);
            mToolBarHeight = (int) a.getDimension(R.styleable.AvatarImageBehavior_toolBarHeight, 0);
            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {

        if (dependency instanceof AppBarLayout) {
            _initVariables(child, dependency);

            mPercent = (mAppBarStartY - dependency.getY()) * 1.0f / mTotalScrollRange;

            float percentY = mMoveYInterpolator.getInterpolation(mPercent);
            AnimHelper.setViewY(child, mOriginalY, mFinalY - mScaleSize, percentY);

            if (mPercent > ANIM_CHANGE_POINT) {
                float scalePercent = (mPercent - ANIM_CHANGE_POINT) / (1 - ANIM_CHANGE_POINT);
                float percentX = mMoveXInterpolator.getInterpolation(scalePercent);
                AnimHelper.scaleView(child, mOriginalSize, mFinalSize, scalePercent);
                AnimHelper.setViewX(child, mOriginalX, mFinalX - mScaleSize, percentX);
            } else {
                AnimHelper.scaleView(child, mOriginalSize, mFinalSize, 0);
                AnimHelper.setViewX(child, mOriginalX, mFinalX - mScaleSize, 0);
            }
            if (mFinalView != null) {
                if (percentY == 1.0f) {
                    // ��������ʱ����ʾ
                    mFinalView.setVisibility(View.VISIBLE);
                } else {
                    mFinalView.setVisibility(View.GONE);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && dependency instanceof CollapsingToolbarLayout) {
            // ����5.0�������µ����յ�ͷ����Ϊ5.0����AppBarLayout�Ḳ�Ǳ任���ͷ��
            if (mFinalView == null && mFinalSize != 0 && mFinalX != 0 && mFinalViewMarginBottom != 0) {
                mFinalView = new CircleImageView(mContext);
                mFinalView.setVisibility(View.GONE);
                // ���ΪCollapsingToolbarLayout����ͼ
                ((CollapsingToolbarLayout) dependency).addView(mFinalView);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFinalView.getLayoutParams();
                // ���ô�С
                params.width = mFinalSize;
                params.height = mFinalSize;
                // ����λ�ã������ʾʱ�൱�ڱ任���ͷ��λ��
                params.gravity = Gravity.BOTTOM;
                params.leftMargin = (int) mFinalX;
                params.bottomMargin = mFinalViewMarginBottom;
                mFinalView.setLayoutParams(params);
                mFinalView.setImageDrawable(child.getDrawable());
                mFinalView.setBorderColor(child.getBorderColor());
                int borderWidth = (int) ((mFinalSize * 1.0f / mOriginalSize) * child.getBorderWidth());
                mFinalView.setBorderWidth(borderWidth);
            }
        }

        return true;
    }

    /**
     * ��ʼ������
     * @param child
     * @param dependency
     */
    private void _initVariables(CircleImageView child, View dependency) {
        if (mAppBarHeight == 0) {
            mAppBarHeight = dependency.getHeight();
            mAppBarStartY = dependency.getY();
        }
        if (mTotalScrollRange == 0) {
            mTotalScrollRange = ((AppBarLayout) dependency).getTotalScrollRange();
        }
        if (mOriginalSize == 0) {
            mOriginalSize = child.getWidth();
        }
        if (mFinalSize == 0) {
            mFinalSize = mContext.getResources().getDimensionPixelSize(R.dimen.avatar_final_size);
        }
        if (mAppBarWidth == 0) {
            mAppBarWidth = dependency.getWidth();
        }
        if (mOriginalX == 0) {
            mOriginalX = child.getX();
        }
        if (mFinalX == 0) {
            mFinalX = mContext.getResources().getDimensionPixelSize(R.dimen.avatar_final_x);
        }
        if (mOriginalY == 0) {
            mOriginalY = child.getY();
        }
        if (mFinalY == 0) {
            if (mToolBarHeight == 0) {
                mToolBarHeight = mContext.getResources().getDimensionPixelSize(R.dimen.toolbar_height);
            }
            mFinalY = (mToolBarHeight - mFinalSize) / 2 + mAppBarStartY;
        }
        if (mScaleSize == 0) {
            mScaleSize = (mOriginalSize - mFinalSize) * 1.0f / 2;
        }
        if (mFinalViewMarginBottom == 0) {
            mFinalViewMarginBottom = (mToolBarHeight - mFinalSize) / 2;
        }
    }
}