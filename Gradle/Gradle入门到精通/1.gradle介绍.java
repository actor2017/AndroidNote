1.gradle介绍
项目管理工具:
2000年: Ant
2004: Maven
2012: Gradle(基于 Groovy 的特定领域语言(DSL)来声明项目设置, 抛弃了基于 XML 的各种繁琐配置).面向Java应用为主.
      当前支持的语言: Java, Groovy, Scale, 计划未来将支持更多语言.

2.课程介绍
3.gradle的安装
配置环境变量: (系统变量 -> 新建)
GRADLE_HOME:
C:\Users\actor\.gradle\wrapper\dists\gradle-6.0-all\cra2s42cvogxluqqpvbc5e9xd\gradle-6.0

Path -> 新建 -> %GRADLE_HOME%\bin

验证grandle是否配置成功: cmd -> gradle -v

4.gradle介绍
idea 配置 gradle:
主界面 -> Settings -> Build,Execution,Deployment -> Gradle -> OK(什么都不做...)

idea 创建idea项目:
New Project -> Gradle -> Java ->
GroupId: com.google
Artifactld: 模块名   -> Next
Use auto-import(自动导入)
Use local gradle distribution(使用本地gradle)

5.gradle项目目录结构介绍
src/main/java      //正式代码
src/main/resources //正式配置文件
src/test/java      //测试代码
src/test/resources //测试配置文件
src/main/webapp    //防止页面元素: js, css, img, html...


6.grovvy简单语法
println("hello groovy");    //sout快捷键
println("hello groovy")     //可省略 ';'
println "hello groovy"      //可省略 '()'

def i = 1
def s = "hello"
def list = ['a', 'b']
def list2 = ["a", "b"]      //和↑一致
def map = ["k1": "v1", "k2": "v2"]
list << 'c'                 //往list中添加
list.add("d")
println list.get(3)

map.k3a = 'v3'
map.put("k4", "v4")         //和↑一致
println map.get('k3a')
println map.k4

7.grovvy中的闭包
  闭包: 就是一段代码块. 在gradle中, 我们主要是把闭包当参数来使用.
def b1 = {
    println("hello b1")
}
def b2 = {
    abc -> println("hello ${abc}")
}

//定义方法, 参数是'闭包'类型
def method1(Closure closure) {
    closure()
}

def method2(Closure closure) {
    closure("b2")
}
//调用方法
method1(b1)
method2(b2)


8.gradle配置文件的介绍
//build.gradle
//Settings -> Build -> Gradle -> Service director path(jar包下载位置, 默认:C:/Users/actor/.gradle)

9.让gradle使用本地maven仓库
//1. 配置环境变量"GRADLE_USER_HOME": D:\repository
//Settings -> Build -> Gradle -> Service director path 变成了上方配置的目录

//2.repositories {
//      mavenLocal()    //第一行添加, 先在本地maven仓库找, 如果不配置, 只在mavenCentral()中下载?
//  }

10.gradle介绍
11.gradle开发web工程
//新建文件: src -> main -> webapp -> WEB-INF -> web.xml
//在build.gradle中添加: apply plugin: 'war'

12.gradle工程拆分与聚合
allprojects {
    //父工程的配置, 子工程能用, javaEE项目中可这样配置
    group ..., version ..., apply ..., sourceCompatibility = 1.8, repositories {}, dependencies {}
}
















