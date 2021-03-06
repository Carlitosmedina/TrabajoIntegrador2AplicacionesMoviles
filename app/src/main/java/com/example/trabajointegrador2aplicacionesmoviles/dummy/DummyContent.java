package com.example.trabajointegrador2aplicacionesmoviles.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    //private static final int COUNT = 25;

    static {
        // Add some sample items.
        //for (int i = 1; i <= COUNT; i++) {
        //   addItem(createDummyItem(i));
        addItem(new DummyItem("1", "Universidad Nacional Arturo Jauretche", "http://www.unaj.edu.ar"));
        addItem(new DummyItem("2", "Universidad Nacional de La Plata", "http://www.unlp.edu.ar"));
        addItem(new DummyItem("3", "Universidad Nacional de Quilmes", "http://www.unq.edu.ar/"));
        addItem(new DummyItem("4", "Universidad Nacional de La Matanza", "http://www.unlam.edu.ar/"));
        addItem(new DummyItem("5", "Universidad Nacional de Lanus", "http://www.unla.edu.ar/"));
        addItem(new DummyItem("6", "Universidad Nacional de Lomas de Zamora", "http://www.unlz.edu.ar/"));
        addItem(new DummyItem("7", "Universidad Nacional de San Martin", "http://www.unsam.edu.ar/"));
        addItem(new DummyItem("8", "Universidad Nacional de Moreno", "http://www.unm.edu.ar/"));
        addItem(new DummyItem("9", "Universidad Nacional de Almirante Brown", "http://www.unab.edu.ar/"));
        //}
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String website_name;
        public final String website_url;

        public DummyItem(String id, String website_name, String website_url) {
            this.id = id;
            this.website_name = website_name;
            this.website_url = website_url;
        }

        @Override
        public String toString() {
            return website_name;
        }
    }
}