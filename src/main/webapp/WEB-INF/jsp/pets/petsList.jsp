<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />

<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Pets</h2>

		<datatables:table id="pets" data="${selections}" cdn="true" row="pet"
			theme="bootstrap2" cssClass="table table-striped" paginate="false"
			info="false" export="pdf">
			<datatables:column title="Name" cssStyle="width: 150px;">
			<spring:url value="/owners/{ownerId}/pets/{petId}/edit"
				var="editPetURL">
				<spring:param name="ownerId" value="${pet.owner.id}" />
				<spring:param name="petId" value="${pet.id}" />
			</spring:url>
			
			<a href="${fn:escapeXml(editPetURL)}" ><c:out value="${pet.name}" /></a> 
			
			</datatables:column>
			<datatables:column title="Owner" cssStyle="width: 200px;">
				<c:out value="${pet.owner.firstName} ${pet.owner.lastName}" />
			</datatables:column>
		</datatables:table>

		<jsp:include page="../fragments/footer.jsp" />

	</div>
</body>

</html>
