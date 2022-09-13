package com.hgws.sbp;

import com.hgws.sbp.modules.system.generate.service.GeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-09-02 14:32
 * @description: 一键生成
 */
@SpringBootTest
public class ModuleGenerateTests {

    @Autowired
    private GeneratorService generatorService;

    // 当前表名称
    private final static String NAME = "system_role";
    // 父级模块名称
    private final static String PARENT = "system";
    // 当前模块名称
    private final static String CURRENT = "role";
    // 封装模块数据
    private final Map<String, String> params = Map.of(
            "name", NAME,
            "parent", PARENT,
            "current", CURRENT);

    //@Test
    //void generate() {
    //    generatorService.generator(params);
    //}

}
