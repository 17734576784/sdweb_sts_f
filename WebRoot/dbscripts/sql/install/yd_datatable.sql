use YDDataBen 
go




--	'-------Create table ������Ԥ���Ѽ�¼��(JYff2015) on YDDataBen-------'
-- ��ǿ�ʼ
-- MarkStart
if not exists (select * from sysobjects where name = 'JYff2015')
begin
create table JYff2015
(
	rtu_id				int						  		not null,
	mp_id					smallint  					not null,	
	
	res_id				smallint						not null,			/*�ͻ����*/
	res_desc			char(64)								null,			/*�ͻ�����*/

	op_man				char(64)						not null,			/*����Ա*/	
	op_type				tinyint							not null,			/*���β������� 0 ��ʼ̬, 1 ����, 2 �ɷ�, 3 ����, 4 ��д��, 5 ����, 6 ����, 7 ������ 10 ���� */	
	op_date				int									not null,			/*���β�������*/	
	op_time				int									not null,			/*���β���ʱ��*/

	pay_type			tinyint									null,			/*�ɷѷ�ʽ*/

	wasteno				varchar(64)							null,			/*��ˮ��*/	
	rewasteno			varchar(64)							null,			/*��������ˮ��*/	

	pay_money			decimal(12,2)						null,			/*�ɷѽ��*/	
	othjs_money		decimal(12,2)						null,			/*������(����ϵͳ)*/
	zb_money			decimal(12,2)						null,			/*׷�����*/	
	all_money			decimal(12,2)						null,			/*�ܽ��*/

	--add 201406
	buy_dl				decimal(12,2)						null,			/*������*/
	pay_bmc				decimal(12,2)						null,			/*�����*//*����д������*/
	--end
	
	shutdown_val	decimal(12,2)						null,			/*�ϵ�ֵ ���Ʒ�ʱΪ:�ϵ���*/
	--20130108
	shutdown_val2	decimal(12,2)						null,			/*�ϵ�ֵ ���Ʒ�ʱΪ:�ϵ��� ����ģ���� */	
	--20130108

	jsbd_zyz			decimal(12,2)						null,			/*������1-�����ܱ��*/
	jsbd_zyj			decimal(12,2)						null,			/*������1-�������*/	
	jsbd_zyf			decimal(12,2)						null,			/*������1-�������*/	
	jsbd_zyp			decimal(12,2)						null,			/*������1-����ƽ���*/	
	jsbd_zyg			decimal(12,2)						null,			/*������1-����ȱ��*/

	jsbd1_zyz			decimal(12,2)						null,			/*������2-�����ܱ��*/
	jsbd1_zyj			decimal(12,2)						null,			/*������2-�������*/	
	jsbd1_zyf			decimal(12,2)						null,			/*������2-�������*/	
	jsbd1_zyp			decimal(12,2)						null,			/*������2-����ƽ���*/	
	jsbd1_zyg			decimal(12,2)						null,			/*������2-����ȱ��*/

	jsbd2_zyz			decimal(12,2)						null,			/*������3-�����ܱ��*/
	jsbd2_zyj			decimal(12,2)						null,			/*������3-�������*/	
	jsbd2_zyf			decimal(12,2)						null,			/*������3-�������*/	
	jsbd2_zyp			decimal(12,2)						null,			/*������3-����ƽ���*/	
	jsbd2_zyg			decimal(12,2)						null,			/*������3-����ȱ��*/

	jsbd_ymd			int											null,			/*����ʱ��*/

	--20130108
  calc_bdymd		int										 	null,			/*��ѱ��ʱ��-YYYYMMDD*/              		                  
	calc_zyz			decimal(12,2)		  			null,			/*���ʱ�ܱ��*/
	calc_zyj			decimal(12,2)		  			null,			/*���ʱ����*/
	calc_zyf			decimal(12,2)		  			null,			/*���ʱ����*/
	calc_zyp			decimal(12,2)		  			null,			/*���ʱƽ���*/
	calc_zyg			decimal(12,2)		  			null,			/*���ʱ�ȱ��*/

	calc1_zyz			decimal(12,2)		  			null,			/*��������1-���ʱ�ܱ��*/
	calc1_zyj			decimal(12,2)		  			null,			/*��������1-���ʱ����*/
	calc1_zyf			decimal(12,2)		  			null,			/*��������1-���ʱ����*/
	calc1_zyp			decimal(12,2)		  			null,			/*��������1-���ʱƽ���*/
	calc1_zyg			decimal(12,2)		  			null,			/*��������1-���ʱ�ȱ��*/

	calc2_zyz			decimal(12,2)		  			null,			/*��������2-���ʱ�ܱ��*/
	calc2_zyj			decimal(12,2)		  			null,			/*��������2-���ʱ����*/
	calc2_zyf			decimal(12,2)		  			null,			/*��������2-���ʱ����*/
	calc2_zyp			decimal(12,2)		  			null,			/*��������2-���ʱƽ���*/
	calc2_zyg			decimal(12,2)		  			null,			/*��������2-���ʱ�ȱ��*/

	now_remain		decimal(12,2)						null,			/*��ǰʣ��*/
	now_remain2		decimal(12,2)						null,			/*��ǰʣ�� ����ģ����*/

	total_gdz			decimal(12,2)						null,			/*�ۼƹ���ֵ*/

	jt_total_zbdl	decimal(12,2)						null,			/*����׷���ۼ��õ���*/
	jt_total_dl		decimal(12,2)						null,			/*�����ۼ��õ���*/
	--20130108

	alarm1				decimal(12,2)						null,			/*����ֵ1*/		
	alarm2				decimal(12,2)						null,			/*����ֵ2*/			

	buy_times			int											null,			/*�������*/		

	cur_feeproj		smallint								null,			/*��ǰ���ʺ�*/

	op_result			tinyint									null,			/*������� 0:δ�ɹ� 1:д���ɹ� 2:Զ�����óɹ� 3:��վ�����ɹ� 4:��ʱ������ 5:���ڲ��� 6:ʧ��*/

	visible_flag	tinyint									null,			/*�Ƿ���ʾ*/	

	sg186_ysdw		varchar(20)							null,			/*186Ӧ�յ�λ��ʶ*/	
	up186_flag		tinyint									null,			/*186�ϴ��ɹ���־*/
	checkpay_flag	tinyint									null			/*���˳ɹ���־*/
)
grant select on JYff2015 to public
create clustered index JYff2015index1
	on JYff2015(rtu_id,mp_id,op_date,op_time,op_type)	
