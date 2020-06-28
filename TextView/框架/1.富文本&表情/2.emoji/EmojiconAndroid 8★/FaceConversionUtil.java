package zhanghuan.cn.emojiconlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情转换工具
 * Created by zhanghuan on 2016/3/9.
 * 使用:
 * 1.初始化
 *      FaceConversionUtil.getInstace().init(getApplication(), FileUtils.getEmojiFile(this));
 * 2.给EditText设置
 *      SpannableString spannableString =
 *      FaceConversionUtil.getInstace().addFace(getContext(), emoji.getId(), emoji.getCharacter());
 *      et.getText().replace(Math.min(selectionStart, selectionEnd),
 *         Math.max(selectionStart, selectionEnd), spannableString, 0,
 *         spannableString.length());
 * 3.给TextView设置
 *      tv.setText(FaceConversionUtil.getInstace().getExpressionString(this, content));
 * TODO: 2019/5/4 不通过读取文件的形式,直接写成map
 */
public class FaceConversionUtil {

    /** 每一页表情的个数 */
    private int pageSize = 20;

    private static FaceConversionUtil mFaceConversionUtil;

    /** 保存于内存中的表情HashMap */
    private HashMap<String, String> emojiMap = new HashMap<String, String>();

    /** 保存于内存中的表情集合 */
    private List<ChatEmoji> emojis = new ArrayList<ChatEmoji>();

    /** 表情分页的结果集合 */
    public List<List<ChatEmoji>> emojiLists = new ArrayList<List<ChatEmoji>>();

    private FaceConversionUtil() {
    }

    public static FaceConversionUtil getInstace() {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new FaceConversionUtil();
        }
        return mFaceConversionUtil;
    }

    /**
     * 解析字符
     * @param data string = emoji_1.png,[可爱]
     */
    public void init(Context context, List<String> data) {
        if (data == null) {
            return;
        }
        ChatEmoji emojEentry;
        try {
            for (String str : data) {//str = emoji_1.png,[可爱]
                String[] text = str.split(",");//把每一行解析成key,value形式
                String fileName = text[0].substring(0, text[0].lastIndexOf("."));//emoji_1
                emojiMap.put(text[1], fileName);//[可爱], emoji_1
                int resID = context.getResources().getIdentifier(fileName,
                        "drawable", context.getPackageName());

                if (resID != 0) {
                    emojEentry = new ChatEmoji();
                    emojEentry.setId(resID);//R.drawable.emoji_1
                    emojEentry.setCharacter(text[1]);//[可爱]
                    emojEentry.setFaceName(fileName);//emoji_1
                    emojis.add(emojEentry);
                }
            }
            int pageCount = (int) Math.ceil(emojis.size() / pageSize + 0.1);

            for (int i = 0; i < pageCount; i++) {
                emojiLists.add(getData(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分页数据
     *
     * @param page
     * @return
     */
    private List<ChatEmoji> getData(int page) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }
        // 不这么写，会在viewpager加载中报集合操作异常，我也不知道为什么
        List<ChatEmoji> list = new ArrayList<ChatEmoji>();
        list.addAll(emojis.subList(startIndex, endIndex));
        if (list.size() < pageSize) {
            for (int i = list.size(); i < pageSize; i++) {
                ChatEmoji object = new ChatEmoji();
                list.add(object);
            }
        }
        if (list.size() == pageSize) {
            ChatEmoji object = new ChatEmoji();
            object.setId(R.drawable.face_del_icon);
            list.add(object);
        }
        return list;
    }

    /**
     * 添加表情(点击表情后,把表情显示到EditText)
     * @param context
     * @param imgId R.drawable.emoji_1
     * @param spannableString [可爱]
     * @return
     */
    public SpannableString addFace(Context context, int imgId, String spannableString) {
        if (TextUtils.isEmpty(spannableString)) return null;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 32, 32, true);
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断(设置给TextView)
     * @param context
     * @param str 内容显示:[可爱]
     * @return
     */
    public SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     */
    private void dealExpression(Context context, SpannableString spannableString, Pattern patten, int start) {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            String value = emojiMap.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            int resId = context.getResources().getIdentifier(value, "drawable",
                    context.getPackageName());
            // 通过上面匹配得到的字符串来生成图片资源id
            // Field field=R.drawable.class.getDeclaredField(value);
            // int resId=Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        context.getResources(), resId);
                bitmap = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                ImageSpan imageSpan = new ImageSpan(context, bitmap, ImageSpan.ALIGN_BOTTOM);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int end = matcher.start() + key.length();
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(imageSpan, matcher.start(), end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    // 如果整个字符串还未验证完，则继续。。
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }
    }
}
