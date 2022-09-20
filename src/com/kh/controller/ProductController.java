package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Member;
import com.kh.model.vo.Product;
import com.kh.view.ProductView;

public class ProductController {
	
	private ProductService ps = new ProductService();
	
	// 관리자, 담당자 공통 ----------------------------------------
	
	/**
	 * 로그인 요청을 처리해주는 메소드
	 * @param id ~ pwd : 로그인 요청을 위해 입력한 아이디와 비밀번호
	 */
	public void login(String id, String pwd) {
		
		// 입력받은 id, pwd 매개변수로 넘겨주고, Service 결과 Member 로 받음
		Member m = ps.login(id, pwd);
		
		// 일치하는 아이디가 없어 조회 결과가 null 인 경우
		// NullPointException 을 처리하기 위해 따로 조건식 작성
		// 다른 코드 수행 없이 View 로 돌아감
		if(m.getUserId() == null) { 
			return; 
		}
		// 입력받은 id, pwd 와 조회 결과의 userId, userPwd 가 모두 일치할 경우 (로그인 성공할 경우)
		else if(m.getUserId().equals(id) && m.getUserPwd().equals(pwd)) { 
			
			if(id.equals("admin")) { // id 가 admin 과 일치할 경우 (관리자일 경우)
				new ProductView().adminMenu(id); // 관리자용 메인 메뉴 화면
			}
			else { // 그렇지 않은 경우, 일치하는 값이 있는데 id 가 admin 은 아닌 경우 (담당자일 경우)
				new ProductView().userMenu(id); // 담당자용 메인 메뉴 화면
			}
		}
		// 그 외의 경우 (아이디는 맞으나 비밀번호가 틀린 경우도 로그인 시도 횟수에 포함시키기 위해 별도로 else문 작성)
		// 이 경우에도 다른 코드 수행 없이 View 로 돌아감
		else { 
			return;
		}
	}
	
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
	
	/**
	 * 상품명 키워드 검색 요청을 처리해주는 메소드
	 * @param keyword
	 */
	public void selectByProductName(String keyword) {
		
		// 1. 전달 받은 값이 한 개이기 때문에 가공할 내용이 없음
		
		// 2. Service로 전달 후 list로 결과 값 반환
		ArrayList<Product> list = ps.selectByProductName(keyword);
		
		// 3. 결과에 따른 응답 화면 지정
		if(list.isEmpty()) { // 비어있다면 (실패)
			
			new ProductView().displayNodata(keyword + "로 검색된 결과가 없습니다.");
		}
		else { // 비어있지 않다면(성공)
			
			new ProductView().displayList(list);
		}
	}
	
	// 관리자용 ----------------------------------------
	
	/**
	 * 상품 추가 요청을 처리해주는 메소드
	 * @param productId ~ stock : 추가할 입력값들 (상품 정보들)
	 * @return
	 */
	public int insertProduct(String productId, String productName, int price, String description, int stock) {
		
		int result = 0;
		Product p = new Product();

		p.setProductId(productId);
		p.setProductName(productName);
		p.setPrice(price);
		p.setDescription(description);
		p.setStock(stock);

		result = ps.insertProduct(p);

		if (result == 1) {
			new ProductView().displaySuccess("새로운 상품이 등록되었습니다.");
		} else {
			new ProductView().displayFail("상품 등록에 실패했습니다.");
		}
		return result;
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
		int result = ps.updateProduct(p);
		
		// 4. 결과에 따른 응답화면을 지정
		if(result > 0) { // 수정 성공
			new ProductView().displaySuccess("상품 정보가 변경되었습니다.");
		} else { // 수정 실패
			new ProductView().displayFail("상품 정보 변경에 실패했습니다.");
		} 
	}
	
	/**
	 * 상품 삭제를 요청 시 처리해주는 메소드
	 * @param productId
	 */
	public void deleteProduct(String productId) {
		
		// 2) Service에 메소드를 호출
		int result = ps.deleteProduct(productId);
		
		// 3) 성공 / 실패에 따른 응답뷰 호출
		if(result > 0){
			new ProductView().displaySuccess("상품이 삭제되었습니다.");
		}else {
			new ProductView().displayFail("상품 삭제에 실패했습니다.");
		}
	}
	
	/**
	 * 계정 삭제를 요청 시 처리해주는 메소드
	 * @param userId
	 */
	
	public void deleteMember(String userId) {
		
		// 1) Service 메소드를 호출하여 Id를 보내고 int형 변수로 받음
		int result = ps.deleteMember(userId);
		
		// 2) 성공/실패에 따른 응답 뷰 호출
		if(result > 0) {
			new ProductView().displaySuccess("계정이 삭제되었습니다.");
		} else {
			new ProductView().displayFail("계정 삭제에 실패했습니다.");
		}
		
	}
	
	// 담당자용 ----------------------------------------
	
	
	
}	
