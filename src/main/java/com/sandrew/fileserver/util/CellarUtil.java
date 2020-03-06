/**********************************************************************
* <pre>
* FILE : StringUtil.java
* CLASS : StringUtil
*
* AUTHOR : SuMMeR
*
* FUNCTION : TODO
*
*
*======================================================================
* CHANGE HISTORY LOG
*----------------------------------------------------------------------
* MOD. NO.| DATE | NAME | REASON | CHANGE REQ.
*----------------------------------------------------------------------
* 		  |2012-4-20| SuMMeR| Created |
* DESCRIPTION:
* </pre>
***********************************************************************/
/**
* $Id: CellarUtil.java,v 1.2 2012/06/07 02:44:37 weibin Exp $
*/

package com.sandrew.fileserver.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2012-4-20
 * @version    :
 */
public class CellarUtil
{

	private static String hexString = "0123456789ABCDEF";

	/**
	 * 
	 * Function    : 字符串转成16进制字符串
	 * LastUpdate  : 2012-4-20
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str)
	{
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++)
		{
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	private static String toHexs(byte... bs)
	{
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sbd = new StringBuilder();
		for (byte b : bs)
		{
			sbd.append(chars[(b & 0xf0) >> 4]);
			sbd.append(chars[b & 0x0f]);
		}
		return sbd.toString();
	}

	/**
	 * 
	 * Function    : 字节数组转整数
	 * LastUpdate  : 2012-6-4
	 * @param b
	 * @return
	 */
	public static int bytes2Int(byte[] b)
	{
		int value = 0;
		for (int i = 0; i < 4; i++)
		{
			int shift = (4 - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}

	/**
	 * 
	 * Function    : 整数转字节数组
	 * LastUpdate  : 2012-6-4
	 * @param i
	 * @return
	 */
	public static byte[] int2Bytes(int i)
	{
		return new byte[] { (byte) ((i >> 24) & 0xFF), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) (i & 0xFF) };
	}

	/** 
	 * @方法功能 字节数组和长整型的转换 
	 * @param 字节数组 
	 * @return 长整型 
	 */
	public static byte[] longToBytes(long number)
	{
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = new Long(temp & 0xff).byteValue();
			// 将最低位保存在最低位  
			temp = temp >> 8;
			// 向右移8位  
		}
		return b;
	}

	/** 
	 * @方法功能 字节数组和长整型的转换 
	 * @param 字节数组 
	 * @return 长整型 
	 */
	public static long bytesToLong(byte[] b)
	{
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位  
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位  
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff; // s0不变  
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * 
	 * Function    : 将字符传加密成16进制
	 * LastUpdate  : 2012-6-5
	 * @param str
	 * @return
	 */
	public static String encode(String str)
	{
		//根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		//将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++)
		{
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 
	 * Function    : 将16进制解密成普通字符串
	 * LastUpdate  : 2012-6-5
	 * @param s
	 * @return
	 */
	public static String decode(String s)
	{
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++)
		{
			try
			{
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			s = new String(baKeyword, "utf-8");//UTF-16le:Not
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 
	 * Function    : InputStream转byte[]
	 * LastUpdate  : 2016年9月12日
	 * @param is
	 * @return
	 */
	public static byte[] inputStreamToByte(InputStream is)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] block = new byte[1024];
			int readIndex = 0;
			while ((readIndex = is.read(block, 0, 1024)) > 0)
			{
				baos.write(block, 0, readIndex);
			}
			return baos.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Function    : byte[]转File
	 * LastUpdate  : 2016年9月12日
	 * @param bytes
	 * @param filePathAndName
	 * @return
	 */
	public static File byteToFile(byte[] bytes, String filePathAndName)
	{
		File file = null;
		BufferedOutputStream bops = null;
		FileOutputStream fos = null;
		try
		{
			file = new File(filePathAndName);
			fos = new FileOutputStream(file);
			bops = new BufferedOutputStream(fos);
			bops.write(bytes);
			return file;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (null != bops)
				{
					bops.close();
				}
				if (null != fos)
				{
					fos.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}

	public static void main(String[] args)
	{
		//		for (int i = 1; i <= 99; i++)
		//		{
		//			String s = "" + i;
		//			if (s.length() < 2)
		//			{
		//				s = "0" + s;
		//			}
		//			System.out.println("node" + s + ":" + encode("node" + s));
		//		}
		System.out.println(encode("node01"));
		System.out.println(decode("6E6F64653031"));
		//		byte[] b = longToBytes(123456789987654012l);
		//		byte[] b2 = int2Bytes(1234567890);
		//		System.out.println(b);
		//		System.out.println(bytesToLong(b));
	}
}
