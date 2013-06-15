package org.bvp.pre;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import javax.swing.filechooser.FileFilter;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;

public class MetadataExtractor {
/**
 * static Singleton instance 
 */
private static MetadataExtractor instance;
HashMap<String,Metadata> Photos = new HashMap<String,Metadata>();

/**
 * Private constructor for singleton
 */
private MetadataExtractor() {

}

/**
 * Static getter method for retrieving the singleton instance
 */
public static MetadataExtractor getInstance() {
	if (instance == null) {
		instance = new MetadataExtractor();
	}

	return instance;
}

	public void createModel(File photosDirectoryPath) {
		String[] types = {
	            "JPG",
	            "tiff",
	            "gif",
	            "bmp",
	            "png",
	            "psd"
	            
	        };
		GenericExtFilter filter = new GenericExtFilter(types[0]);
		for (final File fileEntry : photosDirectoryPath.listFiles(filter)) {
			if (fileEntry.isDirectory()) {
				createModel(fileEntry);
			} else {
				Metadata metadata = null;
				try {
					metadata = ImageMetadataReader.readMetadata(fileEntry);
						} catch (Exception e) {
					e.printStackTrace(System.err);
					System.exit(1);
				}
				Photos.put(fileEntry.getName(), metadata);
			}
		}
	}

public HashMap<String, Metadata> getPhotos() {
	return Photos;
}

public void setPhotos(HashMap<String, Metadata> photos) {
	Photos = photos;
}
public class GenericExtFilter implements FilenameFilter {
	 
	private String ext;

	public GenericExtFilter(String ext) {
		this.ext = ext;
	}

	public boolean accept(File dir, String name) {
		return (name.endsWith(ext));
	}
}
}
