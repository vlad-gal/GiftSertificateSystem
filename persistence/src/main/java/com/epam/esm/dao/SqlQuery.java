package com.epam.esm.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQuery {
    /*
    Sql queries for gift certificate
     */
    public final String SELECT_CERTIFICATE_BY_ID =
            "SELECT certificateId, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificates " +
                    "WHERE certificateId = ?";
    public final String SELECT_CERTIFICATES_BY_PARAMETERS =
            "SELECT DISTINCT gift_certificates.certificateId, gift_certificates.name, gift_certificates.description, " +
                    "gift_certificates.price, gift_certificates.duration, gift_certificates.create_date, " +
                    "gift_certificates.last_update_date " +
                    "FROM gift_certificates ";
    public final String SELECT_CERTIFICATE_TAGS =
            "SELECT tags.tagId, tags.tagName " +
                    "FROM tags " +
                    "JOIN certificates_has_tags ON tags.tagId = certificates_has_tags.tagId " +
                    "JOIN gift_certificates ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                    "WHERE gift_certificates.certificateId = ?";
    public final String INSERT_CERTIFICATE =
            "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    public final String UPDATE_CERTIFICATE =
            "UPDATE gift_certificates " +
                    "SET name = ?, description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? " +
                    "WHERE certificateId = ?";
    public final String DELETE_CERTIFICATE =
            "DELETE FROM gift_certificates WHERE certificateId = ?";
    public final String INSERT_RELATION_BETWEEN_TAG_AND_GIFT_CERTIFICATE =
            "INSERT INTO certificates_has_tags (certificateId, tagId) VALUES (?, ?)";
    /*
    Sql queries for tags
     */
    public final String SELECT_ALL_TAGS = "SELECT tagId, tagName FROM tags";
    public final String SELECT_TAG_BY_NAME = "SELECT tagId, tagName FROM tags WHERE tagName = ?";
    public final String SELECT_TAG_BY_ID = "SELECT tagId, tagName FROM tags WHERE tagId = ?";
    public final String INSERT_TAG = "INSERT INTO tags (tagName) VALUES (?)";
    public final String DELETE_TAG = "DELETE FROM tags WHERE tagId = ?";
}