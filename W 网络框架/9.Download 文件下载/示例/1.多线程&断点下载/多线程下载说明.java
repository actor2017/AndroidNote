RandomAccessFile raf = new RandomAccessFile("path", "rw");随机访问流,

setLength(long newLength);//创建一个指定大小的空文件
seek(long pos);//从pos出开始写文件


客户端请求服务器,在请求头里告诉服务器下载的开始位置&结束位置:
Range:bytes=开始位置-结束位置
