<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>材料登记管理</title>
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
		<li class="active"><a href="${ctx}/clinic/clinicPhysical/">材料登记列表</a></li>
		<shiro:hasPermission name="clinic:clinicPhysical:edit"><li><a href="${ctx}/clinic/clinicPhysical/form">材料登记添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clinicPhysical" action="${ctx}/clinic/clinicPhysical/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>物证编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>受理编号：</label>
				<form:input path="caseCode" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicPhysical.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicPhysical.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>物证编号</th>
				<th>创建时间</th>
				<th>描述</th>
				<shiro:hasPermission name="clinic:clinicPhysical:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clinicPhysical">
			<tr>
				<td><a href="${ctx}/clinic/clinicPhysical/form?id=${clinicPhysical.id}">${clinicPhysical.code }</a></td>
				<td>
					<fmt:formatDate value="${clinicPhysical.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${clinicPhysical.remarks}
				</td>
				<shiro:hasPermission name="clinic:clinicPhysical:edit"><td>
    				<a href="${ctx}/clinic/clinicPhysical/form?id=${clinicPhysical.id}">修改</a>
					<a href="${ctx}/clinic/clinicPhysical/delete?id=${clinicPhysical.id}" onclick="return confirmx('确认要删除该材料登记吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>