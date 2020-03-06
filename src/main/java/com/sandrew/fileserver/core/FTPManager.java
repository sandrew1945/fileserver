/**********************************************************************
* <pre>
* FILE : FTPManager.java
* CLASS : FTPManager
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
* $Id: FTPManager.java,v 0.1 2016年9月9日 上午11:55:14 SuMMeR Exp $
*/

package com.sandrew.fileserver.core;

import com.sandrew.fileserver.exception.FileServerException;
import com.sandrew.fileserver.util.CellarUtil;
import com.sandrew.fileserver.util.Constants;
import com.sandrew.fileserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月9日
 * @version    :
 */
@Slf4j
public class FTPManager extends AbstractManager
{

	private FTPClient client = null;

	private static final String DEAFULT_REMOTE_CHARSET = "UTF-8"; // 默认的远程字符集


	/* (non-Javadoc)
	 * @see com.sandrew.fileserver.core.AbstractManager#open(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean open(String url, Integer port, String userName, String password) throws FileServerException
	{
		try
		{
			if (null != this.client && this.client.isConnected())
			{
				return true;
			}
			this.client = new FTPClient();
			//			this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
			this.client.setControlEncoding(DEAFULT_REMOTE_CHARSET); // 设置编码格式
			if (null == port)
			{
				this.client.connect(url);
			}
			else
			{
				this.client.connect(url, port);
			}

			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");

			this.client.login(userName, password); // 登录
			int replyCode = this.client.getReplyCode();
			return FTPReply.isPositiveCompletion(replyCode); // 返回连接结果
		}
		catch (SocketException e)
		{
			e.printStackTrace();
			this.close();
			throw new FileServerException("连接FTP失败", e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.close();
			throw new FileServerException("连接FTP失败", e);
		}

	}

	@Override
	public void close() throws FileServerException
	{
		try
		{
			if (null != this.client && this.client.isConnected())
			{
				this.client.disconnect();
				log.info("关闭FTP连接");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new FileServerException("关闭FTP连接失败", e);
		}
	}

	@Override
	public boolean upload(FileInputStream fis, String remotePath, String remoteFileName) throws FileServerException
	{
		try
		{
			this.client.enterLocalPassiveMode();
			this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 判断远程目录是否存在
			boolean hasDir = this.client.changeWorkingDirectory(remotePath);
			if (!hasDir)
			{
				//如果路径不存在，解析路径并创建
				String formatRemotePath = FileUtil.pathFormat(remotePath); //格式化路径
				// 创建目录
				this.createDirectory(formatRemotePath);
			}
			this.client.changeWorkingDirectory(remotePath); // 进入目录，写入文件
			// 检查文件是否存在
			InputStream is = this.client.retrieveFileStream(remoteFileName);
			if (null != is)
			{
				throw new FileServerException("FTP已经存在该文件");
			}
			return this.client.storeFile(remoteFileName, fis); // 上传文件
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new FileServerException("FTP上传文件失败", e);
		}
		finally
		{
			this.close();
		}
	}

	@Override
	public byte[] download(String remotePath, String remoteFileName) throws FileServerException
	{
		try
		{
			this.client.enterLocalPassiveMode();
			this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 如果未连接，返回null
			if (!this.client.isConnected())
			{
				return null;
			}
			this.client.enterLocalPassiveMode(); // 防止卡死

			String formatRemotePath = FileUtil.pathFormat(remotePath); //格式化路径
			this.client.changeWorkingDirectory(formatRemotePath); // 进入目录，查找文件
			InputStream is = this.client.retrieveFileStream(remoteFileName);
			if (null == is)
			{
				return null;
			}
			else
			{
				// 将InputStream转为字节数组
				return CellarUtil.inputStreamToByte(is);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new FileServerException("不存在要访问的目录", e);
		}
		finally
		{
			this.close();
		}
	}

	@Override
	public List<String> fileList(String path) throws FileServerException
	{
		List<String> fileNames = null;
		try
		{
			// 切换远程目录，如果目录不存在，那么返回null
			boolean hasDir = client.changeWorkingDirectory(path);
			if (!hasDir)
			{
				// 目录不存在
				log.info("远程目录不存在");
				return null;
			}
			FTPFile[] files = client.listFiles();
			fileNames = new ArrayList<String>();
			for (FTPFile ftpFile : files)
			{
				log.debug("目录" + path + "文件 ============> " + ftpFile.getName());
				fileNames.add(ftpFile.getName());
			}
			return fileNames;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new FileServerException("获取FTP文件列表失败", e);
		}
		finally
		{
			this.close();
		}
	}

	@Override
	public boolean remove(String path, String fileName) throws FileServerException
	{
		try
		{
			// 判断path是否以/结尾
			if (!path.endsWith(Constants.FILE_SEPARATOR))
			{
				path = path + Constants.FILE_SEPARATOR;
			}
			return this.client.deleteFile(path + fileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new FileServerException("删除FTP文件失败", e);
		}
		finally
		{
			this.close();
		}
	}

	@Override
	public boolean createDirectory(String remotePath) throws FileServerException
	{
		boolean isCompleteCreate = false;
		try
		{
			//如果路径不存在，解析路径并创建
			String formatRemotePath = FileUtil.pathFormat(remotePath); //格式化路径
			// 创建目录
			if (null != formatRemotePath && formatRemotePath.contains("/"))
			{
				String[] pathParts = formatRemotePath.split("/");
				StringBuffer currentPath = new StringBuffer();
				boolean isCreate = false;
				for (String part : pathParts)
				{
					// 空目录不处理
					if (null == part || "".equals(part))
					{
						continue;
					}
					currentPath.append(part).append(Constants.FILE_SEPARATOR);
					// 逐层创建文件夹
					isCreate = this.client.makeDirectory(currentPath.toString());
					if (!isCreate)
					{
						isCompleteCreate = false;
						break;
					}
					isCompleteCreate = true;
				}
			}
			return isCompleteCreate;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new FileServerException("创建远程文件夹失败", e);
		}
	}

	@Override
	public void deleteDirectory(String path) throws FileServerException
	{

	}

}
