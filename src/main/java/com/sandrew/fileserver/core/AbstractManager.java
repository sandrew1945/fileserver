/**********************************************************************
* <pre>
* FILE : AbstractClient.java
* CLASS : AbstractClient
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
* $Id: AbstractClient.java,v 0.1 2016年9月9日 上午10:33:30 SuMMeR Exp $
*/

package com.sandrew.fileserver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.sandrew.fileserver.exception.FileServerException;
import com.sandrew.fileserver.util.FileUtil;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月9日
 * @version    :
 */
public abstract class AbstractManager
{
	/**
	 * 
	 * Function    : 连接服务器
	 * LastUpdate  : 2016年9月9日
	 * @param url
	 * @param port
	 * @param userName
	 * @param password
	 */
	public abstract boolean open(String url, Integer port, String userName, String password) throws FileServerException;

	/**
	 * 
	 * Function    : 关闭连接
	 * LastUpdate  : 2016年9月9日
	 */
	public abstract void close() throws FileServerException;

	/**
	 * 
	 * Function    : 
	 * LastUpdate  : 2016年9月13日
	 * @param file					本地文件句柄
	 * @param remoteCompletePath	远程完整路径
	 * @return
	 * @throws FileServerException
	 */
	public boolean upload(File file, String remoteCompletePath) throws FileServerException
	{
		String formatPath = FileUtil.pathFormat(remoteCompletePath); // 格式化路径
		String remotePath = null; // 解析后的文件路径
		String remoteFileName = null; // 解析后的远程文件名
		if (formatPath.contains("/"))
		{
			remoteFileName = formatPath.substring(formatPath.lastIndexOf("/") + 1);
			remotePath = formatPath.substring(0, formatPath.lastIndexOf("/"));
		}
		else
		{
			remoteFileName = formatPath;
		}
		return this.upload(file, remotePath, remoteFileName);
	}
	
	/**
	 * 
	 * Function    : 上传文件
	 * LastUpdate  : 2016年9月9日
	 * @param file				本地文件句柄
	 * @param remotePath 		远程目录
	 * @param remoteFileName	远程文件名
	 * @return
	 * @throws FileServerException 
	 */
	public boolean upload(File file, String remotePath, String remoteFileName) throws FileServerException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			return upload(fis, remotePath, remoteFileName);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new FileServerException("文件上传错误:" + file.getName(), e);
		}
		finally
		{
			if (null != fis)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * Function    : 
	 * LastUpdate  : 2016年9月9日
	 * @param fis					本地文件流
	 * @param remoteCompletePath	远程完整路径
	 * @return
	 * @throws FileServerException
	 */
	public boolean upload(FileInputStream fis, String remoteCompletePath) throws FileServerException
	{
		// 解析远程完整路径
		String formatPath = FileUtil.pathFormat(remoteCompletePath); // 格式化路径
		String remotePath = null; // 解析后的文件路径
		String remoteFileName = null; // 解析后的远程文件名
		if (formatPath.contains("/"))
		{
			remoteFileName = formatPath.substring(formatPath.lastIndexOf("/") + 1);
			remotePath = formatPath.substring(0, formatPath.lastIndexOf("/"));
		}
		else
		{
			remoteFileName = formatPath;
		}
		return this.upload(fis, remotePath, remoteFileName);
	}

	/**
	 * 
	 * Function    : 上传文件
	 * LastUpdate  : 2016年9月9日
	 * @param fis				本地文件流
	 * @param remotePath 		远程目录
	 * @param remoteFileName	远程文件名
	 * @return
	 */
	public abstract boolean upload(FileInputStream fis, String remotePath, String remoteFileName) throws FileServerException;

	/**
	 * 
	 * Function    : 下载文件
	 * LastUpdate  : 2016年9月12日
	 * @param remoteCompletePath	// 远程附录及文件名
	 * @return
	 * @throws FileServerException
	 */
	public byte[] download(String remoteCompletePath) throws FileServerException
	{
		// 解析远程完整路径
		String formatPath = FileUtil.pathFormat(remoteCompletePath); // 格式化路径
		String remotePath = null; // 解析后的文件路径
		String remoteFileName = null; // 解析后的远程文件名
		if (formatPath.contains("/"))
		{
			remoteFileName = formatPath.substring(formatPath.lastIndexOf("/") + 1);
			remotePath = formatPath.substring(0, formatPath.lastIndexOf("/"));
		}
		else
		{
			remoteFileName = formatPath;
		}
		return this.download(remotePath, remoteFileName);
	}

	/**
	 * 
	 * Function    : 下载文件
	 * LastUpdate  : 2016年9月9日
	 * @param remotePath		// 远程目录
	 * @param remoteFileName	// 远程文件名
	 * @return
	 */
	public abstract byte[] download(String remotePath, String remoteFileName) throws FileServerException;

	/**
	 * 
	 * Function    : 获取目录下所有文件名
	 * LastUpdate  : 2016年9月9日
	 * @param path
	 * @return
	 */
	public abstract List<String> fileList(String path) throws FileServerException;

	/**
	 * 
	 * Function    : 
	 * LastUpdate  : 2016年9月12日
	 * @param remoteCompletePath	//远程附录及文件名
	 * @return
	 * @throws FileServerException
	 */
	public boolean remove(String remoteCompletePath) throws FileServerException
	{
		// 解析远程完整路径
		String formatPath = FileUtil.pathFormat(remoteCompletePath); // 格式化路径
		String remotePath = null; // 解析后的文件路径
		String remoteFileName = null; // 解析后的远程文件名
		if (formatPath.contains("/"))
		{
			remoteFileName = formatPath.substring(formatPath.lastIndexOf("/") + 1);
			remotePath = formatPath.substring(0, formatPath.lastIndexOf("/"));
		}
		else
		{
			remoteFileName = formatPath;
		}
		return remove(remotePath, remoteFileName);
	}

	/**
	 * 
	 * Function    : 删除文件
	 * LastUpdate  : 2016年9月9日
	 * @param path
	 * @param fileName
	 * @return
	 */
	public abstract boolean remove(String remotePath, String RemoteFileName) throws FileServerException;

	/**
	 * 
	 * Function    : 创建目录
	 * LastUpdate  : 2016年9月9日
	 * @param path
	 */
	public abstract boolean createDirectory(String path) throws FileServerException;

	/**
	 * 
	 * Function    : 删除目录
	 * LastUpdate  : 2016年9月9日
	 * @param path
	 */
	public abstract void deleteDirectory(String path) throws FileServerException;
}
