package junitStudy.junit.service;

import junitStudy.junit.domain.Book;
import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.util.MailSender;
import junitStudy.junit.web.dto.BookRespDto;
import junitStudy.junit.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 등록
    // return 값을 Book으로 받게 되어 컨트롤러단까지 흘러 들어가면 안됨
    // open in view, 영속성 컨텍스트가 트랜잭션 범위를 넘어서 살아있어서 지연로딩이 가능하고
    // 지연로딩을 컨트롤러 단에서 이용하면 수많은 변수가 일어날 수 있어서 주의해야함.
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto resisterBook(BookSaveReqDto dto) {
        Book book = dto.toEntity();
        Book bookPS = bookRepository.save(book);
        if (bookPS != null) {
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return bookPS.toDto();

    }

    // 2. 책 목록 보기
    public List<BookRespDto> checkBookList() {
        BookRespDto dto = new BookRespDto();
        List<Book> books = bookRepository.findAll();
        List<BookRespDto> dtos = books.stream()
                //.map(new BookRespDto()::toDto()
                .map(Book::toDto)
                .collect(Collectors.toList());
        return dtos;

    }

    // 3. 책 한건 보기
    public BookRespDto checkOneBook(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제
    // 컨트롤러에서 null 막을 거고, 없는 id가 들어오면 롤백할 필요가 없음
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto editBook(Long id, BookSaveReqDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }

    } // 메서드 종료시에 더티체킹(flush)로 update 된다.
}
