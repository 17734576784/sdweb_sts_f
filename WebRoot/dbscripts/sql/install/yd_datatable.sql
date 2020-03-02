use YDDataBen 
go




--	'-------Create table 测量点预付费记录表(JYff2015) on YDDataBen-------'
-- 标记开始
-- MarkStart
if not exists (select * from sysobjects where name = 'JYff2015')
begin
create table JYff2015
(
	rtu_id				int						  		not null,
	mp_id					smallint  					not null,	
	
	res_id				smallint						not null,			/*客户编号*/
	res_desc			char(64)								null,			/*客户名称*/

	op_man				char(64)						not null,			/*操作员*/	
	op_type				tinyint							not null,			/*本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户 */	
	op_date				int									not null,			/*本次操作日期*/	
	op_time				int									not null,			/*本次操作时间*/

	pay_type			tinyint									null,			/*缴费方式*/

	wasteno				varchar(64)							null,			/*流水号*/	
	rewasteno			varchar(64)							null,			/*被冲正流水号*/	

	pay_money			decimal(12,2)						null,			/*缴费金额*/	
	othjs_money		decimal(12,2)						null,			/*结算金额(其它系统)*/
	zb_money			decimal(12,2)						null,			/*追补金额*/	
	all_money			decimal(12,2)						null,			/*总金额*/

	--add 201406
	buy_dl				decimal(12,2)						null,			/*购电量*/
	pay_bmc				decimal(12,2)						null,			/*表码差*//*又名写卡电量*/
	--end
	
	shutdown_val	decimal(12,2)						null,			/*断电值 金额计费时为:断电金额*/
	--20130108
	shutdown_val2	decimal(12,2)						null,			/*断电值 金额计费时为:断电金额 本地模拟电表 */	
	--20130108

	jsbd_zyz			decimal(12,2)						null,			/*测量点1-结算总表底*/
	jsbd_zyj			decimal(12,2)						null,			/*测量点1-结算尖表底*/	
	jsbd_zyf			decimal(12,2)						null,			/*测量点1-结算峰表底*/	
	jsbd_zyp			decimal(12,2)						null,			/*测量点1-结算平表底*/	
	jsbd_zyg			decimal(12,2)						null,			/*测量点1-结算谷表底*/

	jsbd1_zyz			decimal(12,2)						null,			/*测量点2-结算总表底*/
	jsbd1_zyj			decimal(12,2)						null,			/*测量点2-结算尖表底*/	
	jsbd1_zyf			decimal(12,2)						null,			/*测量点2-结算峰表底*/	
	jsbd1_zyp			decimal(12,2)						null,			/*测量点2-结算平表底*/	
	jsbd1_zyg			decimal(12,2)						null,			/*测量点2-结算谷表底*/

	jsbd2_zyz			decimal(12,2)						null,			/*测量点3-结算总表底*/
	jsbd2_zyj			decimal(12,2)						null,			/*测量点3-结算尖表底*/	
	jsbd2_zyf			decimal(12,2)						null,			/*测量点3-结算峰表底*/	
	jsbd2_zyp			decimal(12,2)						null,			/*测量点3-结算平表底*/	
	jsbd2_zyg			decimal(12,2)						null,			/*测量点3-结算谷表底*/

	jsbd_ymd			int											null,			/*结算时间*/

	--20130108
  calc_bdymd		int										 	null,			/*算费表底时间-YYYYMMDD*/              		                  
	calc_zyz			decimal(12,2)		  			null,			/*算费时总表底*/
	calc_zyj			decimal(12,2)		  			null,			/*算费时尖表底*/
	calc_zyf			decimal(12,2)		  			null,			/*算费时峰表底*/
	calc_zyp			decimal(12,2)		  			null,			/*算费时平表底*/
	calc_zyg			decimal(12,2)		  			null,			/*算费时谷表底*/

	calc1_zyz			decimal(12,2)		  			null,			/*动力关联1-算费时总表底*/
	calc1_zyj			decimal(12,2)		  			null,			/*动力关联1-算费时尖表底*/
	calc1_zyf			decimal(12,2)		  			null,			/*动力关联1-算费时峰表底*/
	calc1_zyp			decimal(12,2)		  			null,			/*动力关联1-算费时平表底*/
	calc1_zyg			decimal(12,2)		  			null,			/*动力关联1-算费时谷表底*/

	calc2_zyz			decimal(12,2)		  			null,			/*动力关联2-算费时总表底*/
	calc2_zyj			decimal(12,2)		  			null,			/*动力关联2-算费时尖表底*/
	calc2_zyf			decimal(12,2)		  			null,			/*动力关联2-算费时峰表底*/
	calc2_zyp			decimal(12,2)		  			null,			/*动力关联2-算费时平表底*/
	calc2_zyg			decimal(12,2)		  			null,			/*动力关联2-算费时谷表底*/

	now_remain		decimal(12,2)						null,			/*当前剩余*/
	now_remain2		decimal(12,2)						null,			/*当前剩余 本地模拟电表*/

	total_gdz			decimal(12,2)						null,			/*累计购电值*/

	jt_total_zbdl	decimal(12,2)						null,			/*阶梯追补累计用电量*/
	jt_total_dl		decimal(12,2)						null,			/*阶梯累计用电量*/
	--20130108

	alarm1				decimal(12,2)						null,			/*报警值1*/		
	alarm2				decimal(12,2)						null,			/*报警值2*/			

	buy_times			int											null,			/*购电次数*/		

	cur_feeproj		smallint								null,			/*当前费率号*/

	op_result			tinyint									null,			/*操作结果 0:未成功 1:写卡成功 2:远程设置成功 3:主站操作成功 4:暂时不处理 5:正在操作 6:失败*/

	visible_flag	tinyint									null,			/*是否显示*/	

	sg186_ysdw		varchar(20)							null,			/*186应收单位标识*/	
	up186_flag		tinyint									null,			/*186上传成功标志*/
	checkpay_flag	tinyint									null			/*对账成功标志*/
)
grant select on JYff2015 to public
create clustered index JYff2015index1
	on JYff2015(rtu_id,mp_id,op_date,op_time,op_type)	
