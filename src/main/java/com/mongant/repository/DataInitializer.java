package com.mongant.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Repository
@Qualifier("dataInitializer")
public class DataInitializer {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static int maxId;

    @Value("${dataFile.path}")
    private String dataFilePath;

    public DataInitializer(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        createAddressTable();
        maxId = findMaxId();
    }

    public static int getMaxId() {
        return maxId;
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

    private void createAddressTable() {
        try {
            jdbcTemplate.execute(createTableSql(),
                                (PreparedStatementCallback) ps -> ps.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Find max id from address data base
     */
    @SuppressWarnings("ConstantConditions")
    private int findMaxId() {
        int maxPartResult = 0;
        String sql = "select max(id) as maxPart from ADDRESS_BOOK";
        try {
            maxPartResult = jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maxPartResult;
    }
}
