package com.hgws.sbp.modules.light.detail.service;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.light.detail.dao.LightDetailDao;
import com.hgws.sbp.modules.light.detail.entity.Detial;
import com.hgws.sbp.modules.light.group.entity.Group;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class LightDetailService extends BaseService<LightDetailDao, Detial> {

    @Value("${server.path}")
    private String path;//服务器上传文件的路径

    public List<Detial> query(){
        return dao.query();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void save(Detial entity, MultipartFile[] images) throws IOException {
        //一、添加校验,检验数据库当前坐标位置是否可以添加路灯


        //1.写入灯具详情,自定义SQL
        //灯具详情与灯具区域建立关系,light_details id——>LightArea id
        dao.insertDetail(entity);

        //二、修改校验,校验查询灯具是否在当前分组中
        List<Group> groupList = entity.getLightGroup();
        if(ObjectUtils.isEmpty(groupList)){
            throw new RuntimeException("灯具分组不能为空");
        }
        //2.写入分组详情关系表 自定义SQL
        dao.insertGroupDetail(entity.getId(),groupList);

        //3.保存图片到服务器指定位置
        if(ObjectUtils.isEmpty(images)){
            throw new RuntimeException("添加的图片不能为空");
        }
        //记录路径,出现异常进行回滚
        List<Path> pathList=new ArrayList<>();
        for (MultipartFile image:images){
            String name = image.getOriginalFilename();//获取图片原文件名
            String suffix = name.substring(name.lastIndexOf("."));//获取图片后缀名".XXX"
            String realname = System.currentTimeMillis() + suffix;//图片存储在服务器的真实文件名
            try {
                Path serverPath = Path.of(path + realname);
                image.transferTo(serverPath);//图片存储为在path路径下叫realname的文件
                pathList.add(serverPath);//存储成功后记录路径
                Thumbnails
                        .of(serverPath.toFile())//装载原始图片
                        .scale(0.5)//设置压缩比
                        .toFile(path+"t_yasuo"+realname);//设置压缩后保存路径
            } catch (IOException e) {
                //删除已经保存的文件
                if(pathList.size()>0){
                    pathList.forEach(path1 -> {
                        path1.toFile().delete();
                    });
                }
                throw new IOException("文件上传失败");//之所以已经抓取异常后还要抛出,就是为了触发事务
            }

        }

        //4.写入图片信息
        //图片表插入多条记录-> light_detail_id外键


    }




    //判断与数据库范围内是否存在路灯
    private boolean hasExistLamps(List<Detial> lightdetails,BigDecimal x0,BigDecimal y0,double range){
        boolean flag=false;
        for (Detial detial : lightdetails) {
            BigDecimal  x = detial.getAreaLng();
            BigDecimal  y= detial.getAreaLat();
            double length = Math.sqrt(x.subtract(x0).multiply(x.subtract(x0)).add(y.subtract(y0).multiply(y.subtract(y0))).doubleValue());
            if(length<range){
                flag=true;
                break;
            }
        }
        return flag;
    }
}
