package com.library.lendit_book_kiosk.Utility.Generator;

import java.util.UUID;

public class InMemoryUniqueIdGenerator implements UniqueIdGenerator<UUID> {
    @Override
    public UUID getNextUniqueId() {
        return UUID.randomUUID();
    }
}
