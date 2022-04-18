package Logic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.WeakHashMap;

public class Spy implements ISpy {

	private Map<String, File> map = new WeakHashMap<>();

	public File findDataFile(String path) {
		String dataPath = path + "\\record.txt";
		if (map.get(dataPath) == null) { // if file is not stored in the map, it's added to it
			map.put(dataPath, getData(path, "record.txt"));
			return null; // because it wasn't in the memory, returning value is null
		}

		return map.get(dataPath);
	}

	public File findImageFile(String path) {
		String imagePath = path + "\\image.png";
		if (map.get(imagePath) == null) { // if file is not stored in the map, it's added to it
			map.put(imagePath, getImage(path, "image.png"));
			return null; // because it wasn't in the memory, returning value is null
		}
		return map.get(imagePath);

	}

	@Override
	public String[] findAllDirectories(String path) {
		File file = new File(path);
		String[] directories = file.list((FilenameFilter) new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return directories;
	}

	@Override
	public File getData(String path, String fileName) {
		File f = new File(path + "\\" + fileName);
		return f;

	}

	@Override
	public File getImage(String path, String fileName) {
		File f = new File(path + "\\" + fileName);
		return f;
	}

}
