  <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医鉴定初稿管理</title>
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
			
			
			$("#checksituation").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checksituation",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});
			
			$("#checkheadFace").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checkheadFace",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});
			
			$("#checktrunk").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checktrunk",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});
			
			$("#checklimbs").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checklimbs",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});
			
			
			$("#checkother").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checkother",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});
			
			$("#checkreading").click(function() {
				var  registerId=$("#registerId").val();    
				$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/clinic/clinicFirstdraft/checkreading",//路径  
	             data : {  
	                 "registerId":"${registerId}"  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {//返回数据根据结果进行相应的处理  
	     			$("#cc").val(data.result);
	     			document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'
	             }
	          });  
			});

			$("#checkpic").click(function(){
			   	  $("#a").toggle();
			   	  });
			$("#checkpics").click(function(){
			   	  $("#ab").toggle();
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
	<script src="${ctxStatic}/common/postil4.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil5.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil6.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil7.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil8.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil9.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/postil10.js" type="text/javascript"></script>
	
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
    margin-right:100 px;
	width: 930px;
    background-color: #F0FFFF;
    padding: 85px 6px 0 60px;
    margin-top: 10px;
    margin-left: 731px;
    }
.nav-tabs{
background-color: #F0FFFF;
}
.control-label{
}
		#boxdiv {
			 position: fixed;
  			 left: -478px;
 			 top: 59px;
 			 width:100%;
  			 text-align: center;
				}
	 .black_overlay{  display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;
	   background-color: black;  z-index:1001;  -moz-opacity: 0.8;  opacity:.80;  filter: alpha(opacity=80); 
	    } 
	     .white_content {  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto;  }  
	  #light {
		   position: fixed;
		   left:0;
		   top:99px;
		   width:43%;
  			 text-align: center;
		}
		
	
	#close{
	 position: fixed;
		   left:528px;
		   top: 20px;
	}
	  #a {
		display: none;
        position: fixed;
    left: 0;
    top: 99px;
    left: 0%;
    width: 46%;
    height: 86%;
    padding: 6px;
    border: 6px solid orange;
    background-color: white;
    z-index: 1002;
    overflow: auto;
		}
		 #ab {
		  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		} 
		
	  </style> 
		
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clinic/clinicFirstdraft/form?id=${clinicFirstdraft.id}">法医鉴定初稿<shiro:hasPermission name="clinic:clinicFirstdraft:edit">${not empty clinicFirstdraft.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clinic:clinicFirstdraft:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clinicFirstdraft" action="${ctx}/clinic/clinicFirstdraft/save" method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">       
		<sys:message content="${message}"/>			
	<%-- 	<div class="control-group">
			<label class="control-label">意见：</label>
			<div class="controls">
				<form:input path="opinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">first_opinion：</label>
			<div class="controls">
				<form:input path="firstOpinion" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>&
		</div> --%>
<div id="boxdiv">
	<input type="button" value="一般情况" id="checksituation" class="btn btn-primary"/> 
	<input type="button" value="查看头面部" id="checkheadFace" class="btn btn-primary"/>  
	<input type="button" value="查看躯干" id="checktrunk"  class="btn btn-primary"/> 	
	<input type="button" value="查看四肢" id="checklimbs"  class="btn btn-primary"/> 	 
	<input type="button" value="查看其它" id="checkother"  class="btn btn-primary"/> 	
	<input type="button" value="查看阅片" id="checkreading"  class="btn btn-primary"/>  
	<input type="button" value="查看物证登记及人员验伤文件" id="checkpic" class="btn btn-primary"/> 
</div>	


<div align="center" id="a" style="display: none;" >
 <h6>物证登记文件</h6>
 <c:forEach  items="${pic}" var="pics">
		<img alt="" src="${pics}"  onclick="big(this.src)" width="50px;" height="50px;">
	</c:forEach>
	<div id="big" style="display:none; width: 1300; height: 800; position:fixed; top:0%; left:0%; z-index:5;">
   		<img src="" width="800" height="600" id="bigimg" onclick="close2()">
	</div>
   	<c:forEach items="${pdf}" var="pdf" varStatus="kl">
	   	<c:forEach items="${pdf1 }" var="pdf1" >
		<a href="http://localhost:8080/${pdf}" target="_blank">	${pdf1} </a>
		</c:forEach>
   	</c:forEach> 
   	<h6>人员验伤文件</h6>
 	<c:forEach  items="${pic2}" var="pics">
		<img alt="" src="${pics}"  onclick="big(this.src)" width="50px;" height="50px;">
	</c:forEach>
	<div id="big" style="display:none; width: 1300; height: 800; position:fixed; top:10%; left:27%; z-index:5;">
   	<img src="" width="800" height="600" id="bigimg" onclick="close2()"></div>
