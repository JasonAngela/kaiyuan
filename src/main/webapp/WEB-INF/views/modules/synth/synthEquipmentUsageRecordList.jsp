<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备使用记录管理</title>
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
		<li class="active"><a href="${ctx}/synth/synthEquipmentUsageRecord/">设备使用记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="synthEquipmentUsageRecord" action="${ctx}/synth/synthEquipmentUsageRecord/" method="post" class="breadcrumb form-search">
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
				<th>设备名称</th>
				<th>使用人</th>
				<th>使用时间</th>
				<th>备注</th>
				<shiro:hasPermission name="synth:synthEquipmentUsageRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="synthEquipmentUsageRecord">
			<tr>
				<td>
					${synthEquipmentUsageRecord.equipment.name}
				</td>
				<td>
					<a href="${ctx}/synth/synthEquipmentUsageRecord/form?id=${synthEquipmentUsageRecord.id}">
						${synthEquipmentUsageRecord.operator.name}
					</a>
				</td>
				<td>
					<fmt:formatDate value="${synthEquipmentUsageRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${synthEquipmentUsageRecord.remarks}
				</td>
				<shiro:hasPermission name="synth:synthEquipmentUsageRecord:edit"><td>
    				<a href="${ctx}/synth/synthEquipmentUsageRecord/form?id=${synthEquipmentUsageRecord.id}">修改</a>
					<a href="${ctx}/synth/synthEquipmentUsageRecord/delete?id=${synthEquipmentUsageRecord.id}" onclick="return confirmx('确认要删除该设备使用记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>