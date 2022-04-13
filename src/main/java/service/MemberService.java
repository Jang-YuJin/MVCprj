package service;

import model.MemberDAO;
import model.MemberDTO;

public class MemberService {
	private MemberDAO dao;
	
	public MemberService() {
		dao = new MemberDAO();
	}
	
	public int setMemberInfo(MemberDTO memberDTO) {
		int result = dao.insertMemberInfo(memberDTO);
		
		return result;
	}
	
	public boolean isIdUnique(String id) {//중복ID 체크
		boolean isDuplId = false; //id 중복 체크 변수, 중복이면 true
		
		if(dao.selectID(id) != null) {
			isDuplId = true;
		}	
		
		return isDuplId;
	}
}
