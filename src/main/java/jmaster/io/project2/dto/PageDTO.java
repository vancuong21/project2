package jmaster.io.project2.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private int totalPages;
    private long totalElements;
    private List<T> contents;
}
