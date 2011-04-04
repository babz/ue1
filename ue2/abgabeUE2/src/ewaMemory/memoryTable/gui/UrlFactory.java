package ewaMemory.memoryTable.gui;

public class UrlFactory {

	private static final String MEMORY_TABLE_CONTROLLER_LOCATION = "MemoryTableController";

	public static String generateMemoryCardClickUrl(int x, int y) {
		return MEMORY_TABLE_CONTROLLER_LOCATION + "?" + MemoryTableParams.X + "=" + x + "&amp;" + MemoryTableParams.Y + "=" + y;
	}

}
