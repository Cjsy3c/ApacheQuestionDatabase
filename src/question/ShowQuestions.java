package question;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Property of Cody Smith (CodySmith@mail.umkc.edu)


@WebServlet("/ShowQuestions")
public class ShowQuestions extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Database db;


	public ShowQuestions() {
		super();

		db = Database.getInstance();		// open database

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher rd = null;

		Vector<Question> v = db.readQuestions();

		rd = request.getRequestDispatcher("/ShowQuestions.jsp");
		request.setAttribute("viewModel", v);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String text = request.getParameter("theQuestion");
		String param = request.getParameter("param");
		if("newQ".equals(param))
		{
			db.addQuestion(text);
			
			response.sendRedirect("ShowQuestions");
		}
		else if ("json".equals(param))
		{
			response.sendRedirect("GetJSON");
		}
	}
}
