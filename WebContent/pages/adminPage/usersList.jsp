<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>
	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
	<script type="text/javascript">
	var currentPage=${page};
	
		function banUser(url, id) {
			<c:if test="${isPageForBlocking==true}">
				if (confirm('<tags:lang text="banUser"></tags:lang>?')) {
			</c:if>
			<c:if test="${isPageForBlocking==false}">
				if (confirm('<tags:lang text="unBlockUser"></tags:lang>?')) {
			</c:if>
			
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						location.reload();
					}
					if (xhr.readyState === 4 && this.status == 500) {
						alert("oops");
					}
				};
				xhttp.open("POST", url, true);
				xhttp.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				var data = "id=" + id;
				xhttp.send(data);
				reload();
			} else {
				// Do nothing!
			}
		}
				
		function filterUser() {
			<c:if test="${isPageForBlocking==false}">
			console.log("filterBannedUser");
			//alert($("#filterFio").val()+$("#filterEmail").val()+$("#filterDiplom").val());
			window.location.href="bannedUser?page="+currentPage
				+"&fio="+$("#filterFio").val()
				+"&email="+$("#filterEmail").val()
				+"&diplom="+$("#filterDiplom").val();
			</c:if>
			<c:if test="${isPageForBlocking==true}">
			console.log("filterClearUser");
			//alert($("#filterFio").val()+$("#filterEmail").val()+$("#filterDiplom").val());
			window.location.href="cleanUser?page="+currentPage
					+"&fio="+$("#filterFio").val()
					+"&email="+$("#filterEmail").val()
					+"&diplom="+$("#filterDiplom").val();	
			</c:if>
		}
		
		function nextPage(){
			currentPage++;
			filterUser();
		}
		function prevPage(){
			if ((currentPage-1)>0){
				currentPage--;
				filterUser();
			}
		}
		
		function modifyUserMark(userId,markId){
			console.log(userId+"|"+markId+"|val="+$("#"+userId+"_"+markId).val());
			
			var datamodel = {
		            userId : userId,
		            subjectId : markId,
		            mark : $("#"+userId+"_"+markId).val()
		         };
		         $.ajax({
		            type : "POST",
		            url : "InsertMarkForStudent",
		            data : datamodel,
		            success : function(data) {
		               alert("ok"+data);
		            },
		            error : function(data) {
		            	alert("error"+data);
		            }
		         });
		}
	</script>

	<div class="jumbotron container" role="main">
		<div class="jumbotron row">
			<c:if test="${isPageForBlocking==true}">

				<c:if test="${ROLE=='SUPERADMIN'}">
					<div>Set Marks for group</div>

					<select>
						<c:forEach var="mark" items="${universityMarks}">
							<option value="${mark.id}">${mark.name}</option>
						</c:forEach>
					</select>

					<!-- excelSetMarks POST -->
					<form enctype="multipart/form-data">
						<input type="file" name="file" size="50" onchange="checking()" />
						<input type="submit" id="sendFile" style="display: inline-block;"
							value='<tags:lang text="uploadFile"></tags:lang>' />
					</form>
				</c:if>
			</c:if>

			<!-- filter -->
			<div>
				Fio<input type="text" id="filterFio" value="${filterFio}">
				Email<input type="text" id="filterEmail" value="${filterEmail}">
				Diplom<input type="text" id="filterDiplom" value="${filterDiplom}">

				<button onclick="filterUser()" class="btn btn-success">
					<tags:lang text="find"></tags:lang>
				</button>
			</div>

			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th><tags:lang text="id"></tags:lang></th>
						<th><tags:lang text="fio"></tags:lang></th>
						<th><tags:lang text="email"></tags:lang></th>
						<th><tags:lang text="diplom"></tags:lang></th>
						<th><tags:lang text="operation"></tags:lang></th>
						<c:if
							test="${(ROLE=='ADMIN' ||ROLE=='SUPERADMIN') && isPageForBlocking==true}">
							<th><tags:lang text="setMark"></tags:lang></th>
						</c:if>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="user" items="${users}">
						<tr>
							<th scope="row"></th>
							<td>${user.id}</td>
							<td>${user.fio}</td>
							<td>${user.email}</td>
							<td>${user.diplom}</td>

							<td><c:choose>
									<c:when test="${isPageForBlocking==true}">
										<button type="button" class="btn btn-danger"
											onclick="banUser('blockUser','${user.id}')">
											<tags:lang text="banUser"></tags:lang>
										</button>

									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-danger"
											onclick="banUser('unblockUser','${user.id}')">
											<tags:lang text="unBlockUser"></tags:lang>
										</button>
									</c:otherwise>
								</c:choose></td>
							<c:if
								test="${(ROLE=='ADMIN' ||ROLE=='SUPERADMIN') && isPageForBlocking==true}">
								<th>
									<ul>
										<c:forEach var="mark" items="${user.studentMarks}">
											<li>${mark.name}<input type="number"
												value="${mark.mark}" id="${user.id}_${mark.id}">
												<button onclick="modifyUserMark(${user.id},${mark.id})"
													class="btn btn-warning">
													<tags:lang text="update"></tags:lang>
												</button>
											</li>
										</c:forEach>
									</ul>
								</th>
							</c:if>
						</tr>
					</c:forEach>

				</tbody>
			</table>

			<div>
				<button style="display: inline-block;" onclick="prevPage()"
					class="btn btn-primary">
					<tags:lang text="prevPage"></tags:lang>
				</button>
				<div style="display: inline-block;">
					<tags:lang text="currPage"></tags:lang>
					<label>${page}</label>
				</div>
				<button style="display: inline-block;" onclick="nextPage()"
					class="btn btn-primary">
					<tags:lang text="nextPage"></tags:lang>
				</button>
			</div>

		</div>
	</div>
	<%@ include file="/pages/jspf/directive/footer.jspf"%>
</body>
</html>