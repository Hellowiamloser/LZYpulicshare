package com.hgws.sbp;

import org.junit.jupiter.api.Test;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-30 16:16
 * @description: 数据库连接信息加密
 */
public class DataSecurityProtectionTests {

    private final static String USER_NAME = "root";
    private final static String PASS_WORD = "123456";

    @Test
    public void security() {
        // 1.生成加密秘钥
        //String securityKey = AES.generateRandomKey();

        // 2.针对字符加密
        //String username = AES.encrypt(USER_NAME, securityKey);
        //String password = AES.encrypt(PASS_WORD, securityKey);

        // 3.获取执行结果
        //System.out.println("秘钥:"+securityKey);  // 秘钥:127d543ffd4a7941
        //System.out.println("账号:"+username);     // 账号:VfkKTxRK9v0IQMEzJ1hgBQ==
        //System.out.println("密码:"+password);     // 密码:voUKzONmh20zDhwYFI47Mg==

        // 4.项目使用
        // idea 设置 Program arguments , 服务器可以设置为启动环境变量
        // --mpw.key=127d543ffd4a7941
    }

}
