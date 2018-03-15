'''

An element in a sorted array can be found in O(log n) time via binary search.
But suppose we rotate an ascending order sorted array at some pivot unknown to
you beforehand. So for instance, 1 2 3 4 5 might become 3 4 5 1 2. Devise a way
to find an element in the rotated array in O(log n) time.

Input  : arr[] = {5, 6, 7, 8, 9, 10, 1, 2, 3};
         key = 3
Output : Found at index 8

Input  : arr[] = {5, 6, 7, 8, 9, 10, 1, 2, 3};
         key = 30
Output : Not found

Input : arr[] = {30, 40, 50, 10, 20}
        key = 10
Output : Found at index 3

'''


def brute_force_search(arr, start, end, key):
    for i in range(start, end):
        if arr[i] == key:
            return i

    return -1


def search_pivot_array(arr, start, end, key):
    length = end - start
    if length <= 3:
        return brute_force_search(arr, start, end, key)
    mid = (start + end) / 2

    if arr[mid] == key:
        return mid

    if arr[start] <= arr[mid]:
        if arr[start] <= key <= arr[mid]:
            return search_pivot_array(arr, start, mid, key)
        else:
            return search_pivot_array(arr, mid, end, key)
    else:
        if arr[mid] <= key <= arr[end-1]:
            return search_pivot_array(arr, mid, end, key)
        else:
            return search_pivot_array(arr, start, mid, key)


if __name__ == '__main__':
    arr = [5, 6, 7, 8, 9, 10, 1, 2, 3]
    key = 3
    print(search_pivot_array(arr, 0, len(arr), key))

    arr = [30, 40, 50, 10, 20]
    key = 10
    print(search_pivot_array(arr, 0, len(arr), key))