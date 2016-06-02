package text.test;

import java.io.File;
import java.io.FileNotFoundException;

import text.core.FarmLabeledWords;

public interface FarmDataSetReader {
	public FarmLabeledWords read(File input) throws FileNotFoundException;
}
