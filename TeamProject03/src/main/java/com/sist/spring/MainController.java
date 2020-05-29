package com.sist.spring;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.*;
import com.sist.vo.*;
@Controller
public class MainController {
   @Autowired
   private NewsDAO newsDao;
   
   @Autowired
   private MemberDAO dao;
   
   @Autowired
   private MainDAO mDao;
   
   @RequestMapping("main.do")
   public String main_main(String email,Model model,HttpSession session)
   {   
	   	  email="ginogin@gmail.com";
	      /*System.out.println("로그인????");*/
	      List<NewsVO> newsList=newsDao.mainNewsList();
	      
	      for(NewsVO vo:newsList)
	      {
	         String temp=vo.getSubject();
	         temp=temp.substring(0, 70);
	         vo.setSubject(temp+"...");
	         /*System.out.println(vo.getRegdate());*/
	      }
	
	      model.addAttribute("newsList",newsList);
	      
	      //사용자 취향 정보 
	      MemberVO userData=mDao.getUserInfo(email);
	      session.getAttribute("email");
	      System.out.println("지금 로그인 한 사람:"+userData.getNick());
	      model.addAttribute("userData",userData);
	      
	      //추천2 : 성별 추천
	      List<MovieVO> ageList=mDao.ageRecommendation();
	      model.addAttribute("ageList",ageList);

		
	   	  List<MovieVO> bigSliderList=mDao.bigSliderList();
		  for(MovieVO svo:bigSliderList)
		  {
		 	//System.out.println(svo.getNet().getEvaluation_point());
		  }

		  model.addAttribute("bigSliderList", bigSliderList);
		  
		  

		  return "main";
	}
}
