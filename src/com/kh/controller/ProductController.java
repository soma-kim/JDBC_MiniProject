package com.kh.controller;

import com.kh.model.vo.Product;

public class ProductController {
	
	public void selectAll() {
		
	}
	
	public void insertProduct() {
		
	}
	
	public void selectByProductName() {
		
	}
	
	public void updateProduct(String productId, int price, String description, int stock) {
		
		// 1. 요청 시 전달값들을 VO 객체로 가공하기
		Product p = new Product();
		
		p.setProductId(productId);
		p.setPrice(price);
		p.setDescription(description);
		p.setStock(stock);
		
		// 2. 전달값을 Dao의 메소드로 넘기기
		// 3. 결과 받기
		int result = new ProductService().updateProduct(p);
		
		// 4. 결과에 따른 응답화면을 지정
		if(result > 0) { // 수정 성공
			new ProductView().displaySuccess("회원 정보 변경 성공");
		} else { // 수정 실패
			new ProductView().displayFail("회원 정보 변경 실패");
		} 
		
	}
	
	public void deleteProduct() {
		
	}
	
	

}
