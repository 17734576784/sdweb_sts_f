package com.hx.ami.spi;

public  class CChgFanc {

	public static int SetIntval(byte []para, int plen, int val, int len)
	{
		if (len == 3) {
			para[plen++] = (byte)((val>>16)& 0xff);
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);
		}
		else if (len == 2) {
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);

		}
		else if (len == 1){	
			para[plen++] = (byte)(val	   & 0xff);
		}
		else {
			para[plen++] = (byte)((val>>24)& 0xff);
			para[plen++] = (byte)((val>>16)& 0xff);
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);
		}

		return plen;
	}
	
	public static int GetIntval(byte buf[], int ptr, int len)
	{
		int item_size = 4;
		int[] b = new int[item_size];
		for (int i = 0; i < item_size; i++) b[i] = buf[ptr++];			//此处转换为int  很有必要 高位为1时有处理
		
		int val = 0;
		
		if (len == 3) {
			val		 = (b[0]& 0xff)<<16;
			val		|= (b[1]& 0xff)<<8;
			val		|= (b[2]& 0xff);
		}
		else if (len == 2) {	
			val		 = (b[0]& 0xff)<<8;
			val		|= (b[1]& 0xff);
		}
		else if (len == 1){	
			val		 = (b[0]& 0xff);
		}
		else if (len == 4){
			val		 = (b[0]& 0xff)<<24;
			val		|= (b[1]& 0xff)<<16;
			val		|= (b[2]& 0xff)<<8;
			val		|= (b[3]& 0xff);
		}

		return  val;
	}
	
	public static long GetLongval(byte buf[], int ptr, int len)
	{
		int item_size = 4;
		long[] b = new long[item_size];
		for (int i = 0; i < item_size; i++) b[i] = buf[ptr++];			//此处转换为int  很有必要 高位为1时有处理
		
		long val = 0;
		
		if (len == 3) {
			val		 = (b[0]& 0xff)<<16;
			val		|= (b[1]& 0xff)<<8;
			val		|= (b[2]& 0xff);
		}
		else if (len == 2) {	
			val		 = (b[0]& 0xff)<<8;
			val		|= (b[1]& 0xff);
		}
		else if (len == 1){	
			val		 = (b[0]& 0xff);
		}
		else if (len == 4){
			val		 = (b[0]& 0xff)<<24;
			val		|= (b[1]& 0xff)<<16;
			val		|= (b[2]& 0xff)<<8;
			val		|= (b[3]& 0xff);
		}

		return  val;
	}
	
	//BCD码转换成正常的十六进制数
	public static int BCD_TO_HEX(byte val) {
		return ((val >> 4) & 0xf) * 10 + (val & 0xf);
	}

	//正常的十六进制数转换成BCD码
	public static int HEX_TO_BCD(int val){
		return ((val /10)<<4) + (val%10);
	}

	public static int SetBcdIntval(byte para[], int plen, int val, int len )
	{
		if (len == 3) {
			para[plen++] = (byte)(HEX_TO_BCD( val % 1000000 / 10000));
			para[plen++] = (byte)(HEX_TO_BCD( val % 10000 / 100));
			para[plen++] = (byte)(HEX_TO_BCD( val % 100));
		}
		else if (len == 2) {	
			para[plen++] = (byte)(HEX_TO_BCD( val % 10000 / 100));
			para[plen++] = (byte)(HEX_TO_BCD( val % 100));
		}
		else if (len == 1){	
			para[plen++] = (byte)(HEX_TO_BCD( val % 100));
		}
		else {
			para[plen++] = (byte)(HEX_TO_BCD( val / 1000000 % 100));
			para[plen++] = (byte)(HEX_TO_BCD( val % 1000000 / 10000));
			para[plen++] = (byte)(HEX_TO_BCD( val % 10000 / 100));
			para[plen++] = (byte)(HEX_TO_BCD( val % 100));
		}

		return plen;
	}

	public static int GetBcdIntval(byte buf[], int ptr, int len)
	{
		int val = 0;
		if (len == 3) {
			val		  = BCD_TO_HEX(buf[ptr]) * 10000;	ptr++;
			val		 += BCD_TO_HEX(buf[ptr]) * 100;		ptr++;
			val		 += BCD_TO_HEX(buf[ptr]);			ptr++;
		}
		else if (len == 2) {
			val		  = BCD_TO_HEX(buf[ptr]) * 100;		ptr++;
			val		 += BCD_TO_HEX(buf[ptr]);			ptr++;
		}
		else if (len == 1){	
			val		 = BCD_TO_HEX(buf[ptr]);			ptr++;
		}
		else {	
			val		  = BCD_TO_HEX(buf[ptr]) * 1000000;	ptr++;
			val		 += BCD_TO_HEX(buf[ptr]) * 10000;	ptr++;
			val		 += BCD_TO_HEX(buf[ptr]) * 100;		ptr++;
			val		 += BCD_TO_HEX(buf[ptr]);			ptr++;
		}

		return val;
	}
		
	/** 
	* 
	* @param b byte[] 
	* @return String 
	*/ 
	public static String Bytes2HexString(byte[] b, int len) {
		String ret = ""; 
		len = (b.length < len) ? b.length:len;
		
		for (int i = 0; i < len; i++) { 
			String hex = Integer.toHexString(b[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
			ret += hex.toUpperCase(); 
		} 

		return ret; 
	}

	public static String Bytes2HexString(byte[] b, int begin, int len) {
		String ret = "";
		
		if (b.length < begin) return ret;
		if (b.length < begin +len) len = b.length - begin;

		for (int i = 0; i < len; i++) { 
			String hex = Integer.toHexString(b[begin + i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
			ret += hex.toUpperCase(); 
		} 
		
		return ret; 
	}
	
	private static byte toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;                                        
	}                                                    

	public static byte[] hexStringToByte(String hex) {
	    int len = (hex.length() / 2);                                             
	    byte[] result = new byte[len];                                            
	    char[] achar = hex.toCharArray();                                         
	    for (int i = 0; i < len; i++) {                                           
	     int pos = i * 2;                                                         
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }                                                                         
	    return result;                                                            
	}
	
	public static byte[] hexStringToByte(String hex, int formlen) {
		if (hex.length() % 2 != 0)  hex = '0' + hex;		//
		
		if (hex.length() > formlen) 	hex = hex.substring(hex.length() - formlen, formlen);
		else if (hex.length() < formlen) {
			while (hex.length() < formlen) {
				hex = "00" + hex; 
			}
		}
			
	    int len = (hex.length() / 2);                                             
	    byte[] result = new byte[len];                                            
	    char[] achar = hex.toCharArray();                                         
	    for (int i = 0; i < len; i++) {                                           
	     int pos = i * 2;                                                         
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }                                                                         
	    return result;                                                            
	}
	

	public static byte MakeCRC(byte buf[], int begin, int end)
	{
		byte CRC = 0;

		for (int i = begin; i < end; i++)  {
			CRC += buf[i];
		}

		return CRC;
	}

    public static byte[] tea_cryproc(byte[] crypbuf, int begpos, int endpos, int []key, boolean crypflag) {
    	return crypbuf;
    }
    
	 /**                                                                      
	  * @功能: BCD码转为10进制串(阿拉伯数据)                                  
	  * @参数: BCD码                                                          
	  * @结果: 10进制串                                                       
	  */                              
//	public static String bcd2Str(byte[] bytes) {                                                 
//	    char temp[] = new char[bytes.length * 2], val;                                           
//	                                                                                             
//	    for (int i = 0; i < bytes.length; i++) {                                                 
//	        val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);                                      
//	        temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');                         
//	                                                                                             
//	        val = (char) (bytes[i] & 0x0f);                                                      
//	        temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');                     
//	    }                                                                                        
//	    return new String(temp);                                                                 
//	}
//	 /**                                                                      
//	  * @功能: 10进制串转为BCD码                                              
//	  * @参数: 10进制串                                                       
//	  * @结果: BCD码                                                          
//	  */                                                                      
//	 public static byte[] str2Bcd(String asc) {                               
//	     int len = asc.length();                                              
//	     int mod = len % 2;                                                   
//	     if (mod != 0) {                                                      
//	         asc = "0" + asc;                                                 
//	         len = asc.length();                                              
//	     }                                                                    
//	     byte abt[] = new byte[len];                                          
//	     if (len >= 2) {                                                      
//	         len = len / 2;                                                   
//	     }                                                                    
//	     byte bbt[] = new byte[len];                                          
//	     abt = asc.getBytes();                                                
//	     int j, k;                                                            
//	     for (int p = 0; p < asc.length() / 2; p++) {                         
//	         if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {                
//	             j = abt[2 * p] - '0';                                        
//	         } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {         
//	             j = abt[2 * p] - 'a' + 0x0a;                                 
//	         } else {                                                         
//	             j = abt[2 * p] - 'A' + 0x0a;                                 
//	         }                                                                
//	         if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {        
//	             k = abt[2 * p + 1] - '0';                                    
//	         } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) { 
//	             k = abt[2 * p + 1] - 'a' + 0x0a;                             
//	         } else {                                                         
//	             k = abt[2 * p + 1] - 'A' + 0x0a;                             
//	         }                                                                
//	         int a = (j << 4) + k;                                            
//	         byte b = (byte) a;                                               
//	         bbt[p] = b;                                                      
//	     }                                                                    
//	     return bbt;                                                          
//	 }                                                                        
//
//	 public static byte asc_to_bcd(byte asc) {        
//		   byte bcd;                              
//		                                          
//		   if ((asc >= '0') && (asc <= '9'))      
//		       bcd = (byte) (asc - '0');          
//		   else if ((asc >= 'A') && (asc <= 'F')) 
//		       bcd = (byte) (asc - 'A' + 10);     
//		   else if ((asc >= 'a') && (asc <= 'f')) 
//		       bcd = (byte) (asc - 'a' + 10);     
//		   else                                   
//		       bcd = (byte) (asc - 48);           
//		   return bcd;                            
//		                                          
//	 }
//	 private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {                              
//		    byte[] bcd = new byte[asc_len / 2];                                                      
//		    int j = 0;                                                                               
//		    for (int i = 0; i < (asc_len + 1) / 2; i++) {                                            
//		        bcd[i] = asc_to_bcd(ascii[j++]);                                                     
//		        bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
//		    }                                                                                        
//		    return bcd;                                                                              
//		}                                                                                            
//		                                                                                             
//	
//		
//	/** 
//	* 将两个ASCII字符合成一个字节； 
//	* 如："EF"--> 0xEF 
//	* @param src0 byte 
//	* @param src1 byte 
//	* @return byte 
//	*/ 
//	public static byte uniteBytes(byte src0, byte src1) { 
//		byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue(); 
//		_b0 = (byte)(_b0 << 4); 
//		byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue(); 
//		byte ret = (byte)(_b0 ^ _b1); 
//		return ret; 
//	} 
//
//	/** 
//	* 将指定字符串src，以每两个字符分割转换为16进制形式 
//	* 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9} 
//	* @param src String 
//	* @return byte[] 
//	*/ 
//	public static byte[] HexString2Bytes(String src){ 
//		byte[] ret = new byte[8]; 
//		byte[] tmp = src.getBytes(); 
//		for(int i=0; i<8; i++){ 
//		ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]); 
//		} 
//		return ret; 
//	} 
//	
//                                                                          
//
//	/** *//**
//	    * 把字节数组转换成16进制字符串
//	    * @param bArray
//	    * @return
//	    */
//
//	 public static String bytesToHexString(byte[] src){
//	        StringBuilder stringBuilder = new StringBuilder("");
//	        if (src == null || src.length <= 0) {
//	            return null;
//	        }
//	        for (int i = 0; i < src.length; i++) {
//	            int v = src[i] & 0xFF;
//	            String hv = Integer.toHexString(v);
//	            if (hv.length() < 2) {
//	                stringBuilder.append(0);
//	            }
//	            stringBuilder.append(hv);
//	        }
//	        return stringBuilder.toString();
//	    }
//	/** *//**
//	    * 把字节数组转换为对象
//	    * @param bytes
//	    * @return
//	    * @throws IOException
//	    * @throws ClassNotFoundException
//	    */
//	public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
//	    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//	    ObjectInputStream oi = new ObjectInputStream(in);
//	    Object o = oi.readObject();
//	    oi.close();
//	    return o;
//	}
//
//	/** *//**
//	    * 把可序列化对象转换成字节数组
//	    * @param s
//	    * @return
//	    * @throws IOException
//	    */
//	public static final byte[] objectToBytes(Serializable s) throws IOException {
//	    ByteArrayOutputStream out = new ByteArrayOutputStream();
//	    ObjectOutputStream ot = new ObjectOutputStream(out);
//	    ot.writeObject(s);
//	    ot.flush();
//	    ot.close();
//	    return out.toByteArray();
//	}
//
//	public static final String objectToHexString(Serializable s) throws IOException{
//	    return bytesToHexString(objectToBytes(s));
//	}
//
//	public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{
//	    return bytesToObject(hexStringToByte(hex));
//	}
//
//	/** *//**
//	    * @函数功能: BCD码转ASC码
//	    * @输入参数: BCD串
//	    * @输出结果: ASC码
//	    */
//	public static String BCD2ASC(byte[] bytes) {
//	    StringBuffer temp = new StringBuffer(bytes.length * 2);
//
//	    for (int i = 0; i < bytes.length; i++) {
//	     int h = ((bytes[i] & 0xf0) >>> 4);
//	     int l = (bytes[i] & 0x0f);   
//	 //    temp.append(BToA[h]).append( BToA[l]);
//	    }
//	    return temp.toString() ;
//	}
//	
//	
//	public static byte[] hexStringToBytes(String hexString) {
//        if (hexString == null || hexString.equals("")) {
//            return null;
//        }
//        hexString = hexString.toUpperCase();
//        int length = hexString.length() / 2;
//        char[] hexChars = hexString.toCharArray();
//        byte[] d = new byte[length];
//        for (int i = 0; i < length; i++) {
//            int pos = i * 2;
//            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
//            
//        }
//        return d;
//    }
//    private static byte charToByte(char c) {
//        return (byte) "0123456789ABCDEF".indexOf(c);
//    }
    /*	public static final String bytesToHexString1(byte[] bArray) {
    StringBuffer sb = new StringBuffer(bArray.length);
    String sTemp;
    for (int i = 0; i < bArray.length; i++) {
     sTemp = Integer.toHexString(0xFF & bArray[i]);
     if (sTemp.length() < 2)
      sb.append(0);
     sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
}
private byte[] encryptByTea(String info){
        byte[] temp = info.getBytes();
        int n = 8 - temp.length % 8;//若temp的位数不足8的倍数,需要填充的位数
        byte[] encryptStr = new byte[temp.length + n];
        encryptStr[0] = (byte)n;
        System.arraycopy(temp, 0, encryptStr, n, temp.length);
        byte[] result = new byte[encryptStr.length];
        for(int offset = 0; offset < result.length; offset += 8){
            byte[] tempEncrpt = tea.encrypt(encryptStr, offset, KEY, 32);
            System.arraycopy(tempEncrpt, 0, result, offset, 8);
        }
        return result;
    }
    //通过TEA算法解密信息
    private String decryptByTea(byte[] secretInfo){
        byte[] decryptStr = null;
        byte[] tempDecrypt = new byte[secretInfo.length];
        for(int offset = 0; offset < secretInfo.length; offset += 8){
            decryptStr = tea.decrypt(secretInfo, offset, KEY, 32);
            System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
        }
        
        int n = tempDecrypt[0];
        return new String(tempDecrypt, n, decryptStr.length - n);
        
    }

*/
//	 public static String bcd2Str(byte[] bytes) {                             
//    StringBuffer temp = new StringBuffer(bytes.length * 2);              
//    for (int i = 0; i < bytes.length; i++) {                             
//        temp.append((byte) ((bytes[i] & 0xf0) >>> 4));                   
//        temp.append((byte) (bytes[i] & 0x0f));                           
//    }                                                                    
//    return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
//            .toString().substring(1) : temp.toString();                  
//} 
}
