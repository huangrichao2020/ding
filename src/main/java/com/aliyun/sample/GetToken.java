package com.aliyun.sample;


import com.aliyun.tea.TeaException;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.taobao.api.ApiException;


import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class GetToken {

    private static String accessToken = "";

    public String getToken() throws ApiException {
        return this.getAccessToken();
    }

    private String getAccessToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey("ding10g0vmxffwsivhn3");
        req.setAppsecret("za2gri_2MRW_4c1PfT3G2mnvbhmXFxKlw6RTDs1PTvoFtTYIDBnMbpdXfaOgqXGB");
        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = client.execute(req);
        accessToken = rsp.getAccessToken();
        return accessToken;
    }

}
