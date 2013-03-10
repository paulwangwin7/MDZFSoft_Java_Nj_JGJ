var defaultEditId = "";
var defaultEditName = "";
function setDefaultVal(editId,editName)
{
	defaultEditId = editId;
	defaultEditName = editName;
}

var setupUrl = contextPath()+"/downloads/md_1.0.0.5.CAB";//安装插件的链接地址
var nowVersion = "1.0.0.3";//当前使用的版本号
var static_selectLetter = "";//当前上传的盘符
var static_sum = 0;//总共的上传文件数量
var static_uploaded = 0;//已经上传的文件数量
var static_fileUrl = "";//上传文件的原始文件全路径
var saveAs = "";//保存的文件名
var saveDir = "";//保存的路径
var static_localFilePath;//本地文件上传保持的路径
//var ftpHost = "127.0.0.1";
//var ftpPort = "21";
//var ftpUser = "ftpdemo";
//var ftpPswd = "ftpdemo"
var static_ftpLogin = false;//ftp登录状态
var static_imgShow = true;//图片显示

var fileTotalNums = 0;

function $BlockNone(objName, displayValue)
{
document.getElementById(objName).style.display = displayValue;
}

function $ALT(alertMsg)
{
//alert(alertMsg);
showMsg(alertMsg);
}
function $DEBUG(alertMsg)
{
alert(alertMsg);
}

/**
 * 获取当前盘符 如果没有安装过插件，跳转至安装插件的界面
 */
function getVersion(){
  var str = "";
  try{
    str  =  MDOCX.getVersion();
    //alert('您当前使用的插件版本是：'+str);
  }
  catch(ex){
    //alert(ex)
    $ALT("您还没有安装插件，请安装上传插件~");
    window.open(setupUrl);
    return str;
  }
  return str;
}

/**
 * ftp连接设置
 * @param ftpHost ftpIp地址
 * @param ftpUser ftp连接的用户名
 * @param ftpPswd ftp用户名对应的密码
 * @param ftpPort ftp连接的端口
 */
function setParm(ftpHost, ftpUser, ftpPswd, ftpPort){
  try{
    var str =  MDOCX.setParm(ftpHost, ftpUser, ftpPswd, ftpPort);
  }
  catch(ex){
    $ALT("ftp连接设置异常： "+ex.description);
  }
}

/**
 * ftp登录
 */
function ftpLogin(){
  try{
    var str =  MDOCX.ftpLogin();
    if(str!="1")
    {
      $ALT("ftp服务器登录失败，请与管理员联系~ 失败原因："+MDOCX.ftpGetErrorMsg());
    }
    else
    {
      static_ftpLogin = true;
    }
  }
  catch(ex){
    //$ALT("ftp服务器登录异常 "+ex.description);
    $ALT("ftp服务器登录失败，请与管理员联系~ 失败原因："+ex.description);
  }
}

/**
 * ftp登出
 */
