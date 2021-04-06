https://github.com/objectbox/objectbox-java
https://objectbox.io/
ObjectBox is a superfast object-oriented database with strong relation support. ObjectBox is embedded into your Android, Linux, macOS, or Windows app.

项目Gradle中:
//https://github.com/objectbox/objectbox-java
classpath "io.objectbox:objectbox-gradle-plugin:2.3.4"

模块Gradle中:
apply plugin: 'io.objectbox'



使用:
MyApplication中初始化:

//数据库
public BoxStore boxStore;
//MyObjectBox: 运行起来后, 会在build/generated/ap_generated_source/debug/out/com.yys.land/bean 目录下生成.
boxStore = MyObjectBox.builder().androidContext(this).build();

Activity中使用:
Box<GatherBean> gatherBeanBox = MyApplication.instance.boxStore.boxFor(GatherBean.class);
GatherBean bean = new GatherBean();
bean.setId(gatherBean.getId());
bean.setCoordinate(coordinate);
bean.setArea(String.valueOf(areaResult));
bean.setLength(String.valueOf(lengthResult));
bean.setTime(df.format(new Date()));
bean.setTitle(title);
gatherBeanBox.put(bean);
