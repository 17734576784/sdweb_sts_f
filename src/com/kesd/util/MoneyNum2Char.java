package com.kesd.util;


public final class MoneyNum2Char { 
    
	  /** 大写数字 */
	  private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆","柒", "捌", "玖" };
	  /** 整数部分的单位 */
	  private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};
	  /** 小数部分的单位 */
	  private static final String[] DUNIT = { "角", "分", "厘"};
	  
	  private static int point = 2;	//小数位数 
	  
	  private static boolean fuflag = false;	//负数
	  
	  /**
	   * 数字金额转大写文字。
	   * 默认精确到分。 
	   *
	   */
	  public static String toChinese(String str,int decNum) {
	    str = str.replaceAll(",", "");// 去掉","
	    String integerStr;// 整数部分数字
	    String decimalStr;// 小数部分数字
	    
	    // 初始化：分离整数部分和小数部分
	    if (str.indexOf(".") > 0) {
	      integerStr = str.substring(0, str.indexOf("."));
	      decimalStr = str.substring(str.indexOf(".") + 1);
	    } else if (str.indexOf(".") == 0) {
	      integerStr = "";
	      decimalStr = str.substring(1);
	    } else {
	      integerStr = str;
	      decimalStr = "";
	    }
	    int len = decimalStr.length()-1;
	    for(int i = 0;i < len ; i ++){//去掉小数后尾0
	    	if(decimalStr.substring(decimalStr.length()-1).equals("0")){
	    		decimalStr = decimalStr.substring(0,decimalStr.length()-1);
	    	}else{
	    		break;
	    	}
	    }
	    if(decimalStr.length() > decNum) decimalStr = decimalStr.substring(0,decNum); //小数位数过多切。
	    
	    // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
	    if (!integerStr.equals("")) {
	    	if(integerStr.substring(0,1).equals("-")){
	    		fuflag = true;
	    		integerStr = integerStr.substring(1);
	    	}else{
	    		fuflag = false;
	    	}
	    	integerStr = Long.toString(Long.parseLong(integerStr));
	      if (integerStr.equals("0")) {
	        integerStr = "";
	      }
	    }
	    // overflow超出处理能力，直接返回
	    if (integerStr.length() > IUNIT.length) {
	      System.out.println(str + ":超出处理能力");
	      return str;
	    }

	    int[] integers = toArray(integerStr);// 整数部分数字
	    boolean isMust5 = isMust5(integerStr);// 设置万单位
	    int[] decimals = toArray(decimalStr);// 小数部分数字
	    
	    String fushu = fuflag ? "负" : ""; 
	    
	    if (Integer.parseInt(decimalStr)==0){
	    	return fushu + getChineseInteger(integers, isMust5) + "整";
	    }else{
	    	return fushu + getChineseInteger(integers, isMust5) + getChineseDecimal(decimals,decNum);
	    }
	    
	  }

	  /**
	   * 整数部分和小数部分转换为数组，从高位至低位
	   */
	  private static int[] toArray(String number) {
	    int[] array = new int[number.length()];
	    for (int i = 0; i < number.length(); i++) {
	      array[i] = Integer.parseInt(number.substring(i, i + 1));
	    }
	    return array;
	  }

	  /**
	   * 得到中文金额的整数部分。
	   */
	  private static String getChineseInteger(int[] integers, boolean isMust5) {
	    StringBuffer chineseInteger = new StringBuffer("");
	    int length = integers.length;
	    for (int i = 0; i < length; i++) {
	      // 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
	      // 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
	      String key = "";
	      if (integers[i] == 0) {
	        if ((length - i) == 13)// 万(亿)(必填)
	          key = IUNIT[4];
	        else if ((length - i) == 9)// 亿(必填)
	          key = IUNIT[8];
	        else if ((length - i) == 5 && isMust5)// 万(不必填)
	          key = IUNIT[4];
	        else if ((length - i) == 1)// 元(必填)
	          key = IUNIT[0];
	        // 0遇非0时补零，不包含最后一位
	        if ((length - i) > 1 && integers[i + 1] != 0)
	          key += NUMBERS[0];
	      }
	      chineseInteger.append(integers[i] == 0 ? key
	          : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
	    }
	    return chineseInteger.toString();
	  }

	  /**
	   * 得到中文金额的小数部分。
	   */
	  private static String getChineseDecimal(int[] decimals, int decNum) {
	    StringBuffer chineseDecimal = new StringBuffer("");
	    for (int i = 0; i < decimals.length; i++) {
	      // 舍去3位小数之后的
	      if (i == decNum)
	        break;
	      chineseDecimal.append(decimals[i] == 0 ? ("零" + DUNIT[i]) : (NUMBERS[decimals[i]] + DUNIT[i]));
	    }
	    
	    return chineseDecimal.toString();
	  }

	  /**
	   * 判断第5位数字的单位"万"是否应加。
	   */
	  private static boolean isMust5(String integerStr) {
	    int length = integerStr.length();
	    if (length > 4) {
	      String subInteger = "";
	      if (length > 8) {
	        // 取得从低位数，第5到第8位的字串
	        subInteger = integerStr.substring(length - 8, length - 4);
	      } else {
	        subInteger = integerStr.substring(0, length - 4);
	      }
	      return Integer.parseInt(subInteger) > 0;
	    } else {
	      return false;
	    }
	  }

	  
	  public static String convertChar(Double dvalue){
		  if(dvalue.equals(0.0))return "零元整";
		  String svalue = String.valueOf(dvalue);
		  
		  return toChinese(svalue, point);
	  }
	  
	  public static String convertChar(Double dvalue,int pointN){
		  if(dvalue.equals(0.0))return "零元整";
			 
		  if(pointN>3) pointN = 3;
		  
		  String svalue = String.format("%."+pointN+"f",dvalue);
		
		  return toChinese(svalue, pointN);
		  
	  }
	  
	  public static void main(String[] args) {
	    Double number = -100.003;
	    System.out.println(number + " " + MoneyNum2Char.convertChar(number,1));
	    number = 156.3;
	    System.out.println(number + " " + MoneyNum2Char.convertChar(number,3));

	  }

  
  
} 
