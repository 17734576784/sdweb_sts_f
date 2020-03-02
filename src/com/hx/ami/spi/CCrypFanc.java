package com.hx.ami.spi;

public class CCrypFanc {
	//加密
    public static byte[] encrypt(byte[] content, int offset, int[] key, int times){//times为加密轮数
        int[] tempInt = byteToInt(content, offset);
        int y = tempInt[0], z = tempInt[1], sum = 0, i;
        int delta=0x9e3779b9; //这是算法标准给的值
        int a = key[0], b = key[1], c = key[2], d = key[3]; 

        for (i = 0; i < times; i++) {   
            
            sum += delta;
            y += ((z<<4) + a) ^ (z + sum) ^ ((z>>5) + b);
            z += ((y<<4) + c) ^ (y + sum) ^ ((y>>5) + d);
        }
        tempInt[0]=y;
        tempInt[1]=z; 
        return intToByte(tempInt, 0);
    }
    //解密
    public static byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times){
        int[] tempInt = byteToInt(encryptContent, offset);
        int y = tempInt[0], z = tempInt[1], sum = 0, i;
        int delta=0x9e3779b9; //这是算法标准给的值
        int a = key[0], b = key[1], c = key[2], d = key[3];
        if (times == 32)
            sum = 0xC6EF3720; /**//* delta << 5*/
        else if (times == 16)
            sum = 0xE3779B90; /**//* delta << 4*/
        else
            sum = delta * times;

        for(i = 0; i < times; i++) { 
            z -= ((y<<4) + c) ^ (y + sum) ^ ((y>>5) + d);
            y -= ((z<<4) + a) ^ (z + sum) ^ ((z>>5) + b);
            sum -= delta; 
        }
        tempInt[0] = y;
        tempInt[1] = z;

        return intToByte(tempInt, 0);
    }
    
    //byte[]型数据转成int[]型数据
    private static int[] byteToInt(byte[] content, int offset){

        int[] result = new int[content.length >> 2];//除以2的n次方 == 右移n位 即 content.length / 4 == content.length >> 2
        for(int i = 0, j = offset; j < content.length; i++, j += 4){
            result[i] = transform(content[j]) | transform(content[j + 1]) << 8 |
            transform(content[j + 2]) << 16 | (int)content[j + 3] << 24;
        }
        return result;
    }
    
    //int[]型数据转成byte[]型数据
    private static byte[] intToByte(int[] content, int offset){
        byte[] result = new byte[content.length << 2];//乘以2的n次方 == 左移n位 即 content.length * 4 == content.length << 2
        for(int i = 0, j = offset; j < result.length; i++, j += 4){
            result[j + 0] = (byte)(content[i] & 0xff);
            result[j + 1] = (byte)((content[i] >> 8) & 0xff);
            result[j + 2] = (byte)((content[i] >> 16) & 0xff);
            result[j + 3] = (byte)((content[i] >> 24) & 0xff);
        }
        return result;
    }
    //若某字节为负数则需将其转成无符号正数
    private static int transform(byte temp){
        int tempInt = (int)temp;
        if(tempInt < 0){
            tempInt += 256;
        }
        return tempInt;
    }

    //	CCrypFanc.test("1234567890123456");
    public static boolean tea_cryproc(byte[] crypbuf, int begpos, int endpos, byte []keybyte, boolean crypflag) {

//    	int k[] = new int[4];
//
//    	for (int i = 0; i < 4; i++) {
//    		k[i] = (keybyte[4 * i + 0]) + (keybyte[4 * i + 1] << 8) + (keybyte[4 * i + 2] << 16) + (keybyte[4 * i + 3] << 24);;
//    	}
    	
    	int k[] = byteToInt(keybyte, 0);
    	
    	byte[] tempbyte = new byte[8];
    	if (((endpos - begpos) % 8)!= 0) return false;
    	
    	for(int offset = begpos; offset < endpos; offset += 8) {
        	System.arraycopy(crypbuf, offset, tempbyte, 0, 8);
        	if (crypflag) tempbyte = encrypt(tempbyte, 0, k, 16);
        	else tempbyte = decrypt(tempbyte, 0, k, 16);
       		System.arraycopy(tempbyte, 0, crypbuf, offset, 8);
       	}

    	return true;
    }

//			tokenstr = CChgFanc.Bytes2HexString(buf, 4, 1016);
//
//    		if (CCrypFanc.tea_cryproc(buf, 100, 420, keybyte, false) == false) return "";
//    		tokenstr = CChgFanc.Bytes2HexString(buf, 4, 1016);

    
    public static void test (String meterNo) {	
	//	byte[] keybyte  = CChgFanc.hexStringToByte(meterNo, 16);
	//	int key[] = new int[4];
		
	//	key[0] = CChgFanc.GetIntval(keybyte, 0, 4);
	//	key[1] = CChgFanc.GetIntval(keybyte, 4, 4);
			
//		for (int i = 0; i < 8; i++) {
//			keybyte[i] = (byte)((~keybyte[i]) & 0xff);
//		}
	//	key[2] = CChgFanc.GetIntval(keybyte, 0, 4);
	//	key[3] = CChgFanc.GetIntval(keybyte, 4, 4);
		
		byte[] keybyte  = new byte [16];
		
		byte[] meterbyte  = CChgFanc.hexStringToByte(meterNo, 16);

		System.arraycopy(meterbyte, 0, keybyte, 0, 8);
		for (int i = 0; i < 8; i++) {
			keybyte[8+i] = (byte)((~meterbyte[i]) & 0xff);
		}
		
		byte[]buf = new byte[16];
		for (int i = 0; i < 16; i++) {
			buf[i] = (byte)(i * 11 +2);
		}
		tea_cryproc(buf, 0, 16, keybyte, true);
		
		//System.arraycopy(crypbuf, 0, buf, 0, 16);
		
		tea_cryproc(buf, 0, 16, keybyte, false);
		//System.arraycopy(crypbuf, 0, buf, 0, 16);
    }
}
