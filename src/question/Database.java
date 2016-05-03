package question;
import java.sql.*;
import java.util.Vector;
public class Database{

	private static final String driver = "oracle.jdbc.OracleDriver";
	private static final String url = "*******";
	private static final String user = "******";
	private static final String pass = "******";
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private static Database db;

	private static final String getCountQs = "SELECT COUNT(Question_ID) FROM questions";
	private static final String getCountAs = "SELECT COUNT(Answer_ID) FROM answers";
	private static final String createQs = "CREATE TABLE questions (question_id int Primary Key ,question Varchar(200))";
	private static final String createAs = "CREATE TABLE answers (answer_id int Primary Key,question_id Int NOT NULL,answer Varchar(200))";

	/**
	 * Open a connection to the database and create the tables
	 * if they don't already exist.
	 */
	private Database() {
		try {
			// open sql database
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			stmt = con.createStatement();

			System.out.println("Connected");
			// if table doesn't exist, create it
			if (!tableExists()){
				stmt.execute(createQs);
				stmt.execute(createAs);
			}
			
		} catch ( ClassNotFoundException | SQLException e) {

			System.out.println("error in Database constructor");
			e.printStackTrace();
		}
	}
	/**
	 * Get an instance of the Database
	 * @return
	 */
	public static Database getInstance(){
		if (db == null)
			db = new Database();
		// return database
		return db;
	}

	/**
	 * Get a Vector of all of the questions in the database.
	 * Returns empty Vector on Exception or empty database.
	 * @return
	 * @throws SQLException
	 */
	public Vector<Question> readQuestions()
	{
		Vector<Question> v = new Vector<Question>();
		String cmd = "SELECT * FROM questions";
		
		try {
			// fetch data
			rs = stmt.executeQuery(cmd);

			// add questions to vector
			Question q;
			while (rs.next())
			{
				q = new Question(rs.getString(2), Integer.parseInt(rs.getString(1)));
				v.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in Read Questions");
		}
		return v;
	}

	/** Retreive all answers for the given question ID
	 * Returns empty Vector on Exception or empty database.
	 * @param questionID
	 * @return
	 * @throws SQLException
	 */
	public Vector<String> readAnswers(Integer questionID)
	{
		Vector<String> v = new Vector<String>();
		String cmd = "SELECT answer FROM Answers where question_id ="+questionID;
		try {
			// query database
			rs = stmt.executeQuery(cmd);
			
			// add values to vector
			while (rs.next())
			{
				v.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in Read Answers");

		}
		
		return v;
	}

	/** Add a Question to the Database. 
	 * 
	 * @param str
	 * @return boolean if added
	 */
	public boolean addQuestion(String str)
	{
		String cmd = "INSERT into Questions VALUES(?,?)";
		PreparedStatement ppst;
		
		try {
			ppst = con.prepareStatement(cmd);
			
			// get next id
			ResultSet rs = stmt.executeQuery(getCountQs);
			rs.next();
			int nextPOS = rs.getInt(1);
			rs.close();

			// add values to statement.
			ppst.setInt(1, nextPOS);
			ppst.setString(2, str);

			// execute
			ppst.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in add question");
		}

		return false;
	}

	/** Add an Answer to the Database for the Given Question ID 
	 * 
	 * @param str
	 * @param QuestionID
	 * @return boolean if added
	 */
	public boolean addAnswer(String str, Integer QuestionID)
	{
		String cmd = "INSERT into Answers VALUES(?,?,?)";
		PreparedStatement ppst;
		try {
			ppst = con.prepareStatement(cmd);
			
			// get next id
			ResultSet rs = stmt.executeQuery(getCountAs);
			rs.next();
			int nextPOS = rs.getInt(1);
			rs.close();

			// set values
			ppst.setInt(1, nextPOS);
			ppst.setInt(2, QuestionID);
			ppst.setString(3,str);

			// execute statement
			ppst.execute();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in Add Answers");

		}

		return false;
	}

	/** Delete the Question with the given ID
	 * 
	 * @param i
	 * @return boolean
	 */
	public boolean deleteQuestion(final int i)
	{
		try {
			// make sure both execute or neither do
			con.setAutoCommit(false);
			String cmd1 = "DELETE from Questions WHERE question_id ="+i;
			String cmd2 = "DELETE from Answers WHERE question_id ="+i;
			
			// attempt to execute
			stmt.executeUpdate(cmd2);
			stmt.executeUpdate(cmd1);
			 
			//save changes
			con.commit();
			con.setAutoCommit(true);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in delete Question");

		}
		return false;
	}

	/**
	 * This method fetches a specific question.
	 * Returns null on database error, invalid on user error.
	 * @param questionID
	 * @return
	 */
	public Question getSpecificQuestion(Integer questionID)
	{
		if (questionID < 0 || questionID.equals(null))
			return new Question("Invalid",1);
		Question q;
		String cmd = "SELECT question FROM Questions where question_id ="+questionID;

		try {
			rs = stmt.executeQuery(cmd);
			
			// get question
			rs.next();
			q = new Question(rs.getString(1),questionID);

			return q;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in get specific Questions");

		}
		
		return null;
	}

	/**
	 * Check if the questions and answers tables exist in the database.
	 * @return
	 * @throws SQLException
	 */
	private boolean tableExists() throws SQLException {
		boolean temp = false;
		ResultSet res;
		try {
			// query the master table for the table name
			String sql = "SELECT COUNT(1) FROM ALL_TABLES WHERE TABLE_NAME = 'QUESTIONS' or TABLE_NAME = 'ANSWERS'";

			res = stmt.executeQuery(sql);

			res.next();	

			temp = (res.getInt(1)>1); 	// there exists a table with that name

			res.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in tableExists");
		}

		return temp;	
	}
}
