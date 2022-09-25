package junitStudy.junit.service;

import junitStudy.junit.domain.Book;
import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.util.MailSender;
import junitStudy.junit.web.dto.response.BookListRespDto;
import junitStudy.junit.web.dto.response.BookRespDto;
import junitStudy.junit.web.dto.request.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 가짜 메모리 환경 생성
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    // 가짜 환경에 띄우기
    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    void resisterBook() {
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub (가설)
        //실행결과를 미리 정의한다
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        //when
        BookRespDto bookRespDto = bookService.resisterBook(dto);
        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());

    }

    //checktpoint
    @Test
    void viewBookList() {

        //given

        //stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListRespDto bookListRespDto = bookService.checkBookList();
        //print

        //then
        assertThat("junit강의").isEqualTo(bookListRespDto.getItems().get(0).getTitle());
    }

    @Test
    void viewOneBook() {
        //given
        Long id = 1L;
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        //stub
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.checkOneBook(id);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void editBook() {
        //given
        Long id = 1L;

        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("spring강의");
        bookSaveReqDto.setAuthor("겟인데어");
        //stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.editBook(id, bookSaveReqDto);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(bookSaveReqDto.getTitle());


    }

}