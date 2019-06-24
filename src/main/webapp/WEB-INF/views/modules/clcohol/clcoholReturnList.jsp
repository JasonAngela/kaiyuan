<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精样品归还管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholReturn/">酒精样品归还列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholReturn:edit"><li><a href="${ctx}/clcohol/clcoholReturn/form">酒精样品归还添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholReturn" action="${ctx}/clcohol/clcoholReturn/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>物证编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
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
				<shiro:hasPermission name="clcohol:clcoholReturn:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholReturn">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholReturn/form?id=${clcoholReturn.id}">
					<fmt:formatDate value="${clcoholReturn.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${clcoholReturn.remarks}
				</td>
				<shiro:hasPermission name="clcohol:clcoholReturn:edit"><td>
    				<a href="${ctx}/clcohol/clcoholReturn/form?id=${clcoholReturn.id}">修改</a>
					<a href="${ctx}/clcohol/clcoholReturn/delete?id=${clcoholReturn.id}" onclick="return confirmx('确认要删除该酒精样品归还吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>