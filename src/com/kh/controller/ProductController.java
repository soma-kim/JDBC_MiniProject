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

	
	public void selectByProductName() {
		
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
	
	public void deleteProduct() {
		
	}
	
	

}
