<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物料领取管理</title>
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
		<li class="active"><a href="${ctx}/material/specimenMaterialOut/">物料领取列表</a></li>
		<shiro:hasPermission name="material:specimenMaterialOut:edit"><li><a href="${ctx}/material/specimenMaterialRegister/collectForm">物料领取添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="specimenMaterialOut" action="${ctx}/material/specimenMaterialOut/" method="post" class="breadcrumb form-search">
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
				<th>编码</th>
				<th>图片</th>
				<th>子项数量</th>
				<th>总量</th>
				<th>状态</th>
				<th>创建者</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="material:specimenMaterialOut:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="specimenMaterialOut">
			<tr>
				<td><a href="${ctx}/material/specimenMaterialOut/form?id=${specimenMaterialOut.id}">
					${specimenMaterialOut.code}
				</a></td>
				<td>
					${specimenMaterialOut.pic}
				</td>
				<td>
					${specimenMaterialOut.itemCount}
				</td>
				<td>
					${specimenMaterialOut.totalQty}
				</td>
				<td>
					${specimenMaterialOut.status}
				</td>
				<td>
					${specimenMaterialOut.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${specimenMaterialOut.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${specimenMaterialOut.remarks}
				</td>
				<shiro:hasPermission name="material:specimenMaterialOut:edit"><td>
    				<a href="${ctx}/material/specimenMaterialOut/form?id=${specimenMaterialOut.id}">修改</a>
					<a href="${ctx}/material/specimenMaterialOut/delete?id=${specimenMaterialOut.id}" onclick="return confirmx('确认要删除该物料领取吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>