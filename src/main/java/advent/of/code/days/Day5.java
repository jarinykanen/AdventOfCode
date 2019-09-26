/**
 *
 */
package advent.of.code.days;

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
	private String stringValue;
	private int finalValue;

	List<Character> listOfChars = new ArrayList<>();

	public Day5() {
		this.input = Main.getCm().readFile("day5.txt").get(0);
	}

	public void firstTask() {

		char[] characters = input.toCharArray();
		runLoop(characters);
		logger.info(stringValue.length() + " units remain after fully reacting the polymer.");
	}

	public void secondTask() {
		char[] characters = input.toCharArray();
		generateAllCharacters(characters);

		final String originalInput = input;

		handle(originalInput);

		logger.info("ANSWER: " + finalValue);
	}

	private void handle(final String originalInput) {

		listOfChars.parallelStream().forEach(c -> {
			char[] characters;

			String tempInput = originalInput;
			String removeThis = Character.toString(c).toUpperCase();
			String removeThisToo = Character.toString(c).toLowerCase();
			tempInput = tempInput.replaceAll(removeThis, "");
			tempInput = tempInput.replaceAll(removeThisToo, "");

			characters = tempInput.toCharArray();

			int value = runLoop2(characters, tempInput);
			if ((value != 0 && value < finalValue) || finalValue == 0) {
				finalValue = value;
			}
		});
	}

	private int runLoop2(char[] characters, String tempInput) {
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
				logger.info("This was the final value from this stream: " + tempInput.length());
			}

		}
		return tempInput.length();
	}

	/**
	 * Run loop as long as there are same characters with opposite "polarities" next
	 * to each other. In this case it means if the current character and the next
	 * character are the same but one is lower and other is upper case character. If
	 * an opposite polarity is found between character both will be removed
	 *
	 * @param characters
	 */
	private void runLoop(char[] characters) {
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
				logger.info("We run out of operations!");
			}
		}
	}

	private char[] splitMethod(int i) {

		String newValue = input;
		String front = newValue.substring(0, i);
		String back = newValue.substring(i + 2);
		String valueAfterSplit = front + back;
		stringValue = valueAfterSplit;
		input = valueAfterSplit;

		return valueAfterSplit.toCharArray();
	}

	private String splitMethod2(int i, String tempInput) {

		String newValue = tempInput;
		String front = newValue.substring(0, i);
		String back = newValue.substring(i + 2);
		return front + back;
	}

	/**
	 * Generate a list of characters that the input has
	 *
	 * @param characters
	 */
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

		Collections.sort(listOfChars);

	}
}
