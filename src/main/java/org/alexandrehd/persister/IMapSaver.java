package org.alexandrehd.persister;

import java.io.File;
import java.util.HashMap;

/**	An interface for writing a map to a location, either as a flat file
    (with extension added) or nested structure, according to a depth.
    Completely removes any existing file and/or structure at this location.
    
 	@author Nick Rothwell, nick@cassiel.com
 */

public interface IMapSaver {
	void saveToLocation(HashMap<String, ?> item, File location, int depth);
}
