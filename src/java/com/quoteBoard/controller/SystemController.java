package com.quoteBoard.controller;


import com.quoteBoard.dto.CreateQuoteDto;
import com.quoteBoard.dto.ResponseQuoteDto;
import com.quoteBoard.dto.UpdateQuoteDto;
import com.quoteBoard.service.QuoteService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SystemController {

    private final BufferedReader br;
    private final QuoteService quoteService;

    public SystemController(InputStream in) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.quoteService = new QuoteService();
    }

    public void register() throws IOException {
        System.out.print("명언 : ");
        String word = br.readLine();
        System.out.print("작가 : ");
        String writer = br.readLine();

        CreateQuoteDto dto = new CreateQuoteDto(word, writer);
        Long id = quoteService.create(dto);

        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        List<ResponseQuoteDto> data = quoteService.getList();
        for(ResponseQuoteDto dto : data) {
            System.out.println(dto.id() + " / " + dto.writer() + " / " + dto.word());
        }
    }

    public void delete(Long id) {
        if(id == -1L) System.out.println("유효하지 않은 템플릿입니다.");
        try {
            quoteService.delete(id);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void update(Long id) throws IOException {
        if(id == -1L) System.out.println("유효하지 않은 템플릿입니다.");
        ResponseQuoteDto oldQuote = quoteService.get(id);
        if(oldQuote.id() == -1) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }
        System.out.println("명언(기존) : " + oldQuote.word());
        System.out.print("명언 : ");
        String word = br.readLine();
        System.out.println("작가(기존) : " + oldQuote.writer());
        System.out.print("작가 : ");
        String writer = br.readLine();

        quoteService.update(new UpdateQuoteDto(id, word, writer));
    }

    public void build() {
        quoteService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
 }
