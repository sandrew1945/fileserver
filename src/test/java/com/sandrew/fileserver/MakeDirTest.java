package com.sandrew.fileserver;

import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.core.FTPManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MakeDirTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public MakeDirTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(MakeDirTest.class);
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
			manager.createDirectory("/f1/f4/f3/f5");
			manager.close();	
		}
		catch (Exception e)
		{
		}
	
	}
}
