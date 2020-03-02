package com.kesd.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.common.YDTable;
import com.kesd.dbpara.YffManDef;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActTreePara extends ActionSupport{
	
	class Org{
		public short id;
		public String desc;
	}
	
	class LineFz{
		public short id;
		public String desc;
		public short org_id;
	}
	
	class Cons{
		public short id;
		public String desc;
		public short org_id;
		public short linefz_id;
	}
	
	class Rtu{
		public int id;
		public String desc;
		public short org_id;
		public short linefz_id;
		public short cons_id;
	}
	
	class Area{
		public int id;
		public String desc;
		public short org_id;
	}
	
	private static final long serialVersionUID = 7732260251430661794L;
	private String result;
	private int refresh = 0;
	private int appType = 1;
	
	public String execute(){
		
		if(appType < 0) {
			result = npTree();
			return SDDef.SUCCESS;
		}
		
		Org org[] 		= null;
		LineFz linefz[] = null;
		Cons cons[] 	= null;
		Rtu rtu[] 		= null;
		
		String sql = null;
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			int count = 0, index = 0;
			
			String qx_org = "", qx_linefz = "";
			YffManDef user = CommFunc.getYffMan();
			if(user.getRank() != 0){	//权限范围 0所有终端
				qx_org = " where id=" + user.getOrgId();
				qx_linefz = "where org_id=" + user.getOrgId();
			}
			
			sql = "select id,describe from orgpara " + qx_org;
			rs = j_dao.executeQuery(sql);
			
			rs.last();
			count = rs.getRow();
			org = new Org[count];
			
			rs.beforeFirst();
			
			while(rs.next()){
				org[index] = new Org();
				org[index].id = rs.getShort(1);
				org[index].desc = CommBase.CheckString(rs.getString(2));
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			
			sql = "select id,describe,org_id from line_fzman " + qx_linefz;
			rs = j_dao.executeQuery(sql);
			
			rs.last();
			count = rs.getRow();
			linefz = new LineFz[count];
			
			rs.beforeFirst();
			
			index = 0;
			while(rs.next()){
				linefz[index] = new LineFz();
				linefz[index].id = rs.getShort(1);
				linefz[index].desc = CommBase.CheckString(rs.getString(2));
				linefz[index].org_id = rs.getShort(3);
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			
			if(qx_org.isEmpty()){
				sql = "select c.org_id,c.line_fzman_id,c.id,c.describe from orgpara a,line_fzman b, conspara c where b.org_id=a.id and c.line_fzman_id=b.id and c.org_id=a.id and c.app_type="+appType+" order by a.id,b.id,c.id";
			}else{
				sql = "select c.org_id,c.line_fzman_id,c.id,c.describe from orgpara a,line_fzman b, conspara c where b.org_id=a.id and c.line_fzman_id=b.id and c.org_id=a.id and c.app_type="+appType+" and a.id=" + user.getOrgId() + " order by a.id,b.id,c.id";
			}
			rs = j_dao.executeQuery(sql);
			
			rs.last();
			count = rs.getRow();
			cons = new Cons[count];
			
			rs.beforeFirst();
			
			index = 0;
			while(rs.next()){
				cons[index] = new Cons();
				cons[index].org_id = rs.getShort(1);
				cons[index].linefz_id = rs.getShort(2);
				cons[index].id = rs.getShort(3);
				cons[index].desc = CommBase.CheckString(rs.getString(4));
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			
			if(qx_org.isEmpty()){
				sql = "select a.id,a.describe,b.org_id,b.line_fzman_id,a.cons_id from rtupara a,conspara b where a.cons_id=b.id and a.app_type=" + appType + " order by b.id,a.id";
			}else{
				sql = "select a.id,a.describe,b.org_id,b.line_fzman_id,a.cons_id from rtupara a,conspara b where a.cons_id=b.id and a.app_type=" + appType + " and b.org_id=" + user.getOrgId() + " order by b.id,a.id";
			}
			
			rs = j_dao.executeQuery(sql);
			
			rs.last();
			count = rs.getRow();
			rtu = new Rtu[count];
			
			rs.beforeFirst();
			
			index = 0;
			while(rs.next()){
				rtu[index] = new Rtu();
				rtu[index].id = rs.getInt(1);
				rtu[index].desc = CommBase.CheckString(rs.getString(2));
				rtu[index].org_id = rs.getShort(3);
				rtu[index].linefz_id = rs.getShort(4);
				rtu[index].cons_id = rs.getShort(5);
				
				index ++;
			}
			j_dao.closeRs(rs);
		}catch(SQLException e){
			j_dao.closeRs(rs);
			e.printStackTrace();
			result = "";
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><tree id=\"0\"><item id=\""+SDDef.GLOBAL_KE2+"\" text=\""+WebConfig.tree_glo+"\" open=\"1\" im0=\"station_name.gif\" im1=\"station_name.gif\" im2=\"station_name.gif\">");
		
		for (int o = 0; o < org.length; o++) {
			ret_buf.append("<item id=\""+YDTable.TABLECLASS_ORGPARA +"_"+org[o].id+"\" text=\""+org[o].desc+"\" open=\"\" im0=\"suo.gif\" im1=\"suo.gif\" im2=\"suo.gif\">");
			
			for (int l = 0; l < linefz.length; l++) {
				if(linefz[l].org_id == org[o].id){
					ret_buf.append("<item id=\""+YDTable.TABLECLASS_LINEFZMAN + "_"+linefz[l].id+"\" text=\""+linefz[l].desc+"\" open=\"\" im0=\"linkman.gif\" im1=\"linkman.gif\" im2=\"linkman.gif\">");
					
					for(int c = 0; c < cons.length; c++){
						if(cons[c].org_id == org[o].id && cons[c].linefz_id == linefz[l].id){
							ret_buf.append("<item id=\""+YDTable.TABLECLASS_CONSPARA + "_"+cons[c].id+"\" text=\""+cons[c].desc+"\" open=\"\" im0=\"user.gif\" im1=\"user.gif\" im2=\"user.gif\">");
							for (int r = 0; r < rtu.length; r++) {
								if(rtu[r].cons_id == cons[c].id){
									ret_buf.append("<item id=\""+YDTable.TABLECLASS_RTUPARA + "_"+rtu[r].id+"\" text=\""+rtu[r].desc+"\" im0=\"rtu.gif\" im1=\"rtu.gif\" />");
								}
							}
							ret_buf.append("</item>");
						}
					}

					ret_buf.append("</item>");
				}
			}
		
			ret_buf.append("</item>");
		}
		
		ret_buf.append("</item></tree>");
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
		
	}
	
	/**农排树结构*/
	private String npTree() {
		String stationName = WebConfig.tree_glo;
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?><tree id=\"0\"><item id=\""+SDDef.GLOBAL_KE2+"\" text=\""+stationName+"\" open=\"1\" im0=\"station_name.gif\" im1=\"station_name.gif\" im2=\"station_name.gif\">");
		
		String qx_org = "";
		YffManDef user = CommFunc.getYffMan();
		
		if(user.getRank() != 0){	//权限范围 0所有终端
			qx_org = " where id=" + user.getOrgId();
		}
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		Org org[] 		= null;
		Area area[]		= null;
		
		try{
			int count = 0, index = 0;
			String sql = "select count(a.id) from orgpara as a " + qx_org;
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
			j_dao.closeRs(rs);
			org = new Org[count];
			sql = "select a.id,a.describe from orgpara as a " + qx_org;
			rs = j_dao.executeQuery(sql);
			while(rs.next()){
				org[index] = new Org();
				org[index].id = rs.getShort(1);
				org[index].desc = CommFunc.CheckString(rs.getString(2));
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			sql = "select count(a.id) from areapara as a";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
			j_dao.closeRs(rs);
			
			area = new Area[count];
			sql = "select a.id,a.describe,a.org_id from areapara as a";
			rs = j_dao.executeQuery(sql);
			index = 0;
			while(rs.next()){
				area[index] = new Area();
				area[index].id = rs.getShort(1);
				area[index].desc = CommFunc.CheckString(rs.getString(2));
				area[index].org_id = rs.getShort(3);
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			for (int o = 0; o < org.length; o++) {
				ret_buf.append("<item id=\""+YDTable.TABLECLASS_ORGPARA +"_"+org[o].id+"\" text=\""+org[o].desc+"\" open=\"\" im0=\"suo.gif\" im1=\"suo.gif\" im2=\"suo.gif\">");
				for (int l = 0; l < area.length; l++) {
					if(area[l].org_id == org[o].id){
						ret_buf.append("<item id=\""+YDTable.TABLECLASS_AREAPARA + "_"+area[l].id+"\" text=\""+area[l].desc+"\" open=\"\" im0=\"area.gif\" im1=\"area.gif\" im2=\"area.gif\"></item>");
					}
				}
			
				ret_buf.append("</item>");
			}
			
			ret_buf.append("</item></tree>");
			
		} catch (Exception e) {
			
		}
		
		return ret_buf.toString();
	}
	
	/**片区选择*/
	public String selArea() {
		String stationName = WebConfig.tree_glo;
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?><tree id=\"0\"><item id=\""+SDDef.GLOBAL_KE2+"\" text=\""+stationName+"\" open=\"1\" im0=\"station_name.gif\" im1=\"station_name.gif\" im2=\"station_name.gif\">");

		String qx_org = "";
		YffManDef user = CommFunc.getYffMan();
		
		if(user.getRank() != 0){	//权限范围 0所有终端
			qx_org = " where id=" + user.getOrgId();
		}
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		Org org[] 		= null;
		Area area[]		= null;
		
		try{
			int count = 0, index = 0;
			String sql = "select count(a.id) from orgpara as a " + qx_org;
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
			j_dao.closeRs(rs);
			org = new Org[count];
			sql = "select a.id,a.describe from orgpara as a " + qx_org;
			rs = j_dao.executeQuery(sql);
			while(rs.next()){
				org[index] = new Org();
				org[index].id = rs.getShort(1);
				org[index].desc = CommFunc.CheckString(rs.getString(2));
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			sql = "select count(a.id) from areapara as a";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
			j_dao.closeRs(rs);
			
			area = new Area[count];
			sql = "select a.id,a.describe,a.org_id from areapara as a";
			rs = j_dao.executeQuery(sql);
			index = 0;
			while(rs.next()){
				area[index] = new Area();
				area[index].id = rs.getShort(1);
				area[index].desc = CommFunc.CheckString(rs.getString(2));
				area[index].org_id = rs.getShort(3);
				
				index ++;
			}
			j_dao.closeRs(rs);
			
			for (int o = 0; o < org.length; o++) {
				ret_buf.append("<item id=\""+YDTable.TABLECLASS_ORGPARA +"_"+org[o].id+"\" text=\""+org[o].desc+"\" open=\"\" im0=\"suo.gif\" im1=\"suo.gif\" im2=\"suo.gif\">");
				for (int l = 0; l < area.length; l++) {
					if(area[l].org_id == org[o].id){
						ret_buf.append("<item id=\""+YDTable.TABLECLASS_AREAPARA + "_"+area[l].id+"\" text=\""+area[l].desc+"\" open=\"\" im0=\"area.gif\" im1=\"area.gif\" im2=\"area.gif\"></item>");
					}
				}
			
				ret_buf.append("</item>");
			}
			
			ret_buf.append("</item></tree>");
			result = ret_buf.toString();
			
		} catch (Exception e) {
			
		}	
		return SUCCESS;
	}
	
	
	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getAppType() {
		return appType;
	}
}
