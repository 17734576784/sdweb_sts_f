use YDparaBen
go


---电表型号 使用意义不大 去掉
/***********************  电表型号 开始  **********************/

--delete metermodel
--go

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(1,  'DTZY1277C-ZL'			  		, 1000,  4,  2,  220,5 ,  0, 	3,2400)	
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(2,  'DTZY1277C-ZH'					  , 1000,  4,  2,  220,10,  0, 	3,2400)	

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(6,  'DTZY1277-ZL'							, 1000,  4,  2,  220,5 ,  0, 	3,2400)	
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(7,  'DTZY1277-ZH'					  	, 1000,  4,  2,  220,10 , 0, 	3,2400)	

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(11,  'DTZY1277CL'							, 1000,  4,  2,  220,5 ,  0, 	3,2400)	
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(12,  'DTZY1277CH'					  	, 1000,  4,  2,  220,10 , 0, 	3,2400)	

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(15,  'DTSD1277-GL'						, 1000,  4,  2,  220,5 ,  0, 	3,2400)	
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(16,  'DTSD1277-GH'					  , 1000,  4,  2,  220,10 , 0, 	3,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(21,  'DDZY1277L'					  	, 1000,  4,  3,  220,5 ,  0, 	3,2400)	
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(22,  'DDZY1277H'					  	, 1000,  4,  3,  220,5 ,  0, 	3,2400)	


--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(31,   '部颁表'								, 1000,  4,  1,  100,5 , 0,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(32,   '部颁表(0.1Wh)'					, 1000,  4,  1,  100,5 , 0,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(33,   '部颁表(0.001Wh)'				, 1000,  4,  1,  100,5 , 0,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(34,   '部颁表反向'						, 1000,  4,  1,  100,5 , 7,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(35,  '07版部颁正向1200'				, 1000,  4,  1,  100,5 , 0,  	3,1200)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(36,  '07版部颁反向1200'				, 1000,  4,  1,  100,5 , 0,  	3,1200)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(37,  '07版部颁正向2400'				, 1000,  4,  1,  100,5 , 15, 	3,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(38,  '07版部颁反向2400'				, 1000,  4,  1,  100,5 , 15, 	3,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(39,  '变电站07版部颁'					, 1000,  4,  1,  100,5 , 15, 	3,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(51,   '威胜表'								, 1000,  4,  1,  100,5 , 0,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(52,   '威胜表反向'						, 1000,  4,  1,  100,5 , 7,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(61,   'DTSF566型A相(正向值)'	, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(62,  'DTSF566型B相(正向值)'		, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(63,  'DTSF566型C相(正向值)'		, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(64,  'DTSF566型A相(反向值)'		, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(65,  'DTSF566型B相(反向值)'		, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(66,  'DTSF566型C相(反向值)'		, 1000,  4,  1,  100,5 , 9,  	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(67,  'DTSF566型正向和'				, 1000,  4,  1,  100,5 , 9,  	1,2400)    		
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(68,  'DTSF566型反向和'				, 1000,  4,  1,  100,5 , 9,  	1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(81,  '林洋A相(正向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(82,  '林洋B相(正向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(83,  '林洋C相(正向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(84,  '林洋A相(反向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(85,  '林洋B相(反向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(86,  '林洋C相(反向值)'				, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(87,  '林洋正向和'							, 1000,  4,  1,  100,5 , 12,  1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(88,  '林洋反向和'							, 1000,  4,  1,  100,5 , 12,  1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(91,  '菏泽正向'								, 1000,  4,  1,  100,5 , 14, 	1,2400)
--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(92,  '菏泽反向'								, 1000,  4,  1,  100,5 , 14, 	1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(96,  '南京新宁光电表'					, 1000,  4,  1,  100,5 , 16, 	1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(101,   '恒通表'								, 1000,  4,  1,  100,5 , 13, 	1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(106,   '脉冲表'								, 1000,  4,  1,  100,5 , 0,  	1,2400)

--insert metermodel(id,describe,pulse_k,fl_num,wiring_mode,rv,ri,factory, prot,baud) values(111,   '485模块'							, 1000,  4,  1,  100,5 , 0,  	1,2400)

--go

/***********************  电表型号 结束  **********************/

-- 终端型号太多 ， 故去掉部分
/***********************  终端型号 开始  **********************/
print '========insert into record  table 终端型号=========='
delete rtumodel
go
------------------------------------------
---1表示峰 2 平  3谷  4 尖----------------
                                                                                                                                                                                                                  
insert rtumodel(id,describe,factory,meter_num,prot,chan_type1 ,chan_type2, dndata_int,dndata_dec,addr_jz,data_cap,day_datacap,mon_datacap,dbfl1,dbfl2,dbfl3,dbfl4) values(104,'General Concentrator',						0, 60, 3,		4,	3, 6,  2,	0,60,	60,	12,4,1,2,3)
insert rtumodel(id,describe,factory,meter_num,prot,chan_type1 ,chan_type2, dndata_int,dndata_dec,addr_jz,data_cap,day_datacap,mon_datacap,dbfl1,dbfl2,dbfl3,dbfl4) values(101,'Concentrator 6600(KLD)',			0, 60, 52,	4,	3, 6,  2,	0,60,	60,	12,4,1,2,3)



go                                                          

/***********************  终端型号 结束  **********************/


/**************** 通道通讯配置表***************/
print '========insert into record  table 通道通讯配置表=========='

delete chancommcfg
go
insert into chancommcfg  values(0,		'直连通道',					16,		6,		120		)
insert into chancommcfg  values(1,		'MODEM通道',				16,		6,		120		)
insert into chancommcfg  values(2,		'GSM通道',					60,	  30,		120		)
insert into chancommcfg  values(3,		'UDP通道',					16,		6,		120		)
insert into chancommcfg  values(4,		'TCP服务端通道',		16,		6,		120		)
insert into chancommcfg  values(5,		'TCP客户端通道',		16,		6,		120		)
insert into chancommcfg  values(6,		'TCP客户端组通道',	16,		6,		120		)
go




/**************** 力调电费参数表***************/

delete  cs_stand
go
insert cs_stand(id, describe, use_flag, stand) values(0,    '执行标准90%', 		1, 90)
insert cs_stand(id, describe, use_flag, stand) values(1,    '执行标准85%', 		1, 85)
insert cs_stand(id, describe, use_flag, stand) values(2,    '执行标准80%', 		1, 80)
go


delete  cs_standitem
go
/*																													力调参数ID 分项ID 实际cos	月电费增减比率*/
--执行标准90%
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,    	0,   			100,    75)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,    	1,   			99,     75)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,			2,   			98,     75)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,			3,   			97,     75)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      4,    		96,     75)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      5,    		95,     75)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      6,    		94,     60)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      7,    		93,     45)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      8,    		92,     20)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      9,    		91,     15)      
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      10,   		90,     0)       
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      11,   		89,     -50)     
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      12,   		88,     -100)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      13,   		87,     -150)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      14,   		86,     -200)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      15,   		85,     -250)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      16,   		84,     -300)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      17,   		83,     -350)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      18,   		82,     -400)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      19,   		81,     -450)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      20,   		80,     -500)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      21,   		79,     -550)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      22,   		78,     -600)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      23,   		77,     -650)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      24,   		76,     -700)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      25,   		75,     -750)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      26,   		74,     -800)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      27,   		73,     -850)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      28,   		72,     -900)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      29,   		71,     -950)    
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      30,   		70,     -1000)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      31,   		69,     -1100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      32,   		68,     -1200)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      33,   		67,     -1300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      34,   		66,     -1400)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      35,   		65,     -1500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      36,   		64,     -1700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      37,   		63,     -1900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      38,   		62,     -2100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      39,   		61,     -2300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      40,   		60,     -2500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      41,   		59,     -2700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      42,   		58,     -2900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      43,   		57,     -3100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      44,   		56,     -3300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      45,   		55,     -3500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      46,   		54,     -3700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      47,   		53,     -3900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      48,   		52,     -4100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      49,   		51,     -4300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      50,   		50,     -4500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      51,   		49,     -4700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      52,   		48,     -4900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      53,   		47,     -5100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      54,   		46,     -5300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      55,   		45,     -5500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      56,   		44,     -5700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      57,   		43,     -5900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      58,   		42,     -6100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      59,   		41,     -6300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      60,   		40,     -6500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      61,   		39,     -6700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      62,   		38,     -6900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      63,   		37,     -7100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      64,   		36,     -7300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      65,   		35,     -7500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      66,   		34,     -7700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      67,   		33,     -7900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      68,   		32,     -8100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      69,   		31,     -8300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      70,   		30,     -8500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      71,   		29,     -8700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      72,   		28,     -8900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      73,   		27,     -9100)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      74,   		26,     -9300)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      75,   		25,     -9500)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      76,   		24,     -9700)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      77,   		23,     -9900)   
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      78,   		22,     -10100)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      79,   		21,     -10300)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      80,   		20,     -10500)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      81,   		19,     -10700)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      82,   		18,     -10900)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      83,   		17,     -11100)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      84,   		16,     -11300)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      85,   		15,     -11500)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      86,   		14,     -11700)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      87,   		13,     -11900)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      88,   		12,     -12100)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      89,   		11,     -12300)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      90,   		10,     -12500)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      91,  			9,      -12700)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      92,  			8,      -12900)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      93,  			7,      -13100)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      94,  			6,      -13300)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      95,  			5,      -13500)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      96,  			4,      -13700)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      97,  			3,      -13900)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      98,  			2,      -14100)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      99,  			1,      -14300)  
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(0,      100, 	 	  0,      -14500)  

--执行标准85%
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      0,   			100,		110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      1,   			99,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      2,   			98,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      3,   			97,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      4,    		96,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      5,    		95,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      6,    		94,			110)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      7,    		93,			95)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      8,    		92,			80)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      9,    		91,			65)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      10,   		90,			50)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      11,   		89,			40)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      12,   		88,			30)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      13,   		87,			20)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      14,   		86,			10)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      15,   		85,			0)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      16,   		84,			-50)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      17,   		83,			-100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      18,   		82,			-150)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      19,   		81,			-200)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      20,   		80,			-250)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      21,   		79,			-300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      22,   		78,			-350)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      23,   		77,			-400)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      24,   		76,			-450)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      25,   		75,			-500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      26,   		74,			-550)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      27,   		73,			-600)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      28,   		72,			-650)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      29,   		71,			-700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      30,   		70,			-750)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      31,   		69,			-800)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      32,   		68,			-850)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      33,   		67,			-900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      34,   		66,			-950)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      35,   		65,			-1000)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      36,   		64,			-1100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      37,   		63,			-1200)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      38,   		62,			-1300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      39,   		61,			-1400)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      40,   		60,			-1500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      41,   		59,			-1700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      42,   		58,      -1900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      43,   		57,      -2100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      44,   		56,      -2300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      45,   		55,      -2500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      46,   		54,      -2700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      47,   		53,      -2900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      48,   		52,      -3100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      49,   		51,      -3300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      50,   		50,      -3500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      51,   		49,      -3700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      52,   		48,      -3900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      53,   		47,      -4100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      54,   		46,      -4300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      55,   		45,      -4500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      56,   		44,      -4700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      57,   		43,      -4900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      58,   		42,      -5100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      59,   		41,      -5300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      60,   		40,      -5500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      61,   		39,      -5700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      62,   		38,      -5900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      63,   		37,      -6100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      64,   		36,      -6300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      65,   		35,      -6500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      66,   		34,      -6700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      67,   		33,      -6900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      68,   		32,      -7100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      69,   		31,      -7300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      70,   		30,      -7500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      71,   		29,      -7700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      72,   		28,      -7900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      73,   		27,      -8100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      74,   		26,      -8300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      75,   		25,      -8500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      76,   		24,      -8700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      77,   		23,      -8900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      78,   		22,      -9100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      79,   		21,      -9300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      80,   		20,      -9500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      81,   		19,      -9700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      82,   		18,      -9900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      83,   		17,      -10100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      84,   		16,      -10300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      85,   		15,      -10500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      86,   		14,      -10700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      87,   		13,      -10900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      88,   		12,      -11100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      89,   		11,      -11300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      90,   		10,      -11500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      91,  			9,       -11700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      92,  			8,       -11900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      93,  			7,       -12100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      94,  			6,       -12300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      95,  			5,       -12500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      96,  			4,       -12700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      97,  			3,       -12900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      98,  			2,       -13100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      99,  			1,       -13300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(1,      100, 	 	  0,       -13500)    

--执行标准80%
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      0,   			100,		 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      1,   			99,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      2,   			98,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      3,   			97,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      4,    		96,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      5,    		95,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      6,    		94,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      7,    		93,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      8,    		92,			 130)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      9,    		91,			 115)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      10,   		90,			 100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      11,   		89,			 90)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      12,   		88,			 80)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      13,   		87,			 70)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      14,   		86,			 60)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      15,   		85,			 50)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      16,   		84,			 40)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      17,   		83,			 30)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      18,   		82,			 20)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      19,   		81,			 10)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      20,   		80,			 0)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      21,   		79,			 -50)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      22,   		78,			 -100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      23,   		77,			 -150)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      24,   		76,			 -200)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      25,   		75,			 -250)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      26,   		74,			 -300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      27,   		73,			 -350)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      28,   		72,			 -400)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      29,   		71,			 -450)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      30,   		70,			 -500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      31,   		69,			 -550)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      32,   		68,			 -600)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      33,   		67,			 -650)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      34,   		66,			 -700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      35,   		65,			 -750)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      36,   		64,			 -800)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      37,   		63,			 -850)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      38,   		62,			 -900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      39,   		61,			 -950)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      40,   		60,			 -1000)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      41,   		59,			 -1100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      42,   		58,			 -1200)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      43,   		57,			 -1300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      44,   		56,			 -1400)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      45,   		55,			 -1500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      46,   		54,	 		 -1700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      47,   		53,	     -1900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      48,   		52,	     -2100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      49,   		51,	     -2300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      50,   		50,	     -2500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      51,   		49,	     -2700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      52,   		48,	     -2900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      53,   		47,	     -3100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      54,   		46,	     -3300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      55,   		45,	     -3500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      56,   		44,	     -3700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      57,   		43,	     -3900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      58,   		42,	     -4100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      59,   		41,	     -4300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      60,   		40,	     -4500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      61,   		39,	     -4700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      62,   		38,	     -4900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      63,   		37,	     -5100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      64,   		36,	     -5300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      65,   		35,	     -5500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      66,   		34,	     -5700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      67,   		33,	     -5900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      68,   		32,	     -6100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      69,   		31,	     -6300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      70,   		30,	     -6500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      71,   		29,	     -6700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      72,   		28,			 -6900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      73,   		27,	     -7100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      74,   		26,	     -7300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      75,   		25,	     -7500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      76,   		24,	     -7700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      77,   		23,	     -7900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      78,   		22,	     -8100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      79,   		21,      -8300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      80,   		20,      -8500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      81,   		19,      -8700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      82,   		18,      -8900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      83,   		17,      -9100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      84,   		16,      -9300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      85,   		15,      -9500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      86,   		14,      -9700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      87,   		13,      -9900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      88,   		12,      -10100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      89,   		11,      -10300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      90,   		10,      -10500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      91,  			9,       -10700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      92,  			8,       -10900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      93,  			7,       -11100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      94,  			6,       -11300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      95,  			5,       -11500)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      96,  			4,       -11700)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      97,  			3,       -11900)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      98,  			2,       -12100)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      99,  			1,       -12300)
insert cs_standitem(cos_id, item_id, realcos, dfchgarate)values(2,      100, 	 	  0,       -12500)
                                   
