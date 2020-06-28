LinearGradient extends Shader

/**
 * Create a shader that draws a linear gradient along a line.
 *
 * @param x0           The x-coordinate for the start of the gradient line 渐变起始点坐标
 * @param y0           The y-coordinate for the start of the gradient line
 * @param x1           The x-coordinate for the end of the gradient line 渐变结束点坐标
 * @param y1           The y-coordinate for the end of the gradient line
 * @param colors       The colors to be distributed along the gradient line 渐变数组
 *                     
 * @param positions    May be null. The relative positions [0..1] of
 *                     each corresponding color in the colors array. If this is null,
 *                     the the colors are distributed evenly along the gradient line.
 *                     位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化
 *
 * @param tile         The Shader tiling mode 用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
 *                     -CLAMP边缘拉伸，为被shader覆盖区域，使用shader边界颜色进行填充
 *                     -REPEAT 在水平和垂直两个方向上重复，相邻图像没有间隙
 *                     -MIRROR以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
 */
public LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[],
            @Nullable float positions[], @NonNull TileMode tile) {


/**
 * Create a shader that draws a linear gradient along a line.
 *
 * @param x0       The x-coordinate for the start of the gradient line 渐变起始点坐标
 * @param y0       The y-coordinate for the start of the gradient line
 * @param x1       The x-coordinate for the end of the gradient line 渐变结束点坐标
 * @param y1       The y-coordinate for the end of the gradient line
 * @param color0   The color at the start of the gradient line. 渐变开始点颜色,16进制的颜色表示，必须要带有透明度
 * @param color1   The color at the end of the gradient line. 渐变结束颜色
 *
 * @param tile     The Shader tiling mode 用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法。
 *                     -CLAMP边缘拉伸，为被shader覆盖区域，使用shader边界颜色进行填充
 *                     -REPEAT 在水平和垂直两个方向上重复，相邻图像没有间隙
 *                     -MIRROR以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
*/
public LinearGradient(float x0, float y0, float x1, float y1, @ColorInt int color0, @ColorInt int color1, @NonNull TileMode tile) {