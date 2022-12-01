package com.example.redisexample.service.impl;

import com.example.redisexample.entity.Coin;
import com.example.redisexample.repository.CoinDao;
//import com.example.redisexample.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CoinServiceImpl {

    @Autowired
    private CoinDao coinDaotDao;

//    @Override
    public boolean saveCoin(Coin coin) {
        return coinDaotDao.saveCoin(coin);
    }

//    @Override
    public List<Coin> getAllCoin() {
        return coinDaotDao.getAllCoin();
    }

//    @Override
    public Coin findCoinById(int id) {
        return coinDaotDao.findCoinById(id);
    }

//    @Override
    public boolean deleteCoinById(int id) {
        return coinDaotDao.deleteCoin(id);
    }

    public void print() {
        log.info("Testing spring quartz!!!");
    }
}
