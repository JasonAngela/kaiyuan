<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>鉴定意见管理</title>
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
						error.appendTo((element.parent()).parent());
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
	
	<style media=print>
   .form-actions{display:none;}
   .btn{display:none;}
</style>
	
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
#pagecontent{
	
	background-color: #F0FFFF;
	    }

.inputTable th {
	font-weight:normal;
	background-color:#C3DAF9;
}
	</style>
	
</head>
<body >


<!-- f m c -->
<div id="pagecontent">

<c:if test="${not empty rcpFC}">
    <br/>    <br/>    <br/>    <br/>    
	<table  align="center" style="border-collapse:collapse;width:704px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-F')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
						<c:if test="${fn:contains(item.specimenCode,'-C')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>


			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 215px;">${strMap.key}</td>

					<c:forEach items="${entrustAbstracts}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-F')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
						<c:if test="${fn:contains(item.specimenCode,'-C')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>

					</c:forEach>

                    <c:forEach items="${fItemList}" var="resultItem">
                        <c:if test="${resultItem.geneLoci eq strMap.key}">
								<td style="text-align: center;width: 215px;">${resultItem.formula}</td>
								<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpFC}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiFC}</th>
               </tr>
            
		</table>

<br/><br/><br/><br/><br/><br/><br/><br/>
</c:if>
<c:if test="${not empty rcpMC }">
   <br/> <br/> <br/> <br/>
	<table  align="center" style="border-collapse:collapse;width:704px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
		<tr>
			<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
			<c:forEach items="${entrustAbstracts}" var="item">
				<c:if test="${fn:contains(item.specimenCode,'-M')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
							${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
				</c:if>
			</c:forEach>
			<c:forEach items="${entrustAbstracts}" var="item">
<c:if test="${fn:contains(item.specimenCode,'-C')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
			</c:forEach>
			<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
				公式
			</th>
			<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
				二联体亲权指数
			</th>
		</tr>


		<c:forEach items="${str}" var="strMap">
			<tr>
				<td style="text-align: center;width: 166px;">${strMap.key}</td>

				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-M')}">
						<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
					</c:if>
						
				</c:forEach>
				<c:forEach items="${entrustAbstracts}" var="item">
              <c:if test="${fn:contains(item.specimenCode,'-C')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
						</c:forEach>
				<c:forEach items="${mItemList}" var="resultItem">
					<c:if test="${resultItem.geneLoci eq strMap.key}">
						<td style="text-align: center;width: 215px;">${resultItem.formula }</td>
						<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
					</c:if>
				</c:forEach>
			</tr>
		</c:forEach>
		   <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpMC}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiMC}</th>
               </tr>
	</table>
           <br/><br/><br/><br/><br/><br/><br/></br>
</c:if>
            <!-- f m c2 -->
            <c:if test="${not empty rcpFC2 }">
   <br/>   <br/>    <br/>    <br/>   
	<table  align="center" style="border-collapse:collapse;width:754px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-F')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>
				<c:forEach items="${entrustAbstracts4}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C2')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item4.clientName}
						<br/>
						(${item4.specimenCode})
					</th>
						</c:if>
					</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>
			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-F')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
					</c:forEach>

				<c:forEach items="${entrustAbstracts4}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C2')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item4.specimenCode]}</td>
						</c:if>
						</c:forEach>
                    <c:forEach items="${fItemList2}" var="resultItem">
                        <c:if test="${resultItem.geneLoci eq strMap.key}">
								<td style="text-align: center;width: 215px;">${resultItem.formula}</td>
								<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpFC2}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiFC2}</th>
               </tr>
		</table>
		
		

         <br/><br/><br/><br/><br/><br/><br/>
</c:if>
            <!-- f m c2 -->
            <c:if test="${not empty rcpMC2 }">
   <br/>   <br/>    <br/>    <br/>    <br/>    <br/>    <br/>  
	<table  align="center" style="border-collapse:collapse;width:754px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-M')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>
				<c:forEach items="${entrustAbstracts4}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C2')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item4.clientName}
						<br/>
						(${item4.specimenCode})
					</th>
						</c:if>
					</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>
			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-M')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
					</c:forEach>

				<c:forEach items="${entrustAbstracts4}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C2')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item4.specimenCode]}</td>
						</c:if>
						</c:forEach>
                    <c:forEach items="${fItemList2}" var="resultItem">
                        <c:if test="${resultItem.geneLoci eq strMap.key}">
								<td style="text-align: center;width: 215px;">${resultItem.formula}</td>
								<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpMC2}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiMC2}</th>
               </tr>
		</table>		
       <br/><br/><br/><br/><br/><br/><br/>
