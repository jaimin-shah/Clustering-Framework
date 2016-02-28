/*
 * custom file filter for showing only csv, arff, xrff files
 */
package gui;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileFilter;

public class ClusteringFileSelectionFilter extends FileFilter {
	
	//check file extension
	Pattern pattern = Pattern.compile(".+\\.(csv|arff|xrff)");
	Matcher match;

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()) {
			return true;
		}
		match = pattern.matcher(f.getName());
		return match.matches();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "ARFF, CSV and XRFF files";
	}

}
