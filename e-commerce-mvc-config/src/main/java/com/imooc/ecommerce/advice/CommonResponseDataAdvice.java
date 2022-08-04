package com.imooc.ecommerce.advice;

import com.imooc.ecommerce.annotation.IgnoreResponseAdvice;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <h1>实现统一响应</h1>
 */
@RestControllerAdvice("com.imooc.ecommerce")

public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * <h2>判断是否需要对响应进行处理</h2>
     */
    @Override
    @SuppressWarnings("all") // 屏蔽警告信息注解
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        // 如果该方法所在的类标识了 IgnoreResponseAdvice 注解，则不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        // 如果方法标识了 IgnoreResponseAdvice 注解，则不需要处理
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            // return false 的意思就是不进行处理，return true 的意思就是进行处理。
            // 处理的方式看下面的 beforeBodyWrite 方法
            return false;
        }
        return true;
    }


    /**
     * beforeBodyWrite 在响应结果返回之前
     */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        // 该方法默认是返回 null，即不做任何处理。

        // 我们定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(0, "");

        /**
         * 如果响应的结果是 null，那么我们不需要进行包装，直接返回统一返回结果 response，
         * 如果是 CommonResponse 类型，那么我们也不需要进行包装。
         * 最后，如果返回结果既不是 null，也不是 CommonResponse 这个类型，
         * 我们将返回结果进行包装，包装为 CommonResponse 类型进行返回
         * */

        if (null == o) {
            return response;
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
        } else {
            response.setData(o);
        }
        return response;
    }
}
