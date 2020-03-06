/**********************************************************************
* <pre>
* FILE : FileServerException.java
* CLASS : FileServerException
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
* $Id: FileServerException.java,v 0.1 2016年9月9日 上午11:19:05 SuMMeR Exp $
*/

package com.sandrew.fileserver.exception;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月9日
 * @version    :
 */
public class FileServerException extends Exception
{
	public FileServerException()
	{
		super();
	}

	public FileServerException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FileServerException(String message)
	{
		super(message);
	}

	public FileServerException(Throwable cause)
	{
		super(cause);
	}
}
