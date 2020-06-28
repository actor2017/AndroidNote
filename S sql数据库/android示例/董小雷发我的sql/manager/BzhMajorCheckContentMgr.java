package com.nkay.swyt.database.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhMajorCheckContent;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: BzhMajorCheckContentMgr
 * Author: zhangwenping
 * Date: 2017/8/28
 * Email: httputils@qq.com
 */
public class BzhMajorCheckContentMgr {
    private Context context;

    public BzhMajorCheckContentMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 根据SELF_CHECKCONTENTS_ID查询专业检查内容表
     */
    public List<BzhMajorCheckContent> selectBzhMajorContentByContentId(String contentId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT c.SELF_CHECKCONTENTS_ID,c.COLLIERY_TYPE_ID,c.YIXUANZEPROJECTS_ID,c.SELF_CHECK_PROJECTNAME,c.SELF_CHECK_PROJECTCONTENTS, ");
        sb.append(" c.SELF_CHECK_PROJECTCONTENTS2,c.SELF_CHECK_BASICREQUIREMENTS,c.SELF_CHECK_STANDARTSCORE,c.SELF_CHECK_METHODOFCOMMENT,c.SELF_CHECK_SORT,c.SELF_CHECK_SCORE_GROUP,c.SELF_CHECK_METHOD_GROUP,c.SELF_CONTENT_SORT from BZH_MAJOR_CHECK_CONTENT c");
        sb.append(" where c.DEL_FLG = 0 and c.YIXUANZEPROJECTS_ID = '"+contentId+"' ");
        sb.append(" ORDER BY c.SELF_CHECK_SORT ");

        Cursor cursor = null;
        ArrayList<BzhMajorCheckContent> bzhContents = new ArrayList<BzhMajorCheckContent>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhMajorCheckContent bzhContent = new BzhMajorCheckContent();
                bzhContent.setSelfCheckcontentsId(cursor.getString(0));
                bzhContent.setCollieryTypeId(cursor.getString(1));
                bzhContent.setYixuanzeprojectsId(cursor.getString(2));
                bzhContent.setSelfCheckProjectname(cursor.getString(3));
                bzhContent.setSelfCheckProjectcontents(cursor.getString(4));
                bzhContent.setSelfCheckProjectcontents2(cursor.getString(5));
                bzhContent.setSelfCheckBasicrequirements(cursor.getString(6));
                bzhContent.setSelfCheckStandartscore(cursor.getString(7));
                bzhContent.setSelfCheckMethodofcomment(cursor.getString(8));
                bzhContent.setSelfCheckSort(cursor.getString(9));
                bzhContent.setSelfCheckScoreGroup(cursor.getString(10));
                bzhContent.setSelfCheckMethodGroup(cursor.getString(11));
                bzhContent.setSelfContentSort(cursor.getString(12));
                bzhContents.add(bzhContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return bzhContents;
    }

}
