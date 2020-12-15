package article.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;
import reply.model.Reply;
import reply.service.ReplyService;

public class ReadArticleHandler implements CommandHandler {
	private ReadArticleService readService = new ReadArticleService();
	private ReplyService replyService = new ReplyService();
	

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		
		try {
			ArticleData articleData = readService.getArticle(articleNum, true);
			List<Reply> replyList = replyService.getReplyList(articleNum); 
			req.setAttribute("articleData", articleData);
			req.setAttribute("replyList", replyList);
			
			return "readArticle";
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
//			req.getServletContext().log("no article", e);
			System.out.println("게시물이 없습니다.");
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
// articleData를 함께 넘겨주는 역할, 또는 어트리뷰트를 하나 더 만들어서 추가해도 됨
}
