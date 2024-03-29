<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备使用记录管理</title>
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
		<li><a href="${ctx}/synth/synthEquipmentUsageRecord/">设备使用记录列表</a></li>
		<li class="active"><a href="${ctx}/synth/synthEquipmentUsageRecord/form?id=${synthEquipmentUsageRecord.id}">设备使用记录<shiro:hasPermission name="synth:synthEquipmentUsageRecord:edit">${not empty synthEquipmentUsageRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="synth:synthEquipmentUsageRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="synthEquipmentUsageRecord" action="${ctx}/synth/synthEquipmentUsageRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">选择设备：</label>
			<div class="controls">
				<form:select path="equipment.id">
					<form:options items="${equipmentList }" itemLabel="name" itemValue="id"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用人：</label>
			<div class="controls">
				<sys:treeselect id="operator" name="operator.id" value="${synthEquipmentUsageRecord.operator.id}" labelName="" labelValue="${synthEquipmentUsageRecord.operator.id}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用时间：</label>
			<div class="controls">
				<input name="operateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${synthEquipmentUsageRecord.operateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="synth:synthEquipmentUsageRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>