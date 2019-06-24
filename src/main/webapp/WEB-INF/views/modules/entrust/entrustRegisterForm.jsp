
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存成功管理</title>
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
			
			$(".idNo").each(function(i){
			    alert(this.value());
			})
			
		});


		
		
		$("#entrustAbstractsList0_idType").change(function(){
			//alert("1245");
		 var  myselect=document.getElementById("entrustAbstractsList0_idType").val();
			 if(myselect=='增加'){
				 alert("23456789");
			 }
		});
		
		$("#submit_form").submit(function(){
			 if($("#upload_file").val()==''){
				 alert("请选择要上传的文件");
				 return false;
			 }
			var formData =  new FormData($("#submit_form")[0]);
			
			alert(formDara);
			
			// console.log(formData);return false;
			
			
			/*  $.ajax({
				debug:true,
				url:'upload.php',
				type: 'POST',
				async: false,
				cache: false,
				data:formData,
				processData: false,
				contentType: false,
				dataType:"json",
				beforeSend: function(){
					uploading = true;
				},
				success : function(data) {
					if (data.status == 1) {
						$("#imgs").attr("src", data.src);
					} else {
						alert("上传出错");
						return false;
					}
					uploading = false;
				}
			 })
			 return false; */
		 })
		
		
		 
		 
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

		$("input[type='hidden']").on('idPicCallBack', function (e, val) {
			// 获取 input type="hidden" 的值
			//cour2(val);
			console.log(val);
		});

		function cour2(obj){
			//hidden赋值
			var pic="#"+obj.id.substring(0,22)+"idPic";
			$(pic).val(obj.value);

			$.ajax({  
	             type : "POST",  //提交方式  
	             url : "${ctx}/entrust/entrustCourier/getID",//路径  
	             data : {  
	                 "imagePath":obj.value  
	             },//数据，这里使用的是Json格式进行传输  
	             success : function(data) {
	            	 for(var key in data){
	            		 if(key=="IDCard"){
	            			 var  df="#"+obj.id.substring(0,22)+"idNo";
	            				$(df).val(data[key]);
	            		 }
						if(key=="name"){
							var  df2="#"+obj.id.substring(0,22)+"clientName";
            				$(df2).val(data[key]);
	            		 }
						if(key=="Birth"){
							var  df3="#"+obj.id.substring(0,22)+"birthday";
							var bir=data[key].substring(0,4)+"/"+data[key].substring(4,6)+"/"+data[key].substring(6,8);
							
            				$(df3).val(bir);
	            		 }
						
						if(key=="sex"){
							var  sexBox=null;
							var  df5="#"+obj.id.substring(0,22)+"appellation";
							
							if(data[key]=="男"){
								sexBox=0;
							$(df3).val("1"); 
							}
							
							if(data[key]=="女"){
								sexBox=1;
								$(df5).val("2"); 
							}
							
							var  df4="#"+obj.id.substring(0,22)+"gender"+sexBox;
							$(df4).attr("checked","checked");
						
	            		 }
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
		  function changeMoneyToChinese(){
		  		var money=$("#allmoney").val();
				var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字
				var cnIntRadice = new Array("","拾","佰","仟"); //基本单位
				var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位
				var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位
				var cnInteger = "整"; //整数金额时后面跟的字符
				var cnIntLast = "元"; //整型完以后的单位
				var maxNum = 999999999999999.9999; //最大处理的数字
				
				var IntegerNum; //金额整数部分
				var DecimalNum; //金额小数部分
				var ChineseStr=""; //输出的中文金额字符串
				var parts; //分离金额后用的数组，预定义
				
				if( money == "" ){
				return "";
				}
				money = parseFloat(money);
				//alert(money);
				if( money >= maxNum ){
				$.alert('超出最大处理数字');
				return "";
				}
				if( money == 0 ){
				ChineseStr = cnNums[0]+cnIntLast+cnInteger;
				//document.getElementById("show").value=ChineseStr;
				return ChineseStr;
				}
				money = money.toString(); //转换为字符串
				if( money.indexOf(".") == -1 ){
				IntegerNum = money;
				DecimalNum = '';
				}else{
				parts = money.split(".");
				IntegerNum = parts[0];
				DecimalNum = parts[1].substr(0,4);
				}
				if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
				zeroCount = 0;
				IntLen = IntegerNum.length;
				for( i=0;i<IntLen;i++ ){
				n = IntegerNum.substr(i,1);
				p = IntLen - i - 1;
				q = p / 4;
				m = p % 4;
				if( n == "0" ){
				zeroCount++;
				}else{
				if( zeroCount > 0 ){
				ChineseStr += cnNums[0];
				}
				zeroCount = 0; //归零
				ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
				}
				if( m==0 && zeroCount<4 ){
				ChineseStr += cnIntUnits[q];
				}
				}
				ChineseStr += cnIntLast;
				//整型部分处理完毕
				}
				if( DecimalNum!= '' ){//小数部分
				decLen = DecimalNum.length;
				for( i=0; i<decLen; i++ ){
				n = DecimalNum.substr(i,1); 
				if( n != '0' ){
				ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
				}
				}
				}
				if( ChineseStr == '' ){
				ChineseStr += cnNums[0]+cnIntLast+cnInteger;
				}
				else if( DecimalNum == '' ){
				ChineseStr += cnInteger;
				}
				$("#dx").val(ChineseStr);
				}
		  function printEntrust(){
				
			  
			  
			  
				window.print();
				
			}

	</script>
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

	<ul class="nav nav-tabs">
		<li><a href="${ctx}/entrust/entrustRegister/">DNA委托受理</a></li>
	</ul><br/>

	<form:form id="inputForm" modelAttribute="entrustRegister" action="${ctx}/entrust/entrustRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="pagecontent">
<!-- 江西景盛司法鉴定中心 -->
			<h1 align="center">江&nbsp;西&nbsp;景&nbsp;盛&nbsp;司&nbsp;法&nbsp;鉴&nbsp;定&nbsp;中&nbsp;心<br/>
		司&nbsp;法&nbsp;鉴&nbsp;定&nbsp;委&nbsp;托&nbsp;书</h1>
		<h6 style="margin-left: 696px;" id="lover1" >景盛[${simple}]鉴/检字第${casecode}号</h6>
		<table width="900" align="center" class="inputTable">

			<tr>
				<th>委 托 人</th>
				<td colspan="2"><form:input path="clientName" htmlEscape="false" class="input-small required"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<th>联系人</th>
				<td colspan="2"><form:input path="clientTel" htmlEscape="false" class="input-small required"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>

			<tr>
				<th>联系地址</th>
				<td colspan="2"><form:input path="clientAddress" htmlEscape="false" class="input-small"/></td>
				<th>联系电话</th>
				<td colspan="2"><form:input path="clientZipcode" htmlEscape="false" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<th>委托日期</th>
				<td colspan="2">
				<form:input path="entrustDate" value="${entrustDate}"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});" class="input-small"/>
				</td>
				<th>送检人(机构)</th>
				<td colspan="2">
				<form:input path="agentName" htmlEscape="false" class="input-small"/>
				</td>
			</tr>
			<tr style="height: 80px;">
			   <th >司法鉴定
			   <br/>
			   机&nbsp;&nbsp;构
			   </th>
			   <td colspan="5">
			  机构名称: &nbsp;&nbsp;江西景盛司法鉴定中心&nbsp;&nbsp;&nbsp;&nbsp;许可证号:360010056   <br/>
			  地址: &nbsp;&nbsp;1、上海市徐汇区漕溪路 250 号银海大楼 A 座 810－811 室；<br/>
								&nbsp;&nbsp;2、上海市奉贤区航南公路 5856 号 404-407 室。<br/>
		            联系人:<form:input path="serverName" value="${user.name}" class="input-small" />
			  联系电话: <form:input path="agentTel" value="${user.phone}" htmlEscape="false" class="input-small"/>
			   </td>
			</tr>                   
			<tr>	
				<th>委托鉴定事项</th>
				<td colspan="5">
				<form:select path="type" class="">
					<form:options items="${fns:getDictList('typeCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small"/>
				</form:select>
				</td>
			</tr>
			<tr height="80px">
				<th>是否属于
				<br/>
				重新鉴定</th>
				<td colspan="2">
						<form:radiobuttons path="authorizeNotification" items="${fns:getDictList('flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""  />
				</td>
				<th>鉴定用途 </th>
				 <td colspan="2">
<input type="radio"  <c:if test="${fn:trim(entrustRegister.whether) eq '1'}">checked="checked"</c:if> name="whetherq" value="1"  readonly="readonly"/> 个人鉴定&nbsp;&nbsp;
<input type="radio" <c:if test="${fn:trim(entrustRegister.whether) eq '2'}">checked="checked"</c:if> name="whetherq" value="2" readonly="readonly"/> 司法鉴定<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.whether) eq '3'}">checked="checked"</c:if> name="whetherq" value="3" readonly="readonly"/> 亲缘鉴定 &nbsp;&nbsp;
<input type="radio" <c:if test="${fn:trim(entrustRegister.whether) eq '4'}">checked="checked"</c:if> name="whetherq" value="4" readonly="readonly"/> 个体识别<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.whether) eq '5'}">checked="checked"</c:if> name="whetherq" value="5" readonly="readonly"/> 同一认定<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.whether) eq '6'}">checked="checked"</c:if> name="whetherq" value="6" readonly="readonly"/> 其他:<br/>
<form:input path="sendMode" htmlEscape="false" class=""/>
				</td>      	      	      
			</tr> 
			<tr>
				<th>鉴定材料:</th>
				<td colspan="5">
				 		<form:select path="aboutMaterials" class=" ">
						<form:options items="${fns:getDictList('aboutMaterialsCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						
					</form:select>
				</td>
			</tr>
			<tr height="70px">
				<th rowspan="2">预计费用及收取方式</th>
				<td colspan="4" >
				   预计收费总金额:￥:
					<form:input path="standardFee"  id="allmoney" class="number input-small" size="3" style="height:20px;" onblur="changeMoneyToChinese()" />
				 大写:<form:input path="capital"  id="dx" />	
				</td>
			</tr>
				<tr>
					<td colspan="5">
						<form:radiobuttons  path="chargeway"  items="${fns:getDictList('chargewayCode')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" />
				</td>
				</tr>
				<tr>
					<th>司法鉴定意见
					<br>
					书发送方式 </th>
				<td colspan="5">
