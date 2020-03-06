/**********************************************************************
* <pre>
* FILE : DESUtil.java
* CLASS : DESUtil
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
* 		  |2012-4-25| SuMMeR| Created |
* DESCRIPTION:
* </pre>
***********************************************************************/
/**
* $Id: DESUtil.java,v 1.1 2012/05/28 07:57:19 weibin Exp $
*/

package com.sandrew.fileserver.util;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2012-4-25
 * @version    :
 */
public class DESUtil
{
	private static DESEncrypt des = null;

	/**
	 * 
	 * Function    : 加密字符串
	 * LastUpdate  : 2012-4-25
	 * @param desStr
	 * @return
	 */
	public static String fadeInDES(String desStr)
	{
		try
		{
			init();
			return des.encrypt(desStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * Function    : 解密字符串
	 * LastUpdate  : 2012-4-25
	 * @param undesStr
	 * @return
	 */
	public static String fadeOutDES(String undesStr)
	{
		try
		{
			init();
			return des.decrypt(undesStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static void init()
	{

		if (des == null)
		{
			try
			{
				des = new DESEncrypt("CellarKey"); //自定义密钥     
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		System.out.println(fadeInDES("123456"));
		
		System.out.println(fadeOutDES("3f7e7fed558b0278"));
	}
}
