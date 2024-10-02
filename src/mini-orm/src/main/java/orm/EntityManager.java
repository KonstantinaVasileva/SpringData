package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s";
    private static final String FIND_QUERY = "SELECT * FROM %s %s LIMIT 1";

    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        if (value == null || (long) value <= 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, primaryKey, value);
    }

    private boolean doUpdate(E entity, Field primaryKey, Object value) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        List<String> columnList = getColumn(entity);
        List<String> valueList = getValues(entity);

        List<String> columnValues = new ArrayList<>();
        for (int i = 0; i < columnList.size(); i++) {
            columnValues.add(columnList.get(i) + "=" + valueList.get(i));
        }

        String condition = primaryKey.getName() + "=" + value;

        String update = String.format(UPDATE_QUERY, tableName, columnValues, condition);

        PreparedStatement statement = connection.prepareStatement(update);
        return statement.executeUpdate() == 1;
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> columnList = getColumn(entity);
        List<String> valuesList = getValues(entity);

        String insert = String.format(INSERT_QUERY,
                tableName,
                String.join(",  ", columnList),
                String.join(", ", valuesList));
        PreparedStatement statement = connection.prepareStatement(insert);
        int result = statement.executeUpdate();
        return result == 1;
    }

    private List<String> getValues(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (!declaredField.isAnnotationPresent(Column.class)) {
                continue;
            }
            declaredField.setAccessible(true);
            Object value = declaredField.get(entity);
            result.add("'" + value + "'");
        }

        return result;
    }

    private List<String> getColumn(E entity) {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            result.add(column.name());
        }


        return result;
    }

    private String getTableName(Class<?> aClass) {

        Entity annotation = aClass.getAnnotation(Entity.class);
        if (annotation == null) {
            throw new RuntimeException("No entity annotation present");
        }
        return annotation.name();

    }

    private static Field getId(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity dose not have primary key"));
    }

    @Override
    public Iterable find(Class table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable find(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);
        String query = FIND_QUERY.formatted(tableName, where != null ? "WHERE " + where : "");
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        List<E> resultList = new ArrayList<>();
        while (result.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            resultList.add(entity);
            fillEntity(table, result, entity);
        }
        return resultList;
    }

    @Override
    public Object findFirst(Class table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);
        String query = FIND_QUERY.formatted(tableName, where != null ? "WHERE " + where : "");
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        result.next();
        E entity = table.getDeclaredConstructor().newInstance();

        fillEntity(table, result, entity);

        return entity;
    }

    private void fillEntity(Class<E> table, ResultSet result, Object entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            fillField(declaredField, result, entity);
        }
    }

    private void fillField(Field field, ResultSet result, Object entity) throws SQLException, IllegalAccessException {
        if (field.getType() == int.class || field.getType() == long.class){
            field.set(entity, result.getInt(field.getName()));
        } else if (field.getType() == LocalDate.class){
            field.set(entity, result.getDate(field.getName()).toLocalDate());
        } else {
            field.set(entity, result.getString(field.getName()));
        }
    }
}