package app.net.tongcheng.util;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Random;

import app.net.tongcheng.R;
import app.net.tongcheng.model.FriendsBean;

/**
 * Created by 76594 on 2016/6/7.
 */
public class FriendBeanUtils {

    private static Random mRandom = new Random();

    public static void pinYin4Remark(FriendsBean mFriendsBean) {
        StringBuilder SearchStringBuilder = new StringBuilder();
        SearchStringBuilder.append(mFriendsBean.getPhone() + ",");
        SearchStringBuilder.append(mFriendsBean.getRemark() + ",");
        if (TextUtils.isEmpty(mFriendsBean.getRemark())) return;
        char[] remarks = mFriendsBean.getRemark().toCharArray();
        StringBuilder SearchStringBuilder_Q = new StringBuilder();
        StringBuilder SearchStringBuilder_S = new StringBuilder();
        if (String.valueOf(remarks[0]).matches("[a-zA-Z\\u4e00-\\u9fa5]")) {
            mFriendsBean.setFY(Pinyin.toPinyin(remarks[0]).substring(0, 1));
        } else {
            mFriendsBean.setFY("~");
        }
        for (char c : remarks) {
            String resultp = Pinyin.toPinyin(c);
            SearchStringBuilder_Q.append(resultp);
            SearchStringBuilder_S.append(resultp.substring(0, 1));
        }
        SearchStringBuilder.append(SearchStringBuilder_Q.toString() + ",");
        SearchStringBuilder.append(SearchStringBuilder_S.toString() + ",");
        mFriendsBean.setSearchString(SearchStringBuilder.toString());
    }

    public static void pinYin4Name(FriendsBean mFriendsBean) {
        StringBuilder SearchStringBuilder = new StringBuilder();
        SearchStringBuilder.append(mFriendsBean.getSearchString());
        char[] names = mFriendsBean.getName().toCharArray();
        StringBuilder SearchStringBuilder_Q = new StringBuilder();
        StringBuilder SearchStringBuilder_S = new StringBuilder();
        for (char c : names) {
            String resultp = Pinyin.toPinyin(c);
            SearchStringBuilder_Q.append(resultp);
            SearchStringBuilder_S.append(resultp.substring(0, 1));
        }
        SearchStringBuilder.append(SearchStringBuilder_Q.toString() + ",");
        SearchStringBuilder.append(SearchStringBuilder_S.toString() + ",");
        mFriendsBean.setSearchString(SearchStringBuilder.toString());
    }

    public static int getImageID() {
        int id = R.drawable.content1;
        switch (mRandom.nextInt(5)) {
            case 0:
                id = R.drawable.content1;
                break;
            case 1:
                id = R.drawable.content2;
                break;
            case 2:
                id = R.drawable.content3;
                break;
            case 3:
                id = R.drawable.content4;
                break;
            case 4:
                id = R.drawable.content5;
                break;
        }
        return id;
    }
}
