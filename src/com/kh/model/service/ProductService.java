package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {
	
	ProductDao pd = new ProductDao();
	
	/**
     * 상품 전체 목록을 조회할 SELECT 문을 실행할 때 필요한 Connection 객체를 생성하고,
     * 사용 후 반납해주는 메소드
     * @return 상품 전체 목록 list
     */
    public ArrayList<Product> selectAll() {
        
        // Connection 객체 생성, Template 클래스에 따로 만들어둔 메소드를 통해서
        Connection conn = JDBCTemplate.getConnection();
        
        // Dao 결과를 ArrayList 로 받고, conn 객체를 매개변수로 넘겨줌
        ArrayList<Product> list = pd.selectAll(conn);
        
        // Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 조회 결과 list 리턴
        return list;
    }

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
	
	public int deleteProduct(String productId) {
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO에 메소드 호출
		int result = new ProductDao().deleteProduct(conn,productId);
		
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

}
