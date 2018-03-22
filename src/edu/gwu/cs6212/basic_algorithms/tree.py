'''
1. left view of a tree

Given a Binary Tree, print left view of it. Left view of a Binary Tree is set of
nodes visible when tree is visited from left side.

Input :
                 5
               /   \
              2     6
             / \     \
            1   3     7
Output : 1 2 5

Input :
        1
      /   \
    2       3
      \
        4
          \
            5
             \
               6

Output :1 2 4 5 6

2. Given a binary tree test if it is a binary search tree

3. Find inorder successor of a number in binary search tree

'''


def max_depth(node):
    if node is None:
        return 0
    l = max_depth(node.left)
    r = max_depth(node.right)

    if l >= r:
        return l + 1
    else:
        return r + 1


def traversal(node_list, out_list):
    if len(node_list) == 0:
        return

    list_of_next_level = []
    for node in node_list:
        out_list.append(node)
        if node.right is not None:
            list_of_next_level.append(node.right)
        if node.left is not None:
            list_of_next_level.append(node.left)

    traversal(list_of_next_level, out_list)

class Node:

    def __init__(self, value, left=None, right=None):
        self.value = value
        self.left = left
        self.right = right

    def left_view(self, elements, current_depth=0):
        if len(view_elements) <= current_depth:
            elements.append(self.value)

        if self.left is not None:
            self.left.left_view(elements, current_depth+1)

        if self.right is not None:
            self.right.left_view(elements, current_depth+1)

    def is_bst(self):
        if self.left is not None:
            if self.left.value > self.value:
                return False
            if not self.left.is_bst():
                return False

        if self.right is not None:
            if self.right.value < self.value:
                return False

            if not self.right.is_bst():
                return False

        return True

    def get_successor(self,  value):
        if self.left is not None:
            self.left.get_successor(value)

        if self.value >= value:
            return self.value

        if self.right is not None:
            self.right.get_successor(value)



if __name__ == '__main__':
    root = Node(5, Node(2), Node(6))
    left = root.left
    left.right = Node(3)
    left.left = Node(1)
    right = root.right
    right.right = Node(7)
    view_elements = []
    root.left_view(view_elements)
    print(view_elements)
    print(root.is_bst())
    print(root.get_successor(4))

    root = Node(1, Node(2), Node(3))
    node = root.left
    node.right = Node(4)
    node = node.right
    node.right = Node(5)
    node = node.right
    node.right = Node(6)

    view_elements = []
    root.left_view(view_elements)
    print(view_elements)
    print(root.is_bst())

    print('Depth: {}'.format(max_depth(root)))

    result = []
    traversal([root], result)
    while len(result) > 0:
        print(result.pop().value)

