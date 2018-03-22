'''
common tricks to make your code more elegant
'''


def use_enumerate():
    a = ['a', 'b', 'c', 'd', 'e']
    for index, item in enumerate(a):
        print('{}:{}'.format(index, item))


def use_zip():
    a = ['a', 'b', 'c', 'd', 'e']
    b = ['1', '2', '3', '4', '5']

    for x, y in zip(a, b):
        print('{}:{}'.format(x, y))


def use_swap():
    a = 1
    b = 2

    a, b = b, a
    print('{}:{}'.format(a, b))


def use_for_else():
    pass


if __name__ == '__main__':
    use_enumerate()
    use_zip()
    use_swap()
