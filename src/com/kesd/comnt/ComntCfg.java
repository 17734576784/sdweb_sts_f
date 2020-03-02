/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ComntCfg.java																		*
*	Description	:	通讯配置																				*
*	Author		:																						*
*	Date		:	2011/03/17																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.comnt;

import com.libweb.common.CommBase;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kesd.common.WebConfig;


public class ComntCfg {
	public String saleman_servic_ip   = "127.0.0.1";	//提供默认初始值
	public int	  saleman_servic_port = 17006;			//提供默认初始值
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : loadComntCfg
	* <p>
	* <p>DESCRIPTION : 读取通讯配置
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public boolean loadComntCfg(){
		
		try {
			Element root = WebConfig.getRoot();
			loadSaleManService(root);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean loadSaleManService(Element father_element) {
		NodeList nl_webservice_addr = father_element.getElementsByTagName("saleman_service_addr");
		if (nl_webservice_addr == null || nl_webservice_addr.getLength() <= 0) return false;
		
		Node node = nl_webservice_addr.item(0);
		if (node == null) return false;
		
		NamedNodeMap node_map = node.getAttributes();
		Node attr_ip   = node_map.getNamedItem("ip");
		Node attr_port = node_map.getNamedItem("port");
		
		if (attr_ip != null) {
			saleman_servic_ip = attr_ip.getNodeValue();
		}
		
		if (attr_port != null) {
			saleman_servic_port = CommBase.strtoi(attr_port.getNodeValue());
		}
		
		return true;
	}
}
