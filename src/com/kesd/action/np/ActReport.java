package com.kesd.action.np;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.RoundEnvironment;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActReport extends ActionSupport {

	private static final long serialVersionUID = -5150355477597234145L;

	private String result;
	private String hz;
	private String field;
	private int count = 1;
	private double[] total_money = { 0, 0, 0, 0 };
	private int tt_gdcs = 0;

	private static class OpmanSum {
		public String org_desc = null;
		public int org_id = -1;
		public String oper_man = null;
		public int salenum = 0;
		public double pay_money = 0.0;
		public double zb_money = 0.0;
		public double js_money = 0.0;
		public double tot_money = 0.0;
	}

	public List<OpmanSum> opmansum = new ArrayList<OpmanSum>();

	public int findOpmanSum(int org_id, String oper_man) {
		for (int i = 0; i < opmansum.size(); i++) {
			if ((opmansum.get(i).org_id == org_id)
					&& (opmansum.get(i).oper_man.equalsIgnoreCase(oper_man)))
				return i;
		}
		return -1;
	}

	private static class AreaSum {
		public String org_desc = null;
		public int org_id = -1;
		public String area_desc = null;
		public int salenum = 0;
		public double pay_money = 0.0;
		public double zb_money = 0.0;
		public double js_money = 0.0;
		public double tot_money = 0.0;
	}

	public List<AreaSum> areasum = new ArrayList<AreaSum>();

	/**
	 * 用电汇总--数据承载实体类
	 * */
	class YDHZObj {
		public String org_desc; // 营业点
		public String area_desc; // 片区名称
		public String cons_no; // 客户编号
		public String cons_desc; // 客户名称
		public String card_no; // 卡号
		public double gdje_total; // 累计购电总金额
		public int last_gddate; // 最后购电时间
		public double gdje; // 累计购电金额(时间段内)
		public double syje; // 剩余金额
		public int last_yddate; // 最后用电时间

		public YDHZObj(String org_desc, String area_desc, String cons_no,
				String cons_desc, String card_no) {
			this.org_desc = org_desc;
			this.area_desc = area_desc;
			this.cons_no = cons_no;
			this.cons_desc = cons_desc;
			this.card_no = card_no;
		}
	}

	/**
	 * 用电汇总--数据库数据载体
	 * */
	class GDValueObj {
		public String card_no;
		public int date;
		public double value;
		public double value1;
	}

	public List<YDHZObj> ydhz_list = new ArrayList<YDHZObj>(); // 用电汇总集合：存储查询到的数据

	/**
	 * 购电明细表
	 */
	public String gdmxReport() {

		StringBuffer ret = new StringBuffer();

		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String area = j_obj.getString("area");

			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				ret.append(mulGdmx(sdate, edate, czy, org, area));

				isyear++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			hz = "{jfje:\"" + CommFunc.FormatBDDLp000(total_money[0])
					+ "\",zbje:\"" + CommFunc.FormatBDDLp000(total_money[1])
					+ "\",jsje:\"" + CommFunc.FormatBDDLp000(total_money[2])
					+ "\",zje:\"" + CommFunc.FormatBDDLp000(total_money[3])
					+ "\"}";
		}
		return SDDef.SUCCESS;
	}

	private String mulGdmx(String sdate, String edate, String czy, String org,
			String area) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();

		try {

			sql = "select fp.id fid,fp.area_id aid,fp.farmer_no,fp.describe farm_desc,fp.card_no,n.pay_money,n.zb_money,n.othjs_money,n.op_man,n.op_date,n.op_time from yddataben.dbo.NYff"
					+ sdate.substring(0, 4)
					+ " n,areapara ap,farmerpara fp where n.area_id=ap.id and n.farmer_id=fp.id and fp.area_id=ap.id and (n.op_type=1 or n.op_type=2 or n.op_type=3 or n.op_type=5 or n.op_type=6 or n.op_type=9 or n.op_type=50) and n.op_date>="
					+ sdate + " and n.op_date<=" + edate;

			if (!czy.isEmpty()) {
				sql += " and n.op_man like '%" + czy + "%'";
			}

			if (!org.equals("-1")) {
				sql += " and ap.org_id=" + org;
			}

			if (!area.equals("-1")) {
				sql += " and ap.id=" + area;
			}
			sql += " order by fp.id,fp.area_id,n.op_date,n.op_time";

			rs = j_dao.executeQuery(sql);
			double all_money = 0, tmp;

			while (rs.next()) {

				if (count > 10000)
					break;

				all_money = 0;

				ret
						.append("{id:\""
								+ (rs.getInt("aid") + "_" + rs.getInt("fid")
										+ "_" + count) + "\",data:[" + count++
								+ ",");

				ret.append("\""
						+ CommBase.CheckString(rs.getString("farmer_no"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("farm_desc"))
						+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("card_no"))
						+ "\",");

				tmp = rs.getDouble("pay_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(tmp) + "\",");
				all_money += tmp;
				total_money[0] += tmp;

				tmp = rs.getDouble("zb_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(tmp) + "\",");
				all_money += tmp;
				total_money[1] += tmp;

				tmp = rs.getDouble("othjs_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(tmp) + "\",");
				all_money += tmp;
				total_money[2] += tmp;

				total_money[3] += all_money;

				ret.append("\"" + CommFunc.FormatBDDLp000(all_money) + "\",");

				// ret.append("\""+avg_price(rs.getInt("fee_type"),rs.getDouble("rated_z"),rs.getDouble("ratef_j"),rs.getDouble("ratef_f"),rs.getDouble("ratef_p"),rs.getDouble("ratef_g"))+"\",");

				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("op_date"))
						+ CommFunc.FormatToHMS(rs.getString("op_time"), 2)
						+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("op_man"))
						+ "\",");

				ret.append("]},");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + sql);
		} finally {
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}

	/**
	 * 用户购电表
	 */
	public String yhgdReport() {

		StringBuffer ret = new StringBuffer();

		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String yhbh = j_obj.getString("yhbh");
			String org = j_obj.getString("org");
			String area = j_obj.getString("area");

			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				ret.append(mulYhgd(sdate, edate, yhbh, org, area));

				isyear++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			hz = "{jfcs:" + tt_gdcs + ",jfje:\""
					+ CommFunc.FormatBDDLp000(total_money[0]) + "\",zbje:\""
					+ CommFunc.FormatBDDLp000(total_money[1]) + "\",jsje:\""
					+ CommFunc.FormatBDDLp000(total_money[2]) + "\",zje:\""
					+ CommFunc.FormatBDDLp000(total_money[3]) + "\"}";
		}
		return SDDef.SUCCESS;
	}

	private String mulYhgd(String sdate, String edate, String yhbh, String org,
			String area) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		try {
			String tmp = "";
			if (!area.equals("-1")) {
				tmp += " and area_id=" + area;
			}

			sql = "select a.*,b.describe farmer_desc,b.farmer_no,b.card_no from (select cast(substring(key_id,1,8) as int) as area_id,"
					+ "cast(substring(key_id,9,6) as int) as farmer_id,count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money,"
					+ "sum(zb_money) as zb_money from (select cast(area_id as char(8))+cast(farmer_id as char(6)) as key_id,a.* from yddataben.dbo.NYff"
					+ sdate.substring(0, 4)
					+ " a "
					+ "where (op_type=1 or op_type=2 or op_type=3 or op_type=5 or op_type=6 or op_type=9 or op_type=50) "
					+ tmp
					+ " and op_date>="
					+ sdate
					+ " and op_date<="
					+ edate
					+ ") a group by key_id) a,"
					+ "farmerpara b,areapara c where a.farmer_id=b.id and a.area_id=b.area_id and b.area_id=c.id ";

			if (!yhbh.isEmpty()) {
				sql += " and b.farmer_no like '%" + yhbh + "%'";
			}

			if (!org.equals("-1")) {
				sql += " and c.org_id=" + org;
			}

			rs = j_dao.executeQuery(sql);

			double all_money = 0, money = 0;

			int gdcs = 0;
			;

			while (rs.next()) {

				if (count > 10000)
					break;

				all_money = 0;

				ret.append("{id:\"" + count + "\",data:[" + count++ + ",");

				ret.append("\""
						+ CommBase.CheckString(rs.getString("farmer_no"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("farmer_desc"))
						+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("card_no"))
						+ "\",");

				gdcs = rs.getInt("gdcs");
				ret.append("\"" + gdcs + "\",");
				tt_gdcs += gdcs;

				money = rs.getDouble("pay_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[0] += money;

				money = rs.getDouble("zb_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[1] += money;

				money = rs.getDouble("js_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[2] += money;

				total_money[3] += all_money;

				ret.append("\"" + CommFunc.FormatBDDLp000(all_money) + "\",");

				// ret.append("\""+avg_price(rs.getInt("fee_type"),rs.getDouble("rated_z"),rs.getDouble("ratef_j"),rs.getDouble("ratef_f"),rs.getDouble("ratef_p"),rs.getDouble("ratef_g"))+"\",");

				ret.append("]},");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + sql);
			return "";
		} finally {
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}

	/**
	 * 购电汇总表
	 */
	public String gdhzReport() {

		StringBuffer ret = new StringBuffer();

		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String org = j_obj.getString("org");
			String area = j_obj.getString("area");

			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				ret.append(mulGdhz(sdate, edate, org, area));

				isyear++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			hz = "{jfcs:" + tt_gdcs + ",jfje:\""
					+ CommFunc.FormatBDDLp000(total_money[0]) + "\",zbje:\""
					+ CommFunc.FormatBDDLp000(total_money[1]) + "\",jsje:\""
					+ CommFunc.FormatBDDLp000(total_money[2]) + "\",zje:\""
					+ CommFunc.FormatBDDLp000(total_money[3]) + "\"}";
		}
		return SDDef.SUCCESS;
	}

	public String mulGdhz(String sdate, String edate, String org, String area) {

		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();

		try {

			String tmp = "";
			if (!area.equals("-1")) {
				tmp = " and a.area_id=" + area;
			}

			sql = "select b.describe as org_desc,a.*  from (select cast(substring(key_id,1,8) as int) as op_date,cast(substring(key_id,9,6) as int) as org_id,"
					+ "count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money from ("
					+ "select cast(a.op_date as char(8))+cast(b.org_id as char(6)) as key_id,a.* from yddataben.dbo.NYff"
					+ sdate.substring(0, 4)
					+ " a,areapara b,farmerpara c "
					+ "where (a.op_type=1 or a.op_type=2 or a.op_type=3 or a.op_type=5 or a.op_type=6 or a.op_type=9 or a.op_type=50) "
					+ tmp
					+ " and a.area_id=b.id and a.farmer_id=c.id and c.area_id=b.id and a.op_date>="
					+ sdate + " and a.op_date<=" + edate;

			sql += ") a group by key_id) a,orgpara b where a.org_id=b.id ";

			if (!org.equals("-1")) {
				sql += " and b.id=" + org;
			}
			sql += " order by b.id,a.op_date";

			rs = j_dao.executeQuery(sql);
			double all_money = 0, money = 0;

			int gdcs = 0;
			;

			while (rs.next()) {

				if (count > 10000)
					break;

				all_money = 0;

				ret.append("{id:\"" + count + "\",data:[" + count++ + ",");

				ret.append("\""
						+ CommFunc.FormatToYMD((rs.getString("op_date")))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("org_desc"))
						+ "\",");

				gdcs = rs.getInt("gdcs");
				ret.append("\"" + gdcs + "\",");
				tt_gdcs += gdcs;

				money = rs.getDouble("pay_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[0] += money;

				money = rs.getDouble("zb_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[1] += money;

				money = rs.getDouble("js_money");
				ret.append("\"" + CommFunc.FormatBDDLp000(money) + "\",");
				all_money += money;
				total_money[2] += money;

				total_money[3] += all_money;

				ret.append("\"" + CommFunc.FormatBDDLp000(all_money) + "\",");

				ret.append("]},");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + sql);
			return "";
		} finally {
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}

	/**
	 * 操作员购电汇总表
	 */
	public String opmanSalsumReport() {

		StringBuffer ret = new StringBuffer();
		count = 1;
		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String org = j_obj.getString("org");
			// String area = j_obj.getString("area");

			opmansum.clear();
			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				OpmanSalsum(sdate, edate, org);

				isyear++;
			}

			for (int i = 0; i < opmansum.size(); i++) {

				// ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				ret.append("{id:\"" + opmansum.get(i).org_id + "_" + count
						+ "\",data:[" + count++ + ",");

				ret.append("\"" + opmansum.get(i).org_desc + "\",");
				ret.append("\"" + opmansum.get(i).oper_man + "\",");
				ret.append("\"" + opmansum.get(i).salenum + "\",");
				ret.append("\"" + opmansum.get(i).pay_money + "\",");
				ret.append("\"" + opmansum.get(i).zb_money + "\",");
				ret.append("\"" + opmansum.get(i).js_money + "\",");
				ret.append("\"" + opmansum.get(i).tot_money + "\",");
				ret.append("]},");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			hz = "{jfcs:" + tt_gdcs + ",jfje:\""
					+ CommFunc.FormatBDDLp000(total_money[0]) + "\",zbje:\""
					+ CommFunc.FormatBDDLp000(total_money[1]) + "\",jsje:\""
					+ CommFunc.FormatBDDLp000(total_money[2]) + "\",zje:\""
					+ CommFunc.FormatBDDLp000(total_money[3]) + "\"}";
		}

		return SDDef.SUCCESS;
	}

	public int OpmanSalsum(String sdate, String edate, String org) {

		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		// StringBuffer ret = new StringBuffer();

		try {

			String tmp = "";
			sql = "select o.id as ogr_id, o.describe as org_desc, a.* from ( "
					+ "select o.id as orgid, n.op_man, count(buy_times) as paysum, sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money, sum(all_money) as all_money "
					+ "from yddataben.dbo.NYff"
					+ sdate.substring(0, 4)
					+ "  as n, farmerpara as f, areapara as a, orgpara as o "
					+ "where n.area_id = f.area_id and n.farmer_id = f.id and n.area_id = a.id and a.org_id = o.id "
					+ "and(n.op_type=1 or n.op_type=2 or n.op_type=3 or n.op_type=5 or n.op_type=6 or n.op_type=9 or n.op_type=50) "
					+ "and n.op_date>=" + sdate + " and n.op_date<=" + edate;

			if (!org.equals("-1")) {
				sql += " and o.id =" + org;
			}

			sql += " group by o.id, n.op_man"
					+ " ) as a, orgpara as o where o.id = a.orgid  order by o.id";

			rs = j_dao.executeQuery(sql);

			while (rs.next()) {
				if (count > 10000)
					break;
				OpmanSum tmpopersum = new OpmanSum();

				tmpopersum.org_id = rs.getInt("ogr_id");
				tmpopersum.org_desc = CommBase.CheckString(rs
						.getString("org_desc"));
				tmpopersum.oper_man = CommBase.CheckString(rs
						.getString("op_man"));
				tmpopersum.salenum = rs.getInt("paysum");
				tt_gdcs += tmpopersum.salenum;
				tmpopersum.pay_money = rs.getDouble("pay_money");
				total_money[0] += tmpopersum.pay_money;
				tmpopersum.zb_money = rs.getDouble("zb_money");
				total_money[1] += tmpopersum.zb_money;
				tmpopersum.js_money = rs.getDouble("js_money");
				total_money[2] += tmpopersum.js_money;
				tmpopersum.tot_money = rs.getDouble("all_money");
				total_money[3] += tmpopersum.tot_money;

				opmansum.add(tmpopersum);

				/*
				 * int idx = findOpmanSum(tmpopersum.org_id,
				 * tmpopersum.oper_man); //从已经保存的记录中找 if (idx < 0)
				 * opmansum.add(tmpopersum); else { opmansum.get(idx).salenum +=
				 * tmpopersum.salenum; opmansum.get(idx).pay_money +=
				 * tmpopersum.pay_money; opmansum.get(idx).zb_money +=
				 * tmpopersum.zb_money; opmansum.get(idx).js_money +=
				 * tmpopersum.js_money; opmansum.get(idx).tot_money +=
				 * tmpopersum.tot_money; }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + sql);
			return -1;
		} finally {
			j_dao.closeRs(rs);
		}
		return 1;
	}

	/**
	 * 时间段内片区购电累计信息 20130426 zp
	 * */
	public String areaSalSumReport() {
		StringBuffer ret = new StringBuffer();
		count = 1;
		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String org = j_obj.getString("org");
			String area = j_obj.getString("area");

			areasum.clear();
			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				AreaSalsum(sdate, edate, org, area);

				isyear++;
			}

			for (int i = 0; i < areasum.size(); i++) {
				ret.append("{id:\"" + areasum.get(i).org_id + "_" + count
						+ "\",data:[" + count++ + ",");

				ret.append("\"" + areasum.get(i).org_desc + "\",");
				ret.append("\"" + areasum.get(i).area_desc + "\",");
				ret.append("\"" + areasum.get(i).salenum + "\",");
				ret.append("\"" + areasum.get(i).pay_money + "\",");
				ret.append("\"" + areasum.get(i).zb_money + "\",");
				ret.append("\"" + areasum.get(i).js_money + "\",");
				ret.append("\"" + areasum.get(i).tot_money + "\",");
				ret.append("]},");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			hz = "{jfcs:" + tt_gdcs + ",jfje:\""
					+ CommFunc.FormatBDDLp000(total_money[0]) + "\",zbje:\""
					+ CommFunc.FormatBDDLp000(total_money[1]) + "\",jsje:\""
					+ CommFunc.FormatBDDLp000(total_money[2]) + "\",zje:\""
					+ CommFunc.FormatBDDLp000(total_money[3]) + "\"}";
		}
		return SDDef.SUCCESS;
	}

	/**
	 * 时间段内片区售电汇总sql语句
	 * */
	public int AreaSalsum(String sdate, String edate, String org, String area) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		StringBuffer ret = new StringBuffer();

		ret
				.append("select o.describe as org_desc,a.org_id, a.describe as area_desc,count(buy_times) as paysum, sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money, sum(all_money) as all_money");
		ret.append(" from yddataben.dbo.NYff" + sdate.substring(0, 4)
				+ " nyff,areapara a, orgpara o");
		ret
				.append(" where (nyff.op_type=1 or nyff.op_type=2 or nyff.op_type=3 or nyff.op_type=5 or nyff.op_type=6 or nyff.op_type=9 or nyff.op_type=50) and nyff.area_id = a.id and a.org_id = o.id");
		ret.append(" and nyff.op_date >= " + sdate + " and nyff.op_date <= "
				+ edate);

		if (!org.equals("-1")) {
			ret.append(" and o.id =" + org);
		}
		if (!area.equals("-1")) {
			ret.append(" and a.id =" + area);
		}
		ret.append(" group by o.describe,a.org_id, a.describe");
		rs = j_dao.executeQuery(ret.toString());

		try {
			while (rs.next()) {
				if (count > 10000)
					break;
				AreaSum tmpareasum = new AreaSum();

				tmpareasum.org_id = rs.getInt("org_id");
				tmpareasum.org_desc = CommBase.CheckString(rs
						.getString("org_desc"));
				tmpareasum.area_desc = CommBase.CheckString(rs
						.getString("area_desc"));
				tmpareasum.salenum = rs.getInt("paysum");
				tt_gdcs += tmpareasum.salenum;
				tmpareasum.pay_money = rs.getDouble("pay_money");
				total_money[0] += tmpareasum.pay_money;
				tmpareasum.zb_money = rs.getDouble("zb_money");
				total_money[1] += tmpareasum.zb_money;
				tmpareasum.js_money = rs.getDouble("js_money");
				total_money[2] += tmpareasum.js_money;
				tmpareasum.tot_money = rs.getDouble("all_money");
				total_money[3] += tmpareasum.tot_money;

				areasum.add(tmpareasum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + ret.toString());
			return -1;
		} finally {
			j_dao.closeRs(rs);
		}
		return 1;
	}

	/**
	 * 用电记录：区域时间段内每笔用电记录的详细信息
	 * */
	public String khjeReport() {

		StringBuffer ret = new StringBuffer();

		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");

			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));

			String isday = sdate.substring(4);
			String ieday = edate.substring(4);

			int year = ieyear - isyear + 1;

			ret.append("{rows:[");

			String org = j_obj.getString("org");
			String area = j_obj.getString("area");
			String khbh = j_obj.getString("khbh");
			String khmc = j_obj.getString("khmc");
			String cardno = j_obj.getString("cardno");
			String meterdesc = j_obj.getString("meterdesc");
			String meteraddr = j_obj.getString("meteraddr");

			for (int i = 0; i < year; i++) {

				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				} else if (i == year - 1) {
					edate = ieyear + ieday;
				}

				ret.append(mulKhje(sdate, edate, org, area, khbh, khmc, cardno,
						meterdesc, meteraddr));

				isyear++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 1) {
			result = "";
		} else {

			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();

			// hz =
			// "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
		}
		return SDDef.SUCCESS;
	}

	private String mulKhje(String sdate, String edate, String org, String area,
			String khbh, String khmc, String cardno, String meterdesc,
			String meteraddr) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();

		try {

			sql = "select o.id org_id,ap.id area_id,fp.id farmer_id,o.describe org_desc,ap.describe area_desc,fp.farmer_no,fp.describe farmer_desc,fp.card_no,mt.describe meter_desc,n.use_money,n.remain_money,n.begin_date,n.begin_time,n.end_date,n.end_time,n.fee,n.use_dl,n.zero_dl,n.farmer_state "
					+ "from yddataben.dbo.npyd_record"
					+ sdate.substring(0, 4)
					+ " n,meter_extparanp mnp,farmerpara fp,meterpara mt,areapara ap,orgpara o "
					+ " where n.rtu_id=mnp.rtu_id and n.mp_id=mnp.mp_id and mnp.area_id=ap.id and mnp.area_id=fp.area_id and mnp.rtu_id=mt.rtu_id and mnp.mp_id=mt.mp_id and fp.area_id=ap.id and n.area_id=ap.id and n.farmer_id=fp.id and ap.org_id=o.id"
					+ " and n.begin_date>="
					+ sdate
					+ " and n.begin_date<="
					+ edate;

			if (!org.equals("-1")) {
				sql += " and o.id=" + org;
			}

			if (!area.equals("-1")) {
				sql += " and ap.id=" + area;
			}
			if (!khbh.isEmpty()) {
				sql += " and fp.farmer_no like '%" + khbh + "%'";
			}
			if (!khmc.isEmpty()) {
				sql += " and fp.describe like '%" + khmc + "%'";
			}
			if (!cardno.isEmpty()) {
				sql += " and fp.card_no like '%" + cardno + "%'";
			}
			if (!meterdesc.equals("-1")) {
				String[] mt = meterdesc.split("_");
				sql += " and mt.mp_id=" + mt[1] + " and mt.rtu_id=" + mt[0];
			}
			if (!meteraddr.isEmpty()) {
				sql += " and mt.comm_addr like '%" + meteraddr + "%'";
			}
			sql += " order by o.id,ap.id,fp.id,n.begin_date,n.begin_time";

			rs = j_dao.executeQuery(sql);

			while (rs.next()) {

				if (count > 10000)
					break;

				ret.append("{id:\""
						+ (rs.getInt("org_id") + "_" + rs.getInt("area_id")
								+ "_" + rs.getInt("farmer_id") + "_" + count)
						+ "\",data:[");

				ret.append("\""
						+ CommBase.CheckString(rs.getString("org_desc"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("area_desc"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("farmer_no"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("farmer_desc"))
						+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("card_no"))
						+ "\",");
				ret.append("\""
						+ CommBase.CheckString(rs.getString("meter_desc"))
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatToYMD(rs.getString("begin_date"))
						+ CommFunc.FormatToHMS(rs.getString("begin_time"), 2)
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatToYMD(rs.getString("end_date"))
						+ CommFunc.FormatToHMS(rs.getString("end_time"), 2)
						+ "\",");
				ret.append("\"" + CommFunc.FormatBDDLp000(rs.getDouble("fee"))
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatBDDLp000(rs.getDouble("use_money"))
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatBDDLp000(rs.getDouble("remain_money"))
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatBDDLp000(rs.getDouble("use_dl"))
						+ "\",");
				ret.append("\""
						+ CommFunc.FormatBDDLp000(rs.getDouble("zero_dl"))
						+ "\",");

				// 用电状态: 0 正常 1 系统停电 2 无脉冲自动拉闸 3人为锁表
				ret.append("\""
						+ Rd.getDict(Dict.DICTITEM_FARMER_STATE, rs
								.getShort("farmer_state")) + "\"");

				ret.append("]},");

				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句：\n" + sql);
		} finally {
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}

	// qjl add 20150204 start 添加
	 /**
	 * 差额汇总--数据承载实体类
	 * */
	class GRtuday_fztObj {
		public int org_id;
		public int cons_id;
		public int rtu_id;
		public String rtu_desc; // 名称
		public int day_fztime;
		
		public String org_desc; 	//供电所名称
		public String cons_desc;	//台区名称
		public double dl;			//电量
		public double ysdf;			//抄见应收电费
		public double totbuy_dl;	//表内合计电量
		public double totmoney;		//表内合计售电电费
		public double ce_dl;		//差额电量
		public double ce_df;		//差额电费
		public double ratio_dl;		//电量差额百分比
		public double ratio_df;		//电费差额百分比
	}

	public List<GRtuday_fztObj> Rtuday_fzt = new ArrayList<GRtuday_fztObj>();

	class GMeterParaObj {
		public int 	  rtu_id;		//终端id
		public short  mp_id;		//测点id
		public String org_desc; 	//供电所名称
		public String cons_desc;	//台区名称
		public String mp_desc; 		//测点名称
		public String meter_desc;	//电表名称
		public String meter_addr;	//电表地址
		public double bg_bd;		//起始表底
		public double end_bd;		//结束表底
		public double bl;			//倍率
		public double dl;			//电量
		public double feiratez;		//电价
		public double ysdf;			//抄见应收电费
		public double totbuy_dl;	//表内合计电量
		public double totmoney;		//表内合计售电电费
		public double ce_dl;		//差额电量
		public double ce_df;		//差额电费
		public double ratio_dl;		//电量差额百分比
		public double ratio_df;		//电费差额百分比
		public double pt;			//pt变比
		public double ct;			//ct变比
		public short  feiid; 		//费率id
		public double feiratej;		//复费率尖 保留
		public double feiratef;		//复费率峰
		public double feiratep;		//复费率平
		public double feirateg;		//复费率谷
	}
	
	public List<GMeterParaObj> Meterpara = new ArrayList<GMeterParaObj>();

	/**
	 * 用电记录：按台区将所属的所有电表电量进行汇总，按照单一电价折算成金额，并汇总相对应的所有电表的用电金额，
	 * 按照一定的区域时间进行对比，形成电费差额或百分比
	 * */
	public String dfceReport() {
		JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		JSONObject jsonTmp = new JSONObject();
		JSONArray jarrayTmp = new JSONArray();		
		try {
			JSONObject j_obj = JSONObject.fromObject(result);
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			String orgid = j_obj.getString("org"); 			//供电所
			String consid = j_obj.getString("cons"); 		//台区
			
			GetDayFzt(orgid, consid); 						//获取冻结时间
			GetMeterPara(orgid, consid, sdate, edate); 		//获取电价、pt和ct变比、表底信息
			GetYdRcd(orgid, consid, sdate, edate); 			//获得表内合计电量和表内合计售电电费
			mulDfce();			//计算倍率、电量等参数
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		if (Meterpara.size() == 0) {
			result = "";
		} else {
			int realm_num = 0;
			for (int j = 0; j < Rtuday_fzt.size(); j++) {
				realm_num = 0;
				for (int i = 0; i < Meterpara.size(); i++) {
					if (Meterpara.get(i).rtu_id != Rtuday_fzt.get(j).rtu_id) continue;
					realm_num ++;
					jsonTmp.put("id", Meterpara.get(i).rtu_id + "_" + count);
//					jarrayTmp.add(Meterpara.get(i).org_desc);											//供电所名称
//					jarrayTmp.add(Meterpara.get(i).cons_desc);											//台区名称
					jarrayTmp.add(Rtuday_fzt.get(j).org_desc);											//供电所名称
					jarrayTmp.add(Rtuday_fzt.get(j).cons_desc);											//台区名称
					
					jarrayTmp.add(Meterpara.get(i).meter_desc+ "[" +Meterpara.get(i).mp_id + "]");											//电表名称
					jarrayTmp.add(Meterpara.get(i).meter_addr);											//电表地址
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).bg_bd));						//起始表底
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).end_bd));					//结束表底
					jarrayTmp.add(CommFunc.FormatBDDLp00(Meterpara.get(i).bl));							//倍率
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).dl));						//电量
					jarrayTmp.add(CommFunc.CheckString(Meterpara.get(i).feiratez));					//电价
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).ysdf));						//抄见应收电费
//					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).totbuy_dl));					//表内合计电量
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).totmoney));					//表内合计售电电费
//					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).ce_dl));						//差额电量
					jarrayTmp.add(CommFunc.FormatBDDLp000(Meterpara.get(i).ce_df));						//差额电费
