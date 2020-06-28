https://github.com/greenrobot/greenDAO
http://greenrobot.org/greendao/
greenDAO��һ�����Android�������ORM�����������������ӳ�䵽SQLite���ݿ⡣

1.root build.gradle
buildscript {
    repositories {
        jcenter()
        mavenCentral() // add repository
    }
    dependencies {
		//greendao-gradle-plugin���°汾: https://search.maven.org/search?q=g:org.greenrobot%20AND%20a:greendao-gradle-plugin
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin
    }
}

2.app/build.gradle:
//greenDAO Gradle���: �ڹ�����Ŀʱ����������DaoMaster��DaoSession��DAOs����
apply plugin: 'org.greenrobot.greendao' // apply plugin
android {
	...
	greendao{
		schemaVersion 1                 //ָ�����ݿ�schema�汾�ţ�Ǩ�ƵȲ������õ�
		daoPackage 'com.greendao.gen'   //dao�İ���������Ĭ����entity���ڵİ�
		targetGenDir 'src/main/java'    //�������ݿ��ļ���Ŀ¼
		//targetGenDirTest false		//���ɵ�Ԫ����
        //generateTests 'src/androidTest/java'	//���ɵĵ�Ԫ���ԵĻ���Ŀ¼
	}
}
dependencies {
	//greendao���°汾: https://search.maven.org/search?q=g:org.greenrobot%20AND%20a:greendao
    implementation 'org.greenrobot:greendao:3.2.2' // add library
}


3.�ĵ�: http://greenrobot.org/greendao/documentation/
Getting Started����ҳ��: http://greenrobot.org/greendao/documentation/how-to-get-started/


4.����: If your project uses R8 or ProGuard add the following rules:
##-----------Begin: proguard configuration for GreenDao----------
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties {*;}

# If you do not use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do not use RxJava:
-dontwarn rx.**
##-----------End: proguard configuration for GreenDao------------

5.����������˵��:
5.1.DaoMaster: Dao�еĹ����ߡ���������sqlitedatebase�����Լ�����DAO classes��ע�⣺���Ƕ��󣩡�
  ���ṩ��һЩ������ɾ��table�ľ�̬���������ڲ���OpenHelper��DevOpenHelperʵ����SQLiteOpenHelper��
  ���������ݿ�Ŀ�ܡ�
5.2.DaoSession: �Ự�㡣���������DAO����ע�⣺�Ƕ��󣩣��������getter����
5.3.Daos: ʵ�����ɵ�ĳĳDAO�࣬ͨ����Ӧ�����java�࣬����NoteDao�ȡ����и����Ȩ�޺ͷ������������ݿ�Ԫ��
5.4.Entities: �־õ�ʵ�����ͨ��������һ�����ݿ�row�ı�׼java properties


