<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精底稿管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clcohol/clcoholPapers/">酒精底稿列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholPapers" action="${ctx}/clcohol/clcoholPapers/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>鉴定人</th>
				<th>修改时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholPapers">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholPapers/form?id=${clcoholPapers.id}">
					${clcoholPapers.people}
				</a></td>
				<td>
					<fmt:formatDate value="${clcoholPapers.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${clcoholPapers.remarks}
				</td>
				<td>
    				<a href="${ctx}/clcohol/clcoholPapers/form?id=${clcoholPapers.id}">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	

	<div class="pagination">${page}</div>
</body>
</html>