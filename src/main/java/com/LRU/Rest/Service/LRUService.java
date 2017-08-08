package com.LRU.Rest.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUService {
    int capacity;
    private ConcurrentHashMap<Integer, Node> map = new ConcurrentHashMap<Integer, Node>();
    Node head = null;
    Node end = null;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    public LRUService(int capacity) {
        this.capacity = capacity;
    }

    public int getValueByKey(int key) {
        readLock.lock();
        Node n = null;
        try {
            if (map.containsKey(key)) {
                n = map.get(key);
                return n.value;
            }
            return -1;
        } finally {
            if(n!=null){
                remove(n);
                setHead(n);
            }
            readLock.unlock();
        }
    }

    public void remove(Node n) {
        writeLock.lock();
        try {
            if (n.pre != null) {
                n.pre.next = n.next;
            } else {
                head = n.next;
            }

            if (n.next != null) {
                n.next.pre = n.pre;
            } else {
                end = n.pre;
            }
        } finally {
            writeLock.unlock();
        }

    }

    public void setHead(Node n) {
        writeLock.lock();
        try {
            n.next = head;
            n.pre = null;

            if (head != null)
                head.pre = n;

            head = n;

            if (end == null)
                end = head;
        } finally {
            writeLock.unlock();
        }
    }

    public void setValueByKey(int key, int value) {
        writeLock.lock();
        try {
            if (map.containsKey(key)) {
                Node old = map.get(key);
                old.value = value;
                remove(old);
                setHead(old);
            } else {
                Node created = new Node(key, value);
                if (map.size() >= capacity) {
                    map.remove(end.key);
                    remove(end);
                    setHead(created);

                } else {
                    setHead(created);
                }

                map.put(key, created);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
