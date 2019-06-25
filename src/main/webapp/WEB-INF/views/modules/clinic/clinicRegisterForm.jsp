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
		});
		
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
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
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
	.form-actions{
	padding-left:0;
	text-align: center;
	}

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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/clinic/clinicRegister/">临床登记列表</a></li>
		<li class="active"><a href="${ctx}/clinic/clinicRegister/form?id=${clinicRegister.id}">
			临床登记<shiro:hasPermission name="clinic:clinicRegister:edit">${not empty clinicRegister.id?'修改':'添加'}
			</shiro:hasPermission><shiro:lacksPermission name="clinic:clinicRegister:edit">查看</shiro:lacksPermission>
			</a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clinicRegister" action="${ctx}/clinic/clinicRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
			<h1 align="center">上海开元司法鉴定中心<br/>
		司法鉴定委托书</h1>
		<h6 style="margin-left: 696px;" id="lover1" >开元[${simple}]伤鉴字第${casecode}号</h6>
	<table width="900" align="center" height="730" class="inputTable">
			<tr>
				<th width="120">委 托 人</th>
				<td colspan="2"><form:input path="clientName" htmlEscape="false" class="input-small required"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<th width="120">送检人</th>
				<td colspan="2"><form:input path="agentName" htmlEscape="false" class="input-small"/></td>
			</tr>
		 <tr>
				<th  width="120">委托日期</th>
				<td colspan="2">
				<form:input path="clientZipcode" value="${clientZipcode}" class="input-small" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/></td>
				<th  width="120">联系电话</th>
				<td colspan="2"><form:input path="agentTel" htmlEscape="false" class="input-small  required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<th width="50" rowspan="2">捡案摘要</th>
				<td  colspan="4"> 姓名:
			<form:input path="surveyorName" class="input-small"/>
				性别:
				 <form:radiobuttons path="surveyorSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""  />
				<br/>
				出生年月: 
				<form:input path="surveyorBirthday" class="input-small" onclick="WdatePicker({dateFmt:'yyyy年MM月',isShowClear:false});"/>
				身份证:
				<form:input path="idCard" />
			</td>
			</tr>	
			<tr>
			<td colspan="4"> <form:textarea path="clientReceiver" rows="1" cols="220"/> </td>
			</tr>
			<tr>
					<td colspan="5">
				委托事项:<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.type) eq '1'}">checked="checked"</c:if> name="typeq" value="1"  />残疾鉴定&nbsp;&nbsp;
				<input type="radio" <c:if test="${fn:trim(clinicRegister.type) eq '2'}">checked="checked"</c:if> name="typeq" value="2" /> 损伤鉴定&nbsp;&nbsp;
				<input type="radio" <c:if test="${fn:trim(clinicRegister.type) eq '3'}">checked="checked"</c:if> name="typeq" value="3" /> 病理FS&nbsp;&nbsp;				  								
				<input type="radio" <c:if test="${fn:trim(clinicRegister.type) eq '4'}">checked="checked"</c:if> name="typeq" value="4" />病理FA		
					<span class="help-inline"><font color="red">*</font> </span>
					<br/>
				鉴定类别:<form:radiobuttons path="clientEmail" items="${fns:getDictList('emailCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				<br/>
					</td>
			</tr> 
			
			<tr>
				<td colspan="5">鉴定材料:<br/>
					<input type="checkbox" <c:if test="${fn:trim(clinicRegister.clientArea) eq '1'}"> checked="checked"</c:if> name="clientArea" value="1" />
					验伤通知书 <form:input path="clinicTriage" class="input-small"/>  份，病史 <form:input path="clinicMedical" class="input-small"/>  份，出院小结
					<form:input path="clinicSummary" class="input-small"/> 份，X片 <form:input path="clinicXray" class="input-small"/>  张，CT  <form:input path="clinicCt" class="input-small"/> 张，MRI
					<form:input path="clinicMri" class="input-small"/> 张
				<br/>
				<input type="checkbox" <c:if test="${fn:trim(clinicRegister.clientArea) eq '0'}"> checked="checked"</c:if> name="clientArea" value="2" class="input-small"/>
				其他: <form:input path="appraisalItem" class="input-small"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="5">报告方式:<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.sendMode) eq '1'}">checked="checked"</c:if> name="sendMode" value="1" />
				自取（凭被鉴定人身份证、委托书第二联或其他有效凭证领取鉴定文书）<br/>
				<input type="radio" <c:if test="${fn:trim(clinicRegister.sendMode) eq '2'}">checked="checked"</c:if> name="sendMode" value="2" />
				邮寄   &nbsp; &nbsp; &nbsp;
					邮编：
					<form:input path="mattersEntrusted"/>
					&nbsp; &nbsp; &nbsp;
					地址与收件人：  <form:input path="clientAddress"/>
			</td>
			</tr>
		<tr>
			<td colspan="5">
			约定事项：<br/> 
