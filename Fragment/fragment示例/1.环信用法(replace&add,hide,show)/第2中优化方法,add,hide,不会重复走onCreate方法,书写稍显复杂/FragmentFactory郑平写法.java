package cn.itcast.huanxin12.view.impl.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by zhengping on 2017/3/18,16:30.
 */

public class FragmentFactory {

    private static HashMap<Integer, Fragment> sSavedFragment = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = sSavedFragment.get(position);
        if(fragment == null) {
            switch (position) {
                case 0:
                    fragment = new ConversationFragment();
                    break;
                case 1:
                    fragment = new ContactsFragment();
                    break;
                case 2:
                    fragment = new PluginFragment();
                    break;
            }
            sSavedFragment.put(position, fragment);
        }
        return fragment;
    }
}
