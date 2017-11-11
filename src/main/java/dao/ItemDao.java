package dao;

import dao.exceptions.EmptyTableException;
import dao.exceptions.ItemNotFoundException;
import entities.Item;
import java.util.List;

public interface ItemDao {

    void insertNewItem(Item item);
    List<Item> findAllItems() throws EmptyTableException;
    Item findItemById(Integer id) throws ItemNotFoundException;
    void updateOneItem(Item item);
    void deleteOneItem(Integer id);
    void deleteAllItems();
}
