/**********************************************************************
* <pre>
* FILE : XMLConfigParser.java
* CLASS : XMLConfigParser
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
* $Id: XMLConfigParser.java,v 1.2 2012/06/07 02:44:37 weibin Exp $
*/

package com.sandrew.fileserver.config;

import com.sandrew.fileserver.exception.FileServerException;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;


/**
 * Function    : 文件服务器配置文件解析器
 * @author     : SuMMeR
 * CreateDate  : 2012-4-19
 * @version    :
 */
@Slf4j
public class XMLConfigParser implements ConfigParser
{

	/* (non-Javadoc)
	 * @see com.autosys.fileserver.config.ConfigParser#parse()
	 */
	public Configuration parse() throws FileServerException
	{
		try
		{
			log.debug("开始解析Fileserver.xml");
			Document doc = getConfigureFile();
			// 解析服务器连接信息
			Configuration config = new Configuration();
			Node hostNode = doc.selectSingleNode("/FileServer/ServerConfig/host");
			if (null != hostNode)
			{
				config.setHost(hostNode.getText());
			}
			Node port = doc.selectSingleNode("/FileServer/ServerConfig/port");
			if (null != port)
			{
				config.setPort(Integer.valueOf(port.getText()));
			}
			Node usernameNode = doc.selectSingleNode("/FileServer/ServerConfig/username");
			if (null != usernameNode)
			{
				config.setUsername(usernameNode.getText());
			}
			Node passwordNode = doc.selectSingleNode("/FileServer/ServerConfig/password");
			if (null != passwordNode)
			{
				config.setPassword(passwordNode.getText());
			}
			// 解析服务器存储节点信息
			List<Node> nodes = doc.selectNodes("/FileServer/Nodes/Node");
			for (Node node : nodes)
			{
				NodeInfo nodeInfo = new NodeInfo();
				String nodeId = node.valueOf("@id");
				nodeInfo.setId(nodeId);
				Node fileRootNode = node.selectSingleNode("FileRoot");
				nodeInfo.setFileRoot(fileRootNode.getText());
				config.addNode(nodeId, nodeInfo);
			}
			return config;
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
			throw new FileServerException("Configuration file parse failure!", e);
		}
	}


	/**
	 * 
	 * Function    : 
	 * LastUpdate  : 2012-4-25
	 * @return
	 * @throws DocumentException
	 */
	private Document getConfigureFile() throws DocumentException
	{
		return getConfigureFile("/FileServer.xml");
	}

	/**
	 * 
	 * Function    : 获取配置文件
	 * LastUpdate  : 2012-4-25
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 */
	private Document getConfigureFile(String fileName) throws DocumentException
	{
		// 获取配置文件的输入流
		InputStream is = this.getClass().getResourceAsStream(fileName);
		// File file = new File();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		return doc;
	}

	public static void main(String[] args)
	{
		XMLConfigParser x = new XMLConfigParser();
		try
		{
			x.parse();
		}
		catch (FileServerException e)
		{
			e.printStackTrace();
		}
	}

}
