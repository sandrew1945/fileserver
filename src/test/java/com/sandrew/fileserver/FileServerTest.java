package com.sandrew.fileserver;

import com.sandrew.fileserver.core.FTPManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;

/**
 * Unit test for simple App.
 */
@Slf4j
public class FileServerTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public FileServerTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(FileServerTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		try
		{
			FileServer server = new FileServer(new FTPManager());
			File file = new File("/Users/summer/Desktop/nginx.conf");
			log.debug("fileid ====> " + server.upload("nginx.conf", new FileInputStream(file)));
			//byte[] bytes = server.download("/node01/1473754757118221416E6F64653031.rar");
//			if(null != bytes)
//			{
//				System.out.println(bytes.length);
//			}
//			else
//			{
//				System.out.println("no file");
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
}
