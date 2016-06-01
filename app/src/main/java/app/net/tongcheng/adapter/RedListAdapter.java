package app.net.tongcheng.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.net.tongcheng.R;
import app.net.tongcheng.model.GiftsBean;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.MyRecyclerViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/24 16:07
 */
public class RedListAdapter extends MyBaseRecyclerViewAdapter<GiftsBean> {
    private SimpleDateFormat sdformat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdformat_2 = new SimpleDateFormat("MM月dd日-HH:mm");
    private SimpleDateFormat sdformat_3 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdformat_4 = new SimpleDateFormat("MM月dd日");
    private SimpleDateFormat sdformat_5 = new SimpleDateFormat("yyyyMMdd");
    private int datestr_today;

    public RedListAdapter(Context mContext, List<GiftsBean> mDatas) {
        super(mContext, mDatas, R.layout.red_listviewadapter);
        datestr_today = Integer.parseInt(sdformat_5.format(new Date()));
    }

    @Override
    public void onItemClick(View view, GiftsBean itemdata, List<GiftsBean> list, int position) {

    }

    @Override
    public void onCreateItemView(MyRecyclerViewHolder holder, GiftsBean itemdata, List<GiftsBean> list, int position) {
        if (itemdata != null) {
            try {
                TextView red_type_text = holder.getView(R.id.red_type_text);
                TextView red_creatime_text = holder.getView(R.id.red_creatime_text);
                TextView red_has_open_text = holder.getView(R.id.red_has_open_text);
                TextView red_moneys = holder.getView(R.id.red_moneys);
                ImageView red_has_open_image = holder.getView(R.id.red_has_open_image);
                red_moneys.setText(itemdata.getMoney() / (double) 100 + "元");
                if (itemdata.getDirect().equals("sended")) {

                    red_type_text.setText("发出: " + sdformat_2.format(sdformat.parse(itemdata.getCreate_time().trim())));
                    String status = itemdata.getStatus();
                    if ("has_sended".equals(status)) {
                        red_has_open_text.setText("领取中...");
                    } else if ("has_packed".equals(status)) {
                        red_has_open_text.setText("已包好");
                    } else if ("has_ended".equals(status)) {
                        if (itemdata.getHas_open() == itemdata.getSplitsnumber()) {
                            red_has_open_text.setText("已领完");
                        } else {
                            red_has_open_text.setText("已过期");
                        }
                    } else {
                        red_has_open_text.setText("");
                    }
                    red_has_open_image.setImageResource(R.drawable.red_image_frend);
                    double money_temp = itemdata.getMoney() / (double) 100;
                    double returned_money_d = itemdata.getReturned_money() / (double) 100;
                    double received_money_d = itemdata.getReceived_money() / (double) 100;
                    red_creatime_text.setText("已领取 "
                            + itemdata.getHas_open() + "/"
                            + itemdata.getSplitsnumber() + "个,共"
                            + received_money_d + "元/" + money_temp + "元");
                    if (returned_money_d != 0) {
                        red_has_open_text.setText("已结束  已退回"
                                + returned_money_d + "元");
                    }
                } else {

                    // 设置发红包方
                    if (!TextUtils.isEmpty(itemdata.getFromnickname())) {
                        red_type_text.setText("红包: " + itemdata.getFromnickname().trim() + "的"
                                + itemdata.getName());
                    } else {
                        if (!TextUtils.isEmpty(itemdata.getFrom())) {
                            if ("aixin_money".equals(itemdata.getFrom().trim()) || "system".equals(itemdata.getFrom().trim())) {
                                red_type_text.setText("红包: " + itemdata.getName());
                            } else {
                                red_type_text.setText("红包: "
                                        + itemdata.getFrom().trim() + "的"
                                        + itemdata.getName());
                            }
                        }
                    }

                    // 设置图片
                    if (itemdata.getHas_open() == 1) {
                        if ("aixin_money".equals(itemdata.getType()) || "system".equals(itemdata.getFrom().trim())) {
                            if ("invite_recharge_return".equals(itemdata.getSub_type())) {
                                red_has_open_image
                                        .setImageResource(R.drawable.red_image_fenxiang);
                            } else {
                                red_has_open_image
                                        .setImageResource(R.drawable.red_buzhu_image);
                            }
                        } else {
                            red_has_open_image
                                    .setImageResource(R.drawable.red_image_frend);
                        }
                    } else {
                        red_has_open_image
                                .setImageResource(R.drawable.red_cai_image);
                        red_moneys.setText("");
                    }

                    // 日期
                    String creatime = itemdata.getCreate_time();
                    if (creatime != null && creatime.trim().length() == 19) {
                        Date date = sdformat.parse(creatime.trim());
                        red_creatime_text.setText("收到: " + sdformat_2.format(date));
                    }

                    // 设置是否已拆动作
                    if (itemdata.getHas_open() == 0) {
                        // (如果是未拆红包)定义未拆红包是否过期
                        boolean pasedata = false;// true为过期
                        if (itemdata.getHas_open() == 0) {
                            String exp_time = itemdata.getExp_time();
                            if (exp_time != null && exp_time.trim().length() == 10) {
                                // 过期日期
                                int datestr = Integer.parseInt(itemdata.getExp_time()
                                        .trim().replaceAll("-", "")
                                        .replaceAll(" ", "").replaceAll(":", ""));
                                pasedata = datestr_today > datestr;
                            }
                        }
                        if (pasedata) {
                            red_moneys.setText("已失效");
                        }
                        // 格式化日期
                        String exp_time = itemdata.getExp_time();
                        if (exp_time != null && exp_time.trim().length() == 10) {
                            Date date = sdformat_3.parse(exp_time.trim());
                            red_has_open_text.setText("未拆: " + sdformat_4.format(date) + " 前有效");
                        }
                    } else if (itemdata.getHas_open() == 1) {
                        // 格式化日期
                        String open_time = itemdata.getOpen_time();
                        if (open_time != null && open_time.length() == 19) {
                            Date date = sdformat.parse(open_time.trim());
                            red_has_open_text.setText("拆开: " + sdformat_2.format(date));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
