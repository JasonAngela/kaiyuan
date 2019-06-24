<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精授权签字人管理</title>
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
	<script src="${ctxStatic}/common/postil2.js" type="text/javascript"></script>
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
	#icona {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}
	
	</style>
	
	
<body>
	<ul class="nav nav-tabs">
		<li class="active">酒精授权签字人</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholAuthorization" action="${ctx}/clcohol/clcoholAuthorization/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">
		<input type="hidden" name="other1" value="${user.name}" id="userId"> 
		<sys:message content="${message}"/>		
<div  style="width: 630px;" id="pagecontent" >		
	<h4 align="center">江西景盛司法鉴定中心<br/>
		司法鉴定乙醇含量检测报告书
	</h4>		
	 <h5 align="right">景盛[${simple}]伤鉴字第[${casecode}]号</h5>	
		 <h4>一:基本情况</h4>
		<div class="control-group">
			<div>
			<h4 class="h-left">委托方:</h4> ${clcoholFirst.entrust}
			<br/>
			<h4 class="h-left">委托鉴定事项:</h4>
				血液中乙醇定性定量分析
			<br/>
			<h4 class="h-left">受理日期:</h4> ${clcoholFirst.acceptDate}
			<br/>
			 <h4 class="h-left">鉴定材料:</h4> ${clcoholFirst.testingMaterials}
			<br/>
			<%--<h4 class="h-left">鉴定日期:</h4> ${clcoholFirst.other}
			<br/>
			<h4 class="h-left">鉴定地点:</h4> 上海景盛生物科技有限公司司法鉴定所
			<br/>
			<h4 class="h-left">在场人员:</h4> 本中心工作人员
			<br/> --%>
			<h4 class="h-left">被检测人姓名：</h4> ${clcoholFirst.personBeing}
			<br/>
			</div>
			
<h4 class="h-height">二:基本案情</h4>
	<div class="control-group">
			<%-- <div id="icon" style="display: none;" position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icona"></div>
			<div class="controls content">
			  ${clcoholSecond.basicFacts}
			  </div>
			<div class="list"></div>
			<form:hidden path="basicFacts" id="content_value"/> --%>
			<div class="h-height"> 
				据委托书介绍：对送检血样进行血液乙醇检测。
			</div>
		</div>
</div>	

 <h4 class="h-height">三、送检样品情况</h4>		
	 <div class="control-group">
				${clcoholFirst.sampleStatus}
		</div>
<h4 class="h-height">四:检验过程</h4>	
		  <div class="control-group">
			<label class="h-height">（一）作业指导</label>
			<div class="h-height"> 
				按照本所（SFD-T-1-2017）《血液乙醇检测-顶空气相分析法作业指导
				<br/>
				书》对送检样品进行检测。
			</div>
		</div> 
		<div class="control-group">
			<label class="h-height">（二）检测标准</label>
			<div class="h-height"> 
				《生物样品血液、尿液中乙醇、甲醇、正丙醇、乙醛、丙酮、异丙醇和正丁醇的
				<br/>
				顶空-气相色谱检验方法（GA/T 1073-2013）》
			</div>
		</div> 
		
		<div class="control-group">
			<label class="h-height">（三）仪器</label>
			<div class="h-height"> 
				中惠普顶空进样器（HS-5）、天美气相分析仪（GC7980）
			</div>
		</div> 
		
	 <div class="control-group">
		<label class="h-height">（四）检测经过</label>
			<div class="control-group">
			<div id="icon" style="display: none;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icona"></div>
			<div class="controls content">
			${clcoholSecond.basicFacts}
			</div>
			<div class="list"></div>
			<form:hidden path="basicFacts" id="content_value"/>
		</div>  
		
	<h4 class="h-height">五、检测结果</h4>
		  <div class="control-group">
				<div id="icon1" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconb"></div>
			<div class="controls content1">
			${clcoholSecond.testResult}
			</div>
			<div class="list1"></div>
			<form:hidden path="testResult" id="content_value1"/>
		</div>
 		</div>
		<div class="form-actions" style="padding:0;text-align: center;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="通过" onclick="$('#flag').val('yes')"/>&nbsp;
 			<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="不通过" onclick="$('#flag').val('no')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>