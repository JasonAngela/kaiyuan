<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>DNA归档</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			
		});
		
		function hide6() {
			$("#exam1").hide();  
			$("#exam2").hide();
		}
		function show6() {
			$("#exam1").show();
			$("#exam2").show();
		}
		

		function hide7() {
			$("#exam3").hide();
			$("#exam4").hide();
		}
		function show7() {
			$("#exam3").show();
			$("#exam4").show();
		}
		
		
	</script>
	
	<style type="text/css">
	a:HOVER {
	color: red;	
}
	a{
	color: blue;
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
		    <table style="margin-top:30px;width: 248px;height: 700px;"  class="inputTable">
				<tr>
					<th>序号${jdname}</th>
					<th>内容</th>
				</tr>
				<tr>
					<th>1</th>
					<td>卷宗封面</td>
				</tr>
				<tr>
					<th>2</th>
					<td>卷内目录</td>
				</tr>
				<tr>
					<th>3</th>
					<td><a  href="${ctx}/clcohol/clcoholRegister/details?id=${clcoholRegister.id}">司法鉴定委托书</a></td>
				</tr>
		
				<tr>
					<th>4</th>
					<td><a <%--  href="${ctx}/clcohol/clcoholRegister/mater?id=${clcoholRegister.id}"> --%>onclick="show6()">材料登记</a></td>
				</tr>
				<tr>
					<th>5</th>
						<td><a href="${ctx}/clcohol/clcoholRegister/sheeet?id=${clcoholRegister.id}" >物证领取</a></td>
				</tr>
				<tr>
					<th>6</th>
						<td><a href="${ctx}/clcohol/clcoholRegister/photo?id=${clcoholRegister.id}" >人员验伤登记材料</a></td>
				</tr>
				<tr>
					<th>7</th>
					<td><a  <%-- href="${ctx}/clcohol/clcoholRegister/report?id=${clcoholRegister.id}"> --%>onclick="show7()">司法鉴定意见书（正稿）</a></td>
				</tr>
				<tr>
					<th>8</th>
					<td><a  href="${ctx}/clcohol/clcoholRegister/papers?id=${clcoholRegister.id}">司法鉴定意见书底稿</a></td>
				</tr>
				<tr>
					<th>9</th>
					<td><a  href="${ctx}/clcohol/clcoholRegister/chargeCredentials?id=${clcoholRegister.id}">收费凭据</a></td>
				</tr>
				<tr>
					<th>10</th>
					<td><a href="${ctx}/clcohol/clcoholRegister/licensed?id=${clcoholRegister.id}">鉴定人执业证复印件</a></td>
				</tr>
				<tr>
					<th>11</th>
					<td><a href="${ctx}/appraisal_xkzfyj.do?id=${clcoholRegister.id}" >司法鉴定许可证复印件</a></td>
				</tr>
				<tr>
					<th>12</th>
					<td>卷内备考表</td>
				</tr>
				<tr>
					<th>13</th>
					<td>案卷封底</td>
				</tr>
			</table>
			
			
	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   id="exam1"style="display: none;">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${attorneyPath}" type="application/pdf" style="position: absolute;left: 389px;top: 36px;"  >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;"id="exam2" onclick="hide6()">关闭</h2>
	
	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   id="exam3"style="display: none;">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${reportPath}" type="application/pdf" style="position: absolute;left: 389px;top: 36px;"  >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;"id="exam4" onclick="hide7()">关闭</h2>
	
</body>
</html>