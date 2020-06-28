//1.使用path需关闭硬件加速,否则可能出错

//Path封装了由直线和曲线(二次，三次贝塞尔曲线)构成的几何路径。
//你能用Canvas中的drawPath来把这条路径画出来(同样支持Paint的不同绘制模式)，
//也可以用于剪裁画布和根据路径绘制文字。我们有时会用Path来描述一个图像的轮廓，
//所以也会称为轮廓线(轮廓线仅是Path的一种使用方法，两者并不等价)

Path.Direction:方向，趋势.是一个枚举(Enum)类型
CW(clockwise顺时针)				在添加图形时确定闭合顺序(各个点的记录顺序)
CCW(counter-clockwise逆时针)	对图形的渲染结果有影响(是判断图形渲染的重要条件)

RectF矩形:
Rect oval = new Rect(int left, int top, int right, int bottom);
RectF oval = new RectF();
RectF oval = new RectF(float left, float top, float right, float bottom);//构造一个指定了4个参数的矩形.F:float,精度更大
RectF oval = new RectF(RectF r);	//根据指定的RectF对象来构造一个RectF对象(对象的左边坐标不变) 
RectF oval = new RectF(Rect r);		//根据给定的Rect对象来构造一个RectF对象 


//Path一些方法:
Path path = new Path();
path.moveTo(float x, float y);			//移动下一次操作的起点位置
path.setLastPoint(float dx, float dy);	//重置当前终点位置,而当前位置有一个点信息.如果在绘制之前调用，效果和moveTo相同
path.lineTo(float x, float y);			//连接直线.如果没有进行过操作则默认原点为坐标原点
path.close();							//闭合路径.第一个点连接到最后一个点，形成一个闭合区域
path.isEmpty();							//判断Path是否为空
path.isRect(RectF rect);				//判断path是否是一个矩形
path.set(Path src);						//替换路径,用新的路径替换到当前路径所有内容


//添加"矩形"到当前Path
path.addRect(RectF rect, Path.Direction dir);
path.addRect(float left, float top, float right, float bottom, Path.Direction dir);


//添加"圆角矩形"到当前Path
path.addRoundRect(RectF rect, float[] radii, Path.Direction dir);
path.addRoundRect(RectF rect, float rx, float ry, Path.Direction dir);


//添加"椭圆/圆"到当前Path
path.addOval(RectF oval, Path.Direction dir);
path.addCircle(float x, float y, float radius, Path.Direction dir);


//添加'路径'到当前Path
path.addPath(Path src);
path.addPath(Path src, float dx, float dy);	//后2个参数:将src进行了位移之后再添加进当前path中
path.addPath(Path src, Matrix matrix);		//Matrix:将src添加到当前path之前先使用Matrix进行变换


//添加'圆弧'到当前Path(注意addArc和arcTo的区别)
path.addArc(RectF oval, float startAngle, float sweepAngle);					//参数2:圆弧"开始"角度(→开始), 参数3: 角度"范围"
path.arcTo(RectF oval, float startAngle, float sweepAngle);						//如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
path.arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo);//boolean:是否强制使用moveTo,默认false
boolean forceMoveTo:是否强制使用moveTo,是否使用moveTo将变量移动到圆弧的起点位移，也就意味着:
forceMoveTo				含义													  等价方法
true		将最后一个点移动到圆弧起点,即不连接最后一个点与圆弧起点	  public void addArc(RectF oval, float startAngle, float sweepAngle);
false		不将最后一个点移动到圆弧起点,而是连接最后一个点与圆弧起点 public void arcTo(RectF oval, float startAngle, float sweepAngle);


//偏移/平移路径,对当前Path之前的操作进行偏移(不会影响之后的操作)
path.offset(float dx, float dy);
path.offset(float dx, float dy, Path dst);	//dst!=null:将当前path平移后的状态存入实际是替换(set)到dst中,不会影响当前path.
											//dst=null:平移将作用于当前path,相当于上一个方法


path.quadTo贝塞尔曲线	二次贝塞尔曲线的方法
path.cubicTo贝塞尔曲线	三次贝塞尔曲线的方法
path.rXxx方法	rMoveTo, rLineTo, rQuadTo, rCubicTo	不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
path.setFillType, getFillType, isInverseFillType, toggleInverseFillType	填充模式	设置,获取,判断和切换填充模式
path.incReserve提示方法		提示Path还有多少个点等待加入(这个方法貌似会让Path优化存储结构)
path.op布尔操作(API19)		对两个Path进行布尔运算(即取交集、并集等操作)
path.computeBounds计算边界		计算Path的边界
path.reset, 	重置路径	清除Path中的内容.reset不保留内部数据结构，但会保留FillType.
path.rewind	重置路径	清除Path中的内容.rewind会保留内部的数据结构，但不保留FillType
path.transform矩阵操作		矩阵变换


//示例,先创建画笔:
Paint mPaint = new Paint();             // 创建画笔
mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
mPaint.setStrokeWidth(10);              // 边框宽度 - 10

1.lineTo(float x, float y);见图1
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)
Path path = new Path();                     // 创建Path
path.lineTo(200, 200);                      // lineTo
path.lineTo(200,0);
canvas.drawPath(path, mPaint);              // 绘制Path

