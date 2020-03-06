package com.sandrew.fileserver;

import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.core.FTPManager;
import com.sandrew.fileserver.exception.FileServerException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class FTPManagerTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public FTPManagerTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(FTPManagerTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		
		try
		{
			AbstractManager manager = new FTPManager();
			manager.open("192.168.1.26", 21, "develop", "123456");
			List<String> files = manager.fileList("/PowerDesigner/");
			if(null != files)
			{
				System.out.println(files.size());
			}
			manager.close();
		}
		catch (FileServerException e)
		{
			e.printStackTrace();
		}
	}
}
