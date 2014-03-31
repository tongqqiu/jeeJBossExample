package org.tongqing.jee.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
@Secure
@Named
@RequestScoped
@Stereotype
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecureWebService
{
}