//һƪ��������֮Android���ݿ� GreenDao��ʹ����ȫ���� - ����.html
https://www.jianshu.com/p/53083f782ea2
6.ʹ��
  6.1.����һ��ʵ����
  //annotation
  @Convert(converter = MapConverter.class, columnType = String.class) //����������ע����Զ����ֶ�, ת�������ݿ��ܹ��洢����������
  private Map<String, Object> params;
  
  @Entity			//���ʵ����������ݿ������ɶ�Ӧ�ı�
						nameInDb: 					//�����ñ�ı�����Ĭ��ȡ����
						indexes: {}					//����,����:Index[]
													//1.���ڽ�������, Ӧ�ó���: �����ж������ʱ, ����־һ�����ݵ�Ψһ��, ���unique.
													//2.������DAOӦ�ô������ݿ��(Ĭ��Ϊtrue)��������ж��ʵ��ӳ�䵽һ����
													//���߱�Ĵ�������greenDAO֮����еģ���ô��������Ϊfalse
													//��:indexes = {@Index(value = "name DESC", unique = true)}

						createInDb: true			//�Ƿ񴴽���Ĭ��Ϊtrue, falseʱ������??ʲô����
						schema: "default"			//���ж��schema(����), ��������Ը������ݿ⵱ǰentity�����ĸ�schema(����)

						active: false				//��־ĳ��entity�Ƿ���active(��Ծ״̬)�ģ�active��ʵ������ɾ�ĵķ���,
													//Ϊtrue��ʱ�����entity�����Զ���������Ĵ���:
													//ɾ��
													@Generated(hash = 128553479)
												   public void delete() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.delete(this);
												   }
												   //ˢ��
												   @Generated(hash = 1942392019)
												   public void refresh() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.refresh(this);
												   }
												   //����
												   @Generated(hash = 713229351)
												   public void update() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.update(this);
												   }
													
						generateConstructors: true	//�Ƿ���'��ʵ����'���ɺ����в����Ĺ��캯����Ĭ��Ϊtrue
						generateGettersSetters: true//�Ƿ���'��ʵ����'����getter/setter��Ĭ��Ϊtrue
						protobuf: void.class
						
  @Generated		//������Ŀ����ʵ�����Զ����ɵĹ��췽��
						hash						//int
						
  @Id				//���ֶ���id��ע����ֶε���������Ϊ��װ���͡���Long/long����
						autoincrement = false		//������, �������������=true, ������Long����
						
  @Index			//Ϊ�����ڶ�Ӧ�����ݿ��д������ݿ�����
						value
						name: ""		//�����ϲ��Ϊ�������ɵ�Ĭ�����ƣ�����������ָ���Լ�������
						unique: false	//Ϊ�������ΨһԼ����ǿ������ֵ����Ψһ��(trueʱ�����ͬ�������쳣)
				//ʾ��:
				//��ʱ�����ǵ�������һ����long����Long�͵Ŀ�����string֮��ģ�
				//���ʱ�����ǾͿ��Զ����������Բ���ע����һ�޶�:
				@Index(name = "keyword", unique = true)
				private String key;
						
  @JoinEntity		//��Զ�?
						entity			//Class<?>, 
						sourceProperty	//string
						targetProperty	//string
  @JoinProperty
  @Keep				//https://www.jianshu.com/p/1044c9cdcc97
					//����@Generated ע�⡣�⽫����greenDAO��Զ��Ҫ������ע��Ĵ��롣
					//���ĸ��Ŀ��ܻ��ƻ�ʵ���greenDAO���ಿ��֮�����Լ��
					//���⣬greenDAO��δ���汾���ܻ������ɵķ������в�ͬ�Ĵ��롣
					//����,С��!���ʵ��ĵط����е�Ԫ�����Ա����鷳��һ��������
					
  @NotNull			//���ֶβ�����Ϊ��, ͨ����ǻ�����������(long, int...)��Ϊ��, �����ǿ�Ϊ�յ�����

  @OrderBy			//����

  @Property 		//�����Խ���Ϊ���һ���ֶ�
						nameInDb="URL"//����ĳ�����ڱ��е�����, �����д, Ĭ�ϴ�д�շ�, ʾ��: customName -> CUSTOM_NAME

  @ToMany			//һ�Զ�(һ��ѧУ��Ӧ�ж��ѧ��)
						referencedJoinProperty="studentId"//���studentId��'ѧУ'�ж�Ӧ��studentId
						joinProperties

  @ToOne			//һ��һ(һ��ѧ����Ӧһ��ѧУ)
						joinProperty="���id����"

  @Transient		//������������ӳ�䵽���ݱ���

  @Unique			//���ֶ�Ψһ
  
  //annotation/apihint
  @Beta
  @Experimental
  @Internal
  
  //converter
  @PropertyConverter


/**
 * json��map����ת��
 * 1.������public
 * 2.������ڲ���, ������static
 */
public class MapConverter implements PropertyConverter<Map<String, Object>, String> {
	@Override
	public Map<String, Object> convertToEntityProperty(String databaseValue) {
		if (databaseValue == null) return null;
		return JSONObject.parseObject(databaseValue, Map.class);
	}
	@Override
	public String convertToDatabaseValue(Map<String, Object> entityProperty) {
		if (entityProperty == null) return null;
		return JSONObject.toJSONString(entityProperty);
	}
}

6.2. 1�Զ�, ���1 ʾ��(ѧ����ѧУ):
@Entity
class Student{
    private long fk_schoolId;//���
    @ToOne(joinProperty = "fk_schoolId")
    private School school;
}
@Entity
class School{
    @ToMany(referencedJoinProperty = "fk_schoolId")
    private List<Student> students;
}

6.3. ��Զ� ʾ��(ѧ����γ�):
@Entity
class Student{
    @ToMany
    @JoinEntity(
            entity = StudentWithCourse.class,
            sourceProperty = "sId",
            targetProperty = "cId"
    )
    private List<Course> courses;
}

@Entity 
class Course{
    @ToMany
    @JoinEntity(
            entity = StudentWithCourse.class,
            sourceProperty = "cId",
            targetProperty = "sId"
    )
    private List<Course> courses;
}

@Entity
class StudentWithCourse{
    @Id
    private Long id;
    private Long sId;
    private Long cId;
}

7.������Ŀ: Build -> Make Project(ctrl + F9)
  ʵ�����л��Զ�����get��set�������һ���com.greendao.genĿ¼�����������ļ�: DaoMaster, DaoSession, ItemEntityDao(�Լ�д��ʵ��+Dao)

8.MyApplication�г�ʼ�����ݿ�:
public GreenDaoUtils greenDaoUtils;
greenDaoUtils = GreenDaoUtils.getInstance(this, isDebugMode);


8.2.Java��:
WhereCondition ����:
eq 					//���
notEq 				//�����
like				//ģ����ѯ, stringҪ�ü���%key%�м�, ��: Properties.FirstName.like("%doris%"), ��ѯFristName����doris����
gt >				//����
lt <				//С��
ge >=				//���ڵ���
le <=				//С�ڵ���
between()			//...��...֮��
in��..., ..., ...)	//�ڸ�����value�ķ�Χ�ڵķ�����
notIn()
isNull		//Ϊ��
isNotNull	//��Ϊ��


