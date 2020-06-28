https://git-scm.com/book/zh/v2/%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%B8%8A%E7%9A%84-Git-%E5%8D%8F%E8%AE%AE
1.Git 可以使用四种主要的协议(protocol)：
本地协议（Local）:
    git clone /opt/git/project.git(应该不要.git)
    git clone file:///opt/git/project.git(会触发网路传输资料的进程,效率较低)
    git remote add 简称 /opt/git/project.git(增加一个本地版本到现在的Git项目)
	git remote -v	//查看项目地址


HTTP 协议
    1.哑（Dumb） HTTP 协议(Git1.6.6之前)
	如果服务器没有提供智能 HTTP 协议的服务，Git 客户端会尝试使用更简单的“哑” HTTP 协议。 哑 HTTP 协议里 web 服务器仅把裸版本库当作普通文件来对待，提供文件服务。 哑 HTTP 协议的优美之处在于设置起来简单。 基本上，只需要把一个裸版本库放在 HTTP 根目录，设置一个叫做 post-update 的挂钩就可以了（见 Git 钩子）。 此时，只要能访问 web 服务器上你的版本库，就可以克隆你的版本库。 下面是设置从 HTTP 访问版本库的方法：
$ cd /var/www/htdocs/
$ git clone --bare /path/to/git_project gitproject.git
$ cd gitproject.git
$ mv hooks/post-update.sample hooks/post-update
$ chmod a+x hooks/post-update
	这样就可以了。 Git 自带的 post-update 挂钩会默认执行合适的命令（git update-server-info），来确保通过 HTTP 的获取和克隆操作正常工作。 这条命令会在你通过 SSH 向版本库推送之后被执行；然后别人就可以通过类似下面的命令来克隆：
$ git clone https://example.com/gitproject.git
	这里我们用了 Apache 里设置了常用的路径 /var/www/htdocs，不过你可以使用任何静态 web 服务器 —— 只需要把裸版本库放到正确的目录下就可以。 Git 的数据是以基本的静态文件形式提供的（详情见 Git 内部原理）。
	通常的，会在可以提供读／写的智能 HTTP 服务和简单的只读的哑 HTTP 服务之间选一个。 极少会将二者混合提供服务。
    2.智能（Smart） HTTP 协议
	运行方式和 SSH 及 Git 协议类似，只是运行在标准的 HTTP/S 端口上并且可以使用各种 HTTP 验证机制，这意味着使用起来会比 SSH 协议简单的多，比如可以使用 HTTP 协议的用户名／密码的基础授权，免去设置 SSH 公钥。
	智能 HTTP 协议或许已经是最流行的使用 Git 的方式了，它即支持像 git:// 协议一样设置匿名服务，也可以像 SSH 协议一样提供传输时的授权和加密。 而且只用一个 URL 就可以都做到，省去了为不同的需求设置不同的 URL。 如果你要推送到一个需要授权的服务器上（一般来讲都需要），服务器会提示你输入用户名和密码。 从服务器获取数据时也一样。

SSH（Secure Shell）协议
    架设 Git 服务器时常用 SSH 协议作为传输协议。 因为大多数环境下已经支持通过 SSH 访问 —— 即时没有也比较很容易架设。 SSH 协议也是一个验证授权的网络协议；并且，因为其普遍性，架设和使用都很容易。
通过 SSH 协议克隆版本库，你可以指定一个 ssh:// 的 URL：
$ git clone ssh://user@server/project.git
或者使用一个简短的 scp 式的写法：
$ git clone user@server:project.git
你也可以不指定用户，Git 会使用当前登录的用户名。

Git 协议
    接下来是 Git 协议。 这是包含在 Git 里的一个特殊的守护进程；它监听在一个特定的端口（9418），类似于 SSH 服务，但是访问无需任何授权。 要让版本库支持 Git 协议，需要先创建一个 git-daemon-export-ok 文件 —— 它是 Git 协议守护进程为这个版本库提供服务的必要条件 —— 但是除此之外没有任何安全措施。 要么谁都可以克隆这个版本库，要么谁也不能。 这意味着，通常不能通过 Git 协议推送。 由于没有授权机制，一旦你开放推送操作，意味着网络上知道这个项目 URL 的人都可以向项目推送数据。 不用说，极少会有人这么做。

$ git config --global core.autocrlf true	//#提交时转换为LF，检出时转换为CRLF(windows的回车)
$ git config --global core.autocrlf false	//#提交检出均不转换(windows也可这样设置)
$ git config --global core.autocrlf input	//#提交时转换为LF，检出时不转换(linux,mac)

git config --global core.safecrlf true		//#拒绝提交包含混合换行符的文件
git config --global core.safecrlf false		//#允许提交包含混合换行符的文件
git config --global core.safecrlf warn		//#提交包含混合换行符的文件时给出警告


//设置/修改用户名,--global:全局修改. 如果特殊项目使用不同的名称,可在那个项目目录下运行没有--global的命令来配置.
git config --global user.name "actor20170211030627"
git config --global user.email 1455198886@qq.com//★★★name和email必须配置
git config --global core.editor "D:/'Program Files (x86)'/Notepad++/notepad++.exe"//设置文本编辑器(默认Vim)

//使用get参数显示设置后的信息
git config --global --get user.name
git config --global --get user.email


cd C:		//打开C盘
cd ..		//上一级
ls		//文件/夹列表
exit		//退出

//=========================================================================================================
git或git help		//帮助
git help <verb>		//git help config,获取config命令手册.git help commit,获取commit命令手册...
git version		//版本
git init       		//在现有目录中初始化仓库
pwd			//★★★显示当前目录,示例:/c/Users/actor/Desktop/test
git config --list	//配置列表
git config user.name	//查看某个配置的值...


