package com.wx.school.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.vo.EntityResults;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;

public interface IUserService {

	public User login(User user, boolean back);


	public void validAdmin(String userId);

	public User loadUserForAdmin(User user);

	public void editUserForAdmin(User user);

	public User submitPersonInfo(User user);

	public User loadMyPersonInfo();

	public void submitStudentInfo(Student student);

	public List<Student> listStudentInfo();

	public void updateUserPassword(User user);

	public void valideMobileNumber(User user);

	public User updateUserPasswordWhenForgot(User user);

	public EntityResults<Student> listStudentsForAdmin(SearchVO uvo);

	public void deleteStudentInfo(Student student);

	public Student loadStudentInfo(Student student);

	public void updateStudentInfo(Student student);

	public Map<String, Object> sumtUserInfo();


	public String exportStudentInfo(SearchVO uvo);





	
	

}
