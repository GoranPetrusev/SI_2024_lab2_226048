# Втора лабораториска вежба по Софтверско инженерство

## Горан Петрушев, бр. ба индекс 226048

### Control Flow Graph

![alt text](https://github.com/GoranPetrusev/SI_2024_lab2_226048/blob/master/CFG.png?raw=true)

### Цикломатска комплексност

Цикломатската комплексност на овој код е 10, истата ја добив преку формулата P+1, каде што P е бројот на предикатни јазли. Во случајoв P=9, па цикломатската комплексност изнесува 10.

### Тест случаи според критериумот Every branch

Во овој случај се тестира кога листата е `null` и се фрла исклучок
```java
    @Test
    void testAllItemsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 10));
        assertEquals(ex.getMessage(), "allItems list can't be null!");
    }
```

Во овој случај се тестира кога името на производот е `null` или кога е празно т.е. со 0 знаци
```java
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
```

Во овој случај се тестира кога баркодот на производот е `null`
```java
    @Test
    void testBarcodeNull() {
        Item item = new Item("Item", null, 13, 2f);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(List.of(item), 10));
        assertEquals(ex.getMessage(), "No barcode!");
    }
```

Во овој случај се тестира кога баркодот на производот содржи невалиден знак. Валидни знаци за баркодот на производот се `012345678`
```java
    @Test
    void testInvalidBarcode() {
        Item item = new Item("Item", "123n", 13, 2f);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(List.of(item), 10));
        assertEquals(ex.getMessage(), "Invalid character in item barcode!");
    }
```

Во овој тест случај се тестира намалувањето на производот. Ако производот има намалување тогаш соодветно ќе се промени цената. Доколку нема никакво намалување, си останува оригиналната цена на производот
```java
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
```

Во овој тест случај се тестира специјалното намалување на производот, кое се однесува само за производи кои што истовремено се над 300 по цена, имаат намалување и баркодот им почнува со 0
```java
    @Test
    void testSpecialDiscount() {
        // The sum of all prices is lowered by 30 when the following happens:
        // item price > 300 && item discount > 0 && item barcode starts with '0'
        Item item = new Item("Item", "0123", 500, 0.5f);
        assertTrue(SILab2.checkCart(List.of(item), 220));
    }
```

Во овој случај се тестира сценариото во кое корисникот нема доволно пари за да ги купи производите во кошничката
```java
    @Test
    void testInsufficientPayment() {
        Item item = new Item("Item", "123", 1000, 0.0f);
        assertFalse(SILab2.checkCart(List.of(item), 200));
    }
```

Исто како претходниот тест, но овде корисникот има доволно пари за производите
```java
    @Test
    void testSufficientPayment() {
        Item item = new Item("Item", "123", 1000, 0.0f);
        assertTrue(SILab2.checkCart(List.of(item), 1200));
    }
```

### Тест случаи според критериумот Multiple Condition

```java
if (item.getPrice() > 300 && item.getDiscount() > 0 && item.getBarcode().charAt(0) == '0'){
    sum -= 30;
}
``` 
За овој дел треба да се тестира горе наведениот услов според Multiple Condition критериумот. Овде имаме 5 можни случаи:
- Ни еден од условите не е задоволен
- Првиот услов е задоволен со производ со цена над 300
- Вториот услов е задоволен со производ кој е на намалување
- Третиор услов е задоволен со производ чиј што баркод започнува со 0
- Сите услови се исполнети од производ со тоа што вкупната цена на сметката се намалува за 30

Доленаведениот код го тестира ова со 5 производи од кои што секој редоследно по исполнува еден од горе наведените услови
```java
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
```

### Објаснување на напишаните unit tests


