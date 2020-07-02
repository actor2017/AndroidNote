package com.itheima.androidl.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.androidl.R;

/**
 * 阴影和裁剪
 */
public class ShadowFragment extends BaseFragment{

	TextView tv1,tv2,circle1,circle2,circle3,circle4,circle5,circle6,cut1,cut2,cut3,cut4,cut5,cut6;
	EditText et1,et2;
	Button bt;

	protected View initView(){
		View view = View.inflate(getActivity(), R.layout.fragment_shadow,null);

        //改变z轴
		changeZ(view);

        //轮廓
		outLine(view);

        //裁剪
		cut(view);

		return view;
	}

	/**
	 * 改变z轴
	 * 做出一个有层次感的界面
     */
	private void changeZ(View view){
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);

		et1 = (EditText) view.findViewById(R.id.et1);
		et2 = (EditText) view.findViewById(R.id.et2);

		bt = (Button) view.findViewById(R.id.bt);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String str1 = et1.getText().toString();
				String str2 = et2.getText().toString();
				if (str1 == null || str1.isEmpty()){
					str1 = "0";
				}
				if (str2 == null || str2.isEmpty()){
					str2 = "0";
				}
				int z1 = Integer.valueOf(str1);
				int z2 = Integer.valueOf(str2);

				tv1.setText(z1 + "dp");
				tv2.setText(z2 + "dp");

				tv1.setElevation(z1);
				tv2.setElevation(z2);
			}
		});
	}

    //轮廓
	private void outLine(View view){
		circle1 = (TextView) view.findViewById(R.id.tv_circle_1);
		circle2 = (TextView) view.findViewById(R.id.tv_circle_2);
		circle3 = (TextView) view.findViewById(R.id.tv_circle_3);
		circle4 = (TextView) view.findViewById(R.id.tv_circle_4);
		circle5 = (TextView) view.findViewById(R.id.tv_circle_5);
		circle6 = (TextView) view.findViewById(R.id.tv_circle_6);

        circle1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setAlpha(1);//0-1
                // 可以指定圆形，矩形，圆角矩形，Path
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());//left, top, right, bottom
//                outline.setConvexPath();//Path
            }
        });
		circle2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
		circle3.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, view.getWidth(), view.getHeight());
            }
        });
		circle4.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 8L);//圆角矩形,Radius
            }
        });
        circle5.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Path p = new Path();
                p.moveTo(view.getWidth()/2, 0);
                p.lineTo(0, view.getHeight());
                p.lineTo(view.getWidth(), view.getHeight());
                p.close();
                outline.setConvexPath(p);
            }
        });
        circle6.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Path p = new Path();
                p.moveTo(view.getWidth()/2, 0);
                p.lineTo(0, view.getHeight());
                p.lineTo(view.getWidth(), view.getHeight());
                p.close();
                outline.setConvexPath(p);
            }
        });
        ImageView ivCircle7 = (ImageView) view.findViewById(R.id.iv_circle_7);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.circle);//背景
        Bitmap triangle = BitmapFactory.decodeResource(getResources(), R.drawable.triangle);//三角形
        //创建一个空的bitmap存放合成的图片
        final Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(triangle, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        paint.setXfermode(null);
        ivCircle7.setImageBitmap(result);

        ivCircle7.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int x = (view.getWidth() - result.getWidth())/2;
                int y = (view.getHeight() - result.getHeight())/2;
                Path p = new Path();
                p.moveTo(x, y);
                p.lineTo(x + result.getWidth(), y);
                p.lineTo(x + result.getWidth()/2, y + result.getHeight());
                p.close();
                outline.setConvexPath(p);

//                Path p = new Path();
//                p.moveTo(view.getWidth()/2, 0);
//                p.lineTo(0, view.getHeight());
//                p.lineTo(view.getWidth(), view.getHeight());
//                p.close();
//                outline.setConvexPath(p);
            }
        });
        //下面不知道什么效果,默认false
//        circle1.setClipToOutline(true);
//        circle2.setClipToOutline(true);
//        circle3.setClipToOutline(true);
//        circle4.setClipToOutline(true);
//        circle5.setClipToOutline(false);
//        circle6.setClipToOutline(false);
//        ivCircle7.setClipToOutline(true);
	}

    //裁剪
	private void cut(View view){
		cut1 = (TextView) view.findViewById(R.id.cut1);
		cut2 = (TextView) view.findViewById(R.id.cut2);
		cut3 = (TextView) view.findViewById(R.id.cut3);
		cut4 = (TextView) view.findViewById(R.id.cut4);
		cut5 = (TextView) view.findViewById(R.id.cut5);
		cut6 = (TextView) view.findViewById(R.id.cut6);

        //1.三角形△
        cut2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Path p = new Path();
                p.moveTo(view.getWidth()/2, -20);
                p.lineTo(-20, view.getHeight());
                p.lineTo(view.getWidth() + 20, view.getHeight());
                p.close();
                outline.setConvexPath(p);
            }
        });

        //2.圆形
        cut3.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0,0,view.getWidth(),view.getHeight());
            }
        });

        //3.棱形◇
		cut4.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Path path = new Path();
                path.moveTo(view.getWidth()/2, -20);
                path.lineTo(-20, view.getHeight()/2);
                path.lineTo(view.getWidth()/2, view.getHeight() + 20);
                path.lineTo(view.getWidth() + 20, view.getHeight()/2);
                path.close();
                outline.setConvexPath(path);
            }
        });

        //4.扩大矩形的轮廓后裁剪
		cut5.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-20,-20,view.getWidth() + 20,view.getHeight()+20);
            }
        });

        //5.减小矩形的轮廓后剪裁成矩形
        cut6.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(10,10,view.getWidth() - 10,view.getHeight()-10);
            }
        });
        cut2.setClipToOutline(false);
        cut3.setClipToOutline(false);
        cut4.setClipToOutline(false);
        cut5.setClipToOutline(false);
        cut6.setClipToOutline(false);


        cut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"扩大矩形的轮廓后剪裁",Toast.LENGTH_SHORT).show();
            }
        });
        cut6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"减小矩形的轮廓后剪裁",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public String getUrl() {
		return "file:///android_asset/shadow.html";
	}
}
