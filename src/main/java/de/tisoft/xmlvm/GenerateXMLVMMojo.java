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
import java.util.*;

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
	 * @parameter expression="${project.build.directory}/classes"
	 * @required
	 */
	private File in;

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
	 * @parameter
	 * @required
	 */
	private String target;

	/**
	 * Location of the file.
	 * 
	 * @parameter
	 * @optional
	 */
	private List<String> lib;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.name}"
	 * @required
	 */
	private String app_name;

	/**
	 * Location of the file.
	 * 
	 * @parameter
	 */
	private List<String> resources;

	/**
	 * Template to use for Xcode project: iphone iPhone project skeleton", ipad
	 * iPad project skeleton", ios iPhone and iPad project skeleton", iphone3
	 * Legacy iPhone 3.1 project skeleton",
	 * 
	 * @parameter expression="iphone"
	 */
	private String xcodeProject;

	/**
	 * The value of CFBundleIdentifier in Info.plist
	 * 
	 * @parameter expression="${project.groupId}.${project.artifactId}"
	 */
	private String bundleIdentifier;

	/**
	 * The value of CFBundleVersion in Info.plist
	 * 
	 * @parameter expression="${project.version}"
	 */
	private String bundleVersion;

	/**
	 * The value of CFBundleDisplayName in Info.plist
	 * 
	 * @parameter
	 */
	private String bundleDisplayName;

	/**
	 * The iPhone application icon is already pre-rendered
	 * 
	 * @parameter
	 */
	private boolean prerenderedIcon;

	/**
	 * Hide (value is 'true') or display (value is 'false') status bar
	 * 
	 * @parameter
	 */
	private boolean statusBarHidden;

	/**
	 * Application does not run in background on suspend
	 * 
	 * @parameter
	 */
	private boolean applicationExits = true;

	/**
	 * Initial interface orientation. Should be one of:
	 * UIInterfaceOrientationPortrait UIInterfaceOrientationPortraitUpsideDown
	 * UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight
	 * 
	 * @parameter
	 */
	private String interfaceOrientation;

	/**
	 * Colon seperated list of supported interface orientations. See property
	 * InterfaceOrientation
	 * 
	 * @parameter
	 */
	private List<String> supportedInterfaceOrientations;

    /**
     * Is iTunes file sharing enabled?
     *
     * @parameter
     */
    private boolean fileSharingEnabled;


	/**
	 * Colon separated list of custom fonts
	 * 
	 * @parameter
	 */
	private List<String> appFonts;

	/**
	 * Location of the file.
	 * 
	 * @parameter
	 * @optional
	 */
	private Map<String, String> xcodeProperties;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="warning"
	 * @required
	 */
	private String debug;

	public void execute() throws MojoExecutionException {
		try {
			List<String> args = new LinkedList<String>();
			args.add("--in=" + in.getAbsolutePath());
			args.add("--out=" + out.getAbsolutePath());
			args.add("--target=" + target);
			if (lib != null && !lib.isEmpty()) {
				args.add("--lib=" + concat(lib, ","));
			}
			args.add("--app-name=" + app_name);
			if (resources != null && !resources.isEmpty()) {
				args.add("--resource=" + concat(resources, File.pathSeparator));
			}

			args.add("-DXcodeProject=" + xcodeProject);
			args.add("-DBundleIdentifier=" + bundleIdentifier);
			args.add("-DBundleVersion=" + bundleVersion);
			if (bundleDisplayName != null) {
				args.add("-DBundleDisplayName=" + bundleDisplayName);
			}
			args.add("-DPrerenderedIcon=" + prerenderedIcon);
			args.add("-DStatusBarHidden=" + statusBarHidden);
			args.add("-DApplicationExits=" + applicationExits);
            args.add("-DFileSharingEnabled=" + fileSharingEnabled);
			if (interfaceOrientation != null) {
				args.add("-DInterfaceOrientation=" + interfaceOrientation);
			}
			if (supportedInterfaceOrientations != null && !supportedInterfaceOrientations.isEmpty()) {
				args.add("-DSupportedInterfaceOrientations=" + concat(supportedInterfaceOrientations, ","));
			}
			if (appFonts != null && !appFonts.isEmpty()) {
				args.add("-DAppFonts=" + concat(appFonts, ","));
			}

			args.add("--debug=" + debug);
			getLog().info("Running XMLVM with command line: " + args);
			Main.main(args.toArray(new String[args.size()]));
		} catch (Exception e) {
			throw new MojoExecutionException("XMLVM failed " + System.getProperty("xmlvm.sdk.path"), e);
		}
	}

	private String concat(Collection<String> collection, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}
}