go 

/***********************  数据字典表 开始  ********************/
print '========insert into record  table 数据字典表=========='
delete diction
go

/**/
--	type_name   char(32)   		not null,				/*字典类型名*/
--	item_name	  char(64)   		not null,				/*字典项目名*/
--	value       int           null,						/*数值*/
--	str_val			varchar(64)   null,						/*字符值*/
--1.   2.   3.  
---------------------1      2            3            4     5
insert diction values(0,  '采集频率',  	'1',	 				1,  	'')
insert diction values(0,  '采集频率',  	'2',					2,	 	'')
insert diction values(0,  '采集频率',  	'3',	 				3,	 	'')
insert diction values(0,  '采集频率',  	'4',	 				4,	 	'')
insert diction values(0,  '采集频率',  	'5',	 				5,	 	'')
insert diction values(0,  '采集频率',  	'6',	 				6,	 	'')
insert diction values(0,  '采集频率',  	'7',	 				7,	 	'')
go

insert diction values(1,  '补召策略',  	'不补召',	   	0,	 '')
insert diction values(1,  '补召策略',  	'补召',	 			1,	 '')
go

insert diction values(2,  '次数',  			'无设置',  		0,  '')
insert diction values(2,  '次数',  			'1次',		  	1,	 '')
insert diction values(2,  '次数',  			'2次',		  	2,	 '')
insert diction values(2,  '次数',  			'3次',		  	3,	 '')
insert diction values(2,  '次数',  			'4次',		  	4,	 '')
insert diction values(2,  '次数',  			'5次',		  	5,	 '')
insert diction values(2,  '次数',  '6次',		  6,	 '')
insert diction values(2,  '次数',  '7次',		  7,	 '')
insert diction values(2,  '次数',  '8次',		  8,	 '')
insert diction values(2,  '次数',  '9次',		  9,	 '')
go

/*中间码: 分别对应江西规约的2、3、4、5以及国电05规约的0、1、2、3*/
insert diction values(3,  '任务时间单位',  '分',	 2,  '')
insert diction values(3,  '任务时间单位',  '时',	 3,	 '')
insert diction values(3,  '任务时间单位',  '日',	 4,	 '')
insert diction values(3,  '任务时间单位',  '月',	 5,	 '')
go

insert diction values(4,  '任务类型',  '普通任务',	 1,	 '')
insert diction values(4,  '任务类型',  '中继任务',	 2,	 '')
insert diction values(4,  '任务类型',  '异常任务',	 4,	 '')
go

insert diction values(5,  '模拟量属性',  '无设置',	 0,	 '')
insert diction values(5,  '模拟量属性',  'A相电压',	 1,	 '')
insert diction values(5,  '模拟量属性',  'B相电压',	 2,	 '')
insert diction values(5,  '模拟量属性',  'C相电压',	 3,	 '')
insert diction values(5,  '模拟量属性',  'A相电流',	 4,	 '')
insert diction values(5,  '模拟量属性',  'B相电流',	 5,	 '')
insert diction values(5,  '模拟量属性',  'C相电流',	 6,	 '')
go

insert diction values(6,  '脉冲属性',  '无设置',		         0,  '')
insert diction values(6,  '脉冲属性',  '正向有功',		       1,  '')
insert diction values(6,  '脉冲属性',  '反向有功',		       2,	 '')
insert diction values(6,  '脉冲属性',  '正向感性无功(I)',	   3,	 '')
insert diction values(6,  '脉冲属性',  '正向容性无功(Iv)',	 4,	 '')
insert diction values(6,  '脉冲属性',  '反向容性无功(II)',	 5,  '')
insert diction values(6,  '脉冲属性',  '反向感性无功(III)',  6,	 '')
go

insert diction values(7,  '使用标志',  'No',	 0,	 '')
insert diction values(7,  '使用标志',  'Yes',		 1,  '')
go

insert diction values(8,  '抄表方式',  '485',		  0,	'')
insert diction values(8,  '抄表方式',  '422',		  1,	'')
insert diction values(8,  '抄表方式',  '232',		  2,	'')
insert diction values(8,  '抄表方式',  '电流环',  3,	'')
go

insert diction values(9,  '控制类型',  '无控制功能',	0,	 '')
insert diction values(9,  '控制类型',  '非卡式控制',  1,	 '')
insert diction values(9,  '控制类型',  '卡式控制',  	2,	 '')
go

insert diction values(10,  '行政编码',  '江西龙南',  2439,	 '')
insert diction values(10,  '行政编码',  '江西定南',  5000,	 '')
go

insert diction values(11,  '主副表标志',  '主表',  0,	 '')
insert diction values(11,  '主副表标志',  '副表',  1,	 '')
go

insert diction values(12,  '小数位个数',  '1位小数',	 1,	 '')
insert diction values(12,  '小数位个数',  '2位小数',	 2,	 '')
insert diction values(12,  '小数位个数',  '3位小数',	 3,	 '')
insert diction values(12,  '小数位个数',  '4位小数',	 4,	 '')

insert diction values(13,  '整数位个数',  '4位整数',	 4,	 '')
insert diction values(13,  '整数位个数',  '5位整数',	 5,	 '')
insert diction values(13,  '整数位个数',  '6位整数',	 6,  '')
insert diction values(13,  '整数位个数',  '7位整数',	 7,  '')
insert diction values(13,  '整数位个数',  '8位整数',	 8,  '')
go

insert diction values(14,  '测点类型',  '485表',		 0,	 '')
insert diction values(14,  '测点类型',  '模拟量',		 1,	 '')
insert diction values(14,  '测点类型',  '脉冲量',		 2,	 '')
insert diction values(14,  '测点类型',  '计算量',		 3,	 '')
insert diction values(14,  '测点类型',  '交流采样',  4,  '')
go

insert diction values(15,  '采集来源',  '终端',	   0,	 '')
insert diction values(15,  '采集来源',  '测量点',	 1,	 '')
insert diction values(15,  '采集来源',  '总加组',	 2,	 '')
insert diction values(15,  '采集来源',  '模拟量',  3,	 '')
go

insert diction values(16,  '安装地点',  'Indoor Measurement Case',  0,	 '')
insert diction values(16,  '安装地点',  'Box Transformer Substation',		       1,	 '')
insert diction values(16,  '安装地点',  'Outdoor Measurement Case',	 2,	 '')
--insert diction values(16,  '安装地点',  '杆变',		       3,	 '')
insert diction values(16,  '安装地点',  'Basement',		     4,	 '')
--insert diction values(16,  '安装地点',  '变电站',		     5,	 '')
insert diction values(16,  '安装地点',  'Others',		       6,	 '')
go

--insert diction values(17,  '运行状态',  'Uninstalled',  0,  '')
insert diction values(17,  '运行状态',  'Put into operation',	   1,	 '')
--insert diction values(17,  '运行状态',  'Failure',	   2,	 '')
--insert diction values(17,  '运行状态',  'Abandonment',	   3,	 '')
insert diction values(17,  '运行状态',  'Waiting operation',	   4,	 '')
insert diction values(17,  '运行状态',  'Stop',	   50, '')
go

insert diction values(18,  '变压器类型',  'Special Transformer',  0,	 '')
insert diction values(18,  '变压器类型',  'Mix Transformer',	 1,	 '')
insert diction values(18,  '变压器类型',  'Public Transformer',	 2,	 '')
go

insert diction values(19,  '线路类型',  '馈线',	 0,	 '')
go

insert diction values(20,  '电压等级',  '1000kV', 0,	 '')
insert diction values(20,  '电压等级',  '750kV',  1,	 '')
insert diction values(20,  '电压等级',  '500kV',  2,	 '')
insert diction values(20,  '电压等级',  '220kV',	3,	 '')
insert diction values(20,  '电压等级',  '110kV',	4,	 '')
insert diction values(20,  '电压等级',  '35kV',	  5,	 '')
insert diction values(20,  '电压等级',  '10kV',	  6,	 '')
insert diction values(20,  '电压等级',  '6kV',	  7,	 '')
insert diction values(20,  '电压等级',  '380V',	  8,	 '')
insert diction values(20,  '电压等级',  '220V',	  9,	 '')
insert diction values(20,  '电压等级',  '100V',	  10,	 '')
go

insert diction values(21,  '负荷类型',  '一类负荷',	 0,	 '')
insert diction values(21,  '负荷类型',  '二类负荷',	 1,	 '')
insert diction values(21,  '负荷类型',  '其他负荷',  2,	 '')
go

insert diction values(22,  '电源类型',  'Single Power Supply',	 0,	 '')
insert diction values(22,  '电源类型',  'Double Power Supply',	 1,	 '')
insert diction values(22,  '电源类型',  'Poly Power Supply',	 2,	 '')
go

insert diction values(23,  '终端类型',  'Load Control Terminal',	   0,	 '')
insert diction values(23,  '终端类型',  'Electricity Energy Terminal',  1,	 '')
insert diction values(23,  '终端类型',  'Distributed Transformer Terminal ',	   2,	 '')
insert diction values(23,  '终端类型',  'Concentrator',	     3,	 '')
go

insert diction values(24,  '通信加密',  '不加密',	 0,	 '')
insert diction values(24,  '通信加密',  '加密',	   1,	 '')
go

insert diction values(25,  '接线方式',  '三相三线',	 1,	 '')
insert diction values(25,  '接线方式',  '三相四线',	 2,	 '')
insert diction values(25,  '接线方式',  '单相',	     3,	 '')
go

insert diction values(26,  '通道类型',  '直连模式',		     0,  '')
insert diction values(26,  '通道类型',  'MODEM 模式',	     1,	 '')
insert diction values(26,  '通道类型',  'GSM短信模式',	   2,	 '')
insert diction values(26,  '通道类型',  'UDP模式',	   		 3,	 '')
insert diction values(26,  '通道类型',  'TCP服务端',  		 4,  '')
insert diction values(26,  '通道类型',  'TCP客户端',  		 5,	 '')
insert diction values(26,  '通道类型',  'TCP客户端组',	   6,	 '')
go

insert diction values(27,  '是否标志',  'No',  0,	 '')
insert diction values(27,  '是否标志',  'Yes',	 1,  '')
go

insert diction values(28,  '总加运算标志',  '加(+)',		0,	'')
insert diction values(28,  '总加运算标志',  '减(-)',		1,	'')
go

insert diction values(29,  '总加方向',  '正向',	 0,	 '')
insert diction values(29,  '总加方向',  '反向',  1,  '')
go

insert diction values(30,  '波特率',  '默认速率',	300,	  '')
insert diction values(30,  '波特率',  '600bps',		600,	  '')
insert diction values(30,  '波特率',  '1200bps',	1200,	  '')
insert diction values(30,  '波特率',  '2400bps',	2400,	  '')
insert diction values(30,  '波特率',  '4800bps',	4800,	  '')
insert diction values(30,  '波特率',  '7200bps',	7200,	  '')
insert diction values(30,  '波特率',  '9600bps',	9600,	  '')
insert diction values(30,  '波特率',  '19200bps', 19200,	'')
go

insert diction values(31,  '数据位',  '5位',		5,	'')
insert diction values(31,  '数据位',  '6位',		6,	'')
insert diction values(31,  '数据位',  '7位',		7,	'')
insert diction values(31,  '数据位',  '8位',		8,	'')
go

insert diction values(32,  '校验位',  '无校验',	0,	'')
insert diction values(32,  '校验位',  '奇校验',	1,	'')
insert diction values(32,  '校验位',  '偶校验',	2,	'')
go

insert diction values(33,  '停止位',  '1位',		 0,	 '')
insert diction values(33,  '停止位',  '1.5位',	 1,	 '')
insert diction values(33,  '停止位',  '2位',		 2,	 '')
go

insert diction values(34,  '流控制',  '无',		     0,	 '')
insert diction values(34,  '流控制',  'Xon/Xoff',  1,	 '')
insert diction values(34,  '流控制',  '硬件',	     2,	 '')
go

/*在终端型号中使用 zhj*/
insert diction values(35,  '终端通道类型',  '直连模式',		     0,  '')
insert diction values(35,  '终端通道类型',  'MODEM 模式',	     1,	 '')
insert diction values(35,  '终端通道类型',  'GSM短信模式',	   2,	 '')
insert diction values(35,  '终端通道类型',  'UDP模式',	   		 3,	 '')
insert diction values(35,  '终端通道类型',  'TCP服务端',  		 4,  '')
insert diction values(35,  '终端通道类型',  'TCP客户端',  		 5,	 '')
go


insert diction values(36,  '串口号',  '未设置',  0,	  '')
insert diction values(36,  '串口号',  'COM1',	   1,	  '')
insert diction values(36,  '串口号',  'COM2',	   2,	  '')
insert diction values(36,  '串口号',  'COM3',	   3,	  '')
insert diction values(36,  '串口号',  'COM4',	   4,	  '')
insert diction values(36,  '串口号',  'COM5',	   5,	  '')
insert diction values(36,  '串口号',  'COM6',	   6,	  '')
insert diction values(36,  '串口号',  'COM7',	   7,	  '')
insert diction values(36,  '串口号',  'COM8',	   8,	  '')
insert diction values(36,  '串口号',  'COM9',	   9,	  '')
insert diction values(36,  '串口号',  'COM10',   10,  '')
insert diction values(36,  '串口号',  'COM11',	 11,	'')
insert diction values(36,  '串口号',  'COM12',	 12,	'')
insert diction values(36,  '串口号',  'COM13',	 13,	'')
insert diction values(36,  '串口号',  'COM14',	 14,	'')
insert diction values(36,  '串口号',  'COM15',	 15,	'')
insert diction values(36,  '串口号',  'COM16',	 16,	'')
insert diction values(36,  '串口号',  'COM17',	 17,	'')
insert diction values(36,  '串口号',  'COM18',	 18,	'')
insert diction values(36,  '串口号',  'COM19',	 19,	'')
insert diction values(36,  '串口号',  'COM20',	 20,	'')
insert diction values(36,  '串口号',  'COM21',	 21,	'')
insert diction values(36,  '串口号',  'COM22',	 22,	'')
insert diction values(36,  '串口号',  'COM23',	 23,	'')
insert diction values(36,  '串口号',  'COM24',	 24,	'')
insert diction values(36,  '串口号',  'COM25',	 25,	'')
insert diction values(36,  '串口号',  'COM26',	 26,	'')
insert diction values(36,  '串口号',  'COM27',	 27,	'')
insert diction values(36,  '串口号',  'COM28',	 28,	'')
insert diction values(36,  '串口号',  'COM29',	 29,	'')
insert diction values(36,  '串口号',  'COM30',	 30,	'')
insert diction values(36,  '串口号',  'COM31',	 31,	'')
insert diction values(36,  '串口号',  'COM32',	 32,	'')
go

insert diction values(37,  '所属行业',  '未设置',  0,	'')
insert diction values(37,  '所属行业',  '工业',	   1,	'')
insert diction values(37,  '所属行业',  '农业',	   2,	'')
insert diction values(37,  '所属行业',  '商业',	   3,	'')
insert diction values(37,  '所属行业',  '副业',	   4,	'')
insert diction values(37,  '所属行业',  '综合',	   5,	'')
go

insert diction values(38,  'SIM卡类型',  'GPRS卡',	 0,	 '')
insert diction values(38,  'SIM卡类型',  '短信卡',	 1,	 '')
insert diction values(38,  'SIM卡类型',  'CDMA卡',	 2,	 '')
go

