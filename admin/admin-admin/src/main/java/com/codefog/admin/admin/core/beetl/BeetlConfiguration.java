package com.codefog.admin.admin.core.beetl;

import com.codefog.admin.admin.core.util.KaptchaUtil;
import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
        groupTemplate.registerFunctionPackage("constant",ConstantFactory.me());

    }

}
