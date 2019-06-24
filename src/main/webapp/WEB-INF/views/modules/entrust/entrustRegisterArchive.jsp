<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
	<title>DNA归档</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
		   
			
			
		});
		
		function hide() {
			$("#pagecontent").hide();
		}
		function show() {
			$("#pagecontent").show();
		}
		
		

		function hide5() {
			$("#entrust1").hide();
			$("#entrust2").hide();
		}
		function show5() {
			$("#entrust1").show();
			$("#entrust2").show();
		}
		
		
		function hide7() {
			$("#img1").hide();
			$("#img2").hide();
		}
		function show7() {
			$("#img1").show();
			$("#img2").show();
		}
		
		
		function hide8() {
			$("#img1").hide();
			$("#img2").hide();
		}
		function show8() {
			$("#img1").show();
			$("#img2").show();
		}
		

		function hide9() {
			$("#cer1").hide();
			$("#cer2").hide();
		}
		function show9() {
			$("#cer1").show();
			$("#cer2").show();
		}
		
		function hide10() {
			$("#lis1").hide();
			$("#lis2").hide();
		}
		function show10() {
			$("#lis1").show();
			$("#lis2").show();
		}
		
		
		function hide11() {
			$("#cir1").hide();
			$("#cir2").hide();
		}
		function show11() {
			$("#cir1").show();
			$("#cir2").show();
		}
		
		function hide12() {
			$("#ampLi1").hide();
			$("#ampLi2").hide();
		}
		function show12() {
			$("#ampLi1").show();
			$("#ampLi2").show();
		}
		
		function hide13() {
			$("#IDpic1").hide();
			$("#IDpic2").hide();
		}
		function show13() {
			$("#IDpic1").show();
			$("#IDpic2").show();
		}
		
		function hide14() {
			$("#jdPath1").hide();
			$("#jdPath2").hide();
		}
		function show14() {
			$("#jdPath1").show();
			$("#jdPath2").show();
		}
		
		
		function hide6() {
			$("#report1").hide();
			$("#report2").hide();
			$("#report3").hide();
			$("#report4").hide();
			
			
		}
		function show6() {
			$("#report1").show();
			$("#report2").show();
			$("#report3").show();
			$("#report4").show();
		}
		
		
		
		
		
		
		function show2() {
			$("#pagecontent2").show();
		}
		
		
		function hide2() {
			$("#pagecontent2").hide();
		}
		
		
		
		
		function show3() {
			$("#pagecontent3").show();
		}
		
		
		function hide3() {
			$("#pagecontent3").hide();
		}
		
		function show4() {
			$("#pagecontent4").show();
		}
		
		
		function hide4() {
			$("#pagecontent4").hide();
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
		
		
	     
	     function printEntrust(){
				$("#lover").hide();
				$("#lover2").hide();
				$("#a").show();
				window.print();
				$("#lover").show();
				$("#lover2").show();
				$("#a").hide();
				
			}
		
	</script>
	
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

#pagecontent2{
	
	background-color: #F0FFFF;
	    }
	    #pagecontent3{
	
	background-color: #F0FFFF;
	    }
	    
	    #pagecontent4{
	
	background-color: #F0FFFF;
	    }
	    
	
	
	
	
	
	
	
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

 #divA4{
      width: 21cm;
      min-height: 29.7cm;
      padding: 2cm;
      margin: 1cm auto;
      border: 1px #D3D3D3 solid;
      border-radius: 5px;
      background: white;
      box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    }
</style>   

</head>