insert diction values(39,  '终端生产厂家',  'KELIN',			0,	 '')
--insert diction values(39,  '终端生产厂家',  '公共',			1,	 '')
--insert diction values(39,  '终端生产厂家',  '龙电',			2,	 '')
--insert diction values(39,  '终端生产厂家',  '浩宁达',		3,	 '')
insert diction values(39,  '终端生产厂家',  'WASION',	 	  4,	 '')
--insert diction values(39,  '终端生产厂家',  '华隆',	 	  5,	 '')
--insert diction values(39,  '终端生产厂家',  '中原',	 	  6,	 '')
--insert diction values(39,  '终端生产厂家',  '晨泰',	 	  7,	 '')
--insert diction values(39,  '终端生产厂家',  '协同',	 	  8,	 '')
--insert diction values(39,  '终端生产厂家',  '兰吉尔',	  9,	 '')
--insert diction values(39,  '终端生产厂家',  '环能',	 	  10,	 '')
--insert diction values(39,  '终端生产厂家',  '中电金锋',	11,  '')
--insert diction values(39,  '终端生产厂家',  '华立',	 	  12,	 '')
--insert diction values(39,  '终端生产厂家',  '科能',	 	  13,	 '')
--insert diction values(39,  '终端生产厂家',  '正泰龙',	 	30,	 '')
go

insert diction values(40,  '电表生产厂家',  'KELIN',		 0,	  '')
--insert diction values(40,  '电表生产厂家',  '蜀达',		 1,	  '')
--insert diction values(40,  '电表生产厂家',  '红相',		 2,	  '')
--insert diction values(40,  '电表生产厂家',  '华隆',		 3,	  '')
insert diction values(40,  '电表生产厂家',  'SAMSUNG',		 4,	  '')
--insert diction values(40,  '电表生产厂家',  '浩宁达',  5,	  '')
insert diction values(40,  '电表生产厂家',  'ABB',		 6,	  '')
insert diction values(40,  '电表生产厂家',  'WASION',		 7,	  '')
--insert diction values(40,  '电表生产厂家',  '许继',		 8,	  '')
--insert diction values(40,  '电表生产厂家',  '正泰',		 9,	  '')
--insert diction values(40,  '电表生产厂家',  '华立',		 10,	'')
--insert diction values(40,  '电表生产厂家',  '龙电',		 11,	'')
--insert diction values(40,  '电表生产厂家',  '林洋',		 12,	'')
--insert diction values(40,  '电表生产厂家',  '恒通',		 13,	'')
--insert diction values(40,  '电表生产厂家',  '菏泽',		 14,	'')
--insert diction values(40,  '电表生产厂家',  '部颁',		 15,	'')
--insert diction values(40,  '电表生产厂家',  '新宁光',	 16,	'')
--insert diction values(40,  '电表生产厂家',  '科能',	 	 17,	'')

go

insert diction values(41,  '电表通讯规约',  '无需抄表',			 				0,	 '')
insert diction values(41,  '电表通讯规约',  'DL/T 645—1997',			 	01,	 '')
insert diction values(41,  '电表通讯规约',  '交流采样装置通信',			02,	 '')
insert diction values(41,  '电表通讯规约',  '有载调容控制器',					27,	 '')
insert diction values(41,  '电表通讯规约',  '智能断路器',						28,	 '')
insert diction values(41,  '电表通讯规约',  '换相开关',						29,	 '')
insert diction values(41,  '电表通讯规约',  'DL/T 645—2007',	      30,	 '')
insert diction values(41,  '电表通讯规约',  '串行接口低压载波',	    31,	 '')
--20130920 zhp 新增电表通讯规约
insert diction values(41,  '电表通讯规约',  '保护设备Modbus通信协议',	    32,	 '')
insert diction values(41,  '电表通讯规约',  '河南农排表通信协议',	    35,	 '')
go

/*
insert diction values(41,  '电表通讯规约',  '科林规约',	       00,	 '')
insert diction values(41,  '电表通讯规约',  '部颁规约',	       10,	 '')
insert diction values(41,  '电表通讯规约',  'IEC1107',	       20,	 '')
insert diction values(41,  '电表通讯规约',  '威胜规约',	       31,	 '')
insert diction values(41,  '电表通讯规约',  '红相规约',	       32,	 '')
insert diction values(41,  '电表通讯规约',  '浩宁达规约',	     33,	 '')
insert diction values(41,  '电表通讯规约',  '华隆规约',	       34,	 '')
insert diction values(41,  '电表通讯规约',  '龙电规约',	       35,	 '')
insert diction values(41,  '电表通讯规约',  '兰吉尔D',	       36,	 '')
insert diction values(41,  '电表通讯规约',  '许继规约',	       37,	 '')
insert diction values(41,  '电表通讯规约',  '科陆规约',	       38,	 '')
insert diction values(41,  '电表通讯规约',  '三星规约',	       39,	 '')
insert diction values(41,  '电表通讯规约',  '爱拓利归约',	     40,	 '')
insert diction values(41,  '电表通讯规约',  'ABB规约A表',	     41,	 '')
insert diction values(41,  '电表通讯规约',  'ABB规约圆表',	   42,	 '')
insert diction values(41,  '电表通讯规约',  '大琦规约',	       43,	 '')
insert diction values(41,  '电表通讯规约',  '红相MK3规约',	   44,	 '')
insert diction values(41,  '电表通讯规约',  '华立规约',	       45,	 '')
insert diction values(41,  '电表通讯规约',  '兰吉尔B',	       46,	 '')
insert diction values(41,  '电表通讯规约',  '林洋规约',	       47,	 '')
insert diction values(41,  '电表通讯规约',  '东方电子',	       48,	 '')
insert diction values(41,  '电表通讯规约',  '伊梅尔规约',	     49,	 '')
insert diction values(41,  '电表通讯规约',  '伊斯卡规约',	     50,	 '')
insert diction values(41,  '电表通讯规约',  '隆基宁光预付费',	 51,	 '')
*/

insert diction values(42,  '电表精度',  '无设置',	  0,	'0.0')
insert diction values(42,  '电表精度',  '0.1S',	  	1,	'0.1')
insert diction values(42,  '电表精度',  '0.2S',	  	2,	'0.2')
insert diction values(42,  '电表精度',  '0.5S',	  	5,	'0.5')
insert diction values(42,  '电表精度',  '1.0S',	  	10,	'1.0')
insert diction values(42,  '电表精度',  '2.0',	  	20,	'2.0')
insert diction values(42,  '电表精度',  '2.5',	  	25,	'2.5')
go

--费控类型
--insert diction values(43,  '费控类型',  '无',	  0,	'')	-- 即为费控 必选其一
--chg 20111219
insert diction values(43,  '费控类型',  'Terminal Cost Control',	  0,	'')
insert diction values(43,  '费控类型',  'Master Station Cost Control',	  1,	'')
go

insert diction values(44,  '终端上行通信模式',  'TCP混合模式',	      0,	  '')
insert diction values(44,  '终端上行通信模式',  'TCP客户机永久在线',	17,	  '')
insert diction values(44,  '终端上行通信模式',  'TCP客户机被动激活',  18,	  '')
insert diction values(44,  '终端上行通信模式',  'TCP客户机时段在线',	19,	  '')
insert diction values(44,  '终端上行通信模式',  'TCP服务器模式',	    32,	  '')
insert diction values(44,  '终端上行通信模式',  'UDP混合模式',	      128,  '')
insert diction values(44,  '终端上行通信模式',  'UDP客户机永久在线',	145,	'')
insert diction values(44,  '终端上行通信模式',  'UDP客户机被动激活',	146,	'')
insert diction values(44,  '终端上行通信模式',  'UDP客户机时段在线',	147,	'')
insert diction values(44,  '终端上行通信模式',  'UDP服务器模式',	    160,	'')
go

/*--jack101229--*/
insert diction values(45,  '费率个数',  '单费率',   1,	  '')
insert diction values(45,  '费率个数',  '4费率',	 4,	  '')
insert diction values(45,  '费率个数',  '8费率',	 8,	  '')
insert diction values(45,  '费率个数',  '14费率',  14,  '')
go
/*--jack101229---*/


insert diction values(46,  'RAS监测类型',  '不监测',		 0,	 '')
insert diction values(46,  'RAS监测类型',  'PING监测',	 1,	 '')
go


insert diction values(47,  '冻结密度',  '不冻结',	 0,	  '')
insert diction values(47,  '冻结密度',  '15分钟',	 15,	'')
insert diction values(47,  '冻结密度',  '30分钟',	 30,	'')
insert diction values(47,  '冻结密度',  '60分钟',  60,	'')
go

insert diction values(48,  '电容控制方式',  '分相循环控制',	 0,	 '')
insert diction values(48,  '电容控制方式',  '三相循环控制',	 1,	 '')
insert diction values(48,  '电容控制方式',  '分相编码控制',	 2,	 '')
insert diction values(48,  '电容控制方式',  '三相编码控制',  3,	 '')
go

--hzhw 权限范围,全局权限，供电所权限, 线路负责人权限
insert diction values(49,  '权限范围',  '全局权限',	   0,	 '')
insert diction values(49,  '权限范围',  '变电所权限',  1,	 '')
go

/*
			对于江西 0 终端参数 1 测点参数 2 测点实时数据 3 测点日统计数据 4 测点月统计数据 5 测点事件记录 
			6 前置机参数（暂时不使用） 7 错误编码 8 告警编码 9 瞬时量数据 10 当前电能数据 11 上月电能数据 12 当前最大需量及发生时间
			13 上月最大需量及发生时间 14 其他数据
			对于国电相当于AFN 0 确认否认 1复位命令 其他类同

*/
insert diction values(50,  '江西配变编码类型',  '实时数据',		   2,  '')
insert diction values(50,  '江西配变编码类型',  '日统计数据',	   3,	 '')
insert diction values(50,  '江西配变编码类型',  '月统计数据',	   4,	 '')
insert diction values(50,  '江西配变编码类型',  '事件记录数据',  5,	 '')
go

insert diction values(51,  '江西负控编码类型',  '事件记录数据',					   5, 	'')
insert diction values(51,  '江西负控编码类型',  '瞬时量数据',						   9,	  '')
insert diction values(51,  '江西负控编码类型',  '当前电能数据',					   10,	'')
insert diction values(51,  '江西负控编码类型',  '上月电能数据',					   11,  '')
insert diction values(51,  '江西负控编码类型',  '当前最大需量及发生时间',  12,	'')
insert diction values(51,  '江西负控编码类型',  '上月最大需量及发生时间',  13,	'')
insert diction values(51,  '江西负控编码类型',  '其他数据',							   14,	'')
go

insert diction values(52,  '计量点类型',  '电表/交流采样',	  0,	'')
insert diction values(52,  '计量点类型',  '脉冲量',	  				1,	'')
insert diction values(52,  '计量点类型',  '模拟量',	  				2,	'')
insert diction values(52,  '计量点类型',  '断路器',	  				28,	'')
insert diction values(52,  '计量点类型',  '换相开关',  				29,	'')
insert diction values(52,  '计量点类型',  '有载调容控制器',  	30,	'')
insert diction values(52,  '计量点类型',  '其它',	  					50,	'')



--insert diction values(53,  '计费方式',	'Non',						0,	'')
--按金额计费  按电量计费
insert diction values(53,  '计费方式',  'Money Amount',  		1,	'')
insert diction values(53,  '计费方式',  'Energy Consumption',	 		2,	'')
go



insert diction values(55,  '控制方式',  '脉冲方式',  0,	 '')
insert diction values(55,  '控制方式',  '电平方式',  1,	 '')
go

insert diction values(56,  '智能表远程控制',   '跳闸',      0x1a,	 '')
insert diction values(56,  '智能表远程控制',   '合闸允许',  0x1b,	 '')
insert diction values(56,  '智能表远程控制',   '报警',      0x2a,	 '')
insert diction values(56,  '智能表远程控制',   '报警解除',  0x2b,	 '')
insert diction values(56,  '智能表远程控制',   '保电',      0x3a,	 '')
insert diction values(56,  '智能表远程控制',   '保电解除',  0x3b,	 '')
go

insert diction values(57,  '智能表密钥更新',   '身份认证',   0x01,	'')
insert diction values(57,  '智能表密钥更新',   '远程控制',	 0x02,	'')
insert diction values(57,  '智能表密钥更新',   '参数更新',   0x03,	'')
go


insert diction values(60,  '规约对象类型',  '终端',	       0,	 '')
insert diction values(60,  '规约对象类型',  '测量点',	     1,	 '')
insert diction values(60,  '规约对象类型',  '总加组',	     2,	 '')
insert diction values(60,  '规约对象类型',  '控制轮次',	   3,	 '')
insert diction values(60,  '规约对象类型',  '任务号',	     4,	 '')
insert diction values(60,  '规约对象类型',  '直流模拟量',  5,	 '')
insert diction values(60,  '规约对象类型',  '报警编码',	   6,  '')
go

insert diction values(61,  '规约数据类型',  '参数',	               0,	 '')
insert diction values(61,  '规约数据类型',  '控制',	               5,	 '')
insert diction values(61,  '规约数据类型',  '实时数据',	           10,	 '')
insert diction values(61,  '规约数据类型',  '分钟/小时数据',	     11,	 '')
insert diction values(61,  '规约数据类型',  '日数据及日统计数据',  12,	 '')
insert diction values(61,  '规约数据类型',  '月数据及月统计数据',	 13,	 '')
insert diction values(61,  '规约数据类型',  '抄表日数据',	 				 16,	 '')
insert diction values(61,  '规约数据类型',  '事项数据',	           20,	 '')
insert diction values(61,  '规约数据类型',  '错误警告',	           21,	 '')
insert diction values(61,  '规约数据类型',  '其它',	               30,	 '')
go


insert diction values(62,  '居民用户类型',  '普通用户',	           0,	 '')
insert diction values(62,  '居民用户类型',  '重点用户',	           1,	 '')
insert diction values(62,  '居民用户类型',  '总表',	               2,	 '')
go

insert diction values(63,  '地址录入进制',  '十进制',	               0,	 '')
insert diction values(63,  '地址录入进制',  '十六进制',	             1,	 '')
go

insert diction values(64,  '任务主动上报',  '不上报',	               0,	 '')
insert diction values(64,  '任务主动上报',  '上报',	             		 1,	 '')
go


insert diction values(65,  '支持类型',  '不支持',	               0,	 '')
insert diction values(65,  '支持类型',  '支持',	             		 1,	 '')
go


insert diction values(66,  '电表厂家',  '科林',  0,	'0')
insert diction values(66,  '电表厂家',  '科陆',	1,	'1')
insert diction values(66,  '电表厂家',  '金雀',	2,	'2')
insert diction values(66,  '电表厂家',  '许继',	3,	'3')
insert diction values(66,  '电表厂家',  '威胜',	4,	'4')
insert diction values(66,  '电表厂家',  '恒通',	5,	'5')
insert diction values(66,  '电表厂家',  '华立',	6,	'6')
insert diction values(66,  '电表厂家',  '林洋',	7,	'7')
insert diction values(66,  '电表厂家',  '三星',	8,	'8')
insert diction values(66,  '电表厂家',  '正泰',	9,	'9')
insert diction values(66,  '电表厂家',  'ABB',	10,	'10')
go

insert diction values(67,  '终端使用属性',  '变电站应用',		    0,	'')
insert diction values(67,  '终端使用属性',  '专变应用',		      1,	'')
insert diction values(67,  '终端使用属性',  '公变应用',  				2,  '')
insert diction values(67,  '终端使用属性',  '低压集抄应用',  		3,  '')
insert diction values(67,  '终端使用属性',  '农排应用',  				5,  '')	--jack add 20130130
go

