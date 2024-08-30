package com.aliyun.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkcontact_1_0.Client;
import com.aliyun.dingtalkcontact_1_0.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserCreateRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2UserCreateResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;

public class HRService {
    private static Logger log = Logger.getLogger(HRService.class);
    private static String accessToken;


    public HRService() throws ApiException {
        accessToken = new GetToken().getToken();
        log.info("当前刷新的token是" + accessToken);
    }

    public void createUser(String userId) throws ApiException {
        log.debug("当前传入的userID是" + userId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/create");
        OapiV2UserCreateRequest req = new OapiV2UserCreateRequest();
        req.setUserid(userId);
        req.setExclusiveAccount(true);
        req.setExclusiveAccountType("dingtalk");
        req.setLoginId("test" + userId);
        req.setInitPassword("673" + userId);
        req.setName("test" + userId);
        req.setDeptIdList("903271838");
//                    req.setTelephone("+86-18657167430");
//                    req.setJobNumber(userId);
//                    req.setTitle("账号迁移专家");
//                    req.setWorkPlace("北京");
//                    req.setExtension("{\"爱好\":\"旅游\",\"年龄\":\"24\"}");
//                    req.setSeniorMode(false);
//                    req.setHiredDate(1650351000000l);
//                    req.setManagerUserid("1260091337650025754");
//                    req.setExclusiveMobile("+86-18657167430");

        OapiV2UserCreateResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
    }

    public void getUser(String userId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");
        OapiV2UserGetResponse rsp = client.execute(req, accessToken);
        log.debug(rsp.getBody());
    }

    public void getUserByPhone(String phone) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile("15363369018");
        req.setSupportExclusiveAccountSearch(true);
        OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
    }

    /*** v1.0的Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkcontact_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcontact_1_0.Client(config);
    }


    public void coldUser(String userId) throws Exception {
        com.aliyun.dingtalkcontact_1_0.Client client = createClient();
        SetDisableHeaders setDisableHeaders = new SetDisableHeaders();
        setDisableHeaders.xAcsDingtalkAccessToken = accessToken;
        SetDisableRequest setDisableRequest = new SetDisableRequest()
                .setUserId(userId)
                .setReason("账号过期");
        SetDisableResponse rs = client.setDisableWithOptions(setDisableRequest, setDisableHeaders, new RuntimeOptions());
        log.debug(rs.getBody());
        queryUserStatus(userId, client);
    }

    public void openUser(String userId) throws Exception {
        Client client = createClient();
        SetEnableHeaders setEnableHeaders = new SetEnableHeaders();
        setEnableHeaders.xAcsDingtalkAccessToken = accessToken;
        SetEnableRequest setEnableRequest = new SetEnableRequest()
                .setUserId(userId);

        SetEnableResponse rs = client.setEnableWithOptions(setEnableRequest, setEnableHeaders, new RuntimeOptions());
        log.debug(rs.getBody());
        queryUserStatus(userId, client);
    }


    public void queryUserStatus(String userId, Client client) throws Exception {
        QueryStatusHeaders queryStatusHeaders = new QueryStatusHeaders();
        queryStatusHeaders.xAcsDingtalkAccessToken = accessToken;
        QueryStatusRequest queryStatusRequest = new QueryStatusRequest()
                .setUserId(userId);

        QueryStatusResponseBody body = client.queryStatusWithOptions(queryStatusRequest, queryStatusHeaders, new RuntimeOptions()).getBody();
        if (body.getDisable()) {
            log.info("企业账号已关闭");
        } else {
            log.info("企业账号已启用");
        }
    }
    
    public void stickUser(String userId) throws Exception {
        Client client = createClient();
        SignOutHeaders signOutHeaders = new SignOutHeaders();
        signOutHeaders.xAcsDingtalkAccessToken = accessToken;
        SignOutRequest signOutRequest = new SignOutRequest()
                .setUserId(userId)
                .setReason("账号停用");
        SignOutResponse rs = client.signOutWithOptions(signOutRequest, signOutHeaders, new com.aliyun.teautil.models.RuntimeOptions());
        if (rs.getBody().getResult()) {
            log.info("踢出成功");
        }
    }

    // V2.0的Client
    public void queryDeptUser(Long deptId) throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
        OapiV2UserListRequest req = new OapiV2UserListRequest();
        req.setDeptId(deptId);
        req.setCursor(0L);
        req.setSize(10L);
        OapiV2UserListResponse rsp = client.execute(req, accessToken);
        Integer count = 0;
        for (OapiV2UserListResponse.ListUserResponse user:rsp.getResult().getList()) {
            log.info((count++).toString()+"号部门员工"+ JSON.toJSONString(user));
            }
        }
    }