QueryBuilder ����:
or					//����
whereOr				//��
and(>=2������)		//����
orderAsc	//����(����, С->��)	List list = dao.queryBuilder().orderAsc(SonDao.Properties.Age).list();
orderDesc	//����(����, ��->С)
limit(int)	//���Ʋ�ѯ���ص�����
offset(int)	//�������ݷ���ƫ�����ֵ, ���limitʹ��, ��: limit(3), offset(2): ���[1,2,3] => [3,4,5]
			//queryBuilder().where(Properties.Year.gt(2001)).offset(3).limit(10);
			//��ݴ���2001��ĵ�3����ʼ��ʮ����
join		//����ѯ


list() ���������أ�������ѯ����ѯ���������ص��ڴ�
listLazy() ʵ�尴����ص��ڴ��С� һ���б��е�Ԫ�ص�һ�α����ʣ����������ز������Թ�����ʹ�á� ����ر�
listLazyUncached() һ�������⡱ʵ���б����б�Ԫ�ص��κη��ʶ��ᵼ�´����ݿ���������ݡ� ����ر�
listIterator() ������ͨ������������ݣ�����أ������������ ����û�б����档 ����ر�





//��ȡdao
private ItemEntityDao dao = MyApplication.instance.greenDaoUtils.getDaoSession().getItemEntityDao();

dao.insert(T);//��
dao.insertInTx(T...);//��������
dao.insertInTx(Iterable<T> entities);
dao.insertOrReplace(T);//�������滻�������������, �����������ݵ�id�ᱻ�޸ġ���

dao.delete(T);//ɾ, һ���ϲ�ѯ��������ѯ��һ����¼֮��ɾ��, ������ܻᱨ��
dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).buildDelete().executeDeleteWithoutDetachingEntities();//�ظ�ɾ�����ᱨ��
dao.deleteByKey(long id);//ɾ
dao.deleteInTx();		//����ɾ������
dao.deleteByKeyInTx();	//ͨ����������ɾ������
dao.deleteAll();		//ɾ������

dao.update(T);//��

//ԭʼ��ѯ1
Query<T> query = dao.queryBuilder()
	.where(new WhereCondition.StringCondition("_ID IN (SELECT USER_ID FROM USER_MESSAGE WHERE _ID < 5)"))
	.build();//��ѯID < 5������USER
Query<User> query = dao.queryRawCreate(", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID", "admin");//?
List<T> ts = dao.queryRaw(ItemEntity.class, " where id = ?", id);//(String where, String... selectionArg)������ѯ

//queryBuilder ��ѯ
T t = dao.queryBuilder().where(ItemEntityDao.Properties.Id.eq(longId)).build().unique();//unique: ��ѯһ������
long count = dao.queryBuilder().where(ItemEntityDao.Properties.HistoryListId.eq(id)).count();//��ѯ����
List<T> ts = dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).list();//��������������ѯ
List<T> ts = dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).build().list();//��һ����
List<T> ts = dao.loadAll();//����ȫ��
List<T> ts = dao.queryBuilder().list();//����ȫ��

//debug��ѯ, �������������ԾͿ��Կ���log
QueryBuilder.LOG_SQL = true;
QueryBuilder.LOG_VALUES = true;


//���̲߳�ѯ(�����ǵ����ݿ�ǳ��Ӵ��ʱ��)
private void queryThread() {
  final Query query = sonDao.queryBuilder().build();
    new Thread(){
        @Override
        public void run() {
            List list = query.forCurrentThread().list();
            Log.d("queryThread", "run() called" + list);
        }
    }.start();
}


//���ݿ�����
����˼·��
1.������ʱ��TMP_,����ԭ�������ݿ⵽��ʱ���У�
2.ɾ��֮ǰ��ԭ��
3.�����±�
4.����ʱ���е����ݸ��Ƶ��±��У����TMP_��ɾ������


//���ݿ����
GreenDao����ͨ��SQLCipher��ֱ�Ӷ����ݿ��ļ����м���
1.�������
//SQLCipher: https://github.com/sqlcipher/sqlcipher
implementation 'net.zetetic:android-database-sqlcipher:3.5.6'

2.�޸�DaoSession�����ɷ�ʽ:
//����д��, ʹ��getEncryptedReadableDb()��getEncryptedWritableDb()��ȡ���ܵ����ݿ�
Database database = openHelper.getEncryptedWritableDb("aserbao"); //���ݿ��������Ϊ��aserbao"
daoMaster = new DaoMaster(database);


//����:
Please either mark it with @Keep annotation instead of @Generated to keep it untouched,
or use @Generated (without hash) to allow to replace it.
ͨ�������ֽ���취��
1.�����Ļ�ԭΪ @Generated.���ɵĴ��룬��Ҳ������ȫɾ�����ĵĹ������ͷ��������ǽ�����һ�ι������������ɡ�
2.�� @Keep ע�����@Generated ע�⡣�⽫����greenDAO��Զ��Ҫ������ע��Ĵ��롣���ĸ��Ŀ��ܻ��ƻ�ʵ���greenDAO���ಿ��֮�����Լ��
  ���⣬greenDAO��δ���汾���ܻ������ɵķ������в�ͬ�Ĵ��롣����,С��!���ʵ��ĵط����е�Ԫ�����Ա����鷳��һ�������⡣