</c:if>
	
	  <c:if test="${not empty rcpFC3 }">
   <br/>   <br/>    <br/>    <br/>    <br/>    <br/>    <br/>    
	<table  align="center" style="border-collapse:collapse;width:754px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-F')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>
				<c:forEach items="${entrustAbstracts5}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C3')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item4.clientName}
						<br/>
						(${item4.specimenCode})
					</th>
						</c:if>
					</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>
			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-F')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
					</c:forEach>

				<c:forEach items="${entrustAbstracts5}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C3')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item4.specimenCode]}</td>
						</c:if>
						</c:forEach>
                    <c:forEach items="${fItemList3}" var="resultItem">
                        <c:if test="${resultItem.geneLoci eq strMap.key}">
								<td style="text-align: center;width: 215px;">${resultItem.formula}</td>
								<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpFC3}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiFC3}</th>
               </tr>
		</table>		
         <br/><br/><br/><br/><br/><br/><br/>
</c:if>
	   <!-- f m c2 -->
            <c:if test="${not empty rcpMC3}">
   <br/>   <br/>    <br/>    <br/>    <br/>    <br/>    <br/> 
	<table  align="center" style="border-collapse:collapse;width:754px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-M')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>
				<c:forEach items="${entrustAbstracts5}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C3')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item4.clientName}
						<br/>
						(${item4.specimenCode})
					</th>
						</c:if>
					</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>
			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-M')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>
					</c:forEach>

				<c:forEach items="${entrustAbstracts4}" var="item4">
						<c:if test="${fn:contains(item4.specimenCode,'-C3')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item4.specimenCode]}</td>
						</c:if>
						</c:forEach>
                    <c:forEach items="${fItemList3}" var="resultItem">
                        <c:if test="${resultItem.geneLoci eq strMap.key}">
								<td style="text-align: center;width: 215px;">${resultItem.formula}</td>
								<td style="text-align: center;width: 215px;">${resultItem.pi}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>rcp:</th>
                  <th>${rcpMC3}</th>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <th>cpi:</h>
                  <th>${cpiMC3}</th>
               </tr>
		</table>		
            </c:if>
     <!-- G -->
        
        
        
            <c:if test="${not empty entrustAbstracts7}">
    <br/>    <br/>    <br/>    <br/>    
	<table  align="center" style="border-collapse:collapse;width:704px;border-bottom: 1px solid #000;border-top: 1px solid #000;" cellpadding="0" cellspacing="0"
			class="inputTable">
			<tr>
				<th style="width: 166px;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">Str基因座</th>
				<c:forEach items="${entrustAbstracts7}" var="item">
					<c:if test="${fn:contains(item.specimenCode,'-G')}">
					<th   style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;" >
						${item.clientName}
						<br/>
						(${item.specimenCode})
					</th>
						</c:if>
				</c:forEach>

				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						公式
				</th>
				<th style="text-align: center; border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000;">
						二联体亲权指数
				</th>
			</tr>


			<c:forEach items="${str}" var="strMap">
                <tr>
                    <td style="text-align: center;width: 215px;">${strMap.key}</td>
					<c:forEach items="${entrustAbstracts7}" var="item">
						<c:if test="${fn:contains(item.specimenCode,'-G')}">
							<td style="text-align: center;width: 215px;">${strMap.value[item.specimenCode]}</td>
						</c:if>

					</c:forEach>

                   
                </tr>
            </c:forEach>
		</table>
<br/><br/><br/><br/><br/><br/><br/><br/>
</c:if>
            
            
            
            </div>
            
            <div id="lover" class="form-actions" align="center">
			<input class="btn" type="button" value="去打印" onclick="window.focus();window.print()"/>
			<a class="jquery-word-export"><input class="btn" type="button" value="导出word"></a>
		</div>
</body>
</html>