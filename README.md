## 🔒 Java Concurrency: Mutual Exclusion (Mutex) Demonstration

This repository contains an educational Java project that demonstrates fundamental concepts of **safe concurrent programming** and **Mutual Exclusion (Mutex)**.

The primary goal is to showcase how to prevent **data races**—a critical concurrency bug—when multiple threads attempt to modify a shared resource simultaneously.

-----

## ⚙️ Project Goal

The project runs multiple threads to increment a single shared integer variable (`sharedCounter`).

  * **Expected Result:** If the locking mechanism works, the final counter value will exactly match (Number of Threads $\times$ Iterations per Thread).
  * **The Problem:** Without proper locking, the atomic operation $\text{count}++$ breaks down into three non-atomic steps (read, increment, write), leading to lost updates and an incorrect final value (a data race).

-----

## 💻 Implementation Mechanisms

The project provides two robust and distinct ways to enforce mutual exclusion in Java:

### 1\. Intrinsic Locking (`SynchronizedCounter.java`)

This class utilizes Java's built-in monitor lock, achieved through the **`synchronized`** keyword.

| Feature | Description |
| :--- | :--- |
| **Mechanism** | Uses the object's intrinsic lock (or monitor). |
| **Usage** | Simplest and most idiomatic Java solution for mutual exclusion. |
| **Pattern** | Applies `synchronized` to the method, effectively locking the entire block. |

### 2\. Explicit Locking (`ReentrantLockCounter.java`)

This class uses an explicit lock object from the `java.util.concurrent` package, which offers greater flexibility.

| Feature | Description |
| :--- | :--- |
| **Mechanism** | Uses the **`ReentrantLock`** class. |
| **Usage** | Ideal for more complex scenarios (e.g., timed attempts, interruptible waiting). |
| **Pattern** | Employs a **`try...finally`** structure to mimic the **RAII** (Resource Acquisition Is Initialization) pattern found in C++, guaranteeing the lock is released (`unlock()`) even if exceptions occur. |

-----

## 🚀 Getting Started

### Prerequisites

  * Java Development Kit (JDK) 8 or newer.

### Running the Example

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/JavaConcurrencyProject.git
    cd JavaConcurrencyProject
    ```

2.  **Compile the Java files:**

    ```bash
    javac *.java
    ```

3.  **Run the `Synchronized` example:**

    ```bash
    java SynchronizedCounter
    ```

4.  **Run the `ReentrantLock` example:**

    ```bash
    java ReentrantLockCounter
    ```

### Example Output

```
Starting 10 threads to increment the counter...

Expected Final Value: 1000000
Actual Final Value:   1000000
✅ SUCCESS: The [Intrinsic Lock/ReentrantLock] prevented data races.
```

-----

## 🔑 Key Concurrency Concepts

This project demonstrates the successful application of the following concepts:

  * **Mutual Exclusion:** Guaranteeing that only one thread can access the critical section (`sharedCounter++`) at any given time.
  * **Thread Safety:** The code remains correct even when multiple threads execute it concurrently.
  * **Visibility:** Both locking mechanisms ensure that changes made by one thread to `sharedCounter` are immediately visible to all other threads once the lock is released.
