package com.focuszz.mkfs.web.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focuszz.mkfs.service.LoginInfoService;
import com.focuszz.mkfs.web.form.account.LoginForm;

@Controller
@RequestMapping("/account/")
public class LoginController {

    @Autowired
    private LoginInfoService loginInfoService;

    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public String login(@ModelAttribute("form") LoginForm form, HttpServletRequest request) {
        if (form.isLogon()) {
            return "redirect:"
                   + (StringUtils.isBlank(form.getRedirectURL()) ? "/" : form.getRedirectURL());
        }
        form.setRedirectURL(
            StringUtils.defaultIfBlank(StringUtils.trimToEmpty(request.getHeader("referer")), "/"));
        return "account/login";
    }

    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("form") LoginForm form, HttpServletRequest request,
                          HttpServletResponse response) {

        if (!loginInfoService.login(form.getLoginAccount(), form.getPassword(), request, response,
            form.getWebContext().getIp())) {
            form.putError("userName", "账号或密码不正确 (注意大小写)，请重试。");
            return "account/login";
        }

        String redirectURL = StringUtils.defaultIfBlank(form.getRedirectURL(),
            StringUtils.trimToEmpty(request.getHeader("referer")));
        return "redirect:" + (StringUtils.isBlank(redirectURL) ? "/" : redirectURL);
    }
}
