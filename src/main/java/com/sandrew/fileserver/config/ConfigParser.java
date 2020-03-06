/**********************************************************************
* <pre>
* FILE : ConfigParser.java
* CLASS : ConfigParser
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
* 		  |2012-4-19| SuMMeR| Created |
* DESCRIPTION:
* </pre>
***********************************************************************/
/**
* $Id: ConfigParser.java,v 1.2 2012/06/07 02:44:37 weibin Exp $
*/

package com.sandrew.fileserver.config;

import com.sandrew.fileserver.exception.FileServerException;

/**
 * Function    : 文件服务器配置文件解析器
 * @author     : SuMMeR
 * CreateDate  : 2012-4-19
 * @version    :
 */
public interface ConfigParser
{

	/**
	 * 
	 * Function    : 解析配置文件
	 * LastUpdate  : 2012-4-25
	 * @return
	 */
	public Configuration parse() throws FileServerException;
}
