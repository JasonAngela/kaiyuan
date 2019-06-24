<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>当事人</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
			$("#btnMenu").hide();
			
			$("#page").show();
			$("#page2").show();
			$("#page3").show();
			$("#page4").show();
			$("#page5").show();
			$("#page6").show();
			$("#page7").show();
			window.print();
			$("#lover").show();
			$("#btnMenu").show();
			$("#page").hide();
			$("#page2").hide();
			$("#page3").hide();
			$("#page4").hide();
			$("#page5").hide();
			$("#page6").hide();
			$("#page7").hide();
		}
	  
     </script>
     <style>
    .page
    {
      width:800px;
      min-height: 1130px;
	 margin:0 auto;
      background: white;
    }
    
	 .page2
    {
      width:800px;
      min-height: 1130px;
	 margin:0 auto;
     background: white;
	 margin-top:80px;
  
    }
     .page3
    {
      width:800px;
      min-height: 1030px;
	 margin:0 auto;
     background: white;
	 margin-top:130px;
  
    }
    
      .page4
    {
      width:800px;
      min-height: 1030px;
	 margin:0 auto;
     background: white;
	 margin-top:130px;
  
    }
    
      .page5
    {
      width:800px;
      min-height: 1030px;
	 margin:0 auto;
     background: white;
	 margin-top:130px;
  
    }
      .page6
    {
      width:800px;
      min-height: 1030px;
	 margin:0 auto;
     background: white;
	 margin-top:130px;
  
    }
    
       .page7
    {
      width:800px;
      min-height: 1030px;
	 margin:0 auto;
     background: white;
	 margin-top:130px;
  
    }
    
    
    
    
    
    .subpage1
    {
	  position: absolute;
      height: 222px;
	  width:353px;
    
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
    }
	
	
	
	 .subpage10
    {
	
	  position: absolute;
      height: 500px;
	  width:800px;
     
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
    }
	
	
	 .subpage11
    {
	position: absolute;
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
     height: 500px;
	  width:800px;
	 
    }
	 .subpage2
    {
	position: absolute;
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
     height: 222px;
	  width:353px;
      margin-left: 120mm;
	
      
    }
	
	
	
	 .subpage3
    {
     importCSS: false;
	position: absolute;
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
     height: 222px;
	  width:353px;


      
    }
	
	 .subpage4
    {
     importCSS: false;
	  position: absolute;
	  background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
     height: 222px;
	  width:353px;
	
      margin-left: 120mm;
    }
  </style>
	
</head>
<body>



<c:if test="${not empty  strs}">
<c:if test="${kl>0}">
 <div class="page">
 <c:if test="${not empty NOpic}">
   ${NOpic}
 </c:if>
 <c:forEach items="${strs}" var="pics">
 	<c:if test="${pics.a==1}">
 	<div style="margin-top: 2.5mm;">
 	${pics.d}
 	</div>
 	
 	<img alt="" src="/photo/${pics.b}" class="subpage1"/>
    <img alt="" src="/photo/${pics.c}" class="subpage2"/>
 	</c:if>
 	<c:if test="${pics.a==2}">
 	<div style="margin-top: 145mm;">
 	${pics.d}
 	</div>
 	<img alt="" src="/photo/${pics.b}" class="subpage3"/>
   <img alt="" src="/photo/${pics.c}" class="subpage4"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>

 <c:if test="${not empty  strs}">
<c:if test="${kl>2 && kl<=4}">
 <div class="page2">
 <c:forEach items="${strs}" var="pics">
 <div>
 
 </div>
 	<c:if test="${pics.a==3}">
 	<div style="margin-top: 2.5mm;">
 	${pics.d}
 	</div>
 	<img alt="" src="/photo/${pics.b}" class="subpage1"/>
    <img alt="" src="/photo/${pics.c}" class="subpage2"/>
 	</c:if>
 	<c:if test="${pics.a==4}">
 	<div style="margin-top: 2.5mm;">
 	${pics.d}
 	</div>
 	<img alt="" src="/photo/${pics.b}" class="subpage3"/>
   <img alt="" src="/photo/${pics.c}" class="subpage4"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>
 

 <c:if test="${not empty  strs2}">
<c:if test="${kl2>0}">

 <div class="page3">
<div style="margin-top: 2.5mm;">
 	${strs2[0].d}
 	</div>
 <c:forEach items="${strs2}" var="pics">
 	<c:if test="${pics.a==1}">

 	<img alt="" src="/photo/${pics.b}"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>
 
 

 <c:if test="${not empty  strs2}">
<c:if test="${kl2>1 && kl2<=2}">
 <div class="page4">
 <div style="margin-top: 2.5mm;">
 	${strs2[1].d}
 	</div>
 
 <c:forEach items="${strs2}" var="pics">
 	<c:if test="${pics.a==2}">
 	<img alt="" src="/photo/${pics.b}"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>
 
 
 
 <c:if test="${not empty  strs2}">
<c:if test="${kl2>2 && kl2<=3}">
 <div class="page5">
 <div style="margin-top: 2.5mm;">
 	${strs2[1].d}
 	</div>
 <c:forEach items="${strs2}" var="pics">
 	<c:if test="${pics.a==3}">
 	<img alt="" src="/photo/${pics.b}"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>
  
<c:if test="${not empty  strs3}">
<c:if test="${kl3>0}">
 <div class="page6">
 <c:forEach items="${strs3}" var="pics">
 	<c:if test="${pics.a==1}">
 <div style="margin-top: 4.5mm;">
 ${pics.d}
 </div>
 	<img alt="" src="/photo/${pics.b}" class="subpage10"/>
 	</c:if>
 	
 	<c:if test="${pics.a==2}">
<div style="margin-top: 150mm;">
${pics.d}
</div>
 	<img alt="" src="/photo/${pics.b}" class="subpage11"/>
 	</c:if>
 </c:forEach>
 </div>
 </c:if>
 </c:if>

<c:if test="${not empty  strs4}">
 <div class="page7">
 <div style="margin-top: 2.5mm;">
 	${strs4[0].d}
 	</div>
 	<img alt="" src="/photo/${strs4[0].b}"/>
 </div>
 </c:if>



<div id="lover" class="form-actions" align="center" style="position: absolute;left: 1667px;
  top: 20px;">
	<input class="btn" type="button" value="去打印" onclick="printEntrust()"/>
</div>

</body>
</html>