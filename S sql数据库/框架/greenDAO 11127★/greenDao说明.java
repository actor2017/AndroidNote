https://github.com/greenrobot/greenDAO
http://greenrobot.org/greendao/
greenDAO是一个针对Android的轻快速ORM解决方案，它将对象映射到SQLite数据库。

1.root build.gradle
buildscript {
    repositories {
        jcenter()
        mavenCentral() // add repository
    }
    dependencies {
		//greendao-gradle-plugin最新版本: https://search.maven.org/search?q=g:org.greenrobot%20AND%20a:greendao-gradle-plugin
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin
    }
}

2.app/build.gradle:
//greenDAO Gradle插件: 在构建项目时，它会生成DaoMaster、DaoSession和DAOs等类
apply plugin: 'org.greenrobot.greendao' // apply plugin
android {
	...
	greendao{
		schemaVersion 1                 //指定数据库schema版本号，迁移等操作会用到
		daoPackage 'com.greendao.gen'   //dao的包名，包名默认是entity所在的包
		targetGenDir 'src/main/java'    //生成数据库文件的目录
		//targetGenDirTest false		//生成单元测试
        //generateTests 'src/androidTest/java'	//生成的单元测试的基本目录
	}
}
dependencies {
	//greendao最新版本: https://search.maven.org/search?q=g:org.greenrobot%20AND%20a:greendao
    implementation 'org.greenrobot:greendao:3.2.2' // add library
}


3.文档: http://greenrobot.org/greendao/documentation/
Getting Started入门页面: http://greenrobot.org/greendao/documentation/how-to-get-started/


4.混淆: If your project uses R8 or ProGuard add the following rules:
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

5.几个核心类说明:
5.1.DaoMaster: Dao中的管理者。它保存了sqlitedatebase对象以及操作DAO classes（注意：不是对象）。
  其提供了一些创建和删除table的静态方法，其内部类OpenHelper和DevOpenHelper实现了SQLiteOpenHelper，
  并创建数据库的框架。
5.2.DaoSession: 会话层。操作具体的DAO对象（注意：是对象），比如各种getter方法
5.3.Daos: 实际生成的某某DAO类，通常对应具体的java类，比如NoteDao等。其有更多的权限和方法来操作数据库元素
5.4.Entities: 持久的实体对象。通常代表了一个数据库row的标准java properties


