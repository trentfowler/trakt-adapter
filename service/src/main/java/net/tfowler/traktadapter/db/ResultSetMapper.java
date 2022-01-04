package net.tfowler.traktadapter.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ResultSetMapper {
    public static <T> T mapToEntity(final ResultSet rs, final Class<T> cl) throws Exception {
        validate(cl);
        if (!rs.next()) {
            return null;
        }
        T entity = cl.newInstance();
        for (Field field : cl.getDeclaredFields()) {
            Class<?> boxedType = TypeHelper.boxedType(field.getType());
            Object boxed = boxedType.cast(rs.getObject(field.getName()));
            field.setAccessible(true);
            field.set(entity, boxed);
        }
        if (rs.next()) {
            throw new Exception("Error! More than one result cannot be mapped to a single entity.");
        }
        return entity;
    }

    public static <T> List<T> mapToList(final ResultSet rs, final Class<T> cl) throws Exception {
        validate(cl);
        final List<T> entityList = new ArrayList<>();
        while (rs.next()) {
            T entity = cl.newInstance();
            for (Field field : cl.getDeclaredFields()) {
                Class<?> boxedType = TypeHelper.boxedType(field.getType());
                Object boxed = boxedType.cast(rs.getObject(field.getName()));
                field.setAccessible(true);
                field.set(entity, boxed);
            }
            entityList.add(entity);
        }
        return entityList;
    }

    private static void validate(final Class<?> cl) {
        if (!cl.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(String.format("Invalid type %s! Beans must be annotated with @Entity", cl.getSimpleName()));
        }
    }
}
