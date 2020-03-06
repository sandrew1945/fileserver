package com.sandrew.fileserver.util;

import com.sandrew.fileserver.exception.FileServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by summer on 2020/3/6.
 */
@Slf4j
public class FtpUtil
{
    private FTPClient client = null;

    private static final String DEAFULT_REMOTE_CHARSET = "UTF-8"; // 默认的远程字符集

    /**
     *  初始化客户端
     * @param host
     * @param port
     * @param user
     * @param password
     * @return
     * @throws FileServerException
     */
    public boolean initClient(String host, Integer port, String user, String password) throws FileServerException
    {
        boolean isInit = false;
        try
        {
            this.client = new FTPClient();
            //			this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
            this.client.setControlEncoding(DEAFULT_REMOTE_CHARSET); // 设置编码格式
            if (null == port)
            {
                this.client.connect(host);
            }
            else
            {
                this.client.connect(host, port);
            }

            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");

            this.client.login(user, password); // 登录
            int replyCode = this.client.getReplyCode();
            isInit = FTPReply.isPositiveCompletion(replyCode); // 返回连接结果
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            this.close();
        }
        return isInit;
    }

    /**
     *  上传文件
     * @param relativePath
     * @param file
     * @return
     * @throws FileServerException
     */
    public boolean upload(String relativePath, File file) throws FileServerException
    {
        try
        {
            return this.doUpload(relativePath, file);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new FileServerException("FTP上传文件失败", e);
        }
        finally
        {
            this.close();
        }
    }

    /**
     *  批量上传文件到同一目录
     * @param relativePath
     * @param fileList
     * @return
     * @throws FileServerException
     */
    public boolean[] upload(String relativePath, List<File> fileList) throws FileServerException
    {
        boolean[] results = new boolean[fileList.size()];
        try
        {
            for (int i = 0; i < fileList.size(); i++)
            {
                results[i] = this.doUpload(relativePath, fileList.get(i));
            }
            return results;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new FileServerException("FTP上传文件失败", e);
        }
        finally
        {
            this.close();
        }
    }

    /**
     *  下载远程文件
     * @param relativePath
     * @return
     * @throws FileServerException
     */
    public byte[] download(String relativePath) throws FileServerException
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

            String formatRemotePath = FileUtil.pathFormat(relativePath); //格式化路径
            this.client.changeWorkingDirectory(formatRemotePath); // 进入目录，查找文件
            InputStream is = this.client.retrieveFileStream(relativePath);
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


    /**
     *  删除远程文件
     * @param relativePath
     * @return
     * @throws FileServerException
     */
    public boolean remove(String relativePath) throws FileServerException
    {
        try
        {
            // 判断path是否以/结尾
            return this.client.deleteFile(relativePath);
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

    private boolean doUpload(String relativePath, File file) throws FileServerException
    {
        try
        {
            this.client.enterLocalPassiveMode();
            this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 判断远程目录是否存在
            boolean hasDir = this.client.changeWorkingDirectory(relativePath);
            if (!hasDir)
            {
                //如果路径不存在，解析路径并创建
                String formatRemotePath = FileUtil.pathFormat(relativePath); //格式化路径
                // 创建目录
                this.createDirectory(formatRemotePath);
            }
            this.client.changeWorkingDirectory(relativePath); // 进入目录，写入文件
            // 检查文件是否存在
            InputStream is = this.client.retrieveFileStream(file.getName());
            if (null != is)
            {
                throw new FileServerException("FTP已经存在该文件");
            }
            return this.client.storeFile(file.getName(), new FileInputStream(file)); // 上传文件
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new FileServerException("FTP上传文件失败", e);
        }
    }


    /**
     *  关闭客户端
     * @throws FileServerException
     */
    private void close() throws FileServerException
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


}
