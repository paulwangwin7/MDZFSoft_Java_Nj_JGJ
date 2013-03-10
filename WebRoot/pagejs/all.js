$(document).ready(function(){
	
	$(".mod_hd").click(function(){
			$(this).toggleClass("bor").siblings(".inner_bor").toggle();
		});
	//table graybg
	$(".Js_grayBg  tr").each(function(){
			var _this=$(this)
			_this.hover(function(){
					_this.find("td").toggleClass("hoverbg")
				})
		});
	//tab
	$(".Js_tab > li").each(function(index){
			var _this=$(this)
			_this.click(function(){
					_this.addClass("current").siblings("li").removeClass("current")
					$(".tab_conitem").eq(index).addClass("current").siblings("li").removeClass("current")
				})
		})
	//police list
	$(".Js_ShowPolice > li > .police_hd").each(function(){
			var _this=$(this)
			var _thisSib=$(this).siblings("ul")
			_this.click(function(){
					_thisSib.toggle();
				})
		})
		//data picker
		//var locale = "<s:property value='#request.locale'/>";//struts2取语言环境
		//var locale = "<%=request.getLocale()%>"; //jsp取浏览器语言环境
		//datePicker('#dateDemo',locale);//根据语言环境切换日期控件语言
		if($("#begainDate").length>0)
		{
			datePicker('#begainDate','zh_CN');
		}
		if($("#endDate").length>0 )
		{
		datePicker('#endDate','zh_CN');
		}
		if($("#UploadBeginDate").length>0)
		{
		datePicker('#UploadBeginDate','zh_CN')
		}
		if($("#UploadendDate").length>0 )
		{
		
		datePicker('#UploadendDate','zh_CN')
		}
		//datePicker('#dateDemo',''); //''默认的样式在ui.datepicker.js内已定义为英文样式，与附件内的ui.datepicker-en_US.js相同
		$(".dialog-mask").css("opacity","0.6")
	})
	
function showLocale(objD)
{
	var str,colorhead,colorfoot;
	var yy = objD.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objD.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objD.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = objD.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = objD.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = objD.getSeconds();
	if(ss<10) ss = '0' + ss;
	var ww = objD.getDay();
	if  ( ww==0 )  colorhead="";
	if  ( ww > 0 && ww < 6 )  colorhead="";
	if  ( ww==6 )  colorhead="";
	if  (ww==0)  ww="星期日";
	if  (ww==1)  ww="星期一";
	if  (ww==2)  ww="星期二";
	if  (ww==3)  ww="星期三";
	if  (ww==4)  ww="星期四";
	if  (ww==5)  ww="星期五";
	if  (ww==6)  ww="星期六";
	colorfoot="</font>"
	str = colorhead + yy + "年" + MM + "月" + dd + "日" + hh + ":" + mm + ":" + ss + "  " ;
	return(str);
}
function tick()
{
	var today;
	today = new Date();
	document.getElementById("localtime").innerHTML = showLocale(today);
	window.setTimeout("tick()", 1000);
}
tick();


	function datePicker(pickerName,locale) {
	$(pickerName).datepicker($.datepicker.regional[locale]);
	$(pickerName).datepicker('option', 'changeMonth', true);//月份可调
	$(pickerName).datepicker('option', 'changeYear', true);//年份可调
	}
