package com.wss.amd.note.designpattern.proxy;

/**
 * Describe：代理类
 * Created by 吴天强 on 2022/1/17.
 */
public class UserServiceProxy implements UserService {

    private UserService userService = new UserServiceImpl();

    @Override
    public User creteAdmin() {
        System.out.println("代理类开始创建管理员");
        User user = userService.creteAdmin();
        user.setDec("由代理类创建的管理员");
        return user;
    }

    @Override
    public User createUser() {
        System.out.println("代理类开始创建用户");
        User user = userService.creteAdmin();
        user.setDec("由代理类创建的用户");
        return user;
    }
}
