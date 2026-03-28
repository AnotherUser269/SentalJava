package utils;

public class CsvHelper {
    public static String escapeCsv(String field) {
        if (field == null) return "";
        boolean needQuotes = field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r");
        String escaped = field.replace("\"", "\"\"");
        return needQuotes ? "\"" + escaped + "\"" : escaped;
    }
}