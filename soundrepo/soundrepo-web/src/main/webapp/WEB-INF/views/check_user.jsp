<%@page import="ua.devfactory.users.dto.User"%>
<% User user = (User) session.getAttribute("user"); 
   if (user == null){
	   response.sendRedirect("/login.do");
	   return;
   }
%>
