<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流转单管理</title>
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
		<li class="active"><a href="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/">流转单列表</a></li>
		<shiro:hasPermission name="entrust_flow_sheet:entrust:entrustFlowSheet:edit"><li><a href="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/form">流转单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="entrustFlowSheet" action="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/" method="post" class="breadcrumb form-search">
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
				<th>pic</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="entrust_flow_sheet:entrust:entrustFlowSheet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="entrustFlowSheet">
			<tr>
				<td><a href="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/form?id=${entrustFlowSheet.id}">
					${entrustFlowSheet.pic}
				</a></td>
				<td>
					<fmt:formatDate value="${entrustFlowSheet.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${entrustFlowSheet.remarks}
				</td>
				<shiro:hasPermission name="entrust_flow_sheet:entrust:entrustFlowSheet:edit"><td>
    				<a href="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/form?id=${entrustFlowSheet.id}">修改</a>
					<a href="${ctx}/entrust_flow_sheet/entrust/entrustFlowSheet/delete?id=${entrustFlowSheet.id}" onclick="return confirmx('确认要删除该流转单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>