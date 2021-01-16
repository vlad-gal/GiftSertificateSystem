package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final String TIME_ZONE = "Europe/Minsk";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(ColumnName.CERTIFICATE_ID));
        certificate.setName(rs.getString(ColumnName.NAME));
        certificate.setDescription(rs.getString(ColumnName.DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(ColumnName.PRICE));
        certificate.setDuration(rs.getInt(ColumnName.DURATION));
        LocalDateTime createdDate = rs.getTimestamp(ColumnName.CREATE_DATE)
                .toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime();
        certificate.setCreatedDate(createdDate);
        LocalDateTime lastUpdateDate = rs.getTimestamp(ColumnName.LAST_UPDATE_DATE)
                .toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime();
        certificate.setLastUpdateDate(lastUpdateDate);
        return certificate;
    }
}