//一篇技术好文之Android数据库 GreenDao的使用完全解析 - 简书.html
https://www.jianshu.com/p/53083f782ea2
6.使用
  6.1.定义一个实体类
  //annotation
  @Convert(converter = MapConverter.class, columnType = String.class) //将添加了这个注解的自定义字段, 转换成数据库能够存储的数据类型
  private Map<String, Object> params;
  
  @Entity			//这个实体类会在数据库中生成对应的表
						nameInDb: 					//声明该表的表名，默认取类名
						indexes: {}					//索引,类型:Index[]
													//1.用于建立索引, 应用场景: 当表有多个主键时, 来标志一条数据的唯一性, 配合unique.
													//2.标记如果DAO应该创建数据库表(默认为true)，如果您有多个实体映射到一个表，
													//或者表的创建是在greenDAO之外进行的，那么将其设置为false
													//例:indexes = {@Index(value = "name DESC", unique = true)}

						createInDb: true			//是否创建表，默认为true, false时不创建??什么作用
						schema: "default"			//当有多个schema(构架), 用这个属性告诉数据库当前entity属于哪个schema(构架)

						active: false				//标志某个entity是否是active(活跃状态)的，active的实体类有删改的方法,
													//为true的时候会在entity里面自动生成下面的代码:
													//删除
													@Generated(hash = 128553479)
												   public void delete() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.delete(this);
												   }
												   //刷新
												   @Generated(hash = 1942392019)
												   public void refresh() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.refresh(this);
												   }
												   //更新
												   @Generated(hash = 713229351)
												   public void update() {
													   if (myDao == null) {
														   throw new DaoException("Entity is detached from DAO context");
													   }
													   myDao.update(this);
												   }
													
						generateConstructors: true	//是否在'本实体内'生成含所有参数的构造函数，默认为true
						generateGettersSetters: true//是否在'本实体内'生成getter/setter，默认为true
						protobuf: void.class
						
  @Generated		//编译项目后在实体中自动生成的构造方法
						hash						//int
						
  @Id				//该字段是id，注意该字段的数据类型为包装类型★★★Long/long★★★
						autoincrement = false		//自增长, ★★★如果自增长=true, 必须是Long★★★
						
  @Index			//为属性在对应的数据库列创建数据库索引
						value
						name: ""		//如果不喜欢为索引生成的默认名称，可以在这里指定自己的名称
						unique: false	//为索引添加唯一约束，强制所有值都是唯一的(true时添加相同数据抛异常)
				//示例:
				//有时候我们的主键不一定是long或者Long型的可能是string之类的，
				//这个时候我们就可以定义索引属性并且注明独一无二:
				@Index(name = "keyword", unique = true)
				private String key;
						
  @JoinEntity		//多对多?
						entity			//Class<?>, 
						sourceProperty	//string
						targetProperty	//string
  @JoinProperty
  @Keep				//https://www.jianshu.com/p/1044c9cdcc97
					//代替@Generated 注解。这将告诉greenDAO永远不要触摸带注解的代码。
					//您的更改可能会破坏实体和greenDAO其余部分之间的契约。
					//另外，greenDAO的未来版本可能会在生成的方法中有不同的代码。
					//所以,小心!在适当的地方进行单元测试以避免麻烦是一个好主意
					
  @NotNull			//该字段不可以为空, 通常标记基本数据类型(long, int...)不为空, 而不是可为空的类型

  @OrderBy			//排序

  @Property 		//该属性将作为表的一个字段
						nameInDb="URL"//声明某变量在表中的列名, 如果不写, 默认大写驼峰, 示例: customName -> CUSTOM_NAME

  @ToMany			//一对多(一个学校对应有多个学生)
						referencedJoinProperty="studentId"//这个studentId是'学校'中对应的studentId
						joinProperties

  @ToOne			//一对一(一个学生对应一个学校)
						joinProperty="外键id名称"

  @Transient		//声明变量不被映射到数据表中

  @Unique			//该字段唯一
  
  //annotation/apihint
  @Beta
  @Experimental
  @Internal
  
  //converter
  @PropertyConverter


