'''
There is N clients and K server,
choose a

'''

import sys

def optimal_placement(clients, server_number):
    #first item is not used.
    client_number = len(clients) - 1

    traffic = [[0 for _ in range(client_number+1)]
               for _ in range(client_number+1)]

    for i in range(1, client_number+1):
        for j in range(1, client_number+1):
            traffic[i][j] = clients[i] * abs(j-i)

    for temp in traffic:
        print(' '.join('{:>2}'.format(k) for k in temp))

    placement = [[0 for _ in range(client_number+1)]
                 for _ in range(client_number+1)]

    position = [[0 for _ in range(client_number+1)]
                for _ in range(client_number+1)]

    print('\n Placement Matrix: \n')
    for i in range(1, client_number+1):
        for j in range(i, client_number+1):
            placement[i][j] = place_one_server(traffic, i, j, position)

    k_means = [[0 for _ in range(server_number+1)]
               for _ in range(client_number+1)]

    '''
    k_means[i][m] is the optimal traffic of i clients in m clusters
    Then we have:
    k_means[i][m] = Min {k_means[j-1][m-1] + placement[j][i]}, j = m to i   
    '''

    for i in range(1, client_number+1):
        k_means[i][1] = placement[1][i]

    for m in range(2, server_number+1):
        for i in range(m, client_number+1):
            min_value = sys.maxint
            div_pos = m
            for j in range(m, i+1):
                temp = k_means[j-1][m-1] + placement[j][i]
                if min_value > temp:
                    min_value = temp
                    div_pos = j
            k_means[i][m] = min_value
            print('[{}, {}] div pos {}.{}'.format(m, i, div_pos, min_value))

    print('\n Placement:')
    for temp in placement:
        print(' '.join('{:>4}'.format(k) for k in temp))
    print('\n Position:')
    for temp in position:
        print(' '.join('{:>4}'.format(k) for k in temp))

    print('\n K means:')
    for temp in k_means:
        print(' '.join('{:>4}'.format(k) for k in temp))


def place_one_server(traffic, start, end, position):
    min_traffic = sys.maxint
    length = end - start + 1

    pos = 0
    for i in range(start, start+length):
        traffics = 0
        for j in range(start, start+length):
            traffics += traffic[j][i]
        if min_traffic > traffics:
            min_traffic = traffics
            pos = i
    position[start][end] = pos
    return min_traffic


if __name__ == '__main__':
    # first item is not used.
    client_trafic = [0, 3, 5, 7, 2, 4, 22, 8, 9, 7, 2]
    server_number = 6
    optimal_placement(client_trafic, server_number)