insert diction values(68,  '加载情况',  '未加载',		    0,	'')
insert diction values(68,  '加载情况',  '已加载',		      1,	'')
go

insert diction values(69,  '通讯端口376',  '未设置',  		0,	  '')
insert diction values(69,  '通讯端口376',  '交流采样',		1,	  '')
insert diction values(69,  '通讯端口376',  'RS485 1接口', 2,	  '')
insert diction values(69,  '通讯端口376',  'RS485 2接口', 3,	  '')
insert diction values(69,  '通讯端口376',  'RS485 3接口', 4,	  '')
insert diction values(69,  '通讯端口376',  '终端级联',  	30,	  '')
insert diction values(69,  '通讯端口376',  '载波通讯',  	31,	  '')

insert diction values(70,  '日数据上报类型',  '45条规约',  		0,	  '')
insert diction values(70,  '日数据上报类型',  '92条规约',  		1,	  '')



insert diction values(72,  '无线网络在线方式',  'Real Time Online(Fixed IP/Send heart)实时在线(IP固定/发心跳)',		 0,	 '')
insert diction values(72,  '无线网络在线方式',  'Real Time Online(Fixed IP/Send no heart)实时在线(IP固定/)',	 1,	 '')
insert diction values(72,  '无线网络在线方式',  'Real Time Online(Fixed IP/Send heart)实时在线(IP不固定/发心跳)',	 2,	 '')
insert diction values(72,  '无线网络在线方式',  'Real Time Online(Fixed IP/Send no heart)永久在线(IP固定/不发心跳)',	 3,	 '')
go

insert diction values(73,  '无线回令标志',  '不回令',  0,	'')
insert diction values(73,  '无线回令标志',  '回令',		1,	'')
go

insert diction values(74,  '设置标志',  '未设置',  0,  '')
insert diction values(74,  '设置标志',  '设置',		1,	'')
go

insert diction values(75,  '加减标志',  '减(-)',		0,	'')
insert diction values(75,  '加减标志',  '加(+)',		1,	'')
go

insert diction values(76,  '线路使用属性',  '进线',		0,	'')
insert diction values(76,  '线路使用属性',  '出线',		1,	'')
insert diction values(76,  '线路使用属性',  '联络线',  2,  '')
go

insert diction values(77,  '监测类型',  '不监测',	0,	'')
insert diction values(77,  '监测类型',  '监测',	  1,	'')
go

insert diction values(78,  '任务优先级别',  '普通',		  0,	'')
insert diction values(78,  '任务优先级别',  '中级',		  1,	'')
insert diction values(78,  '任务优先级别',  '最高优先',  2,  '')
go

insert diction values(79,  '无功标志',  '有功',	0,	'')
insert diction values(79,  '无功标志',  '无功',	1,	'')
go



insert diction values(82,  '最大回溯时间',  '未设置',  0,	 '')
insert diction values(82,  '最大回溯时间',  '1天',		  1,	 '')
insert diction values(82,  '最大回溯时间',  '2天',		  2,	 '')
insert diction values(82,  '最大回溯时间',  '3天',		  3,	 '')
insert diction values(82,  '最大回溯时间',  '4天',		  4,	 '')
insert diction values(82,  '最大回溯时间',  '5天',		  5,	 '')
insert diction values(82,  '最大回溯时间',  '6天',		  6,	 '')
insert diction values(82,  '最大回溯时间',  '7天',		  7,	 '')
insert diction values(82,  '最大回溯时间',  '8天',		  8,	 '')
insert diction values(82,  '最大回溯时间',  '9天',		  9,	 '')
insert diction values(82,  '最大回溯时间',  '10天',		10,	 '')
insert diction values(82,  '最大回溯时间',  '11天',		11,	 '')
insert diction values(82,  '最大回溯时间',  '12天',		12,	 '')
insert diction values(82,  '最大回溯时间',  '13天',		13,	 '')
insert diction values(82,  '最大回溯时间',  '14天',	  14,	 '')
insert diction values(82,  '最大回溯时间',  '15天',    15,	 '')
go

insert diction values(83,  '线损及平衡类型',  '线损',	    0,	'')
insert diction values(83,  '线损及平衡类型',  '母线平衡',	1,	'')
insert diction values(83,  '线损及平衡类型',  '主变损耗',	2,	'')
go

insert diction values(84,  '报警转发类型',  '负荷恢复',		          0,	 '')
insert diction values(84,  '报警转发类型',  '负荷高于限值',		      1,	 '')
insert diction values(84,  '报警转发类型',  '负荷低于限值',		      2,	 '')
insert diction values(84,  '报警转发类型',  '负荷高于断电控',	      3,   '')
insert diction values(84,  '报警转发类型',  '设备全失压',		        4,	 '')
insert diction values(84,  '报警转发类型',  'A相失压恢复',		      5,	 '')
insert diction values(84,  '报警转发类型',  'B相失压恢复',		      6,	 '')
insert diction values(84,  '报警转发类型',  'C相失压恢复',		      7,	 '')
insert diction values(84,  '报警转发类型',  'A相失压',		          8,	 '')
insert diction values(84,  '报警转发类型',  'B相失压',		          9,	 '')
insert diction values(84,  '报警转发类型',  'C相失压',		          10,	 '')
insert diction values(84,  '报警转发类型',  '电压低于限值',		      11,	 '')
insert diction values(84,  '报警转发类型',  '电压高于限值',		      12,	 '')
insert diction values(84,  '报警转发类型',  '电压恢复',		          13,	 '')
insert diction values(84,  '报警转发类型',  '计量箱门被打开',	      14,	 '')
insert diction values(84,  '报警转发类型',  '设备断电',		          15,	 '')
insert diction values(84,  '报警转发类型',  '设备上电',		          16,	 '')
insert diction values(84,  '报警转发类型',  '电流不平衡恢复',	      17,	 '')
insert diction values(84,  '报警转发类型',  '电流不平衡',		        18,	 '')
insert diction values(84,  '报警转发类型',  '手抄器修改预付费数据', 19,	 '')
insert diction values(84,  '报警转发类型',  '帐户余额不足',		      20,	 '')
insert diction values(84,  '报警转发类型',  '电量用尽延时断电',	    21,  '')
insert diction values(84,  '报警转发类型',  '欠费断电',		          22,  '')
insert diction values(84,  '报警转发类型',  '上报日数据',		        23,	 '')


insert diction values(84,  '报警转发类型',  '电表电压越限',		      30,	 '')
insert diction values(84,  '报警转发类型',  '电表电压恢复',		      31,	 '')
insert diction values(84,  '报警转发类型',  '电表电流越限',		      32,	 '')
insert diction values(84,  '报警转发类型',  '电表电流恢复',		      33,	 '')
insert diction values(84,  '报警转发类型',  '电表通讯超时',		      34,	 '')
insert diction values(84,  '报警转发类型',  '母线不平衡越限',	      35,	 '')
insert diction values(84,  '报警转发类型',  '母线不平衡恢复',	      36,	 '')
insert diction values(84,  '报警转发类型',  '电量汇总越限',		      37,	 '')
insert diction values(84,  '报警转发类型',  '电量汇总恢复',		      38,  '')
go

insert diction values(84,  '报警转发类型',  '数据初始化和版本变更记录',		  		100,  '')
insert diction values(84,  '报警转发类型',  '参数丢失记录',		      						101,  '')
insert diction values(84,  '报警转发类型',  '参数变更记录',		      						102,  '')
insert diction values(84,  '报警转发类型',  '状态量变位记录',		      					103,  '')
insert diction values(84,  '报警转发类型',  '遥控跳闸记录',		      						104,  '')
insert diction values(84,  '报警转发类型',  '功控跳闸记录',		      						105,  '')
insert diction values(84,  '报警转发类型',  '电控跳闸记录',		      						106,  '')
insert diction values(84,  '报警转发类型',  '电能表参数变更',		      					107,  '')
insert diction values(84,  '报警转发类型',  '电流回路异常',		      						108,  '')
insert diction values(84,  '报警转发类型',  '电压回路异常',		      						109,  '')
insert diction values(84,  '报警转发类型',  '相序异常',		      								110,  '')
insert diction values(84,  '报警转发类型',  '电能表时间超差',		      					111,  '')
insert diction values(84,  '报警转发类型',  '电能表故障信息',		      					112,  '')
insert diction values(84,  '报警转发类型',  '终端停/上电事件',		      				113,  '')
insert diction values(84,  '报警转发类型',  '谐波越限告警',		      						114,  '')
insert diction values(84,  '报警转发类型',  '直流模拟量越限记录',		      			115,  '')
insert diction values(84,  '报警转发类型',  '电压/电流不平衡越限',		      		116,  '')
insert diction values(84,  '报警转发类型',  '电容器投切自锁记录',		      			117,  '')
insert diction values(84,  '报警转发类型',  '购电参数设置记录',		      				118,  '')
insert diction values(84,  '报警转发类型',  '消息认证错误记录',		      				119,  '')
insert diction values(84,  '报警转发类型',  '终端故障记录',		      						120,  '')
insert diction values(84,  '报警转发类型',  '有功总电能量差动越限事件记录',			121,  '')
insert diction values(84,  '报警转发类型',  '备用',		      										122,  '')
insert diction values(84,  '报警转发类型',  '电压越限记录',		      						123,  '')
insert diction values(84,  '报警转发类型',  '电流越限记录',		      						124,  '')
insert diction values(84,  '报警转发类型',  '视在功率越限记录',		      				125,  '')
insert diction values(84,  '报警转发类型',  '电能表示度下降记录',		      			126,  '')
insert diction values(84,  '报警转发类型',  '电能量超差记录',		      					127,  '')
insert diction values(84,  '报警转发类型',  '电能表飞走记录',		      					128,  '')
insert diction values(84,  '报警转发类型',  '电能表停走记录',		      					129,  '')
insert diction values(84,  '报警转发类型',  '485抄表失败事件记录',		      		130,  '')
insert diction values(84,  '报警转发类型',  '终端与主站通信流量超门限事件记录',	131,  '')
insert diction values(84,  '报警转发类型',  '电能表运行状态字变位事件记录',		  132,  '')
insert diction values(84,  '报警转发类型',  'CT异常',		      									133,  '')
insert diction values(84,  '报警转发类型',  '发现未知电表',		      						134,  '')
go


insert diction values(85,  '母线平衡及线损分项类型',  '总表',	0,	'')
insert diction values(85,  '母线平衡及线损分项类型',  '分表',	1,	'')
go

insert diction values(86,  '换表换倍率类型',  '更换电表',	   	  0,	 '')
insert diction values(86,  '换表换倍率类型',  '更换CT',	     	  1,	 '')
insert diction values(86,  '换表换倍率类型',  '更换电表/CT', 	  2,	 '')
insert diction values(86,  '换表换倍率类型',  '更换PT',	     	  3,	 '')
insert diction values(86,  '换表换倍率类型',  '更换电表/PT', 	  4,	 '')
insert diction values(86,  '换表换倍率类型',  '更换PT/CT', 	 	  5,	 '')
insert diction values(86,  '换表换倍率类型',  '更换电表/PT/CT', 6,	 '')
go

insert diction values(87,  '电表系数',  '*1',	    0,	 '')
insert diction values(87,  '电表系数',  '*10',	    1,	 '')
insert diction values(87,  '电表系数',  '*100',	  2,	 '')
insert diction values(87,  '电表系数',  '*1000',	  3,	 '')
insert diction values(87,  '电表系数',  '*0.1',	  -1,	 '')
insert diction values(87,  '电表系数',  '*0.01',	  -2,	 '')
insert diction values(87,  '电表系数',  '*0.001',  -3,	 '')
go

insert diction values(88,  '保护类型',  '科林9211A',	    1,	 '')
insert diction values(88,  '保护类型',  '科林9241A',	    2,	 '')
go

insert diction values(89,  '屏蔽状态',  '屏蔽',	    0,	 '')
insert diction values(89,  '屏蔽状态',  '开启',	    1,	 '')
go


insert diction values(90,  '受控标志',  '不受控',   0,	 '')
insert diction values(90,  '受控标志',  '受控',	    1,	 '')
go

insert diction values(91,  '短信发送结果',  '初始',   0,	 '')
insert diction values(91,  '短信发送结果',  '等待', 	1,	 '')
insert diction values(91,  '短信发送结果',  '成功',   2,	 '')
insert diction values(91,  '短信发送结果',  '失败',   3,	 '')
insert diction values(91,  '短信发送结果',  '成功等待回执',   4,	 '')
insert diction values(91,  '短信发送结果',  '取消',   100, '')
go



insert diction values(101,	'计量点方案数据类型',	'102总电量',						200, 	'')
insert diction values(101,	'计量点方案数据类型',	'102费率电量',					211, 	'')	
insert diction values(101,	'计量点方案数据类型',	'102瞬时量',						212, 	'')
insert diction values(101,	'计量点方案数据类型',	'102最大需量',					213, 	'')
insert diction values(101,	'计量点方案数据类型',	'102事项',							214, 	'')
insert diction values(101,	'计量点方案数据类型',	'102对时',							215, 	'')
insert diction values(101,	'计量点方案数据类型',	'DL719分钟电量',				220, 	'')
insert diction values(101,	'计量点方案数据类型',	'DL719分钟曲线',				221, 	'')
insert diction values(101,	'计量点方案数据类型',	'DL719数据透传',				222, '')
go


insert diction values(102,	'总电量计量点',	'正向有功总',   	0,	'')
insert diction values(102,	'总电量计量点',	'反向有功总',   	1,	'')
insert diction values(102,	'总电量计量点',	'正向无功总',			2,	'')
insert diction values(102,	'总电量计量点',	'反向无功总',			3,	'')
insert diction values(102,	'总电量计量点',	'I相限无功总',   	4,	'')
insert diction values(102,	'总电量计量点',	'II相限无功总',  	5,	'')
insert diction values(102,	'总电量计量点',	'III相限无功总', 	6,	'')
insert diction values(102,	'总电量计量点',	'IV相限无功总',  	7,	'')

go

insert diction values(103,	'费率电量计量点','正向有功总',	0,	'')
insert diction values(103,	'费率电量计量点','正向有功尖',	1,	'')
insert diction values(103,	'费率电量计量点','正向有功峰',	2,	'')
insert diction values(103,	'费率电量计量点','正向有功平',	3,	'')
insert diction values(103,	'费率电量计量点','正向有功谷',	4,	'')
insert diction values(103,	'费率电量计量点','反向有功总',	5,	'')
insert diction values(103,	'费率电量计量点','反向有功尖',	6,	'')
insert diction values(103,	'费率电量计量点','反向有功峰',	7,	'')
insert diction values(103,	'费率电量计量点','反向有功平',	8,	'')
insert diction values(103,	'费率电量计量点','反向有功谷',	9,	'')
insert diction values(103,	'费率电量计量点','正向无功总',	10,	'')
insert diction values(103,	'费率电量计量点','正向无功尖',	11,	'')
insert diction values(103,	'费率电量计量点','正向无功峰',	12,	'')
insert diction values(103,	'费率电量计量点','正向无功平',	13,	'')
insert diction values(103,	'费率电量计量点','正向无功谷',	14,	'')
insert diction values(103,	'费率电量计量点','反向无功总',	15,	'')
insert diction values(103,	'费率电量计量点','反向无功尖',	16,	'')
insert diction values(103,	'费率电量计量点','反向无功峰',	17,	'')
insert diction values(103,	'费率电量计量点','反向无功平',	18,	'')
insert diction values(103,	'费率电量计量点','反向无功谷',	19,	'')
go

