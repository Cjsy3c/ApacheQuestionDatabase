<%@ page import="question.Database" import="question.Question" import="java.util.Vector"
	language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
Integer ansID = (Integer)request.getAttribute("id");
String str = (String)request.getAttribute("question");
out.println("<title>Answers for question \""+ansID+"\"</title>");
out.println("<h1>Answers</h1>");
out.println("<h2>\"" + str+"\"</h2>");
%>
</head>
<body>
<%
		Vector<String> v = (Vector<String>)request.getAttribute("vector");
		int count = 0;

    	for (String s : v)
		{
    		count++;
	        out.print("<p>" + count + ". <a \">" + s + "</a></p>");
		}

out.println("<form method=\"POST\" action=\"Answers?id=" +ansID+"\">");
%>
<p><input type=text name=theAnswer size=50> <input type=submit value="Add Answer"></p>
<p><input type=hidden name=param value=newQ></p>
</form>

<%
out.println("<form method=\"POST\" action=\"Answers?id="+ansID+"\">");
%>      

<input type=submit value="Delete Question"></p>
<p><input type=hidden name=param value=delete> </p>
</form>
       
<form method=POST action=Answers>
<input type=submit value=Back></p>
<p><input type=hidden name=param value=back></p>
</form>
</body></html>


</body>
</html>