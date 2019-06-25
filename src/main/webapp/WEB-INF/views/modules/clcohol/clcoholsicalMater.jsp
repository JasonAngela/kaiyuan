<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精物证管理</title>
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
		
	     function  window.onload() {
		  factory.printing.header = "";       //页眉
		  factory.printing.footer = "";       //页脚
		  factory.printing.portrait = false;    //true为纵向打印，flase为横向打印
		  factory.printing.leftMargin = 1.0; //左页边距
		  factory.printing.topMargin = 1.0;    //上页边距
		  factory.printing.rightMargin = 1.0;//右页边距
		  factory.printing.bottomMargin = 1.0; //下页边距
		  
		}
		
	</script>
	
	<style media=print>
   .form-actions{display:none;}
   
</style>
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
width: 750px;
    background-color: #F0FFFF;
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
			
		.table {
	font-size:12px;
	border-collapse:collapse;
	border-top: 1px solid #7F9DB9;
	border-left: 1px solid #7F9DB9;
}

 td{ text-align:center;}

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
	<form:form id="inputForm" modelAttribute="clcoholEvidence" action="${ctx}/clcohol/clcoholEvidence/save" method="post" class="form-horizontal">
		<h3 align="center">上海开元司法鉴定中心<br/>
		    法医毒化室取（采）样 表</h3>
		<h5 style="margin-left:430px;" id="lover1" >检案编号：赣(景)[${simple}]物鉴字第${casecode}号</h5>	
	</div>
	
<table  style="width: 659px; height: 288px;"  align="center" class="inputTable">
			<tr>
				<th width="60" ><font style="al"></font>委 托 方</th>
				
				<td colspan="2"><form:input path="entrust" htmlEscape="false" class="required input-small"/>
				<th  style="width: 116px;">委托日期</th>
				<td colspan="2"><form:input path="entrustDate"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"  class="input-small"/></td>
			</tr>
			<tr>
				<th width="60">取采样地点</th>
				<td colspan="2">
				<form:input path="address" htmlEscape="false" class="input-small" />
                        <form:radiobuttons path="mining" items="${fns:getDictList('miningCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small" /></td>
				<th width="60">取（采）样日期</th>
				<td colspan="2"><form:input path="miningdate" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});" class="input-small"/></td>
			</tr>
			
			<tr> 
				<th width="60">样品类型</th>
				<td colspan="6"><input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '1'}">checked="checked"</c:if> name="type" value="1" />血液乙醇检测  &nbsp; &nbsp; &nbsp;
<input type="radio" <c:if test="${fn:trim(clcoholRegister.waytosend) eq '0'}">checked="checked"</c:if> name="type" value="0" />其他：<form:input path="other"/>
				</td>
			</tr>
			<tr> 
				<th width="60">样品状态</th>
				<td colspan="6">
					  <form:radiobuttons path="sampleState" items="${fns:getDictList('sampleStateCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="5">
					送检人签名:<%-- <form:input path="inspection" htmlEscape="false"/> --%>__________\________
				          联系电话:<form:input path="inspectionNumber" htmlEscape="false" maxlength="255" class="input-small"/>
				</td>
			</tr>
							<tr>
								<th  >样品编号</th>
								<th  style="width: 211px;" >姓名</th>
								<th  colspan="4">证件号</th> 
							</tr>
							<tr>
										<td><input  name="clcoholEvidenceIteamList[0].code" type="text"  style="width: 100px;" value="${codeA}" class="input-small"/></td>
										<td ><input  name="clcoholEvidenceIteamList[0].name" type="text" class="input-small"  value="${nameA}"  /></td>
										<td colspan="4" ><input  name="clcoholEvidenceIteamList[0].idnumber"   type="text"  value="${idnumberA}"/></td>
							</tr>
							<tr>
										<td><input  name="clcoholEvidenceIteamList[1].code" type="text" style="width: 100px;" value="${codeB}" class="input-small"/></td>
										<td ><input  name="clcoholEvidenceIteamList[1].name" type="text"   class="input-small" value="${nameB}" /></td>
										<td colspan="4"><input  name="clcoholEvidenceIteamList[1].idnumber" type="text"   value="${idnumberB}"/></td>
							</tr>
					</table>
		<div class="form-actions" style="padding:0;text-align: center;">
			<input class="btn"   type="button" value="打印" onclick="window.focus();window.print()" />
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>