#include <jni.h>
#include <android/log.h>

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getPARTNER(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "2088121327291341");
}

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getSELLER(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "tongchengtx@sina.com");
}

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getRSAPRIVATE(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMnFFe6SNX+FKTKPvgOFpXWTUMMjVuqvZrzeEBLzG66HBFiK+2052GVN1qyc5Muqy0CzK2pYo+C/P/5XqcWigLdhtxjoVhR8K3HTvDuPmrcTsvq0OAKabULmcAk01nsFJbpMVkvkPmLJE4c1tpC9PowH4jhaXbgI9Fk2aXH6rNYpAgMBAAECgYBKwRtcFY1+nn5h/kGfGm/v/NGKStiUAMJmrTt1Wd7iraFdkLiQgkL7XXhw4XwfPTsq0HcAYrDsvs7d0+rRj2ByaDzMGXcTPfUvCgxh4SgHMJj1yZvFze5+hUTf2tBkKCaQr31DAzrSv8/YEsTI1v4Dt3+fSwAvbfHmevEb9TuMJQJBAO+AADp6gHZ3MuPN/zDoKDTBcnodXZjyRAXTC4Hg+KWmeMuDxNuuImy0y20EWIgM3wsZ2Qkq73YlluXoImwK5OMCQQDXq6XvOU+AZB4M64bnokPhgQAQ17DT7jpwRWLVO4GLgedpKfe5izOQsJeipa1ggdQQ1RRA31cC02+NRSGQY1KDAkEA4pXUIX9SWEHvmIyEuY16tGasWpG7wn66ElSXl3nzZCz6LXjt3vSBRx1JNEufQqACyOrcZgsD4GAxwjN7lYI9BwJATq/Sp9hqGDbu+9nG66Y5TAJL6tk3K+ukKKg4KgI+/o5TxvvH5UtTcfvsJyx5eFeF7uo/LHgP//jyn0FUwKBsTwJBAOYXezablWx5onfvGvmRP68qNDH9ilYFsM2wPzUMzgIOXiLyaAnw1cSmStkaxhs4w5XyBBG19KwrffUVut10G/I=");
}

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getRSAPUBLIC(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB");
}

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getnotifyurl(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "http://user.zjtongchengshop.com:8060/AliSecurity/notify_url.php");
}

JNIEXPORT jstring JNICALL
                  Java_app_net_tongcheng_util_NativeUtils_getMerchantSecretKey(JNIEnv *env, jobject instance) {

    // TODO

    return (*env)->NewStringUTF(env, "key=sdvb753!@&*qpaltrko#$%^159plmokn");
}