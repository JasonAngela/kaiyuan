<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精初稿管理</title>
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
		function big(picname){
	     	var div=document.getElementById("big");
	     	var img=document.getElementById("bigimg");
	     	img.src=picname;
	     	div.style.display="block";
	     }
	     function close2(){
	   		var div=document.getElementById("big");
	   		var img=document.getElementById("bigimg");
	   		img.src="";
	   		div.style.display="none";
	   	}
		
	</script>
	<script src="${ctxStatic}/common/artDialog.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil2.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil3.js" type="text/javascript"></script>
	
	<style type="text/css">
	
		#boxdiv {
			 position: fixed;
  			 left: 184px;
 			 top: 59px;
				}
	 .black_overlay{  display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;
	   background-color: black;  z-index:1001;  -moz-opacity: 0.8;  opacity:.80;  filter: alpha(opacity=80); 
	    } 
	     .white_content {  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto;  }  
	  #light {
		   position: fixed;
		   left:200px;
		   top: 100px;
		}
		
	
	#close{
	 position: fixed;
		   left:528px;
		   top: 20px;
	}
	  #a {
		  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		}
		 #ab {
		  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		} 
		#pagecontent{
	
	background-color: #F0FFFF;
	    }
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
  margin-right:100 px;
	width: 630px;
    background-color: #F0FFFF;
    padding: 85px 6px 0 60px;
    margin-top: -39px;
    margin-left: 123px;
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
.h-left{
display:inline-block;
line-height: 40px;
}
.h-height{
line-height: 40px;
}
	  </style> 
	  
	
</head>
<body>

<h6>物证登记文件</h6>
 <c:forEach  items="${pic}" var="pics">
		<img alt="" src="${pics}"  onclick="big(this.src)" width="50px;" height="50px;">
	</c:forEach>
	<div id="big" style="display:none; width: 1300; height: 800; position:fixed; top:0%; left:0%; z-index:5;">
   	<img src="" width="800" height="600" id="bigimg" onclick="close2()"></div>
   	<c:forEach items="${pdf}" var="pdf" varStatus="kl">
	   	<c:forEach items="${pdf1 }" var="pdf1" >
		<a href="http://localhost:8080/${pdf}" target="_blank">	${pdf1} </a>
		</c:forEach>
   	</c:forEach>
	<form:form id="inputForm" modelAttribute="clcoholFirst" action="${ctx}/clcohol/clcoholFirst/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/> 
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">
		<input type="hidden" name="other1" value="${user.name}" >
		<sys:message content="${message}"/>	

			
<div  style="width: 630px" id="pagecontent" >		
	<h4 align="center">上海开元司法鉴定中心<br/>
		司法鉴定乙醇含量检测报告书
	</h4>		
	 <h5 align="right">开元[${simple}]伤鉴字第[${casecode}]号</h5>
	 <h4>一:基本情况</h4>
		<div class="control-group">
			<div>
			<h4 class="h-left">委 托 方:</h4> <form:input path="entrust" htmlEscape="false" class="input-small "/>
			<br/>
			<h4 class="h-left">委托事项:</h4>
				对送检血样进行乙醇检测
			<br/>
			<h4 class="h-left">受理日期:</h4> <form:input path="acceptDate"   onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});" class="input-small "/>
			<br/>
			<%-- <h4 class="h-left">鉴定日期:</h4> <form:input path="other" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/>
			<br/> --%>
			<h4 class="h-left">检测材料:</h4> 
			${clcoholFirst.testingMaterials }
			
			
			<br/>
			<!-- <h4 class="h-left">鉴定地点:</h4> 上海开元司法鉴定中心
			<br/>
			<h4 class="h-left">在场人员:</h4> 本中心工作人员
			<br/> -->
			<h4 class="h-left">被检测人姓名：</h4> <form:input path="personBeing"  htmlEscape="false" class="input-small "/>
			<br/>
			</div>
			
<h4 class="h-height">二:基本案情</h4>
		<%-- <div class="control-group">
			<div class="controls content">
			${clcoholAuthorization.basicFacts}
			</div>
			<div class="list"></div>
			<div>
				<form:textarea path="basicFacts" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
			</div>
		</div> --%>
		<div class="h-height"> 
				据委托书介绍：对送检血样进行血液乙醇检测。
			</div>
	
		</div>
		
	 <h4 class="h-height">三、送检样品情况</h4>		
		<div class="control-group">

				<div>
					<form:textarea path="sampleStatus" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
				</div>
		</div>
		
		
	<!-- 	
	 <div class="control-group">
			<label class="h-height">（二）检验</label>
			<div class="h-height"> 
				检材经处理后采用GC-FID进行检测。<br/>
				本次检验方法采用《血液酒精含量的检验方法》GA/T842-2009对送检检材进行血液酒精含量检验。
			</div>
		</div> -->
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
			<div class="controls content">
			${clcoholAuthorization.basicFacts}
			</div>
			<div class="list"></div>
			<div>
				<form:textarea path="basicFacts" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
			</div>
		</div>       
		
		
		<h4 class="h-height">五、检测结果</h4>
		 <div class="control-group"> 
			<div class="controls content1">
			${clcoholAuthorization.testResult}
			</div>
			<div class="list1"></div>
			<div>
				<form:textarea path="testResults" rows="3" cols="100" cssStyle="margin: 0px; width: 602px; height: 70px;"/>
			</div>
		
		
		
	
		
		     
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="clcohol:clcoholFirst:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		</div>	
	</form:form>
</body>
</html>