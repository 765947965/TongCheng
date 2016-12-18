-dontshrink
#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*,Exceptions,InnerClasses

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#如果引用了v4或者v7包
-dontwarn android.support.**

############混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    public static final android.os.Parcelable$Creator *;
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-renamesourcefileattribute SourceFile

# webview + js
-keepattributes *JavascriptInterface*,SourceFile,LineNumberTable
# keep 使用 webview 的类
-keepclassmembers class  com.veidy.activity.WebViewActivity {
   public *;
}
# keep 使用 webview 的类的所有的内部类
-keepclassmembers  class  com.veidy.activity.WebViewActivity$*{
    *;
}

# 保护泛型
-keepattributes Signature

#忽略警告
-ignorewarning

#####################记录生成的日志数据,gradle build时在本项目根目录输出################

##apk 包内所有 class 的内部结构
#-dump class_files.txt
##未混淆的类和成员
#-printseeds seeds.txt
##列出从 apk 中删除的代码
#-printusage unused.txt
##混淆前后的映射
#-printmapping mapping.txt

#####################记录生成的日志数据，gradle build时 在本项目根目录输出-end################


################混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#-libraryjars libs/umeng-analytics-v5.2.4.jar
#-libraryjars libs/alipaysdk.jar
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/wup-1.0.0-SNAPSHOT.jar
#-libraryjars libs/weibosdkcore.jar


#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar


#自己项目特殊处理代码

#忽略警告
-dontwarn app.net.tongcheng.model.**
#保留一个完整的包
-keep class app.net.tongcheng.model.**{*;}

#JNI
-dontwarn app.net.tongcheng.util.NativeUtils
-keep class app.net.tongcheng.util.NativeUtils { *;}

 #ShareSDK混淆
 -keep class cn.sharesdk.**{*;}
 -keep class com.sina.**{*;}
 -keep class **.R$* {*;}
 -keep class **.R{*;}
 -dontwarn cn.sharesdk.**
 -dontwarn **.R$*
 -dontwarn com.mob.**
 -keep class m.framework.**{*;}
 #ShareSDK混淆

 #eventbus混淆
 -dontwarn de.greenrobot.event.**
 -keep class de.greenrobot.event.** { *;}
 -keepclassmembers class ** {
     public void onEvent*(**);
 }
 #eventbus混淆

 #友盟
 -keep class com.umeng.**{*;}
 -keepclassmembers class * {
    public <init> (org.json.JSONObject);
 }
 -keep public class app.net.tongcheng.R$*{
 public static final int *;
 }
  #友盟

 #支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

 #fastjson
 -dontwarn com.alibaba.fastjson.**
 -keep class com.alibaba.fastjson.** { *;}
 #fastjson

 ################### region for xUtils
 -keepattributes Signature,*Annotation*
 -keep public class org.xutils.** {
     public protected *;
 }
 -keep public interface org.xutils.** {
     public protected *;
 }
 -keepclassmembers class * extends org.xutils.** {
     public protected *;
 }
 -keepclassmembers @org.xutils.db.annotation.* class * {*;}
 -keepclassmembers @org.xutils.http.annotation.* class * {*;}
 -keepclassmembers class * {
     @org.xutils.view.annotation.Event <methods>;
 }
 #################### end region

 #Glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }
 #Glide

 # ProGuard configurations for Bugtags
 -keepattributes LineNumberTable,SourceFile
 -keep class com.bugtags.library.** {*;}
 -dontwarn org.apache.http.**
 -dontwarn android.net.http.AndroidHttpClient
 -dontwarn com.bugtags.library.**
 # End Bugtags

 # 小米推送
 -keep class com.xiaomi.mipush.sdk.DemoMessageReceiver {*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}
#okio