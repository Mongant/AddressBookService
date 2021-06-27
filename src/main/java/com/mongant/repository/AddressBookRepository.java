package com.mongant.repository;

import com.mongant.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class AddressBookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AddressBookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Find data between two id parts
     * @param startPart start id from database
     * @param endPart end id from database
     * @return information about contact names
     */
    public List<Contact> findAllContacts(int startPart, int endPart) throws SQLException {
        String sql = "select id, name from ADDRESS_BOOK where id between :startPart and :endPart";
        List<Contact> contactsResult;

        RowMapper<Contact> mapper = (rs, rowNum) -> {
            Contact contact = new Contact();
            contact.setId(rs.getInt("id"));
            contact.setName(rs.getString("name"));
            return contact;
        };

        try {
            contactsResult = jdbcTemplate.query(sql,
                    new MapSqlParameterSource().addValue("startPart", startPart).
                                                addValue("endPart", endPart),
                    mapper);
        } catch (Exception ex) {
            throw new SQLException("Inner SQL exception");
        }
        return contactsResult;
    }
}
