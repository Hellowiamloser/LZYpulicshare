package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceImplName};
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
* @author zhouhonggang
* @version 1.0.0
* @project spring-boot-pro
* @datetime ${date}
* @description: [${table.comment!}·接口]
*/
@RestController
@RequestMapping("${controllerMappingHyphen?replace('-', '/')}")
public class ${table.controllerName} {

    @Resource
    private ${table.serviceImplName} ${table.serviceImplName?uncap_first};

    /**
     * ${table.comment!} 添加
     * @param entity 实体类
     */
    @PostMapping
    @LoggerOperation(module = "${table.comment!}", message = "添加", type = TypeEnumerate.INSERT)
    public void save(@Valid @RequestBody ${entity} entity) {
        ${table.serviceImplName?uncap_first}.insert(entity);
    }

    /**
     * ${table.comment!} 修改
     * @param entity 实体类
     */
    @PutMapping
    @LoggerOperation(module = "${table.comment!}", message = "修改", type = TypeEnumerate.UPDATE)
    public void update(@Valid @RequestBody ${entity} entity) {
        ${table.serviceImplName?uncap_first}.update(entity);
    }

    /**
     * ${table.comment!} 单删
     * @param id 主键id
     */
    @DeleteMapping("{id}")
    @LoggerOperation(module = "${table.comment!}", message = "单删", type = TypeEnumerate.DELETE)
    public void delete(@NotNull(message = "主键ID不能为null") @PathVariable int id) {
        ${table.serviceImplName?uncap_first}.delete(id);
    }

    /**
     * ${table.comment!} 主键ID查询
     * @param id 主键id
     * @return 实体类
     */
    @GetMapping("{id}")
    @LoggerOperation(module = "${table.comment!}", message = "主键查询", type = TypeEnumerate.SELECT)
    public ${entity} load(@PathVariable @NotNull(message = "主键ID不能为null") int id) {
        return ${table.serviceImplName?uncap_first}.load(id);
    }

    /**
     * ${table.comment!} 分页查询
     * @param entity 主键对象
     * @return 分页对象
     */
    @PostMapping("page")
    @LoggerOperation(module = "${table.comment!}", message = "分页查询", type = TypeEnumerate.SELECT)
    public Page<${entity}> page(@RequestBody ${entity} entity) {
        return ${table.serviceImplName?uncap_first}.page(entity);
    }

}