package org.bvp.pre;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String photosDirectoryPath = "/Users/sdass/Pictures";
		MetadataExtractor.getInstance().createModel(new File (photosDirectoryPath));
		printMap(MetadataExtractor.getInstance().getPhotos());
	}
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + getGeoLocation((Metadata) pairs.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	private static String getGeoLocation(Metadata value) {
		// TODO Auto-generated method stub
		JpegDirectory directory = (JpegDirectory) value.getDirectory(JpegDirectory.class);
		String date = null;
		try {
		    JpegDescriptor descriptor = new JpegDescriptor(directory);
		    String program = descriptor.getComponentDataDescription(1);
		 date = directory.getDescription(ExifDirectory.TAG_GPS_INFO);
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date.toString();
	}
}
