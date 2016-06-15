# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\AndroidStudio_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn app.net.tongcheng.model.**
-keep class app.net.tongcheng.model.** { *;}
-dontwarn com.tal.**
-keep class com.tal.** { *;}
-dontwarn com.ut.**
-keep class com.ut.** { *;}
-dontwarn org.json.alipay.**
-keep class org.json.alipay.** { *;}
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *;}
-dontwarn at.markushi.ui.**
-keep class at.markushi.ui.** { *;}
-dontwarn android.support.design.**
-keep class android.support.design.** { *;}
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** { *;}
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *;}
-dontwarn com.yancy.imageselector.**
-keep class com.yancy.imageselector.** { *;}
-dontwarn com.wang.avi.**
-keep class com.wang.avi.** { *;}
-dontwarn me.drakeet.materialdialog.**
-keep class me.drakeet.materialdialog.** { *;}
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *;}
-dontwarn com.github.johnpersano.supertoasts.**
-keep class com.github.johnpersano.supertoasts.** { *;}
-dontwarn com.github.promeg.pinyinhelper.**
-keep class com.github.promeg.pinyinhelper.** { *;}
-dontwarn com.kevin.wraprecyclerview.**
-keep class com.kevin.wraprecyclerview.** { *;}
-dontwarn android.backport.webp.**
-keep class android.backport.webp.** { *;}
-dontwarn org.xutils.**
-keep class org.xutils.** { *;}

#ShareSDK混淆
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
-dontwarn com.mob.**
-keep class m.framework.**{*;}
