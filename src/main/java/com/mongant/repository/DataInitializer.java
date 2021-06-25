package com.mongant.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DataInitializer {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${dataFile.path}")
    private String dataFilePath;

    public DataInitializer(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String createTableSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ADDRESS_BOOK(\n").
           append("                          ID int not null,\n").
           append("                          NAME varchar(255),\n").
           append("                          primary key (ID))\n").
           append("                          as select * from csvread(\n'").append(dataFilePath).
           append("', null, 'charset=UTF-8 fieldSeparator=,')");
        return sb.toString();
    }

    @PostConstruct
    public void createAddressTable() {
        try {
            jdbcTemplate.execute(createTableSql(), (PreparedStatementCallback) ps -> ps.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