//					jarrayTmp.add(CommFunc.FormatBDDLp00(Meterpara.get(i).ratio_dl)+ "%");				//电量差额百分比// + "%");
					jarrayTmp.add(CommFunc.FormatBDDLp00(Meterpara.get(i).ratio_df)+ "%");  			//电费差额百分比// + "%");
					
					jsonTmp.put("data", jarrayTmp);
					jarray.add(jsonTmp);
					jarrayTmp.clear();
					jsonTmp.clear();
					count ++;
				}
				
				if (realm_num > 0) {
					jsonTmp.put("id", Rtuday_fzt.get(j).rtu_id + "_" + count + "_t");
					jarrayTmp.add(Rtuday_fzt.get(j).org_desc);											//供电所名称
					jarrayTmp.add(Rtuday_fzt.get(j).cons_desc);											//台区名称
					jarrayTmp.add("合计");																//电表名称
					jarrayTmp.add("");																	//电表地址
					jarrayTmp.add("");																	//起始表底
					jarrayTmp.add("");																	//结束表底
					jarrayTmp.add("");																	//倍率
					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).dl));						//电量
					jarrayTmp.add("");																	//电价
					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).ysdf));						//抄见应收电费
//					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).totbuy_dl));				//表内合计电量
					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).totmoney));					//表内合计售电电费
