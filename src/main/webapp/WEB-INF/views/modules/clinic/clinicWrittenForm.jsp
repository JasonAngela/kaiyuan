<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医成文修改管理</title>
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
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
.control-label{
}

.h-left{
display:inline-block;
line-height: 40px;
}
	</style>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="${ctx}/clinic/clinicWritten/form?id=${clinicWritten.id}">
				法医成文修改<shiro:hasPermission name="clinic:clinicWritten:edit">
				${not empty clinicWritten.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="clinic:clinicWritten:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clinicWritten" action="${ctx}/clinic/clinicWritten/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">    
		<sys:message content="${message}"/>
<div  style="width: 930px;" id="dic">		
	<h4 align="center">江西景盛司法鉴定中心<br/>
		法医临床司法鉴定意见书
	</h4>		
	 <h5 align="right">景盛[${datecode }]伤鉴字第[${casecode}]号</h5>		
<h4>一:基本情况</h4>
		<div class="control-group">
			<div class="controls">
			<h4 class="h-left" style="margin-left: 20px;"> 委托方:</h4> <form:input path="delegate" htmlEscape="false" class="input-small"/>
			<br/>
			<h4 class="h-left">委托事项:</h4>
			 <form:select path="toaccept" class="">
					<form:options items="${fns:getDictList('fayitypeCode')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			<br/>
			<h4 class="h-left">受理日期:</h4> <form:input path="acceptdate" htmlEscape="false" class="input-small" />
			<br/>
			<h4 class="h-left">鉴定材料:</h4> <form:textarea path="identification"  rows="3" cols="100" cssStyle="margin: 0px; width: 233px; height: 65px;"/>
			<br/>
			<h4 class="h-left">被鉴定人:</h4> <form:textarea path="bysurveyor" rows="3" cols="100" cssStyle="margin: 0px; width: 233px; height: 65px;"/>
			<br/>
			</div>
		</div>		
<h4>二:基本案情</h4>
		<div class="control-group">
			<div class="controls">
				<form:textarea path="opinion" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
<h4>三:资料摘要</h4>		
		<div class="control-group">
			<div class="controls">
				<form:textarea path="clinicthispaper" rows="3" style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
<h4>四:鉴定过程</h4>		
		<div class="control-group">
			<label class="control-label">（一）检验方法</label>
				<div class="controls">
					<form:textarea path="inspectionmethods" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
				</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">（二）鉴定标准</label>
			<div class="controls"> 
				<form:textarea path="appraisalstandard" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">（三）鉴定经过</label>
			</div>
		<div class="control-group">
			<label class="control-label">鉴定日期：</label>
				<div class="controls">
						<form:input path="authorisesurveyor" class="input-small"/>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴定地点：</label>
				<div class="controls">
						<form:input path="identifylocations"/>
				</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">在场人员：</label>
			<div class="controls">
				<form:input path="personnel" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> 
		<div class="control-group">
		 <label class="control-label">主诉：</label>
			<div class="controls">
				<form:textarea path="cc" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查体：</label>
			<div class="controls">
				<form:textarea path="body" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">（四）阅片</label>
			</div>
		<div class="control-group">
			<div class="controls">
				<form:textarea path="reading" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
	<h4>五:分析说明</h4>		
		<div class="control-group">
			<label class="control-label">分析说明：</label>
			<div class="controls">
				<form:textarea path="analysisshows" rows="3" style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
	<h4>六:鉴定意见</h4>	
		<div class="control-group">
			<label class="control-label">鉴定意见：</label>
			<div class="controls">
				<form:textarea path="expertopinion"  style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">鉴定人：</label>
			<div class="controls">
				<form:input path="firstuser" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
			<div class="controls">
				<form:input path="secouduser" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复核人：</label>
			<div class="controls">
				<form:input path="authoriseuser" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>	
		<div class="form-actions">
	 </div>
	 <div style="padding:0;text-align: center;">
			<shiro:hasPermission name="clinic:clinicWritten:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
		</div>
	</form:form>
</body>
</html>
