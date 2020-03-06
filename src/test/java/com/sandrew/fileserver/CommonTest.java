/**********************************************************************
* <pre>
* FILE : CommonTest.java
* CLASS : CommonTest
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
* 		  |2016年9月10日| SuMMeR| Created |
* DESCRIPTION:
* </pre>
***********************************************************************/
/**
* $Id: CommonTest.java,v 0.1 2016年9月10日 下午7:21:04 SuMMeR Exp $
*/

package com.sandrew.fileserver;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月10日
 * @version    :
 */
public class CommonTest
{
	public static void main(String[] args)
	{
		String formatPath = "/f1/f2/f3/f4/healthcare.sql";
		String remoteFileName = formatPath.substring(formatPath.lastIndexOf("/") + 1);
		String remotePath = formatPath.substring(0, formatPath.lastIndexOf("/"));
		System.out.println(remoteFileName);
		System.out.println(remotePath);
	}
}
