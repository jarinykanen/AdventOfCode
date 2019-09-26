/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import advent.of.code.Main;
import advent.of.code.commons.CommonMethods;

/**
 * @author Jari Needs a bit of optimization but works for now
 *
 */
public class Day6 {

	private static final Logger logger = Logger.getLogger(Day6.class.getSimpleName());

	private List<String> inputList = Main.getCm().readFile("Day6.txt");
	private List<String> smallIdList = new ArrayList<>();
	private List<String> idList = new ArrayList<>();
	private List<String> finalList = new ArrayList<>();
	private String[][] multi;

	public Day6() {
		generateRandomChar();
		multi = generate2dList();
	}

	public void firstTask() {
		calculateDistance();
		calculateAmount();
	}

	public void secondTask() {
		List<String> coordinatesInRange = calculateDistanceVol2();
		logger.info("Length is: " + coordinatesInRange.size());
	}

	private String[][] generate2dList() {

		String arrayLength = getLimits();
		String[] parseLength = arrayLength.split(", ");
		int xLength = Integer.parseInt(parseLength[0]) + 1;
		int yLength = Integer.parseInt(parseLength[1]) + 1;
		int xMinLength = Integer.parseInt(parseLength[2]) - 1;
		int yMinLength = Integer.parseInt(parseLength[3]) - 1;

		String[][] temp = new String[xLength][yLength];

		String x = null;
		String y = null;
		CommonMethods methods = new CommonMethods();
		int counter = 0;
		for (String input : inputList) {

			String[] parse = input.split(", ");
			x = parse[0];
			y = parse[1];
			for (int i = xMinLength; i < xLength; ++i) {

				for (int j = yMinLength; j < yLength; j++) {
					if (i == Integer.parseInt(x) && j == Integer.parseInt(y)) {
						temp[i][j] = methods.getValue(idList).split(":")[0];
					}

				}
			}
			finalList.add(idList.get(counter) + input);
			counter++;
		}

		return temp;

	}

	private List<String> calculateDistanceVol2() {

		String[][] copy = multi;

		int x0 = 0;
		int y0 = 0;
		int x1 = 0;
		int y1 = 0;

		Integer totalDistance = 0;
		List<String> rangeCoords = new ArrayList<>();

		for (int i = 0; i < copy.length; ++i) {

			for (int j = 0; j < copy[i].length; j++) {

				for (int u = 0; u < finalList.size(); u++) {
					String[] parse = finalList.get(u).split(", ");
					x0 = Integer.parseInt(parse[0].split(":")[1]);
					y0 = Integer.parseInt(parse[1]);

					x1 = i;
					y1 = j;

					int distance = Math.abs(x1 - x0) + Math.abs(y1 - y0);

					totalDistance += distance;

				}

				if (totalDistance < 10000) {
					rangeCoords.add(Integer.toString(i) + ", " + Integer.toString(j));
				}

				totalDistance = 0;

			}
		}

		return rangeCoords;

	}

	/**
	 * Create random ID's for the areas that the input has
	 */
	private void generateRandomChar() {

		Random r = new Random();
		while (smallIdList.size() < inputList.size()) {
			String id = returnRandom(r);

			if (!smallIdList.contains(id)) {
				smallIdList.add(id);

				id = id.toUpperCase();
				idList.add(id + ":");
			}

		}

		Collections.sort(smallIdList);

	}

	private String returnRandom(Random r) {
		char c = (char) (r.nextInt(26) + 'a');
		char b = (char) (r.nextInt(26) + 'a');
		return Character.toString(c) + Character.toString(b);
	}

	private String getLimits() {

		int biggestX = 0;
		int biggestY = 0;
		Integer lowestX = null;
		Integer lowestY = null;

		for (String input : inputList) {

			String[] parse = input.split(", ");
			if (Integer.parseInt(parse[0]) > biggestX) {
				biggestX = Integer.parseInt(parse[0]);
			}

			if (Integer.parseInt(parse[1]) > biggestY) {
				biggestY = Integer.parseInt(parse[1]);
			}

			if (lowestX == null || Integer.parseInt(parse[0]) < lowestX) {
				lowestX = Integer.parseInt(parse[0]);
			}

			if (lowestY == null || Integer.parseInt(parse[1]) < lowestY) {
				lowestY = Integer.parseInt(parse[1]);
			}
		}

		return Integer.toString(biggestX) + ", " + Integer.toString(biggestY) + ", " + Integer.toString(lowestX) + ", "
				+ Integer.toString(lowestY);

	}

	private void calculateDistance() {

		String[][] copy = multi;

		int x0 = 0;
		int y0 = 0;
		int x1 = 0;
		int y1 = 0;

		Integer shortestDist = null;
		String id = null;

		for (int i = 0; i < copy.length; ++i) {

			for (int j = 0; j < copy[i].length; j++) {
				if (copy[i][j] != null) {
				} else {
					for (int u = 0; u < finalList.size(); u++) {
						String[] parse = finalList.get(u).split(", ");
						x0 = Integer.parseInt(parse[0].split(":")[1]);
						y0 = Integer.parseInt(parse[1]);

						x1 = i;
						y1 = j;

						int distance = Math.abs(x1 - x0) + Math.abs(y1 - y0);

						if (shortestDist == null || distance < shortestDist) {
							shortestDist = distance;
							id = parse[0].split(":")[0].toLowerCase();
						} else if (distance == shortestDist) {
							id = ".";

						}

					}

					copy[i][j] = id;
					shortestDist = null;
				}

			}
		}

	}

	private void calculateAmount() {

		String[][] copy = multi;
		Integer highestAmount = null;
		String id = null;
		List<String> ignore = new ArrayList<>();

		for (String smallId : smallIdList) {

			int amount = 0;
			for (int i = 0; i < copy.length; ++i) {
				for (int j = 0; j < copy[i].length; ++j) {
					if ((i == 0 || j == 0) || (i == copy.length - 1 || j == copy[i].length - 1)) {
						if (!ignore.contains(smallId) && copy[i][j].equalsIgnoreCase(smallId)) {
							ignore.add(smallId);
						}
					}
					if (copy[i][j].equalsIgnoreCase(smallId) && !ignore.contains(copy[i][j])) {
						amount++;
					}
				}

			}

			if (highestAmount == null || amount > highestAmount) {
				highestAmount = amount;
				id = smallId;

			}
		}

		logger.info("ID: " + id + ", highest amount: " + highestAmount);
	}

}
