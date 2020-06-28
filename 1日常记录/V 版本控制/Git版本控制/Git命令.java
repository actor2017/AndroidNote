https://git-scm.com/book/zh/v2/%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%B8%8A%E7%9A%84-Git-%E5%8D%8F%E8%AE%AE
1.Git ����ʹ��������Ҫ��Э��(protocol)��
����Э�飨Local��:
    git clone /opt/git/project.git(Ӧ�ò�Ҫ.git)
    git clone file:///opt/git/project.git(�ᴥ����·�������ϵĽ���,Ч�ʽϵ�)
    git remote add ��� /opt/git/project.git(����һ�����ذ汾�����ڵ�Git��Ŀ)
	git remote -v	//�鿴��Ŀ��ַ


HTTP Э��
    1.�ƣ�Dumb�� HTTP Э��(Git1.6.6֮ǰ)
	���������û���ṩ���� HTTP Э��ķ���Git �ͻ��˻᳢��ʹ�ø��򵥵ġ��ơ� HTTP Э�顣 �� HTTP Э���� web ������������汾�⵱����ͨ�ļ����Դ����ṩ�ļ����� �� HTTP Э�������֮���������������򵥡� �����ϣ�ֻ��Ҫ��һ����汾����� HTTP ��Ŀ¼������һ������ post-update �Ĺҹ��Ϳ����ˣ��� Git ���ӣ��� ��ʱ��ֻҪ�ܷ��� web ����������İ汾�⣬�Ϳ��Կ�¡��İ汾�⡣ ���������ô� HTTP ���ʰ汾��ķ�����
$ cd /var/www/htdocs/
$ git clone --bare /path/to/git_project gitproject.git
$ cd gitproject.git
$ mv hooks/post-update.sample hooks/post-update
$ chmod a+x hooks/post-update
	�����Ϳ����ˡ� Git �Դ��� post-update �ҹ���Ĭ��ִ�к��ʵ����git update-server-info������ȷ��ͨ�� HTTP �Ļ�ȡ�Ϳ�¡�������������� �������������ͨ�� SSH ��汾������֮��ִ�У�Ȼ����˾Ϳ���ͨ�������������������¡��
$ git clone https://example.com/gitproject.git
	������������ Apache �������˳��õ�·�� /var/www/htdocs�����������ʹ���κξ�̬ web ������ ���� ֻ��Ҫ����汾��ŵ���ȷ��Ŀ¼�¾Ϳ��ԡ� Git ���������Ի����ľ�̬�ļ���ʽ�ṩ�ģ������ Git �ڲ�ԭ����
	ͨ���ģ����ڿ����ṩ����д������ HTTP ����ͼ򵥵�ֻ������ HTTP ����֮��ѡһ���� ���ٻὫ���߻���ṩ����
    2.���ܣ�Smart�� HTTP Э��
	���з�ʽ�� SSH �� Git Э�����ƣ�ֻ�������ڱ�׼�� HTTP/S �˿��ϲ��ҿ���ʹ�ø��� HTTP ��֤���ƣ�����ζ��ʹ��������� SSH Э��򵥵Ķ࣬�������ʹ�� HTTP Э����û���������Ļ�����Ȩ����ȥ���� SSH ��Կ��
	���� HTTP Э������Ѿ��������е�ʹ�� Git �ķ�ʽ�ˣ�����֧���� git:// Э��һ��������������Ҳ������ SSH Э��һ���ṩ����ʱ����Ȩ�ͼ��ܡ� ����ֻ��һ�� URL �Ϳ��Զ�������ʡȥ��Ϊ��ͬ���������ò�ͬ�� URL�� �����Ҫ���͵�һ����Ҫ��Ȩ�ķ������ϣ�һ����������Ҫ��������������ʾ�������û��������롣 �ӷ�������ȡ����ʱҲһ����

SSH��Secure Shell��Э��
    ���� Git ������ʱ���� SSH Э����Ϊ����Э�顣 ��Ϊ������������Ѿ�֧��ͨ�� SSH ���� ���� ��ʱû��Ҳ�ȽϺ����׼��衣 SSH Э��Ҳ��һ����֤��Ȩ������Э�飻���ң���Ϊ���ձ��ԣ������ʹ�ö������ס�
ͨ�� SSH Э���¡�汾�⣬�����ָ��һ�� ssh:// �� URL��
$ git clone ssh://user@server/project.git
����ʹ��һ����̵� scp ʽ��д����
$ git clone user@server:project.git
��Ҳ���Բ�ָ���û���Git ��ʹ�õ�ǰ��¼���û�����

