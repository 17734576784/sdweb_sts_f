use master

if exists (select * from sysdatabases where name = 'YDParaBen')
	drop database YDParaBen
GO

if exists (select * from sysdatabases where name = 'YDDataBen')
	drop database YDDataBen
GO

if exists (select * from sysdevices where name = 'YDParaBen')
	EXEC sp_dropdevice YDParaBen ,DELFILE
GO

if exists (select * from sysdevices where name = 'YDParaBenLog')
	EXEC sp_dropdevice YDParaBenLog ,DELFILE
GO

if exists (select * from sysdevices where name = 'YDDataBen')
	EXEC sp_dropdevice YDDataBen ,DELFILE
GO

if exists (select * from sysdevices where name = 'YDDataBenLog')
	EXEC sp_dropdevice YDDataBenLog ,DELFILE
GO


create database YDParaBen
  ON 
	Primary ( name=YDParaBen, 
	filename='D:\MSSQL\Data\YDParaBen.mdf',
	size = 10MB, filegrowth=5MB)
  LOG ON
       (name=YDParaBenLog,
        filename = 'D:\MSSQL\data\YDParaBenLog.ldf',
        size= 32MB, filegrowth=5MB)

GO

create database YDDataBen
  On  Primary 
	(name=YDDataBen,
	filename='D:\MSSQL\data\YDDataBen.mdf',
 	 size=10MB, filegrowth=10MB),
	(name=YDDataBenEx1,
	filename='D:\MSSQL\data\YDDataBenEx1.ndf',
 	 size=2MB, filegrowth=10MB),
	(name=YDDataBenEx2,
	filename='D:\MSSQL\data\YDDataBenEx2.ndf',
 	 size=2MB, filegrowth=10MB),
	(name=YDDataBenEx3,
	filename='D:\MSSQL\data\YDDataBenEx3.ndf',
 	 size=2MB, filegrowth=10MB),
	(name=YDDataBenEx4,
	filename='D:\MSSQL\data\YDDataBenEx4.ndf',
 	 size=2MB, filegrowth=10MB)
  LOG ON 
 	(name = YDDataBenLog,
	 filename='D:\MSSQL\data\YDDataBenLog.ldf',
	size=32MB, filegrowth=10MB
	)     
go

use YDDataBen
go



/*添加数据库容量存储过程*/

use master
go
sp_dboption  YDParaBen,"select into",true
go
sp_dboption YDParaBen,"trunc. log on chkpt",true
go
use YDParaBen
go
checkpoint
go

use master
go
sp_dboption YDDataBen,"select into",true
go
use YDDataBen
go
checkpoint
go

use master
go
sp_dboption YDDataBen,"trunc. log on chkpt",true
go
use YDDataBen
go
checkpoint
go


print 'dump transaction db to YDParaBen '
go

dump transaction YDParaBen with truncate_only
go

checkpoint
go


