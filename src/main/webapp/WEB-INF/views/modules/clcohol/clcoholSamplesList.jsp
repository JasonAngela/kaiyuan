<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精领取样品管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholSamples/">酒精领取样品列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholSamples:edit"><li><a href="${ctx}/clcohol/clcoholSamples/form">酒精领取样品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholSamples" action="${ctx}/clcohol/clcoholSamples/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="clcohol:clcoholSamples:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholSamples">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholSamples/form?id=${clcoholSamples.id}">
					<fmt:formatDate value="${clcoholSamples.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${clcoholSamples.remarks}
				</td>
				<shiro:hasPermission name="clcohol:clcoholSamples:edit"><td>
    				<a href="${ctx}/clcohol/clcoholSamples/form?id=${clcoholSamples.id}">修改</a>
					<a href="${ctx}/clcohol/clcoholSamples/delete?id=${clcoholSamples.id}" onclick="return confirmx('确认要删除该酒精领取样品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>