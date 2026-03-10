package configs;

public class AppConfig {
    @ConfigProperty(propertyName = "csv.delimiter")
    public String delimiter;

    @ConfigProperty(propertyName = "csv.bookArchiveFilePath")
    public String bookArchiveFilePath;

    @ConfigProperty(propertyName = "csv.requestArchiveFilePath")
    public String requestArchiveFilePath;

    @ConfigProperty(propertyName = "csv.orderArchiveFilePath")
    public String orderArchiveFilePath;

    @ConfigProperty(propertyName = "csv.bookCatalogFilePath")
    public String bookCatalogFilePath;

    @ConfigProperty(propertyName = "csv.requestCatalogFilePath")
    public String requestCatalogFilePath;

    @ConfigProperty(propertyName = "csv.orderCatalogFilePath")
    public String orderCatalogFilePath;

    @ConfigProperty(propertyName = "appLogic.monthsToMarkAsStale")
    public int monthsToMarkAsStale;

    @ConfigProperty(propertyName = "appLogic.autoCompleteRequestAfterAdding")
    public boolean autoCompleteRequestAfterAdding;

    @ConfigProperty(propertyName = "sql.url")
    public String url;

    @ConfigProperty(propertyName = "sql.username")
    public String username;

    @ConfigProperty(propertyName = "sql.password")
    public String password;
}