package drawable.anyan_client_demo.tmp;

import java.util.Arrays;

/**
 * <b>PackageName:</b> cn.com.sand.pb.data<br>
 * <b>FileName:</b> Transform.java<br>
 * <b>ClassName:</b> Transform<br>
 * @Description  TODO
 * @author       fanlin	fanlin_0224_sp@msn.com
 * @date         2013-5-13 上午10:39:14
 */
public class Transform extends BaseTransform{
	@SuppressWarnings("unused")
	private static String TAG = "Transform_";	
	
	/**
	 * <b>Function:</b> Hexs2Asciis<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x01,0x23} length=1 ->  {0x31}
	 * {0x01,0x23} length=2 ->  {0x30,0x31}
	 * {0x01,0x23} length=2 ->  {0x30,0x31}
	 * {0x01,0x23} length=3 ->  {0x31,0x32,0x33}
	 * {0x01,0x23} length=4 ->  {0x30,0x31,0x32,0x33}
	 * {0x01,0x23} length=5 ->  {0x30,0x31,0x32,0x33,0x00}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] Hexs2Asciis(byte[] src, int len) {
		if(src == null)
			return null;
		byte[] bReturn = new byte[len];
		byte[] b = String2Bytes(Bytes2HexString(src));
			if(len > b.length)
				System.arraycopy(b, 0, bReturn, 0, b.length);
			else {
				if(len % 2 == 0)
					System.arraycopy(b, 0, bReturn, 0, len);
				else {
					System.arraycopy(b, 1, bReturn, 0, 1);
					System.arraycopy(b, 2 , bReturn, 1, len - 1);
				}
			}
		return bReturn;
	}
	
	/**
	 * <b>Function:</b> Asciis2Hexs<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>'21ef1' = {0x32,0x31,0x65,0x66,0x31}
	 * inlen 4 outlen 3             -> {0x00,0x21,0xEF}
	 * inlen 4 outlen < inlen/2     -> {0x21,0xEF}
	 * inlen 5 outlen 4             -> {0x00,0x02,0x1E,0xF1}
	 * inlen 5 outlen < inlen/2     -> {0x02,0x1E,0xF1}
	 * inlen > src.length           -> null </pre>
	 * @param src
	 * @param inlen
	 * @param outlen
	 * @return
	 */
	public static byte[] Asciis2Hexs(byte[] src, int inlen, int outlen) {
		if(src == null)
			return null;
		if(inlen > src.length)
			return null;
		byte[] b;
		if(inlen % 2 == 0) {
			b = new byte[inlen];
			System.arraycopy(src, 0, b, 0, inlen);
		} else {
			b = new byte[inlen + 1];
			b[0] = 0x30;
			System.arraycopy(src, 0, b, 1, inlen);
		}
		byte[] bReturn = HexString2Bytes(String2HexString(Bytes2String(b)));
		int len = bReturn.length;
		if(outlen <= len) {
			return bReturn;
		} else {
			b = new byte[outlen];
			System.arraycopy(bReturn, 0, b, outlen - len, len);
			return b;
		}
	}
	
/*	public static byte Asciis2BCD(byte src) {  
		byte bcd;
		if ((src >= '0') && (src <= '9'))  
			bcd = (byte) (src - '0');  
		else if ((src >= 'A') && (src <= 'F'))  
			bcd = (byte) (src - 'A' + 10);  
		else if ((src >= 'a') && (src <= 'f'))  
			bcd = (byte) (src - 'a' + 10);  
		else  
			bcd = (byte) (src - 48);  
		return bcd;  
	} 
	
	public static byte[] Asciis2BCD(byte[] src, int len) {
		if(len > src.length)
			return null;
		byte[] bcd = new byte[(len + 1 ) / 2];  
		int j = 0;  
		for (int i = 0; i < (len + 1) / 2; i++) {  
			bcd[i] = Asciis2BCD(src[j++]);  
			bcd[i] = (byte) (((j >= len) ? 0x00 : Asciis2BCD(src[j++])) + (bcd[i] << 4));  
		}  
		return bcd;  
	} */

