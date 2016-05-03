package question;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Answers")
public class Answers extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	private Database db;
		
    public Answers() {
        super();
        db = Database.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer ansID = 0;
		Vector<String> v = new Vector<String>();
		Question q = new Question ("INVALID",1);
		RequestDispatcher rd = null;
		
		String questionID = request.getParameter("id");
		try
		{
			ansID = Integer.parseInt(questionID);
			q = db.getSpecificQuestion(ansID);
		}
		catch (NullPointerException e1)
		{
			System.out.println("Its the thing");
		}
		
        
        	v = new Vector<String>();
			v = db.readAnswers(ansID);
		

    	rd = request.getRequestDispatcher("/ShowAnswers.jsp");
    	request.setAttribute("vector", v);
    	request.setAttribute("id", ansID);
    	request.setAttribute("question", q.getQuestion());
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Integer ansID = 0;

		String called =request.getParameter("param");
		response.setContentType("text/html");
		
		if ("newQ".equals(called))
		{
			ansID =Integer.parseInt(request.getParameter("id"));
			String text = request.getParameter("theAnswer");
	        
			
				db.addAnswer(text, ansID);
			
	        response.sendRedirect("Answers?id="+request.getParameter("id"));
		}
		else if("delete".equals(called))
		{
			
				ansID =Integer.parseInt(request.getParameter("id"));
				db.deleteQuestion(ansID);
				response.sendRedirect("ShowQuestions");
			
		}
		else if("back".equals(called))
		{
			response.sendRedirect("ShowQuestions");
		}
	}

}
