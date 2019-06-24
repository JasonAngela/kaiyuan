<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报告修改记录管理</title>
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
		<li class="active"><a href="${ctx}/entrust/entrustModifyrecord/">报告修改记录列表</a></li>
		<shiro:hasPermission name="entrust:entrustModifyrecord:edit"><li><a href="${ctx}/entrust/entrustModifyrecord/form">报告修改记录添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>修改时间</th>
				<th>名字</th>
				<th>备注</th>
				<shiro:hasPermission name="entrust:entrustModifyrecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="entrustModifyrecord">
			<tr>
				<td><a href="${ctx}/entrust/entrustModifyrecord/form?id=${entrustModifyrecord.id}">
					<fmt:formatDate value="${entrustModifyrecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				
				<td>
					${entrustModifyrecord.userby}
				</td>
				
				<td>
					${entrustModifyrecord.modefy}
				</td>
				<shiro:hasPermission name="entrust:entrustModifyrecord:edit"><td>
    				<a href="${ctx}/entrust/entrustModifyrecord/form?id=${entrustModifyrecord.id}">修改</a>
					<a href="${ctx}/entrust/entrustModifyrecord/delete?id=${entrustModifyrecord.id}" onclick="return confirmx('确认要删除该报告修改记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>