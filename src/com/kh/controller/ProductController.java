package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductView;

public class ProductController {
	
	ProductService ps = new ProductService();
	
	/**
     * 상품 전체 조회 요청을 처리해주는 메소드
     */
    public void selectAll() {
        
        // Service 결과를 Product 만 담을 수 있는 ArrayList 로 받기
        ArrayList<Product> list = ps.selectAll();
        
        if(list.isEmpty()) { // 만약 list 가 비었다면 (조회된 결과가 없다면)
            new ProductView().displayNodata("현재 등록된 상품이 없습니다.");
        }
        else { // 조회된 결과가 있다면
            new ProductView().displayList(list);
        }
    }
	
    public int insertProduct(String productId, String productName, int price, String description, int stock) {
        
    	int result = 0;
        Product p = new Product();
        
        p.setProductId(productId);
        p.setProductName(productName);
        p.setPrice(price);
        p.setDescription(description);
        p.setStock(stock);
        
        result = new ProductService().insertProduct(p);
        
        if (result == 1) {
            new ProductView().processSuccess("새로운 상품이 등록되었습니다.");
        } else {
            new ProductView().processFail("상품등록에 실패했습니다.다시 확인하세요.");
        }
        return result;
    }

	
	public void selectByProductName(String keyword) {
		
		// 1. 전달 받은 값이 한 개이기 때문에 가공할 내용이 없음
		
		// 2. Service로 전달 후 list로 결과 값 반환
		ArrayList<Product> list = ps.selectByProductName(keyword);
		
		// 3. 결과에 따른 응답 화면 지정
		if(list.isEmpty()) { // 비어있다면 (실패)
			
			new ProductView().processFail("검색된 결과가 없습니다.");
		}
		else { // 비어있지 않다면(성공)
	
			new ProductView().displayList(list);
		}
	}
	
	
	/**
	 * 사용자가 상품 정보 변경 요청 시 처리해 주는 메소드 
	 * @param productId: 변경하고자 하는 상품의 아이디 (구분용)
	 * @param productName ~ stock:변경할 회원 정보들(가격, 사양, 재고)
	 */
	public void updateProduct(String productId, String productName, int price, String description, int stock) {
		
		// 1. 요청 시 전달값들을 VO 객체로 가공하기
		Product p = new Product();
		
		p.setProductId(productId);
		p.setProductName(productName);
		p.setPrice(price);
		p.setDescription(description);
		p.setStock(stock);
		
		// 2. 전달값을 Dao의 메소드로 넘기기
		// 3. 결과 받기
		int result = new ProductService().updateProduct(p);
		
		// 4. 결과에 따른 응답화면을 지정
		if(result > 0) { // 수정 성공
			new ProductView().processSuccess("제품 정보 변경 성공");
		} else { // 수정 실패
			new ProductView().processFail("제품 정보 변경 실패");
		} 
		
	}
	
	// 회원 탈퇴를 요청 시 처리해주는 메소드
		public void deleteProduct(String productId) {
			
			// 2) Service에 메소드를 호출
			int result = new ProductService().deleteProduct(productId);
			
			// 3) 성공 / 실패에 따른 응답뷰 호출
			if(result > 0){
				new ProductView().processSuccess("해당 상품의 삭제가 성공하였습니다.");
			}else {
				new ProductView().processFail("해당 상품이 존재하지 않습니다.");
			}
		}
	
}
