package com.codefog.admin.admin.modular.system.controller;

import com.codefog.admin.bean.exception.InvalidKaptchaException;
import com.codefog.admin.admin.core.base.controller.BaseController;
import com.codefog.admin.platform.log.LogManager;
import com.codefog.admin.platform.log.LogTaskFactory;
import com.codefog.admin.service.system.UserService;
import com.codefog.admin.shiro.ShiroKit;
import com.codefog.admin.utils.HttpKit;
import com.codefog.admin.admin.core.util.ApiMenuFilter;
import com.codefog.admin.admin.core.util.KaptchaUtil;
import com.codefog.admin.bean.vo.node.MenuNode;
import com.codefog.admin.bean.core.ShiroUser;
import com.codefog.admin.bean.entity.system.User;
import com.codefog.admin.dao.system.MenuRepository;
import com.codefog.admin.dao.system.UserRepository;
import com.codefog.admin.service.system.MenuService;
import com.codefog.admin.utils.ToolUtil;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Long> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }


            List<MenuNode> menuNodes =  menuService.getMenusByRoleIds(roleList);
            List<MenuNode> titles = MenuNode.buildTitle(menuNodes);
            titles = ApiMenuFilter.build(titles);

            model.addAttribute("titles", titles);


        //获取用户头像
        Long id = ShiroKit.getUser().getId();
        User user = userService.get(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
