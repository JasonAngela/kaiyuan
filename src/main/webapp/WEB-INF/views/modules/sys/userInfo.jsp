<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	<style type="text/css">
		.table {
	
	
}

.table td, .table th {
	vertical-align: middle;
	border-bottom: 21px solid #7F9DB9;
	border-right: 21px solid #7F9DB9;
	padding:21px;
	text-align: center;
	line-height:18px;
	
}

.table th {
	font-weight:normal;
	background-color:#C3DAF9;
}

.table td{
	text-align:center;
	vertical-align:middle;
	

}

.inputTable {
	font-size:12px;
	border-collapse:collapse; 
	border-top: 1px solid #7F9DB9;
	border-left: 12px solid #C3DAF9;
}

.inputTable td, .inputTable th {
	vertical-align: middle;
	text-align:middle;
	border-bottom: 2px solid #7F9DB9;
	border-right: 2px solid #7F9DB9;
	padding:2px 0px 0px 18px;
	line-height:20px;
}

.inputTable th {
	font-weight:normal;
	background-color:#C3DAF9;
}


.tdone{
		text-align:center;
		vertical-align:middle;
}

 /*滑动门*/
     .div1,.div2{
        position: absolute;
         top:0;
         left:234px;
         display: none;
         margin-left: -1px;
     }
