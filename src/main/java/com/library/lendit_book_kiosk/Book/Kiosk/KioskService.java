package com.library.lendit_book_kiosk.Book.Kiosk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class KioskService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(KioskService.class);
    @Autowired
    private final KioskRepository kioskRepository;

    public KioskService(KioskRepository kioskRepository) {
        this.kioskRepository = kioskRepository;
    }
}
