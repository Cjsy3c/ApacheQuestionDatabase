<%@ page import="question.Database" import="question.Question" import="java.util.Vector"
	language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSON</title>
</head>
<body>

<%
Vector<Question> v = (Vector<Question>)request.getAttribute("viewModel");
String json = "";
for (int i = 0; i < v.size(); i++)
{
	if (i == 0)
	{
		json = new String("{\"questions\": ")
		+"{\"question\":\""+v.get(i).getQuestion()+"\"}";
	}
	else
		json = json + ",{\"question\":\""+v.get(i).getQuestion()+"\"}";
}
json = json +"}";
out.println(json);
%>
</body>
</html>