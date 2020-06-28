// (c)2016 Flipboard Inc, All Rights Reserved.

package com.rengwuxian.rxjavasamples.module.cache_6;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.rengwuxian.rxjavasamples.application.App;
import com.rengwuxian.rxjavasamples.network.Network;
import com.rengwuxian.rxjavasamples.R;
import com.rengwuxian.rxjavasamples.bean.Item;
import com.rengwuxian.rxjavasamples.util.GankBeautyResultToItemsMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * dependencies {
 *     compile ‘com.android.support:support-annotations:24.2.0’//如果有,不用添加依赖
 * }
 */
@Target://描述注解的使用范围, 可多个
2.ElementType.ANNOTATION_TYPE:	定义的这个注解A（名词）用于对注解B（名词）进行声明https://blog.csdn.net/u010002184/article/details/79865753
1.ElementType.CONSTRUCTOR:		用于描述构造器
2.ElementType.FIELD:			用于描述字段
3.ElementType.LOCAL_VARIABLE:	用于描述局部变量
4.ElementType.METHOD:			用于描述方法
5.ElementType.PACKAGE:			用于描述包
6.ElementType.PARAMETER:		用于描述参数
7.ElementType.TYPE:				用于描述类、接口(包括注解类型) 或enum声明

@Retention//定义该Annotation被保留的时间长短
1.SOURCE:在源文件中有效,表示注解的信息会被编译器抛弃，不会留在class文件中，注解的信息只会留在源文件中；
2.CLASS:在class文件中有效m,表示注解的信息被保留在class文件(字节码文件)中当程序编译时，但不会被虚拟机读取在运行的时候；
3.RUNTIME:在运行时有效,表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时;

@Documente//描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。Documented是一个标记注解，没有成员

@Inherited//是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类


public class Data {
	
	private int dataSource;
    private static final int DATA_SOURCE_MEMORY = 1;
    private static final int DATA_SOURCE_DISK = 2;
    private static final int DATA_SOURCE_NETWORK = 3;

	//@StringDef({"1", "2"})//也可以String
    @IntDef({DATA_SOURCE_MEMORY, DATA_SOURCE_DISK, DATA_SOURCE_NETWORK})//★★★int,long这里面的值不能重复, @StringDef({})里的值可以重复★★★
	@Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    @interface DataSource {
    }

	//示例使用
    private void setDataSource(@DataSource int dataSource) {
        this.dataSource = dataSource;
    }


    public String getDataSourceText() {
        int dataSourceTextRes;
        switch (dataSource) {
            case DATA_SOURCE_MEMORY:
                dataSourceTextRes = R.string.data_source_memory;
                break;
            case DATA_SOURCE_DISK:
                dataSourceTextRes = R.string.data_source_disk;
                break;
            case DATA_SOURCE_NETWORK:
                dataSourceTextRes = R.string.data_source_network;
                break;
            default:
                dataSourceTextRes = R.string.data_source_network;
        }
        return App.getInstance().getString(dataSourceTextRes);
    }
}

//---------------IntDef----------------
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE})//注释起作用的位置，类、接口、枚举...
public @interface IntDef {
    /** Defines the allowed constants for this element */
    long[] value() default {};	//如果没有default默认值, 这个就必须写!

    /** Defines whether the constants can be used as a flag, or just as an enum (the default) */
    boolean flag() default false;
}

//---------------StringDef----------------
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE})
public @interface StringDef {
    /** Defines the allowed constants for this element */
    String[] value() default {};
}

//---------------LongDef----------------
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE})
public @interface LongDef {
    /** Defines the allowed constants for this element */
    long[] value() default {};

    /** Defines whether the constants can be used as a flag, or just as an enum (the default) */
    boolean flag() default false;
}

