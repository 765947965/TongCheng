package app.net.tongcheng.util;

/**
 * Created by 76594 on 2016/5/29.
 */
public class ErrorInfoUtil {

    public static String getErrorMessage(int type) {
        String message = "";
        switch (type) {
            case 1:
                message = "未登录";
                break;
            case 4:
                message = "参数格式错误";
                break;
            case 5:
                message = "参数不能为空";
                break;
            case 6:
                message = "sign错误";
                break;
            case 7:
                message = "用户名或密码错误";
                break;
            case 9:
                message = "注册请求失败";
                break;
            case 10:
                message = "您已绑定了该银行卡，不能重复绑定!";
                break;
            case 12:
                message = "密码错误";
                break;
            case 13:
                message = "用户不存在";
                break;
            case 14:
                message = "查询余额服务器繁忙";
                break;
            case 15:
                message = "查询余额失败";
                break;
            case 16:
                message = "充值服务器繁忙";
                break;
            case 17:
                message = "被叫号码格式错误";
                break;
            case 18:
                message = "不能呼叫自己的号码";
                break;
            case 19:
                message = "余额不足";
                break;
            case 22:
                message = "邀请码不正确";
                break;
            case 30:
                message = "uid已经绑定了其他同类型帐号/手机号";
                break;
            case 31:
                message = "该账号已被其他用户绑定";
                break;
            case 32:
                message = "验证码错误";
                break;
            case 33:
                message = "验证码过期";
                break;
            case 34:
                message = "手机号码重复绑定";
                break;
            case 35:
                message = "发送给同一手机号码短信数量超过当日限制";
                break;
            case 37:
                message = "手机号码/UID格式不对";
                break;
            case 38:
                message = "暂不支持该地区电话号码注册、找回密码";
                break;
            case 39:
                message = "无同城好友";
                break;
            case 50:
                message = "红包id已经存在，重复添加";
                break;
            case 51:
                message = "红包id不存在，无法拆红包";
                break;
            case 52:
                message = "红包已经拆过了";
                break;
            case 53:
                message = "红包已过有效期";
                break;
            case 54:
                message = "今天收红包个数已超标，不能再加红包了。";
                break;
            case 55:
                message = "不支持这种类型红包";
                break;
            case 56:
                message = "红包类型错误，如给免口令红包生成口令、在免口令红包中找到了某个口令、";
                break;
            case 57:
                message = "红包口令已被占用、口令错误、";
                break;
            case 58:
                message = "摇一摇本次未摇中";
                break;
            case 59:
                message = "已领摇中过该红包，不允许同一个人多次领某个口令红包";
                break;
            case 60:
                message = "服务器没有保存用户的个人资料";
                break;
            case 61:
                message = "个人资料头像图片太大";
                break;
            case 62:
                message = "获取个人资料，当前客户端ver和服务器一样。";
                break;
            case 63:
                message = "获取最新开屏页，没有最新图片";
                break;
            case 64:
                message = "好友管理中：客户端ver与服务器ver相同，";
                break;
            case 70:
                message = "服务页配置：客户端ver与服务器ver相同";
                break;
            case 71:
                message = "红包已输入口令错误次数已经到达当天上限";
                break;
            case 45:
                message = "服务器异常";
                break;
        }
        return message;
    }
}
