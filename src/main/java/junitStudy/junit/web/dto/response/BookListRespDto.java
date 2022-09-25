package junitStudy.junit.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListRespDto {

    List<BookRespDto> items;

    @Builder
    public BookListRespDto(List<BookRespDto> bookRespDtoList) {
        this.items = bookRespDtoList;
    }
}