insert diction values(104,	'瞬时量计量点','Ua(Uab)',	0,	'')
insert diction values(104,	'瞬时量计量点','Ub(Ubc)',	1,	'')
insert diction values(104,	'瞬时量计量点','Uc(Uca)',	2,	'')
insert diction values(104,	'瞬时量计量点','Ia',			3,	'')
insert diction values(104,	'瞬时量计量点','Ib',			4,	'')
insert diction values(104,	'瞬时量计量点','Ic',			5,	'')
insert diction values(104,	'瞬时量计量点','P',				6,	'')
insert diction values(104,	'瞬时量计量点','Q',				7,	'')
insert diction values(104,	'瞬时量计量点','Cosf',		8,	'')
insert diction values(104,	'瞬时量计量点','Pa',			9,	'')
insert diction values(104,	'瞬时量计量点','Pb',			10,	'')
insert diction values(104,	'瞬时量计量点','Pc',			11,	'')
insert diction values(104,	'瞬时量计量点','Qa',			12,	'')
insert diction values(104,	'瞬时量计量点','Qb',			13,	'')
insert diction values(104,	'瞬时量计量点','Qc',			14,	'')
insert diction values(104,	'瞬时量计量点','CosfA',		15,	'')
insert diction values(104,	'瞬时量计量点','CosfB',		16,	'')
insert diction values(104,	'瞬时量计量点','CosfC',		17,	'')
insert diction values(104,	'瞬时量计量点','Hz',			18,	'')
go

insert diction values(105,	'需量计量点','正向有功总',	0,	'')
insert diction values(105,	'需量计量点','正向有功尖',	1,	'')
insert diction values(105,	'需量计量点','正向有功峰',	2,	'')
insert diction values(105,	'需量计量点','正向有功平',	3,	'')
insert diction values(105,	'需量计量点','正向有功谷',	4,	'')
insert diction values(105,	'需量计量点','反向有功总',	5,	'')
insert diction values(105,	'需量计量点','反向有功尖',	6,	'')
insert diction values(105,	'需量计量点','反向有功峰',	7,	'')
insert diction values(105,	'需量计量点','反向有功平',	8,	'')
insert diction values(105,	'需量计量点','反向有功谷',	9,	'')
insert diction values(105,	'需量计量点','正向无功总',	10,	'')
insert diction values(105,	'需量计量点','正向无功尖',	11,	'')
insert diction values(105,	'需量计量点','正向无功峰',	12,	'')
insert diction values(105,	'需量计量点','正向无功平',	13,	'')
insert diction values(105,	'需量计量点','正向无功谷',	14,	'')
insert diction values(105,	'需量计量点','反向无功总',	15,	'')
insert diction values(105,	'需量计量点','反向无功尖',	16,	'')
insert diction values(105,	'需量计量点','反向无功峰',	17,	'')
insert diction values(105,	'需量计量点','反向无功平',	18,	'')
insert diction values(105,	'需量计量点','反向无功谷',	19,	'')
go

insert diction values(106,	'DL719分钟电量','正向有功总',	0,	'')
insert diction values(106,	'DL719分钟电量','反向有功总',	1,	'')
insert diction values(106,	'DL719分钟电量','一相限无功',	2,	'')
insert diction values(106,	'DL719分钟电量','二相限无功',	3,	'')
insert diction values(106,	'DL719分钟电量','三相限无功',	4,	'')
insert diction values(106,	'DL719分钟电量','四相限无功',	5,	'')
insert diction values(106,	'DL719分钟电量','正向无功总',	6,	'')
insert diction values(106,	'DL719分钟电量','反向无功总',	7,	'')
go

insert diction values(107,	'DL719分钟曲线','正向有功总',		0,	'')
insert diction values(107,	'DL719分钟曲线','正向有功尖值',	1,	'')
insert diction values(107,	'DL719分钟曲线','正向有功峰值',	2,	'')
insert diction values(107,	'DL719分钟曲线','正向有功平值',	3,	'')
insert diction values(107,	'DL719分钟曲线','正向有功谷值',	4,	'')
insert diction values(107,	'DL719分钟曲线','反向有功总',		5,	'')
insert diction values(107,	'DL719分钟曲线','反向有功尖值',	6,	'')
insert diction values(107,	'DL719分钟曲线','反向有功峰值',	7,	'')
insert diction values(107,	'DL719分钟曲线','反向有功平值',	8,	'')
insert diction values(107,	'DL719分钟曲线','反向有功谷值',	9,	'')
insert diction values(107,	'DL719分钟曲线','第一象限无功',	10,	'')
insert diction values(107,	'DL719分钟曲线','第二象限无功',	11,	'')
insert diction values(107,	'DL719分钟曲线','第三象限无功',	12,	'')
insert diction values(107,	'DL719分钟曲线','第四象限无功',	13,	'')
insert diction values(107,	'DL719分钟曲线','正向有功最大需量',	14,	'')
insert diction values(107,	'DL719分钟曲线','有功功率总',		15,	'')
insert diction values(107,	'DL719分钟曲线','A相电流',			16,	'')
insert diction values(107,	'DL719分钟曲线','B相电流',			17,	'')
insert diction values(107,	'DL719分钟曲线','C相电流',			18,	'')
insert diction values(107,	'DL719分钟曲线','A相电压',			19,	'')
insert diction values(107,	'DL719分钟曲线','B相电压',			20,	'')
insert diction values(107,	'DL719分钟曲线','C相电压',			21,	'')

insert diction values(108,	'DL719数据透传','正向有功尖值',		0,	'')
insert diction values(108,	'DL719数据透传','正向有功峰值',		1,	'')
insert diction values(108,	'DL719数据透传','正向有功平值',		2,	'')
insert diction values(108,	'DL719数据透传','正向有功谷值',		3,	'')
insert diction values(108,	'DL719数据透传','反向有功尖值',		4,	'')
insert diction values(108,	'DL719数据透传','反向有功峰值',		5,	'')
insert diction values(108,	'DL719数据透传','反向有功平值',		6,	'')
insert diction values(108,	'DL719数据透传','反向有功谷值',		7,	'')
insert diction values(108,	'DL719数据透传','第一象限无功',		8,	'')
insert diction values(108,	'DL719数据透传','第二象限无功',		9,	'')
insert diction values(108,	'DL719数据透传','第三象限无功',		10,	'')
insert diction values(108,	'DL719数据透传','第四象限无功',		11,	'')
insert diction values(108,	'DL719数据透传','正向有功最大需量', 12,	'')
insert diction values(108,	'DL719数据透传','正向有功需量时间', 13,	'')
insert diction values(108,	'DL719数据透传','A相电流',				14,	'')
insert diction values(108,	'DL719数据透传','B相电流',				15,	'')
insert diction values(108,	'DL719数据透传','C相电流',				16,	'')
insert diction values(108,	'DL719数据透传','A相电压',				17,	'')
insert diction values(108,	'DL719数据透传','B相电压',				18,	'')
insert diction values(108,	'DL719数据透传','C相电压',				19,	'')
insert diction values(108,	'DL719数据透传','功率因数',				20,	'')
insert diction values(108,	'DL719数据透传','有功功率总',			21,	'')
go


insert diction  values(109,	'数据类型个数','1个',	1,  '')
insert diction  values(109,	'数据类型个数','2个',	2,  '')
insert diction  values(109,	'数据类型个数','3个',	3,  '')
insert diction  values(109,	'数据类型个数','4个',	4,  '')	
insert diction  values(109,	'数据类型个数','5个',	5,  '')
insert diction  values(109,	'数据类型个数','6个',	6,  '')
insert diction  values(109,	'数据类型个数','7个',	7,  '')
insert diction  values(109,	'数据类型个数','8个',	8,  '')
go

insert diction values(110,	'无功方案','一四正二三反',	0,	'')
insert diction values(110,	'无功方案','一相正四相反',	1,	'')

go

insert diction values(111,	'DL719BUF','缓存区1',	0,	'')
insert diction values(111,	'DL719BUF','缓存区2',	1,	'')
insert diction values(111,	'DL719BUF','缓存区3',	2,	'')

go

insert diction values(112,	'积分周期','积分周期1',	0,	'')
insert diction values(112,	'积分周期','积分周期2',	1,	'')
insert diction values(112,	'积分周期','积分周期3',	2,	'')

go



insert diction values(113,	'启用标志','不启用',			0,'')
insert diction values(113,	'启用标志',' 启用',				1,'')
go

insert diction values(118,	'中继方式','自动中继',			0,'')
insert diction values(118,	'中继方式','手动中继',			1,'')
go


insert diction values(124,  '晓程抄表次数',  			'不限次数', 0,  '')
insert diction values(124,  '晓程抄表次数',  			'1次',  		1,  '')
insert diction values(124,  '晓程抄表次数',  			'2次',  		2,  '')
insert diction values(124,  '晓程抄表次数',  			'3次',  		3,  '')
insert diction values(124,  '晓程抄表次数',  			'4次',  		4,  '')
insert diction values(124,  '晓程抄表次数',  			'5次',  		5,  '')
insert diction values(124,  '晓程抄表次数',  			'6次',  		6,  '')
insert diction values(124,  '晓程抄表次数',  			'7次',  		7,  '')
insert diction values(124,  '晓程抄表次数',  			'8次',  		8,  '')
insert diction values(124,  '晓程抄表次数',  			'9次',  		9,  '')
insert diction values(124,  '晓程抄表次数',  			'10次',  		10, '')
insert diction values(124,  '晓程抄表次数',  			'11次',  		11, '')
insert diction values(124,  '晓程抄表次数',  			'12次',  		12, '')
insert diction values(124,  '晓程抄表次数',  			'24次',  		13, '')
insert diction values(124,  '晓程抄表次数',  			'48次',  		14, '')

insert diction values(125,  '采集状态',  					'无数据', 	0,  '')
insert diction values(125,  '采集状态',  					'未冻结', 	1,  '')
insert diction values(125,  '采集状态',  					'上报', 		2,  '')
insert diction values(125,  '采集状态',  					'召测', 		3,  '')

insert diction values(130,  '通道通讯状态',  			'无', 			0,  '')
insert diction values(130,  '通道通讯状态',  			'正常', 		1,  '')
insert diction values(130,  '通道通讯状态',  			'异常', 		2,  '')

insert diction values(135,  '终端通讯状态',  			'无', 			0,  '')
insert diction values(135,  '终端通讯状态',  			'正常', 		1,  '')
insert diction values(135,  '终端通讯状态',  			'未知', 		2,  '')
insert diction values(135,  '终端通讯状态',  			'不在线', 	3,  '')

insert diction values(138,  '终端任务状态',  			'空闲', 		0,  '')
insert diction values(138,  '终端任务状态',  			'自动任务',	1,  '')
insert diction values(138,  '终端任务状态',  			'手工任务',	2,  '')




insert diction values(155,  '告警延时时间',  '立即跳闸',	0,	  '')
insert diction values(155,  '告警延时时间',  '1分钟',			1,	  '')
insert diction values(155,  '告警延时时间',  '2分钟',			2,	  '')
insert diction values(155,  '告警延时时间',  '5分钟',			5,	  '')
insert diction values(155,  '告警延时时间',  '10分钟',		10,	  '')
insert diction values(155,  '告警延时时间',  '15分钟',		15,	  '')
go

insert diction values(156,  '限电时间',  '紧急限电',	0,	  '')
insert diction values(156,  '限电时间',  '半小时',		1,	  '')
insert diction values(156,  '限电时间',  '1小时',			2,	  '')
insert diction values(156,  '限电时间',  '2小时',			4,	  '')
insert diction values(156,  '限电时间',  '3小时',			6,	  '')
insert diction values(156,  '限电时间',  '5小时',			10,	  '')
insert diction values(156,  '限电时间',  '7小时',			14,	  '')
go

insert diction values(157,  '保电持续时间',  '长期保电',	0,	  '')
insert diction values(157,  '保电持续时间',  '半小时',		1,	  '')
insert diction values(157,  '保电持续时间',  '1小时',			2,	  '')
insert diction values(157,  '保电持续时间',  '2小时',			4,	  '')
insert diction values(157,  '保电持续时间',  '5小时',			10,	  '')
insert diction values(157,  '保电持续时间',  '10小时',		20,	  '')
insert diction values(157,  '保电持续时间',  '15小时',		30,	  '')
insert diction values(157,  '保电持续时间',  '20小时',		40,	  '')
insert diction values(157,  '保电持续时间',  '24小时',		48,	  '')
go
insert diction values(158,  '无通讯时间',  '不保电',	0,	  '')
insert diction values(158,  '无通讯时间',  '1小时',		1,	  '')
insert diction values(158,  '无通讯时间',  '2小时',		2,	  '')
insert diction values(158,  '无通讯时间',  '3小时',		3,	  '')
insert diction values(158,  '无通讯时间',  '5小时',		5,	  '')
insert diction values(158,  '无通讯时间',  '10小时',	10,	  '')
insert diction values(158,  '无通讯时间',  '15小时',	15,	  '')
insert diction values(158,  '无通讯时间',  '20小时',	20,	  '')
insert diction values(158,  '无通讯时间',  '30小时',	30,	  '')
insert diction values(158,  '无通讯时间',  '50小时',	50,	  '')
insert diction values(158,  '无通讯时间',  '80小时',	80,	  '')
insert diction values(158,  '无通讯时间',  '100小时',	100,	  '')
insert diction values(158,  '无通讯时间',  '150小时',	150,	  '')
insert diction values(158,  '无通讯时间',  '200小时',	200,	  '')
insert diction values(158,  '无通讯时间',  '250小时',	250,	  '')
go




insert diction values(160,  '控制状态',	'设置',		0,	'')
insert diction values(160,  '控制状态',	'回令',		1,	'')

insert diction values(161,  '功率因数标准',	'90%',		0,	'')
insert diction values(161,  '功率因数标准',	'85%',		1,	'')
insert diction values(161,  '功率因数标准',	'80%',		2,	'')
go

insert diction values(162,  '功率计算参与标志',	'不参与计算',		0,	'')
insert diction values(162,  '功率计算参与标志',	'参与计算',			1,	'')
go


insert diction values(163,  '缴费方式',	'SmartCard/Token',			0,	'')
insert diction values(163,  '缴费方式',	'Remote Payment',		1,	'')
insert diction values(163,  '缴费方式',	'Master Station Payment',		2,	'')
go


insert diction values(164,  '终端上行通信模式05fk',  '永久在线',	1,	  '')
insert diction values(164,  '终端上行通信模式05fk',  '被动激活',  2,	  '')
go

insert diction values(165,  'KE485工作方式',  '主动召测',	0,	  '')
insert diction values(165,  'KE485工作方式',  '被动侦听', 85,	  '')
go


insert diction values(166,  'KE变电站通讯规约',  '科林规约',	0,	  '')
insert diction values(166,  'KE变电站通讯规约',  'CDT规约',		1,	  '')
insert diction values(166,  'KE变电站通讯规约',  '102规约',		2,	  '')
go



insert diction values(168,  '主测点表标志',  '主测点',  0,	 '')
insert diction values(168,  '主测点表标志',  '副测点',  1,	 '')
go


insert diction values(169,  '事项等级',  		'一般事项',  0,	 '')
insert diction values(169,  '事项等级',  		'重要事项',  50, '')
go

insert diction values(170,  '用电类别',  		'无设置',  				0,	 '')
insert diction values(170,  '用电类别',  		'居民生活用电',  	100, '')
insert diction values(170,  '用电类别',  		'大工业用电',  		200, '')
insert diction values(170,  '用电类别',  		'一般工商业用电', 300, '')
insert diction values(170,  '用电类别',  		'非工业用电',  		400, '')
insert diction values(170,  '用电类别',  		'农业生产用电',  	500, '')
go


