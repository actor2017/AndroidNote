https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/README-CN.md
��������� -> ClipboardUtils.java -> Test
copyText  : �����ı���������
getText   : ��ȡ��������ı�
copyUri   : ���� uri ��������
getUri    : ��ȡ������� uri
copyIntent: ������ͼ��������
getIntent : ��ȡ���������ͼ



AndroidӦ�ÿ���֮��ͨ��ClipboardManager, ClipData���и���ճ����

�ڿ���һЩϵͳӦ�õ�ʱ�����ǻ��õ�Android�ļ����幦�ܣ����罫�ı��ļ�������������ʽ�����ݸ��Ƶ���������ߴӼ������ȡ���ݵȲ�����Androidƽ̨��ÿ�������Ӧ���������Լ��Ľ��̿ռ��У������Win32����Android��֮��Ľ��̼䴫����Ҫ��IPC�����а塣��Ȼ��������˵����򵥵�ClipboardManager��ʹ�ü��а����ֱ��ʵ�����ݵĴ��䡣����ʵ�ֱȽϼ򵥣�ע����а��е������жϡ�
ʹ�������ܼ򵥣�ϵͳ�������ṩ�˺ܷ���Ľӿڣ������ı���Ϣ����������ʾ��
//��ȡ������������
ClipboardManager cm =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//���ı����ݸ��Ƶ�������
cm.setText(message);
//��ȡ����������
cm.getText();