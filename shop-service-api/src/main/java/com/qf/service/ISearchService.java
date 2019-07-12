package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {
    List<Goods> searchBykey(String keyword);

    int add(Goods goods);
}
