                                                                                                                                                                                      <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>临床登记管理</title>
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
			$("a.jquery-word-export").click(function(event) {
		        $("#pagecontent").wordExport();
		    });
		});
		  function printEntrust(){
				$("#lover").hide();
				$("#lover1").show();
				$("#a").show();
				$("#b").show();
				window.print();
				$("#lover").show();
				$("#lover1").hide();
				$("#a").hide();
				$("#b").hide();
			}
	</script>
		<style type="text/css">
	 body{  
            margin:0;  
            padding:0;  
            background-color:#C1CDCD;
            height: 200px;  
            width: 200px;  
            /*body的背景色是不受body本身的宽高的影响的。  
              body的背景色就是铺满整个页面的。  
            */  
              
        }
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

.inputTable th {
	font-weight:normal;
	background-color:#C3DAF9;
}
body
{
font-family : 微软雅黑,宋体;
font-size : x-large;
}
	#pagecontent{
	
	background-color: #F0FFFF;
	    }
	</style>
	
</head>
<body>
<div id="pagecontent" style="width: 930px;margin-left: 300px;">
	<form:form id="inputForm" modelAttribute="clinicRegister" action="${ctx}/clinic/clinicRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="a" class="o">
		<h1 align="center">江西景盛司法鉴定中心<br/>
		司法鉴定委托书</h1>
		<h6 style="margin-left: 630px;" id="lover1" >赣(景)[${simple}]物鉴字第${casecode}号</h6>	
	</div>
	<table width="830" align="center" height="730" class="inputTable">
			<tr>
				<th width="120">委 托 人</th>
				<td colspan="2"> 
				${clinicRegister.clientName }</td>
				<th width="120">送检人</th>
				<td colspan="2">
				${clinicRegister.agentName }</td>
			</tr>
		 <tr>
				<th  width="120">委托日期</th>
				<td colspan="2">
				${clinicRegister.clientZipcode }</td>
				<th  width="120">联系电话</th>
				<td colspan="2">
				${clinicRegister.agentTel}
				</td>
			</tr>
			<tr>
				<th width="50" rowspan="2">捡案摘要</th>
				<td  colspan="4"> 	被鉴定人姓名:
					${clinicRegister.surveyorName}
				性别:
					${clinicRegister.surveyorSex}
				出生日期:
				${clinicRegister.surveyorBirthday}
			</td>
			</tr>	
			<tr>
			<td colspan="4">
			${clinicRegister.clientReceiver}
			</td>
			</tr>
			<tr>
					<td colspan="5">
				委托事项:<br/>
						<form:radiobuttons path="type" items="${fns:getDictList('fayitypeCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"  />
					</span>
					<br/>
				鉴定类别:<form:checkboxes path="clientEmail" items="${fns:getDictList('emailCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				<br/>
					</td>
			</tr> 
			
			<tr>
				<td colspan="5">鉴定材料:<br/>
					<input type="radio" <c:if test="${fn:trim(clinicRegister.clientArea) eq '1'}">checked="checked"</c:if> name="clientArea" value="1" /> 
					验伤通知书   ${clinicRegister.clinicTriage}  份，病史  ${clinicRegister.clinicMedical} 份，出院小结 ${clinicRegister.clinicSummary} 份，X片${clinicRegister.clinicXray}  张，CT ${clinicRegister.clinicCt} 张，MRI  ${clinicRegister.clinicMri} 张	
				<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.clientArea) eq '1'}">checked="checked"</c:if> name="clientArea" value="2" />
				其他:    ${clinicRegister.appraisalItem}
				</td>
			</tr>
			
			<tr>
				<td colspan="5">报告方式:<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.sendMode) eq '1'}">checked="checked"</c:if> name="sendMode" value="1" />
				自取（凭被鉴定人身份证、委托书第二联或其他有效凭证领取鉴定文书）<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.sendMode) eq '1'}">checked="checked"</c:if> name="sendMode" value="2" />
				邮寄   &nbsp; &nbsp; &nbsp;     邮编：  ${clinicRegister.mattersEntrusted}    &nbsp; &nbsp; &nbsp;      地址与收件人：${clinicRegister.clientAddress}  
			</td>
			</tr>
		<tr>
			<td colspan="5">
			约定事项：
1.鉴定工作严格按照《司法鉴定程序通则》规定，并按国家标准、行业技术标准和技术规范或专业领域认可的技术方法进行鉴定。<br/>
2.本鉴定所仅对送检材料和检验工作负责。因委托人或送检人提供虚假情况或不真实材料而产生的后果，本鉴定所不承担相应责任。送检材料经本鉴定所的有效方法进行存档保存。有约定者从约定。<br/>
3.签订委托后，经审查，存在《司法鉴定程序通则》第二十九条情形之一的，本鉴定所有权终止委托的履行，如①出现不可抗力致使鉴定无法继续进行的；②确需补充鉴定材料而无法补充的；③发现自身难以解决的技术问题的；④委托人拒不履行相关义务，被鉴定人拒不配合或鉴定活动受到严重影响，无法继续进行鉴定活动等。<br/>
4.委托人要求终止委托的，鉴定费不予退还。<br/>
5.<input type="checkbox" <c:if test="${fn:trim(clinicRegister.timeLimitResult) eq '1'}">checked="checked"</c:if> name="timeLimitResult" value="1" /> 委托人申请鉴定人回避。申请回避鉴定人：         ${clinicRegister.timeLimitReport}       理由：      ${clinicRegister.specialty}           
  本鉴定所意见：               ${clinicRegister.clientFax}                     。<br/>
6.鉴定时间从协议签订之日起       ${clinicRegister.materialDispose}       个工作日完成。其他需延长鉴定时限的，由双方另行商定。<br/>
7.鉴定收费：<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '1'}">checked="checked"</c:if> name="material" value="1" />标准      ${clinicRegister.standardFee}      元；
<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '2'}">checked="checked"</c:if> name="material" value="2" />协议：       ${clinicRegister.specialFee}       元；
<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '3'}">checked="checked"</c:if> name="material" value="3" />疑难复杂：            ${clinicRegister.totalFee}    元。
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
		</form:form>
		</div>
		
		<div id="lover" class="form-actions" style="margin-left: 570px">
			<input class="btn" type="button" value="去打印" onclick="printEntrust()"/>
			<a class="jquery-word-export"><input class="btn" type="button" value="导出word"></a>
		</div>

</body>
</html>