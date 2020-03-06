package com.sandrew.fileserver.config;

import java.util.Set;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * Function    : 节点信息
 * @author     : SuMMeR
 * CreateDate  : 2016年9月10日
 * @version    :
 */
@Data
public class NodeInfo
{
	private String id;

	private Set<String> roles;

	private String fileRoot;

	@Override
	public String toString()
	{
		return "NodeInfo [id=" + id + ", fileRoot=" + fileRoot + "]";
	}
	
	
}