/**
 * json和map参数转换
 * 1.必须是public
 * 2.如果是内部类, 必须是static
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

6.2. 1对多, 多对1 示例(学生和学校):
@Entity
class Student{
    private long fk_schoolId;//外键
    @ToOne(joinProperty = "fk_schoolId")
    private School school;
}
@Entity
class School{
    @ToMany(referencedJoinProperty = "fk_schoolId")
    private List<Student> students;
}

6.3. 多对多 示例(学生与课程):
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

7.编译项目: Build -> Make Project(ctrl + F9)
  实体类中会自动生成get、set方法并且会在com.greendao.gen目录下生成三个文件: DaoMaster, DaoSession, ItemEntityDao(自己写的实体+Dao)

8.MyApplication中初始化数据库:
public GreenDaoUtils greenDaoUtils;
greenDaoUtils = GreenDaoUtils.getInstance(this, isDebugMode);


8.2.Java中:
WhereCondition 方法:
eq 					//相等
notEq 				//不相等
like				//模糊查询, string要用夹在%key%中间, 例: Properties.FirstName.like("%doris%"), 查询FristName包含doris的人
gt >				//大于
lt <				//小于
ge >=				//大于等于
le <=				//小于等于
between()			//...和...之间
in（..., ..., ...)	//在给出的value的范围内的符合项
notIn()
isNull		//为空
isNotNull	//不为空


QueryBuilder 方法:
or					//或者
whereOr				//↑
and(>=2个参数)		//并且
orderAsc	//升序(正序, 小->大)	List list = dao.queryBuilder().orderAsc(SonDao.Properties.Age).list();
orderDesc	//降序(倒序, 大->小)
limit(int)	//限制查询返回的条数
offset(int)	//设置数据返回偏向后移值, 结合limit使用, 例: limit(3), offset(2): 结果[1,2,3] => [3,4,5]
			//queryBuilder().where(Properties.Year.gt(2001)).offset(3).limit(10);
			//年份大于2001年的第3个开始的十个人
join		//多表查询


list() 不是懒加载，正常查询，查询结果立马加载到内存
listLazy() 实体按需加载到内存中。 一旦列表中的元素第一次被访问，它将被加载并缓存以供将来使用。 必须关闭
listLazyUncached() 一个“虚拟”实体列表：对列表元素的任何访问都会导致从数据库加载其数据。 必须关闭
listIterator() 让我们通过按需加载数据（懒惰地）来遍历结果。 数据没有被缓存。 必须关闭





//获取dao
private ItemEntityDao dao = MyApplication.instance.greenDaoUtils.getDaoSession().getItemEntityDao();

dao.insert(T);//增
dao.insertInTx(T...);//批量插入
dao.insertInTx(Iterable<T> entities);
dao.insertOrReplace(T);//存在则替换，不存在则插入, ★★★这条数据的id会被修改★★★

dao.delete(T);//删, 一般结合查询方法，查询出一条记录之后删除, 否则可能会报错
dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).buildDelete().executeDeleteWithoutDetachingEntities();//重复删除不会报错
dao.deleteByKey(long id);//删
dao.deleteInTx();		//批量删除数据
dao.deleteByKeyInTx();	//通过主键批量删除数据
dao.deleteAll();		//删除所有

dao.update(T);//改

//原始查询1
Query<T> query = dao.queryBuilder()
	.where(new WhereCondition.StringCondition("_ID IN (SELECT USER_ID FROM USER_MESSAGE WHERE _ID < 5)"))
	.build();//查询ID < 5的所有USER
Query<User> query = dao.queryRawCreate(", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID", "admin");//?
List<T> ts = dao.queryRaw(ItemEntity.class, " where id = ?", id);//(String where, String... selectionArg)条件查询

//queryBuilder 查询
T t = dao.queryBuilder().where(ItemEntityDao.Properties.Id.eq(longId)).build().unique();//unique: 查询一条数据
long count = dao.queryBuilder().where(ItemEntityDao.Properties.HistoryListId.eq(id)).count();//查询总数
List<T> ts = dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).list();//根据其它变量查询
List<T> ts = dao.queryBuilder().where(ItemEntityDao.Properties.Name.eq("name")).build().list();//↑一样的
List<T> ts = dao.loadAll();//查找全部
List<T> ts = dao.queryBuilder().list();//查找全部

//debug查询, 设置这两个属性就可以看到log
QueryBuilder.LOG_SQL = true;
QueryBuilder.LOG_VALUES = true;


//多线程查询(当我们的数据库非常庞大的时候)
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


//数据库升级
升级思路：
1.创建临时表TMP_,复制原来的数据库到临时表中；
2.删除之前的原表；
3.创建新表；
4.将临时表中的数据复制到新表中，最后将TMP_表删除掉；


//数据库加密
GreenDao可以通过SQLCipher来直接对数据库文件进行加密
1.添加依赖
//SQLCipher: https://github.com/sqlcipher/sqlcipher
implementation 'net.zetetic:android-database-sqlcipher:3.5.6'

2.修改DaoSession的生成方式:
//加密写法, 使用getEncryptedReadableDb()和getEncryptedWritableDb()获取加密的数据库
Database database = openHelper.getEncryptedWritableDb("aserbao"); //数据库加密密码为“aserbao"
daoMaster = new DaoMaster(database);


//报错:
Please either mark it with @Keep annotation instead of @Generated to keep it untouched,
or use @Generated (without hash) to allow to replace it.
通常有两种解决办法：
1.将更改还原为 @Generated.生成的代码，你也可以完全删除更改的构造器和方法。它们将在下一次构建中重新生成。
2.用 @Keep 注解代替@Generated 注解。这将告诉greenDAO永远不要触摸带注解的代码。您的更改可能会破坏实体和greenDAO其余部分之间的契约。
  另外，greenDAO的未来版本可能会在生成的方法中有不同的代码。所以,小心!在适当的地方进行单元测试以避免麻烦是一个好主意。


