/** 
 * 把一个浮点数，以小数点后几位四舍五入
 * @param srcValue 要舍位的值
 * @param iCount  要舍位到小数点后几位
 * @return 四舍五入后的数
 */
function round(srcValuef, iCount) {
    var srcValue=srcValuef;
    var zs=true;
    // 判断是否是负数
    if (srcValue<0){
        srcValue=Math.abs(srcValue);
        zs=false;
    }
    var iB = Math.pow(10, iCount);
    var value1 = srcValue * iB;
    var anumber = new Array();
    var anumber1 = new Array();

    var fvalue = value1;    // 保存原值
    var value2 = value1.toString();
    var idot = value2.indexOf(".");
  
    // 如果是小数
    if (idot!=-1){
        anumber = srcValue.toString().split(".");
        // 如果是科学计数法结果
        if (anumber[1].indexOf("e") != -1 || anumber[1].indexOf("E") != -1){
            return Math.round(value1)/iB;
        }
        
        anumber1=value2.split(".");
        if (anumber[1].length <= iCount){
            return parseFloat(srcValuef,10);
        }
    
        var fvalue3=parseInt(anumber[1].substring(iCount,iCount+1),10);
        if (fvalue3 >= 5){
            fvalue=parseInt(anumber1[0],10)+1;
        } else {
            //对于传入的形如111.834999999998 的处理（传入的计算结果就是错误的，应为111.835）
            if (fvalue3==4 && anumber[1].length>10 && parseInt(anumber[1].substring(iCount+1,iCount+2),10)==9 ){
                fvalue=parseInt(anumber1[0],10)+1;
            } else {
                fvalue=parseInt(anumber1[0],10);
            }
        }
    }
  
    // 如果是负数就用0减四舍五入的绝对值
    
    if (zs){
    	return fvalue/iB;
    } else {
        return 0-fvalue/iB;
    }
}