create index JYff2015index2 
	on JYff2015(rtu_id,res_id,op_date,op_time,op_type)  
end
go
-- ��ǽ���
-- MarkEnd


--	'-------Create table ���ʸ��ļ�¼��(JFeeRateChg2015) on YDDataBen-------'
-- ��ǿ�ʼ
-- MarkStart
if not exists (select * from sysobjects where name = 'JFeeRateChg2015')
begin
create table JFeeRateChg2015
(
	rtu_id						int						  		not null,
	mp_id							smallint  					not null,
	res_id						smallint						not null,					/*�ͻ����*/

	op_date						int									not null,
	op_time						int									not null,
	
	op_man						char(64)								null,					/*����Ա*/	

	/*�ɷ���*/
	old_id						smallint        				null,					/*���*/
	old_describe			varchar(64)     				null,					/*����*/
	
	/*add*/
	old_fee_type			tinyint									null,					/*����*/ /*1������ 2������ 3��Ϸ��� 4����*/
	
	/*������*/
	old_rated_z				decimal(12,6)			  		null,					/*�ܷ���*/
	
	/*������*/
	old_ratef_j				decimal(12,6)   				null,					/*�����*/
	old_ratef_f				decimal(12,6)			  		null,					/*�����*/	
	old_ratef_p				decimal(12,6)  					null,					/*ƽ����*/
	old_ratef_g				decimal(12,6)   				null,					/*�ȷ���*/
	
	/*��Ϸ���*/
	old_rateh_1				decimal(12,6)   				null,					/*��Ϸ���1*/
	old_rateh_2				decimal(12,6)			  		null,					/*��Ϸ���2*/	
	old_rateh_3				decimal(12,6)  					null,					/*��Ϸ���3*/
	old_rateh_4				decimal(12,6)   				null,					/*��Ϸ���4*/	
	
	old_rateh_bl1			tinyint									null,					/*��ϱ���1*/	
	old_rateh_bl2			tinyint									null,					/*��ϱ���2*/		
	old_rateh_bl3			tinyint									null,					/*��ϱ���3*/		
	old_rateh_bl4			tinyint									null,					/*��ϱ���4*/		
	
	/*���ݵ��*/
	old_ratej_type 		tinyint									null,					/*���ݵ������ 	0 ��ȷ���, 1�¶ȷ���	2�¶ȷ��/���ݻ��*/
	old_ratej_num 		tinyint									null,					/*���ݵ����*/
	old_meterfee_type	tinyint									null,					/*����������  1������ 2������*/
	old_meterfee_r		decimal(12,6)   				null,					/*���ִ�е��*/
		
	old_ratej_r1			decimal(12,6)   				null,					/*���ݷ���1*/
	old_ratej_r2			decimal(12,6)			  		null,					/*���ݷ���2*/	
	old_ratej_r3			decimal(12,6)  					null,					/*���ݷ���3*/
	old_ratej_r4			decimal(12,6)   				null,					/*���ݷ���4*/
		
	old_ratej_td1			decimal(12,6)						null,					/*�����ݶ�ֵ1*/
	old_ratej_td2			decimal(12,6)						null,					/*�����ݶ�ֵ2*/
	old_ratej_td3			decimal(12,6)						null,					/*�����ݶ�ֵ3*/

	/*��Ͻ��ݵ��*/
	old_ratehj_type 		tinyint								null,					/*���ݵ������*/ 	/*0 ��ȷ���, 1�¶ȷ���*/
	old_ratehj_num			tinyint								null,					/*���ݵ����*/
	
	old_meterfeehj_type	tinyint								null,					/*����������  0������ 1������ 20120704*/
	old_meterfeehj_r		decimal(12,6)   			null,					/*���ִ�е��*/
	
	old_ratehj_hr1_r1		decimal(12,6)   			null,					/*��1�������-���ݷ���1*/
	old_ratehj_hr1_r2		decimal(12,6)					null,					/*��1�������-���ݷ���2*/
	old_ratehj_hr1_r3		decimal(12,6)  				null,					/*��1�������-���ݷ���3*/
	old_ratehj_hr1_r4		decimal(12,6)   			null,					/*��1�������-���ݷ���4*/
		
	old_ratehj_hr1_td1	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ1*/
	old_ratehj_hr1_td2	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ2*/
	old_ratehj_hr1_td3	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ3*/
	
	old_ratehj_hr2			decimal(12,6)   			null,					/*��2�������*/
	old_ratehj_hr3			decimal(12,6)   			null,					/*��3�������*/
	
	old_ratehj_bl1			tinyint								null,					/*��ϱ���1*/	
	old_ratehj_bl2			tinyint								null,					/*��ϱ���2*/	
	old_ratehj_bl3			tinyint								null,					/*��ϱ���3*/	

	/*�·���*/
	new_id							smallint        			null,					/*���*/
	new_describe				varchar(64)     			null,					/*����*/
	
	/*add*/
	new_fee_type				tinyint								null,					/*����*/ /*1������ 2������ 3��Ϸ��� 4����*/
	
	/*������*/
	new_rated_z					decimal(12,6)			  	null,					/*�ܷ���*/
	
	/*������*/
	new_ratef_j					decimal(12,6)   			null,					/*�����*/
	new_ratef_f					decimal(12,6)			  	null,					/*�����*/	
	new_ratef_p					decimal(12,6)  				null,					/*ƽ����*/
	new_ratef_g					decimal(12,6)   			null,					/*�ȷ���*/
	
	/*��Ϸ���*/
	new_rateh_1					decimal(12,6)   			null,					/*��Ϸ���1*/
	new_rateh_2					decimal(12,6)			  	null,					/*��Ϸ���2*/	
	new_rateh_3					decimal(12,6)  				null,					/*��Ϸ���3*/
	new_rateh_4					decimal(12,6)   			null,					/*��Ϸ���4*/	
	              			
	new_rateh_bl1				tinyint								null,					/*��ϱ���1*/	
	new_rateh_bl2				tinyint								null,					/*��ϱ���2*/		
	new_rateh_bl3				tinyint								null,					/*��ϱ���3*/		
	new_rateh_bl4				tinyint								null,					/*��ϱ���4*/		
	
	/*���ݵ��*/
	new_ratej_type 			tinyint								null,					/*���ݵ������ 	0 ��ȷ���, 1�¶ȷ���	2�¶ȷ��/���ݻ��*/
	new_ratej_num 			tinyint								null,					/*���ݵ����*/	
	new_meterfee_type		tinyint								null,					/*����������  1������ 2������*/
	new_meterfee_r			decimal(12,6)   			null,					/*���ִ�е��*/
	                                        	
	new_ratej_r1				decimal(12,6)   			null,					/*���ݷ���1*/
	new_ratej_r2				decimal(12,6)			  	null,					/*���ݷ���2*/	
	new_ratej_r3				decimal(12,6)  				null,					/*���ݷ���3*/
	new_ratej_r4				decimal(12,6)   			null,					/*���ݷ���4*/

	new_ratej_td1				decimal(12,6)					null,					/*�����ݶ�ֵ1*/
	new_ratej_td2				decimal(12,6)					null,					/*�����ݶ�ֵ2*/
	new_ratej_td3				decimal(12,6)					null,					/*�����ݶ�ֵ3*/

	/*��Ͻ��ݵ��*/
	new_ratehj_type 		tinyint								null,					/*���ݵ������*/ 	/*0 ��ȷ���, 1�¶ȷ���*/
	new_ratehj_num			tinyint								null,					/*���ݵ����*/
	
	new_meterfeehj_type	tinyint								null,					/*����������  0������ 1������ 20120704*/
	new_meterfeehj_r		decimal(12,6)   			null,					/*���ִ�е��*/
	
	new_ratehj_hr1_r1		decimal(12,6)   			null,					/*��1�������-���ݷ���1*/
	new_ratehj_hr1_r2		decimal(12,6)					null,					/*��1�������-���ݷ���2*/
	new_ratehj_hr1_r3		decimal(12,6)  				null,					/*��1�������-���ݷ���3*/
	new_ratehj_hr1_r4		decimal(12,6)   			null,					/*��1�������-���ݷ���4*/
		
	new_ratehj_hr1_td1	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ1*/
	new_ratehj_hr1_td2	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ2*/
	new_ratehj_hr1_td3	decimal(12,6)					null,					/*��1�������-�����ݶ�ֵ3*/
	
	new_ratehj_hr2			decimal(12,6)   			null,					/*��2�������*/
	new_ratehj_hr3			decimal(12,6)   			null,					/*��3�������*/
	
	new_ratehj_bl1			tinyint								null,					/*��ϱ���1*/	
	new_ratehj_bl2			tinyint								null,					/*��ϱ���2*/	
	new_ratehj_bl3			tinyint								null,					/*��ϱ���3*/
	reserve1		 				int										null,
	reserve2		 				int										null		
)
grant select on JFeeRateChg2015 to public
create unique clustered index JFeeRateChg2015index1
	on JFeeRateChg2015(rtu_id, mp_id, op_date, op_time)
