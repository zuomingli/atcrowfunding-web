package com.atguigu.atcrowdfunding.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.AJAXResult;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowfunding.service.UserService;

@Controller
public class DispatcherController {
	@Autowired
	private UserService userService;
   @RequestMapping("/login")
   public String login() {
	   //����һ����½
	   return "login";
   }
   /*
    * ִ�е�¼
    * @return
    * */
   @RequestMapping("doLogin")
   public String doLogin(User user,Model model) throws Exception {
	   //1)��ȡ������
	   //1-1)HttpServletRequest
	   //1-2)�ڷ��������б������ӱ䵭��Ӧ�Ĳ�����������ͬ
	   //1-3)�������ݷ�װʵ����
	   
	/*   String loginacct=user.getLoginacct();
	   //�������ַ������մ���ı��뷽ʽת��Ϊԭʼ���ֽ�������
	   byte []bs=loginacct.getBytes("ISO8859-1");
	   //��ԭʼ���ֽ������а�����ȷ�ı���ת��Ϊ��ȷ�����ּ���
	   loginacct=new String(bs,"UTF-8");*/
	   
	   //2)��ѯ�û���Ϣ
	  User dbUser= userService.query4Login(user);
	   
	  //3)�ж��û���Ϣ�Ƿ����
	  if (dbUser!=null) {
		  //��¼�ɹ�����ת����ҳ��
		return "main";
	}
	  else {
		  //��¼ʧ��,��ת�ص���¼ҳ�棬��ʾ������Ϣ
		  String errorMsg="��¼�˺ź����벻��ȷ������������";
		  model.addAttribute("errorMsg", errorMsg);
	  }
	   return "redirect:login";
   }
   @RequestMapping("/doAJAXLogin")
   @ResponseBody
   public Object doAJAXLogin(User user,HttpSession session) {
	  AJAXResult result=new AJAXResult();
	   User dbUser= userService.query4Login(user);
	   if (dbUser!=null) {
		   session.setAttribute("loginUser", dbUser);
		result.setSuccess(true);
	}else {
		result.setSuccess(false);
	}
	   return result;
   }
   @RequestMapping("/main")
   public String main() {
	   return "main";
   }
   @RequestMapping("/logout")
   public String logout(HttpSession session) {
	   session.removeAttribute("loginUser");
	   //�������session��ֵ
	   session.invalidate();
	   return "redirect:/login";
   }


}
