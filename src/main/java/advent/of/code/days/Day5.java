/**
 *
 */
package advent.of.code.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day5 {

	private String input = null;
	private static final Logger logger = Logger.getLogger(Day5.class.getSimpleName());
	String finalValue = null;
	Integer lowestValue = null;
	List<Character> listOfChars = new ArrayList<>();

	public Day5() {
		this.input = Main.getCm().readFile("day5.txt").get(0);
	}

	private char[] splitMethod(int i) {

		String newValue = input;
		String front = newValue.substring(0, i);
		String back = newValue.substring(i + 2);
		String valueAfterSplit = front + back;
		finalValue = valueAfterSplit;
		input = valueAfterSplit;

		return valueAfterSplit.toCharArray();
	}

	private String splitMethod2(int i, String tempInput) {

		String newValue = tempInput;
		String front = newValue.substring(0, i);
		String back = newValue.substring(i + 2);
		String valueAfterSplit = front + back;
		return valueAfterSplit;
	}

	public void firstTask() {

		char[] characters = input.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			try {
				String currentValue = Character.toString(characters[i]);
				String nextValue = Character.toString(characters[i + 1]);
				boolean isLower = Character.isLowerCase(characters[i]);
				boolean isNextLower = Character.isLowerCase(characters[i + 1]);

				if ((isLower && !isNextLower) || !isLower && isNextLower) {

					if (nextValue.equalsIgnoreCase((currentValue))) {
						characters = splitMethod(i);
						i = 0;
					}

				}
			} catch (Exception e) {
				logger.info(e.getLocalizedMessage());
			}
		}
		logger.info("ANSWER: " + finalValue.length());
	}

	public void secondTask() {
		String secondInput = null;
		try {
			for (String line : Files.readAllLines(Paths.get("C:\\Jari\\test3.txt"))) {
				secondInput = line;
			}
		} catch (IOException e1) {
			logger.info(e1.getLocalizedMessage());
		}

		char[] characters = secondInput.toCharArray();
		generateAllCharacters(characters);
		Collections.sort(listOfChars);

		final String originalInput = secondInput;

		for (char character : listOfChars) {
			String tempInput = originalInput;
			String removeThis = Character.toString(character).toUpperCase();
			String removeThisToo = Character.toString(character).toLowerCase();
			tempInput = tempInput.replaceAll(removeThis, "");
			tempInput = tempInput.replaceAll(removeThisToo, "");

			characters = tempInput.toCharArray();

			for (int i = 0; i < characters.length; i++) {
				try {
					String currentValue = Character.toString(characters[i]);
					String nextValue = Character.toString(characters[i + 1]);
					boolean isLower = Character.isLowerCase(characters[i]);
					boolean isNextLower = Character.isLowerCase(characters[i + 1]);

					if ((isLower && !isNextLower) || !isLower && isNextLower) {

						if (nextValue.equalsIgnoreCase((currentValue))) {
							tempInput = splitMethod2(i, tempInput);
							characters = tempInput.toCharArray();
							i = 0;
						}

					}
				} catch (Exception e) {
					logger.info(e.getLocalizedMessage());
				}

				finalValue = tempInput;
			}

			if (lowestValue == null) {
				lowestValue = finalValue.length();
			} else if (finalValue.length() < lowestValue) {
				lowestValue = finalValue.length();
			}
		}

		logger.info("ANSWER: " + lowestValue);
	}

	private void generateAllCharacters(char[] characters) {

		for (char character : characters) {
			try {
				if (!listOfChars.contains(Character.toLowerCase(character))
						&& !listOfChars.contains(Character.toUpperCase(character))) {
					listOfChars.add(character);
				}

			} catch (Exception e) {
				logger.info(e.getLocalizedMessage());
			}
		}

	}
}
