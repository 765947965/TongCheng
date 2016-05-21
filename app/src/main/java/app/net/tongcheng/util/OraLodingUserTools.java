package app.net.tongcheng.util;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
import java.util.TreeSet;

import app.net.tongcheng.model.OraLodingUser;

public class OraLodingUserTools {

    public static ArrayList<OraLodingUser> getolus(Context context) {
        String json_str = OperationUtils.getString(OperationUtils.LODINGUSERJSON);
        if (json_str == null || "".equals(json_str)) {
            return null;
        }
        try {
            JSONObject json = new JSONObject(json_str);
            JSONArray jsarray = json.getJSONArray("users");
            if (jsarray == null || jsarray.length() == 0) {
                return null;
            }
            TreeSet<OraLodingUser> tmap = new TreeSet<OraLodingUser>();
            for (int num = 0; num < jsarray.length(); num++) {
                String phonenum = jsarray.getJSONObject(num).optString(
                        "phonenum");
                long time = jsarray.getJSONObject(num).optLong("time");
                if (!"".equals(phonenum) && time > 0) {
                    OraLodingUser olu = new OraLodingUser(phonenum, time);
                    tmap.add(olu);
                }
            }
            ArrayList<OraLodingUser> tmaplist = new ArrayList<OraLodingUser>();
            tmaplist.addAll(tmap);
            tmap.clear();
            tmap = null;
            return tmaplist;
        } catch (Exception e) {
            return null;
        }

    }

    public static void addolus(Context context, OraLodingUser olu) {
        try {
            ArrayList<OraLodingUser> tmaplist = getolus(context);
            if (tmaplist == null) {
                tmaplist = new ArrayList<OraLodingUser>();
            }
            boolean tenpf = true;
            JSONArray jsay = new JSONArray();
            for (int num = 0; num < tmaplist.size(); num++) {
                JSONObject json = new JSONObject();
                if (tmaplist.get(num).getPhonenum().equals(olu.getPhonenum())) {
                    json.put("phonenum", tmaplist.get(num).getPhonenum());
                    json.put("time", olu.getTime());
                    jsay.put(json);
                    tenpf = false;
                } else {
                    json.put("phonenum", tmaplist.get(num).getPhonenum());
                    json.put("time", tmaplist.get(num).getTime());
                    jsay.put(json);
                }
            }
            if (tenpf) {
                JSONObject json = new JSONObject();
                json.put("phonenum", olu.getPhonenum());
                json.put("time", olu.getTime());
                jsay.put(json);
            }
            JSONObject json = new JSONObject();
            json.put("users", jsay);
            OperationUtils.PutString(OperationUtils.LODINGUSERJSON, json.toString());
            tmaplist.clear();
            tmaplist = null;
        } catch (Exception e) {
        }
    }
}
