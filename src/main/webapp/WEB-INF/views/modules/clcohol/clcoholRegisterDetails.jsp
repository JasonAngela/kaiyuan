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
	<form:form id="inputForm" modelAttribute="clcoholRegister" action="${ctx}/clcohol/clcoholRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div id="a" class="o">
		<h1 align="center">江西景盛司法鉴定中心<br/>
		司法鉴定委托书</h1>
		<h6 style="margin-left:698px;" id="lover1" >赣(景)[${simple}]物鉴字第${casecode}号</h6>	
	</div>
		<table width="900" align="center" height="730" class="inputTable">
			<tr>
				<th width="120">委 托 人</th>
				<td colspan="2"><form:input path="clientname" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<th width="120">送检人</th>
				<td colspan="2"><form:input path="makesPeople" htmlEscape="false"/></td>
			</tr>
		 <tr>
				<th  width="120">委托日期</th>
				<td colspan="2">
				<form:input path="entrustdate"/>
				<th  width="120">联系电话</th>
				<td colspan="2"><form:input path="contactphone" htmlEscape="false" class=" required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<th width="50" rowspan="2">捡案摘要</th>
				<td  colspan="4"> 	被鉴定人姓名:
			<form:input path="name"/>
				性别:
				 <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""  />
				证件号   :
			<form:input path="idnumber" htmlEscape="false"/>
			</td>
			</tr>	
			<tr>
					<td colspan="5">
				法医毒物鉴定
<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '1'}">checked="checked"</c:if> name="type" value="1" />血液乙醇检测  &nbsp; &nbsp; &nbsp;
<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '0'}">checked="checked"</c:if> name="type" value="0" />其他：<form:input path="other"/>
					</td>
			</tr>      
			
			<tr>
				<td colspan="5">报告方式:<br/>
				<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '1'}">checked="checked"</c:if> name="waytosend" value="1" />
				自取（凭被鉴定人身份证、委托书第二联或其他有效凭证领取鉴定文书）<br/>
				<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '0'}">checked="checked"</c:if> name="waytosend" value="0" />
				邮寄   &nbsp; &nbsp; &nbsp;
					邮编：
					<form:input path="zipcode"/>
					&nbsp; &nbsp; &nbsp;
					地址与收件人：  <form:input path="address"/>
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
				<form:input path="withdrawal"/>
				理由：         <form:input path="reson"/>
  本鉴定所意见：
				<form:input path="opinion"/>            。<br/>
6.鉴定时间从协议签订之日起
				<form:input path="signed"/>      个工作日完成。其他需延长鉴定时限的，由双方另行商定。<br/>
7.鉴定收费：<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '1'}">checked="checked"</c:if> name="material" value="1" />标准
				<form:input path="standardfee"/>      元；
<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '2'}">checked="checked"</c:if> name="material" value="2" />协议：
				<form:input path="specialfee"/>         元；
<input type="radio" <c:if test="${fn:trim(clcoholRegister.material) eq '3'}">checked="checked"</c:if> name="material" value="3" />疑难复杂：
				<form:input path="totalfee"/>     元。
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

		<div class="form-actions" style="padding:0;text-align: center;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>