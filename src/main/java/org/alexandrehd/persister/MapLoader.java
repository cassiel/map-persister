package org.alexandrehd.persister;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**	An interface for reading an entire map from a location, which is either
	a flat file (we add the extension) or a directory. For hysterical reasons,
	We instantiate a MapLoader on each root location.
	
	@author Nick Rothwell, nick@cassiel.com
*/

class MapLoader extends MapIO {
	public MapLoader(File root) {
		super(root);
	}
	
	private Object loadNodeFromRoot() throws ClassNotFoundException, IOException {
		if (getFlatFile().exists()) {
			return loadObjectFromRoot();
		} else if (getRootPath().exists()) {
			File root = getRootPath();
			File[] contents = root.listFiles();
			if (contents == null) {
				throw new IOException("cannot read directory at: " + root);
			} else {
				HashMap<String, Object> result = new HashMap<String, Object>();
				
				for (File f: contents) {
					//	This is a bit scrappy: we might hit both FOO and FOO.ser, in which case
					//	we'll process FOO twice and get FOO.ser each time (as preference).
					//	We don't care that much since it should never happen in a well-formed
					//	saved directory.
					String n = f.getName();
					n = n.replaceAll("\\.ser$", "");
					//	TODO: sanitise names!
					result.put(n, new MapLoader(new File(f.getParentFile(), n)).loadNodeFromRoot());
				}
				
				return result;
			}
		} else {
			throw new FileNotFoundException("cannot find node at " + getRootPath());
		}
	}

	@SuppressWarnings("unchecked")
	//@Override
	HashMap<String, ?> loadFromRoot() throws ClassNotFoundException, IOException {
		return (HashMap<String, ?>) loadNodeFromRoot();
	}

	/*package*/ Object loadObjectFromRoot() throws IOException, ClassNotFoundException {
		ObjectInputStream stream = null;
		try {
			InputStream in = new java.io.FileInputStream(getFlatFile());
			stream = new ObjectInputStream(in);
			return stream.readObject();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}
