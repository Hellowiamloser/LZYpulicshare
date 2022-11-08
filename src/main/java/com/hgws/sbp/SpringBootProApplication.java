/*
 * Copyright (c) [2022] [zhouhonggang]
 *
 * [spring-boot-pro] is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 *  http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package com.hgws.sbp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-11 09:28
 * @description: 应用启动入口
 */
@Slf4j
@SpringBootApplication
public class SpringBootProApplication {

    public static void main(String[] args) {
        console(SpringApplication.run(SpringBootProApplication.class, args));
    }

    public static void console(ConfigurableApplicationContext application) {
            Environment env = application.getEnvironment();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = env.getProperty("server.port");

        String[] profiles = env.getActiveProfiles();
        if(Arrays.stream(profiles).anyMatch(profile -> profile.equals("dev")))
        {
            log.info("\n" +
                    "[spring-boot-pro]应用启动完成 \n" +
                    "接口地址: http://" + ip + ":" + port + "\n" +
                    "监控地址: http://" + ip + ":" + port + "/druid/\n" +
                    "文档地址: http://" + ip + ":" + port + "/swagger-ui/");
        }
    }

}
