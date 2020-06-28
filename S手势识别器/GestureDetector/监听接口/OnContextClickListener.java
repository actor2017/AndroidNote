    /**
     * The listener that is used to notify when a context click occurs. When listening for a
     * context click ensure that you call {@link #onGenericMotionEvent(MotionEvent)} in
     * {@link View#onGenericMotionEvent(MotionEvent)}.
     */
    public interface OnContextClickListener {
        /**
         * Notified when a context click occurs.
         *
         * @param e The motion event that occurred during the context click.
         * @return true if the event is consumed, else false
         */
        boolean onContextClick(MotionEvent e);
    }