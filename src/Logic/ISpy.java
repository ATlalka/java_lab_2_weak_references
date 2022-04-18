package Logic;

import java.io.File;

public interface ISpy {
	public String[] findAllDirectories(String path);

	public File getData(String path, String fileName);

	public File getImage(String path, String fileName);
}
