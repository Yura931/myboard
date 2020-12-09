package member.service;

import java.util.Map;

public class JoinRequest { // Member DTO객체와 유사, 회원가입을 위한 DTO라고 볼 수 있다.

	private String id;  
	private String name;
	private String password;
	private String confirmPassword;
	
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	public void validate(Map<String, Boolean> errors) {
		// id값이 잘 들어왔는지?
		checkEmpty(errors, id, "id");
		// name 잘 들어왔는지?
		checkEmpty(errors, name, "name");
		// password 잘 들어왔는지?
		checkEmpty(errors, password, "password");
		// confirmpassword 잘 들어왔는지? 검사 예정
		checkEmpty(errors, confirmPassword, "confirmPassword");
		// 결과를 어딘가에 보여주어야 하는데 , 그 결과를 어딘가에 담기로 결정, 어디에? 파라미터에 넘어온 Map 객체에 담기로 결정, errors 문제가 있을 때에만 Map에 담기로 , 어떤 항목에 대해 문제가 있는지 없는지 에러 체크
		
		if (!errors.containsKey("confirmPassword")) { // confirmPassword이름으로 에러가 등록되지 않았을 때
			if (!isPasswordEqualToConfirm()) { 
				errors.put("notMatch", true);
			}
		}
	
	}
	
	public boolean isPasswordEqualToConfirm() {
		return password != null && password.contentEquals(confirmPassword); // password와 confirmPassword가 같은지 확인하는 코드, 값이 없으면 nullpointException이 날 수 있기 때문에 null인지 아닌지부터 확인
	}
	
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) { // value가 잘 있는지 확인
		if (value == null || value.isEmpty()) { // value가 null이거나 비어있으면 문제가 되는 것, checkEmpty 메소드도 errors에 담기를 원함, checkEmpty메소드에도 Map객체 추가, 문제가 되면 Map객체인 errors에 put메소드를 통해 내용 추가
			errors.put(fieldName,  true); // value가 비어있으면 fieldName 이름으로 문제가 있다고 맵에 넣어둔 것, 
		}
	}
	
}
