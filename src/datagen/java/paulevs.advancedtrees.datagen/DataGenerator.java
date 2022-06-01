package paulevs.advancedtrees.datagen;

import net.modificationstation.stationapi.api.util.math.Direction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataGenerator {
	public static void main(String[] args) {
		generateLog("oak_log", 0, 7);
	}
	
	public static void generateLog(String name, int minAge, int maxAge) {
		for (int age = minAge; age <= maxAge; age++) {
			makeLogModel(name, age);
		}
		
		Direction[] directions = Direction.values();
		int maxCount = (maxAge - minAge + 1) * directions.length;
		
		StringBuilder builder = new StringBuilder("{\n");
		builder.append("\t\"variants\": {\n");
		
		int counter = 0;
		for (Direction dir: directions) {
			String rot = getRotation(dir);
			for (int age = minAge; age <= maxAge; age++) {
				counter++;
				
				String propStr = String.format("direction=%s,age=%d", dir.asString(), age);
				builder.append("\t\t\"");
				builder.append(propStr);
				builder.append("\": { \"model\": \"advancedtrees:block/");
				builder.append(name);
				builder.append("_");
				builder.append(age);
				builder.append("\"");
				builder.append(rot);
				
				if (counter == maxCount) {
					builder.append(" }\n");
				}
				else {
					builder.append(" },\n");
				}
			}
		}
		
		builder.append("\t}\n");
		builder.append("}");
		
		File dest = new File("./src/main/resources/assets/advancedtrees/stationapi/blockstates/" + name + ".json");
		writeFile(dest, builder.toString());
	}
	
	private static String getRotation(Direction direction) {
		String result = "";
		switch (direction) {
			case UP -> result = ", \"x\": 180";
			case WEST -> result = ", \"x\": 90";
			case EAST -> result = ", \"x\": 90, \"y\": 180";
			case NORTH -> result = ", \"x\": 90, \"y\": 90";
			case SOUTH -> result = ", \"x\": 90, \"y\": 270";
		}
		return result;
	}
	
	private static void writeFile(File file, String string) {
		file.getParentFile().mkdirs();
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(string);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void makeLogModel(String name, int age) {
		StringBuilder builder = new StringBuilder("{\n");
		builder.append("\t\"parent\": \"advancedtrees:block/log_");
		builder.append(age);
		builder.append("\",\n");
		builder.append("\t\"textures\": {\n");
		builder.append("\t\t\"top\": \"advancedtrees:block/");
		builder.append(name);
		builder.append("_top\",\n");
		builder.append("\t\t\"side\": \"advancedtrees:block/");
		builder.append(name);
		builder.append("_side\"\n");
		builder.append("\t}\n");
		builder.append("}");
		
		File dest = new File("./src/main/resources/assets/advancedtrees/stationapi/models/block/" + name + "_" + age + ".json");
		writeFile(dest, builder.toString());
	}
}
