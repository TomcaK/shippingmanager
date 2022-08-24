package cz.comkop.shipingmanager;

import java.util.ArrayList;

public class SelectedItems {
    private ArrayList<ItemTemplate> selectedItemTemplates = new ArrayList<>();

    /**
     * Method takes selected types of itemTemplates and their quantity and adds them to ArrayList.
     *
     * @param itemTemplates
     */
   /* public void createSelectedItems(ArrayList<ItemTemplate> itemTemplates) {

        for (int i = 0; i < itemTemplates.size(); i++) {
            for (int j = 0; j < itemTemplates.get(i).getQuantity(); j++) {
                selectedItemTemplates.add(new ItemTemplate(itemTemplates.get(i)));
                selectedItemTemplates.get(selectedItemTemplates.size() - 1).setQuantity(itemTemplates.get(i).getQuantity());
            }
        }

    }*/

    public void searchItemsFromInput(ArrayList<ItemTemplate> itemTemplates, String itemsChoice) {
        String[] arrItemsChoice = itemsChoice.split("\\s");
        for (String s : arrItemsChoice) {
            String[] separate = s.split("\\.");
            itemTemplates.get(Integer.parseInt(separate[0]) - 1).setQuantity(Integer.parseInt(separate[1]));
        }
    }

    private void sortSelectedItemsByDimension() {
        ArrayList<ItemTemplate> sortArray = new ArrayList<>();
        int maxSize = 0;
        while (selectedItemTemplates.size() != 0) {
            for (int i = 0; i < selectedItemTemplates.size(); i++) {
                if (selectedItemTemplates.get(i).getWidth() * selectedItemTemplates.get(i).getLength() > maxSize) {
                    maxSize = selectedItemTemplates.get(i).getWidth() * selectedItemTemplates.get(i).getLength();
                }
            }
            for (int i = 0; i < selectedItemTemplates.size(); i++) {
                if (maxSize == selectedItemTemplates.get(i).getWidth() * selectedItemTemplates.get(i).getLength()) {
                    sortArray.add(selectedItemTemplates.get(i));
                    selectedItemTemplates.remove(i);
                    i--;
                }
            }
            maxSize = 0;
        }
        selectedItemTemplates = sortArray;

    }

    private void createPackFromItems(Trailer trailer) {
        int rest = 0;
        int pack = 0;
        for (int i = 0; i < selectedItemTemplates.size(); ) {
            pack = trailer.getTotalWidth() / selectedItemTemplates.get(i).getWidth();
            rest = selectedItemTemplates.get(i).getQuantity() % pack;
            for (int j = i; j < i + selectedItemTemplates.get(i).getQuantity(); j++) {
                if (j < i + rest) {
                    selectedItemTemplates.get(i).setLoadLast(true);
                }
            }
            i += selectedItemTemplates.get(i).getQuantity();
        }
    }

    public void sorting(Trailer trailer) {
        sortSelectedItemsByDimension();
        createPackFromItems(trailer);
    }

    public ArrayList<ItemTemplate> getSelectedItems() {
        return selectedItemTemplates;
    }
}
