package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;
import com.kh.model.vo.Product;

public class ProductDao {
	
	private Properties prop = new Properties();
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	// new 구문으로 Dao 클래스를 호출할 때마다 실행할 수 있는 기본 생성자
	// 쿼리문들이 작성된 xml 파일을 불러오기
	public ProductDao() {
		
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 관리자, 담당자 공통 ----------------------------------------
	
	/**
	 * 로그인 요청시 조건 검사를 위한 아이디, 비밀번호 조회용 SELECT 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param id ~ pwd : 조회하고자 하는 아이디와 비밀번호
	 * @return 조회된 사용자 결과
	 */
	public Member login(Connection conn, String id, String pwd) {
		
		// 결과값을 담을 Member 변수 선언
		Member m = new Member();
		
		// 실행할 sql문
		String sql = prop.getProperty("login");
		
		try {
			// 매개변수로 전달받은 conn 객체로 pstmt 객체 생성, sql문 전달
			pstmt = conn.prepareStatement(sql);
			
			// 미완성된 쿼리문 완성시키기
			pstmt.setString(1, id);
			
			// 조회 실행 및 rset 객체로 결과 받기
			rset = pstmt.executeQuery();
			
			// id 값은 unique 제약조건으로 인해 조회가 되어도 하나뿐이니 if문
			if(rset.next()) {
				
				// Member 변수 m 에 setter 메소드를 이용하여 값 대입
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 객체 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 조회된 Member 리턴
		return m;
	}
	
	/**
	 * 상품 전체 목록 조회 요청시 SELECT 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @return 테이블로부터 조회된 전체 상품의 정보를 담고 있는 ArrayList
	 */
	public ArrayList<Product> selectAll(Connection conn) {
		
		// 결과값을 담을 ArrayList 변수 선언
		ArrayList<Product> list = new ArrayList<>();
		
		// 실행할 sql문, xml 파일에서 key 값으로 불러옴
		String sql = prop.getProperty("selectAll");
		
		try {
			
			// conn 객체를 통해 PreparedStatement 객체 생성
			// 생성과 동시에 sql 넘겨주기
			pstmt = conn.prepareStatement(sql);
			
			// 위치홀더(채울 값)가 없으므로 
			// 바로 ResultSet 객체로 조회 결과 받기 
			rset = pstmt.executeQuery();
			
			// 조회 결과를 한 행씩 뽑아서
			while(rset.next()) {
				
				// VO 객체의 각 필드에 담아 list 에 추가
				list.add(new Product(rset.getString("PRODUCT_ID")
								   , rset.getString("PRODUCT_NAME")
								   , rset.getInt("PRICE")
								   , rset.getString("DESCRIPTION")
								   , rset.getInt("STOCK")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 객체 반납
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		
		// 조회된 list 리턴
		return list;
	}
	
	/**
	 * 상품명 키워드 검색 요청시 select 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param keyword
	 * @return
	 */
	public ArrayList<Product> selectByProductName(Connection conn, String keyword){
	
		// 필요한 객체 생성
		ArrayList<Product> list = new ArrayList<>();
		
		// 실행할 sql문 작성
		String sql = prop.getProperty("selectByProductName");
		
		try {
			
			// PreparedStatemtnt객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 미완성된 sql문 완성시키기
			pstmt.setString(1, "%" + keyword + "%");
			
			// ResultSet으로 결과 받기
			rset = pstmt.executeQuery();
			
			// ResultSet에 담긴 데이터 옮겨담기
			while(rset.next()) {
				
				// 한 행에 담긴 데이터들 Product타입의 객체로 옯겨담기
				list.add(new Product(rset.getString("PRODUCT_ID")
								   , rset.getString("PRODUCT_NAME")
								   , rset.getInt("PRICE")
								   , rset.getString("DESCRIPTION")
								   , rset.getInt("STOCK")));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			
			// 객체 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 결과 반환
		return list;
	}
	
	// 관리자용 ----------------------------------------
	
	/**
	 * 상품 추가 요청시 insert 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param p
	 * @return
	 */
	public int insertProduct(Connection conn, Product p) {
		
		int result = 0;

		String sql = prop.getProperty("insertProduct");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,p.getProductId());
			pstmt.setString(2,p.getProductName());
			pstmt.setInt(3,p.getPrice());
			pstmt.setString(4,p.getDescription());
			pstmt.setInt(5,p.getStock());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
	
	/**
	 * 상품 수정 요청시 update 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param p
	 * @return
	 */
	public int updateProduct(Connection conn, Product p) {
		
		// 1) 필요한 변수 세팅
 		int result = 0;
 		
 		// 2) SQL문 설정
 		String sql = prop.getProperty("updateProduct");
 		
 		try {
 	 		
 			// 맥북에서는 이 구문이 없으면 자동 커밋 설정된 채 롤백할 수 없다는
 			// 오류 메시지가 떠서 추가
 	 		// conn.setAutoCommit(false);
 			
 			// 3) sql문을 보낼 수 있는 PreparedStatement 객체 생성
 			// 전역 변수로 생성했으므로 바로 가지고 옴
			pstmt = conn.prepareStatement(sql);
			
			/*
			 * 실행할 sql 문
			  	UPDATE *
				SET PRODUCT_NAME = ?
				  , PRICE = ?
				  , DESCRIPTION = ?
				  , STOCK = ?
				WHERE PRODUCT_ID = ?
			 */
			
			// 3-1) 미완성 쿼리문 값 채워 넣기
			pstmt.setString(1, p.getProductName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setString(3, p.getDescription());
			pstmt.setInt(4, p.getStock());
			pstmt.setString(5, p.getProductId());
			
			// 4) SQL문 결과 담기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 5) 자원 반납(생성 역순)
			JDBCTemplate.close(pstmt);
		}
 	
 		// 6) 결과 반환
 		return result;
 		
 	}	
	
	/**
	 * 상품 삭제 요청시 delete 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param productId
	 * @return
	 */
	public int deleteProduct(Connection conn, String productId) { 
		// 필요한 변수 설정
		int result = 0;
		
		// SQL문 설정
		String sql = prop.getProperty("deleteProduct");
		
		try {
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 미완성된 쿼리문인 경우, 값채워넣기
			pstmt.setString(1, productId);
			
			// SQL문 결과 담기(처리된 행의 개수)
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	// 자원반납
			JDBCTemplate.close(pstmt);
		}
		
		// 결과값 반환
		return result;
	}
	
	/**
	 * 회원 삭제 요청 시 delete문을 실행해 요청을 처리해 주는 메소드
	 * @param conn
	 * @param userId
	 * @return
	 */
	public int deleteMember(Connection conn, String userId) {
		
		// 1) 필요한 변수 설정
		int result = 0;
		
		// 2) SQL문 설정
		String sql = prop.getProperty("deleteMember");
		
		try {
			
			// 3) sql문을 보낼 수 있는 PreparedStatement 객체 생성
			// 전역 변수로 생성했으므로 바로 가지고 옴
			pstmt = conn.prepareStatement(sql);
			
			// 3-1) 미완성 쿼리문 값 채워 넣기
			// 	DELETE FROM MEMBER WHERE USERID = ?
			pstmt.setString(1, userId);
			
			// 4) SQL문 결과 담기
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 5) 자원 반납
			JDBCTemplate.close(pstmt);
			
		}
		
		// 6) 결과 반환
		return result;
		
	}
	
	/**
	 * 
	 * 본사에서 상품별로 영업담당자를 조회하는 메소드
	 */
	public ArrayList<Member> selectByseller(Connection conn, String productId) {
		
		//필요한 변수 설정 담당자가 여러명일 수 있으니 빈 list 설정
		ArrayList<Member> list= new ArrayList<>();
		// SQL문 생성
		String sql = prop.getProperty("selectByseller");
		
		try {
			// PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			// 미완성된 쿼리문인 경우, 값채워넣기
			pstmt.setString(1, productId);
			
			rset=pstmt.executeQuery();
			
			//쿼리문에서 값 뽑아오기
			while(rset.next()) {
				Member m = new Member();
				m.setUserId(rset.getString("USERID"));
				list.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//자원 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
			
		}
		
		return list;
		
	}

	
	// 담당자용 ----------------------------------------
	
	public int updateByProduct(Connection conn, String productId, int stock) {
		
		// 객체 생성
		PreparedStatement pstmt = null;
		int result = 0;
		
		// sql문 작성
		String sql = prop.getProperty("updateByProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stock);
			pstmt.setString(2, productId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 객체 반납
			JDBCTemplate.close(pstmt);
		}
		
		// 결과 반환
		return result;
	}

	/**
	 * 담당 상품 검색 요청시 select 문을 실행해 요청을 처리해주는 메소드
	 * @param conn
	 * @param id
	 * @return
	 */
	public ArrayList<Product> selectByUserId(Connection conn, String id){
	
		// 필요한 객체 생성
		ArrayList<Product> list = new ArrayList<>();
		
		// 실행할 sql문 작성
		String sql = prop.getProperty("selectByUserId");
		
		try {
			// PreparedStatemtnt객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// sql문에 파라메터 삽입 시키기
			pstmt.setString(1,id);
			
			// ResultSet으로 결과 받기
			rset = pstmt.executeQuery();
			
			// ResultSet에 담긴 데이터 옮겨담기
			while(rset.next()) {			
				// 한 행에 담긴 데이터들 Product타입의 객체로 옯겨담기
				list.add(new Product(rset.getString("PRODUCT_ID")
								   , rset.getString("PRODUCT_NAME")
								   , rset.getInt("PRICE")
								   , rset.getString("DESCRIPTION")
								   , rset.getInt("STOCK")));			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			
			// 객체 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 결과 반환
		return list;
	}

}
