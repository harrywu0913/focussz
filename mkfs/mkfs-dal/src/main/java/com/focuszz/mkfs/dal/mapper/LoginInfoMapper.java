package com.focuszz.mkfs.dal.mapper;

import org.apache.ibatis.annotations.Param;

import com.focuszz.mkfs.entity.LoginInfo;

public interface LoginInfoMapper extends BaseMapper {

    public LoginInfo queryByLoginAccountAndPassword(@Param("loginAccount") String loginAccount,
                                                    @Param("password") String password);

    public LoginInfo queryByLoginAccount(@Param("loginAccount") String loginAccount);
}
