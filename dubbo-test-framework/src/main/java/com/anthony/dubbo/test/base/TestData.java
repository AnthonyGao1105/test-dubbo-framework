package com.anthony.dubbo.test.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author anthony
 * @date Jul 5, 2017
 * @updateTime 4:21:48 PM
 */

public class TestData implements Iterator<Object[]> {
	BufferedReader in;
	ArrayList<String> csvList = new ArrayList<String>();
	int rowNum = 0; // 行数
	int columnNum = 0; // 列数
	int curRowNo = 0; // 当前行数
	String columnName[]; // 列名

	/**
	 * 在TestNG中由@DataProvider(dataProvider = "name")修饰的方法
	 * 取csv文件数据时时，调用此类构造方法（此方法会得到列名并将当前行移到下以后）执行后，转发到
	 * TestNG自己的方法中去，然后由它们调用此类实现的hasNext()、next()方法
	 * 得到一行数据，然后返回给由@Test(dataProvider = "name")修饰的方法，如此 反复到数据读完为止
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public TestData(String fileName) throws IOException {
		File directory = new File(".");
		String path = ".src.main.resources.";
		String absolutePath = directory.getCanonicalPath() + path.replaceAll("\\.", Matcher.quoteReplacement("\\"))
				+ fileName;

		Properties pro = System.getProperties();
		String osName = pro.getProperty("os.name");// 获得当前操作系统的名称
		if ("Linux".equals(osName) || "linux".equals(osName) || "LINUX".equals(osName)) {
			absolutePath = directory.getCanonicalPath() + path.replaceAll("\\.", Matcher.quoteReplacement("//"))
					+ fileName;
			// linux环境下的路径
		} else if (osName.contains("Mac")) {
			absolutePath = directory.getCanonicalPath() + path.replaceAll("\\.", Matcher.quoteReplacement("//"))
					+ fileName;
			// mac环境下的路径
		}
		File csv = new File(absolutePath);
		in = new BufferedReader(new FileReader(csv));
		while (in.ready()) {
			csvList.add(in.readLine());
			this.rowNum++;
		}
		String[] str = csvList.get(0).split(",");
		this.columnNum = str.length;
		columnName = new String[columnNum];
		// 获取列名
		for (int i = 0; i < columnNum; i++) {
			columnName[i] = str[i];
		}
		this.curRowNo++;
	}

	public boolean hasNext() {
		if (rowNum == 0 || curRowNo >= rowNum) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取一组参数，即一行数据
	 */
	public Object[] next() {
		Map<String, String> s = new TreeMap<String, String>();
		String csvCell[] = csvList.get(curRowNo).split(",");
		for (int i = 0; i < this.columnNum; i++) {
			s.put(columnName[i], csvCell[i]);
		}
		Object[] d = new Object[1];
		d[0] = s;
		this.curRowNo++;
		return d;
	}

	public void remove() {
		throw new UnsupportedOperationException("remove unsupported");
	}
}
