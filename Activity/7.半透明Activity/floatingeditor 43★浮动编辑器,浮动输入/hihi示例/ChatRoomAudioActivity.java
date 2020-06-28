package com.ly.hihifriend.activity;

public class ChatRoomAudioActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView         tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_audio);
    }

    private EditorCallback editorCallback = new EditorCallback() {

        @Override
        public void onSubmit(String content) {
            tvMsg.setText(content);
            if (!TextUtils.isEmpty(content)) chatRoomAudioPresenter.sendText(content);
        }

        @Override
        public void onAttached(View rootView, View cancel, View submit, EditText editText) {
            if (editText != null) editText.setText(tvMsg.getText());
        }
    };
    private EditorHolder editorHolder = new EditorHolder(R.layout.fast_reply_floating_layout_2, -1,
            R.id.tv_submit, R.id.et_msg);

    @OnClick({R.id.tv_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_msg://输入聊天内容
                FloatEditorActivity.openEditor(this, editorCallback, editorHolder);
                break;
        }
    }
}
