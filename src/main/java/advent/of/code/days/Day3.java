/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import advent.of.code.Main;
import advent.of.code.commons.CommonMethods;

/**
 * @author Jari
 *
 */
public class Day3 {

	private List<String> input = Main.getCm().readFile("day3.txt");
	private static final Logger logger = Logger.getLogger(Day3.class.getSimpleName());
	private Map<String, List<String>> map = new HashMap<>();

	/**
	 * Needs optimization
	 */
	public void firstTask() {
		CommonMethods methods = new CommonMethods();
		List<String> listOfClaims = new ArrayList<>();
		while (methods.getCounter() != input.size()) {

			String raw = methods.getValue(input);

			String[] s1 = raw.split(" @ ");

			String raw2 = s1[1];
			String[] s2 = raw2.split(": ");
			String coordinates = s2[0];
			String area = s2[1];

			String[] coords = coordinates.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			String[] areaSplit = area.split("x");
			int width = Integer.parseInt(areaSplit[0]);
			int height = Integer.parseInt(areaSplit[1]);

			addArea(listOfClaims, x, y, width, height);

		}

		List<String> newList = removeDuplicates(listOfClaims);
		int sizeAfter = newList.size();

		logger.severe(sizeAfter + " square inches of fabric are within two or more claims");

	}

	/**
	 * Adds the area of the claim
	 *
	 * @param listOfClaims : List of all claims
	 * @param x            : X coordinate
	 * @param y            : Y coordinate
	 * @param width        : Width of the claim
	 * @param height       : Height of the claim
	 *
	 *                     String value : coordinate of one dot (claim)
	 */
	private void addArea(List<String> listOfClaims, int x, int y, int width, int height) {
		String value;

		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				value = Integer.toString(i) + "," + Integer.toString(j);
				listOfClaims.add(value);

			}
		}
	}

	public void secondTask() {
		CommonMethods methods = new CommonMethods();
		List<String> listOfClaims = new ArrayList<>();
		while (methods.getCounter() != input.size()) {

			String raw = methods.getValue(input);

			String[] s1 = raw.split(" @ ");
			String id = s1[0].substring(1, s1[0].length());

			String raw2 = s1[1];
			String[] s2 = raw2.split(": ");
			String coordinates = s2[0];
			String area = s2[1];

			String[] coords = coordinates.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			String[] areaSplit = area.split("x");
			int width = Integer.parseInt(areaSplit[0]);
			int height = Integer.parseInt(areaSplit[1]);

			addToList(listOfClaims, id, x, y, width, height);

		}

		getId(listOfClaims);
	}

	/**
	 *
	 * *
	 * 
	 * 
	 * @param listOfClaims : List of all claims
	 * @param id           : ID of the claim
	 * @param x            : X coordinate
	 * @param y            : Y coordinate
	 * @param width        : Width of the claim
	 * @param height       : Height of the claim
	 *
	 *                     String value : coordinate of one dot (claim)
	 */
	private void addToList(List<String> listOfClaims, String id, int x, int y, int width, int height) {
		String value;
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				value = Integer.toString(i) + "," + Integer.toString(j);
				listOfClaims.add(id + " " + value);
			}
		}
	}

	private void getId(List<String> list) {
		List<String> result = new ArrayList<>();
		List<String> newList = new ArrayList<>();

		for (String item : list) {
			item = item.split(" ")[1].trim();
			newList.add(item);
		}

		Map<String, Long> counts = newList.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

		createMap(list, result, counts);

		foundId();
	}

	/**
	 * Creates a map containing: Id and result.
	 *
	 * @param list
	 * @param result : Format is: coordinate and count
	 * @param counts
	 */
	private void createMap(List<String> list, List<String> result, Map<String, Long> counts) {
		String id = null;
		String value = null;

		for (String item : list) {
			if (id == null) {
				id = item.split(" ")[0].trim();
			}
			if (!id.equals(item.split(" ")[0].trim())) {
				addToMap(id, result);
				id = item.split(" ")[0].trim();
				result = new ArrayList<>();
			}
			Long howMany = counts.get(item.split(" ")[1].trim());
			value = item.split(" ")[1].trim();

			result.add(value + ":" + howMany);

		}
		map.put(id, result);
	}

	/**
	 * Check each entry from the map and loop the values within each entry. If int
	 * found and entry size are the same we have found the one area that doesn't
	 * overlap with any other area.
	 */
	private void foundId() {
		for (Entry<String, List<String>> entry : map.entrySet()) {
			int found = 0;
			for (String item : entry.getValue()) {

				String amount = null;
				amount = item.split(":")[1].trim();
				if (amount.equals("1")) {
					found++;
				}
			}

			if (found == entry.getValue().size()) {
				logger.severe("The ID of the only claim that doesn't overlap is: " + entry.getKey());
				break;
			}
		}
	}

	private void addToMap(String id, List<String> result) {
		map.put(id, result);

	}

	/**
	 * Counts the amount of each coordinate point
	 *
	 * @param list : List of coordinates
	 * @return
	 */
	private List<String> removeDuplicates(List<String> list) {

		List<String> result = new ArrayList<>();
		HashSet<String> set = new HashSet<>();
		Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

		for (String item : list) {
			if (!set.contains(item) && counts.get(item) > 1) {
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}

}
