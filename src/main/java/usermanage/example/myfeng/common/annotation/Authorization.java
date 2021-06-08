package usermanage.example.myfeng.common.annotation;


import usermanage.example.myfeng.common.security.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权注解，作用于controller类及其方法上，方法优先
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization
{
    UserRole[] value() default{};
}
