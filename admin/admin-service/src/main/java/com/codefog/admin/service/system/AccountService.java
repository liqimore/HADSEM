package com.codefog.admin.service.system;

import com.codefog.admin.bean.core.ShiroUser;
import com.codefog.admin.bean.entity.system.User;
import com.codefog.admin.dao.cache.TokenCache;
import com.codefog.admin.platform.log.LogManager;
import com.codefog.admin.platform.log.LogTaskFactory;
import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.Convert;
import com.codefog.admin.utils.HttpKit;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * AccountService
 *
 */
@Service
public class AccountService {
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private UserService userService;

    public String login(Long idUser) {
        String token = UUID.randomUUID().toString();
        tokenCache.put(token, idUser);
        LogManager.me().executeLog(LogTaskFactory.loginLog(idUser, HttpKit.getIp()));
        User user = userService.get(idUser);
        Long[] roleArray = Convert.toLongArray(true,Convert.toStrArray(",",  user.getRoleid()));
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setAccount(user.getAccount());
        shiroUser.setDeptId(user.getDeptid());
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));
        shiroUser.setId(idUser);
        shiroUser.setName(user.getName());
        shiroUser.setRoleList(Lists.newArrayList(roleArray));
        List<String> roleNames = Lists.newArrayList();
        List<String> roleCodes = Lists.newArrayList();
        for (Long roleId : roleArray) {
            roleCodes.add(ConstantFactory.me().getSingleRoleTip(roleId));
            roleNames.add(ConstantFactory.me().getSingleRoleName(roleId));

        }
        shiroUser.setRoleNames(roleNames);
        shiroUser.setRoleCodes(roleCodes);
        tokenCache.setUser(token,shiroUser);
        return token;
    }


    public void logout(String token) {
        tokenCache.remove(token);
    }

}
