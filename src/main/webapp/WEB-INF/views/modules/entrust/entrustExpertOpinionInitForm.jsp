<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>鉴定意见管理</title>
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
		
		function cour(entrustId){
			$.ajax({  
             type : "POST",  //提交方式  
             url : "${ctx}/entrust/entrustCourier/update",//路径  
             data : {  
                 "entrustId":entrustId  
             },//数据，这里使用的是Json格式进行传输  
             success : function(data) {//返回数据根据结果进行相应的处理  
            $("#ids").val(data.id);
     	  	 $("#entrustId").val(entrustId);
     	  	 $("#orderno").val(data.orderno);
     	  	 $("#other").val(data.other);
     	  	 $("#other1").val(data.other1);
     	  	 $("#sender").val(data.sender);
     		document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block';
             }
          });  
		
		
	}
	
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
	
	#pagecontent{

	background-color: #F0FFFF;
	    }
	
	</style>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/entrust/entrustExpertOpinion/form?id=${entrustExpertOpinion.id}">DNA鉴定意见初稿 <shiro:lacksPermission name="entrust:entrustExpertOpinion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<div id="pagecontent" style="width: 930px;margin-left: 300px;">
			<h1 align="center">上&nbsp;海&nbsp;尚&nbsp;法&nbsp;科&nbsp;技&nbsp;有&nbsp;限&nbsp;公&nbsp;司<br/>
		司&nbsp;法&nbsp;鉴&nbsp;定&nbsp;意&nbsp;见&nbsp;书</h1>
		<h6 style="margin-left: 696px;" id="lover1" >赣(景)[${simple}]物鉴字第${casecode}号</h6>	
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 280px;">
		<thead>
			<tr>
				<th>序号</th>
				<th>样本编码</th>
				<th>样本编码</th>
				<th>CPI</th>
				<th>RCP</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="#{dnaResultList}" var="result" varStatus="status"> 
				<tr>
					<th>${status.index+1}</th>
					<th>${result.parentCode}</th>
					<th>${result.childCode}</th>
					<th>${result.cpi}</th>
					<th>${result.rcp}</th>
					<th><a href="javascript:" onclick='$("#contentTable_${result.id}").toggle()'>详情</a></th>
				</tr>
				<tr>
					<table id="contentTable_${result.id}" class="table table-striped table-bordered table-condensed" style="display: none;">
						<thead>
							<tr>
								<th>序号</th>
								<th>基因座</th>
								<th>pi值</th>
								<th>公式</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${result.dnaPiResultItemList }" var="item" varStatus="itemStatus">
							<tr>
								<th>${itemStatus.index+1}</th>
								<th>${item.geneLoci}</th>
								<th>${item.pi}</th>
								<th>${item.formula}</th>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</tr>
			</c:forEach>
		</tbody>
		<br/><br/>
	</table>
		<%-- 	<table  align="center" style="border-collapse:collapse;width:794px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"  
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">基因座名称</th>
				<c:forEach items="${entrustAbstracts}" var="item" >
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
				</c:forEach>  
			</tr>
			
			<c:forEach items="${str}" var="strMap" >
				<tr>
					<td style="text-align: center;width: 166px;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item" >
						<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
			</table>
	<br>	
		
	 <c:forEach  items="${pic}" var="pics" >
			<object data="${pics}" type="application/pdf" width="1000" height="200" class="hiddenObjectForIE" align="middle" style="margin-left: 400px;" onclick="big(this.src)">    
		</object>          
      </c:forEach>
      <c:if test="${not empty  pic}">
	       <c:forEach  items="${pic}" var="pics" >
				  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"> 
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				   <embed id="pdffile" width="100%" height="680" src="${pics}" type="application/pdf" >  
				  </object> 
		           <br/><br/><br/>
		 </c:forEach>
	 </c:if>
	  --%>

     <!--  <div id="big" style="display:none; width: 600; height: 600; position:fixed; top:10%; left:27%; z-index:5;">
   	<img src="" width="800" height="600" id="bigimg" onclick="close2()"></div> -->
	<%-- <c:forEach  items="${pic}" var="pics">
		<img alt="" src="${pics}" style="width: 30px;height: 30px;" onclick="big(this.src)">
	</c:forEach>
	
	<div id="big" style="display:none; width: 800; height: 600; position:fixed; top:10%; left:27%; z-index:5;">
   	<img src="" width="800" height="600" id="bigimg" onclick="close2()"></div> --%>

	<form:form id="inputForm" modelAttribute="entrustExpertOpinion" action="${ctx}/entrust/entrustExpertOpinion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.businessId"/>
		<form:hidden path="register.id"/>
		<input type="hidden" value="${user}" id="userId"> 
		
		<sys:message content="${message}"/>		
		 
		<div class="control-group" >
				<strong>分析说明初稿：</strong>
				<div id="icon" style="display: none; position: absolute;" title="添加批注"><img src="${ctxStatic}/images/tips.png" class="tipsIcon"></div>
			<div class="controls content" style="width: 647px">
	 		${entrustExpertOpinion.explainRemark}
			</div>
			<div class="list"></div>
			<form:hidden path="explain" id="content_value"/>
		</div>
		<div class="control-group">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			<strong>鉴定意见：</strong>
			<div class="controls">                    
				<form:textarea path="draft" htmlEscape="true" rows="4" maxlength="1000"   class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions" style="padding: 32px; margin-left: 641px;">
			<a  href="${ctx}/entrust/entrustRegister/report?id=${register.id}"><input  class="btn btn-primary" type="button" value="预 览"/></a>
			<shiro:hasPermission name="entrust:entrustExpertOpinion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
	<table   style="border-collapse:collapse;width:794px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"  
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">基因座名称</th>
				<c:forEach items="${entrustAbstracts}" var="item" >
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
				</c:forEach>  
			</tr>
			
			<c:forEach items="${str}" var="strMap" >
				<tr>
					<td style="text-align: center;width: 166px;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item">
						<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
			</table>
	<br>	
			 <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0" style="display:none;"> 
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				   <embed id="pdffile" width="100%" height="680" src="${ctxStatic}/images/1.pdf"" type="application/pdf" >  
				  </object>
		
      <c:if test="${not empty  pic}">
	       <c:forEach  items="${pic}" var="pics" >
				  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"> 
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				   <embed id="pdffile" width="100%" height="680" src="${pics}" type="application/pdf" >  
				  </object> 
		           <br/><br/><br/>
		 </c:forEach>
	 </c:if>
	
	</div>

	
	
</body>
</html>