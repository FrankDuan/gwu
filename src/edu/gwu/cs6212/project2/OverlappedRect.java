package edu.gwu.cs6212.project2;

import java.util.*;


public class OverlappedRect {
    /**
     * In a 2 dimensional plane, a rectangle can be specified using the top left (north west)
     * and the bottom right (south east) points. Two rectangles are said to overlap, if there
     * is a common point that is contained in both of them. [If a rectangle is entirely contained
     * in another, they are still said to “overlap”, even though their lines don’t cross.] Given n
     * rectangles (using 2 points each), give an O(n log n) algorithm to determine if any two
     * rectangles from the list overlap.
     */
    public Integer size;
    public static final Integer MIN_GROUP_SIZE = 10;

    //The corresponding nodes in the two lists are overlapped.
    List<Integer> overlappedA = new ArrayList<>();
    List<Integer> overlappedB = new ArrayList<>();
    Rectangles rectangles;

    class Rectangles {
        Integer[] x_nw;
        Integer[] y_nw;
        Integer[] x_se;
        Integer[] y_se;
        Integer[] indexes;

        public Rectangles(int size) {
            x_nw = new Integer[size];
            y_nw = new Integer[size];
            x_se = new Integer[size];
            y_se = new Integer[size];
            indexes = new Integer[size];
        }
    }

    class Boundary {
        Integer leftBoundary;
        Integer rightBoundary;
    }

    public void init(Integer size) {
        this.size = size;
        rectangles = new Rectangles(size);
        Random rand = new Random();
        int upBound = 10000;
        for(int i = 0; i < size; i++) {
            rectangles.indexes[i] = i;
            rectangles.x_nw[i] = rand.nextInt(upBound);
            rectangles.y_se[i] = rand.nextInt(upBound);
            rectangles.x_se[i] = rectangles.x_nw[i] + 1
                                 + (int)(rand.nextGaussian() * 1);
            rectangles.y_nw[i] = rectangles.y_se[i] + 1
                                 + (int)(rand.nextGaussian() * 1);
        }
    }

    public static void main(String[] args) {
        int numOfRect = 10000;
        OverlappedRect rects = new OverlappedRect();
        rects.init(numOfRect);
        rects.getOverlappedRect(0, numOfRect - 1);
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
        System.out.printf("%d, %d, %d, %d %n", start, leftBoundary, rightBoundary, end);
        quickSort(rectangles.x_se, start, leftBoundary);
        //System.out.println(123);
        quickSort(rectangles.x_nw, rightBoundary, end);
        //System.out.println(456);
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
        int wt_pos = start;
        Boundary boundary = new Boundary();

        //Move rects in the left side of the pivot to the start of the array
        int tmp = start - 1;
        for(int i = end; i >= start; i--) {
            int index1 = rectangles.indexes[i];
            if (rectangles.x_se[index1] < pivot) {
                //The rectangle is in the left side of the pivot
                for (; wt_pos < i; wt_pos++) {
                    int index2 = rectangles.indexes[wt_pos];
                    if (rectangles.x_se[index2] >= pivot) {
                        break;
                    }
                    else {
                        tmp = wt_pos;
                    }
                }

                if (wt_pos >= i) {
                    tmp = i;
                    break;
                }

                rectangles.indexes[i] = rectangles.indexes[wt_pos];
                rectangles.indexes[wt_pos] = index1;
                wt_pos++;
            }
        }
        boundary.leftBoundary = tmp;

        //Move rects in the right side of the pivot to the end of the array
        tmp = end + 1;
        wt_pos = end;
        for(int i = boundary.leftBoundary + 1; i <= end; i++) {
            int index1 = rectangles.indexes[i];
            if (rectangles.x_nw[index1] > pivot) {
                //The rectangle is in the right side of the pivot
                //The rectangle is in the left side of the pivot
                for (; wt_pos > i; wt_pos--) {
                    int index2 = rectangles.indexes[wt_pos];
                    if (rectangles.x_nw[index2] <= pivot) {
                        break;
                    }
                    else {
                        tmp = wt_pos;
                    }
                }
                if (wt_pos <= i) {
                    tmp = i;
                    break;
                }
                rectangles.indexes[i] = rectangles.indexes[wt_pos];
                rectangles.indexes[wt_pos] = index1;
                wt_pos--;
            }
        }
        boundary.rightBoundary = tmp;
        return boundary;
    }

    Integer findOutMedian(Integer[] ref, Integer start, Integer end){
        int len = end - start + 1;
        Integer[] data = new Integer[len];
        for(int i = 0; i < len; i++) {
            data[i] = ref[rectangles.indexes[start+i]];
        }
        //System.arraycopy(ref, start, data, 0, data.length );
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
        //System.out.printf("%d, %d \n", start, end);
        int p = start;
        int len = end - start + 1;
        Integer[] data = new Integer[len];
        for(int i = 0; i < len; i++) {
            data[i] = ref[rectangles.indexes[start+i]];
        }
        int median = getMedian(data, 0, data.length - 1);

        int wt_pos = start;
        for(int i = end; i >= start; i--) {
            int index1 = rectangles.indexes[i];
            if (ref[index1] <= median) {
                //The rectangle is in the left side of the pivot
                for (; wt_pos < i; wt_pos++) {
                    int index2 = rectangles.indexes[wt_pos];
                    if (ref[index2] > median) {
                        break;
                    }
                    else {
                        p = wt_pos;
                    }
                }

                if (wt_pos >= i) {
                    p = i;
                    break;
                }

                rectangles.indexes[i] = rectangles.indexes[wt_pos];
                rectangles.indexes[wt_pos] = index1;
                wt_pos++;
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
            Arrays.sort(data, start, end + 1); //sort
            return data[start + k];
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
