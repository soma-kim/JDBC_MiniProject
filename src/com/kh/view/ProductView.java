package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Member;
import com.kh.model.vo.Product;

public class ProductView {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();
	
	/**
	 * 관리자 또는 사용자 로그인 화면
	 */
	public void login() {
		
		// 로그인 시도 횟수 체크를 위한 count 변수 선언 및 1로 초기화
		int count = 1;

		// 로그인 시도 5번 반복 (5회 실패 시 프로그램 종료)
		for(int i = 0; i < 5; i++) {

			System.out.println("\n***** 로 그 인 *****");
			System.out.print("아이디 : ");
			String id = sc.nextLine();
			System.out.print("비밀번호 : ");
			String pwd = sc.nextLine();
			
			// 입력받은 id 가 Member 에 있는지, 있으면 pwd 가 일치하는지 확인
			pc.login(id, pwd);
			
			// 만약 각 메인 메뉴 화면이 실행되지 않고 여기로 돌아왔다면
			// 조회 결과가 null이거나, 비밀번호가 틀리는 등 로그인에 실패한 경우
			if(1 <= count && count < 5) { // count 가 1 이상 5 미만인 경우
				System.out.println("로그인 실패. 다시 입력해주세요 (" + count + "/5)"); // 로그인 실패 및 횟수 안내
				count++; // 코드 실행 후 count 1 증가
			}
			else if(count == 5) { // count == 5 가 되는 경우
				System.out.println("로그인 5회 실패. 프로그램을 종료합니다."); return;
			}
		}
	}

