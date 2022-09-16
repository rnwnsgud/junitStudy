package junitStudy.junit.service;

import junitStudy.junit.domain.Book;
import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.web.dto.BookRespDto;
import junitStudy.junit.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    // return 값을 Book으로 받게 되어 컨트롤러단까지 흘러 들어가면 안됨
    // open in view, 영속성 컨텍스트가 트랜잭션 범위를 넘어서 살아있어서 지연로딩이 가능하고
    // 지연로딩을 컨트롤러 단에서 이용하면 수많은 변수가 일어날 수 있어서 주의해야함.
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto resisterBook(BookSaveReqDto dto) {
        Book book = dto.toEntity();
        Book bookPS = bookRepository.save(book);
        return new BookRespDto().toDto(bookPS);

    }

    // 2. 책 목록 보기

    // 3. 책 한건 보기

    // 4. 책 삭제

    // 5. 책 수정정
}