Git Э��
    �������� Git Э�顣 ���ǰ����� Git ���һ��������ػ����̣���������һ���ض��Ķ˿ڣ�9418���������� SSH ���񣬵��Ƿ��������κ���Ȩ�� Ҫ�ð汾��֧�� Git Э�飬��Ҫ�ȴ���һ�� git-daemon-export-ok �ļ� ���� ���� Git Э���ػ�����Ϊ����汾���ṩ����ı�Ҫ���� ���� ���ǳ���֮��û���κΰ�ȫ��ʩ�� Ҫô˭�����Կ�¡����汾�⣬Ҫô˭Ҳ���ܡ� ����ζ�ţ�ͨ������ͨ�� Git Э�����͡� ����û����Ȩ���ƣ�һ���㿪�����Ͳ�������ζ��������֪�������Ŀ URL ���˶���������Ŀ�������ݡ� ����˵�����ٻ�������ô����

$ git config --global core.autocrlf true	//#�ύʱת��ΪLF�����ʱת��ΪCRLF(windows�Ļس�)
$ git config --global core.autocrlf false	//#�ύ�������ת��(windowsҲ����������)
$ git config --global core.autocrlf input	//#�ύʱת��ΪLF�����ʱ��ת��(linux,mac)

git config --global core.safecrlf true		//#�ܾ��ύ������ϻ��з����ļ�
git config --global core.safecrlf false		//#�����ύ������ϻ��з����ļ�
git config --global core.safecrlf warn		//#�ύ������ϻ��з����ļ�ʱ��������


//����/�޸��û���,--global:ȫ���޸�. ���������Ŀʹ�ò�ͬ������,�����Ǹ���ĿĿ¼������û��--global������������.
git config --global user.name "actor20170211030627"
git config --global user.email 1455198886@qq.com//����name��email��������
git config --global core.editor "D:/'Program Files (x86)'/Notepad++/notepad++.exe"//�����ı��༭��(Ĭ��Vim)

//ʹ��get������ʾ���ú����Ϣ
git config --global --get user.name
git config --global --get user.email


cd C:		//��C��
cd ..		//��һ��
ls		//�ļ�/���б�
exit		//�˳�

//=========================================================================================================
git��git help		//����
git help <verb>		//git help config,��ȡconfig�����ֲ�.git help commit,��ȡcommit�����ֲ�...
git version		//�汾
git init       		//������Ŀ¼�г�ʼ���ֿ�
pwd			//������ʾ��ǰĿ¼,ʾ��:/c/Users/actor/Desktop/test
git config --list	//�����б�
git config user.name	//�鿴ĳ�����õ�ֵ...


git clone url		//��¡��Ŀ����������ļ�����,ʾ����¡����:git clone /c/Users/actor/Desktop/test
git clone url custonName//�Զ���ֿ���
git remote		//�鿴���õ�Զ�ֿ̲�,Ĭ��origin
git remote -v		//��ʾԶ�ֿ̲�ʹ�ñ���ļ�д�����Ӧ�� URL
git remote add ��� url	//���һ��Զ�ֿ̲�

work on the current change (see also: git help everyday)
git add a.txt		//����ļ�����һ���ύ��(����/�޸��ļ���)
git mv fileFrom fileTo	//�ƶ�/����ĳ���ļ�/��

   reset      Reset current HEAD to the specified state //�ع�
git reset --hard 02f1dfb40e5aa95842cb7e761fea60a3dd5050ea	//�޸�HEAD��λ�ã���HEADָ���λ�øı�Ϊ֮ǰ���ڵ�����汾(reset��,HEAD֮��İ汾������)

git revert -n 8b89621019c9adc6fc4d242cd41daeb13aeb9861		//��������ĳһ���汾,�Դﵽ�����ð汾���޸ĵ�Ŀ��(�����С�ķ�����, ���ԡ�)
git commit -m "revert add �汾����"					//��һ����������Ҫ�ύ

git push origin HEAD --force								//��������

rm a.txt		//�����Ƿ����Ѹ����ļ��嵥��,��ɾ��
git rm a.txt		//���Ѹ����ļ��嵥���Ƴ� & ɾ���ļ�
git rm --cached a.txt	//�� Git �ֿ���ɾ��(Ҳ���ݴ������Ƴ�)���Ա����ڴ�����


examine the history and state (see also: git help revisions)
   bisect     Use binary search to find the commit that introduced a bug
   grep       Print lines matching a pattern
