    public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener {

	    public boolean onSingleTapConfirmed(MotionEvent e) {//单击
            return false;
        }
		
        public boolean onSingleTapUp(MotionEvent e) {//只要有点击
            return false;
        }

        public void onLongPress(MotionEvent e) {//长按
        }

		//基于当前位置,进行滑动, 参1:开始滚动时的Down事件;参2:处罚现在滚动的移动事件;参3:x偏移,不是e1和e2的距离;参4:y偏移(到顶部距离)
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onDown(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {//双击
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {//这个不是双击
            return false;
        }

        public boolean onContextClick(MotionEvent e) {
            return false;
        }
    }