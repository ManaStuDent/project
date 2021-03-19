package com.manastudent.core.config;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 如果想在参数中使用 @NotNull 这种注解校验，就必须在 Controller 上添加 @Validated；
     * public UserDTO getUser(@NotNull(message = "userId不能为空") Integer userId){
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object handle1(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        String message = "";
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
//            String paramName = pathImpl.getLeafNode().getName();
            message = constraintViolation.getMessage();
            break;
        }
        return message;
    }

    /**
     * 如果方法中的参数是对象类型，则必须要在参数对象前面添加 @Validated
     * public Response<UserDTO> getUser(@Validated @RequestBody UserDTO userDTO){
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handle2(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult != null) {
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                String defaultMessage = fieldError.getDefaultMessage();
                return defaultMessage;
            } else {
                return "MethodArgumentNotValidException";
            }
        } else {
            return "MethodArgumentNotValidException";
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        e.printStackTrace();
        return "error";
    }
}
