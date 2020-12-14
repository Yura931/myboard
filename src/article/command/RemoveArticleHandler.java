package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.RemoveArticleService;
import mvc.command.CommandHandler;

public class RemoveArticleHandler implements CommandHandler {
	private static final String FORM_VIEW="removeArticleSuccess";
	RemoveArticleService removeArticleService = new RemoveArticleService();
	
	@Override
		public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
			if (req.getMethod().equalsIgnoreCase("GET")) {
				return processForm(req, res);
			} else if (req.getMethod().equalsIgnoreCase("POST")) {
				return processSubmit(req, res);
			} else {
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return null;
			}
		}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		removeArticleService.removeArticle(no);
		
		return FORM_VIEW;
			
		
	}
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
}

