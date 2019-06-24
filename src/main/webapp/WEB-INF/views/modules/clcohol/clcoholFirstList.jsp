<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精初稿管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholFirst/">酒精初稿列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholFirst:edit"><li><a href="${ctx}/clcohol/clcoholFirst/form">酒精初稿添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholFirst" action="${ctx}/clcohol/clcoholFirst/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>register_id：</label>
				<form:input path="register.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>委托方：</label>
				<form:input path="entrust" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<shiro:hasPermission name="clcohol:clcoholFirst:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholFirst">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholFirst/form?id=${clcoholFirst.id}">
					<fmt:formatDate value="${clcoholFirst.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${clcoholFirst.remarks}
				</td>
				<shiro:hasPermission name="clcohol:clcoholFirst:edit"><td>
    				<a href="${ctx}/clcohol/clcoholFirst/form?id=${clcoholFirst.id}">修改</a>
					<a href="${ctx}/clcohol/clcoholFirst/delete?id=${clcoholFirst.id}" onclick="return confirmx('确认要删除该酒精初稿吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>