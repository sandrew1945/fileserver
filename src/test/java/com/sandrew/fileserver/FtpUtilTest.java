package com.sandrew.fileserver;

import com.sandrew.fileserver.util.FtpUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
@Slf4j
public class FtpUtilTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public FtpUtilTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(FtpUtilTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		try
		{
//			AbstractManager manager = new FTPManager();
//			manager.open("118.190.158.35", 27051, "ftp", "123456");
//			boolean isupload = manager.upload(new File("/Users/summer/Desktop/nginx.conf"), "node1", "nginx.conf");
//			log.debug("" + isupload);

			FtpUtil util = new FtpUtil();
			util.initClient("192.190.158.35", 27051, "ftp", "123456");
			//boolean result = util.upload("/util/test", new File("/Users/summer/Desktop/nginx.conf"));
			//log.debug("file size =====> " + bytes.length);
			//boolean result = util.remove("/util/test/nginx.conf");

			//log.debug("opt success ====> " + result);
			List<File> files = new ArrayList<File>();
			files.add(new File("/Users/summer/Desktop/nginx.conf"));
			boolean[] results = util.upload("/util/test", files);
			for (boolean result : results)
			{
				log.debug("result =====> " + result);
			}

		}
		catch (Exception e)
		{
		}
	
	}
}
