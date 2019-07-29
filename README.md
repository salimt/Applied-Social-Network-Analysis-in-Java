# Social Network Analysis 
_My capstone project for the [Object Oriented Java Programming: Data Structures and Beyond Specialization](https://www.coursera.org/specializations/java-object-oriented), offered by UC - San Diego on Coursera._

This project investigates the communities within a social network. The first part of the project will look at relationships between a user’s friends (specifically, are they all connected as friends too). In the second part, the goal will be to find the minimum dominating set, so we can reach all the people in the network just sharing with a number of friends.

### Data
facebook_ucsd.txt
This graph contains Facebook friendships between students at UCSD in 2005. 
This data was originally stored in a Matlab sparse matrix; however, this was 
processed using Python to create a more suitable format for reading with Java.
The edges in this file are directed; however, each edge also appears in reverse
order, making the final result undirected.
Source: https://archive.org/details/oxford-2005-facebook-matrix
Citation: Facebook data scrape related to paper "The Social Structure of Facebook 
Networks", by Amanda L. Traud, Peter J. Mucha, Mason A. Porter.

- Number of nodes (users) = 14947

### Questions
**Easier:** For a given user, which of their friends aren’t connected as friends? For example, if the given user, Maria, is friends
with both Jamaal and Huang, if Jamaal and Huang are not friends, we’ll suggest them as potential friends.

**Harder:** Find the Exact Cover Set. Figure out the smallest set of people who are connected to everyone in the network. For example, if everyone in the smallest set were to post something to their friends, everyone would see the post.

### Algorithms, Data Structures, and Answer to Question
**Main Data Structure:** The network has been laid out as a classic graph using an adjacency list. Each individual in the graph
is a vertex and an edge between vertices represents a friendship. This should work for both problems. Each node is stored as the key in a HashMap, with values representing the node’s outgoing edges, stored as a HashSet for quick O(1) lookup.

The exact cover problem is represented in Algorithm X using a matrix A consisting of 0s and 1s. The goal is to select a subset of the rows so that the digit 1 appears in each column exactly once.

[Algorithm X](https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X) functions as follows:

    If the matrix A has no columns, the current partial solution

    is a valid solution; terminate successfully. 
   
    Otherwise, choose a column c (deterministically). 

    Choose a row r such that A[r] = 1 (nondeterministically). 

    Include row r in the partial solution. 

    For each column j such that A[r][j] = 1,

        for each row i such that A[i][j] = 1, 
        
            delete row i from matrix A. 
            
        delete column j from matrix A. 
      
    Repeat this algorithm recursively on the reduced matrix A. 



### Test Results

    //facebook_1000 -> 214
    //facebook_2000 -> 223
    //facebook_ucsd -> 357 (18K Nodes)