function ftpLogout(){
  try{
    var str =  MDOCX.ftpLogout();
    if(str!="1")
    {
      $ALT("ftp服务器退出失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("ftp服务器退出异常 "+ex.description);
  }
}

/**
 * ftp文件缓存设置
 * @param valueMB 缓存大小 1即1MB缓存
 */
function ftpSetFileStepSize(valueMB){
  try{
    var str =  MDOCX.ftpSetFileStepSize(1024*valueMB);
    if(str!="1")
    {
      $ALT("ftp文件缓存设置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("ftp文件缓存设置异常 "+ex.description);
  }
} 

/**
 * 文件夹创建 创建完成后即进入到创建的目录级
 * @param remoteDir 文件夹目录名
 */
function ftpCreateRemoteDir(remoteDir){
  try{
    ftpSetRemoteRoot();
    saveDir = remoteDir;
    var dirArr = remoteDir.split(",");
    for(i=0; i<dirArr.length; i++)
    {
      if(MDOCX.ftpCreateRemoteDir(dirArr[i])=="0"){
        ftpSetRemoteDir(dirArr[i]);
      }
    }
    //var str =  MDOCX.ftpCreateRemoteDir(remoteDir);
    
    /*if(str!="1")
    {
      $ALT("文件夹创建失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }*/
  }
  catch(ex){
    $ALT("文件夹创建异常 "+ex.description);
  }
}

/**
 * 文件夹删除 包括文件夹下的所有文件 ***慎用***
 * @param remoteDir 文件夹目录名
 */
function ftpDelRemoteDir(){
  try{
    var str =  MDOCX.ftpDelRemoteDir(static_fileUrl);
  }
  catch(ex){
    $ALT("info "+ex.description);
  }
}

/**
 * 设置进入文件夹的位置 如果想进入"\test\test\",那么传入的参数为"test\\test"
 * @param remoteDir 文件夹目录名
 */
function ftpSetRemoteDir(remoteDir){
  try{
    var str =  MDOCX.ftpSetRemoteDir(remoteDir);
    if(str!="1")
    {
      $ALT("设置进入文件夹的位置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("设置进入文件夹的位置异常 "+ex.description);
  }
}

/**
 * 设置进入ftp的根目录
 * @param remoteDir 文件夹目录名
 */
function ftpSetRemoteRoot(){
  try{
    var str =  MDOCX.ftpSetRemoteRoot();
    if(str!="1" && static_ftpLogin)
    {
      $ALT("设置进入ftp的根目录失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("设置进入ftp的根目录异常 "+ex.description);
  }
} 

/**
 * 获取当前进入文件夹的位置 ***基本可以不用这个方法，此方法调试模式下用***
 */
function ftpGetRemoteDir(){
  try{
    var str =  MDOCX.ftpGetRemoteDir();
    if(str!="1")
    {
      $ALT("获取当前进入文件夹的位置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("info "+ex.description);
  }
}

/**
 * ftp开始上传文件
 * @param localFile 需要上传的文件全路径 例如"c:\\2223.mp3"
 * @param ftpFile 上传在ftp的文件名称（前提，设置进入文件夹的位置）  例如"222.sys"
 * @return 1-成功，开始上传； 2-失败
 */
function ftpUploadFile(localFile, ftpFile){
  //alert("localFile=="+localFile+"  ftpFile=="+ftpFile);
  var str = "";
  try{
    str =  MDOCX.ftpUploadFile(localFile, ftpFile);
    if(str!="1")
    {
      $ALT("ftp开始上传文件失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    $ALT("ftp开始上传文件异常 "+ex.description);
    return str;
  }
  return str;
} 

/**
 * 获取ftp文件上传进度
 */
function ftpGetUploadFilePercent(){
  var str = 0;
  try{
    str =  MDOCX.ftpGetUploadFilePercent();
    //alert(str)
  }
  catch(ex){
    $ALT("获取ftp文件上传进度异常 "+ex.description);
    return str;
  }
  return str;
} 

/**
 * 删除本地文件 ***慎用***
 * @param localFile 需要删除本地的文件全路径 例如"c:\\2223.mp3"
 */
function DelLocalFile(){
  try{
    var str =  MDOCX.DelLocalFile(static_fileUrl);
    if(str!="1")
    {
      $ALT("删除本地文件失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
    else
    {
      static_uploaded++;
      //alert(static_uploaded+"/"+static_sum);
      if(getLocalFirstFile(static_selectLetter)!="")
      {
      	setTimeout(uploadFile(),1000);
      }
      else
      {
        alert("上传成功");
      }
    }
  }
  catch(ex){
    //$ALT("删除本地文件异常 "+ex.description);
  }
}

/**
 * 删除本地文件 ***慎用***
 * @param localFile 需要删除本地的文件全路径 例如"c:\\2223.mp3"
 */
function DelLocalFile2(){
  try{
    var str =  MDOCX.DelLocalFile(static_fileUrl);
    if(str!="1")
    {
      $ALT("删除本地文件失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
    else
    {
      return true;
    }
  }
  catch(ex){
    //$ALT("删除本地文件异常 "+ex.description);
  }
  return false;
}

/**
 * 取得本地文件夹下的所有文件（含文件夹下的文件） 迭代查询
 * @param remoteDir 例如"D:\\wavecut\\"
 */
function getLocalDirFiles(remoteDir){
  var str = "";
  try{
    str =  MDOCX.getLocalDirFiles(remoteDir,1);
  }
  catch(ex){
    $ALT("取得本地文件夹下的所有文件（含文件夹下的文件）异常 "+ex.description);
  }
  return str;
}

/**
 * 获取ftp文件上传速度
 * @param remoteDir 例如"D:\\wavecut\\"
 */
function ftpGetUploadSpeed(){
  var str = 0;
  try{
    str =  MDOCX.ftpGetUploadSpeed();
    //alert('文件上传速度:'+str);
  }
  catch(ex){
    $ALT("获取ftp文件上传速度异常 "+ex.description);
    return str;
  }
  return str;
}

/**
 * 获取USB外接设备连接的盘符 并显示在选择列表中
 */
function getUsbDriver(){
  try{
    var str =  MDOCX.getUsbDriver();
    if(str!="")
    {
      var select_letter = str.split("*");
      for(i=0; i<select_letter.length; i++)
      {
        document.getElementById("selectLetter").innerHTML += "<input type=radio name=r_letter value="+select_letter[i]+" onclick=radioSelectLetter('"+select_letter[i]+"')>("+select_letter[i]+":)盘<br/>";
      	//showMsg("请选择需要上传文件的盘符");
      }
    }
    else
    {
      //$ALT("您还没有插入USB设备，请确认~");
      showMsg("您还没有插入USB设备，请确认~");
      //document.getElementById("uploadTable").style.display = "none";
    }
  }
  catch(ex){
    $ALT("info "+ex.description);
  }
}

/**
 * 选择本地文件
 */
function selectLocalSaveDir()
{
    try{
		var str =  MDOCX.selectLocalSaveDir();
		if(str!="")
		{
			setLocalSaveDir(str);
jQuery(function($) {
			$("#localSaveDir").val(str);
			$("#localSaveButton").css("display", "block");
});
		}
	}
    catch(ex){
    	$ALT("选择本地文件异常 "+ex.description);
	}
}

/**
 * 选择本地文件（将本地上传至FTP）
 */
function selectLocalSaveDir2()
{
    try{
		var str =  MDOCX.selectLocalSaveDir();
		if(str!="")
		{
			setLocalSaveUploadDir(str);
			jQuery(function($) {
				$("#localUploadDir").val(str);
			    $("#localUploadDiv").css("display", "block");
			});
			var firstFileUrl = getLocalFirstFile(static_selectLetter);
			var firstFileName = firstFileUrl.substring(firstFileUrl.lastIndexOf("\\")+1);
			$('#uploadNameValue1').val(firstFileName);
			if(firstFileName.length==34)
			{
				var codeIndex = firstFileName.indexOf("_")+1;
				var jingyuanCode = firstFileName.substring(codeIndex, codeIndex+6);

				$.ajax({
					url:contextPath()+'servletAction.do?method=userFormByCode',
					type: 'post',
					dataType: 'json',
					cache: false,
					async: false,
					data: {"userCode":jingyuanCode},
					success:function(res){
						if(res != null)
						{
							try{
								setDefaultVal(res.retObj.userId, res.retObj.userName);
								$('#upload_editId').val(res.retObj.userId);
								$('#editName').val(res.retObj.userName);
							}catch(e){}
						}
					},
					error:function(){
						showMsg("userAddMsg", "请求失败 error function", 1);
					}
				});
			}
		}
	}
    catch(ex){
    	$ALT("选择本地文件异常 "+ex.description);
	}
}

/**
 * 设置本地文件路径（将本地上传至FTP）
 */
function setLocalSaveUploadDir(saveDir){
    try{
		var str =  MDOCX.setLocalSaveDir(saveDir);
		if(str=="1")
		{
    		static_selectLetter = saveDir;
		}
	}
    catch(ex){
		alert("info "+ex.description);
	}
}

/**
 * 设置本地文件路径
 */
function setLocalSaveDir(saveDir){
    try{
		var str =  MDOCX.setLocalSaveDir(saveDir);
		if(str=="1")
		{
			static_localFilePath = saveDir;
		}
	}
    catch(ex){
		alert("info "+ex.description);
	}
}

function copyLocalFile(){
    try{
    	var fileF = getLocalFirstFile(static_selectLetter);
    	static_fileUrl = fileF;
    	if(fileF=="")
    	{
    	  alert("上传成功");
    	}
    	else
    	{
	    	//alert(fileF);
	    	//alert(static_localFilePath+fileF.substring(fileF.lastIndexOf("\\"),fileF.length));
	    	//alert(fileF);
	    	//alert(static_localFilePath+fileF.substring(fileF.lastIndexOf("\\"),fileF.length));
	        var str =  MDOCX.copyLocalFile(fileF,static_localFilePath+fileF.substring(fileF.lastIndexOf("\\"),fileF.length));
			if(str==1)
			{
				if(DelLocalFile2())//删除这个上传完成的文件
				{
					copyLocalFile();//开始传递下一个文件
				}
				else
				{
					alert("删除"+fileF+"时异常~");
				}
			}
		}
     }
    catch(ex){
          alert("info "+ex.description);
     }
}

/**
 * 获得选择需要进行上传盘符下的第一个文件
 */
function getLocalFirstFile(letter){
  var str = "";
  try{
  	if(letter.indexOf(':')>0)
  	{
    str =  MDOCX.getLocalFirstFile(letter,1);
  	}
  	else
  	{
    str =  MDOCX.getLocalFirstFile(letter+":\\",1);
    }
  }
  catch(ex){
    $ALT("获得选择需要进行上传盘符下的第一个文件异常 "+ex.description);
    return str;
  }
  return str;
}

/**
 * 获取上传文件的文件创建时间
 * @param localFile 例如"c:\\2223.mp3"
 */
function getLocalFileCreateTime(){
  var str = "";
  try{
    str =  MDOCX.getLocalFileCreateTime(static_fileUrl);
  }
  catch(ex){
    $ALT("获取上传文件的文件创建时间异常 "+ex.description);
    return str;
  }
  return str;
}

/**
 * 获取选择需要进行上传盘符下的文件个数
 * @param letter 盘符 例如"C"
 */
function getLocalDirFilesNum(letter){
  var str = 0;
  try{
    //str =  MDOCX.getLocalDirFilesNum(letter+":\\",1);
    //var str =  MDOCX.getLocalDirFilesNum(letter+"\\",1);
    if(letter.indexOf(":")>0)
    {
      //alert(letter+"\\");
   	  str = MDOCX.getLocalDirFilesNum(letter+"\\",1);
    }
    else
    {
      //alert(letter+":\\");
      str = MDOCX.getLocalDirFilesNum(letter+":\\",1);
    }
   // alert(fileTotalNums);
  }
  catch(ex){
    $ALT("获取选择需要进行上传盘符下的文件个数异常 "+ex.description);
    return str;
  }
  return str;
}

var yewu = true;
/**
 * 判断该用户使用的版本是否正确
 */
if(nowVersion.split(".")[3]>getVersion().split(".")[3])
{
$ALT("您使用的版本不能正常使用~");
yewu = false;
}
/**
 * 设置ftp参数 并登录
 */
if(yewu)
{
setParm(ftpHost,ftpUser,ftpPswd,ftpPort);
ftpLogin();
ftpSetRemoteRoot();
}

/**
 * ftp缓存设置
 */
if(yewu)
{
ftpSetFileStepSize(10);
}

/**
 * 我要上传
 */
function uploadTable(remoteDir)
{
  if(yewu)
  {
	ftpCreateRemoteDir(remoteDir);
  }
}

/**
 * 我要上传（本地文件上传至FTP）
 */
function uploadTable2(remoteDir)
{
  if(yewu)
  {
	ftpCreateRemoteDir(remoteDir);
  }
}

/**
 * 选择盘符
 */
function radioSelectLetter(letterStr)
{
  if(getLocalFirstFile(letterStr) != "")
  {
    static_selectLetter = letterStr;
jQuery(function($) {
    $("#localSaveDiv").css("display", "block");
    
    var firstFileUrl = getLocalFirstFile(static_selectLetter);
    //alert(firstFileUrl);
			var firstFileName = firstFileUrl.substring(firstFileUrl.lastIndexOf("\\")+1);
			$('#uploadNameValue1').val(firstFileName);
			if(firstFileName.length==34)
			{
				var codeIndex = firstFileName.indexOf("_")+1;
				var jingyuanCode = firstFileName.substring(codeIndex, codeIndex+6);

				$.ajax({
					url:contextPath()+'servletAction.do?method=userFormByCode',
					type: 'post',
					dataType: 'json',
					cache: false,
					async: false,
					data: {"userCode":jingyuanCode},
					success:function(res){
						if(res != null)
						{
							try{
								setDefaultVal(res.retObj.userId, res.retObj.userName);
								$('#upload_editId').val(res.retObj.userId);
								$('#editName').val(res.retObj.userName);
							}catch(e){}
						}
					},
					error:function(){
						showMsg("userAddMsg", "请求失败 error function", 1);
					}
				});
			}
    
});
  }
  else
  {
    $ALT("您选择的盘符下没有可以上传的文件，请您确认~");
  }
}

/**
 * 取消选择盘符
 */
function cancelRadioSelectLetter()
{
  $BlockNone("step3_div", "none");
  uploadTable();
}

/**
 * 取消 重新选择上传
 */
function cancelUpload()
{
  $BlockNone("step5_div", "none");
  $BlockNone("step4_div", "none");
  $BlockNone("step3_div", "none");
  $BlockNone("step2_div", "none");
  $BlockNone("step1_div", "block");
}

/**
 * 开始上传第一个文件
 */
function startUpload()
{
jQuery(function($) {
if($('#editName').val()=='')
{
	alert('请选择采集人');
	return;
}
$.ajax({
		url:contextPath()+'servletAction.do?method=letterFreeSpace',
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {},
		success:function(res){
			if(res != null)
			{
				if(res.retCode=='0')
				{
					//alert('可以上传');
					static_sum = getLocalDirFilesNum(static_selectLetter);
					uploadFile();
				}
				else
				{
					$ALT(res.msg);
				}
			}
		},
		error:function(){
			showMsg("userAddMsg", "请求失败 error function", 1);
		}
	});
});
}

/**
 * 上传文件
 */
function uploadFile()
{
  static_fileUrl = getLocalFirstFile(static_selectLetter);
  if(static_fileUrl!="")
  {
  	var firstFileUrl = static_fileUrl;
	var firstFileName = firstFileUrl.substring(firstFileUrl.lastIndexOf("\\")+1);
	$('#uploadNameValue1').val(firstFileName);
	if(firstFileName.length==34){
		var codeIndex = firstFileName.indexOf("_")+1;
		var jingyuanCode = firstFileName.substring(codeIndex, codeIndex+6);

		$.ajax({
			url:contextPath()+'servletAction.do?method=userFormByCode',
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"userCode":jingyuanCode},
			success:function(res){
				if(res != null)
				{
					$('#upload_editId').val(res.retObj.userId);
					$('#editName').val(res.retObj.userName);
				}
			},
			error:function(){
				showMsg("userAddMsg", "请求失败 error function", 1);
			}
		});
	} else {
		$('#upload_editId').val(defaultEditId);
		$('#editName').val(defaultEditName);
	}
  	
  	var extension = "";//扩展名
  	if(static_fileUrl.toUpperCase().lastIndexOf("AVI")>0) {
  		extension = "avi";
  	} else if(static_fileUrl.toUpperCase().lastIndexOf("MP4")>0) {
  		extension = "mp4";
  	} else if(static_fileUrl.toUpperCase().lastIndexOf("WAV")>0) {
  		extension = "wav";
  	} else {
  		extension = "jpg";
  	}
    saveAs = getFileName()+'.'+extension;
	ftpUploadFile(static_fileUrl, saveAs);
	fileTotalNums = static_sum;
	//alert(contextPath()+"/sc.jsp?static_sum="+fileTotalNums+"&static_uploaded="+(static_uploaded+1)+"&speed="+ftpGetUploadSpeed()+"&timer="+Math.random())
	$('#sc').attr('src', contextPath()+"/sc.jsp?static_sum="+fileTotalNums+"&static_uploaded="+(static_uploaded+1)+"&speed=0&timer="+Math.random());
	//document.getElementById("sc").src = contextPath()+"/sc.jsp?static_sum="+fileTotalNums+"&static_uploaded="+(static_uploaded+1)+"&speed="+ftpGetUploadSpeed()+"&timer="+Math.random();
  }
  else
  {
    static_sum = 0;
  }
}

function getFileName()
{
  var myDate = new Date();
  var yyyy = myDate.getFullYear(); //获取完整的年份(4位,1970-????)
  var MM = myDate.getMonth(); //获取当前月份(0-11,0代表1月)
  var dd = myDate.getDate(); //获取当前日(1-31)
  myDate.getDay(); //获取当前星期X(0-6,0代表星期天)
  myDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数)
  var h24 = myDate.getHours(); //获取当前小时数(0-23)
  var mm = myDate.getMinutes(); //获取当前分钟数(0-59)
  var ss = myDate.getSeconds(); //获取当前秒数(0-59)
  var ms = myDate.getMilliseconds(); //获取当前毫秒数(0-999)
  return yyyy+""+(MM<10?("0"+MM):MM)+""+(dd<10?("0"+dd):dd)+"_"+(h24<10?("0"+h24):h24)+""+(mm<10?("0"+mm):mm)+""+(ss<10?("0"+ss):ss)+"_"+myDate.getMilliseconds();
}

function uploadSuccess()
{
  document.getElementById("upload_playCreatetime").value = getLocalFileCreateTime();
  document.getElementById("upload_playPath").value = saveDir +','+ saveAs;
  if(document.getElementById("uploadNameValue1").value!="")
  {
  	document.getElementById("upload_uploadName").value = document.getElementById("uploadNameValue1").value;
  }
  else
  {
    document.getElementById("upload_uploadName").value = document.getElementById("uploadNameValue2").value;
  }
  document.getElementById("uploadForm").submit();
}

/**
 * 获取信息div
 * @param objId 显示操作的id
 * @param msg 提示信息
 * @param type 提示类型 0-success 1-error 2-warning 3-information
 * @param focusId 焦点停留id名称
 */
function showMsg(msg)
{
jQuery(function($) {
	$("#fileManagerAddMsg").html(msg);
	$("#fileManagerAddMsg").attr("class", "warning msg");
	$("#fileManagerAddMsg").css("opacity" ,4);
	showObj("fileManagerAddMsg");
});
}

/**
 * 显示信息
 * @param objId 显示操作的id
 * @param msg 提示信息
 * @param type 提示类型 0-success 1-error 2-warning 3-information
 * @param focusId 焦点停留id名称
 */
function showInformation(msg)
{
jQuery(function($) {
	$("#fileManagerAddInformation").html(msg);
	$("#fileManagerAddInformation").attr("class", "information msg");
	$("#fileManagerAddInformation").css("opacity" ,4);
	showObj("fileManagerAddInformation");
});
}