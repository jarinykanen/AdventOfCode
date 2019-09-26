/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day1 {

	private static final Logger logger = Logger.getLogger(Day1.class.getSimpleName());
	private int counter = 0;
	private List<String> list = new ArrayList<>();

	public void firstTask() {

		list = Main.getCm().readFile("day1.txt");
		Integer sum = list.stream().mapToInt(a -> Integer.parseInt(a)).sum();
		logger.log(Level.INFO, "{0} ", "FINAL VALUE IS: " + sum);

	}

	/**
	 * VERY unoptimized and slow way of finding the first duplicate.
	 */
	public void secondTask() {

		int freq = 0;
		boolean found = false;
		List<Integer> copyList = new ArrayList<>();
		while (!found) {
			String value = getValue();
			freq += Integer.parseInt(value);

			if (!copyList.contains(freq)) {
				copyList.add(freq);
			} else {
				found = true;
			}
		}
		logger.severe("FIRST DUPLICATE IS : " + freq);

	}

	private String getValue() {
		String value = list.get(counter);
		counter = (counter + 1) % list.size();
		return value;
	}

}
