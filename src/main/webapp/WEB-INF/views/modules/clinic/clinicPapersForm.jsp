<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医底稿管理</title>
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
		 function printEntrust(){
				$("#lover").hide();
				$("#lover2").hide();
				$("#lover1").show();
				window.print();
				$("#lover").show();
				$("#lover2").show();
				$("#lover1").hide();
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
</head>
<body>

<div id="lover2">

	<ul class="nav nav-tabs">
		<li><a href="${ctx}/clinic/clinicPapers/">法医底稿列表</a></li>
 	</ul><br/>
		<sys:message content="${message}"/>	
	</div>	
<div id="lover1">		
		 <h3>基本案情</h3>
		<div class="control-group">
			<div class="controls content">
			${clinicPapers.commentaries}
			</div>
			<div class="list"></div>
		</div>
<h3>资料摘要</h3>		
		<div class="control-group">
				<div class="controls content1">
				${clinicPapers.clinicThisPaper}
				</div>
				<div class="list1"></div>
		</div> 
<h3>鉴定过程</h3>		
		<div class="control-group">
			<label class="control-label">（一）检验方法</label>
				<div class="controls content2">
				${clinicPapers.inspectionMethods}
				</div>
				<div class="list2"></div>
		</div>
		
		<div class="control-group">
			<label class="control-label">（二）鉴定标准</label>
				<div class="controls content3">
				${clinicPapers.appraisalStandard}
				</div>
				<div class="list3"></div>
		</div>
		<div class="control-group">
			<label class="control-label">（三）鉴定经过</label>
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
		<div class="control-group">
			<label class="control-label">主诉：</label>
				<div class="controls content4">
				${clinicPapers.cc}
				</div>
				<div class="list4"></div>
		</div>
		<div class="control-group">
			<label class="control-label">查体：</label>
				<div class="controls content5">
				${clinicPapers.body}
				</div>
				<div class="list5"></div>
		</div>
		<div class="control-group">
			<label class="control-label">阅片：</label>
				<div class="controls content6">
				${clinicPapers.reading}
				</div>
				<div class="list6"></div>
		</div>
	<h3>分析说明</h3>		
		<div class="control-group">
			<label class="control-label">分析说明：</label>
				<div class="controls content7">
				${clinicPapers.analysisShows}
				</div>
				<div class="list7"></div>
		</div>
	<h3>鉴定意见</h3>	
		<div class="control-group">
			<label class="control-label">鉴定意见：</label>
				<div class="controls content8">
				${clinicPapers.expertOpinion}
				</div>
				<div class="list8"></div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">鉴定人：</label>
			<div class="controls">
				${clinicPapers.surver}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
				<div class="controls content9">
				${clinicPapers.remarks}
				</div>
				<div class="list9"></div>
		</div>
		</div>
		
		<div class="form-actions" id="lover">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<input class="btn" type="button" value="去打印" onclick="printEntrust()"/>
		</div>
</body>
</html>