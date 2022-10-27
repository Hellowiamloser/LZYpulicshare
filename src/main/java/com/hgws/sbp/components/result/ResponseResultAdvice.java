package com.hgws.sbp.components.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgws.sbp.commons.annotation.DictionaryOperation;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.commons.utils.PropertiesUtils;
import com.hgws.sbp.modules.system.dictionary.service.DictionaryDetailService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 10:59
 * @description: 封装公共响应
 */
@RestControllerAdvice(basePackages = "com.hgws.sbp.modules")
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

    @Value("${spring.profiles.active}")
    private String profiles;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;

    private final static Logger logger = LoggerFactory.getLogger(ResponseResultAdvice.class);

    /**
     * 定义需要拦截的方法
     * @param methodParameter 方法参数
     * @param converterType   目标对象
     * @return  是否拦截
     */
    @Override
    public boolean supports(MethodParameter methodParameter, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return (methodParameter.hasMethodAnnotation(ResponseBody.class)
                || AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResponseBody.class));
    }

    /**
     * 统一封装响应返回结果
     * @param data                  返回结果
     * @param methodParameter       方法参数
     * @param mediaType             返回类型
     * @param aClass                目标对象
     * @param request               请求
     * @param response              响应
     * @return  封装结果集
     */
    @Override
    public Object beforeBodyWrite(Object data, @NotNull MethodParameter methodParameter, @NotNull MediaType mediaType, @NotNull Class<? extends HttpMessageConverter<?>> aClass, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if("dev".equals(profiles)) {
            String result = "";
            try {
                //普通输出
                //result = objectMapper.writeValueAsString(data);
                //美化输出
                result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            logger.info("\n" +
                    "\u5f53\u524d\u5185\u5bb9\u4ec5\u5728\u5f00\u53d1\u73af\u5883\u8f93\u51fa: \u5f00\u59cb\u8f93\u51fa\u672c\u6b21\u6267\u884c\u7ed3\u679c\n"
                    + result + "\n" +
                    "\u5f53\u524d\u5185\u5bb9\u4ec5\u5728\u5f00\u53d1\u73af\u5883\u8f93\u51fa: \u7ed3\u675f\u8f93\u51fa\u672c\u6b21\u6267\u884c\u7ed3\u679c");
        }

        if (!ObjectUtils.isEmpty(data)) {
            if (data instanceof Base) {
                data = dynamicProperties(data);
            } else if (data instanceof Page) {
                if (data instanceof Base) {
                    Page<Object> page = (Page) data;
                    List<Object> records = page.getRecords();
                    if (!ObjectUtils.isEmpty(records)) {
                        for (int i = 0; i < records.size(); i++) {
                            Object object = records.get(i);
                            records.set(i, dynamicProperties(object));
                        }
                    }
                }
            }
        }
        if(data instanceof String)
            return data;
        else if(data instanceof Result)
            return data;
        return Result.success(data);
    }

    /**
     * CGLIB动态属性增强
     * @param data 带增强实例
     * @return 增强后实例
     */
    public Object dynamicProperties(Object data)
    {
        Field[] fields = data.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(field.isAnnotationPresent(DictionaryOperation.class))
            {
                DictionaryOperation dictionaryOperation = field.getAnnotation(DictionaryOperation.class);
                //获取数据字典code
                String code = dictionaryOperation.code();
                //获取数据库真实值
                Integer value = 1;
                try {
                    field.setAccessible(true);
                    value = (Integer) field.get(data);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //获取数据字典表现值
                String showValue = dictionaryDetailService.loadDetailNameByCode(code, value);
                //封装动态数据字典值
                Map<String, Object> addProperties = new HashMap() {{
                    put(field.getName()+"Text", showValue);
                }};
                //返回封装后动态数据
                return PropertiesUtils.getTarget(data, addProperties);
            }
        }
        return data;
    }

    /**
     * 统一封装错误响应
     * @param exception 异常
     * @return  封装结果集
     */
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception exception) {
        logger.error(exception.getMessage(), exception);
        //参数校验封装
        if(exception instanceof MethodArgumentNotValidException)
            return Result.failure(ResultEnumerate.PARAM_IS_INVALID, methodArgumentNotValidException((MethodArgumentNotValidException)exception));
        //公共的错误
        return Result.failure(ResultEnumerate.INTERFACE_INNER_ERROR, exception.getMessage());
    }

    /**
     * 用户未授权异常不处理
     * @param exception AccessDeniedException
     * @throws AccessDeniedException AccessDeniedException
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException exception) throws AccessDeniedException {
        throw exception;
    }

    /**
     * 对于校验封装
     * @param exception 校验异常
     * @return 封装的提示信息
     */
    public Map<String, Object> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, Object> result = new LinkedHashMap<>();
        //获取验证错误的总数量
        result.put("total", bindingResult.getErrorCount());
        //获取每个验证失败的属性
        exception.getFieldErrors().forEach(error->result.put(error.getField(), error.getDefaultMessage()));
        return result;
    }

}
