/**
 *
 */
package advent.of.code;

import java.util.logging.Logger;

import advent.of.code.commons.CommonMethods;
import advent.of.code.days.Day1;
import advent.of.code.days.Day2;
import advent.of.code.days.Day3;
import advent.of.code.days.Day4;
import advent.of.code.days.Day5;
import advent.of.code.days.Day6;
import advent.of.code.days.Day7;

/**
 * @author Jari
 *
 */
public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

	private static Day1 day1 = new Day1();
	private static Day2 day2 = new Day2();
	private static Day3 day3 = new Day3();
	private static Day4 day4 = new Day4();
	private static Day5 day5 = new Day5();
	private static Day6 day6 = new Day6();
	private static Day7 day7 = new Day7();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		day1.firstTask();
//		day1.secondTask();
//		day2.firstTask();
//		day2.secondTask();
//		day3.firstTask();
//		day3.secondTask();
//		day4.firstTask();
//		day4.secondTask();
//		day5.firstTask();
//		day5.secondTask();
//		day6.firstTask();
//		day6.secondTask();
		day7.firstTask();
		day7.secondTask();

	}

	public static CommonMethods getCm() {
		return CommonMethods.getInstance();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
