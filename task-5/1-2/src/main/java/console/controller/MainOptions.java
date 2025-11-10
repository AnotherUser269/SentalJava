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
    PRINT_ORDER_ARCHIVE(12);

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