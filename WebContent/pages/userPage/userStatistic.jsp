<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>
<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script type="text/javascript">
function go(path,id){
	console.log(path);
	console.log(id);
	window.location.href = path+'?id='+id;
}

$(document).ready(function(){
    sortable_tables.sorting_field_table();
    $('.non-fixed-width-table tbody').sortable();
});

var sortable_tables =
{
    sorting_field_table: function()
    {
        $('.fixed-width-table tbody').sortable({
            helper: sortable_tables.fixWidthHelper
        }).disableSelection();
    },

    fixWidthHelper: function(e, ui) {
        ui.children().each(function() {
            $(this).width($(this).width());
        });
        return ui;
    }
}

function confirmDepartmentOrder(){
	
	var rows = $("#orderDep").children('tbody').children('tr');
	var list=[];
	for (var i=0;i<rows.length;i++){
		console.log($(rows[i]).attr("id"));
		list.push($(rows[i]).attr("id"));
	}
	sendNewOrder(list);
}

function sendNewOrder(list){
	
	var requestJSON = JSON.stringify({order:list});
    $.ajax({
       type : "PUT",
       url : "userStat",
       headers : {
          "Content-Type" : "application/json"
       },
       data : requestJSON,
       success : function(data) {
          alert("updated!");
       },
       error : function(xhr, ajaxOptions, thrownError) {
    	   alert("error "+xhr.responseText);
       }
    });
	
}

</script>
<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<div class="jumbotron container theme-showcasejumbotron" role="main">
		<div class="jumbotron row">

			<table class="table fixed-width-table" id="orderDep">
				<thead>
					<tr>
						<th>#</th>
						<th><tags:lang text="department"></tags:lang></th>
						<th><tags:lang text="placesGov"></tags:lang></th>
						<th><tags:lang text="placesTot"></tags:lang></th>
						<th><tags:lang text="positionMy"></tags:lang></th>
						<th><tags:lang text="totalUserPass"></tags:lang></th>
						<th><tags:lang text="operation"></tags:lang></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userPositionList}" var="userPos">
						<tr id="${userPos.id}">
							<th>${userPos.id}</th>
							<td>${userPos.name}</td>
							<td>${userPos.placeGov}</td>
							<td>${userPos.totaPlace}</td>
							<td>${userPos.myPlace}</td>
							<td>${userPos.totalPeople}</td>
							<td><c:choose>
									<c:when test="${ROLE=='USER'}">
										<button type="button" class="btn btn-primary "
											onclick="go('departmentRating',${userPos.id})">
											<tags:lang text="showList"></tags:lang>
										</button>
									</c:when>
								</c:choose></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			
			<div>
				<tags:lang text="prioritySupport"></tags:lang>
				<br>
				<button onclick="confirmDepartmentOrder()" class="button btn-success"><tags:lang text="confirm"></tags:lang></button>
			</div>
		</div>
	</div>



</body>
</html>