insert diction values(171,  '执行标志',  		'不执行',  	0,	 '')
insert diction values(171,  '执行标志',  		'执行',  		1,	 '')
go


insert diction values(172,  '力调奖罚标志',  		'执行奖罚',  0,	 '')
insert diction values(172,  '力调奖罚标志',  		'只罚不奖',  1,	 '')
go

insert diction values(173,  '报停标志',  		'正常用电',  0,	 '')
insert diction values(173,  '报停标志',  		'报停',  		 1,	 '')
go








/****************任务参数******************/
insert diction values(500,  '任务数据类型',						'实时数据',					0,	'')
insert diction values(500,  '任务数据类型',						'分钟数据',					1,	'')
insert diction values(500,  '任务数据类型',						'日数据',						2,	'')
insert diction values(500,  '任务数据类型',						'月数据',						3,	'')

insert diction values(501,  '任务执行周期',						'分钟',							0,	'')
insert diction values(501,  '任务执行周期',						'日',								1,	'')
insert diction values(501,  '任务执行周期',						'月',								2,	'')
insert diction values(501,  '任务执行周期',						'抄表日',						3,	'')

insert diction values(502,  '任务执行类型',						'主动上报',					0,	'')
insert diction values(502,  '任务执行类型',						'主动召测',					1,	'')

insert diction values(505,  '任务执行截止时间类型',		'当前时间',					0,	'')
insert diction values(505,  '任务执行截止时间类型',		'上一日',						1,	'')

insert diction values(508,  '任务对象范围',						'单个点',					  	0,	'')
insert diction values(508,  '任务对象范围',						'所有测量点',					1,	'')
insert diction values(508,  '任务对象范围',						'所有总加组',					2,	'')
insert diction values(508,  '任务对象范围',						'所有电表计量点',			3,	'')
insert diction values(508,  '任务对象范围',						'所有普通用户',				4,	'')
insert diction values(508,  '任务对象范围',						'所有重点用户',				5,	'')
insert diction values(508,  '任务对象范围',						'所有总表',						6,	'')
insert diction values(508,  '任务对象范围',						'所有重点用户和总表', 7,	'')

insert diction values(510,  '单个点',  								'终端',	     				0,	 '')
insert diction values(510,  '单个点',  								'测量点',	    			1,	 '')
insert diction values(510,  '单个点',  								'总加组',	    			2,	 '')

go
insert diction values(511,  '所有测量点',  						'测量点',	  				1,	 '')
insert diction values(512,  '所有总加组',  						'总加组',	  				2,	 '')
insert diction values(513,  '所有电表计量点',  				'测量点',	  				1,	 '')
insert diction values(514,  '所有普通用户',  					'测量点',	  				1,	 '')
insert diction values(515,  '所有重点用户',  					'测量点',	  				1,	 '')
insert diction values(516,  '所有总表',  							'测量点',	  				1,	 '')
insert diction values(517,  '所有重点用户和总表', 		'测量点',	 			 		1,	 '')
go

insert diction values(550,  '计算任务执行单位',				'分钟(周期执行)',		0,	'')
insert diction values(550,  '计算任务执行单位',				'日(定时执行)',			1,	'')
insert diction values(550,  '计算任务执行单位',				'月(定时执行)',			2,	'')
go


insert diction values(551,  '计算任务执行截止时间',		'距当前一个周期',		1,	'')
insert diction values(551,  '计算任务执行截止时间',		'距当前两个周期',		2,	'')
insert diction values(551,  '计算任务执行截止时间',		'距当前三个周期',		3,	'')
insert diction values(551,  '计算任务执行截止时间',		'距当前四个周期',		4,	'')
insert diction values(551,  '计算任务执行截止时间',		'距当前五个周期',		5,	'')
go






/*******************事项******************/
insert diction values(600,  '系统事项',  							'全部事项'						 ,0,	 '')
insert diction values(600,  '系统事项',  							'系统运行维护事项'		 ,1,	 '')
insert diction values(600,  '系统事项',  							'前置机事件'					 ,2,	 '')
go


insert diction values(601,  '终端事项',  							'全部事项'						 ,0,	 '')
insert diction values(601,  '终端事项',  							'终端运行事项'				 ,3,	 '')
insert diction values(601,  '终端事项',  							'通讯事项'						 ,4,	 '')
insert diction values(601,  '终端事项',  							'电表事项'						 ,5,	 '')
insert diction values(601,  '终端事项',  							'功率事项'						 ,6,	 '')
insert diction values(601,  '终端事项',  							'电压事项'						 ,7,	 '')
insert diction values(601,  '终端事项',  							'电流事项'						 ,8,	 '')
insert diction values(601,  '终端事项',  							'电容事项'						 ,9,	 '')
insert diction values(601,  '终端事项',  							'参数修改事项'				 ,10,	 '')
insert diction values(601,  '终端事项',  							'控制事项'						 ,11,	 '')
insert diction values(601,  '终端事项',  							'预付费事项'					 ,12,	 '')
go


/****************电表类型******************/
insert diction values(620,  '科林大用户表类型',  			'威胜表',								1,	   '1')                
insert diction values(620,  '科林大用户表类型',  			'485模块',							2,	   '2')
insert diction values(620,  '科林大用户表类型',  			'部颁表',								3,	   '3')
insert diction values(620,  '科林大用户表类型',  			'恒通表',								4,	   '4')
insert diction values(620,  '科林大用户表类型',  			'威胜表反向',						5,	   '5')
insert diction values(620,  '科林大用户表类型',  			'部颁表反向',						6,	   '6')
insert diction values(620,  '科林大用户表类型',  			'DTSF566型A相(正向值)'	,7,	 	 '7')
insert diction values(620,  '科林大用户表类型',  			'DTSF566型B相(正向值)'	,8,	 	 '8')  
insert diction values(620,  '科林大用户表类型',  			'DTSF566型C相(正向值)'	,9,	 	 '9')    
insert diction values(620,  '科林大用户表类型',  			'DTSF566型A相(反向值)'	,10,	 'A')    
insert diction values(620,  '科林大用户表类型',  			'DTSF566型B相(反向值)'	,11,	 'B')    
insert diction values(620,  '科林大用户表类型',  			'DTSF566型C相(反向值)'	,12,	 'C')    
insert diction values(620,  '科林大用户表类型',  			'DTSF566型正向和'		 		,13,	 'D')
insert diction values(620,  '科林大用户表类型',  			'DTSF566型反向和'		 		,14,	 'E')       
insert diction values(620,  '科林大用户表类型',  			'林洋A相(正向值)'		 		,15,	 'F')       
insert diction values(620,  '科林大用户表类型',  			'林洋B相(正向值)'		 		,16,	 'G')       
insert diction values(620,  '科林大用户表类型',  			'林洋C相(正向值)'		 		,17,	 'H')       
insert diction values(620,  '科林大用户表类型',  			'林洋A相(反向值)'		 		,18,	 'I')       
insert diction values(620,  '科林大用户表类型',  			'林洋B相(反向值)'		 		,19,	 'J')       
insert diction values(620,  '科林大用户表类型',  			'林洋C相(反向值)'		 		,20,	 'K')       
insert diction values(620,  '科林大用户表类型',  			'林洋正向和'					 	,21,	 'L')            
insert diction values(620,  '科林大用户表类型',  			'林洋反向和'					 	,22,	 'M')            
insert diction values(620,  '科林大用户表类型',  			'07版部颁正向1200'		 	,23,	 'N')      
insert diction values(620,  '科林大用户表类型',  			'07版部颁反向1200'		 	,24,	 'O')      
insert diction values(620,  '科林大用户表类型',  			'菏泽正向'						 	,25,	 'P')              
insert diction values(620,  '科林大用户表类型',  			'菏泽反向'						 	,26,	 'Q')              
insert diction values(620,  '科林大用户表类型',  			'07版部颁正向2400'		 	,27,	 'R')       
insert diction values(620,  '科林大用户表类型',  			'07版部颁反向2400'		 	,28,	 'S')             
go

insert diction values(621,  '科林变电站表类型',  			'脉冲表',  							0,	   '')
insert diction values(621,  '科林变电站表类型',  			'威胜表',								1,	   '')                
insert diction values(621,  '科林变电站表类型',  			'部颁表',								3,	   '')
insert diction values(621,  '科林变电站表类型',  			'部颁表(0.001Wh)',			4,	   '')       
insert diction values(621,  '科林变电站表类型',  			'部颁表(0.1Wh)',				5,	   '')   
insert diction values(621,  '科林变电站表类型',  			'南京新宁光电表',			 	20,	 	 '')
insert diction values(621,  '科林变电站表类型',  			'变电站07版部颁'			 	,32,	 '')
go

insert diction values(622,  '晓程电表类型',  					'单相有功',							0,	   '')
insert diction values(622,  '晓程电表类型',  					'总表',		  						128,	 '')
go


insert diction values(630,  '居民电表类型',						'智能单相表'					 	,0,	   '')
insert diction values(630,  '居民电表类型',						'智能三相表'					 	,1,	   '')
insert diction values(630,  '居民电表类型',						'普通单相表'					  ,2,	   '')
insert diction values(630,  '居民电表类型',						'普通三相表'					 	,3,	 	 '')
go


insert diction values(631,  '用户大类号',						'缺省值',									0,	   '')
insert diction values(631,  '用户大类号',						'大型专变用户(A类)'	,			1,	   '')
insert diction values(631,  '用户大类号',						'中小型专变用户(B类)',		2,	   '')
insert diction values(631,  '用户大类号',						'三相一般工商业用户(C类)',3,	   '')
insert diction values(631,  '用户大类号',						'单相一般工商业用户(D类)',4,	   '')
insert diction values(631,  '用户大类号',						'居民用户(E类)',					5,	   '')
insert diction values(631,  '用户大类号',						'公用配变考核计量点(F类)',6,	   '')
go


insert diction values(632,  '用户小类号',						'缺省值',									0,	   '')
insert diction values(632,  '用户小类号',						'单相智能电表用户',				1,	   '')
insert diction values(632,  '用户小类号',						'三相智能电表用户',				2,	   '')
go

/****************间隔周期******************/

insert diction values(700,  '轮次间隔',  		'30分钟',  	1,    '')
insert diction values(700,  '轮次间隔',  		'1小时',	 	2,	  '')
insert diction values(700,  '轮次间隔',  		'2小时',	 	4,	  '')
insert diction values(700,  '轮次间隔',  		'5小时',	 	10,   '')
insert diction values(700,  '轮次间隔',  		'12小时',	 	24,	 	'')
insert diction values(700,  '轮次间隔',  		'1天',		 	48,	 	'')
insert diction values(700,  '轮次间隔',  		'2天',		 	96,	 	'')
insert diction values(700,  '轮次间隔',  		'5天',		 	240,	'')
go

insert diction values(701,  '分钟冻结间隔',  '未设置',	0,		'')
insert diction values(701,  '分钟冻结间隔',  '1分钟',	  1,	  '')
insert diction values(701,  '分钟冻结间隔',  '5分钟',	  5,	  '')
insert diction values(701,  '分钟冻结间隔',  '10分钟',	10,	  '')
insert diction values(701,  '分钟冻结间隔',  '15分钟',	15,	  '')
insert diction values(701,  '分钟冻结间隔',  '30分钟',	30,	  '')
insert diction values(701,  '分钟冻结间隔',  '1小时',	  60,	  '')
insert diction values(701,  '分钟冻结间隔',  '2小时',	  120,  '')
insert diction values(701,  '分钟冻结间隔',  '3小时',	  180,	'')
go

insert diction values(702,  '召测周期间隔',  '未设置',	0,	  '')
insert diction values(702,  '召测周期间隔',  '5分钟',	  5,	  '')
insert diction values(702,  '召测周期间隔',  '10分钟',	10,	  '')
insert diction values(702,  '召测周期间隔',  '30分钟',	30,	  '')
insert diction values(702,  '召测周期间隔',  '1小时',	  60,	  '')
insert diction values(702,  '召测周期间隔',  '2小时',	  120,	'')
insert diction values(702,  '召测周期间隔',  '3小时',	  180,	'')
insert diction values(702,  '召测周期间隔',  '4小时',	  240,	'')
insert diction values(702,  '召测周期间隔',  '5小时',	  300,	'')
insert diction values(702,  '召测周期间隔',  '6小时',	  360,  '')
go


insert diction values(703,	'晓程抄表间隔',		'半小时',				0,'')
insert diction values(703,	'晓程抄表间隔',		'1小时',				1,'')
insert diction values(703,	'晓程抄表间隔',		'2小时',				2,'')
insert diction values(703,	'晓程抄表间隔',		'4小时',				3,'')
insert diction values(703,	'晓程抄表间隔',		'6小时',				4,'')
insert diction values(703,	'晓程抄表间隔',		'12小时',				5,'')
go


insert diction values(704,  '重拨间隔',  			'未设置',			0,	  '')
insert diction values(704,  '重拨间隔',  			'15秒',				15,	  '')
insert diction values(704,  '重拨间隔',  			'30秒',				30,	  '')
insert diction values(704,  '重拨间隔',  			'1分钟',			60,	  '')
insert diction values(704,  '重拨间隔',  			'2分钟',			120,  '')
insert diction values(704,  '重拨间隔',  			'5分钟',			300,  '')
insert diction values(704,  '重拨间隔',  			'10分钟',			600,  '')
insert diction values(704,  '重拨间隔',  			'15分钟',			900,  '')
insert diction values(704,  '重拨间隔',  			'20分钟',			1200, '')
insert diction values(704,  '重拨间隔',  			'30分钟',			1800, '')
insert diction values(704,  '重拨间隔',  			'1小时',			3600, '')
insert diction values(704,  '重拨间隔',  			'2小时',			7200, '')
insert diction values(704,  '重拨间隔',  			'5小时',			18000,'')
go

insert diction values(705,  'gprs重连间隔',  	'未设置',			0,	  '')
insert diction values(705,  'gprs重连间隔',  	'1分钟',			1,	  '')
insert diction values(705,  'gprs重连间隔',  	'2分钟',			2,  	'')
insert diction values(705,  'gprs重连间隔',  	'5分钟',			5,  	'')
insert diction values(705,  'gprs重连间隔',  	'10分钟',			10,  	'')
insert diction values(705,  'gprs重连间隔',  	'15分钟',			15,  	'')
insert diction values(705,  'gprs重连间隔',  	'30分钟',			30, 	'')
insert diction values(705,  'gprs重连间隔',  	'1小时',			60, 	'')
insert diction values(705,  'gprs重连间隔',  	'2小时',			120, 	'')
insert diction values(705,  'gprs重连间隔',  	'5小时',			300,	'')
insert diction values(705,  'gprs重连间隔',  	'10小时',			600,	'')
insert diction values(705,  'gprs重连间隔',  	'15小时',			900,	'')
go

insert diction values(706,  '心跳周期',  			'无心跳',	 		0,	  '')
insert diction values(706,  '心跳周期',  			'1分钟',	 		1,	  '')
insert diction values(706,  '心跳周期',  			'2分钟',	 		2,	  '')
insert diction values(706,  '心跳周期',  			'3分钟',	 		3,	  '')
insert diction values(706,  '心跳周期',  			'4分钟',	 		4,	  '')
insert diction values(706,  '心跳周期',  			'5分钟',	 		5,	  '')
insert diction values(706,  '心跳周期',  			'10分钟',  		10,		'')
insert diction values(706,  '心跳周期',  			'15分钟',	 		15,		'')
insert diction values(706,  '心跳周期',  			'20分钟',	 		20,		'')
insert diction values(706,  '心跳周期',  			'30分钟',	 		30,		'')
insert diction values(706,  '心跳周期',  			'40分钟',	 		40,		'')
insert diction values(706,  '心跳周期',  			'60分钟',	 		60,  	'')
go

