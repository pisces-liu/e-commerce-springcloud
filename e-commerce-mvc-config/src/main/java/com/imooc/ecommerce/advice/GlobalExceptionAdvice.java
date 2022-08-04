package com.imooc.ecommerce.advice;

import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>全局异常捕获处理</h2>
 */
@Slf4j
// 不对 value 进行标识，我们捕获所有的包中爆出的异常
@RestControllerAdvice // 也可以通过 aop ，拦截器，进行实现该结果。
public class GlobalExceptionAdvice {

    /**
     * 即使代码抛出异常，我们也返回统一的返回类型。做到真正的统一返回结果。
     */

    // 该注解的作用，给 HttpServletRequest req, Exception ex 传递参数
    // 该注解可以对系统中的所有异常进行拦截，拦截之后，异常就不会直接对客户端进行返回，
    // 而是通过 handlerCommerceException 这个方法进行返回。
    // value = Exception.class 捕捉哪些类型的异常。在 value 中定义即可。
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(HttpServletRequest request, Exception ex) {

        // 将错误码定义为 -1，错误信息为 "business error"
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        // 保存异常信息
        response.setData(ex.getMessage());
        // 打印日志
        log.error("commerce service has error: [{}]", ex.getMessage(), ex);
        return response;
    }

}