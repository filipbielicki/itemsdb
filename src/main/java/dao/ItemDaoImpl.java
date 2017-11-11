package dao;

import dao.exceptions.EmptyTableException;
import dao.exceptions.ItemNotFoundException;
import dbsettings.ConnectionFactory;
import dbsettings.DBSettings;
import entities.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    private final Connection connection = ConnectionFactory
            .getConnection(DBSettings.DB_CONNECTION,
                           DBSettings.DB_USERNAME,
                           DBSettings.DB_PASSWORD);

    private static final String INSERT_INTO_ITEMS = "INSERT INTO warehouse.items (name, price) VALUES (?,?)";
    private static final String FIND_ITEM_BY_ID = "SELECT * FROM warehouse.items WHERE id=?";
    private static final String FIND_ALL_ITEMS = "SELECT * FROM warehouse.items";
    private static final String UPDATE_ITEM = "UPDATE warehouse.items SET name=?, price=? WHERE id=?";
    private static final String DELETE_ITEM = "DELETE FROM warehouse.items WHERE id=?";
    private static final String DELETE_ALL_ITEMS = "DELETE FROM warehouse.items";

    @Override
    public void insertNewItem(Item item) {
        insertItem(item);
    }

    @Override
    public List<Item> findAllItems() throws EmptyTableException {
        return findAll();
    }

    @Override
    public Item findItemById(Integer id) throws ItemNotFoundException {
        return findById(id);
    }

    @Override
    public void updateOneItem(Item item) {
        try {
            updateItem(item);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOneItem(Integer id) {
        try {
            deleteItem(id);
        } catch (ItemNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllItems() {
        deleteAll();
    }

    private void insertItem(Item item) {

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(INSERT_INTO_ITEMS);
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Item findById(Integer id) throws ItemNotFoundException {
        PreparedStatement statement;
        Item searchedItem = null;

        try {
            statement = connection.prepareStatement(FIND_ITEM_BY_ID);
            statement.setInt(1, id);
            List<Item> items = getAllItemsFromResultSet(statement.executeQuery());
            if (items.size() > 0) {
                searchedItem = items.get(0);
            } else {
                throw new ItemNotFoundException();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedItem;
    }

    private List<Item> findAll() throws EmptyTableException {
        Statement statement;
        List<Item> result = new ArrayList<>();

        try {
            statement = connection.createStatement();
            result = getAllItemsFromResultSet(statement.executeQuery(FIND_ALL_ITEMS));
            if (result.size() == 0) {
                throw new EmptyTableException();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Item> getAllItemsFromResultSet(ResultSet resultSet) {

        List<Item> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");

                Item item = new Item();
                item.setId(id);
                item.setName(name);
                item.setPrice(price);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void updateItem(Item item) throws ItemNotFoundException {

        PreparedStatement statement;

        try {
            Item updatedItem = findById(item.getId());

            if (updatedItem == null){
                throw new ItemNotFoundException();
            }

            statement = connection.prepareStatement(UPDATE_ITEM);

            statement.setInt(3, updatedItem.getId());
            if (item.getName() == null) {
                statement.setNull(1, 0);
            } else {
                statement.setString(1, item.getName());
            }
            if (item.getPrice() == null) {
                statement.setNull(2, 0);
            } else {
                statement.setBigDecimal(2, item.getPrice());
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(Integer id) throws ItemNotFoundException {

        PreparedStatement statement;

        Item itemToDelete = findItemById(id);
        if(itemToDelete == null){
            throw new ItemNotFoundException();
        }

        try {
            statement = connection.prepareStatement(DELETE_ITEM);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAll() {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(DELETE_ALL_ITEMS);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
