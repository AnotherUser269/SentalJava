package console.controller;

public enum ScreenKey {
    START("Start"),
    MAIN_MENU("MainMenu"),
    BOOK_ADD("BookAdd"),
    BOOK_REMOVE("BookRemove"),
    ORDER_MAKE("OrderMake"),
    ORDER_CANCEL("OrderCancel"),
    REQUEST_MAKE("RequestMake"),
    REQUEST_CANCEL("RequestCancel");

    private final String key;

    ScreenKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}