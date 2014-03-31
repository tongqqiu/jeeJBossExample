package org.tongqing.jee.security;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
@InterceptorBinding
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secure
{

}