git clone url		//克隆项目到现在这个文件夹里,示例克隆本地:git clone /c/Users/actor/Desktop/test
git clone url custonName//自定义仓库名
git remote		//查看配置的远程仓库,默认origin
git remote -v		//显示远程仓库使用保存的简写与其对应的 URL
git remote add 简称 url	//添加一个远程仓库

work on the current change (see also: git help everyday)
git add a.txt		//添加文件到下一次提交中(新增/修改文件后)
git mv fileFrom fileTo	//移动/改名某个文件/夹

   reset      Reset current HEAD to the specified state //回滚
git reset --hard 02f1dfb40e5aa95842cb7e761fea60a3dd5050ea	//修改HEAD的位置，将HEAD指向的位置改变为之前存在的这个版本(reset后,HEAD之后的版本不见了)

git revert -n 8b89621019c9adc6fc4d242cd41daeb13aeb9861		//“反做”某一个版本,以达到撤销该版本的修改的目的(如果不小心反做了, 可以↑)
git commit -m "revert add 版本名称"					//上一条反做后需要提交

git push origin HEAD --force								//撤销操作

rm a.txt		//不管是否在已跟踪文件清单中,都删除
git rm a.txt		//从已跟踪文件清单中移除 & 删除文件
git rm --cached a.txt	//从 Git 仓库中删除(也从暂存区域移除)但仍保留在磁盘中


examine the history and state (see also: git help revisions)
   bisect     Use binary search to find the commit that introduced a bug
   grep       Print lines matching a pattern
git log			//显示提交记录&历史
git log -p -2		//p:显示每次提交的内容差异. 2:仅显示最近2次提交
git log --stat		//每次提交的简略统计信息
git log --pretty=oneline//将每个提交显示在一行,还有short,full,fuller
git log --pretty=oneline --graph//添加一些ASCII字符来形象的展示
git log --pretty=format:"%h - %an, % ar : % s"//提交对象简短哈希 - 作者, 提交时间 : 提交书面
			//%H	提交对象（commit）的完整哈希字串
			//%h	提交对象的简短哈希字串
			//%T	树对象（tree）的完整哈希字串
			//%t	树对象的简短哈希字串
			//%P	父对象（parent）的完整哈希字串
			//%p	父对象的简短哈希字串
			//%an	作者（author）的名字
			//%ae	作者的电子邮件地址
			//%ad	作者修订日期（可以用 --date= 选项定制格式）
			//%ar	作者修订日期，按多久以前的方式显示
			//%cn	提交者（committer）的名字
			//%ce	提交者的电子邮件地址
			//%cd	提交日期
			//%cr	提交日期，按多久以前的方式显示
			//%s	提交说明
git log --pretty=format:"%h %s" --graph//添加一些ASCII字符来形象的展示
	//-p		按补丁格式显示每个更新之间的差异。
	//--stat	显示每次更新的文件修改统计信息。
	//--shortstat	只显示 --stat 中最后的行数修改添加移除统计。
	//--name-only	仅在提交信息后显示已修改的文件清单。
	//--name-status	显示新增、修改、删除的文件清单。
	//--abbrev-commit仅显示 SHA-1 的前几个字符，而非所有的 40 个字符。
	//--relative-date使用较短的相对时间显示（比如，“2 weeks ago”）。
	//--graph	显示 ASCII 图形表示的分支合并历史。
	//--pretty	使用其他格式显示历史提交信息。可用的选项包括 oneline，short，full，fuller 和 format（后跟指定格式）。
	//-(n)	仅显示最近的 n 条提交
	//--since, --after	仅显示指定时间之后的提交。
	//--until, --before	仅显示指定时间之前的提交。
	//--author	仅显示指定作者相关的提交。
	//--committer	仅显示指定提交者相关的提交。
	//--grep	仅显示含指定关键字的提交
	//-S	仅显示添加或移除了某个关键字的提交
git log --since=2weeks	//最近2周
git log --pretty="%h - %s" --author=gitster --since="2008-10-01" --before="2008-11-01" --no-merges????? -- t

   show       Show various types of objects
git status		//显示文件状态(已更新,已暂存,以提交)
git status -s		//也可写成--short,显示文件状态,更为紧凑的格式输出,文件前面标志:
			//??	:为跟踪文件
			//A	:新添加到暂存区中的文件
			//M	:文件被修改 & 已放入暂存区
			// M	:文件被修改 & 还未放入暂存区

grow, mark and tweak your common history
   branch     List, create, or delete branches
   checkout   Switch branches or restore working tree files
git commit		//提交,在弹出的文本内第1行必须输入提交注释,提交后输出那个分支(master)提交的&完整sha-1校验
git commit -v		//提交,并显示修改内容(diff),需在第1行必须输入提交注释
git commit -m "提交了xx"//直接输入提交注释
git commit -a -m "xxx"	//自动把以跟踪过的文件暂存一起提交,跳过git add步骤
git diff		//显示具体哪些行发生了改变Show changes between commits, commit and working tree, etc
git diff --cached	//显示已暂存的将要添加到下次提交里的内容
git diff --staged	//1.6版本+,上面一样效果
   merge      Join two or more development histories together
   rebase     Reapply commits on top of another base tip
   tag        Create, list, delete or verify a tag object signed with GPG

collaborate (see also: git help workflows)
   fetch      Download objects and refs from another repository
   pull       Fetch from and integrate with another repository or a local branch
   push       Update remote refs along with associated objects

'git help -a' and 'git help -g' list available subcommands and some
concept guides. See 'git help <command>' or 'git help <concept>'
to read about a specific subcommand or concept.
