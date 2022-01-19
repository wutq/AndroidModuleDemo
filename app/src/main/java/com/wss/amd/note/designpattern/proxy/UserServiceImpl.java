package com.wss.amd.note.designpattern.proxy;

/**
 * Describe：创建用户实现
 * Created by 吴天强 on 2022/1/17.
 */
public class UserServiceImpl implements UserService {
    @Override
    public User creteAdmin() {
        User user = new User();
        user.setName("admin");
        user.setPassword("000000");
        return user;
    }

    @Override
    public User createUser() {
        User user = new User();
        user.setName("Tom");
        user.setPassword("000000");
        user.setAge(18);
        return user;
    }
}
