<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费凭据管理</title>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/entrust/entrustChargeCredentials/">收费凭据列表</a></li>
		<li class="active"><a href="${ctx}/entrust/entrustChargeCredentials/form?id=${entrustChargeCredentials.id}">收费凭据<shiro:hasPermission name="entrust:entrustChargeCredentials:edit">${not empty entrustChargeCredentials.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="entrust:entrustChargeCredentials:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="entrustChargeCredentials" action="${ctx}/entrust/entrustChargeCredentials/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="entrustId" value="${entrustId}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">pic：</label>
			<div class="controls">
				<form:hidden id="pic" path="pic" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="pic" type="files" uploadPath="/entrust/entrustChargeCredentials" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>