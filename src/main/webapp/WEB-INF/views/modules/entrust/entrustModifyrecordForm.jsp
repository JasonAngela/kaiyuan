<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报告修改记录管理</title>
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
	<script src="${ctxStatic}/common/artDialog.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil.js" type="text/javascript"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/entrust/entrustModifyrecord/">报告修改记录列表</a></li>
		<li class="active"><a href="${ctx}/entrust/entrustModifyrecord/form?id=${entrustModifyrecord.id}">报告修改记录<shiro:hasPermission name="entrust:entrustModifyrecord:edit">${not empty entrustModifyrecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="entrust:entrustModifyrecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
		<sys:message content="${message}"/>		
		
	<div class="control-group">
			<label class="control-label">修改意见：</label>
			<div id="icon" style="display: none; position: absolute;" title="添加批注"><img src="${ctxStatic}/images/tips.png" class="tipsIcon"></div>
			<div class="controls content">
			${entrustModifyrecord.modefy}
			</div>
			<div class="list"></div>
		</div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
</body>
</html>