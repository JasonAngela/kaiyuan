<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法医成文修改管理</title>
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
	$("a.jquery-word-export").click(function(event) {
        $("#pagecontent").wordExport();
    });
});

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
	#no{font-family : SimHei;
		font-size:24px;
		letter-spacing: 1px;}
	#gan{font-family : simsun;
		font-size:14px;}
	#one{font-family : SimHei;
		font-size:19px;
		letter-spacing: 2px;}
	#two{font-family : SimHei;
		font-size:18px;
		letter-spacing: 1px;
		text-align: right;}
	#yes{font-family : STFangsong;
		font-size:18px;
		font-weight: 100;
		letter-spacing: 1px;
		}
	textarea{font-family : STFangsong;    
			font-size:19px;
			font-weight: 100;
			letter-spacing: 2px;}
	#ca{
		border-top: 1px solid #000;
		border-bottom: 1px solid #000;
		}
	#gui{
		border-bottom: 1px solid #000;
		}
	#pagecontent{
	
	background-color: #F0FFFF;
	    }
</style>
</head>


<body>

<div id="pagecontent" style="width: 930px;margin-left: 300px;">
	<div id="a" class="o" >
		<h1 align="center">上海开元司法鉴定中心<br/>
		法医物证司法鉴定意见书</h1>
		<h6 style="margin-left:620px;" id="lover1" >赣(景)[${simple}]物鉴字第${casecode}号</h6>	
	</div>
	
<table  style="width: 830px;"  width="830" align="center" height="730" class="inputTable">
			<tr>
				<th id="one" width="17%">一、基本情况</th>
				<td></td>
			</tr>
			<tr>
				<td id="two">委 托 方:</td>
				<td id="yes" align="left">${clinicWritten.delegate}</td>
			</tr>
			<tr>
				<td id="two">委托事项:</td>
				<td id="yes" align="left">${fns:getDictLabel(clinicWritten.toaccept, 'material_TypeCode', '')}</td>
			</tr>
			<tr>
				<td id="two">受理日期:</td>
				<td id="yes" align="left">${clinicWritten.acceptdate}</td>
			</tr>
			<tr>
				<td id="two">鉴定材料:</td>
				<td id="yes" align="left">${clinicWritten.identification}</td>
			</tr>
			<tr>
				<td id="two">被鉴定人:</td>
				<td id="yes" align="left">${clinicWritten.bysurveyor}</td>
			</tr>
			
			<tr>
			<tr >
				<td id="one" colspan="2">二、基本案情</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;${clinicWritten.opinion}</td>
			</tr>
			
			
			<tr >
				<td id="one" colspan="2">三、资料摘要</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;${clinicWritten.clinicthispaper}</td>
			</tr>
			
			
			<tr>
				<td id="one" colspan="2">四、鉴定过程</th>
			</tr>
			
			<tr>
				<td id="yes" colspan="2">（一）检验方法</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">
					&nbsp;&nbsp;${clinicWritten.inspectionmethods} <br />
				</td>
			</tr>	
			<tr>
				<td id="yes" colspan="2">（二）鉴定标准</td>
			</tr>
			<tr>
				<td id="yes"colspan="2" >
					&nbsp;&nbsp;${clinicWritten.appraisalstandard}
				</td>
			</tr>	
				
			<tr>
				<td id="yes" colspan="2">（三）鉴定经过</td>
			</tr>
			<tr>
				<td id="yes">鉴定日期:</td>
				<td id="yes" align="left">${clinicWritten.authorisesurveyor}</td>
			</tr>	
			
			<tr>
				<td id="yes">在场人员:</td>
				<td id="yes" align="left">${clinicWritten.personnel}</td>
			</tr>
			
			<tr>
				<td id="yes"">主诉:</td>
				<td id="yes" align="left">${clinicWritten.cc}</td>
			</tr>
			<tr>
				<td id="yes">查体:</td>
				<td id="yes" align="left">${clinicWritten.body}</td>
			</tr>
			
			<tr>
				<td id="yes" colspan="2">（四）阅片</td>
			</tr>
			<tr>
				<td id="yes"colspan="2" >
					&nbsp;&nbsp;${clinicWritten.reading}
				</td>
			</tr>
			<tr >
				<td id="one" colspan="2">五:分析说明</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;${clinicWritten.analysisshows}</td>
			</tr>
			
			<tr >
				<td id="one" colspan="2">六:鉴定意见</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;${clinicWritten.expertopinion}</td>
			</tr>
			
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;${first }</td>
			</tr>
		<%-- 	${first }
		${second}
		${authoriseuser } --%>
			
			
			
			
</table>
</div>

<table  id="lover" border="0" width="500" align="center" style="margin-top: 20px">
		<tr id="btn">
			<td align="center">
			<input class="btn" type="button" value="打印" onclick="printEntrust()"/>
			<a class="jquery-word-export"><input class="btn" type="button" value="导出word"/></a>
		</tr>
	</table>			


		
	
		
		
		
	
</body>
</html>
</body>
</html>