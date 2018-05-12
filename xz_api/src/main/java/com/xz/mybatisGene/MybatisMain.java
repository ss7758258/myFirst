package com.xz.mybatisGene;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MybatisMain {

	public static void main(String[] args) {
		 try {
			List<String> warnings = new ArrayList<String>();
			String configFileName = MybatisMain.class.getClassLoader().getResource("generatorConfig.xml").getFile();
			File configFile = new File(configFileName);
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			 System.out.println("success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
