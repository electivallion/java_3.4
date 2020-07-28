public class Main {
    static Object monitor = new Object();
    static final int NUMBER = 5;
    static volatile char currentCharacter = 'A';

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < NUMBER; i++) {
                synchronized (monitor) {
                    while (currentCharacter != 'A') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('A');
                    currentCharacter = 'B';
                    monitor.notifyAll();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < NUMBER; i++) {
                synchronized (monitor) {
                    while (currentCharacter != 'B') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('B');
                    currentCharacter = 'C';
                    monitor.notifyAll();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < NUMBER; i++) {
                synchronized (monitor) {
                    while (currentCharacter != 'C') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('C');
                    currentCharacter = 'A';
                    monitor.notifyAll();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}