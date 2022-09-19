package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductView {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();
	    
	    public void mainMenu() {
	        
	        while(true) {
	            
	            System.out.println("\n***** 제품 관리 프로그램 *****");
	            System.out.println();
	            
	            System.out.println("1. 상품 전체 조회하기");
	            System.out.println("2. 상품 추가하기");
	            System.out.println("3. 상품명 검색하기");
	            System.out.println("4. 상품 정보 수정하기");
	            System.out.println("5. 상품 삭제하기");
	            System.out.println("0. 프로그램 종료");
	            System.out.println("-----------------------------");
	            System.out.print("메뉴 입력 : ");
	            int menu = sc.nextInt();
	            sc.nextLine();
	            System.out.println();
	            
	            switch(menu) {
	            case 1 : selectAll(); break;
	            case 2 : insertProduct(); break;
	            case 3 : selectByProductName(); break;
	            case 4 : updateProduct(); break;
	            case 5 : deleteProduct(); break;
	            case 0 : System.out.println("프로그램을 종료합니다."); return;
	            default : System.out.println("없는 메뉴입니다. 다시 입력해주세요.");
	            }
	        }
	    }

			 /**
		     * 등록된 상품 전체 목록을 조회하는 화면
		     */
		    public void selectAll() {
		        
		        System.out.println("=== 상품 전체 조회 ===");
		        pc.selectAll(); // 전체 조회는 따로 입력받을 값이 없으므로 바로 Controller 호출
		    }
		
		    /**
	         * 관리자(본사)에서 상품을 추가하는 메소드
	         */
	        public void insertProduct() {
	            
	        System.out.println("상품을 등록");
	        System.out.print("상품 아이디:");
	        String productId = sc.nextLine();
	            
	        System.out.print("상품명:");
	        String productName = sc.nextLine();
	        
	        System.out.print("상품가격:");
	        int price = sc.nextInt();
	        sc.nextLine();
	        System.out.print("상세정보:");
	        String description =sc.nextLine();
	        
	        System.out.print("재고량:");
	        int Stock = sc.nextInt();
	        
	        
	        pc.insertProduct(productId,productName,price,description,Stock);
	        
	        }
	        
		// 상품명 검색(상품 이름으로 키워드 검색)
		public void selectByProductName() {
			
		}
		
		/**
		 * 사용자에게 상품ID, 변경할 정보들(가격, 사양, 재고)를 입력받은 후
		 * 변경을 요청하는 화면
		 */
		public void updateProduct() {
			
			Product p = new Product();
			
			System.out.println("--- 상품 정보를 수정합니다. ---");
			System.out.print("수정할 상품 ID: ");
			String productId = sc.nextLine();
			
			System.out.println("--- 상품ID '" + productId + "' 에 대한 정보를 수정합니다. ---");
			
			System.out.print("상품명: ");
			String productName = sc.nextLine();
			System.out.print("변동 가격: ");
			int price = sc.nextInt();
			sc.nextLine();
			System.out.print("변동 사양: ");
			String description = sc.nextLine();
			System.out.print("변동 재고: ");
			int stock = sc.nextInt();
			
			
			pc.updateProduct(productId, productName, price, description, stock);
			
		}
		
		// 상품 삭제(상품 id로 조회해서 삭제)
		public void deleteProduct() {
			
		}
		
		//-----------------서비스 성공or 실패시 사용하는 메소드---------------------------
        public void processSuccess(String message) {
            System.out.println("서비스 요청 성공! " + message);
            
        }
        public void processFail(String message) {
            System.out.println("서비스 요청 실패! " + message);
            
        }
        
        public void displayNodata(String message) {
        	System.out.println(message);
        }
        
        public void displayList(ArrayList<Product> list) {
        	System.out.println("조회된 결과는 다음과 같습니다.");
        	for(int i = 0; i < list.size(); i++) {
        		System.out.println(list.get(i));
        	}
        }
        
        

}
