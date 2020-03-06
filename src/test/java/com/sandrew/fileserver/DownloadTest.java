package com.sandrew.fileserver;

import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.core.FTPManager;
import com.sandrew.fileserver.util.CellarUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DownloadTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public DownloadTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(DownloadTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		try
		{
			AbstractManager manager = new FTPManager();
			manager.open("118.190.158.35", 27051, "develop", "123456");
			byte[] bytes = manager.download("/a/b/c/d", "car.rar");
			manager.close();
			System.out.println("bytes =======" + bytes.length);
			CellarUtil.byteToFile(bytes, "f://car.rar");
		}
		catch (Exception e)
		{
		}
	
	}
}
