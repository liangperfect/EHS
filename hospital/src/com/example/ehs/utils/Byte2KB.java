package com.example.ehs.utils;

import java.math.BigDecimal;

public class Byte2KB {
	public   static  String bytes2kb( long  bytes)   {
        BigDecimal filesize  =   new  BigDecimal(bytes);
        BigDecimal megabyte  =   new  BigDecimal( 1024 * 1024 );
         float  returnValue  =  filesize.divide(megabyte,  2 , BigDecimal.ROUND_UP).floatValue();
         if  (returnValue  >   1 )
             return (returnValue  +   "  MB " );
        BigDecimal kilobyte  =   new  BigDecimal( 1024 );
        returnValue  =  filesize.divide(kilobyte,  2 , BigDecimal.ROUND_UP).floatValue();
         return (returnValue  +   "  KB " );
 } 
}
