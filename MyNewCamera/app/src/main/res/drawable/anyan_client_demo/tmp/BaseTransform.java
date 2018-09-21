package drawable.anyan_client_demo.tmp;

import java.util.Arrays;

/**
 * <b>PackageName:</b> cn.com.sand.pb.data<br>
 * <b>FileName:</b> BaseTransform.java<br>
 * <b>ClassName:</b> BaseTransform<br>
 * @Description  TODO
 * @author       fanlin	fanlin_0224_sp@msn.com
 * @date         2013-5-13 上午10:54:48
 */
public class BaseTransform {
	@SuppressWarnings("unused")
	private static String TAG = "BaseTransform_";

	/**
	 * bit group info
	 */
	public static String[] bitsGroup = {"0000", "0001", "0010", "0011",
		"0100", "0101", "0110", "0111",
		"1000", "1001", "1010", "1011",
		"1100", "1101", "1110", "1111",
		"1010", "1011", "1100", "1101", 
		"1110", "1111"}; 
	public static String[] hexsGroup = {"0", "1", "2", "3",
		"4", "5", "6", "7",
		"8", "9", "A", "B",
		"C", "D", "E", "F",
		"a", "b", "c", "d",
		"e", "f",};

	public static String hexChar = "0123456789ABCDEFabcdef";
	public static String bcdChar = "0123456789=";	

	public static boolean isHexChar(byte src) {
		if(hexChar.indexOf(src) >= 0) {
			return true;
		}
		return false;
	}

	public static boolean isHexChar(char src) {
		if(hexChar.indexOf(src) >= 0) {
			return true;
		}
		return false;
	}

	public static byte Char2Byte(char src) {
		byte b = (byte) hexChar.indexOf(src);
		return b;
	}
	
	public static byte[] Chars2Bytes(char[] src) {
		byte[] bsReturn = new byte[src.length];
		for(int i = 0; i < src.length; i++)
			bsReturn[i] = (byte) src[i];
		return bsReturn;
	}

