'''
Given a string containing only English character, find the first unique
character in the string, upper and lower cases should not be treated as the same
character.
'''


'''
solution:
    fast_set = set()
    loop every char in string
        if char in fast_set(): continue
        if occur only once: return char
        else: put char to fast_set()
'''
def find_unique(a):
    fast_set = set()
    length = len(a)

    for i in range(length):
        unique = True
        if a[i] in fast_set:
            continue
        for j in range(length):
            if j == i:
                continue
            if a[i] == a[j]:
                fast_set.add(a[i])
                unique = False
                break
        if unique:
            return a[i]


if __name__ == '__main__':
    a = 'aGreekfGreeks'
    print('{} -> {}'.format(a, find_unique(a)))