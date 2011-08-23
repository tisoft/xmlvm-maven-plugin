package de.tisoft.xmlvm;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.xmlvm.Main;

import java.io.File;
import java.util.Arrays;

/**
 * Goal which generates xmlvm sources
 * 
 * @goal generate-xmlvm
 * 
 * @phase prepare-package
 */
public class GenerateXMLVMMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}/${project.artifactId}"
	 * @required
	 */
	private File out;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}/classes"
	 * @required
	 */
	private File in;

	/**
	 * Location of the file.
	 * 
	 * @parameter
	 * @required
	 */
	private String target;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="warning"
	 * @required
	 */
	private String debug;

	
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.name}"
	 * @required
	 */
	private String app_name;

	public void execute() throws MojoExecutionException {
		try {
			System.setProperty("one-jar.jar.path", System.getProperty("xmlvm.sdk.path") + "/dist/xmlvm.jar");
			String[] args = new String[] { 
					"--in=" + in.getAbsolutePath(), 
					"--out=" + out.getAbsolutePath(),
					"--target=" + target, 
					"--app-name=" + app_name, 
					"--debug=" + debug };
			getLog().info("Running XMLVM with command line: " + Arrays.asList(args));
			Main.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("XMLVM failed " + System.getProperty("xmlvm.sdk.path"), e);
		}
	}
}
