/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day8 {

	private List<Integer> input = new ArrayList<>();
	private int index = 0;
	private List<Integer> meta = new ArrayList<>();

	public Day8() {
		String raw = Main.getCm().readFile("day8.txt").get(0);
		List<String> stringList = Arrays.asList(raw.split(" "));
		parseToInt(stringList);
	}

	/**
	 * Parse input to Integer
	 *
	 * @param stringList
	 */
	private void parseToInt(List<String> stringList) {
		stringList.stream().forEach(string -> input.add(Integer.parseInt(string)));
	}

	/**
	 * Both task can and should be calculated at the same time.
	 */
	public void bothTasks() {
		Integer part2 = handle();
		int finalValue = calculateSum(meta);
		Main.getLogger().info("Sum of all metadata entries is: " + finalValue);
		Main.getLogger().info("Value of the root node is: " + part2);

	}

	/**
	 * Handle the reading recursively
	 *
	 * @return
	 */
	private Integer handle() {

		int nodeCount = getNexInt();
		int metaCount = getNexInt();
		List<Integer> tempMetaValue = new ArrayList<>();
		List<Integer> tempNodeValue = new ArrayList<>();
		Integer metaValue = 0;

		/**
		 * Loop this function as mane times as there are nodes.
		 */
		IntStream.range(0, nodeCount).forEachOrdered(n -> {
			tempNodeValue.add(handle());
		});

		IntStream.range(0, metaCount).forEachOrdered(n -> {
			Integer value = getNexInt();
			meta.add(value);
			tempMetaValue.add(value);
		});

		return calculateMetaValue(nodeCount, tempMetaValue, tempNodeValue, metaValue);
	}

	/**
	 * Do calculation based on the instructions
	 * 
	 * @param nodeCount
	 * @param tempMetaValue
	 * @param tempNodeValue
	 * @param metaValue
	 * @return
	 */
	private Integer calculateMetaValue(int nodeCount, List<Integer> tempMetaValue, List<Integer> tempNodeValue,
			Integer metaValue) {
		if (nodeCount == 0) {
			metaValue = calculateSum(tempMetaValue);
		} else {
			for (Integer value : tempMetaValue) {
				if (value >= 1 && value <= tempNodeValue.size()) {
					metaValue += tempNodeValue.get(value - 1);
				}
			}
		}
		return metaValue;
	}

	/**
	 * Calculate sum of given list
	 *
	 * @param tempMetaValue
	 * @return
	 */
	private Integer calculateSum(List<Integer> tempMetaValue) {
		Integer finalValue = 0;
		for (Integer value : tempMetaValue) {
			finalValue += value;
		}
		return finalValue;
	}

	/**
	 * Return the next value from input list
	 *
	 * @return
	 */
	private int getNexInt() {
		index++;
		return input.get(index - 1);
	}

}
