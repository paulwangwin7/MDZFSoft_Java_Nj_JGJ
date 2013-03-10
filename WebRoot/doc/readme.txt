1. 数据库配置修改
	WebRoot/WEB-INF/applicationContext.xml
	WebRoot/WEB-INF/classes/hibernate.cfg.xml

2. 系统配置
	WebRoot/WEB-INF/ApplicationResources.properties
		字段说明如下
		readFilePath		文件读取盘符
		saveFilePath		文件保存盘符
		ffmpegBegin		转换起始时间
		ffmpegEnd		转换结束时间
		ffmpegSleep		转换休眠时长
		sysLoginName	系统管理员帐号
		sysLoginPswd	系统管理员密码
		loginIPFilter		是否做系统管理员登录IP鉴权
		loginIPList			系统管理员登录IP
		fileRoot				资源文件绝对路径
		ffmpegPath		转码工具绝对路径
		formatFactoryPath	未用保留
		fileSavePath		资源文件http访问前路径
		letter					监控磁盘空间的盘符
		minfreeSpace		监控磁盘空间不足告警阀值（单位：GB）
		expiredDay		系统删除离当前时间前XX天的资源
		fileSystemDel		是否系统自动删除（加星重要的不会删除）
		resetPswd			系统默认重置密码
		ftpHost				FTP当前服务器IP
		ftpPort				FTP端口
		ftpUser				FTP帐号
		ftpPswd				未用保留
		listenUrl			不可修改