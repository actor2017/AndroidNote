Unicode为Emoji分配码点，Emoji 符号就是一个字体，它会被渲染为图片显示,
在2018年，将发布Emoji 6.0（之后会重命名为Emoji11，其实就是Emoji 5.0的升级版）版本的标准.
在 Android 4.4 及以下的设备上，使用 EmojiAppCompatXxx 控件没有emoji效果.

实际测试,不用这些控件都可以,不过有些是方块.
compile "com.android.support:support-emoji-appcompat:25.+"//兼容,4.4以下不显示表情

//包含 EmojiCompat 方案所需要的基本实现以及 Downloadable fonts configuration 的代码
//可下载的原生支持字体,依赖Google服务,国内用不上
//compile "com.android.support:support-emoji:25.+"

//Apk内捆绑字体,打包的时向assets目录嵌入一个NotoColorEmojiCompat.ttf字体文件大概7.4MB
//compile "com.android.support:support-emoji-bundled:25.+"

截止到现在，Emoji 5.0 中，被列入 Unicode 的已经有 2623 个了。
http://www.unicode.org/emoji/charts/full-emoji-list.html

2.1 如何使用 Emoji？
一个标准的 Emoji ，其实是有多种表示方法的，举个例子，先看看前面说的笑脸 U+1F601。

Code、UTF-8、Surrogates 这些形式，都可以表示这个笑脸的 Emoji。通常这个 Emoji 表情是来自用户输入的数据或者服务端传递过来的数据，虽然这些形式都可以表示这个 Emoji，但是不同的格式就需要不同的形式来解析。

正常来说，我们推荐使用 Surrogates 传递 Emoji，例如：\uD83D\uDE01，它本身就是一个 Unicode 的编码，是通用的，可以在 TextView 中直接使用就可以显示。

假如我们得到的并不是 Surrogates ，而是 Code ，例如 1F601，这样我们就需要进行额外的处理了。其实也很简单，经过几步转换就可以做到。
String(Character.toChars(Integer.parseInt("1F601", 16)))//Kotlin 代码


3.1 什么是 EmojiCompat
根据官方文档描述，EmojiCompat 支持库主要是为了让 Android 设备，达到最新的 Emoji 符号的显示效果，它可以防止应用中，出现以豆腐块 “☐” 的形式来显示 Emoji，虽然它仅仅只是因为你当前的设备没有这个字体而已。通过 EmojiCompat ，你的设备无需等待 Android 系统更新，就可以获得最新的 Emoji 表情显示效果。


3.2 如何使用 EmojiCompat
EmojiCompat 支持库，最低支持到 Android 4.4(Api Level 19) 的系统设备。
EmojiCompat 提供两种字体的支持方式，它们分别是：
  1.可下载的字体配置。(完全依赖 Google 服务,在国内基本上是处于残废状态)
  2.本地捆绑的字体配置。

无论使用哪种方式配置字体，对于 EmojiCompat 而言，其实是不关心的，它只需要判断当前设备是否支持这个 Emoji，支持就使用系统内置的，不支持的话，就使用 EmojiSpans 来替换 CharSequence，来达到替换渲染的效果。


3.3 本地捆绑的字体配置方式
第一步，需要添加 Gradle 依赖。
compile "com.android.support:support-emoji-bundled:27.0.2"

第二步，在Application中初始化 EmojiCompat,去加载打包的时候，嵌入的 Emoji 字体文件，大概需要消耗150ms，大概 200kb 的内存
EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
EmojiCompat.init(config);

第三步，使用 EmojiCompat。
EmojiCompat 的处理逻辑:加载一个 Emoji 字体，然后判断当前设备是否支持需要显示的 Emoji，如果不支持，则使用 EmojiSpans 替换它，最终将处理过的 CharSequence 设置到 TextView 上。
EmojiCompat.get().process("笑脸： \uD83D\uDE01");


3.4 Emoji AppCompat Widgets
在实际项目中，如果每次都需要通过 EmojiCompat.get().process() 对字符串进行处理，其实也挺麻烦的。为此 Google 还为开发者提供了对应控件支持。

compile "com.android.support:support-emoji-appcompat:27.0.2"
使用:
EmojiAppCompatTextView
EmojiAppCompatEditText
EmojiAppCompatButton

使用 support-emoji-appcompat  只是节省了我们 process() 的步骤，但是依然需要 init() 


3.5 自定义控件支持 Emoji
你可以一直使用 progress() 或者使用 EmojiAppCompatXxx 控件，但是如果你想要自定义一个控件来显示 Emoji，就需要使用 EmojiCompat 提供的另外两个帮助类。

EmojiTextViewHelper

EmojiEditViewHelper

这两个使用起来非常简单，一个用于处理纯展示的控件，一个用于处理有输入的状态的控件，非常的简洁明了。

    public class MyTextView extends AppCompatTextView {
        ...
        public MyTextView(Context context) {
            super(context);
            init();
        }
        ...
        private void init() {
            getEmojiTextViewHelper().updateTransformationMethod();
        }

        @Override
        public void setFilters(InputFilter[] filters) {
            super.setFilters(getEmojiTextViewHelper().getFilters(filters));
        }

        @Override
        public void setAllCaps(boolean allCaps) {
            super.setAllCaps(allCaps);
            getEmojiTextViewHelper().setAllCaps(allCaps);
        }

        private EmojiTextViewHelper getEmojiTextViewHelper() {
            ...
        }
    }


    public class MyEditText extends AppCompatEditText {
        ...
        public MyEditText(Context context) {
            super(context);
            init();
        }
        ...
        private void init() {
            super.setKeyListener(getEmojiEditTextHelper().getKeyListener(getKeyListener()));
        }

        @Override
        public void setKeyListener(android.text.method.KeyListener keyListener) {
            super.setKeyListener(getEmojiEditTextHelper().getKeyListener(keyListener));
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            InputConnection inputConnection = super.onCreateInputConnection(outAttrs);
            return getEmojiEditTextHelper().onCreateInputConnection(inputConnection, outAttrs);
        }

        private EmojiEditTextHelper getEmojiEditTextHelper() {
            ...
        }
    }

