</div>	
   	<c:forEach items="${pdf2}" var="pdf" varStatus="kl">
	   	<c:forEach items="${pdf2}" var="pdf1" >
		<a href="http://localhost:8080/${pdf}" target="_blank">	${pdf1} </a>
		</c:forEach>
   	</c:forEach> 
</div>	

<div id="light" class="white_content" > 
   <textarea id="cc" cols="105" rows="10" style="margin: 0px; height: 244px; width: 710px;"></textarea>
    <a href="javascript:void(0)" onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'" id="close"> 
    <img src="${ctxStatic}/images/x.png" ></a></div> 
</div> 
 <h1 align="center">上海开元司法鉴定中心<br/>
		司法鉴定委托书</h1>
		<h6 style="margin-left: 696px;" id="lover1" >开元[${simple}]伤鉴字第${casecode}号</h6>

 <h3>基本案情</h3>
		<div class="control-group">
			<div class="controls content">
			${clinicAuthorise.opinion}
			</div>
			<div class="list"></div>
			<div>
				<form:textarea path="clinicAttorney" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
<h3>资料摘要</h3>		
		<div class="control-group">
				<div class="controls content1">
				${clinicAuthorise.clinicThisPaper}
				</div>
				<div class="list1"></div>
			<div class="controls" style="margin:0">
				<form:textarea path="clinicThisPaper" rows="3" style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
<h3>鉴定过程</h3>		
		<div class="control-group">
			<label >（一）检验方法</label>
				<div class="controls content2">
				${clinicAuthorise.inspectionMethods}
				</div>
				<div class="list2"></div>
				<div>
					<form:textarea path="inspectionMethods" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
				</div>
		</div>
		
		<div class="control-group">
			<label>（二）鉴定标准</label>
				<div class="controls content3">
				${clinicAuthorise.appraisalStandard}
				</div>
				<div class="list3"></div>
			<div> 
				<form:input path="appraisalStandard" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label>（三）鉴定经过</label>
			<%-- <div class="controls">
				<form:input path="identifiedThrough" htmlEscape="false" class="input-xlarge "/>
			</div> --%>
		<div class="control-group">
			<label>鉴定日期：</label>
				<div>
						<form:input path="appraisalDate" value="${date}"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/>
				</div>
		</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">鉴定日期：</label>
			<div class="controls">
				<input name="appraisalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${clinicFirstdraft.appraisalDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在场人员：</label>
			<div class="controls">
				<form:input path="presencePersonnel" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<div>
			<label>主诉：</label>
				<div class="controls content4">
				${clinicAuthorise.cc}
				</div>
				<div class="list4"></div>
			<div>
				<form:textarea path="cc" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"  />
			</div>
		</div>
		<div class="control-group">
			<label>查体：</label>
				<div class="controls content5">
				${clinicAuthorise.body}
				</div>
				<div class="list5"></div>
			<div>
				<form:textarea path="body" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
		<div class="control-group">
			<label>阅片：</label>
				<div class="controls content6">
				${clinicAuthorise.reading}
				</div>
				<div class="list6"></div>
			<div>
				<form:textarea path="reading" rows="3" cols="100" cssStyle="margin: 0px; width: 702px; height: 70px;"/>
			</div>
		</div>
	<h3>分析说明</h3>		
		<div class="control-group">
			<label>分析说明：</label>
				<div class="controls content7">
				${clinicAuthorise.analysisShows}
				</div>
				<div class="list7"></div>
			<div>
				<form:textarea path="analysisShows" rows="3" style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
	<h3>鉴定意见</h3>	
		<div class="control-group">
			<label>鉴定意见：</label>
				<div class="controls content8">
				${clinicAuthorise.expertOpinion}
				</div>
				<div class="list8"></div>
			<div>
				<form:textarea path="expertOpinion"  style="margin: 0px; width: 702px; height: 146px;"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label>第一鉴定人：</label>
			<div>
				<input type="text" name="firstSurveyor" value="${user.name}" readonly="readonly"> 
			</div>
		</div>
		<div class="control-group">
			<label>备注：</label>
				<div class="controls content9">
				${clinicAuthorise.remarks}
				</div>
				<div class="list9"></div>
			<div>
				<form:textarea path="remarks" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="clinic:clinicFirstdraft:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>