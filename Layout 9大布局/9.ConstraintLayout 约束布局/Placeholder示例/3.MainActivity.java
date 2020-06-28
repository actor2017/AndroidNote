//PlaceHolder还可以在Java代码中动态替换自己的内容：

public class MainActivity extends AppCompatActivity {
  private Placeholder placeholder;
  private ConstraintLayout root;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ...
  }
  public void onClick(View view) {
    placeholder.setContentId(view.getId());
  }
}


//如果结合过渡动画的话，就可以实现一些比较有趣的效果：
public class MainActivity extends AppCompatActivity {
  private Placeholder placeholder;
  private ConstraintLayout root;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ...
  }
  public void onClick(View view) {
    TransitionManager.beginDelayedTransition(root);
    placeholder.setContentId(view.getId());
  }
}
