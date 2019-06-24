<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>材料登记管理</title>
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
				$("#lover1").hide();
				$("#a").show();
				$("#b").show();
				$("#c").show();
				window.print();
				$("#lover").show();
				$("#lover1").show();
				$("#a").hide();
				$("#b").hide();
				$("#c").hide();
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
	<div id="a" class="o">
		<h1 align="center">江西景盛司法鉴定中心<br/>
		法医临床</h1>
	</div>
	
	<table align="center"  class="inputTable" style="width: 210mm;">
							<tr>
								<th width="52.5mm">材料编号</th>
								<th width="52.5mm">材料名称</th>
								<th width="52.5mm">材料数量</th>
								<th width="52.5mm">材料类型</th>
							</tr>
				<c:forEach items="${clinicPhysicalIteam}" var="clinicPhysicalIteam">
						<tr>
							<td >${clinicPhysicalIteam.code}</td>	
							<td width="52.5mm">${clinicPhysicalIteam.name}</td>
							<td>${clinicPhysicalIteam.quantity}</td>	
							<td>${clinicPhysicalIteam.type}</td>	
						</tr>
							<tr>
								<th>描述</th>
								<td colspan="3">${clinicPhysicalIteam.remarks}</td>
							</tr>
				</c:forEach>
	</table>
					<div align="center" style="float: left;padding-left: 400px;display:none" id="b" class="o">
					委托人<br/>
					（承办人签名或盖章）:
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					          &nbsp;&nbsp; &nbsp;&nbsp;年 &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;日
					</div>
					<div style="padding-left: 600px;display:none" id="c">
							接受委托的鉴定机构<br/>
							(签名或盖章):<br/>
							<br/>
							<br/>
							<br/>
							<br/>
					       &nbsp;&nbsp; &nbsp;&nbsp;年 &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;日
					</div>
					
							
					
					
		<div class="form-actions" id="lover" align="center">
		
			<input class="btn" type="button" value="打印" onclick="printEntrust()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
</body>
</html>