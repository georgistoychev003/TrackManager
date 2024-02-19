# Complex Data Structures Project Overview

This README provides an overview of the Complex Data Structures (CDS) project, implemented as part of my coursework. The project showcases the implementation of various data structures and algorithms to manage and manipulate complex data efficiently. Below is a concise overview of the project's components, highlighting the key data structures and algorithms used.

## Project Components

### Station and Track Classes
- **Validations**: Implemented using regular expressions to ensure data integrity before addition to the collection.
- **CSV Reader**: Facilitates reading station/track data from CSV files with regex-based validation to maintain data quality.

### Data Structures

#### DoublyLinkedList
- A custom implementation that stores elements in nodes with references to both next and previous nodes.
- Features include search, conversion to ArrayList, access methods (`get`, `getNodeAtIndex`), and an iterator for sequential traversal.

#### HashMap
- A custom implementation to store and retrieve data efficiently.

#### BinarySearchTree & AVLTree
- Implemented for efficient data sorting and retrieval.

#### MinHeap
- Used for efficient minimum value access.

#### WeightedMatrixGraph
- Supports graph-based data manipulation and traversal.

### Algorithms

#### Linear and Binary Search
- Implemented for station lookup by name. Binary search offers `O(log n)` complexity, while linear search has `O(n)` complexity.

#### InsertionSort & MergeSort
- Used for sorting stations. InsertionSort is designed for `O(n^2)` complexity, ideal for small datasets, while MergeSort offers `O(n log n)` complexity, suitable for larger data.

#### Breadth-First Search (BFS) & Depth-First Search (DFS)
- Graph traversal algorithms for exploring nodes in a graph.

#### A* & Dijkstra Algorithm
- Pathfinding algorithms implemented for efficient route finding.

#### MCSTPrim
- Algorithm for finding the Minimum Spanning Tree of a graph.

### Utilities

#### StationsWithinRectangle
- A feature to find stations within a specified rectangular area.

#### Menu
- An interactive console menu to navigate through the functionalities offered by the application.

## Testing
- The Station and Track classes are tested with 96% and 94% line coverage, respectively.
- StationSearch and other algorithms are thoroughly tested for reliability.

## Usage
- The project is equipped with a menu-driven interface for easy navigation and testing of implemented features.

---

This project encapsulates my journey through understanding and implementing complex data structures and algorithms, offering a robust foundation for efficient data handling and manipulation.
