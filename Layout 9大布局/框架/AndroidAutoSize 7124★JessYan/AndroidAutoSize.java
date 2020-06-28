https://github.com/JessYanCoding/AndroidAutoSize
https://github.com/JessYanCoding/AndroidAutoSize/blob/master/README-zh.md

����ͷ����Ļ���䷽���ռ��棬һ�����ͳɱ��� Android ��Ļ���䷽��

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

//��ĳ�� Activity �����ͼ�ߴ����� AndroidManifest ����д��ȫ�����ͼ�ߴ粻ͬʱ������ʵ�� CustomAdapt �ӿ���չ�������
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

//��ĳ�� Activity ��������䣬��ʵ�� CancelAdapt �ӿ�
public class CancelAdaptActivity extends AppCompatActivity implements CancelAdapt {
}

//Fragment
//���ȿ���֧�� Fragment �Զ�������Ĺ���
AutoSizeConfig.getInstance().setCustomFragment(true);

//��ĳ�� Fragment �����ͼ�ߴ����� AndroidManifest ����д��ȫ�����ͼ�ߴ粻ͬʱ������ʵ�� CustomAdapt �ӿ���չ�������
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

//��ĳ�� Fragment ��������䣬��ʵ�� CancelAdapt �ӿ�
public class CancelAdaptFragment extends Fragment implements CancelAdapt {
}

//Subunits (�����濴 demo-subunits����������ϸ����)
//������ pt��in��mm ���������ŵ�λ�У�ѡ��һ����Ϊ����λ������λ�����ڹ���޸� DisplayMetrics#density ����ɵĶ�������ʹ�� dp ���ֵ�ϵͳ�ؼ���������ؼ��Ĳ���Ӱ�죬ʹ�ø���λ���ֱ����д���ͼ�ϵ����سߴ磬����Ҫ�ٽ�����ת��Ϊ dp
AutoSizeConfig.getInstance().getUnitsManager()
        .setSupportDP(false)
        .setSupportSP(false)
        .setSupportSubunits(Subunits.MM);



