package app.net.tongcheng.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.AssetsDatabaseManager;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 16:14
 */
public class ChangeCity extends BaseActivity {

    private ViewHolder mViewHolder;
    private ListView mListView;
    private Map<String, String> maps = new HashMap<>();
    private String province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_province_layout);
        setTitle("选择城市");
        initView();
        setEventBus();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), null);
        mListView = mViewHolder.getView(R.id.mListView);
    }

    @Override
    public void loadData() {
        province = getIntent().getStringExtra("province");
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
                startActivity(new Intent(TCApplication.mContext, ChangeCounty.class).putExtra("parent", maps.get(item)).putExtra("province", province).putExtra("city", item));
            }
        });

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            if (event.getMsg().startsWith("provinceCity=")) {
                finish();
            }
        }
    }
}
