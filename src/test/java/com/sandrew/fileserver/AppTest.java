package com.sandrew.fileserver;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		String s = "\\\\PowerDesigner/";
		
		String newPath = s.replaceAll("\\\\\\\\", "\\\\");		// 把\\转成\
		newPath = newPath.replaceAll("//", "\\\\");		// 把//转成\
		newPath = newPath.replaceAll("\\\\", "\\/");		// 把\转成/	
		
		System.out.println(s);
		System.out.println(newPath);
		
	}
}
