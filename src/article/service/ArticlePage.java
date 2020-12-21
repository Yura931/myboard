package article.service;

import java.util.List;

import article.model.Article;

public class ArticlePage {

		private int total; // 전체 게시물 수
		private int currentPage; // 현재 페이지
		private List<Article> content; // 얻어온 데이터를 Article리스트에
		private int totalPages; // 총 페이지 수
		private int startPage; // 시작 페이지
		private int endPage; // 마지막 페이지
		
		public ArticlePage(int total, int currentPage, int size, List<Article> content) {
			this.total = total;
			this.currentPage = currentPage;
			this.content = content;
			
			if (total != 0) { // 게시물이 하나도 없으면 페이지 계산 할 필요 없음, 즉 0이 아닐 때만 일을 하면 됨
				this.totalPages = total / size;
				if (total % size > 0) { // 페이지 수가 딱 떨어지지 않았을 때 남아있는 짜투리 페이지를 넣어줄 자리 마련 해주어야 함
					this.totalPages++; 
				}
				
				this.startPage = (currentPage - 1) / 5 * 5 + 1;  
				this.endPage = Math.min(startPage + 4, totalPages);
			}
		}

		public int getTotal() {
			return total;
		}
		
		public boolean hasNoArticles() {
			return total == 0;
		}
		
		public boolean hasArticles() {
			return total > 0;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public List<Article> getContent() {
			return content;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public int getStartPage() {
			return startPage;
		}

		public int getEndPage() {
			return endPage;
		}
		
		
}
