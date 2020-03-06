package com.sandrew.fileserver;

import java.io.File;
import java.util.List;

import com.sandrew.fileserver.config.ConfigParser;
import com.sandrew.fileserver.config.Configuration;
import com.sandrew.fileserver.config.NodeInfo;
import com.sandrew.fileserver.config.XMLConfigParser;
import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.core.FTPManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConfigTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public ConfigTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(ConfigTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		try
		{
			ConfigParser parser = new XMLConfigParser();
			Configuration configuration = parser.parse();

			NodeInfo node = configuration.getRandomNode();
			System.out.println(node);
//			for (int i = 0; i < 100; i++)
//			{
//				NodeInfo node = configuration.getRandomNode();
//				System.out.println(node);
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
