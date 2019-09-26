/**
 *
 */
package advent.of.code.days;

import java.util.List;
import java.util.logging.Logger;

import advent.of.code.Main;
import advent.of.code.commons.CommonMethods;

/**
 * @author Jari
 *
 */
public class Day2 {

	private static final Logger logger = Logger.getLogger(Day2.class.getSimpleName());

	private List<String> list = Main.getCm().readFile("day2.txt");

	public void firstTask() {

		CommonMethods methods = new CommonMethods();
		int doubleChars = 0;
		int tripleChars = 0;

		while (methods.getCounter() != list.size()) {
			String value = methods.getValue(list);
			char[] copy = value.toCharArray();

			boolean oneDoubleFound = false;
			boolean oneTripleFound = false;

			/**
			 * Loop each string through character by character
			 */
			for (char c : copy) {

				int lengthBefore = 0;
				int lengthAfter = 0;

				String character = Character.toString(c);

				lengthBefore = copy.length;
				value = value.replaceAll(character, "");

				copy = value.toCharArray();
				lengthAfter = copy.length;

				int removedAmount = lengthBefore - lengthAfter;
				if (removedAmount == 2 && !oneDoubleFound) {
					doubleChars++;
					oneDoubleFound = true;
				} else if (removedAmount == 3 && !oneTripleFound) {
					tripleChars++;
					oneTripleFound = true;
				} else {
				}

			}
		}

		int checksum = doubleChars * tripleChars;

		logger.severe("CHECKSUM IS: " + checksum);

	}

	public void secondTask() {

		for (int i = 0; i < list.size(); i++) {

			String initValue = list.get(i);

			for (int j = 1; j < list.size(); j++) {
				if (i + j < list.size()) {
					String compareTo = list.get(i + j);

					for (int a = 0; a < initValue.length(); a++) {
						String firstValue = initValue.substring(0, a) + initValue.substring(a + 1);
						String secondValue = compareTo.substring(0, a) + compareTo.substring(a + 1);

						if (firstValue.equals(secondValue)) {
							logger.severe("FOUND IT: " + firstValue + " equals " + secondValue);
							break;
						}

					}
				}

			}

		}
	}

}
