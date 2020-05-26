package com.sist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sist.mapper.MemberMapper;
import com.sist.vo.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private MemberMapper mapper;
	
	public void signup(MemberVO vo)
	{
		mapper.join(vo);
	}
	
	public int emailCount(String email)
	{
		return mapper.emailCount(email);
	}
	
	public String emailGetPassword(String email)
	{
		return mapper.emailGetPassword(email);
	}
	
	public MemberVO profileData(String email)
	{
		return mapper.profileData(email);
	}
	
}
