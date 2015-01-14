package testvaadin.aep.com.storm;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aepardeau on 14/01/2015.
 */
public class FileIncrementalReader {

	public static List<String> readToString(FileObject listenedFile, LastReadLine lastLineRead){

		final List<String> result = new ArrayList<String>();
		final FileContent fileContent;
		try {
			fileContent = listenedFile.getContent();
			final InputStream fis = fileContent.getInputStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String line;
			final Integer lastLineReadValue = lastLineRead.getValue();
			int cptrLine = 0;
			while ((line = reader.readLine()) != null) {
				cptrLine++;
				if(cptrLine > lastLineReadValue){
					lastLineRead.increment();
					result.add(line);
					System.out.println("content is : " + line);
				}
			}
			reader.close();
		}
		catch (FileSystemException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
