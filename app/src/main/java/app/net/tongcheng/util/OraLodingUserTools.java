package app.net.tongcheng.util;


import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.net.tongcheng.model.LodingUserModel;
import app.net.tongcheng.model.OraLodingUser;

public class OraLodingUserTools {

    public static List<OraLodingUser> getolus(Context context) {
        String json_str = UserDateUtils.getString(UserDateUtils.LODINGUSERJSON);
        if (json_str == null || "".equals(json_str)) {
            return null;
        }
        try {
            return JSON.parseObject(json_str, LodingUserModel.class).getDatas();
        } catch (Exception e) {
            return null;
        }
    }

    public static void addolus(Context context, OraLodingUser olu) {
        try {
            List<OraLodingUser> tmaplist = getolus(context);
            if (tmaplist == null) {
                tmaplist = new ArrayList<>();
            }
            boolean isHas = false;
            for (OraLodingUser mOraLodingUser : tmaplist) {
                if (mOraLodingUser.getPhonenum().equals(olu.getPhonenum())) {
                    mOraLodingUser.setTime(olu.getTime());
                    isHas = true;
                    break;
                }
            }
            if(!isHas){
                tmaplist.add(olu);
            }
            Collections.sort(tmaplist);
            LodingUserModel mLodingUserModel = new LodingUserModel();
            mLodingUserModel.setDatas(tmaplist);
            UserDateUtils.PutString(UserDateUtils.LODINGUSERJSON, JSON.toJSONString(mLodingUserModel));
        } catch (Exception e) {
        }
    }
}
