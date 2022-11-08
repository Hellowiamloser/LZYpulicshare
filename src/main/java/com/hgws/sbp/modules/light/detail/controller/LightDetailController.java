package com.hgws.sbp.modules.light.detail.controller;
import com.hgws.sbp.modules.light.detail.entity.Detial;
import com.hgws.sbp.modules.light.detail.service.LightDetailService;
import com.hgws.sbp.modules.light.group.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("light/detail")
public class LightDetailController {

    @Autowired
    private LightDetailService lightDetailService;

    @GetMapping
    public List<Detial> query(){
        return lightDetailService.query();
    }

    @PostMapping
    public void save(@RequestPart Detial entity, @RequestPart MultipartFile[] images) throws IOException {//MultipartFile单个文件,MultipartFile[]多个文件,名字确保一致
        lightDetailService.save(entity,images);
    }
}
