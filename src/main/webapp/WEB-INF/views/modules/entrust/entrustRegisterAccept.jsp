<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>DNA归档</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.table {
	font-size:12px;
	border-collapse:collapse;
	border-top: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
}

.table td, .table th {
	vertical-align: middle;
	border-bottom: 1px solid #7F9DB9;
	border-right: 1px solid #7F9DB9;
	padding:2px;
	text-align: center;
	line-height:18px;
}

.table th {
	font-weight:normal;
	background-color:#C3DAF9;
}

.inputTable {
	font-size:12px;
	border-collapse:collapse; 
	border-top: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
}

.inputTable td, .inputTable th {
	vertical-align: middle;
	text-align:left;
	border-bottom: 1px solid #7F9DB9;
	border-right: 1px solid #7F9DB9;
	padding:2px 0px 0px 18px;
	line-height:20px;
}

.inputTable th,.inputTable td {
	font-weight:normal;
	background-color:#C3DAF9;
}
	</style> 
</head>
<body>

    	<table  width="200"  height="230" class="inputTable"  style="margin:auto;margin-top: 239px;">
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=1" >个人鉴定</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=2" >司法鉴定</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=3" >个人识别</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=4" >亲缘鉴定</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=5" >同一认定</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="${ctx}/entrust/entrustRegister/form?accept=6" >其它</a><br/>
				    </shiro:hasPermission>
				</td>
			</tr>
			</table>
       



</body>
</html>