<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>材料登记管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		 function   window.onload() {
			  factory.printing.header = "";       //页眉
			  factory.printing.footer = "";       //页脚
			  factory.printing.portrait = false;    //true为纵向打印，flase为横向打印
			  factory.printing.leftMargin = 1.0; //左页边距
			  factory.printing.topMargin = 1.0;    //上页边距
			  factory.printing.rightMargin = 1.0;//右页边距
			  factory.printing.bottomMargin = 1.0; //下页边距
		 }	  
		
		
	</script>
	<style media=print>
  .form-actions{display:none;}
  .nav nav-tabs{display:none;}
  .active{display:none;}
</style>
	<style type="text/css">
	   body{
	background-color: #C1CDCD;
	}
		.table {
	font-size:12px;
	border-collapse:collapse;
	border-top: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
	margin:auto;                     
}
.form-horizontal{
width: 750px;
   background-color: #F0FFFF;
   padding: 85px 60px 0 60px;
   margin: -45px auto auto;
   }
.nav-tabs{
background-color: #F0FFFF;
}

.controls{


}
			.table {
	font-size:12px;
	border-collapse:collapse;
	border-top: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
}

td{ text-align:center;}

.table td, .table th {
	vertical-align: middle;
	border-bottom: 1px solid #7F9DB9;
	border-right: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
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

.inputTable th {
	font-weight:normal;
	background-color:#C3DAF9;
}
	
	</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="clinicPhysical" action="${ctx}/clinic/clinicPhysical/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>                   
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>	   
<input type="hidden" name="register.id" value="${registerId}" >
	<div id="a" class="o" >
		<h3 align="center">上海开元司法鉴定中心<br/>
		    临床取（采）样 表</h3>
		<h5 style="margin-left:484px;" id="lover1" >检案编号：赣(景)[${simple}]物鉴字第${casecode}号</h5>	
	</div>
		
		
			<div class="control-group">
				<div>
					<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-left: 12px;">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>编号</th>
								<th>材料名称</th>
								<th>材料数量</th>
								<th>材料类型</th>
								<th>描述</th>
							</tr>
						</thead>
						<c:forEach items="${clinicPhysicalIteam}" var="clinicRegisterphyList"  varStatus="kl">
						<tr>
							<td class="hide">
								<input name="clinicPhysicalIteamList[${kl.index}].id" type="hidden" value="${row.id}"/>
								<input name="clinicPhysicalIteamList[${kl.index}].delFlag" type="hidden" value="0"/>
							</td>
							<td><input  name="clinicPhysicalIteamList[${kl.index}].code" type="text" value="${clinicRegisterphyList   .code}" maxlength="255" class="input-small "/></td>
							<td><input  name="clinicPhysicalIteamList[${kl.index}].name" type="text" value="${clinicRegisterphyList.name}" maxlength="255" class="input-small "/></td>
							<td><input  name="clinicPhysicalIteamList[${kl.index}].quantity" type="text" value="${clinicRegisterphyList.quantity}" maxlength="255" class="input-small "/></td>
							<td><input  name="clinicPhysicalIteamList[${kl.index}].type" type="text" value="${clinicRegisterphyList.type}" maxlength="255" class="input-small "/></td>
							<td><input  name="clinicPhysicalIteamList[${kl.index}].remarks" type="text" value="${clinicRegisterphyList.remarks}" maxlength="255" class="input-small "/></td>
						</tr>
						</c:forEach>
						
						
					</table>
				</div>
			</div>
		<div class="form-actions" id="lover" >
			<input class="btn"   type="button" value="打印" onclick="window.focus();window.print()" />
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>