package com.focuszz.mkfs.web.controller.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focuszz.mkfs.entity.LoginInfo;
import com.focuszz.mkfs.service.LoginInfoService;
import com.focuszz.mkfs.web.form.account.RegisterForm;

@Controller
@RequestMapping("/account/")
public class RegisterController {

    @Autowired
    private LoginInfoService loginInfoService;

    @RequestMapping(value = "register.html", method = RequestMethod.GET)
    public String register(@ModelAttribute("form") RegisterForm form) {
        return "account/register";
    }

    @RequestMapping(value = "register.html", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("form") RegisterForm form, HttpServletRequest request,
                             HttpServletResponse response) {

        String loginAccount = form.getLoginAccount();
        if (loginInfoService.isExist(loginAccount)) {
            form.putError("mobilePhone",
                "该帐号已经被注册<a href=\"/account/forgot_password.html\">忘记密码？</a>");
            return "account/register";
        }
        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            form.putError("confirmPassword", "两次密码不一样");
            return "account/register";
        }
        String kaptchaCode = (String) request.getSession()
            .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.equals(form.getCheckcode(), kaptchaCode)) {
            form.putError("checkcode", "验证码不正确");
            return "account/register";
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginAccount(form.getLoginAccount());
        loginInfo.setPassword(DigestUtils.md5Hex(StringUtils.trimToEmpty(form.getPassword())));
        loginInfoService.txAdd(loginInfo);

        loginInfoService.login(form.getLoginAccount(), form.getPassword(), request, response,
            form.getWebContext().getIp());

        return "redirect:" + form.getDomain();
    }

    @RequestMapping(value = "validate_login_account_is_registed.json")
    @ResponseBody
    public void validateLoginAccountIsRegisted(@ModelAttribute("form") RegisterForm form,
                                               HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("application/json");
            out = response.getWriter();
            out.write(loginInfoService.isExist(form.getLoginAccount()) ? "false" : "true");
        } catch (IOException e) {
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }

    @RequestMapping(value = "validate_checkcode.json")
    @ResponseBody
    public void validateCheckcode(@ModelAttribute("form") RegisterForm form,
                                  HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("application/json");
            out = response.getWriter();
            String kaptchaCode = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            out.write(StringUtils.equals(form.getCheckcode(), kaptchaCode) ? "true" : "false");
        } catch (IOException e) {
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }
}
