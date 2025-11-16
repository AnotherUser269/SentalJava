package configs;

public record AppConfig() {
    public static String bookArchiveFilePath = "BookArchive.csv";
    public static String requestArchiveFilePath = "RequestArchive.csv";
    public static String orderArchiveFilePath = "OrderArchive.csv";

    public static String bookCatalogFilePath = "BookCatalog.csv";
    public static String requestCatalogFilePath = "RequestCatalog.csv";
    public static String orderCatalogFilePath = "OrderCatalog.csv";

    public static char delimiter = ';';
}
