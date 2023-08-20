## Personalized Product Recommendation System - Flipkart GRiD 5.0

Team Name:  **Byte Shift**

### Problem Definition
Implemented a personalized product ranking system by using an algorithm that can generate accurate and relevant product
rankings for individual users and taking into consideration factors such as user preferences, past interactions, product popularity, and user similarity. 

The working of the the algorithm is demonstrated in an Android application.

#### Deliverables: 
- A personalized recommendation system for effectively ranking products 
- An Android Interface
- Video Demonstration  


## Table Of Contents

  1. [Sub-problems](https://github.com/saudAnwar786/PRS/tree/master#sub-problems)
  2. [Training the data](https://github.com/saudAnwar786/PRS/tree/master#training)
  3. [Metrics](https://github.com/saudAnwar786/PRS/tree/master#metrics)
  4. [App demonstration](https://github.com/saudAnwar786/PRS/tree/master#demonstration)
  5. [Installation](https://github.com/saudAnwar786/PRS/tree/master#installation)
  6. [Authors](https://github.com/saudAnwar786/PRS/tree/master#authors)

<a name="sub-problems"/>

### Sub-problems
- **Data Preprocessing**: The code filters out articles and customers with low frequencies of interactions.


<p align="center" width="100%">
    <img width="70%" src="https://github.com/saniaahmad6/new/assets/94756953/de5adbf9-ffbc-44cc-aac0-5381a161dbde">
</p>


<p align="center" width="100%">
    <img width="70%" src="https://github.com/saniaahmad6/new/assets/94756953/caf5dbed-bd14-4f8a-8a26-d0369b8729fb">
</p>

- **Creating a Graph**: The interactions between customers and articles are merged into the `GraphTravel_HM DataFrame`

<p align="center" width="100%">
    <img width="70%" src="https://github.com/saniaahmad6/new/assets/94756953/cdb650e1-c3d3-4511-9eec-315ce3604da6">
</p>




- Mapping IDs: Customer and article IDs are mapped to numerical indices using dictionaries `(customer_id_mapping and item_name_mapping)`.

- *Biased Random Walk* : It determines the next node in the walk based on three parameters: p (return parameter), q (in-out parameter), and the edge relationships in the graph. This random walk is biased to explore the graph in a way that's sensitive to the local structure.

- Generating Walks: The generate_walks function generates random walks on the graph for a specified number of times. Each random walk has a certain length and follows the biased random walk strategy.

- **Word2Vec Training**: The code trains a Word2Vec model on the generated walks to learn node embeddings. These embeddings represent nodes (customers and articles) in a dense vector space.

![Graph](https://github.com/saniaahmad6/new/assets/94756953/24eb970c-0b22-4278-a602-855f1e1febbe)


<a name="training"/>

### Training Data (Sentences or Sequences)
- The training data corresponds to the sequences of nodes generated from random walks on the graph of customer-item interactions. Each sequence of nodes represents a walk that a "walker" takes in the graph, simulating how a user interacts with different items.
- The generate_walks function in the code generates these sequences of nodes (walks). These walks are treated as "sentences" for the Word2Vec model.
- Each walk typically starts from a user node and traverses through item nodes based on the biased random walk strategy.

The sequences of nodes from the walks are used to train the Word2Vec model, which learns embeddings that capture the relationships between users and items.

<a name="metrics"/>

### Metrics
- For a recommendation system, we can associate a diversity score to the solution. Diversity in this context refers to how different or dissimilar the recommended items are from each other. A higher diversity score indicates a greater variety in the recommendations, which can be beneficial to capture different aspects of a user's preferences.
- Our solution has a diversity score of 0.51 (51%)

  <br>

![WhatsApp Image 2023-08-20 at 16 58 22](https://github.com/saudAnwar786/PRS/assets/94756953/8271a433-8d5f-4bf4-8f33-7b11874c472c)

<a name="demonstration"/>

### App Demonstration
- Demo Video: 
- The **UI layer** that displays application data on the screen and the **data layer** that contains the business logic of your app and exposes application data. An additional layer called the **domain layer** simplifies and reuses the interactions between the UI and data layers.
<p align="center" width="100%">
    <img width="70%" src="https://github.com/saniaahmad6/new/assets/94756953/e7f3b0fa-0929-4e40-9f86-48084aa23f4f">
</p>
- [Retrofit](https://square.github.io/retrofit/) : a type-safe REST client for Android
<p align="center" width="100%">
    <img width="70%" src="https://github.com/saniaahmad6/new/assets/94756953/baf81d95-2341-49db-82f8-514b7d62ee76">
</p>

<a name="installation"/>

### Installation and running in local

**Git** and **npm** should be globally installed in your system.

- Fork this repository

- Clone the repository
```bash
  git clone <your-forked-repository>
```
- Create an environment with all the packages and libraries specified by executing the following command in the terminal and start the server locally.

    ```bash
    cd backend
    pip install -r requirements.txt
    python system.py
    ```

- To run the app on the emulator: In your API/URL directly use`http://127.0.0.1:[your port]/` and under the emulator setting add the proxy address as `10.0.2.2` with the port number.


*Note*: These instructions are intended to set up and run the the project locally.

<a name="authors"/>

### Authors: 
- [Mohammed Ayan](https://github.com/AyanChaudhary)
- [Sania Ahmad](https://github.com/saniaahmad6)
- [Saud Anwar](https://github.com/saudAnwar786)
