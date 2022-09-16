package junitStudy.junit.web.dto;

import junitStudy.junit.domain.Book;
import lombok.Getter;

@Getter
public class BookRespDto {

    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book bookPS) {
        id = bookPS.getId();
        title = bookPS.getTitle();
        author = bookPS.getAuthor();
        return this;
    }


}