insert diction values(707,	'存储周期',				'未设置',			0,		'')
insert diction values(707,	'存储周期',				'1分钟',			1,		'')
insert diction values(707,	'存储周期',				'5分钟',			5,		'')
insert diction values(707,	'存储周期',				'10分钟',			10,		'')
insert diction values(707,	'存储周期',				'15分钟',			15,		'')
insert diction values(707,	'存储周期',				'30分钟',			30,		'')
insert diction values(707,	'存储周期',				'1小时',			60,		'')
insert diction values(707,	'存储周期',				'2小时',			120,	'')
insert diction values(707,	'存储周期',				'3小时',			180,	'')
go


insert diction values(708,  '国电抄表间隔',  	'1分钟',	 		1,	  '')
insert diction values(708,  '国电抄表间隔',  	'2分钟',	 		2,	  '')
insert diction values(708,  '国电抄表间隔',  	'3分钟',	 		3,	  '')
insert diction values(708,  '国电抄表间隔',  	'4分钟',	 		4,	  '')
insert diction values(708,  '国电抄表间隔',  	'5分钟',	 		5,	  '')
insert diction values(708,  '国电抄表间隔',  	'10分钟',  		10,		'')
insert diction values(708,  '国电抄表间隔',  	'15分钟',	 		15,		'')
insert diction values(708,  '国电抄表间隔',  	'20分钟',	 		20,		'')
insert diction values(708,  '国电抄表间隔',  	'30分钟',	 		30,		'')
insert diction values(708,  '国电抄表间隔',  	'40分钟',	 		40,		'')
insert diction values(708,  '国电抄表间隔',  	'60分钟',	 		60,  	'')
go




/****************终端通讯协议******************/
insert diction values(720,  '终端通讯协议',  					'国电负控04规约',	     		1,	'')
insert diction values(720,  '终端通讯协议',  					'国电负控05规约',	     		2,	'')
insert diction values(720,  '终端通讯协议',  					'国电376.1-2009规约',    	3,	'')
insert diction values(720,  '终端通讯协议',  					'科林大用户规约',	       	4,	'')
insert diction values(720,  '终端通讯协议',  					'科林变电站规约',	       	5,	'')
insert diction values(720,  '终端通讯协议',  					'国电376.1-2013规约',	    6,	'')

insert diction values(720,  '终端通讯协议',  					'DL/T 719规约',	  				10,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(标准)',				11,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(河北)',				12,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(南瑞)',				13,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(华立)',				14,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(透传)',				15,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(北京)',				16,	'')
insert diction values(720,  '终端通讯协议',  					'IEC102规约(东方)',				17,	'')
insert diction values(720,  '终端通讯协议',  					'WF102规约(威远102)',			18,	'')


insert diction values(720,  '终端通讯协议',  					'晓程集中器单费率规约',  	50,	'')
insert diction values(720,  '终端通讯协议',  					'晓程集中器复费率规约',	 	51,	'')
insert diction values(720,  '终端通讯协议',  					'河北南网集中器规约',	   	52,	'')

insert diction values(720,  '终端通讯协议',  					'星创GSM规约',       			60,	'')
insert diction values(720,  '终端通讯协议',  					'地方配变规约',	         	70,	'')
insert diction values(720,  '终端通讯协议',  					'地方负控规约',	         	90, '')


insert diction values(721,  '变电站终端通讯协议',  		'科林变电站规约',					5,	'')
insert diction values(721,  '变电站终端通讯协议',  		'DL/T 719规约',						10,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(标准)',				11,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(河北)',				12,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(南瑞)',				13,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(华立)',				14,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(透传)',				15,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(北京)',				16,	'')
insert diction values(721,  '变电站终端通讯协议',  		'IEC102规约(东方)',				17,	'')
insert diction values(721,  '变电站终端通讯协议',  		'WF102规约(威远102)',			18,	'')


insert diction values(722,  '专变公变终端通讯协议',  	'国电负控05规约',					2,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'国电376.1-2009规约',			3,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'科林大用户规约',					4,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'国电376.1-2013规约',	    6,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'星创GSM规约',       			60,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'地方配变规约',						70,	'')
insert diction values(722,  '专变公变终端通讯协议',  	'地方负控规约',						90,	'')


insert diction values(723,  '居民终端通讯协议',  			'376.1-2009Protocol',			3,	'')
insert diction values(723,  '居民终端通讯协议',  			'376.1-2013Protocol',	    6,	'')
--insert diction values(723,  '居民终端通讯协议',  			'晓程集中器单费率规约',		50,	'')
--insert diction values(723,  '居民终端通讯协议',  			'晓程集中器复费率规约',		51,	'')
--insert diction values(723,  '居民终端通讯协议',  			'河北南网集中器规约',			52,	'')
go

--jack add 20130130 start
insert diction values(724,  '农排终端通讯协议',  			'国电376.1-2009规约',			3,	'')
insert diction values(724,  '农排终端通讯协议',  			'国电376.1-2013规约',	    6,	'')
go
--jack add 20130130 end


/****************报警监测范畴******************/
insert diction values(1300,  '报警监测范畴',  				'无',											0,	'')
insert diction values(1300,  '报警监测范畴',  				'终端',										1,	'')

insert diction values(1300,  '报警监测范畴',  				'母线不平衡', 						20,	'')
insert diction values(1300,  '报警监测范畴',  				'线损', 									21,	'')
insert diction values(1300,  '报警监测范畴',  				'电量汇总', 							22,	'')
go

insert diction values(1301,  '报警方式',				'固定值方式',					0,	'')
insert diction values(1301,  '报警方式',				'比例方式',						1,	'')
go




/****************统计对象类型1******************/
insert diction values(1320,  '统计对象类型1',  				'变电站',									1,	'')
insert diction values(1320,  '统计对象类型1',  				'线路',										2,	'')
insert diction values(1320,  '统计对象类型1',  				'供电所',									3,	'')
insert diction values(1320,  '统计对象类型1',  				'线路负责人',							4,	'')
go

insert diction values(1321,  '统计监测间隔',  				'30分钟', 								30,		'')
insert diction values(1321,	 '统计监测间隔',					'1小时',									60,		'')
insert diction values(1321,	 '统计监测间隔',					'2小时',									120,	'')
insert diction values(1321,	 '统计监测间隔',					'3小时',									180,	'')
insert diction values(1321,	 '统计监测间隔',					'5小时',									300,	'')
insert diction values(1321,	 '统计监测间隔',					'10小时',									600,	'')
insert diction values(1321,	 '统计监测间隔',					'24小时',									1440,	'')
go


/****************科林预付费终端计算方式******************/
insert diction values(1330,  '科林预付费终端计算方式',  	'金额',								0,	'')
insert diction values(1330,  '科林预付费终端计算方式',  	'电量',								1,	'')
insert diction values(1330,  '科林预付费终端计算方式',  	'表底',								2,	'')
go



insert diction values(1340,  '控制操作',									'解除',								0,	'')
insert diction values(1340,  '控制操作',									'投入',								1,	'')
go

insert diction values(1341,  '开关操作类型',							'允许合闸',						0,	'')
insert diction values(1341,  '开关操作类型',							'遥控跳闸',						1,	'')

insert diction values(1342,  '开关状态',									'合闸',								0,	'')
insert diction values(1342,  '开关状态',									'分闸',								1,	'')


insert diction values(1343,  '分合闸状态',								'分闸',								0,	'')
insert diction values(1343,  '分合闸状态',								'合闸',								1,	'')

insert diction values(1344,  '有效标志',									'无效',								0,	'')
insert diction values(1344,  '有效标志',									'有效',								1,	'')
go



/****************开关参数******************/

insert diction values(1350,  '开关控制类型',  				'电平方式',								0,	'')
insert diction values(1350,  '开关控制类型',  				'脉冲方式',								1,	'')
go


insert diction values(1360,  '控制逻辑',  						'国电控制逻辑',						0,	'')
insert diction values(1360,  '控制逻辑',  						'科林控制逻辑',						1,	'')


/****************预付费参数******************/

insert diction values(1370,  '科林计费方式',					'金额',										0,	'')
insert diction values(1370,  '科林计费方式',  				'电量',  									1,	'')
insert diction values(1370,  '科林计费方式',  				'表底',	 									2,	'')
go



/****************SG186参数******************/
insert diction values(1400,  '导入标志',  						'不导入',									0,	'')
insert diction values(1400,  '导入标志',  						'导入',										1,	'')
go




/****************预付费参数*************************/
insert diction values(1500,  '预付费人员类型',  '预付费操作员',	 0,	 '')
insert diction values(1500,  '预付费人员类型',  '预付费监护员',	 1,	 '')
insert diction values(1500,  '预付费人员类型',  '负控操作员',		 2,	 '')
insert diction values(1500,  '预付费人员类型',  '负控监护员',		 3,	 '')
go

insert diction values(1501,  '预付费权限范围',	'All Terminals',	 0,	 '')
insert diction values(1501,  '预付费权限范围',	'Terminals of Area',		 1,	 '')
insert diction values(1501,  '预付费权限范围',	'Terminals of Manager',	 2,  '')
go

insert diction values(1502,  '预付费应用类型',	'低压集抄',	 					0, '')
insert diction values(1502,  '预付费应用类型',	'专变用户',		 				1, '')
insert diction values(1502,  '预付费应用类型',	'农业排灌',		 				2, '')
insert diction values(1502,  '预付费应用类型',	'三种应用',						50, '')
go

insert diction values(1503,  '预付费金额',			'0 kWh',	 							0, '')
insert diction values(1503,  '预付费金额',			'10 kWh',	 							10, '')
insert diction values(1503,  '预付费金额',			'20 kWh',	 							20, '')
insert diction values(1503,  '预付费金额',			'50 kWh',	 							50, '')
--insert diction values(1503,  '预付费金额',			'100元',	 						100, '')
--insert diction values(1503,  '预付费金额',			'150元',	 						150, '')
--insert diction values(1503,  '预付费金额',			'200元',	 						200, '')
--insert diction values(1503,  '预付费金额',			'300元',	 						300, '')
--insert diction values(1503,  '预付费金额',			'500元',	 						500, '')
go

--20131021 zhp修改电表名描述
insert diction values(1504,  '预付费表类型',		'KE SinglePhase Prepay-Meter',					1, '')
insert diction values(1504,  '预付费表类型',		'KE ThreePhase Prepay-Meter',					3, '')
/*
insert diction values(1504,  '预付费表类型',		'备用1自管2009版',					2, '')
insert diction values(1504,  '预付费表类型',		'02级密码透传控',						3, '')
insert diction values(1504,  '预付费表类型',		'国网2009版智能',						4, '')
insert diction values(1504,  '预付费表类型',		'国网自管户正式',						5, '')
insert diction values(1504,  '预付费表类型',		'国网自管户公钥',						6, '')
insert diction values(1504,  '预付费表类型',		'晓程表',										7, '')
insert diction values(1504,  '预付费表类型',		'04级密码透传控',						8, '')
insert diction values(1504,  '预付费表类型',		'02级密码透传控1',					9, '')
insert diction values(1504,  '预付费表类型',		'04级密码透传控1',					10, '')
insert diction values(1504,  '预付费表类型',		'02级密码透传控(97)',				11, '')
insert diction values(1504,  '预付费表类型',		'11级密码透传控)',					12, '')


insert diction values(1504,  '预付费表类型',		'通用自管2013版',						50, '')
insert diction values(1504,  '预付费表类型',		'科林自管2013版',						51, '')
insert diction values(1504,  '预付费表类型',		'备用2自管2013版',					52, '')	--//下面留着其他的使用
insert diction values(1504,  '预付费表类型',		'国网2013版智能',						58, '')
insert diction values(1504,  '预付费表类型',		'02级密码透传控2013版',			59, '')
--20140606 zkz
insert diction values(1504,  '预付费表类型',		'科林卡表',									101, '')
insert diction values(1504,  '预付费表类型',		'江机卡表',									102, '')
insert diction values(1504,  '预付费表类型',		'人民卡表',									103, '')
--20140606 zkz end
*/
go

insert diction values(1505,  '专变预付费控制方式',		'轮次控制',						0, '')
insert diction values(1505,  '专变预付费控制方式',		'02级密码控制',				1, '')
insert diction values(1505,  '专变预付费控制方式',		'通用加密机控',				2, '')
insert diction values(1505,  '专变预付费控制方式',		'国网自管户',					3, '')
insert diction values(1505,  '专变预付费控制方式',		'国网自管户(公)',			4, '')
insert diction values(1505,  '专变预付费控制方式',		'备用方式1',					5, '')
insert diction values(1505,  '专变预付费控制方式',		'备用方式2',					6, '')
go

insert diction values(1510,  '预置金额类型',		'居民预置金额30元',			30, '')
insert diction values(1510,  '预置金额类型',		'工商业预置金额100元',		100, '')
insert diction values(1510,  '预置金额类型',		'排浇预置金额200元',			200, '')
go

--jackadd 孟加拉 20150304 start
--一般工商业/居民  Industrial/Commercial/Residential
insert diction values(1520,  '费率类型',	'Tou Rate',						1, '')		--复费率
insert diction values(1520,  '费率类型',	'Step Rate',	 				2, '')		--阶梯费率
insert diction values(1520,  '费率类型',	'Single Rate',				3, '')		--单费率
go
--jackadd 孟加拉 20150304 end

--0 种类A:居民区 3 类型D:非住宅（照明&用电） 4 类型E:商业&办公室

insert diction values(1530,  '用户状态',  						'Origianl State',	 								0,	'')
insert diction values(1530,  '用户状态',  						'Normal State',	 								1,	'')
insert diction values(1530,  '用户状态',  						'Pause State',	 								49,	'')
insert diction values(1530,  '用户状态',  						'Account Cancellation State',	 								50,	'')
go

insert diction values(1540,  '预付费操作类型',  			'Origianl State',	 								0,	'')
insert diction values(1540,  '预付费操作类型',  			'Open an account',	 									1,	'')
insert diction values(1540,  '预付费操作类型',  			'Purchase Electricity',	 							2,	'')
--insert diction values(1540,  '预付费操作类型',  			'Reissued Card',	 									3,	'')
--insert diction values(1540,  '预付费操作类型',  			'补写卡',	 								4,	'')
--insert diction values(1540,  '预付费操作类型',  			'冲正',	 									5,	'')
--insert diction values(1540,  '预付费操作类型',  			'换表',	 									6,	'')
--insert diction values(1540,  '预付费操作类型',  			'换费率',	 								7,	'')
--insert diction values(1540,  '预付费操作类型',  			'换基本费',	 							8,	'')
--insert diction values(1540,  '预付费操作类型',  			'结算',	 									9,	'')
--insert diction values(1540,  '预付费操作类型',  			'恢复',	 									48,	'')
--insert diction values(1540,  '预付费操作类型',  			'暂停',	 									49,	'')
insert diction values(1540,  '预付费操作类型',  			'Account Cancellation',	 									50,	'')
go


insert diction values(1541,  '预付费操作结果',  			'初始态',	 								0,	'')
insert diction values(1541,  '预付费操作结果',  			'写卡成功',	 							1,	'')
insert diction values(1541,  '预付费操作结果',  			'远程设置成功',	 					2,	'')
insert diction values(1541,  '预付费操作结果',  			'主站操作成功',	 					3,	'')
insert diction values(1541,  '预付费操作结果',  			'暂时不处理',	 						4,	'')
insert diction values(1541,  '预付费操作结果',  			'正在操作',	 							5,	'')
insert diction values(1541,  '预付费操作结果',  			'失败',	 									6,	'')
go

