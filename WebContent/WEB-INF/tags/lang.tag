<%--this code need be written in a single line style, or you will see problem--%><%@ attribute
	name="text" required="true" type="java.lang.String"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%><jsp:useBean
	id="data" class="i18n.TextMother" /><%=data.getText((String) request.getSession().getAttribute("LANG"), text)%>${fun:replace(item.comments, "/n", "; ")}<%-- <c:out value="${data.getText(${sessionScope.LANG},${text})}"></c:out> --%>