	/**
	 * 관리자용 메인 메뉴 화면
	 */
	public void adminMenu(String id) {
		
		while(true) {
			
			System.out.println("\n***** 제품 관리 프로그램 (관리자용) *****");
			System.out.println();
			
			System.out.println("1. 상품 전체 조회하기");
			System.out.println("2. 상품 추가하기");
			System.out.println("3. 상품명 검색하기");
			System.out.println("4. 상품 정보 수정하기");
			System.out.println("5. 상품 삭제하기");
			System.out.println("6. 상품별 담당자 검색하기");
			System.out.println("7. 담당자 계정 삭제하기");			
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
			case 6 : selectByseller(); break;
			case 7 : deleteMember(); break;
			case 0 : System.out.println("프로그램을 종료합니다."); System.exit(0); // return 할 경우 login 메소드에서 로그인 검사를 하기 때문에 프로그램 바로 종료시킴
			default : System.out.println("없는 메뉴입니다. 다시 입력해주세요.");
			}
		}
	}
	
	/**
	 * 담당자용 메인 메뉴 화면
	 */
	public void userMenu(String id) {
		
		while(true) {
			
			System.out.println("\n***** 제품 관리 프로그램 (담당자용) *****");
			System.out.println();
			
			System.out.println("1. 상품 전체 조회하기");
			System.out.println("2. 상품명 검색하기");
			System.out.println("3. 담당 상품 검색");
			System.out.println("4. 상품 재고 수정하기"); 
			System.out.println("0. 프로그램 종료");
			System.out.println("-----------------------------");
			System.out.print("메뉴 입력 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			System.out.println();
			
			switch(menu) {
			case 1 : selectAll(); break;
			case 2 : selectByProductName(); break;
			case 3 : selectByUserId(id); break;
			case 4 : updateByProduct(); break;
			case 0 : System.out.println("프로그램을 종료합니다."); System.exit(0);
			default : System.out.println("없는 메뉴입니다. 다시 입력해주세요.");
			}
		}
	}
	
	// 관리자, 담당자 공통 메뉴들 ----------------------------------------
	
	/**
	 * 등록된 상품 전체 목록을 조회하는 화면
	 */
	public void selectAll() {
		
		System.out.println("=== 상품 전체 조회 ===");
		pc.selectAll(); // 전체 조회는 따로 입력받을 값이 없으므로 바로 Controller 호출
	}
	
	/**
	 * 검색할 상품명에 대한 메소드
	 * Controller로 전달
	 */
	public void selectByProductName() {
		
		System.out.println("=== 상품명 검색 ===");
		System.out.print("검색할 상품명 : ");
		String keyword = sc.nextLine();
		
		pc.selectByProductName(keyword);
	}
	
	
	// 관리자용 메뉴들 ----------------------------------------
	
	/**
	 * 관리자(본사)에서 상품을 추가하는 메소드
	 */
	public void insertProduct() {

		System.out.println("=== 상품 등록===");
		System.out.print("상품 아이디 : ");
		String productId;
		while (true) {
			productId = sc.nextLine();
			if ((productId.length()) >= 1) {
				break;
			} else {
				System.out.println("※ 상품 아이디는 1자 이상으로 등록하세요.");
			}
		}

		System.out.print("상품명 : ");
		String productName;
		while (true) {
			productName = sc.nextLine();
			if ((productName.length()) >= 1) {
				break;
			} else {
				System.out.println("※ 상품명은 1자 이상으로 등록하세요.");
			}
		}

		System.out.print("상품 가격 : ");
		int price;
		while (true) {
			price = sc.nextInt();
			if (price != 0) {
				break;
			} else {
				System.out.println("※ 상품 가격은 0일 수 없습니다.");//가격을 0으로 판매할 수 없으므로 필수로 넘어가지 않는다.
			}
		}

		sc.nextLine();
		System.out.print("상품 상세정보 : ");
		String description = sc.nextLine();
		System.out.print("재고 : ");
		int stock = sc.nextInt();

		pc.insertProduct(productId, productName, price, description, stock);

	}
	
	/**
	 * 사용자(관리자)에게 상품ID, 변경할 정보들(가격, 사양, 재고)를 입력받은 후
	 * 변경을 요청하는 화면
	 */
	public void updateProduct() {
	
		System.out.println("=== 상품 정보 수정 ===");
		System.out.print("수정할 상품 아이디 : ");
		String productId = sc.nextLine();
		
		System.out.println("--- 상품 아이디 '" + productId + "' 에 대한 정보를 수정합니다. ---");
		
		System.out.print("상품명 : ");
		String productName = sc.nextLine();
		System.out.print("변동 가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("변동 상세정보 : ");
		String description = sc.nextLine();
		System.out.print("변동 재고 : ");
		int stock = sc.nextInt();
		
		
		pc.updateProduct(productId, productName, price, description, stock);
		
	}
	
	/**
	 * 상품 아이디를 입력받아 상품 삭제를 요청하는 화면
	 */
	public void deleteProduct() {
		System.out.println("=== 상품 삭제 ===");
	
		System.out.print("삭제할 상품 아이디 : ");
		String productId = sc.nextLine();
	
		pc.deleteProduct(productId);
	}
	
	/**
	 * 회원 아이디를 입력받아 회원 삭제를 요청하는 화면
	 */
	public void deleteMember() {
		System.out.println("=== 계정 삭제 ===");
		
		System.out.print("삭제할 계정 아이디 : ");
		String userId = sc.nextLine();
		
		pc.deleteMember(userId);
		
	}	
	
	/**
	 * 상품 아이디를 입력받아 담당자를 조회하는 화면
	 */
	public void selectByseller() {
		System.out.println("=== 영업 담당자 조회 ===");
	
		System.out.print("조회할 상품 아이디 : ");
		String productId = sc.nextLine();
		
		pc.selectByseller(productId);
		
		
	}

	// 담당자용 ----------------------------------------
	
	/**
	 * 상품 재고 수정
	 */
	public void updateByProduct() {
		
		System.out.println("=== 상품 재고 수정 ===");
		System.out.print("수정할 상품 아이디 : ");
		String productId = sc.nextLine();

		System.out.print("수정할 재고 입력 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		pc.updateByProduct(productId, stock);
	}

	/**
	 * 담당 상품 검색 메소드
	 * Controller로 전달
	 */
	public void selectByUserId(String id) {
		
		pc.selectByUserId(id);
	}
	
	
	// 처리 결과 화면들 ----------------------------------------
	
	public void displaySuccess(String message) {
		
		System.out.println("성공 : " + message);
	}
	
	public void displayFail(String message) {
		
		System.out.println("실패 : " + message);
	}
	
	public void displayList(ArrayList<Product> list) {
		
		System.out.println("조회된 결과는 " + list.size() + " 개 입니다.");
		for(Product p : list) {
			System.out.println(p);
		}
	}
	
	public void displayOne(Product p) {
		
		System.out.println(p);
	}
	
	public void displayNodata(String message) {
		
		System.out.println(message);
	}
	
	// 담당자 아이디 조회 결과를 위한 별도의 Member List 출력 화면
	public void displayIdList(ArrayList<Member> list) {
		
		System.out.println("조회된 결과는 " + list.size() + " 개 입니다.");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getUserId());
		}
	}


}
