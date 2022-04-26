package com.library.lendit_book_kiosk.Fines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class FinesService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(FinesService.class);
    @Autowired
    private final FinesRepository finesRepository;

    public FinesService(FinesRepository finesRepository) {
        this.finesRepository = finesRepository;
    }

}
