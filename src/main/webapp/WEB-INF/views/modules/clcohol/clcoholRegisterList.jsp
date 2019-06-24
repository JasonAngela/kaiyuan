<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精委托书管理</title>
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
		<li class="active"><a href="${ctx}/clcohol/clcoholRegister/">酒精委托书列表</a></li>
		<shiro:hasPermission name="clcohol:clcoholRegister:edit"><li><a href="${ctx}/clcohol/clcoholRegister/form">酒精委托书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="clcoholRegister" action="${ctx}/clcohol/clcoholRegister/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>委托编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>案件编码：</label>
				<form:input path="casecode" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>委托人：</label>
				<form:input path="clientname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
			<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicRegister.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicRegister.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>委托编号</th>
			    <th>案件编码</th>
			    <td>委托人</td>
			    <td>委托日期</td>
				<th>被检人</th>
				<th>状态</th>
				<shiro:hasPermission name="clcohol:clcoholRegister:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clcoholRegister">
			<tr>
				<td>
					<a href="${ctx}/clcohol/clcoholRegister/form?id=${clcoholRegister.id}">
						${clcoholRegister.code}
					</a>	
				</td>
				
				<td>
					${clcoholRegister.casecode}
				</td>
				
				<td>
					${clcoholRegister.clientname}
				</td>
				
				<td>
					${clcoholRegister.entrustdate}
				</td>
				
				<td>
					${clcoholRegister.name}
				</td>
				 <td>
					${fns:getDictLabel(clcoholRegister.other2, 'clcoholRegister_statusCode', '')}
				</td>  
				<shiro:hasPermission name="clcohol:clcoholRegister:edit"><td>
    				<a href="${ctx}/clcohol/clcoholRegister/form?id=${clcoholRegister.id}">修改</a>
    				<a href="${ctx}/clcohol/clcoholRegister/archive?id=${clcoholRegister.id}">归档</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>