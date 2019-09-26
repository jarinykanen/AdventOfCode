/**
 *
 */
package advent.of.code.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class CommonMethods {

	private static CommonMethods instance = null;

	public static CommonMethods getInstance() {
		if (instance == null) {
			instance = new CommonMethods();
		}

		return instance;
	}

	private int counter = 0;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getValue(List<String> values) {
		String value = values.get(counter);
		counter++;
		return value;
	}

	public String getValueLoop(List<String> values) {
		String value = values.get(counter);
		counter = (counter + 1) % values.size();
		return value;
	}

	public List<String> readFile(String fileName) {

		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		List<String> list = new ArrayList<>();
		try (FileInputStream fstream = new FileInputStream(file)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String line;

			// Read File Line By Line
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (Exception e) {
			Main.getLogger().severe("Error reading file: " + e.getMessage());
		}

		return list;

	}

}
