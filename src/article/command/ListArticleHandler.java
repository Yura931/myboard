package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler{
	
	private ListArticleService listService = new ListArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception { // form에서 넘어오는것이 아니고, 조회만 하는 것이기 때문에 post방식으로 올 일 없음, get방식, post방식 나눌 필요 없음
		String pageNoVal = req.getParameter("pageNo"); // page번호를 
		int pageNo = 1; // 기본값 1
		
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal); // 받은 값이 있으면 pageNoVal파라미터 값으로 변경 
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		req.setAttribute("articlePage", articlePage);
		return "listArticle";
	}
	
}
