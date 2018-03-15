'''
    Consider a row of n houses represented by an array A[1...n], where the phrase
    'next door neighbor' has its natural meaning. Each resident is assigned a fun
    factor F[1..n], which means how much fun they can bring to the party. Your
    goal is select families to maximumaze the fun of a party with a contraint of
    not selecting three consecutive neighbors. (So for example, if you select A[5]
    and A[6], you can not select A[4] or A[7] any more).
    Give a efficient algorithm to select the guest list.
'''


def plan_party(A):
    length = len(A)
    guests = [[] for _ in range(0, length)]
    fun = [0 for _ in range(0, length)]
    guests[0] = [0]
    guests[1] = [0, 1]
    guests[2] = [0, 1, 2]
    fun[0] = 0
    fun[1] = A[1]
    fun[2] = A[1] + A[2]
    max = 2
    for i in range(3, length):
        temp1 = fun[i-2] + A[i]
        temp2 = fun[i-3] + A[i] + A[i-1]
        if temp2 > temp1:
            fun[i] = temp2
            guests[i] = guests[i-3] + [i-1, i]
        else:
            fun[i] = temp1
            guests[i] = guests[i-2] + [i]

        if fun[max] < fun[i]:
            max = i

    return fun[max], guests[max]


if __name__ == '__main__':
    A = [0, 2, 5, 7, 1, 8, 9, 7, 2, 12, 22, 33]
    fun, guests = plan_party(A)
    print('guests: {} have fun {}'.format(fun, guests))