	/**
	 * <b>Function:</b> Asciis2BCD<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * '12345=' = {0x31,0x32,0x33,0x34,0x35,0x3d}
	 * inlen 4 outlen 3           -> {0x00,0x12,0x34}
	 * inlen 4 outlen < inlen/2   -> {0x12,0x34}
	 * inlen 5 outlen 4           -> {0x00,0x01,0x23,0x45}
	 * inlen 5 outlen < inlen/2   -> {0x01,0x23,0x45}
	 * inlen 6 outlen 4			  -> {0x00,0x12,0x34,0x5d}
	 * inlen 6 outlen < inlen/2	  -> {0x12,0x34,0x5d}
	 * inlen > src.length         -> null
	 * </pre>
	 * @param src
	 * @param inlen
	 * @param outlen
	 * @return
	 */
	public static byte[] Asciis2BCD(byte[] src, int inlen, int outlen) {
		if(src == null)
			return null;
		for(int i = 0; i < inlen; i++) {
			if(bcdChar.indexOf(src[i]) < 0)
				return null;
		}
		byte[] b;
		if(src.length >= inlen) {
			if(inlen % 2 == 0) {
				b = new byte[inlen];
				System.arraycopy(src, 0, b, 0, inlen);
			} else {
				b = new byte[inlen + 1];
				b[0] = 0x30;
				System.arraycopy(src, 0, b, 1, inlen);
			}
		} else {
			return null;
		}
		byte[] bReturn = new byte[outlen];
		b = HexString2Bytes(String2BCDHexString(Bytes2String(b)));
		if(outlen > b.length) {
			System.arraycopy(b, 0, bReturn, outlen - b.length, b.length);
			return bReturn;
		} else {
			return b;
		}
	}
	
