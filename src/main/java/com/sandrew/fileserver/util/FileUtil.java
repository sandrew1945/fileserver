/**********************************************************************
* <pre>
* FILE : FileUtil.java
* CLASS : FileUtil
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
* 		  |2016年9月9日| SuMMeR| Created |
* DESCRIPTION:
* </pre>
***********************************************************************/
/**
* $Id: FileUtil.java,v 0.1 2016年9月9日 下午4:15:23 SuMMeR Exp $
*/

package com.sandrew.fileserver.util;

import java.util.Random;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月9日
 * @version    :
 */
public class FileUtil
{
	/**
	 * 
	 * Function    : 文件路径格式化，统一变成/
	 * LastUpdate  : 2016年9月9日
	 * @param originalPath
	 * @return
	 */
	public static String pathFormat(String originalPath)
	{
		String newPath = null;
		if(null != originalPath)
		{
			newPath = originalPath.replaceAll("\\\\\\\\", "\\\\");		// 把\\转成\
			newPath = newPath.replaceAll("//", "\\\\");		// 把//转成\
			newPath = newPath.replaceAll("\\\\", "\\/");		// 把\转成/
		}
		return newPath;
	}
	

	/**
	 * 
	 * Function    : 根据文件名解析出nodeId
	 * LastUpdate  : 2012-7-13
	 * @param fileName
	 * @return
	 */
	public static String getNodeId(String fileName)
	{
		String fileNameWithOutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
		// 删除当前毫秒数(13位) + 随机数(5位)，获取加密后的nodeId
		String encodeNodeId = fileNameWithOutSuffix.substring(Constants.FILESERVER_CUR_TIME_LENGTH + Constants.FILESERVER_RANDOM_LENGTH);
		String nodeId = CellarUtil.decode(encodeNodeId);
		return nodeId;
	}

	/**
	 * 
	 * Function    : 根据NodeId及FileName生成文件路径
	 *               文件名长度42位
	 *               生成规则: 当前毫秒数(13位) + 随机数(5位) + 加密的nodeId(位数不定).后缀名
	 * LastUpdate  : 2012-6-1
	 * @param nodeId
	 * @param fileName
	 * @return
	 */
	public static String fileNameMaker(String nodeId, String fileName)
	{
		String curTimeMillis = "" + System.currentTimeMillis(); //13位
		String random = "" + ((new Random().nextInt(89999)) + 10000); //5位
		String encodeNode = CellarUtil.encode(nodeId); //位数不固定
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return curTimeMillis + random + encodeNode + "." + suffix;
	}


	public static void main(String[] args)
	{
		System.out.println(fileNameMaker("node01", "mydb.all.sql"));
		System.out.println(getNodeId("1473514239680482306E6F64653031.sql"));
		
	}
}
