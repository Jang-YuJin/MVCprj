package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductDAO {
	private PreparedStatement pstmt;
	private Connection con;
	private DataSource dataFactory;
	
	public ProductDAO() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/banana");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public List<ProductDTO> selectAllProductList(){ //product테이블의 모든 레코드를 list에 저장
		List<ProductDTO> list = new ArrayList<>();
		
		try {
			con = dataFactory.getConnection();
			
			String query = "select * from tb_product1";
			pstmt = con.prepareStatement(query);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setCode(result.getString("code"));
				dto.setName(result.getString("name"));
				dto.setPrice(result.getInt("price"));
				dto.setRepresentative(result.getString("representative"));
				dto.setDetails(result.getString("details"));
				list.add(dto);
			}
			if(result != null) {
				result.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<ProductDTO> selectProductList(String code){ //product테이블의 특정 코드에 해당하는 레코드를 list에 저장
		List<ProductDTO> list = new ArrayList<>();
		System.out.println(code);
		try {
			con = dataFactory.getConnection();
			
			String query = "select * from tb_product1 where code like '%" + code + "%'";
			pstmt = con.prepareStatement(query);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setCode(result.getString("code"));
				dto.setName(result.getString("name"));
				dto.setPrice(result.getInt("price"));
				dto.setRepresentative(result.getString("representative"));
				dto.setDetails(result.getString("details"));
				list.add(dto);
			}
			if(result != null) {
				result.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ProductDTO> selectProductList(String code, int nowPage){ //product테이블 페이징에 필요한 list
		List<ProductDTO> list = new ArrayList<>();
		int offset = 1 + (nowPage - 1) * 12;
		int end = nowPage * 12;
		
		try {
			con = dataFactory.getConnection();
			String query = "select * from ("
					+ "    select dense_rank() over(order by v.num desc) as rnk, v.*"
					+ "    from v_product1 v where code like '%" + code + "%')"
					+ "    where rnk between ? and ?";//TODO: rownum으로 쿼리 바꾸기
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, end);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setCode(result.getString("code"));
				dto.setName(result.getString("name"));
				dto.setPrice(result.getInt("price"));
				dto.setRepresentative(result.getString("representative"));
				dto.setDetails(result.getString("details"));
				list.add(dto);
			}
			if(result != null) {
				result.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	
	
	
//	public int setMemberInfo(MemberDTO dto) { //member테이블에 저장
//		int result = -10;
//		try {
//			con = dataFactory.getConnection();
//			String query = "insert into tb_member1(id, password, name, tel, address, birth)"
//					+ " values (?, ?, ?, ?, ?, ?)";
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, dto.getId());
//			pstmt.setString(2, dto.getPassword());
//			pstmt.setString(3, dto.getName());
//			pstmt.setString(4, dto.getTel());
//			pstmt.setString(5, dto.getAddress());
//			pstmt.setDate(6, dto.getBirth());
//			result = pstmt.executeUpdate();
//			if(pstmt != null) {
//				pstmt.close();
//			}
//			if(con != null) {
//				con.close();
//			}
//			System.out.println(result);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	public boolean isIdUnique(String id) {//중복ID 체크
//		String idC = "";
//		boolean isDuplId = false; //id 중복 체크 변수, 중복이면 true
//		try {
//			con = dataFactory.getConnection();
//			
//			String query = "select * from tb_member1 where id = '" + id + "'";
//			pstmt = con.prepareStatement(query);
//			ResultSet result = pstmt.executeQuery();
//			while(result.next()) {
//				idC = result.getString("id");
//			}
//			
//			if(idC.equals(id)) {
//				isDuplId = true;
//			}
//			
//			if(result != null) {
//				result.close();
//			}
//			if(pstmt != null) {
//				pstmt.close();
//			}
//			if(con != null) {
//				con.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return isDuplId;
//	}
//	
//	public List<ReviewDTO> getReviewList(String code){ //상품코드에 해당하는 tb_review의 모든 레코드를 list에 저장
//		List<ReviewDTO> list = new ArrayList<>();
//		
//		try {
//			con = dataFactory.getConnection();
//			
//			String query = "select * from tb_review1 where code like '" + code + "'";
//			pstmt = con.prepareStatement(query);
//			ResultSet result = pstmt.executeQuery();
//			while(result.next()) {
//				ReviewDTO dto = new ReviewDTO();
//				dto.setReview_id(result.getString("review_id"));
//				dto.setId(result.getString("id"));
//				dto.setCode(result.getString("code"));
//				dto.setTitle(result.getString("title"));
//				dto.setContent(result.getString("content"));
//				dto.setWrite_date(result.getDate("write_date"));
//				list.add(dto);
//			}
//			if(result != null) {
//				result.close();
//			}
//			if(pstmt != null) {
//				pstmt.close();
//			}
//			if(con != null) {
//				con.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
//	
//	public List<ReviewDTO> getReviewList(String code, int nowPage){ //review테이블 페이징에 필요한 list
//		List<ReviewDTO> list = new ArrayList<>();
//		int offset = 1 + (nowPage - 1) * 5;
//		int end = nowPage * 5;
//		
//		try {
//			con = dataFactory.getConnection();
////			
////			String query = "select * from (select rownum, r.* from tb_review1 r where code like '" + code + "')"
////					+ " where rownum >= ?"
////					+ " and rownum <= ?";
//			String query = "select * from ("
//					+ "    select dense_rank() over(order by v.num desc) as rnk, v.*"
//					+ "    from v_review1 v where code like '" + code + "')"
//					+ "    where rnk between ? and ?";
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, offset);
//			pstmt.setInt(2, end);
//			ResultSet result = pstmt.executeQuery();
//			while(result.next()) {
//				ReviewDTO dto = new ReviewDTO();
//				dto.setReview_id(result.getString("review_id"));
//				dto.setId(result.getString("id"));
//				dto.setCode(result.getString("code"));
//				dto.setTitle(result.getString("title"));
//				dto.setContent(result.getString("content"));
//				dto.setWrite_date(result.getDate("write_date"));
//				list.add(dto);
//			}
//			if(result != null) {
//				result.close();
//			}
//			if(pstmt != null) {
//				pstmt.close();
//			}
//			if(con != null) {
//				con.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
}
