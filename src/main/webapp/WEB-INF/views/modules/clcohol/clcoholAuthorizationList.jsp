<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精授权签字人管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholAuthorization/">酒精授权签字人列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholAuthorization:edit"><li><a href="${ctx}/clcohol/clcoholAuthorization/form">酒精授权签字人添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholAuthorization" action="${ctx}/clcohol/clcoholAuthorization/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="clcohol:clcoholAuthorization:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholAuthorization">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholAuthorization/form?id=${clcoholAuthorization.id}">
					<fmt:formatDate value="${clcoholAuthorization.updateDate}" pattern="yyyy年MM月dd日 "/>
				</a></td>
				<td>
					${clcoholAuthorization.remarks}
				</td>
				<shiro:hasPermission name="clcohol:clcoholAuthorization:edit"><td>
    				<a href="${ctx}/clcohol/clcoholAuthorization/form?id=${clcoholAuthorization.id}">修改</a>
					<a href="${ctx}/clcohol/clcoholAuthorization/delete?id=${clcoholAuthorization.id}" onclick="return confirmx('确认要删除该酒精授权签字人吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>