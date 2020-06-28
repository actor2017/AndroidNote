@Override


public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swipe_behavior);
    mCardView = (CardView) findViewById(R.id.swype_card);

    final SwipeDismissBehavior<CardView> swipe
        = new SwipeDismissBehavior();

        swipe.setSwipeDirection(
            SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        swipe.setListener(
            new SwipeDismissBehavior.OnDismissListener() {
            @Override public void onDismiss(View view) {
                Toast.makeText(SwipeBehaviorExampleActivity.this,
                    "Card swiped !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragStateChanged(int state) {}
        });

        LayoutParams coordinatorParams =
            (LayoutParams) mCardView.getLayoutParams();

        coordinatorParams.setBehavior(swipe);
    }