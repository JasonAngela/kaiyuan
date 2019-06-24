  <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医委托受理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#checkpic").click(function(){
			   	  $("#a").toggle();
			   	  });
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
<style type="text/css">
 #a {
		  display: none;  position: absolute;  top: 15%;  left: 25%;  width: 20%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		}
</style>
</head>
 <body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clinic/clinicRegister/">法医委托受理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clinicRegister" action="${ctx}/clinic/clinicRegister/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>委托人：</label>
				<form:input path="clientName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicRegister.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clinicRegister.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>受理类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('fayitypeCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('clinicRegister_statusCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="checkpic" class="btn btn-primary" type="button" value="导出表格"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
		<div align="center" id="a" style="display: none;">
	<form action="${ctx}/clinic/clinicRegister/export" method="post">
			<input type="text" name="beginCreateDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">开始时间
			<input type="text" name="endCreateDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">结束时间
		<table>
			<tr>
				<td><input type="checkbox" name="code" value="1"/></td>
				<td>编码</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="caseCode" value="1"/></td>
				<td>案件编码</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientName" value="1"></td>
				<td>委托人</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientTel" value="1"></td>
				<td>委托人电话</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientReceiver" value="1"></td>
				<td>委托收件人</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="agentName" value="1"></td>
				<td>送检人</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="agentTel" value="1"></td>
				<td>送检人电话</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="serverName" value="1"></td>
				<td>受理人</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="type" value="1"></td>
				<td>委托事项</td>
			</tr>  
		    <tr>
				<td><input type="checkbox" name="totalFee" value="1"></td>
				<td>合计费用</td>
			</tr>
			 <tr>
				<td><input type="checkbox" name="surveyorName" value="1"></td>
				<td> 被检人姓名</td>
			</tr>
			 <tr>
				<td><input type="checkbox" name="surveyorSex" value="1"></td>
				<td>被检人性别</td>
			</tr>
		</table>
		<input  class="btn-primary" type="submit" value="导出exel表格"/>
		</form>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>受理编号</th>
				<th>案件编号</th>
				<th>委托人</th>
				<th>送检人</th>
				<th>委托时间</th>
				<th>状态</th>
				<shiro:hasPermission name="clinic:clinicRegister:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clinicRegister">
			<tr>
				<td><a href="${ctx}/clinic/clinicRegister/form?id=${clinicRegister.id}">${clinicRegister.code }</a></td>
				<td>${clinicRegister.caseCode }</td>		
				<td>${clinicRegister.clientName }</td>
				<td>${clinicRegister.agentName }</td>
			 	<td>
					${clinicRegister.clientZipcode}
				</td> 
				 <td>
					${fns:getDictLabel(clinicRegister.status, 'clinicRegister_statusCode', '')}
				</td>   
				<shiro:hasPermission name="clinic:clinicRegister:edit"><td>
    				<a href="${ctx}/clinic/clinicRegister/form?id=${clinicRegister.id}">修改</a>
    				<a href="${ctx}/clinic/clinicRegister/archive?id=${clinicRegister.id}">归档</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>