'''
Queue
'''


from Queue import Queue

if __name__ == '__main__':
    q = Queue()
    q.put(1)
    q.put(2)
    q.put(3)
    print(q.qsize())
    print(q.get())
    print(q.get())
    print(q.get())
    print(q.get())
