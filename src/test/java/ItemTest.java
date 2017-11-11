import entities.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ItemTest {

    private static final Integer ID = 10;
    private static final String NAME = "The Knights of Ni";
    private static final BigDecimal PRICE = BigDecimal.valueOf(4.76);

    private Item testedObject;

    @Before
    public void setUp(){
        testedObject = new Item();
        testedObject.setId(ID);
        testedObject.setName(NAME);
        testedObject.setPrice(PRICE);
    }

    @Test
    public void shouldCreateItemObject() throws Exception {
        assertThat(testedObject).isNotNull();
        assertThat(testedObject.getId()).isEqualTo(10);
        assertThat(testedObject.getName()).isEqualTo("The Knights of Ni");
        assertThat(testedObject.getPrice()).isEqualTo(BigDecimal.valueOf(4.76));
    }
}
