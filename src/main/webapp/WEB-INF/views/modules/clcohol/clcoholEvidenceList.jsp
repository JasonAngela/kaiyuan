<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精物证管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholEvidence/">酒精物证列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholEvidence:edit"><li><a href="${ctx}/clcohol/clcoholEvidence/form">酒精物证添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholEvidence" action="${ctx}/clcohol/clcoholEvidence/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>物证编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>受理编号：</label>
				<form:input path="casecode" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>委托日期：</label>
				<form:input path="entrustDate" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>采取时间：</label>
				<form:input path="miningdate" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>修改时间</th>
				<shiro:hasPermission name="clcohol:clcoholEvidence:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholEvidence">
			<tr>
				<td><a href="${ctx}/clcohol/clcoholEvidence/form?id=${clcoholEvidence.id}">
					${clcoholEvidence.name}
				</a></td>
				<td>
					<fmt:formatDate value="${clcoholEvidence.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="clcohol:clcoholEvidence:edit"><td>
    				<a href="${ctx}/clcohol/clcoholEvidence/form?id=${clcoholEvidence.id}">修改</a>
					<a href="${ctx}/clcohol/clcoholEvidence/delete?id=${clcoholEvidence.id}" onclick="return confirmx('确认要删除该酒精物证吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>