//					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).ce_dl));					//差额电量
					jarrayTmp.add(CommFunc.FormatBDDLp000(Rtuday_fzt.get(j).ce_df));					//差额电费
//					jarrayTmp.add(CommFunc.FormatBDDLp00(Rtuday_fzt.get(j).ratio_dl));// + "%");		//电量差额百分比
					jarrayTmp.add(CommFunc.FormatBDDLp00(Rtuday_fzt.get(j).ratio_df)+ "%");				//电费差额百分比
					jsonTmp.put("data", jarrayTmp);
					jarray.add(jsonTmp);
					jarrayTmp.clear();
					jsonTmp.clear();
					count ++;
				}
				
			}		
			json.put("rows", jarray);
			result = json.toString();
		}
		return SDDef.SUCCESS;
	}

	private boolean GetDayFzt(String orgid, String consid) {

		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select org.id as org_id,org.describe org_desc,cons.describe cons_desc,rtu.cons_id,rtu.id as rtu_id,rtu.describe as rtu_desc,fz.day_fztime from rtupara rtu,fzcb_template fz,orgpara org,conspara cons where rtu.fzcb_id = fz.id and rtu.cons_id = cons.id and cons.org_id=org.id ";
			if (!orgid.equals("-1")) {
				sql += " and org.id=" + orgid;
			}
			if(!consid.equals("-1")){
				sql += " and rtu.cons_id=" + consid;
			}
			sql += " order by rtu_id";
			rs = j_dao.executeQuery(sql);

			Rtuday_fzt.clear();
			while (rs.next()) {
				GRtuday_fztObj tmprtuday_fzt = new GRtuday_fztObj();
				tmprtuday_fzt.org_id = rs.getInt("org_id"); // 供电所id
				tmprtuday_fzt.cons_id = rs.getInt("cons_id"); // 居民区id
				tmprtuday_fzt.rtu_id = rs.getInt("rtu_id"); // 终端id
				tmprtuday_fzt.day_fztime = rs.getInt("day_fztime"); // 冻结时间
				tmprtuday_fzt.rtu_desc = rs.getString("rtu_desc"); // 终端描述
				
				tmprtuday_fzt.org_desc = rs.getString("org_desc");
				tmprtuday_fzt.cons_desc = rs.getString("cons_desc");
				
				Rtuday_fzt.add(tmprtuday_fzt);
			}
		} catch (Exception e) {
		} finally {
			j_dao.closeRs(rs);
		}

		return true;
	}

	private boolean GetMeterPara(String orgid, String consid, String bgymd,String endymd) {
		if (Rtuday_fzt.size() <= 0)
			return false;

		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select rtu.id rtu_id,mp.id mp_id,mp.describe mp_desc,mp.pt_ratio pt,mp.ct_ratio ct,nppay.feeproj_id,rate.rated_z,meter.describe meter_desc,meter.comm_addr from rtupara rtu,mppara mp,nppay_para nppay,yffratepara rate,orgpara org,conspara cons,meterpara meter where rtu.id = mp.rtu_id and nppay.rtu_id = rtu.id and nppay.mp_id = mp.id and nppay.feeproj_id = rate.id and rate.fee_type = "
					+ SDDef.YFF_FEETYPE_DFL +" and rtu.cons_id = cons.id and org.id = cons.org_id and meter.rtu_id = rtu.id and meter.mp_id = mp.id and meter.prepayflag = 1";
			if (!orgid.equals("-1")) {
				sql += " and org.id=" + orgid;
			}
			if(!consid.equals("-1")){
				sql += " and rtu.cons_id=" + consid;
			}
			sql += " order by rtu_id";
			rs = j_dao.executeQuery(sql);
			// 非单费率找不到  是否 要处理 ？？？？？？？
			Meterpara.clear();
			while (rs.next()) {

				GMeterParaObj tmpMeterpara = new GMeterParaObj();

				tmpMeterpara.rtu_id = rs.getInt("rtu_id"); // 终端id
				tmpMeterpara.mp_id = rs.getShort("mp_id"); // 测点id
				tmpMeterpara.mp_desc = rs.getString("mp_desc"); // 测点描述
				tmpMeterpara.pt = rs.getDouble("pt"); // pt变比
				tmpMeterpara.ct = rs.getDouble("ct"); // ct变比
				tmpMeterpara.feiid = rs.getShort("feeproj_id"); // 费率方案id
				tmpMeterpara.feiratez = rs.getDouble("rated_z"); // 费率 电价
//			tmpMeterpara.org_desc = rs.getString("org_desc");
//			tmpMeterpara.cons_desc = rs.getString("cons_desc");
				tmpMeterpara.meter_desc = rs.getString("meter_desc");
				tmpMeterpara.meter_addr = rs.getString("comm_addr");
				Meterpara.add(tmpMeterpara);
			}
		} catch (Exception e) {
		} finally {
			j_dao.closeRs(rs);
		}

		for (int i = 0; i < Rtuday_fzt.size(); i++) {
			
			if (Rtuday_fzt.get(i).day_fztime == 0) {
				bgymd = getPreviousDay(bgymd);// 往前推一天
				endymd = getPreviousDay(endymd);// 往前推一天
			}
			
			sql = "select mp_id, bd_zy from yddataben.dbo.NDayBdDl"
					+ bgymd.substring(0, 6) + " where date = " + bgymd
					+ " and rtu_id = " + Rtuday_fzt.get(i).rtu_id;
			rs = j_dao.executeQuery(sql);
			try {
				while (rs.next()) {
					short mp_id = rs.getShort("mp_id");
					int id = FindMeterpara(Rtuday_fzt.get(i).rtu_id, mp_id);
					if (id < Meterpara.size()) {
						Meterpara.get(id).bg_bd = rs.getDouble("bd_zy");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			sql = "select mp_id, bd_zy from yddataben.dbo.NDayBdDl"
				+ endymd.substring(0, 6) + " where date = " + endymd
				+ " and rtu_id = " + Rtuday_fzt.get(i).rtu_id;
			rs = j_dao.executeQuery(sql);
			try {
				while (rs.next()) {
					short mp_id = rs.getShort("mp_id");
					int id = FindMeterpara(Rtuday_fzt.get(i).rtu_id, mp_id);
					if (id < Meterpara.size()) {
						Meterpara.get(id).end_bd = rs.getDouble("bd_zy");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	// 日期往前推一天
	private String getPreviousDay(String day) {
		String titleData = day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		try {
			date = sdf.parse(titleData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1); // 把日期向前推一天
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		String retDate = sdf.format(date); // 向前推一天以后的日期
		return retDate;
	}

	private int FindMeterpara(int rtu_id, short mp_id) {
		int i = 0;
		for (i = 0; i < Meterpara.size(); i++) {
			if ((Meterpara.get(i).rtu_id == rtu_id)
					&& (Meterpara.get(i).mp_id == mp_id))
				break;
		}
		return i;
	}

	private boolean GetYdRcd(String orgid, String consid, String bgymd,String endymd) {
		
		if ((Rtuday_fzt.size() <= 0) || (Meterpara.size() <= 0)) return false;

		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		int isyear = Integer.parseInt(bgymd.substring(0, 4));
		int ieyear = Integer.parseInt(endymd.substring(0, 4));
		int year = ieyear - isyear + 1;
		for (int i = 0; i < year; i++) {
			try {
				if (Rtuday_fzt.get(i).day_fztime != 0) {
					sql = "select rtu_id, mp_id, sum(use_money) as totmoney, sum(use_dl) as totdl from yddataben.dbo.npyd_record" + isyear + " where begin_date >" + bgymd +" and end_date <" + endymd + " or (begin_date = " + bgymd + " and end_date = " + bgymd  + " and begin_time >=" + Rtuday_fzt.get(i).day_fztime + ") or (begin_date = " + endymd + " and end_date = " + endymd  + " and end_time <= " + Rtuday_fzt.get(i).day_fztime + ") group by rtu_id, mp_id";
				}else{
					sql = "select rtu_id, mp_id, sum(use_money) as totmoney, sum(use_dl) as totdl from yddataben.dbo.npyd_record" + isyear + " where begin_date >=" + bgymd +" and end_date <" + endymd + " group by rtu_id, mp_id";
				}
				rs = j_dao.executeQuery(sql);
				while (rs.next()) {
					int rtu_id = rs.getInt("rtu_id"); // 实现
					short mp_id = rs.getShort("mp_id");
					int id = FindMeterpara(rtu_id, mp_id);
					if (id < Meterpara.size()) {
						Meterpara.get(id).totmoney += rs.getDouble("totmoney"); // 名称
						Meterpara.get(id).totbuy_dl += rs.getDouble("totdl");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				j_dao.closeRs(rs);
			}
			isyear++;
		}
		return true;
	}

	private boolean mulDfce() {
		if (Meterpara.size() <= 0) return false;
		for (int j = 0; j < Rtuday_fzt.size(); j++) {
			for (int i = 0; i < Meterpara.size(); i++) {
				if (Meterpara.get(i).rtu_id != Rtuday_fzt.get(j).rtu_id) continue;
				
				Meterpara.get(i).bl = Meterpara.get(i).pt*Meterpara.get(i).ct;									//计算倍率
				Meterpara.get(i).dl = Meterpara.get(i).bl*(Meterpara.get(i).end_bd-Meterpara.get(i).bg_bd);		//电量=（止数-起数）×倍率
				Meterpara.get(i).ysdf = Meterpara.get(i).dl*Meterpara.get(i).feiratez;							//抄见应收电费 = 电量×电价
				Meterpara.get(i).ce_dl = Meterpara.get(i).dl-Meterpara.get(i).totbuy_dl;						//差额电量 = 电量-表内合计电量
				Meterpara.get(i).ce_df = Meterpara.get(i).ysdf -Meterpara.get(i).totmoney;						//差额电费 = 抄见应收电费-表内合计售电电费
				if(Meterpara.get(i).dl > 0.01){
					Meterpara.get(i).ratio_dl = Meterpara.get(i).ce_dl/Meterpara.get(i).dl *100;			//电量差额百分比	
				}
				if(Meterpara.get(i).ysdf > 0.01){
					Meterpara.get(i).ratio_df = Meterpara.get(i).ce_df/Meterpara.get(i).ysdf*100;			//电费差额百分比	
				}

				Rtuday_fzt.get(j).dl 		+= Meterpara.get(i).dl;
				Rtuday_fzt.get(j).ysdf 		+= Meterpara.get(i).ysdf;
				Rtuday_fzt.get(j).totbuy_dl += Meterpara.get(i).totbuy_dl;
				Rtuday_fzt.get(j).totmoney 	+= Meterpara.get(i).totmoney;
						
			}
			
			Rtuday_fzt.get(j).ce_dl 	+= Rtuday_fzt.get(j).dl - Rtuday_fzt.get(j).totbuy_dl;
			Rtuday_fzt.get(j).ce_df 	+= Rtuday_fzt.get(j).ysdf - Rtuday_fzt.get(j).totmoney;	
			
			if(Rtuday_fzt.get(j).dl > 0.01){
				Rtuday_fzt.get(j).ratio_dl = Rtuday_fzt.get(j).ce_dl/Rtuday_fzt.get(j).dl*100;				//电量差额百分比	
			}
			if(Rtuday_fzt.get(j).ysdf > 0.01){
				Rtuday_fzt.get(j).ratio_df = Rtuday_fzt.get(j).ce_df/Rtuday_fzt.get(j).ysdf*100;			//电费差额百分比	
			}
		}
		
		return true;
	}
	// qjl add 20150204 end
	/**
	 * 用电汇总:河南清丰
	 * */
	public String loadYDHZData() {
		// 获取数据，存储在ydhz_list中
		areaYDHZReport();
		int nums = ydhz_list.size();

		// 无数据
		if (nums == 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray j_array1 = new JSONArray();
		JSONArray j_array2 = new JSONArray();
		int index = 1;

		YDHZObj temp = null;
		for (int i = 0; i < nums; i++) { // 先显示有用电记录的信息20141007
			temp = ydhz_list.get(i);

			if (temp.last_yddate <= 0)
				continue;

			json1.put("id", index);

			j_array1.add(index++); // 序号
			j_array1.add(CommFunc.CheckString(temp.org_desc)); // 营业点
			j_array1.add(CommFunc.CheckString(temp.area_desc)); // 片区名称
			j_array1.add(CommFunc.CheckString(temp.cons_no)); // 客户编号
			j_array1.add(CommFunc.CheckString(temp.cons_desc)); // 客户名称
			j_array1.add(CommFunc.CheckString(temp.card_no)); // 卡号
			if (temp.last_gddate == 0) {
				j_array1.add(CommFunc.CheckString("")); // 累计购电总金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_gddate))); // 最后购电时间
				j_array1.add(CommFunc.CheckString("")); // 累
			} else {
				j_array1.add(CommFunc.CheckString(temp.gdje_total)); // 累计购电总金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_gddate))); // 最后购电时间
				j_array1.add(CommFunc.CheckString(temp.gdje)); // 累计购电金额
			}
			if (temp.last_yddate == 0) {
				j_array1.add(CommFunc.CheckString("")); // 剩余金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_yddate))); // 最后用电时间
			} else {
				j_array1.add(CommFunc.CheckString(temp.syje)); // 剩余金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_yddate))); // 最后用电时间
			}
			j_array1.add(""); // 最后一行空格

			total_money[0] += temp.gdje_total;
			total_money[1] += temp.gdje;
			total_money[2] += temp.syje;

			json1.put("data", j_array1);
			j_array2.add(json1);
			j_array1.clear();
			json1.clear();
		}

		for (int i = 0; i < nums; i++) {
			temp = ydhz_list.get(i);
			if (temp.last_yddate > 0)
				continue; // 后显示无用电记录的信息20141007

			json1.put("id", index);

			j_array1.add(index++); // 序号
			j_array1.add(CommFunc.CheckString(temp.org_desc)); // 营业点
			j_array1.add(CommFunc.CheckString(temp.area_desc)); // 片区名称
			j_array1.add(CommFunc.CheckString(temp.cons_no)); // 客户编号
			j_array1.add(CommFunc.CheckString(temp.cons_desc)); // 客户名称
			j_array1.add(CommFunc.CheckString(temp.card_no)); // 卡号
			if (temp.last_gddate == 0) {
				j_array1.add(CommFunc.CheckString("")); // 累计购电总金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_gddate))); // 最后购电时间
				j_array1.add(CommFunc.CheckString("")); // 累
			} else {
				j_array1.add(CommFunc.CheckString(temp.gdje_total)); // 累计购电总金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_gddate))); // 最后购电时间
				j_array1.add(CommFunc.CheckString(temp.gdje)); // 累计购电金额
			}

			if (temp.last_yddate == 0) {
				j_array1.add(CommFunc.CheckString("")); // 剩余金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_yddate))); // 最后用电时间
			} else {
				j_array1.add(CommFunc.CheckString(temp.syje)); // 剩余金额
				j_array1.add(CommFunc.CheckString(CommFunc
						.FormatToYMD(temp.last_yddate))); // 最后用电时间
			}

			j_array1.add(""); // 最后一行空格

			total_money[0] += temp.gdje_total;
			total_money[1] += temp.gdje;
			total_money[2] += temp.syje;

			json1.put("data", j_array1);
			j_array2.add(json1);
			j_array1.clear();
			json1.clear();
		}

		json2.put("rows", j_array2);
		result = json2.toString();

		hz = "{gdje_total:\"" + CommFunc.FormatBDDLp000(total_money[0])
				+ "\",gdje:\"" + CommFunc.FormatBDDLp000(total_money[1])
				+ "\",syje:\"" + CommFunc.FormatBDDLp000(total_money[2])
				+ "\"}";
		return SDDef.SUCCESS;
	}

	// public String loadYDHZData(){
	// //获取数据，存储在ydhz_list中
	// areaYDHZReport();
	// int nums = ydhz_list.size();
	//		
	// //无数据
	// if(nums == 0){
	// result = SDDef.EMPTY;
	// return SDDef.SUCCESS;
	// }
	//		
	// JSONObject json1 = new JSONObject();
	// JSONObject json2 = new JSONObject();
	// JSONArray j_array1 = new JSONArray();
	// JSONArray j_array2 = new JSONArray();
	// int index = 1;
	//		
	// YDHZObj temp = null;
	// for(int i=0; i<nums; i++){
	// temp = ydhz_list.get(i);
	// json1.put("id", index);
	//			
	// j_array1.add(index++); //序号
	// j_array1.add(CommFunc.CheckString(temp.org_desc)); //营业点
	// j_array1.add(CommFunc.CheckString(temp.area_desc)); //片区名称
	// j_array1.add(CommFunc.CheckString(temp.cons_no)); //客户编号
	// j_array1.add(CommFunc.CheckString(temp.cons_desc)); //客户名称
	// j_array1.add(CommFunc.CheckString(temp.card_no)); //卡号
	// j_array1.add(CommFunc.CheckString(temp.gdje_total)); //累计购电总金额
	// j_array1.add(CommFunc.CheckString(CommFunc.FormatToYMD(temp.last_gddate)));
	// //最后购电时间
	// j_array1.add(CommFunc.CheckString(temp.gdje)); //累计购电金额
	// j_array1.add(CommFunc.CheckString(temp.syje)); //剩余金额
	// j_array1.add(CommFunc.CheckString(CommFunc.FormatToYMD(temp.last_yddate)));
	// //最后用电时间
	// j_array1.add(""); //最后一行空格
	//			
	// total_money[0] += temp.gdje_total;
	// total_money[1] += temp.gdje;
	// total_money[2] += temp.syje;
	//			
	// json1.put("data", j_array1);
	// j_array2.add(json1);
	// j_array1.clear();
	// json1.clear();
	// }
	// json2.put("rows", j_array2);
	// result = json2.toString();
	//		
	// hz = "{gdje_total:\"" +
	// CommFunc.FormatBDDLp000(total_money[0])+"\",gdje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",syje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\"}";
	// return SDDef.SUCCESS;
	// }

	public void areaYDHZReport() {
		StringBuffer ret = new StringBuffer();

		try {

			JSONObject j_obj = JSONObject.fromObject(result);

			String sdate = j_obj.getString("sdate");
			String edate = CommFunc.nowYMD(); // 终止日期指定为当前日期

			int isyear = Integer.parseInt(sdate.substring(0, 4));// 查询初始年年份
			int ieyear = Integer.parseInt(edate.substring(0, 4));// 查询终止年年份
			String isday = sdate.substring(4);// 查询初始年日期
			String ieday = edate.substring(4);// 查询终止年日期

			String sdate_input = "";
			String date_input = "";

			int year = ieyear - isyear + 1; // 年份差值

			String org = j_obj.getString("org");
			String area = j_obj.getString("area");

			// 根据供电所和片区查出用户信息，存入ydhz_list中
			ydhzBaseInfo(org, area);

			// 如果为同一年
			if (isyear == ieyear) {
				// 向ydhz_list中各元素存储其购电信息
				ydhzGDInfo(sdate, edate);
			}
			// 如果不是同一年
			else {

				for (int i = 0; i < year; i++) {

					// 查询终止年
					if (i == 0) {
						sdate_input = ieyear + "0101";
						date_input = edate;
					}
					// 查询初始初始年
					else if (i == year - 1) {
						sdate_input = sdate;
						date_input = isyear + "1231";
						;
					}
					// 中间年份
					else {
						sdate_input = ieyear - i + "0101";
						date_input = ieyear - i + "1231";
					}
					ydhzGDInfo(sdate_input, date_input);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 用电汇总sql语句从库中取值
	public void ydhzBaseInfo(String org, String area) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = " select o.describe as org_desc, a.describe as area_desc, f.farmer_no as cons_no, f.describe as cons_desc, f.card_no as card_no "
				+ "from ydparaben.dbo.orgpara o, ydparaben.dbo.areapara a, ydparaben.dbo.farmerpara as f "
				+ "where a.org_id = o.id and f.area_id = a.id ";
		if (!org.equals("-1")) {
			sql += " and o.id =" + org;
		}
		if (!area.equals("-1")) {
			sql += " and a.id =" + area;
		}

		sql += " order by f.area_id, a.org_id";
		rs = j_dao.executeQuery(sql);

		try {
			YDHZObj temp = null;
			String org_desc = "", area_desc = "", cons_no = "", cons_desc = "", card_no = "";
			while (rs.next()) {
				org_desc = CommFunc.CheckString(rs.getString("org_desc"));
				area_desc = CommFunc.CheckString(rs.getString("area_desc"));
				cons_no = CommFunc.CheckString(rs.getString("cons_no"));
				cons_desc = CommFunc.CheckString(rs.getString("cons_desc"));
				card_no = CommFunc.CheckString(rs.getString("card_no"));
				temp = new YDHZObj(org_desc, area_desc, cons_no, cons_desc,
						card_no);
				ydhz_list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 用电汇总从库中查询购电信息
	public void ydhzGDInfo(String sdate, String edate) {
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		// 获取累计购电总金额--该时间段内最后一次累计金额
		String sql = "select a.card_no, a.op_date, a.total_gdz, a.pay_money from  yddataben.dbo.NYff"
				+ sdate.substring(0, 4)
				+ " as a, "
				+ "(select card_no,  max(op_date%10000*1000000 + op_time) as mdhms  from  yddataben.dbo.NYff"
				+ sdate.substring(0, 4)
				+ " where op_type != 5 and op_type != 50 and op_date >="
				+ sdate
				+ "and op_date <="
				+ edate
				+ "group by card_no ) as b "
				+ " where a.card_no = b.card_no and (op_date%10000*1000000 + op_time) = b.mdhms";

		rs = j_dao.executeQuery(sql);
		Map<String, GDValueObj> gdtotal_map = new HashMap<String, GDValueObj>();
		GDValueObj temp = null;
		try {
			while (rs.next()) {
				temp = new GDValueObj();
				temp.card_no = CommFunc.CheckString(rs.getString("card_no"));
				temp.date = rs.getInt("op_date");
				temp.value = rs.getDouble("total_gdz");
				temp.value1 = rs.getDouble("pay_money");
				gdtotal_map.put(temp.card_no, temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取累计购电金额--该时间段内累计金额
		// sql =
		// "select card_no, sum(pay_money) as totmoney  from  yddataben.dbo.NYff" +
		// sdate.substring(0,4) + " where op_date >= " + sdate +
		// " and op_date <= " + edate + " group by card_no";
		// rs = j_dao.executeQuery(sql);
		// Map<String, GDValueObj> gd_map = new HashMap<String, GDValueObj>();
		// try {
		// while(rs.next()){
		// temp = new GDValueObj();
		// temp.card_no = CommFunc.CheckString(rs.getString("card_no"));
		// temp.value += rs.getDouble("totmoney"); //累计金额，要累加
		// gd_map.put(temp.card_no, temp);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 获取该时间段内最近一次剩余金额
		sql = "select a.card_no, a.begin_date as op_date, a.remain_money,a.begin_time from  yddataben.dbo.npyd_record"
				+ sdate.substring(0, 4)
				+ " as a, "
				+ "(select card_no,  max(convert(decimal(19,2),begin_date)*1000000+begin_time) as mdhms  from  yddataben.dbo.npyd_record"
				+ sdate.substring(0, 4)
				+ " group by card_no ) as b "
				+ " where a.card_no = b.card_no and (convert(decimal(19,2),begin_date)*1000000+begin_time) = b.mdhms ";
		rs = j_dao.executeQuery(sql);
		Map<String, GDValueObj> sy_map = new HashMap<String, GDValueObj>();
		try {
			while (rs.next()) {
				temp = new GDValueObj();
				temp.card_no = CommFunc.CheckString(rs.getString("card_no"));
				temp.date = rs.getInt("op_date");
				temp.value = rs.getDouble("remain_money");
				sy_map.put(temp.card_no, temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		YDHZObj ydhz_temp = null;
		String card_no = "";

		for (int i = 0, num = ydhz_list.size(); i < num; i++) {
			ydhz_temp = ydhz_list.get(i);
			card_no = ydhz_temp.card_no;
			// 根据card_no,查询出该时间段内对应的最近一次累计购电总金额，跨年时如果不为0，则表示已经赋值。
			if (gdtotal_map.containsKey(card_no) && ydhz_temp.last_gddate <= 0) {
				ydhz_temp.gdje_total = gdtotal_map.get(card_no).value;
				ydhz_temp.last_gddate = gdtotal_map.get(card_no).date;
				ydhz_temp.gdje = gdtotal_map.get(card_no).value1;
			}
			// 根据card_no,查询出该时间段内购电金额累计值，跨年叠加
			// if(gd_map.containsKey(card_no)){
			// ydhz_temp.gdje += gd_map.get(card_no).value;
			// }
			// 根据card_no,查询出该时间段内对应的最近一次剩余金额，跨年时如果不为0，则表示已经赋值。
			if (sy_map.containsKey(card_no) && ydhz_temp.last_yddate <= 0) {
				ydhz_temp.syje = sy_map.get(card_no).value;
				ydhz_temp.last_yddate = sy_map.get(card_no).date;
			}
		}

	}

	public String execute() {
		return SDDef.SUCCESS;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public String getHz() {
		return hz;
	}
}