create index JYff2015index2 
	on JYff2015(rtu_id,res_id,op_date,op_time,op_type)  
end
go
-- 标记结束
-- MarkEnd


--	'-------Create table 费率更改记录表(JFeeRateChg2015) on YDDataBen-------'
-- 标记开始
-- MarkStart
if not exists (select * from sysobjects where name = 'JFeeRateChg2015')
begin
create table JFeeRateChg2015
(
	rtu_id						int						  		not null,
	mp_id							smallint  					not null,
	res_id						smallint						not null,					/*客户编号*/

	op_date						int									not null,
	op_time						int									not null,
	
	op_man						char(64)								null,					/*操作员*/	

	/*旧费率*/
	old_id						smallint        				null,					/*编号*/
	old_describe			varchar(64)     				null,					/*名称*/
	
	/*add*/
	old_fee_type			tinyint									null,					/*费率*/ /*1单费率 2复费率 3混合费率 4阶梯*/
	
	/*单费率*/
	old_rated_z				decimal(12,6)			  		null,					/*总费率*/
	
	/*复费率*/
	old_ratef_j				decimal(12,6)   				null,					/*尖费率*/
	old_ratef_f				decimal(12,6)			  		null,					/*峰费率*/	
	old_ratef_p				decimal(12,6)  					null,					/*平费率*/
	old_ratef_g				decimal(12,6)   				null,					/*谷费率*/
	
	/*混合费率*/
	old_rateh_1				decimal(12,6)   				null,					/*混合费率1*/
	old_rateh_2				decimal(12,6)			  		null,					/*混合费率2*/	
	old_rateh_3				decimal(12,6)  					null,					/*混合费率3*/
	old_rateh_4				decimal(12,6)   				null,					/*混合费率4*/	
	
	old_rateh_bl1			tinyint									null,					/*混合比例1*/	
	old_rateh_bl2			tinyint									null,					/*混合比例2*/		
	old_rateh_bl3			tinyint									null,					/*混合比例3*/		
	old_rateh_bl4			tinyint									null,					/*混合比例4*/		
	
	/*阶梯电价*/
	old_ratej_type 		tinyint									null,					/*阶梯电价类型 	0 年度方案, 1月度方案	2月度峰谷/阶梯混合*/
	old_ratej_num 		tinyint									null,					/*阶梯电价数*/
	old_meterfee_type	tinyint									null,					/*电表费率类型  1单费率 2复费率*/
	old_meterfee_r		decimal(12,6)   				null,					/*电表执行电价*/
		
	old_ratej_r1			decimal(12,6)   				null,					/*阶梯费率1*/
	old_ratej_r2			decimal(12,6)			  		null,					/*阶梯费率2*/	
	old_ratej_r3			decimal(12,6)  					null,					/*阶梯费率3*/
	old_ratej_r4			decimal(12,6)   				null,					/*阶梯费率4*/
		
	old_ratej_td1			decimal(12,6)						null,					/*阶梯梯度值1*/
	old_ratej_td2			decimal(12,6)						null,					/*阶梯梯度值2*/
	old_ratej_td3			decimal(12,6)						null,					/*阶梯梯度值3*/

	/*混合阶梯电价*/
	old_ratehj_type 		tinyint								null,					/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
	old_ratehj_num			tinyint								null,					/*阶梯电价数*/
	
	old_meterfeehj_type	tinyint								null,					/*电表费率类型  0单费率 1复费率 20120704*/
	old_meterfeehj_r		decimal(12,6)   			null,					/*电表执行电价*/
	
	old_ratehj_hr1_r1		decimal(12,6)   			null,					/*第1比例电价-阶梯费率1*/
	old_ratehj_hr1_r2		decimal(12,6)					null,					/*第1比例电价-阶梯费率2*/
	old_ratehj_hr1_r3		decimal(12,6)  				null,					/*第1比例电价-阶梯费率3*/
	old_ratehj_hr1_r4		decimal(12,6)   			null,					/*第1比例电价-阶梯费率4*/
		
	old_ratehj_hr1_td1	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值1*/
	old_ratehj_hr1_td2	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值2*/
	old_ratehj_hr1_td3	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值3*/
	
	old_ratehj_hr2			decimal(12,6)   			null,					/*第2比例电价*/
	old_ratehj_hr3			decimal(12,6)   			null,					/*第3比例电价*/
	
	old_ratehj_bl1			tinyint								null,					/*混合比例1*/	
	old_ratehj_bl2			tinyint								null,					/*混合比例2*/	
	old_ratehj_bl3			tinyint								null,					/*混合比例3*/	

	/*新费率*/
	new_id							smallint        			null,					/*编号*/
	new_describe				varchar(64)     			null,					/*名称*/
	
	/*add*/
	new_fee_type				tinyint								null,					/*费率*/ /*1单费率 2复费率 3混合费率 4阶梯*/
	
	/*单费率*/
	new_rated_z					decimal(12,6)			  	null,					/*总费率*/
	
	/*复费率*/
	new_ratef_j					decimal(12,6)   			null,					/*尖费率*/
	new_ratef_f					decimal(12,6)			  	null,					/*峰费率*/	
	new_ratef_p					decimal(12,6)  				null,					/*平费率*/
	new_ratef_g					decimal(12,6)   			null,					/*谷费率*/
	
	/*混合费率*/
	new_rateh_1					decimal(12,6)   			null,					/*混合费率1*/
	new_rateh_2					decimal(12,6)			  	null,					/*混合费率2*/	
	new_rateh_3					decimal(12,6)  				null,					/*混合费率3*/
	new_rateh_4					decimal(12,6)   			null,					/*混合费率4*/	
	              			
	new_rateh_bl1				tinyint								null,					/*混合比例1*/	
	new_rateh_bl2				tinyint								null,					/*混合比例2*/		
	new_rateh_bl3				tinyint								null,					/*混合比例3*/		
	new_rateh_bl4				tinyint								null,					/*混合比例4*/		
	
	/*阶梯电价*/
	new_ratej_type 			tinyint								null,					/*阶梯电价类型 	0 年度方案, 1月度方案	2月度峰谷/阶梯混合*/
	new_ratej_num 			tinyint								null,					/*阶梯电价数*/	
	new_meterfee_type		tinyint								null,					/*电表费率类型  1单费率 2复费率*/
	new_meterfee_r			decimal(12,6)   			null,					/*电表执行电价*/
	                                        	
	new_ratej_r1				decimal(12,6)   			null,					/*阶梯费率1*/
	new_ratej_r2				decimal(12,6)			  	null,					/*阶梯费率2*/	
	new_ratej_r3				decimal(12,6)  				null,					/*阶梯费率3*/
	new_ratej_r4				decimal(12,6)   			null,					/*阶梯费率4*/

	new_ratej_td1				decimal(12,6)					null,					/*阶梯梯度值1*/
	new_ratej_td2				decimal(12,6)					null,					/*阶梯梯度值2*/
	new_ratej_td3				decimal(12,6)					null,					/*阶梯梯度值3*/

	/*混合阶梯电价*/
	new_ratehj_type 		tinyint								null,					/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
	new_ratehj_num			tinyint								null,					/*阶梯电价数*/
	
	new_meterfeehj_type	tinyint								null,					/*电表费率类型  0单费率 1复费率 20120704*/
	new_meterfeehj_r		decimal(12,6)   			null,					/*电表执行电价*/
	
	new_ratehj_hr1_r1		decimal(12,6)   			null,					/*第1比例电价-阶梯费率1*/
	new_ratehj_hr1_r2		decimal(12,6)					null,					/*第1比例电价-阶梯费率2*/
	new_ratehj_hr1_r3		decimal(12,6)  				null,					/*第1比例电价-阶梯费率3*/
	new_ratehj_hr1_r4		decimal(12,6)   			null,					/*第1比例电价-阶梯费率4*/
		
	new_ratehj_hr1_td1	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值1*/
	new_ratehj_hr1_td2	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值2*/
	new_ratehj_hr1_td3	decimal(12,6)					null,					/*第1比例电价-阶梯梯度值3*/
	
	new_ratehj_hr2			decimal(12,6)   			null,					/*第2比例电价*/
	new_ratehj_hr3			decimal(12,6)   			null,					/*第3比例电价*/
	
	new_ratehj_bl1			tinyint								null,					/*混合比例1*/	
	new_ratehj_bl2			tinyint								null,					/*混合比例2*/	
	new_ratehj_bl3			tinyint								null,					/*混合比例3*/
	reserve1		 				int										null,
	reserve2		 				int										null		
)
grant select on JFeeRateChg2015 to public
create unique clustered index JFeeRateChg2015index1
	on JFeeRateChg2015(rtu_id, mp_id, op_date, op_time)
