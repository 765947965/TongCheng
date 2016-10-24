package app.net.tongcheng.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.net.tongcheng.model.ContentModel;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/6 10:23
 */
public class ContentsUtil {

    public static List<ContentModel> getContacts(Context context) {
        List<ContentModel> datas = new ArrayList<>();
        try {
            //定义常量，节省重复引用的时间
            Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
            String ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            //临时变量
            long contactId;
            String displayName;
            //生成ContentResolver对象
            ContentResolver contentResolver = context.getContentResolver();
            // 获取手机联系人
            Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), new String[]{ID, DISPLAY_NAME}, null, null, null);//获取所有联系人ID
            HashMap<Long, ContentModel> friendList = new HashMap<>();
            // 无联系人直接返回
            if (cursor == null || !cursor.moveToFirst()) {//moveToFirst定位到第一行
                return null;
            }
            do {
                contactId = cursor.getLong(0);
                displayName = cursor.getString(1);
                friendList.put(contactId, new ContentModel(contactId, displayName, new ArrayList<String>()));
            } while (cursor.moveToNext());
            cursor.close();
            Cursor phoneCursor = contentResolver.query(CONTENT_URI, new String[]{CONTACT_ID, NUMBER}, null, null, null);//获取所有联系人号码
            if (phoneCursor == null || !phoneCursor.moveToFirst()) {
                return null;
            }
            do {
                ContentModel mContentModel = friendList.get(phoneCursor.getLong(0));
                if (mContentModel == null) {
                    continue;
                }
                mContentModel.getMobile().add(phoneCursor.getString(1));
            } while (phoneCursor.moveToNext());
            phoneCursor.close();
            datas.addAll(friendList.values());
        } catch (Exception e) {
            ToastUtil.showToast("获取通讯录失败!");
        }
        return datas;
    }

    public static String getPhoneNum(String phone) {
        if (phone.length() == 11) {
            return phone;
        }
        Pattern pattern = Pattern.compile("1[34578]\\d{9}]");
        Matcher matcher = pattern.matcher(phone.replaceAll(" ", "").replaceAll("-", ""));
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }
    }
}
