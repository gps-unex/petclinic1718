<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <c:choose>
        <c:when test="${owner['new']}"><c:set var="method" value="post"/></c:when>
        <c:otherwise><c:set var="method" value="put"/></c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${owner['new']}">New </c:if> Owner
    </h2>
    <form:form modelAttribute="owner" method="${method}" class="form-horizontal" id="add-owner-form">
        
        <style type="text/css">
			input {
   				width: 100px;
			}
			select{
				width: 100px;
			}
		</style>
		<div style="width:100%; overflow: hidden;">
		    <div style="float:left; width:50%;">
		      	<petclinic:inputField label="First Name" name="firstName"/>
		        <petclinic:inputField label="Last Name" name="lastName"/>
		        <petclinic:inputField label="Address" name="address"/>
		    </div>
		    <div style="float:right; width:50%;">
		        <petclinic:inputField label="City" name="city"/>
		        <petclinic:inputField label="Telephone" name="telephone"/>
		        <petclinic:selectField label="Pay Preference" name="payPreference" names="${paymentMethods}" size="1"/>
		    </div>
		</div>
               
        <div class="form-actions">
            <c:choose>
                <c:when test="${owner['new']}">
                    <button type="submit">Add Owner</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Owner</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>

</html>
