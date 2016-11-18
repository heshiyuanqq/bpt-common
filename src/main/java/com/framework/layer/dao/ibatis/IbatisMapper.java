package com.framework.layer.dao.ibatis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IbatisMapper annotation for MapperScannerConfigurer to scan mapper
 * interfaces.
 * 
 * @author zhangjunjun
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IbatisMapper {

}