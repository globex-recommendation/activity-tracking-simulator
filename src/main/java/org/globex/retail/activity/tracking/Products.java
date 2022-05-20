package org.globex.retail.activity.tracking;

import java.util.Arrays;
import java.util.Random;

public class Products {

    Random r = new Random();
         
    String[] products = {"329299","165614","444435","165613","165954","444437","RHNL-007","RHNL-011","RHNL-004","RHNL-008",
            "RHNAM-257","RHNAM-233","RHNAM-465","329199","444434","RHNL-006","RHNL-017","RHNL-002","RHNL-016","RHNL-005",
            "RHNL-015","RHNAM-023E-RED","RHNAM-234","RHNAM-231","RHNAM-232","RHNAM-255","RHNAM-179",
            "RHNAM-222","RHNAM-245","RHNAM-249","RHNAM-094-WHT","RHNAM-042E","RHNAM-239","RHNAM-250",
            "RHNAM-095-RED","RHNAM-240","RHNAM-054E-RED","RHNAM-252-BLK","RHNAM-219-BLKGRY","RHNL-018","RHNL-009"};

    String[] products11 = Arrays.copyOfRange(products, 0, 3);
    String[] products12 = Arrays.copyOfRange(products, 3, 13);
    String[] products13 = Arrays.copyOfRange(products, 13, 41);

    String[] products21 = Arrays.copyOfRange(products, 13, 16);
    String[] products22 = Arrays.copyOfRange(products, 16, 26);
    String[] products23 = arraycopy(Arrays.copyOfRange(products, 0, 13), Arrays.copyOfRange(products, 26, 41));

    String[] products31 = Arrays.copyOfRange(products, 26, 29);
    String[] products32 = Arrays.copyOfRange(products, 29, 39);
    String[] products33 = arraycopy(Arrays.copyOfRange(products, 0, 26), Arrays.copyOfRange(products, 39, 41));

    String[][] group1 = {products11, products12, products13};
    String[][] group2 = {products21, products22, products23};
    String[][] group3 = {products31, products32, products33};

    String[][][] groups = {{products11, products12, products13}, {products21, products22, products23}, {products31, products32, products33}};

    public String[][] group() {
        int i = r.nextInt(groups.length);
        return groups[i];
    }

    public String product(String[][] group) {
        String[] products = group[r.nextInt(group.length)];
        return products[r.nextInt(products.length)];
    }

    private <T> T[] arraycopy(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
