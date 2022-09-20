package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Member;
import com.kh.model.vo.Product;

public class ProductService {
	
	private ProductDao pd = new ProductDao();
	
	// 관리자, 담당자 공통 ----------------------------------------
	/**
	 * 로그인 요청시 사용자를 조회할 SELECT 문을 실행할 때 필요한 Connection 객체 생성 및 반납
	 * @param id ~ pwd
	 * @return 조회한 사용자 결과
	 */
	public Member login(String id, String pwd) {
		
		// conn 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// conn 객체와 입력받은 id, pwd 를 매개변수로 넘기고, Dao 결과를 Member 로 받음
		Member m = pd.login(conn, id, pwd);
		
		// conn 객체 반납
		JDBCTemplate.close(conn);
		
		// 조회된 Member 리턴
		return m;
	}
	
	/**
	 * 상품 전체 목록을 조회할 SELECT 문을 실행할 때 필요한 Connection 객체를 생성하고,
	 * 사용 후 반납해주는 메소드
	 * @return 상품 전체 목록 list
	 */
	public ArrayList<Product> selectAll() {
		
		// Connection 객체 생성, Template 클래스에 따로 만들어둔 메소드를 통해서
		Connection conn = JDBCTemplate.getConnection();
		
		// conn 객체를 매개변수로 넘겨주고, Dao 결과를 ArrayList 로 받음 
		ArrayList<Product> list = pd.selectAll(conn);
		
		// Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 조회 결과 list 리턴
		return list;
	}
	
	/**
	 * 상품명 검색 조회를 실행할 때 필요한 Connection 객체 생성해주는 메소드
	 * @param keyword
	 * @return
	 */
	public ArrayList<Product> selectByProductName(String keyword){
		
		// Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// Dao로 전달(생성한 conn과 keyword)
		ArrayList<Product> list = pd.selectByProductName(conn, keyword);
		
		// 트랜잭션 처리할 거 없음
		JDBCTemplate.close(conn);
		
		// 결과 반환
		return list;
	}
	
	// 관리자용 ----------------------------------------
	
	/**
	 * 상품 추가를 위한 insert 문 실행하기 위해 Connection 객체 생성해주는 메소드
	 * @param p
	 * @return
	 */
	public int insertProduct(Product p) {

		int result = 0;
		Connection conn = null;
		conn = JDBCTemplate.getConnection();
		result = pd.insertProduct(conn, p);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		return result;
	}

	/**
	 * 상품 정보 수정을 위한 update 구문을 실행할 수 있도록 Connection 객체를 생성해주는 메소드
	 * @param p: 수정할 상품의 정보
	 * @return: 수정된 행의 개수
	 */
	public int updateProduct(Product p) {
		
		// 1. Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. Dao 메소드 호출(conn, keyword)
		int result = pd.updateProduct(conn, p);
		
		// 3. 트랜잭션 처리
		if(result > 0) { // 성공
			JDBCTemplate.commit(conn);
		} else { // 실패
			JDBCTemplate.rollback(conn);
		}
		
		// 4. Connection 반납
		JDBCTemplate.close(conn);
		
		// 5. 결과 반환(Controller로)
		return result;
		
	}

	/**
	 * 상품 삭제를 위한 deletd 문 실행하기 위해 Connection 객체 생성해주는 메소드
	 * @param productId
	 * @return
	 */
	public int deleteProduct(String productId) {
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO에 메소드 호출
		int result = pd.deleteProduct(conn, productId);
		
		// 3) 트랜잭션 처리
		if(result > 0 ) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
        
		// 4) Connection 자원 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과값 반환
		return result;
	}
	
	public int deleteMember(String userId) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출
		int result = pd.deleteMember(conn, userId);
		
		// 3) 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4) 자원 반납 
		JDBCTemplate.close(conn);
		
		// 5) 결과값 반환
		return result;
		
	}
	
	// 담당자용 ----------------------------------------
	
	
	

}
