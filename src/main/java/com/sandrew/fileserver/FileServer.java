/**********************************************************************
* <pre>
* FILE : FileServer.java
* CLASS : FileServer
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
* $Id: FileServer.java,v 0.1 2016年9月10日 下午8:52:02 SuMMeR Exp $
*/

package com.sandrew.fileserver;

import com.sandrew.fileserver.config.ConfigParser;
import com.sandrew.fileserver.config.Configuration;
import com.sandrew.fileserver.config.NodeInfo;
import com.sandrew.fileserver.config.XMLConfigParser;
import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.exception.FileServerException;
import com.sandrew.fileserver.util.Constants;
import com.sandrew.fileserver.util.DESUtil;
import com.sandrew.fileserver.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月10日
 * @version    :
 */
public class FileServer
{
	private static Configuration configuration;

	private AbstractManager manager;

	public FileServer(AbstractManager aManager)
	{
		if (null == configuration)
		{
			init();
		}
		manager = aManager;
	}

	/**
	 * 
	 * Function    : 初始化方法
	 * LastUpdate  : 2016年9月10日
	 */
	private synchronized void init()
	{
		try
		{
			if (null == configuration)
			{
				ConfigParser parser = new XMLConfigParser();
				configuration = parser.parse();
			}
		}
		catch (FileServerException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * Function    : 指定节点ID上传文件
	 * LastUpdate  : 2016年9月13日
	 * @param nodeId			要上传的节点ID
	 * @param originalFileName	原始文件名
	 * @param fis				文件流
	 * @return
	 * @throws FileServerException
	 */
	public String upload(String nodeId, String originalFileName, FileInputStream fis) throws FileServerException
	{
		// 根据nodeId获取远程路径
		NodeInfo nodeInfo = configuration.getNode(nodeId);
		if(null == nodeInfo)
		{
			throw new FileServerException("上传的节点ID不存在");
		}
		String remoteFileName = FileUtil.fileNameMaker(nodeId, originalFileName);
		String remoteCompletePath = nodeInfo.getFileRoot() + Constants.FILE_SEPARATOR + remoteFileName;
		connent();
		boolean isUpload = manager.upload(fis, remoteCompletePath);
		if(isUpload)
		{
			return remoteCompletePath;
		}
		return null;
	}

	/**
	 * 
	 * Function    : 随机节点上传文件
	 * LastUpdate  : 2016年9月12日
	 * @param originalFileName
	 * @param fis
	 * @return
	 * @throws FileServerException
	 */
	public String upload(String originalFileName, FileInputStream fis) throws FileServerException
	{
		// 随机获取远程路径
		NodeInfo nodeInfo = configuration.getRandomNode();
		String remoteFileName = FileUtil.fileNameMaker(nodeInfo.getId(), originalFileName);
		String remoteCompletePath = nodeInfo.getFileRoot() + Constants.FILE_SEPARATOR + remoteFileName;
		connent();
		boolean isUpload = manager.upload(fis, remoteCompletePath);
		if(isUpload)
		{
			return remoteCompletePath;
		}
		return null;
	}
	
	/**
	 * 
	 * Function    : 根据节点ID上传文件
	 * LastUpdate  : 2016年9月13日
	 * @param nodeId
	 * @param file
	 * @return
	 * @throws FileServerException
	 */
	public String upload(String nodeId, File file) throws FileServerException
	{
		// 根据nodeId获取远程路径
		NodeInfo nodeInfo = configuration.getNode(nodeId);
		if(null == nodeInfo)
		{
			throw new FileServerException("上传的节点ID不存在");
		}
		String remoteFileName = FileUtil.fileNameMaker(nodeId, file.getName());
		String remoteCompletePath = nodeInfo.getFileRoot() + Constants.FILE_SEPARATOR + remoteFileName;
		connent();
		boolean isUpload = manager.upload(file, remoteCompletePath);
		if(isUpload)
		{
			return remoteCompletePath;
		}
		return null;
	}
	
	/**
	 * 
	 * Function    : 随机节点上传文件
	 * LastUpdate  : 2016年9月13日
	 * @param file
	 * @return
	 * @throws FileServerException
	 */
	public String upload(File file) throws FileServerException
	{
		// 根据nodeId获取远程路径
		NodeInfo nodeInfo = configuration.getRandomNode();
		String remoteFileName = FileUtil.fileNameMaker(nodeInfo.getId(), file.getName());
		String remoteCompletePath = nodeInfo.getFileRoot() + Constants.FILE_SEPARATOR + remoteFileName;
		connent();
		boolean isUpload = manager.upload(file, remoteCompletePath);
		if(isUpload)
		{
			return remoteCompletePath;
		}
		return null;
	}

//	public String upload(String address, int port, String user, String password, String relativePath, File file) throws FileServerException
//	{
//		// 根据nodeId获取远程路径
//		String remoteFileName = FileUtil.fileNameMaker(nodeInfo.getId(), file.getName());
//		String remoteCompletePath = nodeInfo.getFileRoot() + Constants.FILE_SEPARATOR + remoteFileName;
//		connent();
//		boolean isUpload = manager.upload(file, relativePath, "nginx.conf");
//		if(isUpload)
//		{
//			return remoteCompletePath;
//		}
//		return null;
//	}

	/**
	 * 
	 * Function    : 下载远程文件
	 * LastUpdate  : 2016年9月12日
	 * @param remoteCompletePath
	 * @return
	 * @throws FileServerException
	 */
	public byte[] download(String remoteCompletePath) throws FileServerException
	{
		connent();
		return manager.download(remoteCompletePath);
	}

	/**
	 * 
	 * Function    : 下载远程文件
	 * LastUpdate  : 2016年9月12日
	 * @param remotePath
	 * @param remoteFileName
	 * @return
	 * @throws FileServerException
	 */
	public byte[] download(String remotePath, String remoteFileName) throws FileServerException
	{
		connent();
		return manager.download(remotePath, remoteFileName);
	}

	/**
	 * 
	 * Function    : 删除远程文件
	 * LastUpdate  : 2016年9月12日
	 * @param remoteCompletePath
	 * @return
	 * @throws FileServerException
	 */
	public boolean remove(String remoteCompletePath) throws FileServerException
	{
		connent();
		return manager.remove(remoteCompletePath);
	}

	/**
	 * 
	 * Function    : 删除远程文件
	 * LastUpdate  : 2016年9月12日
	 * @param remotePath
	 * @param RemoteFileName
	 * @return
	 * @throws FileServerException
	 */
	public boolean remove(String remotePath, String RemoteFileName) throws FileServerException
	{
		connent();
		return manager.remove(remotePath, RemoteFileName);
	}

	/**
	 * 
	 * Function    : 获取指定远程目录下的文件列表
	 * LastUpdate  : 2016年9月12日
	 * @param remotePath
	 * @return
	 * @throws FileServerException
	 */
	public List<String> fileList(String remotePath) throws FileServerException
	{
		connent();
		return manager.fileList(remotePath);
	}

	private void connent() throws FileServerException
	{
		boolean isConnect = manager.open(configuration.getHost(), configuration.getPort(), configuration.getUsername(), DESUtil.fadeOutDES(configuration.getPassword()));
		if (!isConnect)
		{
			throw new FileServerException("连接服务器失败");
		}
	}

}
