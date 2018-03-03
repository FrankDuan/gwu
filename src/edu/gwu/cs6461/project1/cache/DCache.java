package edu.gwu.cs6461.project1.cache;


import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;

import java.util.HashMap;
import java.util.Map;


public class DCache implements Cache {
    static DCache _instance = null;
    static class Node {
        Node next;
        Node prev;
        short address;
        short value;

        Node(short address, short value) {
            this.address = address;
            this.value = value;
        }

        void update(short address, short value) {
            this.address = address;
            this.value = value;
        }
    }
    // 16 cache lines * 8 words
    private final int limit = 144;
    // record all the time the head and tail of this double linked list to make sure
    private Node head;
    private Node tail;

    private Map<Short, Node> map;

    private DCache() {
        this.map = new HashMap<Short, Node>();
    }

    static public Cache getInstance() {
        if (_instance == null) {
            _instance = new DCache();
        }
        return _instance;
    }
    @Override
    public void setValue(short address, short value) {
        Node node = null;
        if (map.containsKey(address)) {
            node = map.get(address);
            node.value = value;
            remove(node);
        } else if (map.size() <= limit) {
            node = new Node(address, value);
        } else {
            node = tail;
            remove(node);
            node.update(address, value);
        }
        append(node);
        // set the memory
        MemoryImpl.getInstance().setMemory(address, value);
    }

    @Override
    public short getValue(short address) {
        Node node = map.get(address);
        if (node != null) {
            remove(node);
            append(node);
            return node.value;
        } else {
            DCache.getInstance().setValue(address,
                    MemoryImpl.getInstance().getMemory(address));
            Node secCheck = map.get(address);
            remove(secCheck);
            append(secCheck);
            return secCheck.value;
        }

    }

    private void remove(Node node) {
        map.remove(node.address);
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == tail) {
            tail = tail.prev;
        }
        node.next = node.prev = null;
        return;
    }
    private void append(Node node) {
        map.put(node.address, node);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        return;
    }
}