1.鉴定工作严格按照《司法鉴定程序通则》规定，并按国家标准、行业技术标准和技术规范或专业领域认可的技术方法进行鉴定。<br/>
2.本鉴定所仅对送检材料和检验工作负责。因委托人或送检人提供虚假情况或不真实材料而产生的后果，本鉴定所不承担相应责任。送检材料经本鉴定所的有效方法进行存档保存。有约定者从约定。<br/>
3.签订委托后，经审查，存在《司法鉴定程序通则》第二十九条情形之一的，本鉴定所有权终止委托的履行，如①出现不可抗力致使鉴定无法继续进行的；②确需补充鉴定材料而无法补充的；③发现自身难以解决的技术问题的；④委托人拒不履行相关义务，被鉴定人拒不配合或鉴定活动受到严重影响，无法继续进行鉴定活动等。<br/>
4.委托人要求终止委托的，鉴定费不予退还。<br/>
5.<input type="checkbox" <c:if test="${fn:trim(clinicRegister.timeLimitResult) eq '1'}">checked="checked"</c:if> name="timeLimitResult" value="1" />
				委托人申请鉴定人回避。申请回避鉴定人：
				<form:input path="timeLimitReport" class="input-small"/>
				理由：         <form:input path="specialty" class="input-small"/>
  本鉴定所意见：
				<form:input path="clientFax" class="input-small"/>            。<br/>
6.鉴定时间从协议签订之日起
				<form:input path="materialDispose" class="input-small"/>      个工作日完成。其他需延长鉴定时限的，由双方另行商定。<br/>
7.鉴定收费：<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '1'}">checked="checked"</c:if> name="material" value="1" />标准
				<form:input path="standardFee" class="input-small"/>      元；
<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '2'}">checked="checked"</c:if> name="material" value="2" />协议：
				<form:input path="specialFee" class="input-small"/>         元；
<input type="radio" <c:if test="${fn:trim(clinicRegister.material) eq '3'}">checked="checked"</c:if> name="material" value="3" />疑难复杂：
				<form:input path="totalFee" class="input-small"/>     元。
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
		<h3 align="center">材料登记</h3>
		<div class="control-group">
				<div >
					<table id="contentTable" class="table table-striped table-bordered table-condensed" >
						<thead>
							<tr>
								<th class="hide"></th>
								<th>类型</th>
								<th>名称</th>
								<th>数量</th>
								<th>图片</th>
								<th>备注</th>
								<shiro:hasPermission name="clinic:clinicRegister:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="clinicRegisterphyList">
						</tbody>
						<shiro:hasPermission name="clinic:clinicRegister:edit"><tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#clinicRegisterphyList', clinicRegisterphyRowIdx, clinicRegisterphyTpl);clinicRegisterphyRowIdx = clinicRegisterphyRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="clinicRegisterphyTpl">//<!--
						<tr id="clinicRegisterphyList{{idx}}">
							<td class="hide">
								<input id="clinicRegisterphyList{{idx}}_id" name="clinicRegisterphyList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="clinicRegisterphyList{{idx}}_delFlag" name="clinicRegisterphyList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="clinicRegisterphyList{{idx}}_type" name="clinicRegisterphyList[{{idx}}].type" type="text" value="{{row.type}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clinicRegisterphyList{{idx}}_name" name="clinicRegisterphyList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clinicRegisterphyList{{idx}}_quantity" name="clinicRegisterphyList[{{idx}}].quantity" type="text" value="{{row.quantity}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clinicRegisterphyList{{idx}}_uploud" name="clinicRegisterphyList[{{idx}}].uploud" type="hidden" value="{{row.uploud}}" maxlength="255"/>
								<sys:ckfinder input="clinicRegisterphyList{{idx}}_uploud" type="files" uploadPath="/clinic/clinicRegister" selectMultiple="true"/>
							</td>
							<td>
								<textarea id="clinicRegisterphyList{{idx}}_remarks" name="clinicRegisterphyList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="clinic:clinicRegister:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#clinicRegisterphyList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var clinicRegisterphyRowIdx = 0, clinicRegisterphyTpl = $("#clinicRegisterphyTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(clinicRegister.clinicRegisterphyList)};
							for (var i=0; i<data.length; i++){
								addRow('#clinicRegisterphyList', clinicRegisterphyRowIdx, clinicRegisterphyTpl, data[i]);
								clinicRegisterphyRowIdx = clinicRegisterphyRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		

		<div class="form-actions" style="padding:0">
			<shiro:hasPermission name="clinic:clinicRegister:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>