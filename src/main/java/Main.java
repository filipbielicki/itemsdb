import dao.ItemDao;
import dao.ItemDaoImpl;
import dao.exceptions.EmptyTableException;
import dao.exceptions.ItemNotFoundException;

public class Main {

    public static void main(String[] args) throws EmptyTableException, ItemNotFoundException {

        ItemDao itemDao = new ItemDaoImpl();

    }
}
