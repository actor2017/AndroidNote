https://github.com/promeG/TinyPinyin
������Java��Android�Ŀ��١����ڴ�ռ�õĺ���תƴ���⡣ȱ��,ת��������ȫƴ

1.����
���ɵ�ƴ����������������Ϊ��д��
֧���Զ���ʵ䣬֧�ּ������ġ��������ģ�
ִ��Ч�ʺܸ�(Pinyin4J��4~16��)��
�ܵ͵��ڴ�ռ�ã�����Ӵʵ�ʱС��30KB����

ԭ�����
������õ�Javaƴ����TinyPinyin��һ�������ַ�תƴ���ļ����Ż�
http://promeg.io/2017/03/18/tinypinyin-part-1/
������õ�Javaƴ����TinyPinyin�������������ֿ��ٴ�����
http://promeg.io/2017/03/20/tinypinyin-part-2/
������õ�Javaƴ����TinyPinyin��������API��ƺͲ���ʵ��
http://promeg.io/2017/03/22/tinypinyin-part-3/


2.ʹ��: ����תƴ��API

//���cΪ���֣��򷵻ش�дƴ�������c���Ǻ��֣��򷵻�String.valueOf(c)
String Pinyin.toPinyin(char c);
String s= Pinyin.toPinyin('��');//LIANG

//�������ַ���תΪƴ����ת�������л�ʹ��֮ǰ���õ��û��ʵ䣬���ַ�Ϊ��λ����ָ���(ע��,��ȫƴ)
String toPinyin(String str, String separator)
String pinyin = Pinyin.toPinyin("������","/");//LIANG/CHAO/JIE

//cΪ���֣��򷵻�true�����򷵻�false
boolean Pinyin.isChinese(char c);


3.�ʵ�API

// ������ĳ��дʵ�
Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

// ����Զ���ʵ�
Pinyin.init(Pinyin.newConfig()
            .with(new PinyinMapDict() {
                @Override
                public Map<String, String[]> mapping() {
                    HashMap<String, String[]> map = new HashMap<String, String[]>();
                    map.put("����",  new String[]{"CHONG", "QING"});
					map.put("��ɳ",  new String[]{"CHANG", "SHA"});
					map.put("����",  new String[]{"CHANG", "CHUN"});
					map.put("��",  new String[]{"XIE"});
                    return map;
                }
            }));

4.��ӵ�����
buildscript {
  repositories {
    jcenter()
  }

  dependencies {
    compile 'com.github.promeg:tinypinyin:2.0.3' // TinyPinyin���İ���Լ80KB
    compile 'com.github.promeg:tinypinyin-lexicons-android-cncity:2.0.3' // ��ѡ��������Android���й�����"����"�ʵ�
    compile 'com.github.promeg:tinypinyin-lexicons-java-cncity:2.0.3' // ��ѡ��������Java���й�����"����"�ʵ�
  }
}
��ϸ˵��

1. ���Ŀ��

Pinyin4J������

Jar�ļ��ϴ�205KB��
Pinyin4J��PinyinHelper.toHanyuPinyinStringArray �ڵ�һ�ε���ʱ��ʱ�ǳ�����~2000ms����
����ӷ�ף������������ǲ���Ҫ���������ԣ�
�޷�����Զ���ʵ䣬�����޷���Ч���������
�ڴ�ռ��̫�ߣ�
TinyPinyin����

ת����Ľ�������������ͷ��ԣ�
֧���Զ���ʵ䣬���㴦������֣�
�����ܵ͵��ڴ�ռ�ã�
��Pinyin4J�����ת���ٶ�;
2. Correctness

��Pinyin4J��Ϊ��׼��ȷ�������е��ַ���Character.MAX_VALUE ~ Character.MIN_VALUE����TinyPinyin��Pinyin4J����ͬ�ķ��ؽ����

��Pinyin4J�����������������������ȡ��һ��ƴ�����жԱȣ�

�ò������PinyinTest.java

�������ĵĲ��������PinyinTest.testToPinyin_traditional_chars()

����������������test��

./gradlew clean build :lib:test :tinypinyin-lexicons-android-cncity:test :tinypinyin-android-asset-lexicons:test :android-sample:connectedAndroidTest
3. Effectiveness

�ٶ�

ʹ��JMH���ߵõ�bechmark���Ա�TinyPinyin��Pinyin4J�������ٶȡ�

����������lib/src/jmh/�е����ܲ��Դ��롣

����������������benchmark��

./gradlew :lib:jmhFixed
���ɵı����� pinyinhelper/build/reports/jmh/ �С�

���ܲ��Խ����Ҫ˵���������ַ�תƴ�����ٶ���Pinyin4j���ı�������ֵ���ַ���תƴ�����ٶ���Pinyin4j��16����

��ϸ���Խ����

Benchmark	Mode	Samples	Score	Unit
TinyPinyin_Init_With_Large_Dict����ʼ����ʵ䣩	thrpt	200	66.131	ops/s
TinyPinyin_Init_With_Small_Dict����ʼ��С�ʵ䣩	thrpt	200	35408.045	ops/s
TinyPinyin_StringToPinyin_With_Large_Dict����Ӵ�ʵ�����Stringתƴ����	thrpt	200	16.268	ops/ms
Pinyin4j_StringToPinyin��Pinyin4j��Stringתƴ����	thrpt	200	1.033	ops/ms
TinyPinyin_CharToPinyin���ַ�תƴ����	thrpt	200	14.285	ops/us
Pinyin4j_CharToPinyin��Pinyin4j���ַ�תƴ����	thrpt	200	4.460	ops/us
TinyPinyin_IsChinese���ַ��Ƿ�Ϊ���֣�	thrpt	200	15.552	ops/us
Pinyin4j_IsChinese��Pinyin4j���ַ��Ƿ�Ϊ���֣�	thrpt	200	4.432	ops/us
�ڴ�ռ��

1. ����Ӵʵ�ʱ

3��static byte[7000] �洢���к��ֵ�ƴ���ĵ�8λ��ռ��7000 1 3 = 21KB �ڴ棻
3��static byte[7000/8] �洢���к��ֵ�ƴ���ĵ�9λ�����λ����ռ��7000 / 8 1 3 = 3KB �ڴ棻
һ��String[408] �洢���п��ܵ�ƴ����ռ�� 1.7KB �ڴ棻
��ռ�� < 30KB.

2. ��Ӵʵ�ʱ

ʹ�á�com.github.promeg:tinypinyin-lexicons-java-cncity:2.0.0��ʱ����������Լ43KB�ڴ档

Todo

֧�ַ�������(��֧��)
֧������ƴ��
ѹ���ʿ�
�ʿ����ɹ���
