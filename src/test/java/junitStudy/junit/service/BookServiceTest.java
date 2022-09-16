package junitStudy.junit.service;

import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.util.MailSenderStub;
import junitStudy.junit.web.dto.BookRespDto;
import junitStudy.junit.web.dto.BookSaveReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void resisterBook() {
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        //when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.resisterBook(dto);
        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());


    }

}