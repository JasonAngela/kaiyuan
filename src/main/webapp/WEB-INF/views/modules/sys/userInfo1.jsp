<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="${ctxStatic}/user_css/index.css" rel="stylesheet">
</head>

<script type="text/javascript">
        function hideImg(no) {
            document.getElementById('dnaRecive'+no).style.display = "none";
        }

        function showImg(no) {
            document.getElementById('dnaRecive'+no).style.display = "block";
        }

</script>

<body>

<div class="t-mian1C">
    <!-- 学院SPU -->
    <div class="t-mianCon clearfloat" id="spu-list">
        <div class="t-mainsku" onmouseover="hideImg(1)" onmouseout="showImg(1)">
            <div id="dnaRecive1">
                <img src="${ctxStatic}/images/20180308051010497teacher_icon.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    DNA委托受理
                </p>
            </div>
            <div class="skuShow ">
                <p class="t-miantit" style="color: rgb(53, 212, 143);">
                    DNA委托受理
                </p>
                <div class="t-gang">
                </div>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=1"class="sku-mr">个人鉴定</a>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=2" class="sku-mr">司法鉴定</a>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=3" class="sku-mr">亲缘鉴定</a>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=4" class="sku-mr">个人识别</a>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=5" class="sku-mr">同一认定</a>
                <a href="${ctx}/entrust/entrustRegister/form1?accept=6" class="sku-mr">其他</a>
            </div>
        </div>
        <div class="t-mainsku" onmouseover="hideImg(2)" onmouseout="showImg(2)">
            <div id="dnaRecive2">
                <img src="${ctxStatic}/images/20180308051106603english_icon.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    法医委托受理
                </p>
            </div>
            <div class="skuShow ">
                <p class="t-miantit" style="color: rgb(53, 212, 143);">
                    法医委托受理
                </p>
                <div class="t-gang">
                </div>
                <a href="${ctx}/clinic/clinicRegister/form1?accpet=1" class="sku-mr">残疾鉴定</a>
                <a href="${ctx}/clinic/clinicRegister/form1?accpet=2" class="sku-mr">损伤鉴定</a>
                <a href="${ctx}/clinic/clinicRegister/form1?accpet=3" class="sku-mr">病理FS</a>
                <a href="${ctx}/clinic/clinicRegister/form1?accpet=4" class="sku-mr">病理FA</a>
            </div>
        </div>
        <div class="t-mainsku" onmouseover="hideImg(3)" onmouseout="showImg(3)">
            <div id="dnaRecive3">
                <img src="${ctxStatic}/images/201803080510289gwy.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    酒精委托受理
                </p>
            </div>
            <div class="skuShow skuLie">
                <p class="t-miantit" style="color: rgb(53, 212, 143);">
                    酒精委托受理
                </p>
                <div class="t-gang">
                </div>
                <a href="${ctx}/clcohol/clcoholRegister/form1" class="sku-mr">酒精委托受理</a>

            </div>
        </div>
        <div class="t-mainsku" onmouseover="hideImg(4)" onmouseout="showImg(4)">
            <div id="dnaRecive4">
                <img src="${ctxStatic}/images/20180308051115328xz_icon.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    DNA实验室
                </p>
            </div>
            <div class="skuShow ">
             
                <div class="t-gang">
                </div>
                 <a href="${ctx}/dna/dnaReceive/form" class="sku-mr">领取样品(个，司)</a>
                 <a href="${ctx}/dna/dnaReceive/form2" class="sku-mr">领取样品(亲缘)</a>
                 <a href="${ctx}/dna/dnaReceive/form3" class="sku-mr">领取样品(个体)</a>
                 <a href="${ctx}/dna/dnaReceive/form4" class="sku-mr">领取样品(同一)</a>
                <a href="${ctx}/dna/dnaExtractRecord/form" class="sku-mr">提取室</a>
                <a href="${ctx}/dna/dnaPreparationReagents/form" class="sku-mr">扩增室</a>
                <a href="${ctx}/dna/dnaExperiment/form" class="sku-mr">电泳室</a>
            </div>
        </div>
        <div class="t-mainsku" onmouseover="hideImg(5)" onmouseout="showImg(5)">
            <div id="dnaRecive5">
                <img src="${ctxStatic}/images/20180308051041218jinrong_icon.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    待办事宜
                </p>
            </div>
            <div class="skuShow ">
                <p class="t-miantit" style="color: rgb(53, 212, 143);">
                    待办事宜
                </p>
                <div class="t-gang">
                </div>
                <shiro:hasPermission name="entrust:entrustRegister:view">
                            <a href="${ctx}/act/task/todo/" class="sku-mr">DNA鉴定<font style="color: red;">(${sifaTodo})</font></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="entrust:entrustExpertOpinion:view">
                            <a href="${ctx}/act/task/todo/" class="sku-mr">DNA实验<font style="color: red;">(${dnaTodo})</font></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="clinic:clinicRegister:view">
                            <a href="${ctx}/act/task/todo/" class="sku-mr">法医临床<font style="color: red;">(${fayiTodo})</font></a>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="t-mainsku" onmouseover="hideImg(6)" onmouseout="showImg(6)">
            <div id="dnaRecive6">
                <img src="${ctxStatic}/images/20180308051046601jianzhu_icon.png" class="mt30">
                <p class="t-text" style="color: rgb(53, 212, 143);">
                    已办事宜
                </p>
            </div>
            <div class="skuShow skuLie">
                <p class="t-miantit" style="color: rgb(53, 212, 143);">
                    已办事宜
                </p>
                <div class="t-gang">
                </div>
                <shiro:hasPermission name="entrust:entrustRegister:view">
                            <a href="${ctx}/act/task/historic/" class="sku-mr">DNA鉴定</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="entrust:entrustExpertOpinion:view">
                            <a href="${ctx}/act/task/historic/" class="sku-mr">DNA实验</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="clinic:clinicRegister:view">
                            <a href="${ctx}/act/task/historic/" class="sku-mr">法医临床</a>
                </shiro:hasPermission>

            </div>
        </div>
    </div>
</div>
</body>
</html>