<%@page import="java.util.List"%>
<%
	List<String> errors = (List<String>) request.getAttribute("errors");
%>

<%
	if (errors != null && errors.size() > 0) {
%>
<div class="alert alert-danger">
	<ul>
		<%
			for (String error : errors) {
		%>
		<li><%=error%></li>
		<%
			}
		%>
	</ul>
</div>
<%
	}
%>