package com.keyboard.keyboardthemes;

import android.view.MotionEvent;

class Next_Traacker_KeyWord {

    private static final int NUM_PAST = 4;
    private static final int LONGEST_PAST_TIME = 200;
    final EventRingBuffer eventbuf = new EventRingBuffer(NUM_PAST);
    private float flotekeyword;
    private float tracker;

    public void addMovement(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            eventbuf.clear();
            return;
        }
        long watch = event.getEventTime();
        final int number = event.getHistorySize();
        for (int i = 0; i < number; i++) {
            addPoint(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalEventTime(i));
        }
        addPoint(event.getX(), event.getY(), watch);
    }

    private void addPoint(float x, float y, long time) {
        final EventRingBuffer event = eventbuf;
        while (event.size() > 0) {
            long lastT = event.getTime(0);
            if (lastT >= time - LONGEST_PAST_TIME)
                break;
            event.dropOldest();
        }
        event.add(x, y, time);
    }

    public void computeCurrentVelocity(int units) {
        computeCurrentVelocity(units, Float.MAX_VALUE);
    }

    public void computeCurrentVelocity(int units, float maxVelocity) {
        final EventRingBuffer ringevent = eventbuf;
        final float compuA = ringevent.getX(0);
        final float compuB = ringevent.getY(0);
        final long compuC = ringevent.getTime(0);
        float valuzero = 0;
        float valueA = 0;
        final int number = ringevent.size();
        for (int pos = 1; pos < number; pos++) {
            final int diractoin = (int)(ringevent.getTime(pos) - compuC);
            if (diractoin == 0) continue;
            float destoy = ringevent.getX(pos) - compuA;
            float value = (destoy / diractoin) * units;
            if (valuzero == 0) valuzero = value;
            else valuzero = (valuzero + value) * .5f;

            destoy = ringevent.getY(pos) - compuB;
            value = (destoy / diractoin) * units;
            if (valueA == 0) valueA = value;
            else valueA = (valueA + value) * .5f;
        }
        tracker = valuzero < 0.0f ? Math.max(valuzero, -maxVelocity) : Math.min(valuzero, maxVelocity);
        flotekeyword = valueA < 0.0f ? Math.max(valueA, -maxVelocity) : Math.min(valueA, maxVelocity);
    }

    public float getXVelocity() {
        return tracker;
    }

    public float getYVelocity() {
        return flotekeyword;
    }

    static class EventRingBuffer {
        private final int splingvalue;
        private final float xBuf[];
        private final float yBuf[];
        private final long timeBuf[];
        private int top;
        private int botem;
        private int number;

        public EventRingBuffer(int max) {
            this.splingvalue = max;
            xBuf = new float[max];
            yBuf = new float[max];
            timeBuf = new long[max];
            clear();
        }

        public void clear() {
            top = botem = number = 0;
        }

        public int size() {
            return number;
        }

        private int index(int pos) {
            return (botem + pos) % splingvalue;
        }

        private int advance(int index) {
            return (index + 1) % splingvalue;
        }

        public void add(float x, float y, long time) {
            xBuf[top] = x;
            yBuf[top] = y;
            timeBuf[top] = time;
            top = advance(top);
            if (number < splingvalue) {
                number++;
            } else {
                botem = advance(botem);
            }
        }

        public float getX(int pos) {
            return xBuf[index(pos)];
        }

        public float getY(int pos) {
            return yBuf[index(pos)];
        }

        public long getTime(int pos) {
            return timeBuf[index(pos)];
        }

        public void dropOldest() {
            number--;
            botem = advance(botem);
        }
    }
}