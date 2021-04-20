@ECHO OFF
@echo 1.打开'hosts'所在文件夹: C:\Windows\System32\drivers\etc\
start "" "C:\Windows\System32\drivers\etc"

@ECHO.
@echo 2.将下载的GithubHost信息填入上方目录的'hosts'文件中, 填完后请按任意键继续...

pause

@ECHO.
@echo 3.刷新dns
ipconfig /flushdns

pause