	/**
	 * <b>Function:</b> Asciis2Long<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * '12345678' = {0x31,0x32,0x33,0x34,0x35} -> 12345678
	 * length=2 -> 12
	 * length=5 -> 12345
	 * length=9 -> 12345678
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static long Asciis2Long(byte[] src, int len) {
		String str = Bytes2String(src);
		if(str == null)
			return 0;
		try {
			if(str.length() > len) {
				return Long.valueOf(str.substring(0, len));
			} else {
				return Long.valueOf(str);
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * <b>Function:</b> short2BCD<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 14460 -> {0x01,0x44,0x60}
	 * if      length = 1, if [0]=0x00 return [1]
	 *                     else        return null
	 * else if length = 2, return {0x46,0x60}
	 * else if length = 3, return {0x01,0x46,0x60}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] short2BCD(short src, int len) {
		byte[] b;
		int lenb;
		String str = Short.toString(src);
		if(str.length() % 2 != 0)
			str = "0" + str;
		b = String2HexBytes(str);
		if(b == null)
			return null;
		lenb = b.length;
		byte[] bReturn = new byte[len];
		if(len < lenb) {
			lenb--;
			if(len == 1) {
				for(int i = 0; i < lenb; i++) {
					if(b[i] != 0x00)
						return null;
				}
			}
			lenb++;
			System.arraycopy(b, lenb - len, bReturn, 0, len);	
		} else {
			System.arraycopy(b, 0, bReturn, len - lenb, lenb);
		}
		return bReturn;
	}
	
	/**
	 * <b>Function:</b> BCD2Ascii<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x01,0x23} length=5 -> null
	 * {0x01,0x23} length=4 -> {0x30,0x31,0x32,0x33}
	 * {0x01,0x23} length=3 -> {0x31,0x32,0x33}
	 * {0x01,0x23} length=2 -> {0x30,0x31}
	 * {0x01,0x23} length=1 -> {0x31}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] BCD2Ascii(byte[] src, int len) {
		int tlen = (len + 1) / 2;
		if(tlen > src.length)
			return null;
		byte[] b = new byte[tlen];
		System.arraycopy(src, 0, b, 0, tlen);
		b = String2Bytes(Bytes2HexString(b));
		byte[] bReturn = new byte[len];
		if(len > b.length)
			System.arraycopy(b, 0, bReturn, 0, b.length);
		else {
			if(len % 2 == 0)
				System.arraycopy(b, 0, bReturn, 0, len);
			else {
				System.arraycopy(b, 1, bReturn, 0, 1);
				System.arraycopy(b, 2, bReturn, 1, len - 1);
			}
		}
		return bReturn;
	}
	
	/**
	 * @Title: BCD2Short
	 * @Description: TODO	!NumberFormatException
	 * @Example		{0x12,0x34,0x56} -> !123456
	 				length=1	-> 2
	 				length=2 	-> 12
	 				length=5 	-> 12345
	 				length=7 	-> 0
	 * @param @param src
	 * @param @return	if 0	Error || 0
	 * @return short
	 * @throws
	 * @author: fanlin	fanlin_0224_sp@msn.com
	 */
	/**
	 * <b>Function:</b> BCD2Short<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x12,0x34,0x56} -> !123456
	 * length=1	-> 2
	 * length=2 -> 12
	 * length=5 -> 12345
	 * length=7 -> 0
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static short BCD2Short(byte[] src, int len) {
		String str = Bytes2HexString(src);
		if(str == null)
			return 0;
		try {
			if(str.length() < len)
				return Short.valueOf(str);
			else {
				if(len % 2 == 0) {
					return Short.valueOf(str.substring(0, len));
				} else {
					return Short.valueOf(str.substring(1, len + 1));
				}
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * <b>Function:</b> BCD2Long<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x12,0x34,0x56,0x78,0x90} -> 1234567890
	 * length=3	 234	
	 * length=4	 1234
	 * length=10 1234567890
	 * length=11 1234567890
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static long BCD2Long(byte[] src, int len) {
		String str = Bytes2HexString(src);
		if(str == null)
			return 0;
		try {
			if(str.length() < len)
				return Long.valueOf(str);
			else {
				if(len % 2 == 0) {
					return Long.valueOf(str.substring(0, len));
				} else {
					return Long.valueOf(str.substring(1, len + 1));
				}
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * <b>Function:</b> Long2BCD<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 1234567890 = {0x12,0x34,0x56,0x78,0x90}
	 * if len=2	{0x78,0x90}
	 * if len=7	{0x00,0x00,0x12,0x34,0x56,0x78,0x90}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] Long2BCD(long src, int len) {	
		byte[] b;
		int lenb;
		String str = Long.toString(src);
		if(str.length() % 2 != 0)
			str = "0" + str;
		b = String2HexBytes(str);
		if(b == null)
			return null;
		lenb = b.length;
		byte[] bReturn = new byte[len];
		if(len < lenb) {
			System.arraycopy(b, lenb - len, bReturn, 0, len);			
		} else {
			System.arraycopy(b, 0, bReturn, len - lenb, lenb);
		}
		return bReturn;
	}
	
	/**
	 * <b>Function:</b> Long2ASCII<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 1234567890 = {0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x30}
	 * if len=2	 {0x39,0x30}
	 * //if len=12 {0x00,0x00,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x30}
	 * if len=12 {0x30,0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x30}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] Long2ASCII(long src, int len) {
		String str = Long.toString(src);
		byte[] b = String2Bytes(str);
		byte[] bReturn = new byte[len];
		Arrays.fill(bReturn, (byte) 0x30);
		int blen = b.length;
		if(blen < len) {
			System.arraycopy(b, 0, bReturn, len - blen, blen);
		} else {
			System.arraycopy(b, blen - len, bReturn, 0, len);
		}
		return bReturn;
	}

	/**
	 * <b>Function:</b> Long2TAB<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 1234567890 = {0x49,0x96,0x02,0xD2}
	 * if len=2	{0x02,0xD2}
	 * if len=7	{0x00,0x00,0x00,0x49,0x96,0x02,0xD2}
	 * </pre>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] Long2TAB(long src, int len) {		
		String str = Long.toHexString(src);
		byte[] b = HexString2Bytes(str);
		byte[] bReturn = new byte[len];
		int blen = b.length;
		if(blen < len) {
			System.arraycopy(b, 0, bReturn, len - blen, blen);
		} else {
			System.arraycopy(b, blen - len, bReturn, 0, len);
		}
		return bReturn;
	}
	
	/**
	 * <b>Function:</b> TAB2Long<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x49,0x96,0x02,0xD2} = 1234567890
	 * if inLen=2  0x4996 = 18838
	 * if inLen=4  0x499602D2 = 1234567890
	 * </pre>
	 * @param src
	 * @param inLen
	 * @return
	 */
	public static long TAB2Long(byte[] src, int inLen) {
		byte[] tmp = new byte[inLen];
		System.arraycopy(src,0,tmp,0,inLen);
		String tmpStr = Bytes2HexString(tmp);
		long lReturn=Long.parseLong(tmpStr, 16);
		return lReturn;
	}

}
