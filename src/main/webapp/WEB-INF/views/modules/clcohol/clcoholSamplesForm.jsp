 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精领取样品管理</title>
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
		<li><a href="${ctx}/clcohol/clcoholSamples/">酒精领取样品列表</a></li>
		<li class="active"><a href="${ctx}/clcohol/clcoholSamples/form?id=${clcoholSamples.id}">酒精领取样品<shiro:hasPermission name="clcohol:clcoholSamples:edit">${not empty clcoholSamples.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clcohol:clcoholSamples:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholSamples" action="${ctx}/clcohol/clcoholSamples/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<input type="hidden" name="register.id" value="${registerId}">
		<div id="a" class="o" >
		<h3 align="center">上海开元司法鉴定中心<br/>
		   酒精领取单</h3>
	</div>
	
<table width="7 70" align="center" height="28" class="inputTable">
			<tr>
								<th  style="width: 146px;">样品编号</th>
								<th  style="width: 131px;">姓名</th>
								<th >取样量</th> 
							</tr>
								<tr>
										<input name="clcoholSamplesIteamList[0].id" type="hidden" value="${row.id}"/>
										<input name="clcoholSamplesIteamList[0].delFlag" type="hidden" value="0"/>
										<input name="clcoholSamplesIteamList[1].id" type="hidden" value="0"/>
										<input name="clcoholSamplesIteamList[1].delFlag" type="hidden" value="0"/>			
									<td><input  name="clcoholSamplesIteamList[0].other" type="text"  maxlength="255"  value="${codeA}"/></td>
									<td style="width: 341px;"><input  name="clcoholSamplesIteamList[0].name" type="text"  maxlength="255" class="input-small "value="${nameA }"/></td>
									<td ><input  name="clcoholSamplesIteamList[0].idnumber" type="text"  maxlength="255"class="input-small"   /></td>
									
								</tr>
								<tr>
									<td><input  name="clcoholSamplesIteamListList[1].other" type="text"  maxlength="255" value="${codeB}"   /></td>
									<td style="width: 131px;"><input  name="clcoholSamplesIteamList[1].name" type="text"  maxlength="255"  class="input-small " value="${nameB }"/></td>
									<td><input  name="clcoholSamplesIteamList[1].idnumber" type="text"  maxlength="255" class="input-small "/></td>
								</tr>
					</table>
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="clcohol:clcoholSamples:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>