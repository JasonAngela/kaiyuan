<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#checkpic").click(function(){
			   	  $("#a").toggle();
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
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
<style type="text/css">
 #a {
		  display: none;  position: absolute;  top: 15%;  left: 25%;  width: 20%;  height: 50%;  padding: 16px; 
	  border: 16px solid orange;  background-color: white;  z-index:1002;  overflow: auto; 
		}
		 .black_overlay{  display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;
	   background-color: black;  z-index:1001;  -moz-opacity: 0.8;  opacity:.80;  filter: alpha(opacity=80); 
	    } 
	     .white_content {  display: none;  position: absolute;  top: 25%;  left: 25%;  width: 20%;  height: 23%;  padding: 16px; 
	  border: 10px solid #7F9DB9;  background-color: white;  z-index:1002;  overflow:  ;  }  
	  #light {
		   position: fixed;
		   left:533px;
		   top: 100px;
		}
		
	 
	#close{
	 position: fixed;
		   left:700px;
		   top: 20px;
	}
</style>

</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/entrust/entrustRegister/">委托受理列表</a></li>
	</ul>
<form action="${ctx}/entrust/entrustCourier/save" method="post">
<div id="light" class="white_content" > 
       <input type="hidden" name="id" id="ids">
  		<input type="hidden" name="entrustId" id="entrustId">
  		寄件公司:
  		<input type="text" name="other" id="other"><br/>
  		快递单号:
  		<input type="text" name="orderno" id="orderno"><br/>
  		收件人:
  		<input type="text" name="sender" id="sender"><br/>
  		收件人联系电话:
  		<input type="text" name="other1" id="other1"><br/>
  		<input type="submit" value="保存" class="btn btn-primary"/>
    <a href="javascript:void(0)" onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'" id="close"> 
    <img src="${ctxStatic}/images/x.png" ></a></div> 
</div> 
  </form>		
 
<form:form id="searchForm" modelAttribute="entrustRegister" action="${ctx}/entrust/entrustRegister/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
		<li><label>委托人：</label>
				<form:input path="clientName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
				<li><label>受理人：</label>
				<form:input path="serverName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			 <li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('entrust_statusCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			<li><label>委托日期：</label>
				<input name="beginEntrustDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${entrustRegister.beginEntrustDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endEntrustDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${entrustRegister.endEntrustDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<a href="${ctx}/entrust/entrustRegister/readcard"><input id="btnSubmit1" class="btn btn-primary" type="button" value="读卡"/></a>
				<input id="checkpic" class="btn btn-primary" type="button" value="导出表格"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div align="center" id="a" style="display: none;">
	<form action="${ctx}/entrust/entrustRegister/export" method="post"> 
			<input type="text" name="beginEntrustDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">开始时间
			<input type="text" name="endEntrustDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">结束时间
		<table>
			<tr>
				<td><input type="checkbox" name="code" value="1"/></td>
				<td>编码</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="caseCode" value="1"/></td>
				<td>案件编码</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientName" value="1"></td>
				<td>委托人</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientTel" value="1"></td>
				<td>委托人电话</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="clientZipcode" value="1"></td>
				<td>联系电话</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="type" value="1"></td>
				<td>类型</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="agentName" value="1"></td>
				<td>送检人(机构)</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="serverName" value="1"></td>
				<td>受理人</td>
			</tr>
		    <tr>
				<td><input type="checkbox" name="StandardFee" value="1"></td>
				<td>合计费用</td>
			</tr>
		</table>
		<input  class="btn-primary" type="submit" value="导出exel表格"/>
		</form>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编码</th>
				<th>案件编码</th>
				<th>委托人</th>
				<th>送检人(机构)</th>
				<th>受理人</th>
				<th>专业</th>
				<th>类型</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="entrust:entrustRegister:edit"><th colspan="4">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="entrustRegister">
			<tr>
				<td><a href="${ctx}/entrust/entrustRegister/form?id=${entrustRegister.id}">
					${entrustRegister.code}
				</a>
				</td>
				<td>
					${entrustRegister.caseCode}
				</td>
				<td>
					${entrustRegister.clientName}
				</td>
				<td>
					${entrustRegister.agentName}
				</td>
				<td>
					${entrustRegister.serverName}
				</td>
				<td>
					${fns:getDictLabel(entrustRegister.specialty, 'specialtyCode', '')}
				</td>
				<td>
					${fns:getDictLabel(entrustRegister.type, 'typeCode', '')}
				</td>
				<td>
					${fns:getDictLabel(entrustRegister.status, 'entrust_statusCode', '')}
				</td>
				<td>
					<fmt:formatDate value="${entrustRegister.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${entrustRegister.remarks}
				</td>
				
				 <td>
				 	<input type="button" class="btn btn-primary" value="快递" onclick='cour("${entrustRegister.id}")'/ >
				 </td>					
				
				<shiro:hasPermission name="entrust:entrustRegister:edit">
				<td>
					<a href="${ctx}/entrust/entrustRegister/archive?id=${entrustRegister.id}"  >归档</a>
				</td>
				<td>	
					<a href="${ctx}/entrust/entrustRegister/report?id=${entrustRegister.id}">正稿</a>
				</td>
				</shiro:hasPermission>
				<td>
					<a  target="_blank" href="${ctx}/entrust/entrustRegister/details?id=${entrustRegister.id}">详情</a>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>