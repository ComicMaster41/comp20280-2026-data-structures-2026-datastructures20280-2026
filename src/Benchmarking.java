import project20280.tree.AVLTreeMap;
import project20280.tree.Treap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
public class Benchmarking {

    public static String fileToWriteTo = "test.csv";
    public static void main(String args[]) {
        System.out.println("Tree map testing");
        TreeMapTesting();
        System.out.println("\n\nAVL Tree map testing");
        AVLTreeMapTesting();
        System.out.println("\n\nTreap Testing");
        treapTesting();
    }

    public static void TreeMapTesting() {
        int sizes[] = {1, 10, 100, 250, 500, 750, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        System.out.println(" \t\tinsert\t\tsearch\t\tusearch\t\ttrav\t\tdelete");
        for (int j = 0; j < sizes.length; j++) {
            int size = sizes[j];

            int value[] = new int[size];
            int keys[] = new int[size];

            for (int i = 0; i < size; i++) {
                value[i] = (int) (Math.random() * Integer.MAX_VALUE);
                keys[i] = (int) (Math.random() * Integer.MAX_VALUE);
            }
            fileToWriteTo = "CSVs/treeMapRandom.csv";
            TreeMapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = i;
                keys[i] = i;
            }
            fileToWriteTo = "CSVs/treeMapSorted.csv";
            TreeMapHelper(keys,value,size);

            for(int i = 0;i< (int)Math.log10(size);i++){
                int a = (int)(Math.random() * size);
                int b = (int)(Math.random() * size);

                int temp = keys[a];
                keys[a] =  keys[b];
                keys[b] = temp;
            }
            fileToWriteTo = "CSVs/treeMapPartSort.csv";
            TreeMapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = size - i;
                keys[i] = size - i;
            }
            fileToWriteTo = "CSVs/treeMapRevSort.csv";
            TreeMapHelper(keys,value,size);
        }
    }

    public static void TreeMapHelper(int keys[], int value[], int size){
        long start,end;

        TreeMap<Integer, Integer> tm = new TreeMap<>();
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tm.put(keys[i], value[i]);
        }
        end = System.nanoTime();
        System.out.print(size + ":\t");
        if(size<100) System.out.print("\t");
        printNumber(end-start);

        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            tm.get(keys[i]);
        }
        end = System.nanoTime();
        printNumber(end-start);

        int successes = 0;
        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            if (tm.get(keys[i]+1) != null) successes++;
        }
        end = System.nanoTime();
        printNumber(end-start);
//        if(successes!= 0) System.out.println(successes);

        int prev  = Integer.MIN_VALUE;
        start = System.nanoTime();
        for(Integer i : tm.keySet()){
            if(prev > i){
                System.out.println("error traversing in order");
                break;
            }
            prev = i;
        }
        end = System.nanoTime();
        printNumber(end-start);

        //shuffling list and then deleting the elements
        List<Integer> keyList = new ArrayList<>(tm.keySet().stream().toList());
        Collections.shuffle(keyList);

        start = System.nanoTime();
        for(int k : keyList){
            tm.remove(k);
        }
        end = System.nanoTime();
        printNumber(end-start);

        System.out.println();
        nextEntry();
    }


    public static void AVLTreeMapTesting() {
        int sizes[] = {1, 10, 100, 250, 500, 750, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        System.out.println(" \t\tinsert\t\tsearch\t\tusearch\t\ttrav\t\tdelete");

        for (int j = 0; j < sizes.length; j++) {
            int size = sizes[j];

            int value[] = new int[size];
            int keys[] = new int[size];

            for (int i = 0; i < size; i++) {
                value[i] = (int) (Math.random() * Integer.MAX_VALUE);
                keys[i] = (int) (Math.random() * Integer.MAX_VALUE);
            }
            fileToWriteTo = "CSVs/AVLTreeMapRandom.csv";
            AVLTreeMapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = i;
                keys[i] = i;
            }
            fileToWriteTo = "CSVs/AVLTreeMapSort.csv";
            AVLTreeMapHelper(keys,value,size);

            for(int i = 0;i< (int)Math.log10(size);i++){
                int a = (int)(Math.random() * size);
                int b = (int)(Math.random() * size);

                int temp = keys[a];
                keys[a] =  keys[b];
                keys[b] = temp;
            }
            fileToWriteTo = "CSVs/AVLTreeMapPartSort.csv";
            AVLTreeMapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = size - i;
                keys[i] = size - i;
            }
            fileToWriteTo = "CSVs/AVLTreeMapRevSort.csv";
            AVLTreeMapHelper(keys,value,size);

        }
    }

    public static void AVLTreeMapHelper(int keys[], int value[], int size){

        AVLTreeMap<Integer, Integer> tm = new AVLTreeMap<>();
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tm.put(keys[i], value[i]);
        }
        long end = System.nanoTime();

        System.out.print(size + ":\t");
        if(size<100) System.out.print("\t");
        printNumber(end-start);


//            System.out.println("inserting " + size + " elements: " + (end - start) +" nano seconds");

        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            tm.get(keys[i]);
        }
        end = System.nanoTime();
