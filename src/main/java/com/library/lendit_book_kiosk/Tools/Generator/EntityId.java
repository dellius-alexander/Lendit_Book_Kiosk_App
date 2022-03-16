package com.library.lendit_book_kiosk.Tools.Generator;

import java.io.Serializable;

public interface EntityId <T> extends Serializable {
    /**
     * The value of the underlying type
     *
     * @return the value
     */
    T getId();

    /**
     * Returns a string representation of the id (e.g. for usage in REST endpoints).
     *
     * @return a human friendly String representation of the id
     */
    String asString();
}
