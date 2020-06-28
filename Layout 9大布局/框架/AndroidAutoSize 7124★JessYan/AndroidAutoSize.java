https://github.com/JessYanCoding/AndroidAutoSize
https://github.com/JessYanCoding/AndroidAutoSize/blob/master/README-zh.md

今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案

1.implementation 'me.jessyan:autosize:1.2.1'

2.
<manifest>
    <application>            
        <meta-data
            android:name="design_width_in_dp"
            android:value="360"/>
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>           
     </application>           
</manifest>

//当某个 Activity 的设计图尺寸与在 AndroidManifest 中填写的全局设计图尺寸不同时，可以实现 CustomAdapt 接口扩展适配参数
public class CustomAdaptActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}

//当某个 Activity 想放弃适配，请实现 CancelAdapt 接口
public class CancelAdaptActivity extends AppCompatActivity implements CancelAdapt {
}

//Fragment
//首先开启支持 Fragment 自定义参数的功能
AutoSizeConfig.getInstance().setCustomFragment(true);

//当某个 Fragment 的设计图尺寸与在 AndroidManifest 中填写的全局设计图尺寸不同时，可以实现 CustomAdapt 接口扩展适配参数
public class CustomAdaptFragment extends Fragment implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}

//当某个 Fragment 想放弃适配，请实现 CancelAdapt 接口
public class CancelAdaptFragment extends Fragment implements CancelAdapt {
}

//Subunits (请认真看 demo-subunits，里面有详细介绍)
//可以在 pt、in、mm 这三个冷门单位中，选择一个作为副单位，副单位是用于规避修改 DisplayMetrics#density 所造成的对于其他使用 dp 布局的系统控件或三方库控件的不良影响，使用副单位后可直接填写设计图上的像素尺寸，不需要再将像素转化为 dp
AutoSizeConfig.getInstance().getUnitsManager()
        .setSupportDP(false)
        .setSupportSP(false)
        .setSupportSubunits(Subunits.MM);



