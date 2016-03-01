package com.smok.web.Fetch;

import com.smok.web.utils.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;

/**
 * Created by Administrator on 2016/1/13.
 */
public class FetchOaArch {

    public static void main(String[] args) {
        try {
            System.out.println(getRoot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getRoot() throws Exception{
        String url = "http://oa.58.com.cn/prersonnelmngwf/treeData";


        return HttpClientUtil.post(url, "utf-8", null, null, 3000, 5000);
    }
}
