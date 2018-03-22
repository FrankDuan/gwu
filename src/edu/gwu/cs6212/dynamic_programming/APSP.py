'''
Input: A weighted graph, represented by its weight matrix W.
Problem: Find the distance between every pair of nodes

The nodes are numbered 1..n
    1. D(k)(i,j) = Length of the shortest path from node i to node j using nodes
       {1..k} as intermediate
        nodes Note that this does not say you use k
        intermediate nodes. Rather, this says, you are allowed to use only the set
        of {1..k} nodes as intermediate nodes. You may use 1 or 2 or any number of
        intermediate nodes, but that is the only set of nodes that you are allowed
        to use as intermediate nodes.
            D(0)(i,j) = W[i,j]
    2. As discussed earlier, the portion of a shortest path must be a shortest
        path as well
    3. D(k)(i,j) either uses the node k or does not D(k)(i,j)=min{D(k-1)(i,j),
        D(k-1)(i,k) + D(k-1)(k,j)}

    4.  for i=1 to n do
            for j=1 to n do
                D(0)(i,j) := W[i,j]
        for k=1 to n do
            for i=1 to n do
                for j=1 to n do
                    D(k)(i,j)=min{D(k-1)(i,j), D(k-1)(i,k) + D(k-1)(k,j)}
'''


if __name__ == '__main__':
    pass