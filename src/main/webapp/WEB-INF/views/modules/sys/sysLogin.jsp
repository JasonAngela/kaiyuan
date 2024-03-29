<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      *{ margin:0; padding:0; font-size:12px;}
      .header{height:80px;padding-top:20px;} .alert{position:relative;width:318px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
      
      ul{ margin:0px; padding:0;}
      li{ list-style:none;}
      .clear{ clear:both;}
	
      .l_top{ margin:0 auto 0 auto; width:98%;}
      .l_content{ margin:0px auto; padding-top:20px; width:100%; height:310px; background:url(${ctxStatic}/images/pic_login_bg.png) no-repeat center top;}
      .l_content_b{ margin:0px auto 0 auto; width:1000px; }
      .l_login{ float:right; width:320px; height:250px; background:#FFF;}
      .l_login .title{ margin:0px auto; padding:20px 0 15px; width:75%; color:#f96f2e; font-size:14px;}
      .l_login ul{ margin:0px auto; width:75%;}
      .l_login ul li{ padding:5px 0; height:30px; line-height:30px;}
      .l_login ul li.input-label{ float: left;}
      .l_login input#username,.l_login input#password{ width:180px;}
      .login_btn{ margin: 0px auto; width: 75%;}
      #footer{ text-align: center;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	
	<div class="l_top"><img src="${ctxStatic}/images/logo_js.png" width="400" /></div>
	<!---主体 start--->
	<div class="l_content">
		
		<div class="l_content_b">
			<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
			<div class="l_login">
				<p class="title">后台登录窗口</p>
				<ul>
					<li>
						<label class="input-label" for="username">登录名</label>
						<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
					</li>
					<li>
						<label class="input-label" for="password">密&nbsp;&nbsp;&nbsp;码</label>
						<input type="password" id="password" name="password" class="input-block-level required">
					</li>
					<c:if test="${isValidateCodeLogin}">
						<li class="validateCode">
							<label class="input-label mid" for="validateCode">验证码</label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
						</li>
					</c:if>
					<li>
						<label for="rememberMe" title="下次不需要再登录">
							<input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）
						</label>
					</li>
				</ul>
				<div class="login_btn"><input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;</div>
			</div>
			</form>
		</div>
	</div>
	<!---主体 end--->
	<!---底部 start--->
	<div id="footer">
		 Powered By    ${fns:getConfig('productName')}
	<!---底部 end--->
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>