/*当鼠标悬停在内容上是显示对应的代码块*/
     table td:hover .div1{
        display: block;
         width:350px;
         height: 265px;
     }
     table td:hover .div2{
         display: block;
         width:150px;
         height: 165px;
         margin-bottom: 240px;
     }
     
     
     div:.inputTable{
     		 display: block;
     
     }
     
     .atype{
     margin-left: 38px;
      font-size: 18px;
      
     }
     
     .atype{
     margin-left: 38px;
      font-size: 18px;
      
     }
	</style> 

	</style>    
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info">业务处理</a></li>
		<%-- <li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li> --%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<sys:message content="${message}"/>
		<div class="control-group" style="margin-top: -28px">
		<table width="200"  height="730" class="inputTable" >
			<tr>
				<th><h5>委托受理</h5></th>
			</tr>
			<tr>
				<td class="tdone">
					<shiro:hasPermission name="entrust:entrustRegister:view">	
						<a href="#">DNA委托受理</a><br/>
				    </shiro:hasPermission>
				    <div class="div1" style="  margin-left: -24px;margin-top: 94px;">
				        <table style="width:156px;
				        height: 200px; border-bottom: 1px solid #7F9DB9;
							border-right: 1px solid #7F9DB9;
							border-left: 1px solid #7F9DB9;
							border-top: 1px solid #7F9DB9;">
				           <tr>
				           
				           	<th class="atype1">
				           			DNA委托受理
				           	</th>
				           </tr>
				           <tr>
				           		<td>
				           		<a href="${ctx}/entrust/entrustRegister/form1?accept=1" class="atype1">个人鉴定</a><br/>
				           		</td>
				           </tr>
				            <tr>
				           		<td >
				           		<a href="${ctx}/entrust/entrustRegister/form1?accept=2" class="atype1">司法鉴定</a><br/>
				           		</td>
				           </tr>
				            <tr>
				           		<td >
				           			<a href="${ctx}/entrust/entrustRegister/form1?accept=3" class="atype1">亲缘鉴定</a><br/>
				           		</td>
				           </tr>
				            <tr>
				           		<td >
				           		<a href="${ctx}/entrust/entrustRegister/form1?accept=4" class="atype1">个人识别</a><br/>
				           		</td>
				           </tr>
				            <tr>
				           		<td>
				           			<a href="${ctx}/entrust/entrustRegister/form1?accept=5" class="atype1">同一认定</a><br/>
				           		</td>
				           </tr>
				            <tr>
				           		<td>
				           			<a href="${ctx}/entrust/entrustRegister/form1?accept=6" class="atype1">其它</a><br/>
				           		</td>
				           </tr>
				        </table>
				        </div>
				</td>
						
						
			</tr>
			
			<tr>	
					<shiro:hasPermission name="clinic:clinicRegister:view">
				<td class="tdone">
					<a href=#>法医委托受理</a><br/>
					 <div class="div2" style="margin-left: -24px;
 margin-top: 133px;">
				        <table style="width:156px;
				        height: 160px; border-bottom: 1px solid #7F9DB9;
							border-right: 1px solid #7F9DB9;
							border-left: 1px solid #7F9DB9;
							border-top: 1px solid #7F9DB9;">
							 <tr>
				           		<th>
				           		法医委托受理
							</th>
						</tr>
							   <tr>
				           		<td>
						<a href="${ctx}/clinic/clinicRegister/form1?accpet=1" class="atype1">残疾鉴定</a><br/>
						</td>
						</tr>
						   <tr>
				           		<td>
						<a href="${ctx}/clinic/clinicRegister/form1?accpet=2" class="atype1">损伤鉴定</a><br/>
						</td>
						</tr>
						   <tr>
				           		<td>
						<a href="${ctx}/clinic/clinicRegister/form1?accpet=3" class="atype1">病理FS</a><br/>
						</td>
						</tr>
						   <tr>
				           		<td>
						<a href="${ctx}/clinic/clinicRegister/form1?accpet=4" class="atype1">病理FA</a><br/>
						</td>
						</tr>
						</table>
					</div> 
					
				</td>
					</shiro:hasPermission>
			</tr>

					<shiro:hasPermission name="clcohol:clcoholRegister:view">
			<tr>	
				<td class="tdone">
					<a href="${ctx}/clcohol/clcoholRegister/form1">酒精委托受理</a><br/>
				</td>
			</tr>
					</shiro:hasPermission>		
					
					
			<!--DNA实验室-->
					<shiro:hasPermission name="dna:dnaExperiment:view">
			<tr>
				<th>
					<h5>DNA实验室</h5>
				</th>
				
			</tr>
			<tr>
				<td class="tdone">		
					  <a href="${ctx}/dna/dnaReceive/form">领取样品</a><br/>
				</td>
			</tr>
			
			<tr>
				<td class="tdone">		
					  <a href="${ctx}/dna/dnaExtractRecord/form">提取室</a><br/>
				</td>
			</tr>		
			<tr>
				<td class="tdone">		
					  <a href="${ctx}/dna/dnaPreparationReagents/form">扩增室</a><br/>
				</td>
			</tr>
			<tr>
				<td class="tdone">		  
					  <a href="${ctx}/dna/dnaExperiment/form">电泳室</a><br/>
				</td>
			</tr>
			
			
					</shiro:hasPermission>
			<!--分类代办事宜-->
			<tr>
				<th>
					<h5>待办事宜</h5>		
				</th>
			</tr>
					<shiro:hasPermission name="entrust:entrustRegister:view">	
			<tr>
				<td class="tdone">
						<a href="${ctx}/act/task/todo/">dna鉴定<font style="color: red;">(${sifaTodo})</font></a><br/>
				</td>
			</tr>
					</shiro:hasPermission>
					<shiro:hasPermission name="entrust:entrustExpertOpinion:view">
			<tr>
				<td class="tdone">
						<a href="${ctx}/act/task/todo/">dna实验<font style="color: red;">(${dnaTodo})</font></a><br/>
				</td>
			</tr>
					</shiro:hasPermission>
					<shiro:hasPermission name="clinic:clinicRegister:view">
			<tr>
				<td class="tdone">
							<a href="${ctx}/act/task/todo/">法医临床<font style="color: red;">(${fayiTodo})</font></a>
				</td>
			</tr>
					</shiro:hasPermission>		
				<!-- 分类已办事宜-->
				<tr>
					<th>
						<h5>已办事宜</h5>	
					</th>
				</tr>
						<shiro:hasPermission name="entrust:entrustRegister:view">	
				<tr>
					<td class="tdone">
						<a href="${ctx}/act/task/historic/">dna鉴定<font style="color: red;">(${sifaHistoryCount})</font></a><br/>
					</td>
				</tr>
						</shiro:hasPermission>
						<shiro:hasPermission name="entrust:entrustExpertOpinion:view">
				<tr>
					<td class="tdone">
						<a href="${ctx}/act/task/historic/">dna实验<font style="color: red;">(${dnaHistoryCount})</font></a><br/>
					</td>
				</tr>
						</shiro:hasPermission>
						<shiro:hasPermission name="clinic:clinicRegister:view">
				<tr>
					<td class="tdone">
						<a href="${ctx}/act/task/historic/">法医临床<font style="color: red;">(${fayiHistoryCount})</font></a>
					</td>
				</tr>
						</shiro:hasPermission>
			
			
		</table>
	</form:form>
</body>
</html>