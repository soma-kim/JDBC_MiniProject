package com.kh.view;

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
	    
	    // 상품 전체 조회
		public void selectAll() {
			
		}
		
		// 상품 추가
		public void insertProduct() {
			
		}
		// 상품명 검색(상품 이름으로 키워드 검색)
		public void selectByProductName() {
			
		}
		
		// 상품 정보 수정(상품 id로 조회하고 수정)
		public void updateProduct() {
			
			Product p = new Product();
			
			System.out.println("--- 상품 정보를 수정합니다. ---");
			System.out.print("수정할 상품 ID: ");
			String productId = sc.nextLine();
			System.out.println("--- 상품명 '" + productId + "' 에 대한 정보를 수정합니다. ---");
			System.out.print("변동 가격: ");
			int price = sc.nextInt();
			sc.nextLine();
			System.out.print("변동 사양: ");
			String description = sc.nextLine();
			System.out.print("변동 재고: ");
			int stock = sc.nextInt();
			
			
			p = pc.updateProduct(productId, price, description, stock);
			
		}
		
		// 상품 삭제(상품 id로 조회해서 삭제)
		public void deleteProduct() {
			
		}

}
