package com.upbest.mvc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by li_li on 2015/1/12.
 */
@Service
public class DBChooser {
    @Value("${database}")
    private String database;
    private boolean isSqlServer=false;
    @PostConstruct
    public void judgement(){
        if("SQL_SERVER".equalsIgnoreCase(database)){
            isSqlServer=true;
        }
    }
    public boolean isSQLServer(){
       return isSqlServer;
    }
}
