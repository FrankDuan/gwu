
'''
   LCS Problem Statement: Given two sequences, find the length of longest subsequence present in
   both of them. A subsequence is a sequence that appears in the same relative order, but not
   necessarily contiguous. For example, "abc", "abg", "bdf", "aeg", "acefg"... etc are
   subsequences of "abcdefg". So a string of length n has 2^n different possible subsequences.
   It is a classic computer science problem, the basis of diff (a file comparison program that
   outputs the differences between two files), and has applications in bioinformatics.
   Examples:
    LCS for input Sequences "ABCDGH" and "AEDFHR" is "ADH" of length 3.
    LCS for input Sequences "AGGTAB" and "GXTXAYB" is "GTAB" of length 4.

'''



def LCS(str1, str2):
    len1 = len(str1)
    len2 = len(str2)
    my_lcs = [[0 for _ in range(len2)] for _ in range(len1)]

    if str1[0] == str2[0]:
        initial = 1
    else:
        initial = 0

    for i in range(len1):
        my_lcs[i][0] = initial

    for i in range(1, len1):
        for j in range(1, i+1):
            if str1[i] == str2[j]:
                my_lcs[i][j] = 1 + my_lcs[i-1][j-1]
            else:
                my_lcs[i][j] = max(my_lcs[i-1][j], my_lcs[i][j-1])
    print(my_lcs)

    return my_lcs[len1-1][len2-1]


if __name__ == '__main__':

    str1 = "ABCDGH"
    str2 = "AEDFHR"

    print(LCS(str1, str2))