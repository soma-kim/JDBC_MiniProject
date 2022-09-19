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
import com.kh.model.vo.Product;

public class ProductDao {
	
	Properties prop = new Properties();
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	
	public ProductDao() {
		
			try {
				prop.loadFromXML(new FileInputStream("resources/query.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}

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
    
    public int insertProduct(Connection conn, Product p) {
    	
        int result = 0;
        
        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
    
 // 상품명 검색(상품 이름으로 키워드 검색)
 	public void selectByProductName() {
 		
 	}
 	
 	public int updateProduct(Connection conn, Product p) {
 		
 		int result = 0;
 		
 		String sql = prop.getProperty("updateProduct");
 		
 		try {
 	 		
 			// 맥북에서는 이 구문이 없으면 자동 커밋 설정된 채 롤백할 수 없다는
 			// 오류 메시지가 떠서 추가
 	 		conn.setAutoCommit(false);
 			
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
			
			pstmt.setString(1, p.getProductName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setString(3, p.getDescription());
			pstmt.setInt(4, p.getPrice());
			pstmt.setString(5, p.getProductId());
			
			result = pstmt.executeUpdate();
			
			// 트랜잭션 처리
			if(result > 0) { // 성공
				conn.commit();
			} else { // 실패
				conn.rollback();
			}
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 자원 반납(생성 역순)
			JDBCTemplate.close(pstmt);
		}
 	
 		// 결과 반환
 		return result;
 		
 	}
 	
 	// 상품 삭제(상품 id로 조회해서 삭제)
 	public void deleteProduct() {
 		
 	}

}
