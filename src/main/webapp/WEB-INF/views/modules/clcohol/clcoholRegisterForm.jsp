 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精委托书管理</title>
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

.inputTable th {
	font-weight:normal;
	background-color:#C3DAF9;
}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clcohol/clcoholRegister/form?id=${clcoholRegister.id}">酒精委托书<shiro:hasPermission name="clcohol:clcoholRegister:edit">${not empty clcoholRegister.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clcohol:clcoholRegister:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholRegister" action="${ctx}/clcohol/clcoholRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		
	<h1 align="center">江西景盛司法鉴定中心<br/>
		司法鉴定委托书</h1>
		<h6 style="margin-left: 696px;" id="lover1" >景盛[${simple}]鉴/检字第${casecode}号</h6>	
		<table width="900" align="center" height="730" class="inputTable">
			<tr>
				<th width="120">委 托 人</th>
				<td colspan="2"><form:input path="clientname" htmlEscape="false" class="required input-small"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<th width="120">送检人</th>
				<td colspan="2"><form:input path="makesPeople" htmlEscape="false" class="input-small"/></td>
			</tr>
		 <tr>
				<th  width="120">委托日期</th>
				<td colspan="2">
				<form:input path="entrustdate" value="${entrustDate}"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/>
				<th  width="120">联系电话</th>
				<td colspan="2"><form:input path="contactphone" htmlEscape="false" class="required input-small"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<th width="50" rowspan="1">捡案摘要</th>
				<td  colspan="4"> 	姓名:
			<form:input path="name"  class="required input-small"/>
				性别:
				 <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small"  />
				证件号   :
			<form:input path="idnumber" htmlEscape="false"/>
			</td>
			</tr>	
			
			<tr>
			<th width="50" rowspan="2">委托事项</th>
			
			<td colspan="4">
			鉴定类别:<form:radiobuttons path="other5" items="${fns:getDictList('emailCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</td>
			</tr>
			<tr>
					<td colspan="4">
				法医毒物鉴定：
<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '1'}">checked="checked"</c:if> name="type" value="1" />血液乙醇检测  &nbsp; &nbsp; &nbsp;
<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '0'}">checked="checked"</c:if> name="type" value="0" />其他：<form:input path="other"/>
					</td>
			</tr> 
			
			
			
			
			<%-- <tr>
				<td colspan="5">鉴定材料:<br/>
					<input type="radio" <c:if test="${fn:trim(clcoholRegister.clientArea) eq '1'}"> checked="checked"</c:if> name="clientArea" value="1" />
					验伤通知书 <form:input path="clinicTriage"/>  份，病史 <form:input path="clinicMedical"/>  份，出院小结
					<form:input path="clinicSummary"/> 份，X片 <form:input path="clinicXray"/>  张，CT  <form:input path="clinicCt"/> 张，MRI
					<form:input path="clinicMri"/> 张
				<br/>
				<input type="radio" <c:if test="${fn:trim(clcoholRegister.clientArea) eq '0'}"> checked="checked"</c:if> name="clientArea" value="2" />
				其他: <form:input path="appraisalItem"/>
				</td>
			</tr> --%>
			
			<tr>
				<td colspan="5">报告发送:<br/>
				<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '1'}">checked="checked"</c:if> name="waytosend" value="1" />
				自取（凭被鉴定人身份证、委托书第二联或其他有效凭证领取鉴定文书）<br/>
				<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '0'}">checked="checked"</c:if> name="waytosend" value="0" />
				邮寄   &nbsp; &nbsp; &nbsp;
					地址  <form:input path="address"/>	&nbsp; &nbsp; &nbsp;
					邮编：
					<form:input path="zipcode" class="input-small" />
					&nbsp; &nbsp; &nbsp;
					收件人
					<form:input path="other1" class="input-small" />
					
			</td>
			</tr>
		<tr>
			<td colspan="5">
			约定事项：
