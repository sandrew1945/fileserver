package com.sandrew.fileserver;

import com.sandrew.fileserver.core.AbstractManager;
import com.sandrew.fileserver.core.FTPManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Unit test for simple App.
 */
@Slf4j
public class UploadTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public UploadTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(UploadTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		try
		{
			AbstractManager manager = new FTPManager();
			manager.open("192.190.158.35", 27051, "ftp", "123456");
			boolean isupload = manager.upload(new File("/Users/summer/Desktop/nginx.conf"), "node1", "nginx.conf");
			log.debug("" + isupload);
		}
		catch (Exception e)
		{
		}
	
	}
}
