package junitStudy.junit.web.dto;

import junitStudy.junit.domain.Book;
import lombok.Setter;

@Setter // Controller에서 Setter가 호출되면서 DTO에 값이 채워짐
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
