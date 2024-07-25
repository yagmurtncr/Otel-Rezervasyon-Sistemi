package com.example.otelrezervasyonsistemi;

public class MyQueue {
    private Node front;
    private Node back;
    private int size;
    private static class Node {
        String data;
        Node next;
        Node(String data) {
            this.data =data;
            this.next = null;
        }
    }
    public MyQueue() {
        front = null;
        back = null;
        size = 0;
    }
    public void Ekleme(String data) {
        Node newNode = new Node(data);
        if (back == null) {
            front = back = newNode;
        } else {
            back.next = newNode;
            back = newNode;
        }
        size++;
    }

    public String Cikarma() {
        if (front == null) {
            throw new RuntimeException("Queue is empty");
        }
        String data =front.data;
        front = front.next;
        if (front == null) {
            back = null;
        }
        size--;
        return data;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public String peek() {
        if (front == null) {
            throw new RuntimeException("Queue is empty");
        }
        return front.data;}}