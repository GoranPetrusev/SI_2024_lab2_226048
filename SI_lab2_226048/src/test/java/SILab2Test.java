import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    void testAllItemsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 10));
        assertEquals(ex.getMessage(), "allItems list can't be null!");
    }

    @Test
    void testItemName() {
        // Test name is null
        Item item = new Item(null, "123", 13, 2f);
        SILab2.checkCart(List.of(item), 10);
        assertEquals("unknown", item.getName());

        // Test name is an empty string
        item.setName("");
        SILab2.checkCart(List.of(item), 10);
        assertEquals("unknown", item.getName());
    }

    @Test
    void testBarcodeNull() {
        Item item = new Item("Item", null, 13, 2f);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(List.of(item), 10));
        assertEquals(ex.getMessage(), "No barcode!");
    }

    @Test
    void testInvalidBarcode() {
        Item item = new Item("Item", "123n", 13, 2f);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(List.of(item), 10));
        assertEquals(ex.getMessage(), "Invalid character in item barcode!");
    }

    @Test
    void testItemDiscount() {
        // After discount is applied to the item's price
        // it should be equal to the payment meaning the function should return true
        Item item = new Item("Item", "123", 100, 0.5f);
        assertTrue(SILab2.checkCart(List.of(item), 50));

        // Same test but without any discount applied
        item.setDiscount(0.0f);
        assertTrue(SILab2.checkCart(List.of(item), 100));
    }

    @Test
    void testSpecialDiscount() {
        // The sum of all prices is lowered by 30 when the following happens:
        // item price > 300 && item discount > 0 && item barcode starts with '0'
        Item item = new Item("Item", "0123", 500, 0.5f);
        assertTrue(SILab2.checkCart(List.of(item), 220));
    }

    @Test
    void testInsufficientPayment() {
        Item item = new Item("Item", "123", 1000, 0.0f);
        assertFalse(SILab2.checkCart(List.of(item), 200));
    }

    @Test
    void testSufficientPayment() {
        Item item = new Item("Item", "123", 1000, 0.0f);
        assertTrue(SILab2.checkCart(List.of(item), 1200));
    }

    @Test
    void testSpecialDiscountMultiCondition() {
        Item i1 = new Item("Item1", "123", 50, 0.0f);
        Item i2 = new Item("Item2", "234", 400, 0.0f);
        Item i3 = new Item("Item3", "045", 90, 0.0f);
        Item i4 = new Item("Item4", "456", 50, 0.2f);
        Item i5 = new Item("Item5", "069", 420, 0.25f);

        // Exact price of all items is 625
        assertTrue(SILab2.checkCart(List.of(i1, i2, i3, i4, i5), 625));
    }
}