1.鉴定工作严格按照《司法鉴定程序通则》规定，并按国家标准、行业技术标准和技术规范或专业领域认可的技术方法进行鉴定。<br/>
2.本鉴定所仅对送检材料和检验工作负责。因委托人或送检人提供虚假情况或不真实材料而产生的后果，本鉴定所不承担相应责任。送检材料经本鉴定所的有效方法进行存档保存。有约定者从约定。<br/>
3.签订委托后，经审查，存在《司法鉴定程序通则》第二十九条情形之一的，本鉴定所有权终止委托的履行，如①出现不可抗力致使鉴定无法继续进行的；②确需补充鉴定材料而无法补充的；③发现自身难以解决的技术问题的；④委托人拒不履行相关义务，被鉴定人拒不配合或鉴定活动受到严重影响，无法继续进行鉴定活动等。<br/>
4.委托人要求终止委托的，鉴定费不予退还。<br/>
5.<input type="checkbox" <c:if test="${fn:trim(clcoholRegister.avoid) eq '1'}">checked="checked"</c:if> name="avoid" value="1" />
				委托人申请鉴定人回避。申请回避鉴定人：
				<form:input path="withdrawal" class="input-small"/>
				理由：         <form:input path="reson" class="input-small" />
  本鉴定所意见：
				<form:input path="opinion" class="input-small" />            。<br/>
6.鉴定时间从协议签订之日起
				<form:input path="signed" class="input-small" />      个工作日完成。其他需延长鉴定时限的，由双方另行商定。<br/>
7.鉴定收费：<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '1'}">checked="checked"</c:if> name="material" value="1" />标准
				<form:input path="standardfee" class="input-small" />      元；
<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '2'}">checked="checked"</c:if> name="material" value="2" />协议：
				<form:input path="specialfee" class="input-small" />         元；
<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '3'}">checked="checked"</c:if> name="material" value="3" />疑难复杂：
				<form:input path="totalfee" class="input-small" />     元。
<br/>
风险告知：司法鉴定意见系属专业意见，仅具备证据属性，其最终是否被采纳取决于法官的判断；司法鉴定收费不包含外出交通食宿及出庭质证等费用，如需提供相应服务须与本鉴定所提前约定。<br/>
			
			委托人声明：①鉴定材料合法获得；②鉴定用途合法，不违背社会公德；③不就同一鉴定同时委托其他鉴定机构进行鉴定。委托人知晓上述情况，签名：
			
			委托人               （签名或盖章）             受理鉴定人               （签名和盖章）
                    年    月    日                                       年    月    日
地址：上海市徐汇区漕溪路250号银海大楼A座810－811室；上海市奉贤区航南公路5856号504室      
联系电话： 021-64360528（接待及传真）；投诉电话：021-64360528  
			</td>
			</tr>
			
			
			
		</table>
		
		<div class="control-group">
			<label class="control-label" style="margin-left: 26px;">委托登记材料上传</label>
			
			<div class="controls">
				<form:hidden id="uploud" path="other3" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="uploud" type="files" uploadPath="/clcohol/clcoholRegister" selectMultiple="true"/>
			</div>                      
		</div>
		
		
<%-- 			
		<div class="control-group">
			<label class="control-label">委托事项：</label>
			<div class="controls">
				<form:input path="mattersentrusted" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴定材料：</label>
			<div class="controls">
				<form:input path="fication" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送方式：</label>
			<div class="controls">
				<form:input path="waytosend" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件人：</label>
			<div class="controls">
				<form:input path="recipient" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
				<form:input path="zipcode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请人回避：</label>
			<div class="controls">
				<form:input path="avoid" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">理由：</label>
			<div class="controls">
				<form:input path="reson" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本鉴定所意见：</label>
			<div class="controls">
				<form:input path="opinion" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作人完成：</label>
			<div class="controls">
				<form:input path="complete" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴定收费：</label>
			<div class="controls">
				<form:input path="material" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标准收费：</label>
			<div class="controls">
				<form:input path="standardfee" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特殊收费：</label>
			<div class="controls">
				<form:input path="specialfee" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合计收费：</label>
			<div class="controls">
				<form:input path="totalfee" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其它：</label>
			<div class="controls">
				<form:input path="appraisalitem" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other：</label>
			<div class="controls">
				<form:input path="other" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other1：</label>
			<div class="controls">
				<form:input path="other1" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other2：</label>
			<div class="controls">
				<form:input path="other2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other3：</label>
			<div class="controls">
				<form:input path="other3" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other4：</label>
			<div class="controls">
				<form:input path="other4" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other5：</label>
			<div class="controls">
				<form:input path="other5" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="clcohol:clcoholRegister:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>