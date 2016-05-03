package question;

public class Question {
	private String text = "";
	private Integer id;
	
	Question(String question, Integer id)
	{
		text = question;
		this.id = id;
	}
	public String getQuestion()
	{
		return text;
	}
	public Integer getID()
	{
		return id;
	}
}
