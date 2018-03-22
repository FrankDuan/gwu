'''
word puzzle

Give a list of words, which has a length of n
select n words, so

'''


def arrangement(list_a, list_b, index, length):
    if index >= length:
        print(list_b)
        return

    for node in list_a:
        if node in list_b[0:index]:
            continue

        list_b[index] = node
        arrangement(list_a, list_b, index+1, length)


def combination(list_a, list_b, index, length):
    if index >= length:
        print(list_b)
        return
    len_a = len(list_a)
    for i in range(len_a):
        if list_a[i] in list_b[0:index]:
            continue
        list_b[index] = list_a[i]
        combination(list_a[i+1:len_a], list_b, index+1, length)


if __name__ == '__main__':
    a = [1, 2, 3, 4, 5, 6]
    b = [0, 0, 0, 0, 0]
    print('Arrangement:')
    arrangement(a, b, 0, len(b))
    print('Combination:')
    combination(a, b, 0, len(b))
