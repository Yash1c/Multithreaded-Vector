# Multithreaded Vector Processor

A Java program that demonstrates parallel processing by:
1. Creating and populating a large vector with random values
2. Counting elements within a specific range using multithreading

## Features
- Processes a vector with **200 million entries**
- **Multithreaded implementation** for both:
  - Vector initialization
  - Range counting operation
- **Efficient memory management** for large datasets
- **Thread-safe operations** using proper synchronization

## Technical Specifications
- **Input**: Dynamically generated vector of 200,000,000 double values (0.0-1.0)
- **Processing**: Counts values where 0.25 < x < 0.75
- **Output**:
  - Completion message after initialization
  - Final count of values in specified range
- **Concurrency Model**: Uses Java's native threading (Thread/Runnable or ExecutorService)

## Implementation Details
- **Memory Allocation**: Single array allocation for memory efficiency
- **Thread Management**:
  - Divides work evenly among available processors
  - Uses thread-safe random number generation
  - Proper synchronization for result aggregation
- **Performance Considerations**: Optimized for multi-core processors

## How It Works
1. Allocates memory for the large vector
2. Parallel initialization with random values
3. Synchronization point (initialization complete)
4. Parallel counting of values in range (0.25, 0.75)
5. Result aggregation and display

## Potential Optimizations
- Dynamic thread pool sizing based on available cores
- Work stealing for load balancing
- Memory access pattern optimization