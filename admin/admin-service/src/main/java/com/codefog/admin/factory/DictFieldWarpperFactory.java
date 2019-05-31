package com.codefog.admin.factory;

import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.service.system.IConstantFactory;
import com.codefog.admin.service.system.impl.ConstantFactory;

import java.lang.reflect.Method;

/**
 * 字段的包装创建工厂
 *
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object field, String methodName) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, field.getClass());
            Object result = method.invoke(me, field);
            return result;
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(field.toString()));
                return result;
            } catch (Exception e1) {
                throw new GunsException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }

}
