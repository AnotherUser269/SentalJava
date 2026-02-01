package console.controller;

public enum MainOptions {
    ADD_BOOK(1),
    REMOVE_BOOK(2),
    MAKE_REQUEST(3),
    CANCEL_REQUEST(4),
    MAKE_ORDER(5),
    CANCEL_ORDER(6),
    PRINT_BOOK_CATALOG(7),
    PRINT_REQUEST_CATALOG(8),
    PRINT_ORDER_CATALOG(9),
    PRINT_BOOK_ARCHIVE(10),
    PRINT_REQUEST_ARCHIVE(11),
    PRINT_ORDER_ARCHIVE(12),
    SAVE_BOOK_ARCHIVE_TO_FILE(13),
    SAVE_REQUEST_ARCHIVE_TO_FILE(14),
    SAVE_ORDER_ARCHIVE_TO_FILE(15),
    SAVE_BOOK_CATALOG_TO_FILE(16),
    SAVE_REQUEST_CATALOG_TO_FILE(17),
    SAVE_ORDER_CATALOG_TO_FILE(18),
    LOAD_BOOK_ARCHIVE_FROM_FILE(19),
    LOAD_REQUEST_ARCHIVE_FROM_FILE(20),
    LOAD_ORDER_ARCHIVE_FROM_FILE(21),
    LOAD_BOOK_CATALOG_FROM_FILE(22),
    LOAD_REQUEST_CATALOG_FROM_FILE(23),
    LOAD_ORDER_CATALOG_FROM_FILE(24),
    LOAD_DATABASE(25),
    SAVE_DATABASE(26);

    private final int index;

    MainOptions(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static MainOptions fromIndex(int idx) {
        for (MainOptions m : values()) {
            if (m.index == idx) {
                return m;
            }
        }

        return null;
    }
}