package com.sist.spring;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.NewsDAO;
import com.sist.vo.NewsReviewVO;
import com.sist.vo.NewsVO;

@Controller
public class NewsController {
	@Autowired
	private NewsDAO dao;
	
	@RequestMapping("newsList.do")
	public String news_list(Model model,String page)
	{
		if(page==null)
			page="1";
		int totalpage=dao.newsTotalListPage();
		int curpage=Integer.parseInt(page);
		//한 페이지당 가져오는 데이터수
		int rowSize=5;
		int start=(rowSize*curpage)-(rowSize-1);
		int end=(rowSize*curpage);
		
		//블록 페이징
		int block=5;
		int startblock;
		int endblock;
		if(curpage%block==0){
			startblock=(curpage/block)*block-(block-1);
			endblock=(curpage/block)*block;
		}
		else{
			startblock=(curpage/block)*block+1;
			endblock=(curpage/block+1)*block;
		}
		
		HashMap map=new HashMap();
		map.put("start", start);
		map.put("end", end);
		
		List<NewsVO> list=dao.newsListData(map);
		int count=dao.newsCount();
		
		model.addAttribute("count",count);
		model.addAttribute("startblock",startblock);
		model.addAttribute("endblock",endblock);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("list",list);
		return "project/news/newsList";
	}
	
	@RequestMapping("newsDetail.do")
	public String news_detail(Model model,int no,HttpServletRequest request)
	{
		NewsVO vo=dao.newsDetailData(no);
		List<NewsReviewVO> rlist=dao.newsReviewData(no);
		int newsReviewTotal=dao.newsTotalReview(no);
		
		StringTokenizer st=new StringTokenizer(vo.getContent(),".");
		vo.setContent("<p>"+vo.getContent()+"</p>");
		/*System.out.println(vo.getContent());*/
		model.addAttribute("vo",vo);

		model.addAttribute("rlist",rlist);
		model.addAttribute("newsReviewTotal",newsReviewTotal);
		return "project/news/newsDetail";
	}
	
	@RequestMapping("newsGrid.do")
	public String news_grid(Model model,String page)
	{
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		int totalpage=dao.newsTotalGridPage();
		//한 페이지당 가져오는 데이터수
		int rowSize=18;
		int start=(rowSize*curpage)-(rowSize-1);
		int end=(rowSize*curpage);
		
		//블록 페이징
		int block=5;
		int startblock;
		int endblock;
		if(curpage%block==0){
			startblock=(curpage/block)*block-(block-1);
			endblock=(curpage/block)*block;
		}
		else{
			startblock=(curpage/block)*block+1;
			endblock=(curpage/block+1)*block;
		}
		
		HashMap map=new HashMap();
		
		map.put("start", start);
		map.put("end", end);
		
		List<NewsVO> list=dao.newsGirdListData(map);
		int count=dao.newsCount();
		
		for(NewsVO vo:list)
		{
			String temp=vo.getSubject();
			temp=temp.substring(0, 45);
			
			String temp2=vo.getTitle();
			if(temp2.length()>15){
				temp2=temp2.substring(0,15);
				vo.setTitle(temp2+"...");
			}
			vo.setSubject(temp+"...");
		}
		
		model.addAttribute("count",count);
		model.addAttribute("startblock",startblock);
		model.addAttribute("endblock",endblock);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("list",list);
		return "project/news/newsGrid";
	}
	
	@RequestMapping("newsReview.do")
	public String news_review(NewsReviewVO vo,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		String email=(String)session.getAttribute("email");
		vo.setEmail(email);
		dao.newsReviewInsert(vo);
		return "redirect:newsDetail.do?no="+vo.getNews_no();
	}
	
	/*@RequestMapping("newsSearch.do")
	public String news_search(int page){
		String result="";
		List<NewsVO> list=dao.newsListData(map);
		return result;
	}*/
	
	@RequestMapping("newsReviewUpdate.do")
	public String news_reply_update(NewsReviewVO vo)
	{
		dao.newsReviewUpdate(vo);
		
		return "redirect:newsDetail.do?no="+vo.getNews_no();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping("newsReplyReplyInsert.do")
	public String news_reply_reply_insert(NewsReviewVO vo,int pno,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		String email=(String)session.getAttribute("email");
		
		NewsReviewVO rvo=dao.newsReplyReplySelect(pno);
		dao.newsGroupstepIncrement(rvo);
		
		vo.setEmail(email);
		vo.setGroup_id(rvo.getGroup_id());
		vo.setGroup_step(rvo.getGroup_step()+1);
		vo.setGroup_tab(rvo.getGroup_tab()+1);
		vo.setRoot(pno);
		
		dao.newsReplyReplyDepthIncrement(pno);
		dao.newsReplyReplyInsert(vo);
		
		return "redirect:newsDetail.do?no="+vo.getNews_no();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping("newsReplyDelete.do")
	public String news_reply_reply_delete(int pno)
	{
		System.out.println("pno: "+pno);
		NewsReviewVO vo=dao.newsReplyReplySelect(pno);
		System.out.println("no :"+vo.getNo());
		System.out.println("depth: "+vo.getDepth());
		if(vo.getDepth()==0)
		{
			dao.newsReplyReplyDelete(pno);
			dao.newsReplyDepthDecrement(vo.getRoot());
		}
		else
		{
			vo.setNo(pno);
			vo.setMsg("관리자가 삭제한 댓글입니다.");
			dao.newsReplyReplyDeleteMsg(vo);
		}
		
		return "redirect:newsDetail.do?no="+vo.getNews_no();
	}
}
