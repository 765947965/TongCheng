package app.net.tongcheng.util;

import java.io.File;
import java.util.HashMap;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/18 16:18
 */

public class RequestParams {
    private String url;
    private boolean multipart;
    private String BodyContent;
    private HashMap<String, String> mQueryStringParameterMap = new HashMap<>();
    private HashMap<String, String> mBodyParameterMap = new HashMap<>();
    private HashMap<String, File> mFileParameterMap = new HashMap<>();

    public RequestParams(String url) {
        this.url = url;
    }

    public void addQueryStringParameter(String key, String value) {
        mQueryStringParameterMap.put(key, value);
    }

    public void addBodyParameter(String key, String value) {
        mBodyParameterMap.put(key, value);
    }

    public void addFileParameter(String key, File file) {
        mFileParameterMap.put(key, file);
    }

    public void removeParameter(String key) {
        mQueryStringParameterMap.remove(key);
        mBodyParameterMap.remove(key);
    }

    public String getStringParameter(String key) {
        return mQueryStringParameterMap.containsKey(key) ? mQueryStringParameterMap.get(key) : mBodyParameterMap.get(key);
    }

    public HashMap<String, String> getQueryStringParameterMap() {
        return mQueryStringParameterMap;
    }

    public HashMap<String, String> getBodyParameterMap() {
        return mBodyParameterMap;
    }

    public HashMap<String, File> getFileParameterMap() {
        return mFileParameterMap;
    }

    public String getUrl() {
        return url;
    }

    public boolean isMultipart() {
        return multipart;
    }

    public void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }

    public String getBodyContent() {
        return BodyContent;
    }

    public void setBodyContent(String bodyContent) {
        BodyContent = bodyContent;
    }
}
