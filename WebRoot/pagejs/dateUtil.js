/**
 * 将char8的日期数据转换成现平台需要的格式
 * @param dateStr char8格式的日期 yyyymmdd
 */
function formatJsChar8(dateStr)
{
	return dateStr.substring(4,6)+"/"+dateStr.substring(6,8)+"/"+dateStr.substring(0,4);
}

/**
 * 将char8的日期数据提取向前5年及本年数据
 * @param dateStr char8格式的日期 yyyymmdd 注：返回格式是yyyy
 */
function getYearArrChar8(dateStr)
{
	var yearArr = new Array();
	var arrIndex = 0;
	for(i=5; i>=0; i--)
	{
		yearArr[arrIndex] = (parseInt(dateStr.substring(0,4))-(i));
		arrIndex++;
	}
	return yearArr;
}

/**
 * 获取年月日星期几
 * @param dateStr char8格式的日期 yyyymmdd
 */
function getDayStr(dateStr)
{
	var year = parseInt(dateStr.substring(0,4));
	var month = parseInt(dateStr.substring(4,6))-1;
	var date = parseInt(dateStr.substring(6,7));
	var dt = new Date(year, month, date);
	var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	return weekDay[dt.getDay()];
}