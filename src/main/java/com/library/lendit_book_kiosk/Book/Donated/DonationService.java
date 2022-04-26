package com.library.lendit_book_kiosk.Book.Donated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class DonationService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(DonationService.class);
    @Autowired
    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }


}
