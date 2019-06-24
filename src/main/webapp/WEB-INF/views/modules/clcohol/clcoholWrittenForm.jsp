 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精成文修改管理</title>
	<meta name="decorator" content="default"/>
	<style>
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
width: 630px;
    background-color: #F0FFFF;
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
.h-left{
display:inline-block;
line-height: 40px;
}
.h-height{
line-height: 40px;
}
	</style>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clcohol/clcoholWritten/form?id=${clcoholWritten.id}">酒精成文修改<shiro:hasPermission name="clcohol:clcoholWritten:edit">${not empty clcoholWritten.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clcohol:clcoholWritten:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholWritten" action="${ctx}/clcohol/clcoholWritten/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">
		<input type="hidden" name="other1" value="${user.name}" id="userId"> 
		<sys:message content="${message}"/>		
<div  style="width: 630px;" id="pagecontent" >		
	<h4 align="center">江西景盛司法鉴定中心<br/>
		司法鉴定乙醇含量检测报告书
	</h4>		
	 <h5 align="right">景盛[${simple}]伤鉴字第[${casecode}]号</h5>	
		 <h4>一、基本情况</h4>
		<div class="control-group">
			<div >
			<h4 class="h-left">委托方:</h4> ${clcoholFirst.entrust}
			<br/>
			<h4 class="h-left">委托鉴定事项:</h4>
				血液中乙醇定性定量分析
			<br/>
			<h4 class="h-left">受理日期:</h4> ${clcoholFirst.acceptDate}
			<br/>
			<h4 class="h-left">鉴定材料:</h4> ${clcoholFirst.testingMaterials}
			<br/>
			<%-- <h4  class="h-left">鉴定日期:</h4> ${clcoholFirst.other}
			<br/>
			<h4 class="h-left">鉴定地点:</h4> 江西景盛司法鉴定中心
			<br/>
			<h4 class="h-left">在场人员:</h4> 本中心工作人员
			<br/> --%>
			<h4 class="h-left">被检测人姓名：</h4> ${clcoholFirst.personBeing}
			<br/>
			</div>
			
<h4 class="h-height">二:基本案情</h4>
		<div class="control-group">
				据委托书介绍：对送检血样进行血液乙醇检测。
		 </div>
	 <h4 class="h-height">三、送检样品情况</h4>		
	 <div class="control-group">
	 	<label class="h-height">（一）检材的一般检验及描述</label>
				${clcoholFirst.sampleStatus}
		</div>
	 <div class="control-group">
			<label class="h-height">（二）检验</label>
				检材经处理后采用GC-FID进行检测。<br/>
				本次检验方法采用《血液酒精含量的检验方法》GA/T842-2009对送检检材进行血液酒精含量检验。
		</div>
		<div class="control-group">
			<label class="h-height">（三）仪器</label>
			<div class="h-height"> 
				中惠普顶空进样器（HS-5）、天美气相分析仪（GC7980）
			</div>
		</div> 
		
		 <div class="control-group">
		<label class="h-height">（四）检测经过</label>
				<form:textarea path="basicFacts" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
		</div>  
		
		
	<h4 class="h-height">五、检测结果</h4>
		  <div class="control-group">
		  	<form:textarea path="testResult" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
		</div>
	
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="clcohol:clcoholWritten:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>