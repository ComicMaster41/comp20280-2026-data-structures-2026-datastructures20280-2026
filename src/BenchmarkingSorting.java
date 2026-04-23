import project20280.interfaces.Entry;
import project20280.interfaces.PriorityQueue;
import project20280.priorityqueue.HeapPriorityQueue;
import project20280.tree.Treap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BenchmarkingSorting {

    public static String fileToWriteTo = "CSVsSorting/test.csv";

    public static void main(String args[]){

        PQSortAndTreapTesting();

    }

    public static void PQSortAndTreapTesting(){
        int sizes[] = {1, 10, 100, 250, 500, 750, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
//            int sizes[] = {25000};
        for (int j = 0; j < sizes.length; j++) {
            int size = sizes[j];

            int value[] = new int[size];
            int keys[] = new int[size];

            for (int i = 0; i < size; i++) {
                value[i] = (int) (Math.random() * Integer.MAX_VALUE);
                keys[i] = (int) (Math.random() * Integer.MAX_VALUE);
            }
            fileToWriteTo = "CSVsSorting/PQRandom.csv";
            PQSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/treapRandom.csv";
            treap(keys,value,size);
            fileToWriteTo = "CSVsSorting/TimRandom.csv";
            collectionSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/mergeRandom.csv";
            mergeTest(keys,value,size);
            fileToWriteTo = "CSVsSorting/quickRandom.csv";
            quickTest(keys,value,size);
            System.out.println();

            for (int i = 0; i < size; i++) {
                value[i] = i;
                keys[i] = i;
            }


            for(int i = 0;i< (int)Math.log10(size);i++){
                int a = (int)(Math.random() * size);
                int b = (int)(Math.random() * size);

                int temp = keys[a];
                keys[a] =  keys[b];
                keys[b] = temp;
            }
            fileToWriteTo = "CSVsSorting/PQPartSort.csv";
            PQSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/treapPartSort.csv";
            treap(keys,value,size);
            fileToWriteTo = "CSVsSorting/TimPartSort.csv";
            collectionSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/mergePartSort.csv";
            mergeTest(keys,value,size);
            fileToWriteTo = "CSVsSorting/quickPartSort.csv";
            quickTest(keys,value,size);
            System.out.println();


            for (int i = 0; i < size; i++) {
                value[i] = size - i;
                keys[i] = size - i;
            }
            fileToWriteTo = "CSVsSorting/PQRevSort.csv";
            PQSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/treapRevSort.csv";
            treap(keys,value,size);
            fileToWriteTo = "CSVsSorting/TimRevSort.csv";
            collectionSort(keys,value,size);
            fileToWriteTo = "CSVsSorting/mergeRevSort.csv";
            mergeTest(keys,value,size);
            fileToWriteTo = "CSVsSorting/quickRevSort.csv";
            quickTest(keys,value,size);
            System.out.println();
        }
    }

    public static void treap(int keys[], int values[], int size){
        long sum = 0;
        for(int run = 0;run < 100;run++) {
            long start = System.nanoTime();

            Treap<Integer, Integer> tm = new Treap<>();
            for (int i = 0; i < size; i++) {
                tm.put(keys[i], values[i]);
            }

            int prev = Integer.MIN_VALUE;
            for (Integer i : tm.keySet()) {
                if (prev > i) {
                    System.out.println("error traversing in order");
                    break;
                }
                prev = i;
            }
            long end = System.nanoTime();
            sum += end-start;
        }
        printNumber(sum/100);
        nextEntry();
    }

    public static void collectionSort(int keys[],int values[], int size){
        long sum = 0;
        for(int run = 0;run < 100;run++) {
        List<Integer> l = new ArrayList<Integer>();

        for(int i : keys){
            l.addLast(i);
        }

        long start = System.nanoTime();

        Collections.sort(l);
        int prev = Integer.MIN_VALUE;
        for(int i : l){
            if(prev > i){
                System.out.println("error traversing the list");
                break;
            }
            prev = i;
        }
        long end = System.nanoTime();
            sum += end-start;
        }
        printNumber(sum/100);
        nextEntry();
    }

    public static void mergeTest(int keys[], int values[], int size){
        long sum = 0;
        for(int run = 0;run < 100;run++) {
        Integer a[] = new Integer[size];
        for(int i = 0;i<size;i++){
            a[i] = keys[i];
        }

        long start = System.nanoTime();
        mergeSort(a,Integer::compareTo);

        int prev = Integer.MIN_VALUE;
        for(int i : a){
            if(prev > i){
                System.out.println("error traversing the list");
                break;
            }
            prev = i;
        }
        long end = System.nanoTime();
        sum += end-start;
    }
    printNumber(sum/100);
    nextEntry();
    }

    public static void quickTest(int keys[], int values[], int size){
        long sum = 0;
        for(int run = 0;run < 100;run++) {
        Integer a[] = new Integer[size];
        for(int i = 0;i<size;i++){
            a[i] = keys[i];
        }

        long start = System.nanoTime();
        quickSort(a,Integer::compareTo);

        int prev = Integer.MIN_VALUE;
        for(int i : a){
            if(prev > i){
                System.out.println("error traversing the list");
                break;
            }
            prev = i;
        }
        long end = System.nanoTime();
            sum += end-start;
        }
        printNumber(sum/100);
        nextEntry();
    }

    public static void PQSort(int keys[], int values[], int size){
        long sum = 0;
        for(int run = 0;run < 100;run++) {
        long start = System.nanoTime();

        PriorityQueue<Integer,Integer> pq = new HeapPriorityQueue<>();

        for(int i = 0;i<size;i++){
            pq.insert(keys[i],values[i]);
        }

        int prev = Integer.MIN_VALUE;
        Entry<Integer, Integer> p = null;
        while((p = pq.removeMin()) != null){
            if(prev > p.getKey()){
                System.out.println("error traversing in order");
                break;
            }
            prev = p.getKey();
        }
        long end = System.nanoTime();
        sum += end-start;
    }
    printNumber(sum/100);
    nextEntry();
    }

    public static void printNumber(long num) {
        FileWriter fw;
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(fileToWriteTo,true));
            bw.write(num+"");
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

    public static <T extends Comparable<? super T>> void mergeSort( T[] data, final Comparator<T> comparator) {

        if(data.length < 2){
            return;
        }
        if(data.length == 2){
            if(comparator.compare(data[0], data[1]) > 0){
                T temp = data[0];
                data[0] = data[1];
                data[1] = temp;
            }
            return;
        }

        T[] one = Arrays.copyOfRange(data,0,data.length/2);
        T[] two = Arrays.copyOfRange(data,data.length/2,data.length);

        mergeSort(one,comparator);
        mergeSort(two,comparator);

        int i = 0;
        int j = 0;
        for(int ctr = 0; ctr < data.length;ctr++){
            if(i >= one.length){
                data[ctr] = two[j++];
                continue;
            }
            else if(j >= two.length){
                data[ctr] = one[i++];
                continue;
            }

            data[ctr] = (comparator.compare(one[i], two[j]) > 0) ? two[j++]:one[i++];
        }

    }

    public static <T extends Comparable<? super T>> void quickSort( T[] data, final Comparator<T> comparator) {
        quickSortHelper(data,comparator,0,data.length-1);

    }

    public static <T extends Comparable<? super T>> void quickSortHelper(T [] data, Comparator<T> comparator, int low, int high){
        if(low < high){
            int q = partition(data, low, high,comparator);
            quickSortHelper(data,comparator, low, q - 1);
            quickSortHelper(data, comparator,q + 1, high);
        }
    }

    public static <T extends Comparable<? super T>> int partition(T[] A, int low, int high, final Comparator<T> comparator){
//        int medianIndex = medianOfThree(A, p, r);

//        // move median to end (so your existing code still works)
//        int t = A[medianIndex];
//        A[medianIndex] = A[r];
//        A[r] = t;

        T pivot = A[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(A[j], pivot) <= 0) {
                i++;
                T temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        T temp = A[i + 1];
        A[i + 1] = A[high];
        A[high] = temp;
        return i + 1;
    }



}
