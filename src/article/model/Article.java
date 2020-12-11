package article.model;

import java.util.Date;

public class Article { // 작성자 정보를 보관, 구현해놓은 Writer 클래스 타입의 필드 정의
	
	private Integer number;
	private Writer writer;
	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;
	
	public Article(Integer number, Writer writer, String title, 
			Date regDate, Date modifiedDate, int readCount) {
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
		
	}

	public Integer getNumber() { // 테이블의 article_no
		return number;
	}

	public Writer getWriter() { // 테이블의 writer_id, writer_name
		return writer;
	}

	public String getTitle() { // 테이블의 tite
		return title;
	}

	public Date getRegDate() { // regdate
		return regDate;
	}

	public Date getModifiedDate() { // moddate
		return modifiedDate;
	}

	public int getReadCount() {//  read_cnt
		return readCount;
	}
					
}