insert diction values(1542,  '预付费确认状态',  			'初始态',	 								0,	'')
insert diction values(1542,  '预付费确认状态',  			'等待',	 									1,	'')
insert diction values(1542,  '预付费确认状态',  			'成功',	 									2,	'')
insert diction values(1542,  '预付费确认状态',  			'失败',	 									3,	'')
go


insert diction values(1543,  '预付费后台线程',  			'计算线程',	 							1,	'')
insert diction values(1543,  '预付费后台线程',  			'GSM报警线程',						2,	'')
insert diction values(1543,  '预付费后台线程',  			'声音报警线程',	 					3,	'')
insert diction values(1543,  '预付费后台线程',  			'通讯线程',	 							4,	'')
insert diction values(1543,  '预付费后台线程',  			'业务线程',	 							5,	'')
go


insert diction values(1544,  '抄表周期类型',  	'每月抄表',	 				0,  	'')
insert diction values(1544,  '抄表周期类型',  	'双月抄表',	 				1,  	'')
insert diction values(1544,  '抄表周期类型',  	'单月抄表',					2,	 	'')
insert diction values(1544,  '抄表周期类型',  	'季度抄表',					3,	 	'')
insert diction values(1544,  '抄表周期类型',  	'半年抄表',					4,	 	'')
insert diction values(1544,  '抄表周期类型',  	'年抄表',						5,	 	'')
go

insert diction values(1546,  '阶梯电价类型',  	'年度方案',	 				0,  	'')
insert diction values(1546,  '阶梯电价类型',  	'月度方案',	 				1,  	'')
insert diction values(1546,  '阶梯电价类型',  	'月度峰谷阶梯',	 		2,  	'')
go

insert diction values(1547,  '阶梯电价电表费率类型',  	'Single Tariff',	 		0,  	'')
insert diction values(1547,  '阶梯电价电表费率类型',  	'Poly Tariff',	 		1,  	'')
insert diction values(1547,  '阶梯电价电表费率类型',  	'Step Tariff',	 		3,  	'')		--20140123 add
go

insert diction values(1548,  '档案权限',  			'None',	 								0,  	'')
insert diction values(1548,  '档案权限',  			'Query',	 					1,  	'')
insert diction values(1548,  '档案权限',  			'Modify',	 					2,  	'')
go

--卡表类型
insert diction values(1549,  '卡表类型',  			'无',	 								0,  	'')
insert diction values(1549,  '卡表类型',  			'科林自管户2009版',				1,  	'KE_001')
insert diction values(1549,  '卡表类型',  			'科林自管户2',				2,  	'KE_002')
insert diction values(1549,  '卡表类型',  			'科林6103',						3,  	'KE_003')
--20131127 zhp修改电表名描述增加ke_006
insert diction values(1549,  '卡表类型',  			'科林自管户2013版',						6,  	'KE_006')

go


/****************电容器参数******************/
insert diction values(1700,  '补偿方式',  			'不补偿',	 						0,  	'')
insert diction values(1700,  '补偿方式',  			'共补',	 							1,  	'')
insert diction values(1700,  '补偿方式',  			'分补',	 							2,  	'')

insert diction values(1701,  '分补相位',  			'不补偿',	 						0,  	'')
insert diction values(1701,  '分补相位',  			'A相',	 							1,  	'')
insert diction values(1701,  '分补相位',  			'B相',	 							2,  	'')
insert diction values(1701,  '分补相位',  			'AB相',	 							3,  	'')
insert diction values(1701,  '分补相位',  			'C相',	 							4,  	'')
insert diction values(1701,  '分补相位',  			'AC相',	 							5,  	'')
insert diction values(1701,  '分补相位',  			'BC相',	 							6,  	'')

insert diction values(1702,  '共补相位',  			'全补偿',	 						7,  	'')


insert diction values(1703,  '补偿相位',  			'不补偿',	 						0,  	'')
insert diction values(1703,  '补偿相位',  			'A相',	 							1,  	'')
insert diction values(1703,  '补偿相位',  			'B相',	 							2,  	'')
insert diction values(1703,  '补偿相位',  			'AB相',	 							3,  	'')
insert diction values(1703,  '补偿相位',  			'C相',	 							4,  	'')
insert diction values(1703,  '补偿相位',  			'AC相',	 							5,  	'')
insert diction values(1703,  '补偿相位',  			'BC相',	 							6,  	'')
insert diction values(1703,  '补偿相位',  			'全补偿',	 						7,  	'')


insert diction values(1704,  '电容器厂家',  		'石家庄科林',	 				0,  	'')
insert diction values(1704,  '电容器厂家',  		'上海人民',	 					1,  	'')
insert diction values(1704,  '电容器厂家',  		'温州嘉迪',	 					2,  	'')
insert diction values(1704,  '电容器厂家',  		'广东光达',	 					3,  	'')
insert diction values(1704,  '电容器厂家',  		'西安华超',	 					4,  	'')
insert diction values(1704,  '电容器厂家',  		'上海翼舟',	 					5,  	'')
insert diction values(1704,  '电容器厂家',  		'其他厂家',	 					50,  	'')


insert diction values(1705,  '投切控制方式',  	'当地自动控制',	 			1,  	'')
insert diction values(1705,  '投切控制方式',  	'远方遥控',	 					2,  	'')
insert diction values(1705,  '投切控制方式',  	'闭锁',	 							3,  	'')
insert diction values(1705,  '投切控制方式',  	'解锁',	 							4,  	'')
insert diction values(1705,  '投切控制方式',  	'当地手动控制',	 			5,  	'')
go

insert diction values(1706,  '投入控制',  			'保持原状',	 					0,  	'')
insert diction values(1706,  '投入控制',  			'投入',	 							1,  	'')

insert diction values(1707,  '切除控制',  			'保持原状',	 					0,  	'')
insert diction values(1707,  '切除控制',  			'切除',	 							1,  	'')
go


/****************断路器参数******************/
insert diction values(1720,  '锁死状态',  			'未锁死',	 						0,  	'')
insert diction values(1720,  '锁死状态',  			'锁死',	 							1,  	'')

insert diction values(1721,  '开关动作原因相位','无效',	 							0,  	'')
insert diction values(1721,  '开关动作原因相位','A相',	 							1,  	'')
insert diction values(1721,  '开关动作原因相位','B相',	 							2,  	'')
insert diction values(1721,  '开关动作原因相位','C相',	 							3,  	'')

insert diction values(1722,  '开关动作原因',		'漏电跳闸',	 					0,  	'')
insert diction values(1722,  '开关动作原因',		'突变跳闸',	 					1,  	'')
insert diction values(1722,  '开关动作原因',		'特波跳闸',	 					2,  	'')
insert diction values(1722,  '开关动作原因',		'过载跳闸',	 					3,  	'')
insert diction values(1722,  '开关动作原因',		'过压跳闸',	 					4,  	'')
insert diction values(1722,  '开关动作原因',		'欠压跳闸',	 					5,  	'')
insert diction values(1722,  '开关动作原因',		'短路跳闸',	 					6,  	'')
insert diction values(1722,  '开关动作原因',		'手动跳闸',	 					7,  	'')
insert diction values(1722,  '开关动作原因',		'停电跳闸',	 					8,  	'')
insert diction values(1722,  '开关动作原因',		'互感器故障跳闸',	 		9,  	'')
insert diction values(1722,  '开关动作原因',		'远程跳闸',	 					10,  	'')
insert diction values(1722,  '开关动作原因',		'其它原因跳闸',	 			11,  	'')
insert diction values(1722,  '开关动作原因',		'合闸过程中',	 				12,  	'')
insert diction values(1722,  '开关动作原因',		'合闸失败',	 					13,  	'')

insert diction values(1723,  '轮次控制',		'第1轮次',	 					1,  	'')
insert diction values(1723,  '轮次控制',		'第2轮次',	 					2,  	'')
insert diction values(1723,  '轮次控制',		'第3轮次',	 					3,  	'')
insert diction values(1723,  '轮次控制',		'第4轮次',	 					4,  	'')
insert diction values(1723,  '轮次控制',		'第5轮次',	 					5,  	'')
go

/*--zhaopeng20130708 鼎牌断路器---*/
insert diction values(1724,  '断路器保护功能状态',		'关闭',	 					0,  	'')
insert diction values(1724,  '断路器保护功能状态',		'打开',	 					1,  	'')

insert diction values(1725,  '断路器脱扣告警',		'报警',	 					0,  	'')
insert diction values(1725,  '断路器脱扣告警',		'脱扣',	 					1,  	'')

insert diction values(1726,  '剩余电流自动跟踪',		'定档',	 					0,  	'')
insert diction values(1726,  '剩余电流自动跟踪',		'跟踪',	 					1,  	'')

insert diction values(1727,  '断路器延时方式',		'定时限',	 					0,  	'')
insert diction values(1727,  '断路器延时方式',		'反时限',	 					1,  	'')

insert diction values(1728,  '重合闸功能选择',		'不允许',	 					0,  	'')
insert diction values(1728,  '重合闸功能选择',		'允许',	 					1,  	'')

insert diction values(1729,  '剩余电流档位值',		'200mA',	 					200,  	'')
insert diction values(1729,  '剩余电流档位值',		'400mA',	 					400,  	'')
insert diction values(1729,  '剩余电流档位值',		'500mA',	 					500,  	'')

insert diction values(1730,  '剩余电流分断时间',		'200s',	 					200,  	'')
insert diction values(1730,  '剩余电流分断时间',		'500s',	 					500,  	'')

insert diction values(1731,  '断路器额定电流',		'250A',	 					250,  	'')
insert diction values(1731,  '断路器额定电流',		'400A',	 					400,  	'')
go
/*--zhaopeng20130708 鼎牌断路器 end---*/

/*--zhpeng 20140104添加换相开关参数状态start----*/
insert diction values(1740,  '换相开关状态',		'无故障',	 					0,  	'');
insert diction values(1740,  '换相开关状态',		'突变漏电电流',	 					1,  	'');
insert diction values(1740,  '换相开关状态',		'缓变漏电电流',	 					2,  	'');
insert diction values(1740,  '换相开关状态',		'过流跳闸',	 					3,  	'');
insert diction values(1740,  '换相开关状态',		'过压跳闸',	 					4,  	'');
insert diction values(1740,  '换相开关状态',		'远控跳闸',	 					5,  	'');
insert diction values(1740,  '换相开关状态',		'分闸原因不明',	 					6,  	'');
insert diction values(1740,  '换相开关状态',		'欠压跳闸',	 					7,  	'');

insert diction values(1741,  '换相开关相位',		'A相',	 					1,  	'');
insert diction values(1741,  '换相开关相位',		'B相',	 					2,  	'');
insert diction values(1741,  '换相开关相位',		'C相',	 					3,  	'');

insert diction values(1742,  '换相开关动作选择',		'跳闸',	 					0x55,  	'');	
insert diction values(1742,  '换相开关动作选择',		'合闸',	 					0xAA,  	'');	

insert diction values(1743,  '突变漏电电流值',		'0mA',	 					0,  	'');
insert diction values(1743,  '突变漏电电流值',		'40mA',	 					40,  	'');
insert diction values(1743,  '突变漏电电流值',		'80mA',	 					80,  	'');

insert diction values(1744,  '缓变漏电电流值',		'0mA',	 					0,  	'');
insert diction values(1744,  '缓变漏电电流值',		'80mA',	 					80,  	'');
insert diction values(1744,  '缓变漏电电流值',		'160mA',	 				160,  	'');

insert diction values(1745,  '过流保护值',		'50A',	 					50,  	'');
insert diction values(1745,  '过流保护值',		'60A',	 					60,  	'');
insert diction values(1745,  '过流保护值',		'70A',	 					70,  	'');
insert diction values(1745,  '过流保护值',		'80A',	 					80,  	'');
insert diction values(1745,  '过流保护值',		'90A',	 					90,  	'');
insert diction values(1745,  '过流保护值',		'100A',	 					100,  	'');

insert diction values(1746,  '过压保护值',		'0V',	 					0,  	'');
insert diction values(1746,  '过压保护值',		'280V',	 					280,  	'');
insert diction values(1746,  '过压保护值',		'290V',	 					290,  	'');
insert diction values(1746,  '过压保护值',		'300V',	 					300,  	'');
insert diction values(1746,  '过压保护值',		'310V',	 					310,  	'');
insert diction values(1746,  '过压保护值',		'320V',	 					320,  	'');

insert diction values(1747,  '是否启动自调节功能',		'启动',	 			0x55,  	'');	
insert diction values(1747,  '是否启动自调节功能',		'不启动',	 		0xAA,  	'');	

/*--zhpeng 20140104添加换相开关参数状态end----*/

/*--jack 20140704添加有载调容参数状态start----*/
insert diction values(1748,  '有载调容控制状态',		'自动控制',	 	1,  	'');	
insert diction values(1748,  '有载调容控制状态',		'远程控制',	 	2,  	'');	
insert diction values(1748,  '有载调容控制状态',		'手动控制',	 	4,  	'');	

insert diction values(1749,  '有载调容开关位置',		'非法位置',	 	0,  	'');	
insert diction values(1749,  '有载调容开关位置',		'低容位置',	 	1,  	'');	
insert diction values(1749,  '有载调容开关位置',		'高容位置',	 	2,  	'');	
go

insert diction values(1750,  '有载调容开关操作',		'升容',	 	1,  	'');	
insert diction values(1750,  '有载调容开关操作',		'降容',	 	2,  	'');	
go

/*--jack 20140704添加有载调容参数状态end----*/


/****************农排参数******************/
insert diction values(2001,  '无采样断电时间',		'10分钟',	 					10,  	'')
insert diction values(2001,  '无采样断电时间',		'20分钟',	 					20,  	'')
insert diction values(2001,  '无采样断电时间',		'30分钟',	 					30,  	'')
go


insert diction values(2002,  '农排表用电状态',		'正常',	 						0,  	'')
insert diction values(2002,  '农排表用电状态',		'系统停电',	 				1,  	'')
insert diction values(2002,  '农排表用电状态',		'无脉冲自动拉闸',	 	2,  	'')
insert diction values(2002,  '农排表用电状态',		'人为锁表',	 				3,  	'')
insert diction values(2002,  '农排表用电状态',		'超负荷',	 					4,  	'')
go


insert diction values(2003,  '农排表刷卡故障原因',		'区域号错',	 						1,  	'')
insert diction values(2003,  '农排表刷卡故障原因',		'剩余金额不足',	 				2,  	'')
insert diction values(2003,  '农排表刷卡故障原因',		'卡在其他表上灰锁',	 		3,  	'')        
go

insert diction values(2004,  '农排表类型',					'科林农排表2012版',	 						0,  	'')
insert diction values(2004,  '农排表类型',					'河南公用农排表',	 							20,  	'')
go


insert diction values(2005,  '清丰农排表用电状态',		'正常',	 						0,  	'')
insert diction values(2005,  '清丰农排表用电状态',		'系统停电',	 				1,  	'')
insert diction values(2005,  '清丰农排表用电状态',		'缺相',	 						2,  	'')
insert diction values(2005,  '清丰农排表用电状态',		'无采样自动掉电',	 	3,  	'')
insert diction values(2005,  '清丰农排表用电状态',		'金额不足',	 				4,  	'')
insert diction values(2005,  '清丰农排表用电状态',		'人为锁表',	 				5,  	'')
go