<input type="radio" <c:if test="${fn:trim(entrustRegister.sendWay) eq '1'}">checked="checked"</c:if> name="sendWayq" value="1" />
					自取		<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.sendWay) eq '2'}">checked="checked"</c:if> name="sendWayq" value="2" />
					邮递    &nbsp;&nbsp;地址:<form:input path="clientFax" htmlEscape="false" rows="4" maxlength="255"/><br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.sendWay) eq '3'}">checked="checked"</c:if> name="sendWayq" value="3" />
					其他方式（说明） 	&nbsp;&nbsp;<form:input path="purposeOther" htmlEscape="false" rows="4" maxlength="255"/><br/>
				</tr>




				<tr height="400px;">
					<td colspan="5">
					约定事项:<br/>
					1.(1)关于鉴定材料:<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.materialDispose) eq '1'}">checked="checked"</c:if> name="materialDisposeq" value="1" />
						所有鉴定材料无须退换。	<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.materialDispose) eq '2'}">checked="checked"</c:if> name="materialDisposeq" value="2" />
						鉴定材料须完整，无损坏地退还委托人。	<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.materialDispose) eq '3'}">checked="checked"</c:if> name="materialDisposeq" value="3" />
						因鉴定需要，鉴定材料可能损坏、耗尽，导致无法完整退还。	<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.materialDispose) eq '4'}">checked="checked"</c:if> name="materialDisposeq" value="4" />
						对保管和使用鉴定材料的特殊要求:<form:input path="specialRequirements" htmlEscape="false" class=""/> <br/>



						(2)关于剩余鉴定材料:<br/>
 <input type="radio" <c:if test="${fn:trim(entrustRegister.remaining) eq '1'}">checked="checked"</c:if> name="remainingq" value="1" />      
				      委托人于<form:input  path="weeks" htmlEscape="false" class="input-small"/>周内自行取回。委托人未按时取回的，鉴定机构有权自行处理。<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.remaining) eq '2'}">checked="checked"</c:if> name="remainingq" value="2" />				              
				     	鉴定机构自行处理，如需发生处理费的，按有关收费标准或协商收取<form:input path="specialFee" class="input-small"/>元处理费。<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.remaining) eq '3'}">checked="checked"</c:if> name="remainingq" value="3" class="input-small" />
						其他方式:<form:input path="otherWay" /><br/>



					2.鉴定时限：<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.timeLimitReport) eq '1'}">checked="checked"</c:if> name="timeLimitReportq" value="1" />	
   						<form:input  path="clientEmail" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/>之前完成鉴定，提交司法鉴定意见书。<br/>
