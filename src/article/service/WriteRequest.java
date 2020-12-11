package article.service;

import java.util.Map;

import article.model.Writer;

public class WriteRequest { // 게시글 쓰기에 필요한 데이터 제공
	
	private Writer writer;
	private String title;
	private String content;
	
	public WriteRequest(Writer writer, String title, String content) { // 사용자 아이디와 이름을 가지고 있는 Writer객체, title, content 를 파라미터로 갖는 생성자)
		this.writer = writer;
		this.title = title;
		
	}

	public Writer getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public void validate(Map<String, Boolean> errors) { // 데이터가 유효한지 여부 검사
		if (title == null || title.trim().isEmpty()) { // title이 null이거나 비어있으면
			errors.put("title", Boolean.TRUE); // errors맵에 true put
		}
	}
}
