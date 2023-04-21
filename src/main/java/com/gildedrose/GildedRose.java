package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }


    public void updateQuality() {
        for (Item item : items) {
            String itemName = item.name;

            if (itemIsSulfuras(item)) return;

            if (itemIsConjured(item)) return;

            if (itemIsAgedBrieOrBackstagePass(item, itemName)) return;

            isGeneralItem(item);

        }
    }

    private boolean itemIsAgedBrieOrBackstagePass(Item item, String itemName) {
        if (itemNameCheckStartWith(itemName, "Aged Brie")
            || itemNameCheckStartWith(itemName, "Backstage passes ")) {

            if (isQualityLessThanFifty(item)) {
                isItemBackstagePass(item, itemName);
                isItemAgedBrie(item, itemName);
                sellByDateDecrease(item);
                return true;
            }

            sellByDateDecrease(item);
            item.quality = 50;
            return true;
        }
        return false;
    }

    private boolean itemIsConjured(Item item) {
        if (itemNameCheckStartWith(item.name, "Conjured ")) {
            if (item.sellIn <= 0){
                item.quality -= 2;
            }
            item.quality -= 2;
            sellByDateDecrease(item);
            return true;
        }
        return false;
    }

    private boolean itemIsSulfuras(Item item) {
        if (itemNameCheckStartWith(item.name, "Sulfuras")) {
            item.quality = 80;
            sellByDateDecrease(item);
            return true;
        }
        return false;
    }

    private void isGeneralItem(Item item) {
        sellByDateDecrease(item);
        if (item.quality > 0) {
            if (item.sellIn < 0) {
                item.quality -= 2;
            } else {
                item.quality--;
            }
        }
    }


    private void sellByDateDecrease(Item item) {
        item.sellIn--;
    }

    private void isItemBackstagePass(Item item, String itemName) {
        if (itemNameCheckStartWith(itemName, "Backstage passes ")) {
            if (item.sellIn <= 0) {
                item.quality = 0;
                return;
            }

            if (isSellByDateLessThan(item, 6)) {
                if (isQualityLessThanFifty(item)) {
                    increaseQualityValue(item, 3);
                }
                return;
            }

            if (isSellByDateLessThan(item, 11)) {
                if (isQualityLessThanFifty(item)) {
                    increaseQualityValue(item, 2);
                }
                return;
            }
            if (item.sellIn > 10) {
                if (isQualityLessThanFifty(item)) {
                    increaseQualityValue(item, 1);
                }
            }

        }
    }

    private void isItemAgedBrie(Item item, String itemName) {
        if (itemNameCheckStartWith(itemName, "Aged Brie")) {
            if (item.sellIn <= 0) {
                increaseQualityValue(item, 2);
                return;
            }
            increaseQualityValue(item, 1);
        }
    }

    private boolean itemNameCheckStartWith(String itemName, String productName) {
        return itemName.startsWith(productName);
    }

    private boolean isSellByDateLessThan(Item item, int days) {
        return item.sellIn < days;
    }

    private boolean isQualityLessThanFifty(Item item) {
        return item.quality < 50;
    }

    private void increaseQualityValue(Item item, int rate) {
        item.quality += rate;
    }

}
