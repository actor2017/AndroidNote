一张图片的加载到内存的占用呢？其实就是所有像素的内存占用总和：
bitmap内存大小 = 图片长度 x 图片宽度 x 单位像素占用的字节数
起决定因素就是最后那个参数了，Bitmap'常见有2种编码方式：ARGB_8888和RGB_565，
ARGB_8888:每个像素点4个byte
RGB_565  :每个像素点2个byte
一般都采用ARGB_8888这种。那么常见的1080*1920的图片内存占用就是：
1920 x 1080 x 4 = 7.9M

压缩原理
图片压缩应该从两个方面入手同时进行：先是降低分辨率，然后降低每个像素的质量也就是内存占用。

主要方法:
BitmapFactory.Options options = new BitmapFactory.Options();
options.inPreferredConfig = Bitmap.Config.ARGB_8888;//默认
Bitmap bitmap = BitmapFactory.decodeFile("path", options);
ByteArrayOutputStream baos = new ByteArrayOutputStream();
bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//100:质量,1-100