//            System.out.println("searching successful "+ size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);



        int successes = 0;
        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            if (tm.get(keys[i]+1) != null) successes++;
        }
        end = System.nanoTime();
//            System.out.println("searching unsuccessful "+ size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);


//        if(successes!= 0) System.out.println(successes);

        int prev  = Integer.MIN_VALUE;
        start = System.nanoTime();
        for(Integer i : tm.keySet()){
            if(prev > i){
                System.out.println("error traversing in order");
                break;
            }
            prev = i;
        }
        end = System.nanoTime();
//            System.out.println("traversing " + size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);



        //shuffling list and then deleting the elements
        List<Integer> keyList = new ArrayList<>();
        for(int k : tm.keySet()){
            keyList.add(k);
        }
        Collections.shuffle(keyList);

        start = System.nanoTime();
        for(int k : keyList){
            try {
                tm.remove(k);
            }
            catch (IllegalArgumentException e){
                System.out.println(tm.toBinaryTreeString());
                System.out.println("k: " + k);
                e.printStackTrace();
                System.exit(-1);
            }
            catch(NullPointerException e){
                System.out.println();
                System.out.println(tm.toBinaryTreeString());
                System.out.println("k: " + k);
                e.printStackTrace();
                System.exit(-1);
            }
        }
        end = System.nanoTime();
//            System.out.println("deleting " + size + " elements: "+ (end - start) + " nano seconds");
        printNumber(end-start);



        nextEntry();
        System.out.println();
    }


    public static void treapTesting() {
        int sizes[] = {1, 10, 100, 250, 500, 750, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        System.out.println(" \t\tinsert\t\tsearch\t\tusearch\t\ttrav\t\tdelete");

        for (int j = 0; j < sizes.length; j++) {
            int size = sizes[j];

            int value[] = new int[size];
            int keys[] = new int[size];

            for (int i = 0; i < size; i++) {
                value[i] = (int) (Math.random() * Integer.MAX_VALUE);
                keys[i] = (int) (Math.random() * Integer.MAX_VALUE);
            }
            fileToWriteTo = "CSVs/treapRandom.csv";
            treapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = i;
                keys[i] = i;
            }
            fileToWriteTo = "CSVs/treapSort.csv";
            treapHelper(keys,value,size);

            for(int i = 0;i< (int)Math.log10(size);i++){
                int a = (int)(Math.random() * size);
                int b = (int)(Math.random() * size);

                int temp = keys[a];
                keys[a] =  keys[b];
                keys[b] = temp;
            }
            fileToWriteTo = "CSVs/treapPartSort.csv";
            treapHelper(keys,value,size);

            for (int i = 0; i < size; i++) {
                value[i] = size - i;
                keys[i] = size - i;
            }
            fileToWriteTo = "CSVs/treapRevSort.csv";
            treapHelper(keys,value,size);

        }
    }

    public static void treapHelper(int keys[], int value[], int size){
        Treap<Integer, Integer> tm = new Treap<>();
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tm.put(keys[i], value[i]);
        }
        long end = System.nanoTime();

        System.out.print(size + ":\t");
        if(size<100) System.out.print("\t");
        printNumber(end-start);

//            System.out.println("inserting " + size + " elements: " + (end - start) +" nano seconds");

        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            tm.get(keys[i]);
        }
        end = System.nanoTime();
//            System.out.println("searching successful "+ size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);



        int successes = 0;
        start = System.nanoTime();
        for(int i = 0;i<size;i++){
            if (tm.get(keys[i]+1) != null) successes++;
        }
        end = System.nanoTime();
//            System.out.println("searching unsuccessful "+ size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);


//        if(successes!= 0) System.out.println(successes+"collisions");

        int prev  = Integer.MIN_VALUE;
        start = System.nanoTime();
        for(Integer i : tm.keySet()){
            if(prev > i){
                System.out.println("error traversing in order");
                break;
            }
            prev = i;
        }
        end = System.nanoTime();
//            System.out.println("traversing " + size + " elements: " + (end-start) + " nano seconds");
        printNumber(end-start);



        //shuffling list and then deleting the elements
        List<Integer> keyList = new ArrayList<>();
        for(int k : tm.keySet()){
            keyList.add(k);
        }
        Collections.shuffle(keyList);

        start = System.nanoTime();
        for(int k : keyList){
            try {
                tm.remove(k);
            }
            catch (IllegalArgumentException e){
                System.out.println(tm.toBinaryTreeString());
                System.out.println("k: " + k);
                e.printStackTrace();
                System.exit(-1);
            }
        }
        end = System.nanoTime();
//            System.out.println("deleting " + size + " elements: "+ (end - start) + " nano seconds");
        printNumber(end-start);


        System.out.println();
        nextEntry();
    }

    public static void printNumber(long num) {
        FileWriter fw;
        BufferedWriter bw;
        try {
           bw = new BufferedWriter(new FileWriter(fileToWriteTo,true));
            bw.write(num+",");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.print(num + "\t");
        if(num < 10000000) System.out.print("\t");
        if(num < 1000) System.out.print("\t");
    }
    public static void nextEntry(){
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(fileToWriteTo, true));
            bw.write("\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

