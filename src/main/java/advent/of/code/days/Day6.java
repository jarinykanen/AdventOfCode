/**
 * 
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import advent.of.code.commons.CommonMethods;

/**
 * @author Jari
 *
 */
public class Day6 {

    private static final Logger logger = Logger.getLogger(Day6.class.getSimpleName());

    private static final String INPUT = "1, 1\r\n" + "1, 6\r\n" + "8, 3\r\n" + "3, 4\r\n" + "5, 5\r\n" + "8, 9";
    private static final String REAL_INPUT = "46, 246\r\n" + "349, 99\r\n" + "245, 65\r\n" + "241, 253\r\n"
	    + "127, 128\r\n" + "295, 69\r\n" + "205, 74\r\n" + "167, 72\r\n" + "103, 186\r\n" + "101, 242\r\n"
	    + "256, 75\r\n" + "122, 359\r\n" + "132, 318\r\n" + "163, 219\r\n" + "87, 309\r\n" + "283, 324\r\n"
	    + "164, 342\r\n" + "255, 174\r\n" + "187, 305\r\n" + "145, 195\r\n" + "69, 266\r\n" + "137, 239\r\n"
	    + "241, 232\r\n" + "97, 319\r\n" + "264, 347\r\n" + "256, 214\r\n" + "217, 47\r\n" + "109, 118\r\n"
	    + "244, 120\r\n" + "132, 310\r\n" + "247, 309\r\n" + "185, 138\r\n" + "215, 323\r\n" + "184, 51\r\n"
	    + "268, 188\r\n" + "54, 226\r\n" + "262, 347\r\n" + "206, 260\r\n" + "213, 175\r\n" + "302, 277\r\n"
	    + "188, 275\r\n" + "352, 143\r\n" + "217, 49\r\n" + "296, 237\r\n" + "349, 339\r\n" + "179, 309\r\n"
	    + "227, 329\r\n" + "226, 346\r\n" + "306, 238\r\n" + "48, 163";

    private static final String ID = "A:,B:,C:,D:,E:,F:";
    private static final String SMALL_ID = "a,b,c,d,e,f";

//    private List<String> inputList = Arrays.asList(INPUT.split("\r\n"));
//    private List<String> idList = Arrays.asList(ID.split(","));
//    private List<String> smallIdList = Arrays.asList(SMALL_ID.split(","));

    private List<String> inputList = Arrays.asList(REAL_INPUT.split("\r\n"));
    private List<String> smallIdList = new ArrayList<>();
    private List<String> idList = new ArrayList<>();
    private List<String> finalList = new ArrayList<>();

    public void firstTask() {

	generateRandomChar();

	String[][] multi = generate2dList();

	calculateDistance(multi);
	calculateAmount(multi);
    }

    private String[][] generate2dList() {
	String arrayLength = getLength();
	String[] parseLength = arrayLength.split(", ");
	int xLength = Integer.parseInt(parseLength[0]) + 1;
	int yLength = Integer.parseInt(parseLength[1]) + 1;
	int xMinLength = Integer.parseInt(parseLength[2]) - 1;
	int yMinLength = Integer.parseInt(parseLength[3]) - 1;

	String[][] multi = new String[xLength][yLength];

	String x = null;
	String y = null;
	CommonMethods methods = new CommonMethods();
	int counter = 0;
	for (String input : inputList) {

	    String[] parse = input.split(", ");
	    x = parse[0];
	    y = parse[1];
	    logger.info("x: " + x + " y: " + y);
	    boolean marked = false;

	    for (int i = xMinLength; i < xLength; ++i) {

		for (int j = yMinLength; j < yLength; j++) {
		    if (i == Integer.parseInt(x) && j == Integer.parseInt(y)) {
			multi[i][j] = methods.getValue(idList).split(":")[0];

		    }

		}
	    }
	    finalList.add(idList.get(counter) + input);
	    counter++;
	}

	return multi;

    }

    public void secondTask() {

	System.out.print("Get set...");
	IntStream.range(1, 4).forEach(i -> System.out.print(i + "..."));

	generateRandomChar();

	String[][] multi = generate2dList();
	List<String> coordinatesInRange = calculateDistanceVol2(multi);
	logger.info("Length is: " + coordinatesInRange.size());
    }

    private List<String> calculateDistanceVol2(String[][] multi) {

	int x0 = 0;
	int y0 = 0;
	int x1 = 0;
	int y1 = 0;

	Integer totalDistance = 0;
	String id = null;
	List<String> rangeCoords = new ArrayList<>();

	CommonMethods methods = new CommonMethods();
	for (int i = 0; i < multi.length; ++i) {

	    for (int j = 0; j < multi[i].length; j++) {

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

    private void generateRandomChar() {

	while (smallIdList.size() < inputList.size()) {
	    Random r = new Random();
	    char c = (char) (r.nextInt(26) + 'a');
	    char b = (char) (r.nextInt(26) + 'a');
	    String id = Character.toString(c) + Character.toString(b);

	    if (!smallIdList.contains(id)) {
		smallIdList.add(id);

		id = id.toUpperCase();
		idList.add(id + ":");
	    }

	}

	Collections.sort(smallIdList);

    }

    private String getLength() {

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

	logger.info("asd");

	return Integer.toString(biggestX) + ", " + Integer.toString(biggestY) + ", " + Integer.toString(lowestX) + ", "
		+ Integer.toString(lowestY);

    }

    private void calculateDistance(String[][] multi) {

	int x0 = 0;
	int y0 = 0;
	int x1 = 0;
	int y1 = 0;

	Integer shortestDist = null;
	String id = null;

	CommonMethods methods = new CommonMethods();
	for (int i = 0; i < multi.length; ++i) {

	    for (int j = 0; j < multi[i].length; j++) {
		if (multi[i][j] != null) {
//		    logger.info(multi[i][j] + " found from position: " + i + "," + j);
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
//		    logger.info("Coordinates were: x=" + i + " y=" + j + " Shortest distance was: " + shortestDist
//			    + ". Was found from " + shortestX + "," + shortestY + ". ID is: " + id);

		    multi[i][j] = id;
		    shortestDist = null;
		}

	    }
	}

    }

    private void calculateAmount(String[][] multi) {

	Integer highestAmount = null;
	String id = null;
	List<String> ignore = new ArrayList<>();
	List<String> allow = new ArrayList<>();

	for (String smallId : smallIdList) {

	    int amount = 0;

	    for (int i = 0; i < multi.length; ++i) {

		for (int j = 0; j < multi[i].length; ++j) {
//		    logger.info("X: " + i + " Y: " + j + " has value: " + multi[i][j]);
		    if ((i == 0 || j == 0) || (i == multi.length - 1 || j == multi[i].length - 1)) {
			if (!ignore.contains(smallId) && multi[i][j].equalsIgnoreCase(smallId)) {
			    ignore.add(smallId);
			}
		    }
		    if (multi[i][j].equalsIgnoreCase(smallId) && !ignore.contains(multi[i][j])) {
			amount++;
		    }
		}

	    }

	    if (highestAmount == null || amount > highestAmount) {
		highestAmount = amount;
		id = smallId;
		if (!ignore.contains(smallId)) {
		    allow.add("ID: " + id + ", count: " + amount);
		}

	    }
	}

	logger.info("ID: " + id + ", highest amount: " + highestAmount);
    }

}
