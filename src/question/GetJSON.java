package question;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetJSON
 */
@WebServlet("/GetJSON")
public class GetJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Vector<Question> v;
	private Database db;

	private Runnable readQs = new Runnable(){
		@Override
		public void run() {

			v = new Vector<Question>();
			v = db.readQuestions();

		}
	};

	public GetJSON() {
		super();
		db = Database.getInstance();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Thread t3 = new Thread(readQs);
		t3.start();
		RequestDispatcher rd = null;

		try {
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rd = request.getRequestDispatcher("/json.jsp");
		request.setAttribute("viewModel", v);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