<body>
     
		      <table style="margin-top:30px;width: 300px;height: 700px;"  class="inputTable" id="lover2">
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
					<td><%-- <a  href="${ctx}/entrust/entrustRegister/details?id=${entrustRegister.id}" id="show">司法鉴定委托书</a> --%>
					<a onclick="show5()">司法鉴定委托书 </a>
					
					</td>
				</tr>
		
				<%-- <tr>
					<th>4</th>
					<td><a  href="${ctx}/entrust/entrustRegister/entrustRegisterReceipt?id=${entrustRegister.id}">鉴定材料接收和返还清单</a></td>
				</tr>
				<tr>
					<th>5</th>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
						<td><a href="${ctx}/entrust/entrustRegister/sheeet?id=${entrustRegister.id}" >鉴定材料流转单</a></td>
				</tr> --%> 
				<tr>
				
					<th>6</th>
					<td><%-- <a href="${ctx}/entrust/entrustRegister/findBoard?id=${entrustRegister.id}" > --%><a onclick="show12()">DNA扩增记录表</a></td>
				</tr>
				<tr>
					<th>7</th>
					<td><%-- a href="${ctx}/entrust/entrustRegister/map?id=${entrustRegister.id}" --%><a onclick="show7()">原始检验记录表、图谱</a></td>
				</tr>
				<tr>
					<th>8</th>
					<td><%-- <a  href="${ctx}/entrust/entrustRegister/report?id=${entrustRegister.id}"> --%><a onclick="show6()">司法鉴定意见书（正稿）</a></td>
				</tr>
				<tr>
					<th>9</th>
					<td><a  href="${ctx}/entrust/entrustRegister/modifyrecord?id=${entrustRegister.id}">司法鉴定意见书底稿</a></td>
				</tr>
				<tr>
					<th>10</th>
					<td><%--<a   href="${ctx}/entrust/entrustRegister/parties?id=${entrustRegister.id}" --%><!-- <a onclick="show7()"> -->	
					
					<%-- <a href="${ctx}/entrust/entrustRegister/parties?id=${entrustRegister.id}"  target="_blank"> --%>
					<a onclick="show13()">
					当事人身份证复印件或当事人户口本复印件</a></td>
				</tr>
				<tr>
					<th>11</th>
					<td><a  href="${ctx}/entrust/entrustRegister/findDnaExtract?id=${entrustRegister.id}">	提取</a></td>
				</tr>
				<tr>
					<th>12</th>
					<td><a  href="${ctx}/entrust/entrustRegister/findDnaPreparation?id=${entrustRegister.id}">	扩增</a></td>
				</tr>
				<tr>
					<th>13</th>
					<td><a  href="${ctx}/entrust/entrustRegister/findDnaPreparationReagents?id=${entrustRegister.id}">	电泳</a></td>
				</tr>
				<tr>
					<th>14</th>
					<td><a href="${ctx}/entrust/entrustRegister/calculate?id=${entrustRegister.id}" >计算表格</a></td>
				</tr>
				
				<tr>
					<th>15</th>
					<td><a  href="${ctx}/entrust/entrustRegister/chargeCredentials?id=${entrustRegister.id}">收费凭据</a></td>
				</tr>
				<tr>
					<th>16</th>
					<td><a onclick="show10()"> 
						鉴定材料接收流转及返还清单 
					</a></td>
				</tr>
				
				<tr>
					<th>17</th>
					<td><a onclick="show11()">内部流转审批表</a></td>
				</tr>
				<tr>
					<th>18</th>
					<td><%-- <a href="${ctx}/entrust/entrustRegister/licensed?id=${entrustRegister.id}"> --%><a onclick="show9()">鉴定人执业证复印件</a></td>
				</tr>
				<tr>
					<th>19</th>
					<td><a onclick="show14()">司法鉴定许可证复印件</a></td>
				</tr>
				<tr>
					<th>20</th>
					<td>卷内备考表</td>
				</tr>
				<tr>
					<th>21</th>
					<td>案卷封底</td>
				</tr>
			</table>
			
		
	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   id="entrust1"style="display: none;">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${attorneyPath}" type="application/pdf" style="position: absolute;left: 389px;top: 36px;"  >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;"id="entrust2" onclick="hide5()">关闭</h2>
    
    
     <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"  style="display: none;" id="report1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${reportPath}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;"  >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="report2" onclick="hide6()" >关闭</h2>
	<h2 style="position: absolute;left: 1397px;top: 62px;display: none;" id="report4"> <a  href="${ctx}/entrust/entrustRegister/downLoadFile?registerId=${entrustRegister.id}" >导出word</a></h2>
	
	
	
	
     <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"  style="display: none;" id="img1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${imgPath}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;"  >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="img2" onclick="hide7()" >关闭</h2>
	
	
	
	 <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"  style="display: none;" id="map1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${mapPic}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="map2" onclick="hide8()" >关闭</h2>
			 
			 
			 
			  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="cer1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${cerTi}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="cer2" onclick="hide9()" >关闭</h2>
			 
			 
			  
			  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="lis1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${lisTing}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="lis2" onclick="hide10()" >关闭</h2>
			 
		
    
      
			  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="cir1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${cirCul}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="cir2" onclick="hide11()" >关闭</h2>
	
	
	  <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="ampLi1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${ampLi}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="ampLi2" onclick="hide12()" >关闭</h2>
	
	
	 <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="IDpic1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${IDpic}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="IDpic2" onclick="hide13()" >关闭</h2>
	
	 <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"   style="display: none;" id="jdPath1">
			      <param name="_Version" value="65539"> 
			      <param name="_ExtentX" value="20108"> 
			      <param name="_ExtentY" value="10866"> 
			      <param name="_StockProps" value="0"> 
			      <param name="SRC" value="testing_pdf.pdf"> 
				  <embed id="pdffile" width="1000px" height="880" src="/photo/${jdPath}" type="application/pdf"  style="position: absolute;left: 389px;top: 36px;" >  
			 </object> 
	<h2 style="position: absolute;left: 1397px;top: 32px;display: none;" id="jdPath2" onclick="hide14()" >关闭</h2>
	
			
    
    
    
      
</body>
</html>