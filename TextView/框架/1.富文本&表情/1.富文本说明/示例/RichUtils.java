package com.ly.meeting.util;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ly.meeting.application.MyApplication;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 富文本工具类
 * 一共38种Span - 9个是Interface = 29种
 * <ol>
 *     <li>{@link android.text.SpannableString SpannableString没有append()功能}</li>
 *     <li>{@link SpannableStringBuilder SpannableStringBuilder有append()功能}</li>
 *     <li>Interface:9个</li>
 *     <ol>
 *         <li>AlignmentSpan  extends ParagraphStyle</li>
 *         <li>LeadingMarginSpan extends ParagraphStyle</li>
 *         <li>LineBackgroundSpan extends ParagraphStyle</li>
 *         <li>LineHeightSpan extends ParagraphStyle, WrapTogetherSpan</li>
 *         <li>ParagraphStyle</li>
 *         <li>TabStopSpan extends ParagraphStyle</li>
 *         <li>UpdateAppearance</li>
 *         <li>UpdateLayout extends UpdateAppearance</li>
 *         <li>WrapTogetherSpan extends ParagraphStyle</li>
 *     </ol>
 *
 *     <li>父类，一般不用:2个</li>
 *     <ol>
 *         <li>MetricAffectingSpan</li>
 *         <li>ReplacementSpan</li>
 *     </ol>
 *
 *     <li>方法示例:22个</li>
 *     <ol>
 *         <li>点击事件{@link Builder#addClickableSpan(ClickableSpan)}</li>
 *         <li>图片{@link Builder#addImageSpan(int, Integer, Integer)}</li>
 *         <li>设置图片,基于文本基线或底部对齐{@link Builder#addDynamicDrawableSpan(int, DynamicDrawableSpan)}</li>
 *         <li>超链接{@link Builder#addUrlSpan(String)}</li>
 *         <li>设置前景色,文字颜色{@link Builder#addForegroundColorSpan(int)}</li>
 *         <li>基于x轴缩放{@link Builder#addScaleXSpan(float)}</li>
 *         <li>Ctrl + F效果:查找字体改变前景色{@link Builder#addFindText(String, int)}</li>
 *         <li>文字背景颜色{@link Builder#addBackgroundColorSpan(int)}</li>
 *         <li>设置绝对Size,字体大小{@link Builder#addAbsoluteSizeSpan(int)}</li>
 *         <li>设置相对Size,字体大小{@link Builder#addRelativeSizeSpan(float[])}</li>
 *         <li>粗体{@link Builder#addStyleBoldSpan()}</li>
 *         <li>斜体{@link Builder#addStyleItalicSpan()}</li>
 *         <li>粗体 & 斜体{@link Builder#addStyleBoldItalicSpan()}</li>
 *         <li>文本外貌(包括字体,大小,样式和颜色){@link Builder#addTextAppearanceSpan()}</li>
 *         <li>文本字体{@link Builder#addTypefaceSpan()}</li>
 *         <li>删除线{@link Builder#addStrikethroughSpan()}</li>
 *         <li>下划线{@link Builder#addUnderLineSpan()}</li>
 *         <li>上标(数学公式会用到){@link Builder#addSuperscriptSpan()}</li>
 *         <li>下标(数学公式会用到){@link Builder#addSubscriptSpan()}</li>
 *         <li>模糊&浮雕{@link Builder#addMaskFilterSpan()}</li>
 *         <li>光栅效果{@link Builder#addRasterizerSpan()}</li>
 *         <li>相当于占位符{@link Builder#addSuggestionSpan()}</li>
 *     </ol>
 *
 *     <li>还未测试,todo...</li>
 *     <ol>
 *         <li>BulletSpan?</li>
 *         <li>CharacterStyle?</li>
 *         <li>DrawableMarginSpan?</li>
 *         <li>EasyEditSpan?</li>
 *         <li>IconMarginSpan?</li>
 *         <li>LocaleSpan?</li>
 *         <li>QuoteSpan?</li>
 *         <li>TtsSpan?</li>
 *     </ol>
 * </ol>
 */

public class RichUtils {

    private static Context context = MyApplication.instance;

    public static Builder getBuilder(CharSequence showText) {
        return new Builder(showText);
    }

    public static Builder getBuilder(CharSequence showText, int start, int end) {
        return new Builder(showText, start, end);
    }

    /**
     * 即使是new也必须加static,见{@link android.support.v7.app.AlertDialog.Builder}示例
     */
    public static class Builder {

        private SpannableStringBuilder spanStringBuilder;
        private int start;
        private int end;

        private Builder(@NonNull CharSequence showText) {
            this(showText, 0, showText.length());
        }

        private Builder(@NonNull CharSequence showText, int start, int end) {
            if (showText == null) throw new NullPointerException("showText can't be null!");
            if (start < 0) start = 0;
            if (end < 0) end = 0;
            if (end > showText.length()) end = showText.length();
            spanStringBuilder = new SpannableStringBuilder(showText, start, end);
            this.start = start;
            this.end = end;
        }

        /**
         * 点击事件
         * TextView必须要设置:tv.setMovementMethod(LinkMovementMethod.getInstance());//否则没有点击响应
		 * 点击后背景色颜色: tv.setHighlightColor(@ColorInt int color);
         */
        public Builder addClickableSpan(ClickableSpan span) {
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 超链接
         * TextView必须要设置:tv.setMovementMethod(LinkMovementMethod.getInstance());//否则没有点击响应
         */
        public Builder addUrlSpan(String url) {
            URLSpan span = new URLSpan(url);
            /**
             * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 不包括起始下标和终点下标                        ()
             * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 包括终点下标(这个富文本后面继续有超链接的下划线)  (]
             * Spanned.SPAN_INCLUSIVE_EXCLUSIVE 包括起始下标            [)
             * Spanned.SPAN_INCLUSIVE_INCLUSIVE 同时包括起始下标和终点下标[]
             */
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置相对Size,字体大小
         */
        public Builder addRelativeSizeSpan(@FloatRange(from = 0) float[] fontSizePxs) {
            int min = Math.min(end, fontSizePxs.length);
            for (int i = 0; i < min; i++) {
                RelativeSizeSpan span = new RelativeSizeSpan(fontSizePxs[i]);
                spanStringBuilder.setSpan(span, i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        /**
         * 图片
         */
        public Builder addImageSpan(@DrawableRes int drawableId, Integer width, Integer height) {
            Drawable drawable = context.getResources().getDrawable(drawableId);
            if (width == null || width < 0) width = drawable.getIntrinsicWidth();
            if (height == null || height < 0) height = drawable.getIntrinsicHeight();
            spanStringBuilder.clear();
            spanStringBuilder.append(" ");
            drawable.setBounds(0, 0, width, height);//宽高
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);//ALIGN_BOTTOM(默认),ALIGN_BASELINE
            spanStringBuilder.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 添加图片
         * @param imgPath 图片路径,用Glide加载出Bitmap,再包装成ImageSpan对象
         * @param perfix 前缀, 比如你需要换行\n
         * @param suffix 后缀, 比如你添加图片后也是需要换行\n
         * @param width 图片宽度
         * @param height 图片高度, 暂未作处理
         * @param lishener 回调
         * 示例用法:
         * RichUtils.getBuilder("")
         *         .addImageSpan(path, "\n", "\n", editText.getMeasuredWidth(), null, new
         *                 RichUtils.OnLishener() {
         *             @Override
         *             public void onFinish(RichUtils.Builder builder) {
         *                 SpannableStringBuilder build = builder.build();
         * //                  build.insert(0, "\n");//前面加换行
         * //                  build.append("\n", 0, 0);
         * //                  build.append("\n\n");//后面加换行
         * //                  editText.getText().insert(selectionStart, "\n");
         *                 int selectionStart = editText.getSelectionStart();//光标位置[0,+max]
         *                 editText.getEditableText().insert(selectionStart, build);
         * //                  editText.append("\n\n");
         * //                  editText.requestLayout();
         *             }
         *         });
         */
        public void addImageSpan(String imgPath, String perfix, String suffix, Integer width, Integer height, OnLishener lishener) {
            if (perfix == null) perfix = "";
            if (suffix == null) suffix = "";
            String finalPerfix = perfix;
            String finalSuffix = suffix;
            Glide.with(context)
                    .asBitmap()
                    .load(imgPath)
//                    .apply(new RequestOptions().centerCrop())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            spanStringBuilder.clear();
                            //img乱写,但不能是空格,原因未查明...
                            spanStringBuilder.append(finalPerfix + "img" + finalSuffix);
                            if (width != null && width > 0) {
                                bitmap = zoomBitmapToFixWidth(bitmap, width);
                            } else if (height != null && height > 0) {
                                //暂未实现...
                            }
                            RichImageSpan span = new RichImageSpan(context, bitmap, Uri.parse(imgPath));//context, Uri.parse(imgPath)
                            //把img替换成ImageSpan
                            spanStringBuilder.setSpan(span, finalPerfix.length(), finalPerfix.length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            if (lishener != null) lishener.onFinish(Builder.this);
                        }
                    });
//            spanStringBuilder.clear();
//            spanStringBuilder.append(" ");
//            ImageSpan span = new ImageSpan(context, Uri.parse(imgPath));
//            spanStringBuilder.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return this;
        }

        /**
         * 设置图片,基于文本基线或底部对齐。
         * todo 没测试
         */
        public Builder addDynamicDrawableSpan(@DrawableRes int drawableId, DynamicDrawableSpan span) {
            spanStringBuilder.clear();
            spanStringBuilder.append(" ");
            spanStringBuilder.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置前景色,文字颜色
         */
        public Builder addForegroundColorSpan(@ColorInt int color) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 基于x轴缩放
         * todo 没测试
         * @param proportion 比例
         */
        public Builder addScaleXSpan(float proportion) {
            ScaleXSpan span = new ScaleXSpan(proportion);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * Ctrl + F效果:查找字体改变前景色
         */
        public Builder addFindText(String findText, @ColorInt int color) {
            Pattern pattern = Pattern.compile(findText);
            Matcher matcher = pattern.matcher(spanStringBuilder);
            while (matcher.find()) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                spanStringBuilder.setSpan(colorSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        /**
         * 文字背景颜色
         */
        public Builder addBackgroundColorSpan(@ColorInt int bgColor) {
            BackgroundColorSpan span = new BackgroundColorSpan(bgColor);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置绝对Size,字体大小
         */
        public Builder addAbsoluteSizeSpan(@IntRange(from = 0) int fontSizePx) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(fontSizePx);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 粗体
         * Typeface.BOLD: 粗体,和斜体只是Typeface不一样而已
         * Typeface.ITALIC: 斜体
         * Typeface.BOLD_ITALIC: 粗体&斜体
         * Typeface.NORMAL: 正常
         */
        public Builder addStyleBoldSpan() {
            StyleSpan span = new StyleSpan(Typeface.BOLD);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 斜体
         */
        public Builder addStyleItalicSpan() {
            StyleSpan span = new StyleSpan(Typeface.ITALIC);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 粗体 & 斜体
         */
        public Builder addStyleBoldItalicSpan() {
            StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 文本外貌(包括字体,大小,样式和颜色)
         * todo 没测试
         */
        public Builder addTextAppearanceSpan() {
            TextAppearanceSpan span = new TextAppearanceSpan(Parcel.obtain());
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 文本字体
         * todo 没测试
         */
        public Builder addTypefaceSpan() {
            TypefaceSpan span = new TypefaceSpan(Parcel.obtain());
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 删除线
         */
        public Builder addStrikethroughSpan() {
            StrikethroughSpan span = new StrikethroughSpan();
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 下划线
         */
        public Builder addUnderLineSpan() {
            UnderlineSpan span = new UnderlineSpan();
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 上标(数学公式会用到)
         */
        public Builder addSuperscriptSpan() {
            SuperscriptSpan span = new SuperscriptSpan();
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 下标(数学公式会用到)
         */
        public Builder addSubscriptSpan() {
            SubscriptSpan span = new SubscriptSpan();
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 模糊: BlurMaskFilter(float radius, Blur style);//半径, style
         * 浮雕: EmbossMaskFilter
         * todo 没测试
         */
        public Builder addMaskFilterSpan() {
            /**
             * BlurMaskFilter.Blur.NORMAL:Blur inside and outside the original border.
             * BlurMaskFilter.Blur.SOLID:Draw solid inside the border, blur outside.
             * BlurMaskFilter.Blur.INNER:Blur inside the border, draw nothing outside.
             * BlurMaskFilter.Blur.OUTER:Draw nothing inside the border, blur outside.
             */
            MaskFilterSpan span = new MaskFilterSpan(new BlurMaskFilter(3f, BlurMaskFilter.Blur.NORMAL));
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 光栅效果
         * todo 没测试
         */
//        public Builder addRasterizerSpan() {
//            RasterizerSpan span = new RasterizerSpan(new LayerRasterizer());//Rasterizer
//            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return this;
//        }

        /**
         * 相当于占位符
         * todo 没测试
         */
        public Builder addSuggestionSpan() {
            SuggestionSpan span = new SuggestionSpan(null);
            spanStringBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableStringBuilder build() {
            return spanStringBuilder;
        }
    }

    public interface OnLishener {
        void onFinish(Builder builder);
    }

    private static Bitmap zoomBitmapToFixWidth(Bitmap bitmap, int maxWidth) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newHight = maxWidth * h / w;
        return zoomBitmap(bitmap, maxWidth, newHight);
    }

    private static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
}
