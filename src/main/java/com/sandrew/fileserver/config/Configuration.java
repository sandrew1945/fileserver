/**********************************************************************
* <pre>
* FILE : Configuration.java
* CLASS : Configuration
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
* $Id: Configuration.java,v 0.1 2016年9月10日 下午7:39:56 SuMMeR Exp $
*/

package com.sandrew.fileserver.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Function    : 
 * @author     : SuMMeR
 * CreateDate  : 2016年9月10日
 * @version    :
 */
@Data
public class Configuration
{
	private String host;

	private Integer port;

	private String username;

	private String password;

	private HashMap<String, NodeInfo> nodes = new HashMap<String, NodeInfo>();

	/**
	 * 
	 * Function    : 增加一个节点信息
	 * LastUpdate  : 2016年9月10日
	 * @param node
	 */
	public void addNode(String nodeName, NodeInfo node)
	{
		nodes.put(nodeName, node);
	}
	
	/**
	 * 
	 * Function    : 根据节点ID获取节点信息
	 * LastUpdate  : 2016年9月13日
	 * @param nodeId
	 * @return
	 */
	public NodeInfo getNode(String nodeId)
	{
		return nodes.get(nodeId);
	}

	/**
	 * 
	 * Function    : 随机获取一个node
	 * LastUpdate  : 2016年9月10日
	 * @return
	 */
	public NodeInfo getRandomNode()
	{
		Random random = new Random();
		int randomIndex = random.nextInt(nodes.size());
		Set<String> keys = nodes.keySet();
		int i = 0;
		for (String key : keys)
		{
			if (i == randomIndex)
			{
				return nodes.get(key);
			}
			i++;
		}
		return null;
	}

}
