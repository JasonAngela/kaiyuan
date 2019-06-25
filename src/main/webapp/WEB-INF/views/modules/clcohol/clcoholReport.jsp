 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精成文修改管理</title>
	<meta name="decorator" content="default"/>
	<style>
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
width: 630px;
    background-color: #F0FFFF;
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
.h-left{
display:inline-block;
line-height: 40px;
}
.h-height{
line-height: 40px;
}
	</style>
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
		酒精正稿
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholWritten" action="${ctx}/clcohol/clcoholWritten/save" method="post" class="form-horizontal">
<div  style="width: 630px;" id="pagecontent" >		
	<h4 align="center">上海开元司法鉴定中心<br/>
		司法鉴定乙醇含量检测报告书
	</h4>		
	 <h5 align="right">开元[${simple}]伤鉴字第[${casecode}]号</h5>	
		 <h4>一、基本情况</h4>
		<div class="control-group">
			<div >
			<h4 class="h-left">委托单位:</h4> ${clcoholFirst.entrust}
			<br/>
			<h4 class="h-left">委托鉴定事项:</h4>
				血液中乙醇定性定量分析
			<br/>
			<h4 class="h-left">受理日期:</h4> ${clcoholFirst.acceptDate}
			<br/>
			<h4 class="h-left">鉴定材料:</h4> ${ clcoholFirst.testingMaterials}
			<br/>
			<h4  class="h-left">鉴定日期:</h4> ${clcoholFirst.other}
			<br/>
			<h4 class="h-left">鉴定地点:</h4> 上海开元司法鉴定中心
			<br/>
			<h4 class="h-left">在场人员:</h4> 本中心工作人员
			<br/>
			<h4 class="h-left">被鉴定人：</h4> ${clcoholFirst.personBeing}
			<br/>
			</div>
			
<h4 class="h-height">二、检案摘要</h4>
		<div class="control-group">
					${clcoholWritten.basicFacts }
		 </div>
	 <h4 class="h-height">三、检验过程和检验方法</h4>		
	 <div class="control-group">
	 	<label class="h-height">（一）检材的一般检验及描述</label>
				${clcoholFirst.sampleStatus}
		</div>
	 <div class="control-group">
			<label class="h-height">（二）检验</label>
				检材经处理后采用GC-FID进行检测。<br/>
				本次检验方法采用《血液酒精含量的检验方法》GA/T842-2009对送检检材进行血液酒精含量检验。
		</div>
	 <h4>四、鉴定意见</h4>
		  <div class="control-group">
		  	${clcoholWritten.workInstruction }
		</div>
	 <h5>五、落款</h5>	
				司法鉴定人副主任技师：文金莲<br/>
				《司法鉴定人执业证》证号：360113012030<br/>
				司法鉴定人主管技师：吴冠军<br/>
				《司法鉴定人执业证》证号：360113012029<br/>
		
		
		<div class="form-actions" style="padding:0;text-align: center;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>