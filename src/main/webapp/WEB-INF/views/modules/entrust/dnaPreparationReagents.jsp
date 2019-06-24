<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>dna试验</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function allowDrop(ev)
		{
		ev.preventDefault();
		}

		function drag(ev)
		{
			var gray = $("#"+ev.target.id).css("background-color");
			ev.dataTransfer.setData("sourceId",ev.target.id);
		}

		function drop(ev)
		{
			ev.preventDefault();
			var sourceId=ev.dataTransfer.getData("sourceId");
			ev.target.value=$("#"+sourceId).html();
			$("#"+sourceId).css("display","none")
			$("#"+sourceId).attr("selected","true");
		}
		function clearData(inputVar){
			var sourceId = inputVar.value;
			$("#"+sourceId).css("display","inline");
			$("#"+sourceId).attr("selected","false");
			inputVar.value = "";
			
		 	
		}
		
		function selectedPosition(hang,lie){
			$("#selected_hang").val(hang);
			$("#selected_lie").val(lie);
		}
		
		function selectAll(){
			var selected_hang =$("#selected_hang").val();
			var selected_lie =$("#selected_lie").val();
			if(selected_hang==0||selected_lie==0){
				alert("请选择放入的起始！");
				return;
			}
			
			$(".specimenCode_value[selected='false']").each(function(i){
				if(selected_lie>12){
					alert("改板子不能容下如此多数据！");
					return;
				}
				var targetInput = $("#dnaBoardJggList_"+selected_lie+"_"+selected_hang);
				if(targetInput.val()!=""){
					alert("位置：！"+selected_hang+":"+selected_lie+" 不为空！");
					return;
				}
				targetInput.val($(this).html().trim());
				$(this).css("display","none")
				$(this).attr("selected","true");
				selected_hang++;
				if(selected_hang>8){
					selected_hang=1;
					selected_lie++;
				}
				
			});
			
			
		}
	</script>
 <style type="text/css">
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
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
			<td>&nbsp;</td>
			<c:forEach var="item" begin="1" end="12">
				<td align="center" style="text-align: center;">${item}</td>
			</c:forEach>
			</tr>
			</thead>
			<c:forEach items="#{board.dnaBoardJggList}" var="row" varStatus="kl">	
					<c:if test="${(row.lie-1)%12 eq 0}">
						<tr>
							<td>${row.hangLabel}</td>
					</c:if>
					<td>
						<input type="hidden" name="dnaBoardJggList[${kl.index}].id" value="${row.id}"/>
				 		<input id="dnaBoardJggList{{idx}}_specimenCode" name="dnaBoardJggList[${kl.index}].specimenCode" type="text" style="width: 80px;" value="${row.specimenCode}"/>
					</td>
					<c:if test="${(row.lie)%12 eq 0}">
						</tr>
					</c:if>
			</c:forEach>
		</table>
		<table width="800" align="center" style="margin-top:30px"
		class="inputTable">
		<tr align="center">
			<th colspan="4" style="text-align: center;"><h2>电泳分型</h2></th>
		</tr>
		<tr>
			<th>甲酰胺批号</th>
			<td><input name="formamideBatchNumber" htmlEscape="false" maxlength="255" class="input-xlarge" value="${dnaElectrophoresisParting. formamideBatchNumber}"/></td>
			<th>是否已验证有效</th>
			<td>
			<input type="radio" <c:if test="${fn:trim(dnaElectrophoresisParting.validated) eq '1'}">checked="checked"</c:if> name="validated" value="1" />
			是
			<input type="radio" <c:if test="${fn:trim(dnaElectrophoresisParting.validated) eq '2'}">checked="checked"</c:if> name="validated" value="2" />
			否
		</tr>
		<tr>
			<th>内标</th>
			<td><input type="text"name="internalStandard" htmlEscape="false" maxlength="255" class="input-xlarge " value="${dnaElectrophoresisParting.internalStandard}"/></td>
			<th>是否已验证有效</th>
			<td>
			<input type="radio" <c:if test="${fn:trim(dnaElectrophoresisParting.validated1) eq '1'}">checked="checked"</c:if> name="validated1" value="1" />
			是
			<input type="radio" <c:if test="${fn:trim(dnaElectrophoresisParting.validated1) eq '2'}">checked="checked"</c:if> name="validated1" value="2" />
			否
			
		</tr>
		<tr>
			<th>实验室温度</th>
			<td><input type="text" name="temperature" htmlEscape="false" maxlength="255" class="input-xlarge " value="${dnaElectrophoresisParting.temperature}"/></td>
			<th>冰箱温度</th>
			<td><input type="text" name="refrigeratorTemperature" htmlEscape="false" maxlength="255" class="input-xlarge" value="${dnaElectrophoresisParting.refrigeratorTemperature}"/></td>			
		</tr>
		<tr>
			<th>产物取量和处理</th>
			<td><input type="text" name="product" htmlEscape="false" maxlength="255" class="input-xlarge " value="${dnaElectrophoresisParting.product}"/></td>	
			<th>相对湿度</th>
			<td colspan="3"><input type="text" name="humidity" htmlEscape="false" maxlength="255" class="input-xlarge " value="${dnaElectrophoresisParting.humidity}"/></td>		
		</tr>
		<tr>
			<th>阳性对照</th>
			<td ><input type="text" name="positiveControl" htmlEscape="false" maxlength="255" class="input-xlarge" value="${dnaElectrophoresisParting.positiveControl}"/></td>
			<th>阴性对照</th>
			<td ><input type="text" name="negativeControl" htmlEscape="false" maxlength="255" class="input-xlarge "  value="${dnaElectrophoresisParting.negativeControl}"/></td>
		</tr>
	 	<tr>
			<th>实验室</th>
			<td>
				<a href="${ctx}/synth/synthLab/form?id=${lab.id}">电泳室</a>
				<input type="hidden" name="lab.id" value="${lab.id}"/>
			</td>
		</tr> 
	</table>
</body>

</html>