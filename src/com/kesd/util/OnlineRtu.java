package com.kesd.util;

import com.kesd.comnt.ComntMsg;
import com.libweb.comnt.ComntTime;

/**
 * 终端在线状态
 */
public class OnlineRtu {	
	public static class RTUSTATE_ITEM {
		public int		rtu_id			= -1;
		public byte		comm_state		= 0;	//RTU通讯状态
		public short    frame_ok_cnt	= 0;	//正确侦记数
		public short    frame_err_cnt	= 0;	//错误侦记数
		public int		last_time		= 0;	//RTU最后通讯时间
		public int		ip_addr			= 0;	//当前IP地址
		public byte		run_state		= 0;	//当前运行状态
		public byte		autotask_num	= 0;	//自动任务数量
		public byte		mantask_num		= 0;	//手工任务数量
		
		public void clone(RTUSTATE_ITEM item) {
			rtu_id			= item.rtu_id;
			comm_state		= item.comm_state;
			frame_ok_cnt	= item.frame_ok_cnt;
			frame_err_cnt	= item.frame_err_cnt;
			last_time		= item.last_time;
			ip_addr			= item.ip_addr;
			run_state		= item.run_state;
			autotask_num	= item.autotask_num;
			mantask_num		= item.mantask_num;
		}
	}
	
	public static class RTUSTATE {
		public RTUSTATE_ITEM[] rtuitem_vect = null;
		
		public void fromRtuStateMsg(ComntMsg.MSG_RTUSTATE rtustate_msg) {
			rtuitem_vect = null;
			
			if (rtustate_msg == null || rtustate_msg.rtuitem_vect == null || rtustate_msg.rtuitem_vect.length <= 0) return;
			
			int num = rtustate_msg.rtuitem_vect.length;
			
			rtuitem_vect = new RTUSTATE_ITEM[num];
			
			for (int i = 0; i < num; i++) {
				rtuitem_vect[i] = new RTUSTATE_ITEM();
				
				rtuitem_vect[i].rtu_id			= rtustate_msg.rtuitem_vect[i].rtu_id;
				rtuitem_vect[i].comm_state		= rtustate_msg.rtuitem_vect[i].comm_state;		//RTU通讯状态
				rtuitem_vect[i].frame_ok_cnt	= rtustate_msg.rtuitem_vect[i].frame_ok_cnt;	//正确侦记数
				rtuitem_vect[i].frame_err_cnt	= rtustate_msg.rtuitem_vect[i].frame_err_cnt;	//错误侦记数
				rtuitem_vect[i].last_time		= rtustate_msg.rtuitem_vect[i].last_time;		//RTU最后通讯时间
				rtuitem_vect[i].ip_addr			= rtustate_msg.rtuitem_vect[i].ip_addr;			//当前IP地址
				rtuitem_vect[i].run_state		= rtustate_msg.rtuitem_vect[i].run_state;		//当前运行状态
				rtuitem_vect[i].autotask_num	= rtustate_msg.rtuitem_vect[i].autotask_num;	//自动任务数量
				rtuitem_vect[i].mantask_num		= rtustate_msg.rtuitem_vect[i].mantask_num;		//手工任务数量				
			}
		}
	}
	
	private static RTUSTATE 		glo_rtustate 		= null;
	private static int				glo_lastcommtime 	= 0;

	private static final long STATE_TIMEOUT = 60 * 3;	//扫描间隔	
	
	public synchronized static void setRtuState(ComntMsg.MSG_RTUSTATE rtustate_msg){		
		if (glo_rtustate == null) glo_rtustate = new RTUSTATE();
		
		glo_rtustate.fromRtuStateMsg(rtustate_msg);
	}
	
	public synchronized static RTUSTATE_ITEM[] getRtuState() {
		if (glo_rtustate == null) return null;
		if (glo_rtustate.rtuitem_vect == null || glo_rtustate.rtuitem_vect.length <= 0) return null;
		
		RTUSTATE_ITEM[] ret_items = new RTUSTATE_ITEM[glo_rtustate.rtuitem_vect.length];
		
		for (int i = 0; i < glo_rtustate.rtuitem_vect.length; i++) {
			ret_items[i] = new RTUSTATE_ITEM();			
			ret_items[i].clone(glo_rtustate.rtuitem_vect[i]);
		}
		
		return ret_items;
	}
	
	public static RTUSTATE_ITEM getOneRtuState(int rtu_id) {
		if (glo_rtustate == null) return null;
		if (glo_rtustate.rtuitem_vect == null || glo_rtustate.rtuitem_vect.length <= 0) return null;
		
		RTUSTATE_ITEM ret_items = null;
		
		int high = glo_rtustate.rtuitem_vect.length - 1;
		int low = 0;
		int mid = 0;
		
		//二分查找
		while(high >= low){
			mid = (high + low) / 2;
			ret_items = glo_rtustate.rtuitem_vect[mid];
			if(ret_items.rtu_id > rtu_id){
				high = mid - 1;
			}else if(ret_items.rtu_id < rtu_id){
				low = mid + 1;
			}else{
				//找到
				return ret_items;
			}
		}
		
		return null;
	}
	
	public synchronized static void checkRtuState() {
		int cur_time = ComntTime.cTime();		
		if (Math.abs(cur_time - glo_lastcommtime) < STATE_TIMEOUT) return;		
		
		if (glo_rtustate == null || glo_rtustate.rtuitem_vect == null || glo_rtustate.rtuitem_vect.length <= 0) return;
		
		for (int i = 0; i < glo_rtustate.rtuitem_vect.length; i++) {			
			glo_rtustate.rtuitem_vect[i].comm_state		= 0;	//RTU通讯状态

			glo_rtustate.rtuitem_vect[i].frame_ok_cnt	= 0;	//正确侦记数
			glo_rtustate.rtuitem_vect[i].frame_err_cnt	= 0;	//错误侦记数
			glo_rtustate.rtuitem_vect[i].last_time		= 0;	//RTU最后通讯时间
			glo_rtustate.rtuitem_vect[i].ip_addr		= 0;	//当前IP地址
			glo_rtustate.rtuitem_vect[i].run_state		= 0;	//当前运行状态
			glo_rtustate.rtuitem_vect[i].autotask_num	= 0;	//自动任务数量
			glo_rtustate.rtuitem_vect[i].mantask_num	= 0;	//手工任务数量
		}
		
		glo_lastcommtime = cur_time;
	}
	
	public static void init() {
		glo_rtustate     = new RTUSTATE();
		glo_lastcommtime = ComntTime.cTime();
	}
	
	public static void free() {
		glo_rtustate = null;
	}
	
}