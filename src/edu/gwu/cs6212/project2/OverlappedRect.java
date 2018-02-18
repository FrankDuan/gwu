package edu.gwu.cs6212.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class OverlappedRect {
    /**
     * In a 2 dimensional plane, a rectangle can be specified using the top left (north west)
     * and the bottom right (south east) points. Two rectangles are said to overlap, if there
     * is a common point that is contained in both of them. [If a rectangle is entirely contained
     * in another, they are still said to “overlap”, even though their lines don’t cross.] Given n
     * rectangles (using 2 points each), give an O(n log n) algorithm to determine if any two
     * rectangles from the list overlap.
     */
    public static final Integer SIZE = 1000;
    public static final Integer MIN_GROUP_SIZE = 10;

    //The corresponding nodes in the two lists are overlapped.
    List<Integer> overlappedA = new ArrayList<>();
    List<Integer> overlappedB = new ArrayList<>();
    Rectangles rectangles = new Rectangles();

    class Rectangles {
        Integer[] x_nw = new Integer[SIZE];
        Integer[] y_nw = new Integer[SIZE];
        Integer[] x_se = new Integer[SIZE];
        Integer[] y_se = new Integer[SIZE];

        Integer[] indexes = new Integer[SIZE];
    }

    class Boundary {
        Integer leftBoundary;
        Integer rightBoundary;
    }

    public void init() {

    }

    public static void main(String[] args) {
        OverlappedRect rects = new OverlappedRect();
        rects.init();
        rects.getOverlappedRect(0, SIZE - 1);
        rects.printOverlapped();
    }

    public void getOverlappedRect(Integer start, Integer end) {
        //if
        if(end - start <= MIN_GROUP_SIZE -1) {
            //find overlapped rectangles directly.
            getOverlappedRect3(start, end);
            return;
        }
        //Find median value
        Integer median = findOutMedian(rectangles.x_nw, start, end);

        //Divide the rectangles into three groups
        Boundary boundary = divideByPivot(start, end, median);

        // 1. rectangles on the left side of the median
        getOverlappedRect(start, boundary.leftBoundary);

        // 2. rectangles on the right side of the median
        getOverlappedRect(boundary.rightBoundary, end);

        // 3. rectangles cross the median
        getOverlappedRect2(start, end,boundary.leftBoundary, boundary.rightBoundary);
    }

    //
    void getOverlappedRect2(Integer start, Integer end, Integer leftBoundary, Integer rightBoundary){
        if(leftBoundary > rightBoundary){
            return;
        }

        /**
         *
         */
        quickSort(rectangles.x_se, start, leftBoundary);
        quickSort(rectangles.x_nw, rightBoundary, end);
        quickSort(rectangles.y_se,leftBoundary + 1, rightBoundary -1);

        for(int i = leftBoundary + 1; i <= rightBoundary - 1; i++){
            int index1 = rectangles.indexes[i];
            int x1 = rectangles.x_nw[index1];
            int y1 = rectangles.y_nw[index1];
            int x2 = rectangles.x_se[index1];
            int y2 = rectangles.y_se[index1];

            //Check if current rectangle overlapped with rectangle on the left side.
            for(int j = leftBoundary; j >= start; j--) {
                int index2 = rectangles.indexes[j];
                if(x1 <= rectangles.x_se[index2]){
                    isOverlapped(i, j);
                }
                else {
                    break;
                }
            }

            //Check if current rectangle overlapped with rectangle on the right side.
            for(int j = rightBoundary; j <= end; j ++){
                int index2 = rectangles.indexes[j];
                if(x2 >= rectangles.x_nw[index2]){
                    isOverlapped(i, j);
                }
                else {
                    break;
                }
            }

            //Check if current rectangle overlapped with rectangle on the right side.
            for(int j = i + 1; j < rightBoundary; j ++){
                int index2 = rectangles.indexes[j];
                if(y1 >= rectangles.y_se[index2]){
                    isOverlapped(i, j);
                }
                else{
                    break;
                }
            }
        }
    }

    void getOverlappedRect3(Integer start, Integer end){
        for(int i = start; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                isOverlapped(i, j);
            }
        }
    }

    Boundary divideByPivot(Integer start, Integer end, Integer pivot) {
        int tmp = start - 1;
        Boundary boundary = new Boundary();
        ArrayList<Integer> wtList = new ArrayList<>();

        //Move rects in the left side of the pivot to the start of the array
        for(int i = start; i <= end; i++) {
            int index1 = rectangles.indexes[i];

            if (rectangles.x_se[index1] < pivot) {
                //The rectangle is in the left side of the pivot
                tmp = i;
                if(wtList.size() > 0){
                    int wt_pos = wtList.get(0);
                    if (wt_pos < i) {
                        rectangles.indexes[i] = rectangles.indexes[wt_pos];
                        rectangles.indexes[wt_pos] = index1;
                        tmp = wt_pos;
                        wtList.remove(0);
                    }
                }
            } else {
                wtList.add(i);
            }
        }
        boundary.leftBoundary = tmp;

        //Move rects in the right side of the pivot to the end of the array
        wtList.clear();
        tmp = end + 1;
        for(int i = end; i <= start; i--) {
            int index1 = rectangles.indexes[i];
            if (rectangles.x_nw[index1] > pivot) {
                //The rectangle is in the right side of the pivot
                tmp = i;
                if(wtList.size() > 0){
                    int wt_pos = wtList.get(0);
                    if (wt_pos < i) {
                        rectangles.indexes[i] = rectangles.indexes[wt_pos];
                        rectangles.indexes[wt_pos] = index1;
                        tmp = wt_pos;
                        wtList.remove(0);
                    }
                }
            } else {
                wtList.add(i);
            }
        }
        boundary.rightBoundary = tmp;
        return boundary;
    }

    Integer findOutMedian(Integer[] ref, Integer start, Integer end){
        Integer[] data = new Integer[end - start + 1];
        System.arraycopy(ref, start, data, 0, data.length );
        return getMedian(data, 0, data.length - 1);
    }

    Boolean isOverlapped(Integer rect1, Integer rect2){
        int index1 = rectangles.indexes[rect1];
        int index2 = rectangles.indexes[rect2];

        if((rectangles.x_se[index1] < rectangles.x_nw[index2]) //rect1 in the left side of rect2
            || (rectangles.y_se[index1] > rectangles.y_nw[index2]) //rect1 in the top side of rect2
            || (rectangles.x_se[index2] < rectangles.x_nw[index1]) //rect2 in the left side of rect1
            || (rectangles.y_se[index2] > rectangles.y_nw[index1])) { //rect2 in the top side of rect1
            return false;
        }
        else {
            overlappedA.add(index1);
            overlappedB.add(index2);
            return true;
        }
    }

    void quickSort(Integer[] ref, Integer start, Integer end){
        if(start < end){
            int p = partition(ref, start, end);
            quickSort(ref, start, p - 1);
            quickSort(ref, p + 1, end);
        }
    }

    int partition(Integer[] ref, Integer start, Integer end){
        int p = 0;
        Integer[] data = new Integer[end - start + 1];
        System.arraycopy( ref, start, data, 0, data.length );
        int median =  getMedian(data, 0, data.length - 1);

        ArrayList<Integer> wtList = new ArrayList<>();
        for(int i = start; i <= end; i++) {
            int index1 = rectangles.indexes[i];

            if (ref[index1] < median) {
                //The rectangle is in the left side of the pivot
                p = i;
                if(wtList.size() > 0){
                    int wt_pos = wtList.get(0);
                    if (wt_pos < i) {
                        rectangles.indexes[i] = rectangles.indexes[wt_pos];
                        rectangles.indexes[wt_pos] = index1;
                        p = wt_pos;
                        wtList.remove(0);
                    }
                }
            } else {
                wtList.add(i);
            }
        }
        return p;
    }

    void printOverlapped() {
        Iterator<Integer> it1 = overlappedA.iterator();
        Iterator<Integer> it2 = overlappedB.iterator();
        while(it1.hasNext() && it2.hasNext()) {
            int i = it1.next();
            int j = it2.next();
            System.out.printf("(%d:%d, %d:%d)--(%d:%d, %d:%d)%n",
                                rectangles.x_nw[i], rectangles.y_nw[i],
                                rectangles.x_se[i], rectangles.y_se[i],
                                rectangles.x_nw[j], rectangles.y_nw[j],
                                rectangles.x_se[j], rectangles.y_se[j]);
        }
    }

    int getMedian(Integer[] data, int start, int end)
    {
        int k = (start + end) / 2;
        // 小于等于5个元素 直接排序输出结果
        if(end - start + 1 <= 5) {
            Arrays.sort(data);
            return data[start + k - 1];
        }

        //  1 首先把数组按5个数为一组进行分组，最后不足5个的忽略。
        //    对每组数进行排序（如插入排序）求取其中位数。
        //  2 把上一步的所有中位数移到数组的前面
        int t = start;
        int cnt = (end - start + 1) / 5;
        for(int i = 0;i < cnt; i++) {
            Arrays.sort(data, start+i*5, start + (i+1) *5);
            int tmp = data[start + i * 5 + 2];
            data[start + i * 5 + 2] = data[t];
            data[t] = tmp;
            t++;
        }
        t--;

        //  3 对这些中位数递归调用BFPRT算法求得他们的中位数
        int pos = (start + t) / 2; // l-t的中位数的下标， 中位数是第 pos - l + 1数
        return getMedian(data,start,t); // 递归查找中位数的中位数,确保中位数在pos这个位置
    }
}
