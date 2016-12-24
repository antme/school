package com.wx.school.service;

import java.util.List;

import com.wx.school.bean.user.Person;
import com.wx.school.bean.user.User;

public interface IUserService {

	public User login(User user);

	public void logout();

	public boolean isAdmin(String userId);

	public User regWifiCloudUser(String wcuname, String name, String serverIp);

	public List<User> selectWifiCloudUsers();

	public User loadUserForAdmin(User user);

	public void editUserForAdmin(User user);

	public User submitPersonInfo(Person person, User user);

	public Person loadMyPersonInfo();

}
