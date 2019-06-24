<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领取样品管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">领取样品</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dnaReceive" action="${ctx}/dna/dnaReceive/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">领取样品明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 180px;">
							<tr style="width: 180px;">
								<th class="hide"></th>
								<th>样品编号</th>
								<th width="10">&nbsp;</th>
							</tr>
						</thead>
						<tbody id="dnaReceiveIteamList">
						</tbody>
						 <%--<tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#dnaReceiveIteamList', dnaReceiveIteamRowIdx, dnaReceiveIteamTpl);dnaReceiveIteamRowIdx = dnaReceiveIteamRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot> --%>
					</table>
					<script type="text/template" id="dnaReceiveIteamTpl">//<!--
						<tr id="dnaReceiveIteamList{{idx}}">
							<td class="hide">
								<input id="dnaReceiveIteamList{{idx}}_id" name="dnaReceiveIteamList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="dnaReceiveIteamList{{idx}}_delFlag" name="dnaReceiveIteamList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="dnaReceiveIteamList{{idx}}_specode" name="dnaReceiveIteamList[{{idx}}].specode" type="text" value="{{row.specode}}" maxlength="255" class="input-xlarge " style= "background-color:#CDCDC1" />
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#dnaReceiveIteamList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var dnaReceiveIteamRowIdx = 0, dnaReceiveIteamTpl = $("#dnaReceiveIteamTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(dnaReceive.dnaReceiveIteamList)};
							for (var i=0; i<data.length; i++){
								addRow('#dnaReceiveIteamList', dnaReceiveIteamRowIdx, dnaReceiveIteamTpl, data[i]);
								dnaReceiveIteamRowIdx = dnaReceiveIteamRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>