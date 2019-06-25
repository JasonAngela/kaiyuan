<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物证登记管理</title>
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
		
		  function readcard(e,index) {
			/*   alert(index);
			  $("#specimenXuekas"+index+"_xuekaId").val(index);
						  alert($("#specimenXuekas"+index+"_xuekaId").val()); */
			  $.ajax({
				    url:"${ctx}/material/specimenMaterialRegister/readcard",    //请求的url地址
				   // dataType:"json",   //返回格式为json
				    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
				    type:"post",   //请求方式
				    success:function(req){
				        //请求成功时处理
				        $(e).parent().parent().find('input[type=text]').eq(5).val(req);
				    }
			  });
		  }
		  
		  
		  
		  function check() {
			  
			  
			
			  return true;
			  
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
	
	
	
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/material/specimenMaterialRegister/form?id=${specimenMaterialRegister.id}">DNA物证登记<shiro:hasPermission name="material:specimenMaterialRegister:edit">${not empty specimenMaterialRegister.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="material:specimenMaterialRegister:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	
	
	<div id="pagecontent" style="width: 930px;margin-left: 300px;">
	<form:form id="inputForm" modelAttribute="specimenMaterialRegister" action="${ctx}/material/specimenMaterialRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.businessId"/>
		<form:hidden path="entrustRegister.id"/>
		
		<sys:message content="${message}"/>		
				<h1 align="center">上&nbsp;海&nbsp;开&nbsp;元&nbsp;司&nbsp;法&nbsp;鉴&nbsp;定&nbsp;中&nbsp;心<br/>
		物&nbsp;证&nbsp;登&nbsp;记</h1>
		<h6 style="margin-left: 696px;" id="lover1" >开元[${simple}]鉴/检字第${casecode}号</h6>
		<div class="control-group" style="width: 930px;">
					<table id="contentTable" class="table table-striped table-bordered" style="  margin-top: 39px;">
						<thead>
							<tr style="background-color: red">
								<th>编码</th>
								<th>名字(称谓)</th>
								<th>样品类型</th>
								<th>数量</th>
								<th>度量</th>
								<th>血卡编码</th>
								<th>图片</th>
							</tr>
						</thead>
			<c:forEach items="${entrustAbs}" var="entrustAbs" varStatus="kl" >
					<tfoot>
							<tr>
							<td class="hide">
								<input name="specimenMaterialRegisterItemList[${kl.index}].id" type="hidden" value="${row.id}"/>
								<input name="specimenMaterialRegisterItemList[${kl.index}].delFlag" type="hidden" value="0"/>
								<input name="specimenMaterialRegisterItemList[${kl.index}].abstracts" type="hidden" value="${entrustAbs.id}"/>
								<input name="specimenMaterialRegisterItemList[${kl.index}].clientCode" type="hidden" value="${entrustAbs.clientCode}"/>
							</td>
							<td>
								<input  name="specimenMaterialRegisterItemList[${kl.index}].code" type="text" value="${entrustAbs.specimenCode}" maxlength="255" class="input-medium"   style= "background-color:#CDCDC1" />
								<input type="button" value="使用血卡" onclick="readcard(this,${kl.index})">	
							</td>
							<td>
								${entrustAbs.clientName}(${fns:getDictLabel(entrustAbs.appellation, 'appellationCode', '')})			
							</td>
							<td>
								<select  name="specimenMaterialRegisterItemList[${kl.index}].materialType" class="input-small">
									<c:forEach items="${fns:getDictList('material_TypeCode')}" var="dict" >
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input name="specimenMaterialRegisterItemList[${kl.index}].qty" type="text"  maxlength="11" value="1" class="input-small  required"/>
							</td>
							<td>
								<input  name="specimenMaterialRegisterItemList[${kl.index}].measure" type="text" value="0.22mm" maxlength="255" class="input-small required" />
							</td>
							<td>
								<input  name="specimenXuekas[${kl.index}].xuekaId"  id="specimenXuekas${kl.index}_xuekaId" type="text" maxlength="255" class="input-small" />
								<input  name="specimenXuekas[${kl.index}].materialRegisterItemId" type="hidden" maxlength="255">
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList${kl.index}_pic" name="specimenMaterialRegisterItemList[${kl.index}].pic" type="hidden"  maxlength="255"/>
								<sys:ckfinder input="specimenMaterialRegisterItemList${kl.index}_pic" type="files" uploadPath="/entrust/entrustRegister" selectMultiple="true"/>
							</td>
						</tr>
						</tfoot>
					</c:forEach>
					</table>
	     <div class="control-group"  >
			<font >备注：</font>
			<div class="controls" style="margin-left: 40px;margin-top: -18px;">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			
		</div>
		
		
		
		<%--</div>
		
		
			<div class="control-group">
				<label class="control-label">物证登记明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>编码</th>
								<th>样品类型</th>
								<th>数量</th>
								<th>度量</th>
								<th>图片</th>
								<shiro:hasPermission name="material:specimenMaterialRegister:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="specimenMaterialRegisterItemList">
						</tbody>
						<shiro:hasPermission name="material:specimenMaterialRegister:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#specimenMaterialRegisterItemList', specimenMaterialRegisterItemRowIdx, specimenMaterialRegisterItemTpl);specimenMaterialRegisterItemRowIdx = specimenMaterialRegisterItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="specimenMaterialRegisterItemTpl">//<!--
						<tr id="specimenMaterialRegisterItemList{{idx}}">
							<td class="hide">
								<input id="specimenMaterialRegisterItemList{{idx}}_id" name="specimenMaterialRegisterItemList[${kl.index}].id" type="hidden" value="{{row.id}}"/>
								<input id="specimenMaterialRegisterItemList{{idx}}_delFlag" name="specimenMaterialRegisterItemList[${kl.index}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList{{idx}}_code" name="specimenMaterialRegisterItemList[${kl.index}].code" type="text" value="{{row.code}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList{{idx}}_materialType" name="specimenMaterialRegisterItemList[${kl.index}].materialType" type="text" value="{{row.materialType}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList{{idx}}_qty" name="specimenMaterialRegisterItemList[${kl.index}].qty" type="text" value="{{row.qty}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList{{idx}}_measure" name="specimenMaterialRegisterItemList[${kl.index}].measure" type="text" value="{{row.measure}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="specimenMaterialRegisterItemList{{idx}}_pic" name="specimenMaterialRegisterItemList[${kl.index}].pic" type="hidden" value="{{row.pic}}" maxlength="255"/>
								<sys:ckfinder input="specimenMaterialRegisterItemList{{idx}}_pic" type="files" uploadPath="/material/specimenMaterialRegister" selectMultiple="true"/>
							</td>
							<shiro:hasPermission name="material:specimenMaterialRegister:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#specimenMaterialRegisterItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var specimenMaterialRegisterItemRowIdx = 0, specimenMaterialRegisterItemTpl = $("#specimenMaterialRegisterItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(specimenMaterialRegister.specimenMaterialRegisterItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#specimenMaterialRegisterItemList', specimenMaterialRegisterItemRowIdx, specimenMaterialRegisterItemTpl, data[i]);
								specimenMaterialRegisterItemRowIdx = specimenMaterialRegisterItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div> --%>
		<div class="form-actions" style="padding:0;text-align: right;" >
			<shiro:hasPermission name="material:specimenMaterialRegister:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return check()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
</body>
</html>