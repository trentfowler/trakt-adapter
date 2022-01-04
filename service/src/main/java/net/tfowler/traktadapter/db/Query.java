package net.tfowler.traktadapter.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("Duplicates,unused")
public class Query {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query.class);

    private static final Query instance = new Query();

    private Connection connection;

    private Query() {
    }

    public static Query with(final Connection connection) {
        instance.connection = connection;
        return instance;
    }

    public <T> T fetch(Class<T> cl, String sql, Object... params) throws SQLException {
        if (connection == null) {
            throw new SQLException("Unable to retrieve record. Connection must not be null!");
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object param : params) {
                if (param instanceof Date) {
                    statement.setTimestamp(i++, new Timestamp(((Date) param).getTime()));
                } else if (param instanceof Integer) {
                    statement.setInt(i++, (Integer) param);
                } else if (param instanceof Long) {
                    statement.setLong(i++, (Long) param);
                } else if (param instanceof Double) {
                    statement.setDouble(i++, (Double) param);
                } else if (param instanceof Float) {
                    statement.setFloat(i++, (Float) param);
                } else {
                    statement.setString(i++, (String) param);
                }
            }
            ResultSet rs = statement.executeQuery();
            return ResultSetMapper.mapToEntity(rs, cl);
        } catch (Exception sqle) {
            throw new SQLException("Failed to execute the query: %s", sql, sqle);
        }
    }

    public <T> List<T> fetchList(Class<T> cl, String sql, Object... params) throws SQLException {
        if (connection == null) {
            throw new SQLException("Unable to retrieve records. Connection must not be null!");
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object param : params) {
                if (param instanceof Date) {
                    statement.setTimestamp(i++, new Timestamp(((Date) param).getTime()));
                } else if (param instanceof Integer) {
                    statement.setInt(i++, (Integer) param);
                } else if (param instanceof Long) {
                    statement.setLong(i++, (Long) param);
                } else if (param instanceof Double) {
                    statement.setDouble(i++, (Double) param);
                } else if (param instanceof Float) {
                    statement.setFloat(i++, (Float) param);
                } else {
                    statement.setString(i++, (String) param);
                }
            }
            ResultSet rs = statement.executeQuery();
            return ResultSetMapper.mapToList(rs, cl);
        } catch (Exception sqle) {
            throw new SQLException("Failed to execute the query: %s", sql, sqle);
        }
    }

    public <T> Set<T> fetchSet(Class<T> cl, String sql, Object... args) throws SQLException {
        return new HashSet<>(fetchList(cl, sql, args));
    }
}