end
go
-- ��ǽ���
-- MarkEnd

----------------------Ԥ���ѽ���------------------------------------------------



----------------------�ۺ�Ӧ�ÿ�ʼ------------------------------------------------

--   '-------Create table ������־��¼(operlog2015) on YDDataBen-------'
-- ��ǿ�ʼ
-- MarkStart
if not exists (select * from sysobjects where name = 'operlog2015')
begin
create table operlog2015
(
	oper_id				smallint   			not null,	/*����Ա���*/
	date					int							not null,	/*����*/	
	time					int							not null,	/*ʱ��*/
--	log_index			int							not null, /*index flag*/	
	opertype			tinyint							null,	/*�������� 0 ��¼ 1 �˳�*/
--ipAddr				varchar(32)					null,	/*ip��ַ*/
	oper_info  		varchar(128)    		null,	/*��������*/
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
-- ��ǽ���
-- MarkEnd



--	'-------Create table ����ά����¼��(Tool2015) on YDDataBen-------'
-- ��ǿ�ʼ
-- MarkStart
if not exists (select * from sysobjects where name = 'Tool2015')
begin
create table Tool2015
(
	rtu_id				int						  		not null,
	mp_id					smallint  					not null,	
	
	res_id				varchar(32)					not null,			/*�ͻ����*/
	res_desc			varchar(64)							null,			/*�ͻ�����*/

	op_man				varchar(64)					not null,			/*����Ա*/	
	op_type				tinyint							not null,			/*���β������� 0 SetMaximumPowerLimit, 1 ClearCredit , 2 SetTariffRate, 3 Set1stSectionDecoderKey , 4 Set2ndSectionDecoderKey, 5 ClearTamperCondition, */
																									/*6 SetMaximumPhasePowerUnbalanceLimit, 7 SetWaterMeterFactor 8 Reserved for STS use 11 Reserved for Proprietary use*/	
	op_date				int									not null,			/*���β�������*/	
	op_time				int									not null,			/*���β���ʱ��*/

	wasteno				varchar(32)							null,			/*������ˮ��,�Զ���,�ͳ������Ĺ�����ͬ*/	
	rewasteno			varchar(32)							null,			/*token*/	
	op_result			tinyint									null,			/*������� 1:�ɹ� 2:ʧ��*/
	visible_flag	tinyint									null,			/*�Ƿ���ʾ*/	
	update_flag		tinyint									null,			/*�����ϴ��ɹ���־*/
)
grant select on Tool2015 to public
create clustered index Tool2015index1
	on Tool2015(rtu_id,mp_id,op_date,op_time,op_type)	
create index Tool2015index2 
	on Tool2015(rtu_id,res_id,op_date,op_time,op_type)  
end
go
-- ��ǽ���
-- MarkEnd


--�ĵ���󡾱�ǽ���������뻻��