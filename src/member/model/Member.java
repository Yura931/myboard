package member.model;

import java.util.Date;

public class Member { // 테이블, 칼럼 테이블에서 가져온 데이터를 저장, 관리용 DTO객체 정의

	private String id; // 테이블 속성에 해당하는 필드를 정의, getter, setter 메서드 생성
	private String name;
	private String password;
	private Date regDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public boolean matchPassword(String pwd) { // 회원가입시 패스워드와 확인용 패스워드가 일치하는지 검사하는 메서드 정의
		return password.equals(pwd); // 필드 password와 파라미터로 받는 pwd가 같으면 true를 리턴
	}
	
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}
}
