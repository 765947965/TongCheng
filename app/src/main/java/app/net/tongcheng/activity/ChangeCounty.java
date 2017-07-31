package app.net.tongcheng.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.AssetsDatabaseManager;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 16:14
 */
public class ChangeCounty extends BaseActivity {

    private ViewHolder mViewHolder;
    private ListView mListView;
    private String province, city;
    private Map<String, String> maps = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_province_layout);
        setTitle("选择省份");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), null);
        mListView = mViewHolder.getView(R.id.mListView);
    }

    @Override
    public void loadData() {
        province = getIntent().getStringExtra("province");
        city = getIntent().getStringExtra("city");
        AssetsDatabaseManager.initManager(this);
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("cityData.sqlite");
        Cursor cursor = db.query("city", new String[]{"name", "id"}, "parent=?", new String[]{getIntent().getStringExtra("parent")}, null, null, null);
        ArrayList<String> datas = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String id = cursor.getString(1);
            datas.add(name);
            maps.put(name, id);
        }
        cursor.close();
        mListView.setAdapter(new MyBaseAdapter<String>(mListView, TCApplication.mContext, datas, R.layout.pct_layout) {
            @Override
            protected void convert(ViewHolder holder, String item, List<String> list, int position) {
                holder.setText(R.id.tv_name, item);
            }

            @Override
            protected void MyonItemClick(AdapterView<?> parent, View view, String item, List<String> list, int position, long id) {
                sendEventBusMessage("provinceCity=" + province + ":" + city + ":" + item);
                finish();
            }
        });

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {

    }
}
