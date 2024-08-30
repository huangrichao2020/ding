// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.sample;

import com.aliyun.tea.*;
import com.sun.org.slf4j.internal.Logger;
import sun.rmi.runtime.Log;

import java.util.Random;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Sample {



    public static void main(String[] args_) throws Exception {

        java.util.List<String> args = java.util.Arrays.asList(args_);
        HRService service = new HRService();
        String userId = "024142036123647865";
        String phone = "17623122334";
//        int userId = new Random().nextInt(1000);
        // 创建组织内账号
//        service.createUser(String.valueOf(userId));
        // 查询组织内账号
//        service.getUser(userId);
        // 根据手机号查询企业账号
//        service.getUserByPhone(phone);
        //冻结账号
//        service.coldUser(userId);
        //启用账号
//        service.openUser(userId);
        //踢登账号
//        service.stickUser(userId);
        service.queryDeptUser(860700436L);
    }
}