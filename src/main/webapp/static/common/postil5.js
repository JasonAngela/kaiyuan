/**
 * @作者：WilliamSha
 * @时间：2011-10-18 下午07:52:01
 * @项目名：XXX
 * @描述：操作批注工具
 */
//初始化页面元素
$(function(){
	$(".content4").mouseup(function(e){
		var selectedText ;
		if(window.getSelection) {
			selectedText = window.getSelection().toString();
		}
		else if(document.selection && document.selection.createRange) {
			selectedText = document.selection.createRange().text;
		}
		if(selectedText){
			$("#icon4").css({
				"left" : e.clientX+1,
				"top" : e.clientY-30
			
			}).fadeIn(300);
		}
		else {
			$("#icon4").hide();
		}
	});
	$("#icon4").hover(function(){
		$(this).children().removeClass("tipsicon");
	}
	,
	function(){
		$(this).children().addClass("tipsicon");
	}).click(function() {
		$("#icon4").hide();
		addpostil4();
	});
	loader4();
});
//添加批注
function addpostil4() {
	//IE支持的range对象
	 var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds();
	var ie_range ;
	//其他浏览器的range对象
	var other_range ;
	//使用人
	if(window.getSelection) {
		other_range = window.getSelection().getRangeAt(0);
	}
	else if(document.selection && document.selection.createRange) {
		ie_range = document.selection.createRange();
	}
	var user=$("#userId").val();
 
	art.dialog({
		id:'inputDialog', 
		title:'添加批注', 
		content:'<textarea id="postil4" rows="10" cols="30">\r\n\r\n\r\n\r\n\r\n\r\n\r\n \r\n'+user+'\r\n'+currentdate+'('+other_range+')'+'</textarea>',
		lock:true
	
	}
	, function(){
		var value = document.getElementById("postil4").value;
		if(!value){
			art.dialog({
				content:'批注内容不能为空！', time: 1
			});
			return false;
		}
		if(other_range) {
			/*
			//IE之外的浏览器，如果在选择内容包含其他标签的一部分的时候会报异常
			var mark = document.createElement("ins");
			mark.setAttribute("comment", value);
			mark.className = "postil";
			mark.id=new Date().getTime();
			other_range.surroundContents(mark);
			*/
			var selected = other_range.extractContents().textContent;
			var text = "[ins id='"+(new Date().getTime())+"' comment='"+value+"']"+selected+"[/ins]";
			var textNode = document.createTextNode(text);
			other_range.insertNode(textNode);
			var content = $(".content4").html();
			var reg = /\[ins id='(\d*)' comment='([\w\W]*)']([\w\W]*)\[\/ins]/gi;
			reg.test(content);
			var id = RegExp.$1,
			comment = RegExp.$2,
			c = RegExp.$3;
			var reHtml = "<ins id='"+id+"' comment='"+comment+"' class='postil' >"+c+"</ins>";
			content = content.replace(reg, reHtml);
			$(".content4").html(content);
		}
		else if(ie_range) {
			ie_range.pasteHTML("<ins comment='"+value+"' class='postil' id='"+new Date().getTime()+"'>"+ie_range.htmlText+"</ins>");
			ie_range=null;
		}
		loader4();
	});
}
//解析批注
function loader4(){
	var $list = $(".list4");
	$list.children().remove();
	$.each($(".content4 ins"), function(a, b){
		var content = $(b).attr("comment");
		var $postil = $("<div forid4='"+$(b).get(0).id+"'>"+content+"<span onClick='removepostil4(this)'>　　x</span></div>");
		$postil.hover(function(){
			$(this).css("border-color", "red");
			$("#"+$(this).attr("forid4")+"").removeClass().addClass("postilFocus");
		}
		,
		function(){
			$(this).css("border-color", "#ddd");
			$("#"+$(this).attr("forid4")+"").removeClass().addClass("postil");
		});
		$(b).hover(function(){
			$(this).removeClass().addClass("postilFocus");
			$("div[forid4='"+$(this).get(0).id+"']").css("background-color", "#1E90FF");
		}
		,
		function(){
			$(this).removeClass().addClass("postil");
			$("div[forid4='"+$(this).get(0).id+"']").css("background-color", "#C3DAF9");
		});
		$list.append($postil);
	});
	
	var content_value = $("#content_value4");
	content_value.val(($(".content4").html().trim()));
}
//删除批注
function removepostil4(arg){
	var $div = $(arg).parent();
	var id = $div.attr("forid4");
	var $source = $("#"+id);
	var text = $source.after($source.html());
	$source.remove();
	loader4();
}