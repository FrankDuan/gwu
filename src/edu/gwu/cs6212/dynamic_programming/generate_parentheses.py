

def generate_parentheses(n, sequence, result, left_left, credit=0):
    if n == 0:
        result.append(sequence)
        return
    if credit > 0:
        generate_parentheses(n-1, sequence+')', result, left_left, credit-1)

    if left_left > 0:
        generate_parentheses(n-1, sequence+'(', result, left_left-1, credit+1)


pairs = 5
result = []
generate_parentheses(2*pairs, '', result, pairs)
print(result)