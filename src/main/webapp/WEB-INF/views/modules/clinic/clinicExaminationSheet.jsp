<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员验伤管理</title>
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
				</script>
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
		width: 930px;
		    background-color: #F0FFFF;
		    padding-top: 45px;
		    margin: -45px auto auto;
		    }
		.nav-tabs{
		background-color: #F0FFFF;
		}
		.td-span{
		margin:0;
		width:84px;
		}
		.form-actions{
			text-align: center;
			}
		.inputTable {
			font-size:12px;
			border-collapse:collapse; 
			border-top: 1px solid #7F9DB9;
			border-left: 1px solid #7F9DB9;
		}

		.inputTable td, .inputTable th {
			text-align:left;
			border-bottom: 1px solid #7F9DB9;
			border-right: 1px solid #7F9DB9;
			padding-left:18px;
			line-height:50px;
		}

		.inputTable th {
			font-weight:normal;
			background-color:#C3DAF9;
		}
		table { border-collapse:collapse;box-sizing: border-box; }
		.table1 { padding: 0;margin: auto;border: 1px solid #7f9db9;font-size: 12px;border-collapse:collapse;  }
		.table1 th, .table1 td { margin: 0;padding: 0;line-height: 50px;box-sizing: border-box; }
		.table1 th { background-color:#C3DAF9;border-right: 1px solid #7f9db9;border-bottom: 1px solid #7f9db9; }
		.table1 td { padding-left: 10px;border-right: 1px solid #7f9db9;border-bottom: 1px solid #7f9db9; }
		.table2 { padding: 0;font-size: 12px;  }
		.table2 th, .table2 td { border: 0;margin: 0;line-height: 50px;border-bottom: 0;border-right: 1px solid #7f9db9;box-sizing: border-box; }
		.table2 td:last-child { border-right: 0; } 
			</style>
		</head>
		<body>
			<form:form id="inputForm" modelAttribute="clinicExamination" action="${ctx}/clinic/clinicExamination/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="act.taskId"/>
				<form:hidden path="act.taskName"/>
				<form:hidden path="act.taskDefKey"/>
				<form:hidden path="act.procInsId"/>
				<form:hidden path="act.procDefId"/>
				<form:hidden id="flag" path="act.flag"/>
				<sys:message content="${message}"/>	  
				<div id="a" class="o" >
		<h3 align="center">上海开元司法鉴定中心<br/>
		    临床取（采）样 表</h3>
		<h5 style="margin-left:584px;" id="lover1" >检案编号：赣(景)[${simple}]物鉴字第${casecode}号</h5>	
	</div>
				<input type="hidden" name="register.id" value="${registerId}">                         	
					<table width="800px" border="0" cellspac align="center" height="730" class="table1">
						 	<tr>
								<th style="width: 50px;">被鉴定人</th>
								<td>
									<table width="100%" align="center" class="table2">
										<tr>
											<td width="20%"><form:input path="name" htmlEscape="false" class="required input-small"/></td>
											<th width="21%"> 性别</th>
											<td width="19%">
											<form:select path="sex">
												<form:option value="男" label="男"></form:option>
												<form:option value="女" label="女"></form:option>
											</form:select>
											</td>
											<th width="20%">检查时间</th>
											<td width="20%"><form:input path="checkTime"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});" class="input-small"/></td>
										</tr>
									</table>
								</td>
							</tr>
							 <tr height="50">
									<th width="120" >检查地点</th>
									<td>
										<table width="100%" align="center" class="table2">
											<tr>
												<td width="20%"><form:input path="checkAddress" htmlEscape="false" class="input-small"/></td>
												<th width="20%">检查人</th>
												<td width="60%"><form:input path="clinicSurveyor" htmlEscape="false" class="input-small"/></td>
											</tr>
										</table>
									</td>
									<!--<td colspan="2"><form:input path="checkAddress" htmlEscape="false"/></td>
									<th width="120" >检查人</th>
									<td colspan="2"><form:input path="clinicSurveyor" htmlEscape="false"/></td>-->
									
									
								</tr>
									<tr>
									<th width="120">检查方法</th>
									<td>《法医临床检验规范（SF/Z JD 0103003-5011）》</td> 
									
									
									    
								</tr>
								<tr>
									<th width="120"  >主诉</th>
									<td>
									<form:textarea path="cc" htmlEscape="false" rows="3" cols="100" cssStyle="margin: 0px; width: 625px; height: 74px;"/></td>
								</tr>
								
								
								<tr>
								<th rowspan="5">体格检查</th>
								</tr>
								<tr>
									<td  colspan="4"><span class="td-span">一般情况： </span> <form:textarea path="situation" htmlEscape="false" rows="3"  cssStyle="margin: 0px; width: 562px; height: 54px;"/></td>
								</tr>
								<tr>
								 	<td  colspan="4"><span  class="td-span">头面部：</span> <form:textarea path="headFace" htmlEscape="false" rows="3" cssStyle="margin: 0px; width: 562px; height: 54px;"/></td>
								</tr>
								<tr>
								    <td  colspan="4"><span class="td-span">躯干：</span> <form:textarea path="trunk" htmlEscape="false" rows="3"  cssStyle="margin: 0px; width: 562px; height: 54px;"/></td>
								</tr>
								<tr>
									<td  colspan="4"><span class="td-span">四肢：</span> <form:textarea path="limbs" htmlEscape="false" rows="3"  cssStyle="margin: 0px; width: 562px; height: 54px;"/></td>
								</tr>
								
								
								<tr>
									<th >其他</th>
									<td colspan="4">
									<form:textarea path="other" htmlEscape="false" rows="3" cssStyle="margin: 0px; width: 625px; height: 75px;" /></td>
								</tr>
								<tr>
									<th >阅片</th>
									<td colspan="4">
									<form:textarea path="reading" htmlEscape="false" rows="3" cssStyle="margin: 0px; width: 625px; height: 74px;"/></td>
								</tr>
								<tr>
									<th >复核签发</th>
									<td colspan="4">
									<form:textarea path="douCheck"  rows="3" cssStyle="margin: 0px; width: 625px; height: 72px;"/></td>
								</tr> 
								
					</table>
				<div class="form-actions" style="padding:0">
					<input class="btn"   type="button" value="打印" onclick="window.focus();window.print()" />
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
		</body>
		</html>