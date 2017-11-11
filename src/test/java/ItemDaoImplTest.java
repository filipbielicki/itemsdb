import dao.ItemDaoImpl;
import dao.exceptions.EmptyTableException;
import entities.Item;
import dao.exceptions.ItemNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemDaoImplTest {

    @Mock
    private ItemDaoImpl itemDao;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnSearchedItem() throws Exception {

        Item item = new Item(4,"Buttpa", BigDecimal.valueOf(15.05));

        when(itemDao.findItemById(4)).thenReturn(item);

        Item searchedItem = itemDao.findItemById(4);

        assertThat(searchedItem).isEqualTo(item);
    }

    @Test
    public void shouldReturnAllItems() throws Exception {

        List<Item> itemList = getItemList();

        when(itemDao.findAllItems()).thenReturn(itemList);

        List<Item> testItemList = itemDao.findAllItems();

        assertThat(testItemList.size()).isEqualTo(3);
        assertThat(testItemList.get(0).getId()).isEqualTo(1);
        assertThat(testItemList.get(0).getPrice()).isEqualTo(BigDecimal.valueOf(1.05));
        assertThat(testItemList.get(1).getName()).isEqualTo("BBB");
        assertThat(testItemList.get(2).getName()).isEqualTo("CCC");
    }

    @Test
    public void shouldInsertNewItem() throws Exception {
        Item itemToInsert = new Item("Mr Postman", BigDecimal.valueOf(50.05));
        itemDao.insertNewItem(itemToInsert);
        verify(itemDao, times(1)).insertNewItem(Mockito.any(Item.class));
    }

    @Test
    public void shouldDeleteItem() throws Exception {
        itemDao.deleteOneItem(4);
        verify(itemDao, times(1)).deleteOneItem(4);
    }

    @Test
    public void shouldDeleteAllItems() throws Exception {
        itemDao.deleteAllItems();
        verify(itemDao, times(1)).deleteAllItems();
    }

    @Test
    public void shouldUpdateItem() throws Exception {
        Item itemToUpdate = new Item(4,"kaka", BigDecimal.valueOf(36.77));
        itemDao.updateOneItem(itemToUpdate);
        verify(itemDao, times(1)).updateOneItem(itemToUpdate);
    }

    @Test
    public void shouldThrowItemNotFoundExceptionWhenSearchingForNonExistingItem() throws Exception {
        thrown.expect(ItemNotFoundException.class);
        when(itemDao.findItemById(115)).thenThrow(ItemNotFoundException.class);
        Item searchedItem = itemDao.findItemById(115);
        assertThat(searchedItem).isNull();
    }

    @Test
    public void shouldThrowEmptyTableExceptionWhenFindingAllItems() throws Exception {
        thrown.expect(EmptyTableException.class);
        when(itemDao.findAllItems()).thenThrow(EmptyTableException.class);
        List<Item> itemList = itemDao.findAllItems();
        assertThat(itemList).isNull();
        assertThat(itemList.size()).isEqualTo(0);
    }

    private List<Item> getItemList(){
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item(1,"AAA", BigDecimal.valueOf(1.05));
        Item item2 = new Item(2,"BBB", BigDecimal.valueOf(5.05));
        Item item3 = new Item(3,"CCC", BigDecimal.valueOf(159.05));
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        return itemList;
    }
}