git log			//��ʾ�ύ��¼&��ʷ
git log -p -2		//p:��ʾÿ���ύ�����ݲ���. 2:����ʾ���2���ύ
git log --stat		//ÿ���ύ�ļ���ͳ����Ϣ
git log --pretty=oneline//��ÿ���ύ��ʾ��һ��,����short,full,fuller
git log --pretty=oneline --graph//���һЩASCII�ַ��������չʾ
git log --pretty=format:"%h - %an, % ar : % s"//�ύ�����̹�ϣ - ����, �ύʱ�� : �ύ����
			//%H	�ύ����commit����������ϣ�ִ�
			//%h	�ύ����ļ�̹�ϣ�ִ�
			//%T	������tree����������ϣ�ִ�
			//%t	������ļ�̹�ϣ�ִ�
			//%P	������parent����������ϣ�ִ�
			//%p	������ļ�̹�ϣ�ִ�
			//%an	���ߣ�author��������
			//%ae	���ߵĵ����ʼ���ַ
			//%ad	�����޶����ڣ������� --date= ѡ��Ƹ�ʽ��
			//%ar	�����޶����ڣ��������ǰ�ķ�ʽ��ʾ
			//%cn	�ύ�ߣ�committer��������
			//%ce	�ύ�ߵĵ����ʼ���ַ
			//%cd	�ύ����
			//%cr	�ύ���ڣ��������ǰ�ķ�ʽ��ʾ
			//%s	�ύ˵��
git log --pretty=format:"%h %s" --graph//���һЩASCII�ַ��������չʾ
	//-p		��������ʽ��ʾÿ������֮��Ĳ��졣
	//--stat	��ʾÿ�θ��µ��ļ��޸�ͳ����Ϣ��
	//--shortstat	ֻ��ʾ --stat �����������޸�����Ƴ�ͳ�ơ�
	//--name-only	�����ύ��Ϣ����ʾ���޸ĵ��ļ��嵥��
	//--name-status	��ʾ�������޸ġ�ɾ�����ļ��嵥��
	//--abbrev-commit����ʾ SHA-1 ��ǰ�����ַ����������е� 40 ���ַ���
	//--relative-dateʹ�ý϶̵����ʱ����ʾ�����磬��2 weeks ago������
	//--graph	��ʾ ASCII ͼ�α�ʾ�ķ�֧�ϲ���ʷ��
	//--pretty	ʹ��������ʽ��ʾ��ʷ�ύ��Ϣ�����õ�ѡ����� oneline��short��full��fuller �� format�����ָ����ʽ����
	//-(n)	����ʾ����� n ���ύ
	//--since, --after	����ʾָ��ʱ��֮����ύ��
	//--until, --before	����ʾָ��ʱ��֮ǰ���ύ��
	//--author	����ʾָ��������ص��ύ��
	//--committer	����ʾָ���ύ����ص��ύ��
	//--grep	����ʾ��ָ���ؼ��ֵ��ύ
	//-S	����ʾ��ӻ��Ƴ���ĳ���ؼ��ֵ��ύ
git log --since=2weeks	//���2��
git log --pretty="%h - %s" --author=gitster --since="2008-10-01" --before="2008-11-01" --no-merges????? -- t

   show       Show various types of objects
git status		//��ʾ�ļ�״̬(�Ѹ���,���ݴ�,���ύ)
git status -s		//Ҳ��д��--short,��ʾ�ļ�״̬,��Ϊ���յĸ�ʽ���,�ļ�ǰ���־:
			//??	:Ϊ�����ļ�
			//A	:����ӵ��ݴ����е��ļ�
			//M	:�ļ����޸� & �ѷ����ݴ���
			// M	:�ļ����޸� & ��δ�����ݴ���

grow, mark and tweak your common history
   branch     List, create, or delete branches
   checkout   Switch branches or restore working tree files
git commit		//�ύ,�ڵ������ı��ڵ�1�б��������ύע��,�ύ������Ǹ���֧(master)�ύ��&����sha-1У��
git commit -v		//�ύ,����ʾ�޸�����(diff),���ڵ�1�б��������ύע��
git commit -m "�ύ��xx"//ֱ�������ύע��
git commit -a -m "xxx"	//�Զ����Ը��ٹ����ļ��ݴ�һ���ύ,����git add����
git diff		//��ʾ������Щ�з����˸ı�Show changes between commits, commit and working tree, etc
git diff --cached	//��ʾ���ݴ�Ľ�Ҫ��ӵ��´��ύ�������
git diff --staged	//1.6�汾+,����һ��Ч��
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