2.moveTo(float x, float y);见图2
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();                     // 创建Path
path.lineTo(200, 200);                      // lineTo
path.moveTo(200,100);                       // moveTo
path.lineTo(200,0);                         // lineTo
canvas.drawPath(path, mPaint);              // 绘制Path

3.setLastPoint(float dx, float dy);见图3★★★
//setLastPoint是重置上一次操作的最后一个点，在执行完第一次的lineTo的时候，
//最后一个点是A(200,200),而setLastPoint更改最后一个点为C(200,100),所以在实际执行的时候，
//第一次的lineTo就不是从原点O到A(200,200)的连线了，而变成了从原点O到C(200,100)之间的连线了。
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();                     // 创建Path
path.lineTo(200, 200);                      // lineTo
path.setLastPoint(200,100);                 // setLastPoint
path.lineTo(200,0);                         // lineTo
canvas.drawPath(path, mPaint);              // 绘制Path

4.close();见图4
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();                     // 创建Path
path.lineTo(200, 200);                      // lineTo
path.lineTo(200,0);                         // lineTo
path.close();                               // close
canvas.drawPath(path, mPaint);              // 绘制Path

5.addRect(RectF rect, Path.Direction dir);,见图5
5.1.addRect(float left, float top, float right, float bottom, Path.Direction dir);
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();
path.addRect(-200,-200,200,200, Path.Direction.CW);
canvas.drawPath(path,mPaint);

6.addRect后setLastPoint,查看顺时针效果,见图6
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();
path.addRect(-200,-200,200,200, Path.Direction.CW);
path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置
canvas.drawPath(path,mPaint);

7.addRect后setLastPoint,查看逆时针效果,见图7
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
Path path = new Path();
path.addRect(-200,-200,200,200, Path.Direction.CCW);
path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置
canvas.drawPath(path,mPaint);

8.addPath,见图8
8.1.addPath(Path src);						//添加'路径'到当前Path
8.2.addPath(Path src, float dx, float dy);	//后2个参数:将src进行了位移之后再添加进当前path中
8.3.addPath(Path src, Matrix matrix);		//Matrix:将src添加到当前path之前先使用Matrix进行变换
canvas.translate(mWidth / 2, mHeight / 2);  	// 移动坐标系到屏幕中心
canvas.scale(1,-1);                         	// <-- 注意 翻转y坐标轴
Path path = new Path();
Path src = new Path();
path.addRect(-200,-200,200,200, Path.Direction.CW);
src.addCircle(0,0,100, Path.Direction.CW);
path.addPath(src,0,200);
mPaint.setColor(Color.BLACK);           		// 绘制合并后的路径
canvas.drawPath(path,mPaint);

9.addArc(RectF oval, float startAngle, float sweepAngle);见图9
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
Path path = new Path();
path.lineTo(100,100);
RectF oval = new RectF(0,0,300,300);		//圆弧所在大小,RectF是一个矩形
path.addArc(oval,0,270);
// path.arcTo(oval,0,270,true);             // <-- 和上面一句作用等价:将最后一个点移动到圆弧起点,即不连接最后一个点与圆弧起点
canvas.drawPath(path,mPaint);

10.arcTo(RectF oval, float startAngle, float sweepAngle);见图10
10.1.arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo);
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
Path path = new Path();
path.lineTo(100,100);
RectF oval = new RectF(0,0,300,300);
path.arcTo(oval,0,270);
// path.arcTo(oval,0,270,false);             // <-- 和上面一句作用等价:不将最后一个点移动到圆弧起点,而是连接最后一个点与圆弧起点
canvas.drawPath(path,mPaint);

11.isEmpty();
Path path = new Path();
Log.e("1",path.isEmpty()+"");				//true
path.lineTo(100,100);
Log.e("2",path.isEmpty()+"");				//false

12.isRect(RectF rect);//判断path是否是一个矩形，如果是一个矩形的话，会将矩形的信息存放进参数rect中???
path.lineTo(0,400);
path.lineTo(400,400);
path.lineTo(400,0);
path.lineTo(0,0);
RectF rect = new RectF();
boolean b = path.isRect(rect);
Log.e("Rect","isRect:"+b+"| left:"+rect.left+"| top:"+rect.top+"| right:"+rect.right+"| bottom:"+rect.bottom);//isRect:true| left:0.0| top:0.0| right:400.0| bottom:400.0

13.set(Path src);见图13
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
Path path = new Path();                     // path添加一个矩形
path.addRect(-200,-200,200,200, Path.Direction.CW);
Path src = new Path();                      // src添加一个圆
src.addCircle(0,0,100, Path.Direction.CW);
path.set(src);                              // 大致相当于 path = src;
canvas.drawPath(path,mPaint);

14.offset(float dx, float dy);见图4
14.1.offset(float dx, float dy, Path dst);
canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
Path path = new Path();                     // path中添加一个圆形(圆心在坐标原点)
path.addCircle(0,0,100, Path.Direction.CW);
Path dst = new Path();                      // dst中添加一个矩形
dst.addRect(-200,-200,200,200, Path.Direction.CW);
path.offset(300,0,dst);                     // 平移
canvas.drawPath(path,mPaint);               // 绘制path
mPaint.setColor(Color.BLUE);                // 更改画笔颜色
canvas.drawPath(dst,mPaint);                // 绘制dst



