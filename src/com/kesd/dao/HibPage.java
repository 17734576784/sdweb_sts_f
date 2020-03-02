package com.kesd.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.libweb.dao.HibSessionFactory;

public class HibPage {
	private int pagesize=10;
	private int totalrecords;
	private int currentpage=1;
	
	public HibPage(int currentpage,int pagesize){
    	this.currentpage = currentpage;
    	this.pagesize = pagesize;
    }
	
	@SuppressWarnings("unchecked")
	public List getRecord(String hql) {
		List list = null;
		Session session=HibSessionFactory.currentParaSession();
		Transaction ts = null;
		int totalpages = (totalrecords%pagesize)==0?(totalrecords/pagesize):(totalrecords/pagesize+1);
		if (currentpage > totalpages) {
			currentpage = totalpages;
		}
		try{
			ts = session.beginTransaction();
			Query q = session.createQuery(hql);
			q.setFirstResult((currentpage-1)*pagesize);
			q.setMaxResults(pagesize);
			ts.commit();
			list = q.list();
		}catch (Exception e) {
			e.printStackTrace();
			String cause = e.getCause().getMessage();
			System.out.println(cause);
			HibSessionFactory.reConnect(cause);
			if (ts != null)
				ts.rollback();
			e.printStackTrace();
		} finally {
			HibSessionFactory.closeSession();
		}
		return list;
	}
	
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalrecords() {
		return totalrecords;
	}
	
	public void setTotalRecords(int tt){
		totalrecords = tt;
	}

	public void setTotalrecords(String hql) {
		
		Session session = HibSessionFactory.currentParaSession();
		Transaction ts = null;
		long total = 0;
		try {
			ts = session.beginTransaction();
			total = (Long) session.createQuery(hql).iterate().next();
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			String cause = e.getCause().getMessage();
			HibSessionFactory.reConnect(cause);
			e.printStackTrace();
			if (ts != null)
				ts.rollback();
		} finally {
			HibSessionFactory.closeSession();
		}
		totalrecords = (int) total;
		
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	
}
