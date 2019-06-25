<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>保存成功管理</title>
<meta name="decorator" content="default" />
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
			/*$("a.jquery-word-export").click(function(event) {
		        $("#pagecontent").wordExport();
		    });*/
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
		 function printEntrust(){
				$("#lover").hide();
				window.print();
				$("#lover").show();
			}

		 /*function exportd() {
			//导出模板生成的文档
             var registerId = "${entrustRegister.id}";

             $.ajax({
                 type : "POST",  //提交方式
                 url : "${ctx}/entrust/entrustRegister/downLoadFile",//路径
                 data : {
                     "registerId":registerId
                 },//数据，这里使用的是Json格式进行传输
                 success : function(data) {//返回数据根据结果进行相应的处理
                    /!* $("#cc").val(data.result);
                     document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'*!/
                 }
             });
			
		}*/

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
		<h6 style="margin-left:720px;" id="lover1" >赣(景)[${simple}]物鉴字第${casecode}号</h6>
	</div>


		<table  style="width: 830px;"  width="830" align="center" height="730" class="inputTable">
			<tr>
				<th id="one" width="17%">一、基本情况</th>
				<td></td>
			</tr>
			<tr>
				<td id="two">委 托 人：</td>
				<td id="yes" align="left">${entrustRegister.clientName}</td>
			</tr>
			<tr>
				<td id="two">委托事项：</td>
				<td id="yes" align="left">${fm}</td>
			</tr>
			<tr>
				<td id="two">受理日期：</td>
				<td id="yes" align="left">${df}</td>
			</tr>
			<tr>
				<td id="two">鉴定材料：</td>
				<td id="yes" align="left">${fns:getDictLabel(materialType, 'material_TypeCode', '')}
					(共${qty}份)</td>
			</tr>
			<tr>
				<td id="two">鉴定日期：</td>
				<td id="yes" align="left">${df}</td>
			</tr>
			<tr>
				<td id="two">鉴定地点：</td>
				<td id="yes" align="left">江西省南昌市东湖区五纬路146号</td>
			</tr>
			<tr>
				<td id="two">在场人员：</td>
				<td id="yes" align="left">中心工作人员</td>
			</tr>
			<tr>
				<td id="two" style="text-align: right;" valign="top">
						被鉴定人：
				</td>
					<td id="yes" align="left">
				<table id="ca" cellpadding="0" cellspacing="0"  style="border-collapse:collapse; margin-top: 10px;
			width: 500px;">
				<tr align="center" id="gui">
					<th height="35" width="80">姓名</th>
					<th width="80">性别</th>
					<th width="90">证件类型</th>
					<th width="150">证件号</th>
					<th width="150">样品编号</th>
				</tr>
			</table>

			<table id="gui" cellpadding="0" cellspacing="0"  style="border-collapse:collapse; width: 500px">
			<c:forEach items="${entrustAbstracts}" var="entrustAbstracts" >
				<tr align="center">
				  	<td  width="80" height="35">${entrustAbstracts.clientName}</td>
				    <td  width="80"><c:if test="${entrustAbstracts.gender ==1}">男</c:if>
						<c:if test="${entrustAbstracts.gender ==2}">女</c:if>
						<c:if test="${entrustAbstracts.gender ==3}">未知</c:if>
				   	</td>
					<td  width="90">${fns:getDictLabel(entrustAbstracts.idType, 'idTypeCode', '')}</td>
					<td  width="150">${entrustAbstracts.idNo}</td>
					<td  width="150"> ${entrustAbstracts.specimenCode}</td>
				</tr>
			</c:forEach>
		</table>
				</td>
			</tr>




			<tr >
				<td id="one" colspan="2">二、检案摘要：</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;${testcase}</td>
			</tr>

			<tr>
				<td id="one" colspan="2">三、检验过程:</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">(一)检验规范</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;检材按照《法庭科学DNA实验室检验规范（GA/T383-2014）》、《亲权鉴定技术规范（SF/Z JD0105001-2016）》进行检测。 <br />
				</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">(二)检验步骤</td>
			</tr>
			<tr>
				<td id="yes"colspan="2" >
					&nbsp;&nbsp;&nbsp;&nbsp;1、扩增：使用MicroreaderTM 21 Direct ID System试剂盒在9700型扩增仪（AB）上对样品进行扩增；扩增体系10μ1。 <br />&nbsp;&nbsp;&nbsp;&nbsp;2、毛细管电泳：使用3130xL型基因分析仪(AB)检测扩增产物，确定其DNA分型。
				</td>
			</tr>
			<tr>
				<td id="yes" colspan="2">(三)检验结果</td>
			</tr>
		</table>





		<table  align="center" style="border-collapse:collapse;width:794px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;
border-bottom-color: #000;">基因座名称</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
				</c:forEach>

			</tr>


			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 166px;">${strMap.key}</td>

					<c:forEach items="${entrustAbstracts}" var="item">
						<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
					</c:forEach>
                </tr>
            </c:forEach>

		</table>



		<table   width="830" align="center">
			<tr>
				<td id="one">四、分析说明</td>
			</tr>
			<tr>
				<td id="yes" style="word-break:break-all">
				${finaler}</td>
			</tr>
			<tr>
				<td id="one">五、鉴定意见</td>
			</tr>
			<tr>
				<td id="yes">
				 ${explain}</td>
			</tr>
			<tr>
				<td id="yes" style="line-height: 35px; text-align: right;">
						司法鉴定人签名或者盖章   周武庆<br />
                       《司法鉴定人执业证》证号：360113012032
				<br /> <br /> <br />
						司法鉴定人签名或者盖章   匡渤海<br />
                       《司法鉴定人执业证》证号：360108012007<br />
				</td>
			</tr>
		</table>
	</div>
	<table  id="lover" border="0" width="500" align="center" style="margin-top: 20px">
		<tr id="btn">
			<td align="center">
			<input class="btn" type="button" value="打印" onclick="printEntrust()"/>
			<%--<input class="btn" type="button" value="打印1测试" onclick="exportd()"/>--%>
			<a class="btn btn-primary" href="${ctx}/entrust/entrustRegister/downLoadFile?registerId=${entrustRegister.id}">导出</a>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</tr>
	</table>
</body>
</html>