<input type="radio" <c:if test="${fn:trim(entrustRegister.timeLimitReport) eq '2'}">checked="checked"</c:if> name="timeLimitReportq" value="2" />							
					从该委托书生效之日起     <form:input  path="timeLimitResult" class="input-small"/> 个工作日内完成鉴定，提交司法鉴定意见书。<br/>
					注：鉴定过程中补充或者重新提取鉴定材料所需时间，不计入鉴定时限。<br/>
					3.需要回避的鉴定人：   <form:input  path="avoidAppraisers" class="input-small"/>，回避事由：<form:input  path="evadingReason" class="input-small"/>。<br/>
					4.经双方协商一致，鉴定过程中可变更委托书内容。<br/>
					5.其他约定事项： 	<form:input  path="clientReceiver" class="input-small"/><br/>
					</td>	
				</tr>
				<tr>
				<th>
				  鉴定风险提示：
				</th>
					<td colspan="5">
						1.鉴定意见属于专家的专业意见，是否被采信取决于办案机关的审查和判断，鉴定人和鉴定机构无权干涉；<br/>
						2.由于受鉴定材料或者其他因素限制，并非所有的鉴定都能得出明确的鉴定意见；<br/>
						3.鉴定活动遵循依法独立、客观、公正的原则，只对鉴定材料和案发事实负责，不会考虑是否有利于任何一方当事人。<br/>
		                                         委托人（签名）：        <br/>               
					</td>
				</tr>
				<tr>
					<th>其他需要说
					<br/>
					明的事项</th>
					<td colspan="5">
					<form:textarea path="remarks" htmlEscape ="false" rows="4" maxlength="255" class="input-xxlarge "/>
					</td>
				</tr>

			<tr>
				<th colspan="6" style="text-align: center;">检案摘要信息</th>
			</tr>

		</table>
			<table class="table"   style="width: 900px;height: 150px;margin-left:1.6%;"  >
				<thead>

							<tr>
								<th>名称</th>
								<th>性别</th>
								<th>称谓</th>
								<th>出生日期</th>
								<th>证件类型</th>
								<th>证件号</th>
								<th>证件图片</th>
								<shiro:hasPermission name="entrust:entrustRegister:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="entrustAbstractsList">
						</tbody>
						<shiro:hasPermission name="entrust:entrustRegister:edit"><tfoot>
							<tr><td colspan="11"><a href="javascript:" onclick="addRow('#entrustAbstractsList',  entrustAbstractsRowIdx, entrustAbstractsTpl);entrustAbstractsRowIdx = entrustAbstractsRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="entrustAbstractsTpl">//<!--
						<tr id="entrustAbstractsList{{idx}}">
							<td class="hide">
								<input id="entrustAbstractsList{{idx}}_id" name="entrustAbstractsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="entrustAbstractsList{{idx}}_delFlag" name="entrustAbstractsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
						
							<td>
								<input id="entrustAbstractsList{{idx}}_clientName" name="entrustAbstractsList[{{idx}}].clientName" type="text" value="{{row.clientName}}" maxlength="255" class="required input-small"/>
							</td>

							<td>
								<select id="entrustAbstractsList{{idx}}_gender" name="entrustAbstractsList[{{idx}}].gender" data-value="{{row.gender}}" class="input-small">
									<c:forEach items="${fns:getDictList('sex')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>

							<td>
								<select id="entrustAbstractsList{{idx}}_appellation" name="entrustAbstractsList[{{idx}}].appellation" data-value="{{row.appellation}}" class="input-small">
									<c:forEach items="${fns:getDictList('appellationCode')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>

							<td>
								<input id="entrustAbstractsList{{idx}}_birthday" name="entrustAbstractsList[{{idx}}].birthday" type="text" readonly="readonly" maxlength="20" class="input-small Wdate "
									value="{{row.birthday}}" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});"/>
							</td>
							<td>
								<select id="entrustAbstractsList{{idx}}_idType" name="entrustAbstractsList[{{idx}}].idType" data-value="{{row.idType}}" class="input-small">
									<c:forEach items="${fns:getDictList('idTypeCode')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
										<option value="增加"  onclick=cour(idTypeCode);>增加</option>
								</select>
							</td>
							<td>     
								<input class="input-small" id="entrustAbstractsList{{idx}}_idNo" name="entrustAbstractsList[{{idx}}].idNo" type="text" value="{{row.idNo}}"  />
							</td>

							<td>
                                
								<input id="entrustAbstractsList{{idx}}_idPic" name="entrustAbstractsList[{{idx}}].idPic" type="hidden" value="{{row.idPic}}" maxlength="255"/>

								<input id="entrustAbstractsList{{idx}}_idPic_finder" name="entrustAbstractsList[{{idx}}].idPic_finder" type="button" value="{{row.idPic}}" style="display:none" onclick="cour2(this)"/>
								<sys:ckfinder input="entrustAbstractsList{{idx}}_idPic_finder" type="images" uploadPath="/entrust/entrustRegister" selectMultiple="false"/>
							</td>

							<%--<td>
								<input id="entrustAbstractsList{{idx}}_clientPic" name="entrustAbstractsList[{{idx}}].clientPic" type="button" maxlength="255" onclick="cour2(this)" />
									<sys:ckfinder input="entrustAbstractsList{{idx}}_clientPic" type="files" uploadPath="/entrust/entrustRegister" selectMultiple="true"/>
							</td>--%>

							<%--<td>
								<input id="entrustAbstractsList{{idx}}_clientPic" name="entrustAbstractsList[{{idx}}].clientPic" type="hidden" value="{{row.clientPic}}" maxlength="255"/>

									<sys:ckfinder input="entrustAbstractsList{{idx}}_clientPic" type="files" uploadPath="/entrust/entrustRegister" selectMultiple="true"/>
							</td> --%>
							<shiro:hasPermission name="entrust:entrustRegister:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#entrustAbstractsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var entrustAbstractsRowIdx = 0, entrustAbstractsTpl = $("#entrustAbstractsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
						var data =${fns:toJson(entrustRegister.entrustAbstractsList)};
							for (var i=0; i<data.length; i++){
								addRow('#entrustAbstractsList', entrustAbstractsRowIdx, entrustAbstractsTpl, data[i]);
								entrustAbstractsRowIdx = entrustAbstractsRowIdx + 1;
							}
						});
					</script>
					
					
		<%--<div class="control-group">
			<label class="control-label">人员合照：</label>
			<div class="controls">
				<form:hidden id="clientArea" path="clientArea" htmlEscape="false" maxlength="1000" class="input-xlarge"/>
				<sys:ckfinder input="clientArea" type="files" uploadPath="/entrust/entrustRegister" selectMultiple="true"/>
			</div>
		</div>--%>
					
			</div>
		<div class="form-actions" style="padding:0;text-align: center;">
			<shiro:hasPermission name="entrust:entrustRegister:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
