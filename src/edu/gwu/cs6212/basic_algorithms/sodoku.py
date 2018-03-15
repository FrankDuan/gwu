'''
A standard Sudoku contains 81 cells, in a 9x9 grid, and has 9 boxes, each box
being the intersection of the first, middle, or last 3 rows, and the first,
middle, or last 3 columns. Each cell may contain a number from one to nine,
and each number can only occur once in each row, column, and box. A Sudoku
starts with some cells containing numbers (clues), and the goal is to solve the
remaining cells. Proper Sudokus have one solution. Players and investigators
may use a wide range of computer algorithms to solve Sudokus, study their
properties, and make new puzzles, including Sudokus with interesting symmetries
and other properties.

There are several computer algorithms that will solve most 9x9 puzzles (n=9) in
fractions of a second, but combinatorial explosion occurs as n increases,
creating limits to the properties of Sudokus that can be constructed, analyzed,
and solved as n increases.
'''

'''
solution:
    traverse the matrix, find a blank which has least possibilities.
    If there is only one possible value, set the blank to that value.
    If there is more than one, duplicate the matrix, each with one possible value
    check every no blank value, if is illegal, remove the matrix.
    recursion.
'''

def find_all_cleared(a_list):
    all_clear = True
    for a in a_list:
        for row in a:
            for col in row:
                if col == 0:
                    all_clear = False
                    break
        if all_clear:
            return a
    return None

def solve(a_list):
    a = find_all_cleared(a_list)
    if a is not None:
        return a




    return solve(a_list)

if __name__ == '__main__':
    A = [
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0]
    ]

    init_list = [A]
    B = solve(a_list)

    print(B)