end
go
-- 标记结束
-- MarkEnd

----------------------预付费结束------------------------------------------------



----------------------综合应用开始------------------------------------------------

--   '-------Create table 操作日志记录(operlog2015) on YDDataBen-------'
-- 标记开始
-- MarkStart
if not exists (select * from sysobjects where name = 'operlog2015')
begin
create table operlog2015
(
	oper_id				smallint   			not null,	/*操作员编号*/
	date					int							not null,	/*日期*/	
	time					int							not null,	/*时间*/
--	log_index			int							not null, /*index flag*/	
	opertype			tinyint							null,	/*操作类型 0 登录 1 退出*/
--ipAddr				varchar(32)					null,	/*ip地址*/
	oper_info  		varchar(128)    		null,	/*操作内容*/
	reserve1			int								  null,
	reserve2			decimal(12,3)   		null,
	reserve3			varchar(40)      		null
)
grant select on  operlog2015 to public
create unique clustered index operlog2015index1
	on operlog2015(oper_id,date,time)
	--on operlog2015(oper_id,date,time,log_index)
end
go
-- 标记结束
-- MarkEnd



--	'-------Create table 工具维护记录表(Tool2015) on YDDataBen-------'
-- 标记开始
-- MarkStart
if not exists (select * from sysobjects where name = 'Tool2015')
begin
create table Tool2015
(
	rtu_id				int						  		not null,
	mp_id					smallint  					not null,	
	
	res_id				varchar(32)					not null,			/*客户编号*/
	res_desc			varchar(64)							null,			/*客户名称*/

	op_man				varchar(64)					not null,			/*操作员*/	
	op_type				tinyint							not null,			/*本次操作类型 0 SetMaximumPowerLimit, 1 ClearCredit , 2 SetTariffRate, 3 Set1stSectionDecoderKey , 4 Set2ndSectionDecoderKey, 5 ClearTamperCondition, */
																									/*6 SetMaximumPhasePowerUnbalanceLimit, 7 SetWaterMeterFactor 8 Reserved for STS use 11 Reserved for Proprietary use*/	
	op_date				int									not null,			/*本次操作日期*/	
	op_time				int									not null,			/*本次操作时间*/

	wasteno				varchar(32)							null,			/*交易流水号,自定义,和充电产生的规则相同*/	
	rewasteno			varchar(32)							null,			/*token*/	
	op_result			tinyint									null,			/*操作结果 1:成功 2:失败*/
	visible_flag	tinyint									null,			/*是否显示*/	
	update_flag		tinyint									null,			/*更新上传成功标志*/
)
grant select on Tool2015 to public
create clustered index Tool2015index1
	on Tool2015(rtu_id,mp_id,op_date,op_time,op_type)	
create index Tool2015index2 
	on Tool2015(rtu_id,res_id,op_date,op_time,op_type)  
end
go
-- 标记结束
-- MarkEnd


--文档最后【标记结束】后必须换行