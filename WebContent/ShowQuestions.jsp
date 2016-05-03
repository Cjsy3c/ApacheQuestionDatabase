<%@ page import="question.Database" import="question.Question" import="java.util.Vector"
 language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Survey Servlet</title>
<h2>Questions</h2>
</head>
<body>


<%
		Vector<Question> v = (Vector<Question>)request.getAttribute("viewModel");
		
    	for (int i = 0; i < v.size(); i++)
		{
	        out.print("<p>" +(i+1) + ". <a href=Answers?id=" +v.get(i).getID()
	        		+">" + v.get(i).getQuestion() +"</a> </p>");
		}
%>

<form method=POST action=ShowQuestions>
<p><input type=text name=theQuestion size=50> <input type=submit value="Add Question"></p>
<p><input type=hidden name=param value=newQ ></p>
</form>
        
<form method=POST action=ShowQuestions>
<p><input type=submit value="Go to JSON"></p>
<p><input type=hidden name=param value=json></p>
</form>
</body></html>
