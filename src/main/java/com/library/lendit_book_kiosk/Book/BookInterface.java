package com.library.lendit_book_kiosk.Book;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface BookInterface extends Serializable {
    ///////////////////////////////////////////////////////////////////////////
    // void setId(String ISBN);

    // String getId();

//    void setGenres(String genre);
//
//    void setGenres(Set<Genre> genres);

//    void SetGenres(List<String> genres);

    String getIsbn();

    void setIsbn(String isbn);
}
