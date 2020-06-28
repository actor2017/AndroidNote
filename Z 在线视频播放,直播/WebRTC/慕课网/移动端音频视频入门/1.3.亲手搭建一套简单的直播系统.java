https://www.imooc.com/video/16763

1.mac终端运行命令安装nginx服务+rtmp模块,等待下载编译安装.见图片1
brew install nginx-full --with-rtmp-module

2.修改配置文件 /usr/local/etc/nginx/nginx.conf
#user nobody;
worker_processes  1;//工作处理器个数

#error_log  logs/error.log
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;//事件连接数
}

#配置 rtmp server
rtmp {
    server {
        #指定服务端口
        listen 1935;
        chunk_size 4000;//进行音视频传输时每个传输块的大小4000byte

        #指定rtmp中一个流应用,叫live
        application live {
            live on;//功能打开
            record off;//关闭录制
            allow play all;//允许任何人发起请求
        }
    }
}

http {
    #其它配置...
}


3.第一次配置服务,启动: nginx
  如果不是第一次配置服务,重启服务: nginx -s reload
  如果重启出问题,可以先停止服务: nginx -s stop
  查看网络状态,查看是否有1935端口: netstat -an |grep 1935
          查询结果示例:tcp4    0    0    *.1935    *.*    LISTEN

4.常用工具
  ffmpeg, ffplay(基于ffmpeg的播放器,命令行形式播放), flashplayer(播放rtmp流)

4.1.FFMPEG直播命令
  推流: ffmpeg -re -i out.mp4 -c copy -f flv rtmp://server/live/streamName
  通过ffmpeg将out.mp4推送到rtmp服务地址

  拉流: ffmpeg -i rtmp://server/live/streamName -c copy dump.flv
  通过ffmpeg从rtmp地址输入(-i), 输出为dump.flv

4.2.测试流媒体服务器
    1.ffmpeg将一个直播上的音视频流,推送到本地的直播服务器
ffmpeg -i http://pcws.qing.mgtv.com/nn_live/nn_x64/bm5fdGltzXpvbmU90CZjZG5leF9pZD13c19wY19saXZlMyZ1dWlkPTI5NjU2MjU3YmQyMDRhNDUmZT01MDUzNTMwJnY9MSZpZD1ITllMTVBQMzYwJnM9NjVjMjBlNjQ4ZDM1ZDhkoDM0oGZmNTNh0GYyYTQ0YmY,/HNYLMPP360.flv?nn_timezone=8 -c:a copy -c:v copy -f flv rtmp://localhost:1935/live/room

    2.播放地址 rtmp://localhost:1935/live/room

    3.拉流:http://bbs.chinaffmpeg.com/1.swf

    4.ffplay命令行播放流: ffplay rtmp://localhost:1935/live/room

