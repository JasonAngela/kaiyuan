<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医授权签字人管理</title>
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
width: 930px;
    background-color: #F0FFFF;
    padding: 85px 60px 0 60px;
    margin: -45px auto auto;
    }
.nav-tabs{
background-color: #F0FFFF;
}
	
		#boxdiv {
			 position: fixed;
  			 left: 0px;
 			 top: 59px;
 			 width:100%;
  			 text-align: center;
				}
		#boxdiv {
			 position: fixed;
  			 left: 184px;
 			 top: 59px;
				}
				
		#icona {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}
				#iconb {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#iconc {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#icond {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				
				#icone {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#iconf {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}		
		#icong {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#iconh {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#iconi {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
				}	
				#iconj {
			 position: fixed;
  			 left: 577px;
 			 top: 203px;
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
		  #a {
		  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		}
		 #ab {
		  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 50%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		} 
		
	
	#close{
	 position: fixed;
		   left:528px;
		   top: 20px;
	}
	.h-left{
display:inline-block;
line-height: 40px;
}
		
	  </style> 
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/clinic/clinicAuthorise/form?id=${clinicAuthorise.id}">法医授权签字人<shiro:hasPermission name="clinic:clinicAuthorise:edit">${not empty clinicAuthorise.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clinic:clinicAuthorise:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clinicAuthorise" action="${ctx}/clinic/clinicAuthorise/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" name="register.id" value="${registerId}" id="registerId">    
		<input type="hidden" value="${user}" id="userId"> 
		<sys:message content="${message}"/>			
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
 
		 <h3>基本案情</h3>
 <h6>物证登记文件</h6>
 <c:forEach  items="${pic}" var="pics">
		<img alt="" src="${pics}"  onclick="big(this.src)" width="50px;" height="50px;">
	</c:forEach>
	<div id="big" style="display:none; width: 1300; height: 800; position:fixed; top:10%; left:27%; z-index:5;">
   	<img src="" width="800" height="600" id="bigimg" onclick="close2()"></div>
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
				<div id="icon" style="display: none; position: absolute;" title="添加批注"> 
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icona"></div>
			<div class="controls content">
 					${clinicInspection.clinicAttorney}  
			</div>
			<div class="list"></div>
			<form:hidden path="opinion" id="content_value"/>
			
		</div>
		<%--  <div class="control-group">
			<div id="icon" style="display: none;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icona"></div>
			<div class="controls content">
			${clinicInspection.clinicAttorney}
			</div>
			<div class="list"></div> 9 ·                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
			<form:hidden path="opinion" id="content_value"/>
		</div>  --%>
		
		<h3>资料摘要</h3>		
		<div class="control-group">
			<div id="icon1" style="display: none; position: absolute;" title="添加批注">
					<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconb"></div>
				<div class="controls content1">
				${clinicInspection.clinicThisPaper}
				</div>
				<div class="list1"></div>
				<form:hidden path="clinicThisPaper" id="content_value1"/>
		</div>
		
		
		
		
		
		<h3>鉴定过程</h3>		
		<div class="control-group">
			<label class="control-label">（一）检验方法</label>
			<div id="icon2" style="display: none; position: absolute;" title="添加批注">
					<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconc"></div>
				<div class="controls content2">
				${clinicInspection.inspectionMethods}
				</div>
				<div class="list2"></div>
				<form:hidden path="inspectionMethods" id="content_value2"/>
		</div>
		
		  <div class="control-group">
				<label class="control-label">（二）鉴定标准：</label>
				<div id="icon3" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icond"></div>
			<div class="controls content3">
			${clinicInspection.appraisalStandard}
			</div>
			<div class="list3"></div>
			<form:hidden path="appraisalStandard" id="content_value3"/>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">主诉：</label>
			<div id="icon4" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icone"></div>
			<div class="controls content4">
			${clinicInspection.cc}
			</div>
			<div class="list4"></div>
			<form:hidden path="cc" id="content_value4"/>
		</div>
		
		<div class="control-group">
			<label class="control-label">查体：</label>
			<div id="icon5" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconf"></div>
			<div class="controls content5">
			${clinicInspection.body}
			</div>
			<div class="list5"></div>
			<form:hidden path="body" id="content_value5"/>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">阅片：</label>
			<div id="icon6" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="icong"></div>
			<div class="controls content6">
			${clinicInspection.reading}
			</div>
			<div class="list6"></div>
			<form:hidden path="reading" id="content_value6"/>
		</div>
		
	<h3>分析说明</h3>	
		<div class="control-group">
			<label class="control-label">分析说明：</label>
			<div id="icon7" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconh"></div>
			<div class="controls content7">
			${clinicInspection.analysisShows}
			</div>
			<div class="list7"></div>
			<form:hidden path="analysisShows" id="content_value7"/>
		</div>
			<h3>鉴定意见</h3>	
		<div class="control-group">
			<label class="control-label">鉴定意见：</label>
			<div id="icon8" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconi"></div>
			<div class="controls content8">
			${clinicInspection.expertOpinion}
			</div>
			<div class="list8"></div>
			<form:hidden path="expertOpinion" id="content_value8"/>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div id="icon9" style="display: none; position: absolute;" title="添加批注">
				<img src="${ctxStatic}/images/tips.png" class="tipsIcon" id="iconj"></div>
			<div class="controls content9">
			${clinicInspection.remarks}
			</div>
			<div class="list9"></div>
			<form:hidden path="remarks" id="content_value9"/>
		</div>
		
		
		<%--  <div class="control-group">
			<label class="control-label">检验方法：</label>
			<div class="controls">
				<form:input path="inspectionMethods" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		
		<%--<div class="control-group">
			<label class="control-label">鉴定标准：</label>
			<div class="controls">
				<form:input path="appraisalStandard" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴定经过：</label>
			<div class="controls">
				<form:input path="identifiedThrough" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主诉：</label>
			<div class="controls">
				<form:input path="cc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查体：</label>
			<div class="controls">
				<form:input path="body" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阅片：</label>
			<div class="controls">
				<form:input path="reading" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分析说明：</label>
			<div class="controls">
				<form:input path="analysisShows" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴定意见：</label>
			<div class="controls">
				<form:input path="expertOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		
		<div class="control-group">
			<label class="control-label">授权签字人：</label>
			<div class="controls">
				<input name="authoriseSurveyor" value="${user}" readonly="readonly"> 
			</div>
		</div>
		
		</div> 
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="通过" onclick="$('#flag').val('yes')"/>&nbsp;
 			<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="不通过" onclick="$('#flag').val('no')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
</body>
</html>