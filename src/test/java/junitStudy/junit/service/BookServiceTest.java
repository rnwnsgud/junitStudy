package junitStudy.junit.service;

import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.util.MailSender;
import junitStudy.junit.util.MailSenderStub;
import junitStudy.junit.web.dto.BookRespDto;
import junitStudy.junit.web.dto.BookSaveReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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

}