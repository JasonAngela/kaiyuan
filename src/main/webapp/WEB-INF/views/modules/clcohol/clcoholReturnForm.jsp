<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒精样品归还管理</title>
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
		<li><a href="${ctx}/clcohol/clcoholReturn/">酒精样品归还列表</a></li>
		<li class="active"><a href="${ctx}/clcohol/clcoholReturn/form?id=${clcoholReturn.id}">酒精样品归还<shiro:hasPermission name="clcohol:clcoholReturn:edit">${not empty clcoholReturn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="clcohol:clcoholReturn:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="clcoholReturn" action="${ctx}/clcohol/clcoholReturn/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">register_id：</label>
			<div class="controls">
				<form:input path="register.id" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物证编号：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">酒精样品归还明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>code</th>
								<th>证件号</th>
								<th>联系电话</th>
								<th>other</th>
								<th>剩余数量</th>
								<th>总数</th>
								<th>使用数量</th>
								<th>备注</th>
								<shiro:hasPermission name="clcohol:clcoholReturn:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="clcoholReturnIteamList">
						</tbody>
						<shiro:hasPermission name="clcohol:clcoholReturn:edit"><tfoot>
							<tr><td colspan="10"><a href="javascript:" onclick="addRow('#clcoholReturnIteamList', clcoholReturnIteamRowIdx, clcoholReturnIteamTpl);clcoholReturnIteamRowIdx = clcoholReturnIteamRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="clcoholReturnIteamTpl">//<!--
						<tr id="clcoholReturnIteamList{{idx}}">
							<td class="hide">
								<input id="clcoholReturnIteamList{{idx}}_id" name="clcoholReturnIteamList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="clcoholReturnIteamList{{idx}}_delFlag" name="clcoholReturnIteamList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_code" name="clcoholReturnIteamList[{{idx}}].code" type="text" value="{{row.code}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_idnumber" name="clcoholReturnIteamList[{{idx}}].idnumber" type="text" value="{{row.idnumber}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_contactphone" name="clcoholReturnIteamList[{{idx}}].contactphone" type="text" value="{{row.contactphone}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_other" name="clcoholReturnIteamList[{{idx}}].other" type="text" value="{{row.other}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_remaining" name="clcoholReturnIteamList[{{idx}}].remaining" type="text" value="{{row.remaining}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_totalNumber" name="clcoholReturnIteamList[{{idx}}].totalNumber" type="text" value="{{row.totalNumber}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="clcoholReturnIteamList{{idx}}_useNumber" name="clcoholReturnIteamList[{{idx}}].useNumber" type="text" value="{{row.useNumber}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<textarea id="clcoholReturnIteamList{{idx}}_remarks" name="clcoholReturnIteamList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="clcohol:clcoholReturn:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#clcoholReturnIteamList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var clcoholReturnIteamRowIdx = 0, clcoholReturnIteamTpl = $("#clcoholReturnIteamTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(clcoholReturn.clcoholReturnIteamList)};
							for (var i=0; i<data.length; i++){
								addRow('#clcoholReturnIteamList', clcoholReturnIteamRowIdx, clcoholReturnIteamTpl, data[i]);
								clcoholReturnIteamRowIdx = clcoholReturnIteamRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="clcohol:clcoholReturn:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>