	/**
	 * <b>Function:</b> Bits2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x30,0x31,0x31,0x31,0x31,0x31,0x31}      -> {0xff, 0xbf}
	 * {0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x30,0x31,0x31,0x31,0x31,0x31}           -> {0xff, 0xb7}
	 *                                                                             default add 1 0x00 in the end
	 * {0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x30,0x31,0x31,0x31,0x31,0x31,0x31,0x31} -> {0xff, 0xbf, 0x10}
	 *                                                                             default add 7 0x00 in the end
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] Bits2Bytes(byte[] src) {
		if(src == null)
			return null;
		return BitsString2Bytes(Bits2BitsString(src));
	}

	/**
	 * <b>Function:</b> Bits2BitsString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x30, 0x31, 0x30, 0x31, 0x31, 0x30, 0x30, 0x31}             -> "01011001"
	 * {0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x30} -> "111111110010"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Bits2BitsString(byte[] src) {
		if(src == null)
			return null;
		int maxlen = src.length;
		String field32 = new String();
		char field4[] = new char[4];
		int i = 0, j = 0;
		for(; i < maxlen; i++){
			if(j<4){
				field4[j] = (char) src[i];
				j++;
			}
			if(j >= 4) {
				j = 0;
				field32 = field32 + String.valueOf(field4).replace("\0", "0");
			}
		}
		switch (j) {
		case 1:
			field32 = field32 + "000" + String.valueOf(field4).substring(0, 1);
			break;
		case 2:
			field32 = field32 + "00" + String.valueOf(field4).substring(0, 2);
			break;
		case 3:
			field32 = field32 + "0" + String.valueOf(field4).substring(0, 3);
			break;
		default:
			break;
		}
		return field32;
	}

	/**
	 * <b>Function:</b> BitsString2Bits<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "00110101010101011001111000"
	 *  -> {'0','0','1','1','0','1','0','1','0','1','0','1','0','1','0','1','1','0','0','1','1','1','1','0','0','0'}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] BitsString2Bits(String src) {
		int len = src.length();
		byte[] bReturn = new byte[len];
		String tmp;
		for(int i = 0; i < len; i++) {
			tmp = src.substring(i, i + 1);
			if(tmp.equals("1"))
				bReturn[i] = '1';
			else
				bReturn[i] = '0';
		}
		return bReturn;
	}

	/**
	 * <b>Function:</b> BitsString2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "0101100111110000"     -> {"5980"}  -> {0x59,0x80}
	 * "01011001111100000001" -> {"59801"} -> {0x59,0x80,0x10}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] BitsString2Bytes(String src) {
		if(src == null)
			return null;
		int len = src.length();
		String str = new String();
		String tmp;
		for(int i = 0; i < len; ) {
			tmp = src.substring(i, i + 4);
			for(int j = 0; j < 22; j++) {
				if(bitsGroup[j].equals(tmp)) {
					str += hexsGroup[j];
					break;
				}
			}
			i += 4;
		}
		return HexString2Bytes(str);
	}

	/**
	 * <b>Function:</b> UniteBytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * '3' + '0' -> 0x30 
	 * if '0'~'9' return 0x30
	 * else       return 0x00
	 * </pre>
	 * @param first
	 * @param second
	 * @return
	 */
	public static byte UniteBytes(byte first, byte second, boolean istrue) {
		if(!isHexChar(first) || !isHexChar(second)) {
			istrue = false;
			return 0x00;
		}
		byte _b0 = Byte.decode("0x" + new String(new byte[]{first})).byteValue();
		_b0 = (byte)(_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[]{second})).byteValue();
		byte dst = (byte)(_b0 ^ _b1);
		istrue = true;
		return dst;
	}

	/**
	 * <b>Function:</b> Bytes2String<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x30, 0x31, 0x32, 0x33, 0x34, 0x35} -> "012345"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Bytes2String(byte[] src) {
		if(src == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			sb.append((char) (src[i]));
		}
		return sb.toString();
	}

	/**
	 * <b>Function:</b> Bytes2HexString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x30, 0x31, 0x32, 0x33, 0x34, 0x35} -> "303132333435"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Bytes2HexString(String format, byte ... src) {
		if(src == null)
			return null;
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			//ret.append((src[i] >> 4) & 0x0F);
			//ret.append(src[i] & 0x0F + 20);
			ret.append(String.format("%02X" + format, src[i]));
		}
		return ret.toString();
	}

	/**
	 * <b>Function:</b> Bytes2HexString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x30, 0x31, 0x32, 0x33, 0x34, 0x35} -> "303132333435"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Bytes2HexString(byte ... src) {
		if(src == null)
			return null;
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			//ret.append((src[i] >> 4) & 0x0F);
			//ret.append(src[i] & 0x0F + 20);
			ret.append(String.format("%02X", src[i]));
		}
		return ret.toString();
	}

	/**
	 * <b>Function:</b> Bytes2Long<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {            0x33, 0x30} -> 0x00003330
	 * {      0x00, 0x33, 0x30} -> 0x00003330
	 * {0x00, 0x00, 0x33, 0x30} -> 0x00003330
	 * </pre>
	 * @param src
	 * @return
	 */
	public static long Bytes2Long(byte[] src) {
		if(src.length == 4)
			return (src[3] & 0xff) | (src[2] & 0xff) << 8 | (src[1] & 0xff) << 16 | (src[0] & 0xff) << 24;
		else if(src.length == 3)
			return (src[2] & 0xff) | (src[1] & 0xff) << 8 | (src[0] & 0xff) << 16 | (0 & 0xff) << 24;
		else if(src.length == 2)
			return (src[1] & 0xff) | (src[0] & 0xff) << 8 | (0 & 0xff) << 16 | (0 & 0xff) << 24;
		else if(src.length == 1)
			return (src[0] & 0xff) | (0 & 0xff) << 8 | (0 & 0xff) << 16 | (0 & 0xff) << 24;
		else
			return 0;
	}

	/**
	 * <b>Function:</b> Bytes2Short<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x33, 0x30} -> 0x3330
	 * </pre>
	 * @param src
	 * @return
	 */
	public static short Bytes2Short(byte[] src) {
		return (short) (src[1] & 0xff | (src[0] & 0xff) << 8);
	}

	/**
	 * <b>Function:</b> Bytes2BitsString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x30,0x30,0x31,0x31,0x30,0x31,0x30,0x31,
	 *  0x30,0x31,0x30,0x31,0x30,0x31,0x31,0x30,
	 *  0x30,0x31,0x31,0x31,0x31,0x30,0x30,0x30} -> "00110101010101011001111000"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Bytes2BitsString(byte[] src) {
		if(src == null)
			return null;
		String str = Bytes2HexString(src);
		String strReturn = new String();
		int len = str.length();
		String tmp;
		for(int i = 0; i < len; i++) {
			tmp = str.substring(i, i + 1);
			for(int j = 0; j < 22; j++) {
				if(hexsGroup[j].equals(tmp)) {
					strReturn += bitsGroup[j];
					break;
				}
			}
		}
		return strReturn;		
	}

	/**
	 * <b>Function:</b> Bytes2Bits<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x35,0x56,0x78} 
	 * -> {0x30,0x30,0x31,0x31,0x30,0x31,0x30,0x31,
	 *     0x30,0x31,0x30,0x31,0x30,0x31,0x31,0x30,
	 *     0x30,0x31,0x31,0x31,0x31,0x30,0x30,0x30}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] Bytes2Bits(byte[] src) {
		return BitsString2Bits(Bytes2BitsString(src));
	}

	/**
	 * <b>Function:</b> Bytes2Count<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x35,0x56,0x78,0x00,0x01,0x02,0x00,0x00} -> 3 
	 * {0x35,0x56,0x78,0x05,0x01,0x02,0x00,0x00} -> 6 
	 * </pre>
	 * @param src
	 * @return
	 */
	public static int Bytes2Count(byte[] src) {
		int count = 0;
		for (byte b : src) {
			if(b != 0)
				count++;
			else
				return count;
		}
		return count;
	}

	public static char[] Bytes2Chars(byte[] src) {
		char[] bsReturn = new char[src.length];
		for(int i = 0; i < src.length; i++)
			bsReturn[i] =  (char) (src[i] & 0x00FF);
		return bsReturn;
	}
	
	/**
	 * <b>Function:</b> Short2Int<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 0x0002 -> 2
	 * </pre>
	 * @param src
	 * @return
	 */
	public static int Short2Int(short src) {
		return Integer.valueOf(src & 0xFFFF);
	}

	/**
	 * <b>Function:</b> Short2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 0x0033 -> {0x00, 0x33}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] Short2Bytes(short src) {
		byte[] b = new byte[2];
		b[1] = (byte) (src & 0xff);
		b[0] = (byte) ((src >> 8) & 0xff);
		return b;
	}

	/**
	 * <b>Function:</b> Shorts2HexString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * {0x0033, 0xFF30} -> "0033FF30"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String Shorts2HexString(short[] src) {
		if(src == null)
			return null;
		String ret = "";
		for (int i = 0; i < src.length; i++) {
			String hex = Integer.toHexString(src[i] & 0xFFFF);
			switch (hex.length()) {
			case 1:
				hex = "000" + hex;
				break;
			case 2:
				hex = "00" + hex;
				break;
			case 3:
				hex = "0" + hex;
				break;
			default:
				break;
			}
			ret += hex;
		}
		return ret;
	}
	
	/**
	 * <b>Function:</b> Long2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "123456" = 0x1E240 -> {0x01, 0xE2, 0x40}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] Long2Bytes(long src) {
		String str = Long.toHexString(src);
		if(str.length() % 2 == 1)
			str = "0" + str;
		return HexString2Bytes(str);
	}

	/**
	 * <b>Function:</b> String2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "012345GH" -> {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x47, 0x48}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] String2Bytes(String src) {
		if(src == null)
			return null;
		int len = src.length();
		byte[] bytes = new byte[len];
		for(int i=0; i<len; i++) {
			bytes[i] = (byte)(src.charAt(i));
		} 
		return bytes;
	}

	/**
	 * <b>Function:</b> String2BCDHexString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "6013820800102007307=49125201000006200"  -> "6013820800102007307d49125201000006200"
	 * '=' = {0x3d}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String String2BCDHexString(String src) {
		if(src == null)
			return null;
		char[] cs = src.toCharArray();
		char[] dest = new char[cs.length];
		int index = 0;
		for(char c : cs) {
			byte tmp = (byte) (c - 0x30); 
			switch (tmp) {
			case 0x01:
				dest[index ++] = '1';
				break;
			case 0x02:
				dest[index ++] = '2';
				break;
			case 0x03:
				dest[index ++] = '3';
				break;
			case 0x04:
				dest[index ++] = '4';
				break;
			case 0x05:
				dest[index ++] = '5';
				break;
			case 0x06:
				dest[index ++] = '6';
				break;
			case 0x07:
				dest[index ++] = '7';
				break;
			case 0x08:
				dest[index ++] = '8';
				break;
			case 0x09:
				dest[index ++] = '9';
				break;
			case 0x0A:
				dest[index ++] = 'A';
				break;
			case 0x0B:
				dest[index ++] = 'B';
				break;
			case 0x0C:
				dest[index ++] = 'C';
				break;
			case 0x0D:
				dest[index ++] = 'D';
				break;
			case 0x0E:
				dest[index ++] = 'E';
				break;
			case 0x0F:
				dest[index ++] = 'F';
				break;
			case 0x00:
				dest[index ++] = '0';
				break;
			default:
				break;
			}
		}
		return new String(dest, 0, index);
	}

	/**
	 * <b>Function:</b> String2HexString<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "-+%$1234abijEFGH" -> "1234abEF"
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String String2HexString(String src) {
		if(src == null)
			return null;
		char[] cs = src.toCharArray();
		char[] dest = new char[cs.length];
		int index = 0;
		for(char c : cs) {
			if(isHexChar(c)) {
				dest[index ++] = c;
			}
		}
		return new String(dest, 0, index);
	}

	/**
	 * <b>Function:</b> String2HexBytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "012345GHa" -> {0x01, 0x23, 0x45, 0x0a}
	 * "012345GHab" -> {0x01, 0x23, 0x45, 0xab}
	 * "012345GHab1" -> {0x01, 0x23, 0x45, 0xab, 0x01}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] String2HexBytes(String src) {
		if(src == null)
			return null;
		return HexString2Bytes(String2HexString(src));		
	}

	/**
	 * <b>Function:</b> HexString2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "303132333435"  -> {0x30, 0x31, 0x32, 0x33, 0x34, 0x35}
	 * "3031323334353" -> {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x03}
	 * "303132333435g" -> null
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] HexString2Bytes(String src){
		if(src == null)
			return null;
		int len = (src.length() + 1)/ 2;
		byte[] ret = new byte[len];
		byte[] tmp = src.getBytes();
		boolean istrue = true;
		for(int i = 0; i < len; i++){
			if(i == (len -1)) {
				if(src.length() % 2 == 1) {
					ret[i] = UniteBytes((byte)0x30, tmp[i * 2], istrue);
					if(ret[i] == 0x00 && !istrue)
						return null;
					continue;
				}
			}
			ret[i] = UniteBytes(tmp[i * 2], tmp[i * 2 + 1], istrue);
			if(ret[i] == 0x00 && !istrue)
				return null;
		}			
		return ret;
	}
	
	/**
	 * <b>Function:</b> Hex0xString2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * "/x30/x31/x32/x33/x34/x35"  -> {0x30, 0x31, 0x32, 0x33, 0x34, 0x35}
	 * "/x30/x31/x32/x33/x34/x35/x3" -> {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x03}
	 * "/x30/x31/x32/x33/x34/x35/xg" -> null
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] Hex0xString2Bytes(String src) {
		return HexString2Bytes(src.replace("/x", ""));
	}
	
	/**
	 * <b>Function:</b> IntToBytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 800	-> {0x03, 0x20}
	 * </pre>
	 * @param num
	 * @return
	 */
	public static byte[] IntToBytes(int num) {
		byte[] b = new byte[4];
		int length = 0;
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
			if(b[i] != 0x00)
				length++;
		}
		byte[] bReturn = new byte[length];
		System.arraycopy(b, 4 - length, bReturn, 0, length);
		return bReturn;
	}	

	/**
	 * <b>Function:</b> IntToHexBytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 800	-> {0x08, 0x00}
	 * </pre>
	 * @param num
	 * @return
	 */
	public static byte[] IntToHexBytes(int num) {
		String str = String.valueOf(num);
		if(str.length() % 2 == 1)
			str = "0" + str;
		return BaseTransform.String2HexBytes(str);
	}

	/**
	 * <b>Function:</b> ByteToByte<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * 0x30	-> {0x30}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] ByteToByte(byte src) {
		byte[] bReturn = new byte[1];
		bReturn[0] = src;
		return bReturn;
	}
	
	/**
	 * <b>Function:</b> ByteS2Bytes<br>
	 * <b>Description:</b>  TODO<br>
	 * <b>Example:</b> <pre>
	 * no args -> null !Warring
	 * 0x00 -> {0x00}
	 * 0x00, 0x01, 0x02 -> {0x00, 0x01, 0x02}
	 * </pre>
	 * @param src
	 * @return
	 */
	public static byte[] ByteS2Bytes(byte ... src) {
		return src;
	}
	
	public static byte BitReverse(byte src) {
		byte[] bits = Bytes2Bits(ByteToByte(src));
		byte tmp;
		for(int i = 0; i < 4; i++) {
			tmp = bits[i];
			bits[i] = bits[7 - i];
			bits[7 - i] = tmp;
		}
		return Bits2Bytes(bits)[0];
	}
	
	public static byte[] BitsReverse(byte[] src) {
		byte[] bitsS = new byte[src.length];
		Arrays.fill(bitsS, (byte)0x00);
		System.arraycopy(src, 0, bitsS, 0, src.length);
		for(int i = 0; i < src.length; i++) {
			bitsS[i] = BitReverse(bitsS[i]);
		}
		return bitsS;
	}
}
