from flask import Flask, request, jsonify,render_template
import json

from flask_cors import CORS
import numpy as np
import pandas as pd
import seaborn as sns
from matplotlib import pyplot as plt
import networkx as nx
from gensim.models import Word2Vec
from sklearn.metrics.pairwise import cosine_similarity
import matplotlib.image as mpimg
import random

# Create a Flask app instance
app = Flask(__name__)
CORS(app)


# Define a route and a function to handle the route
@app.route('/')
def hello_world():
    return 'Hello, World!'

articles = pd.read_csv("C:/Users/ayanm/OneDrive/Desktop/New folder/csv file/articles/articles.csv")
transactions = pd.read_csv("C:/Users/ayanm/OneDrive/Desktop/New folder/csv file/transactions_train/transactions_train.csv")




item_freq = transactions.groupby('article_id')['customer_id'].nunique()
user_freq = transactions.groupby('customer_id')['article_id'].nunique()

items = item_freq[item_freq >= 100].index
users = user_freq[user_freq >= 100].index

filtered_df = transactions[transactions['article_id'].isin(items) & transactions['customer_id'].isin(users)]
freq = filtered_df.groupby(['customer_id', 'article_id']).size().reset_index(name='frequency')

GraphTravel_HM = filtered_df.merge(freq, on=['customer_id', 'article_id'], how='left')
GraphTravel_HM = GraphTravel_HM[GraphTravel_HM['frequency'] >= 10]



unique_customer_ids = GraphTravel_HM['customer_id'].unique()
customer_id_mapping = {id: i for i, id in enumerate(unique_customer_ids)}
GraphTravel_HM['customer_id'] = GraphTravel_HM['customer_id'].map(customer_id_mapping)

item_name_mapping = dict(zip(articles['article_id'], articles['prod_name'])) # prod_name


G = nx.Graph()

for index, row in GraphTravel_HM.iterrows():
    G.add_node(row['customer_id'], type='user')
    G.add_node(row['article_id'], type='item')
    G.add_edge(row['customer_id'], row['article_id'], weight=row['frequency'])

# biased random walk  
def biased_random_walk(G, start_node, walk_length, p=1, q=1):
    walk = [start_node]

    while len(walk) < walk_length:
        cur_node = walk[-1]
        cur_neighbors = list(G.neighbors(cur_node))

        if len(cur_neighbors) > 0:
            if len(walk) == 1:
                walk.append(random.choice(cur_neighbors))
            else:
                prev_node = walk[-2]

                probability = []
                for neighbor in cur_neighbors:
                    if neighbor == prev_node:
                        # Return parameter 
                        probability.append(1/p)
                    elif G.has_edge(neighbor, prev_node):
                        # Stay parameter 
                        probability.append(1)
                    else:
                        # In-out parameter 
                        probability.append(1/q)

                probability = np.array(probability)
                probability = probability / probability.sum()  # normalize
                next_node = np.random.choice(cur_neighbors, p=probability)
                walk.append(next_node)
        else:
            break
    return walk



def generate_walks(G, num_walks, walk_length, p=1, q=1):
    walks = []
    nodes = list(G.nodes())
    for _ in range(num_walks):
        random.shuffle(nodes)  # to ensure randomness
        for node in nodes:
            walk_from_node = biased_random_walk(G, node, walk_length, p, q)
            walks.append(walk_from_node)
    return walks


# Random Walk 
walks = generate_walks(G, num_walks=10, walk_length=20, p=9, q=1)
filtered_walks = [walk for walk in walks if len(walk) >= 5]
# to String  (for Word2Vec input)
walks = [[str(node) for node in walk] for walk in walks]
# Word2Vec train
model = Word2Vec(walks, vector_size=128, window=5, min_count=0,  hs=1, sg=1, workers=4, epochs=10)
# node embedding extract
embeddings = {node_id: model.wv[node_id] for node_id in model.wv.index_to_key}


def get_user_embedding(user_id, embeddings):
    return embeddings[str(user_id)]


def get_rated_items(user_id, df):
    return set(df[df['customer_id'] == user_id]['article_id'])


def calculate_similarities(user_id, df, embeddings):
    rated_items = get_rated_items(user_id, df)
    user_embedding = get_user_embedding(user_id, embeddings)

    item_similarities = []
    for item_id in set(df['article_id']):
        if item_id not in rated_items:  
            item_embedding = embeddings[str(item_id)]
            similarity = cosine_similarity([user_embedding], [item_embedding])[0][0]
            item_similarities.append((item_id, np.float32(similarity)))
    return item_similarities


def show_images(items, item_name_mapping, num_items, show_similarity=False):
    lst_items = []
    lst_item_id=[]

    for i, item in enumerate(items):
        item_id, similarity = item
        lst_item_id.append(item_id)
        lst_items.append(item)
    return lst_items


def recommend_items(user_id, df, embeddings, item_name_mapping, num_items):
    rated_items = get_rated_items(user_id, df)
    
    # print(f"User {user_id} has purchased:")
    show_images([(item_id, 0) for item_id in list(rated_items)[:5]], item_name_mapping, min(len(rated_items), 5))

    item_similarities = calculate_similarities(user_id, df, embeddings)
    recommended_items = sorted(item_similarities, key=lambda x: x[1], reverse=True)[:num_items]

    # print(f"\nRecommended items for user {user_id}:")
    return show_images(recommended_items, item_name_mapping, num_items, show_similarity=True)
   


@app.route('/Recommended_items', methods=['GET'])
def Recommended_items():
    user_id = int(request.args.get('user_id'))

    # user_id = json.loads(request.form['user_id'])['user_id']
    lst_items = recommend_items(user_id, GraphTravel_HM, embeddings, item_name_mapping, num_items=10)
    # Convert float32 values to regular float values
    converted_data = [(key, float(value)) for key, value in lst_items]

    lst_items = []
    for article_id,probability in converted_data:
        prod_name = articles[articles['article_id']==article_id]['prod_name'].values[0]
        prod_category = articles[articles['article_id']==article_id]['product_group_name'].values[0]
        lst_items.append({
                    'article_id':article_id,
                    'prod_name':prod_name,
                    'product_category':prod_category,
                    'probability':probability
        })

    dct = {'recommended_items':lst_items}
    return jsonify(dct)


#Categorical Recommendation
def categorical_show_images(items, item_name_mapping, num_items, show_similarity=False):
    lst_items = []
    for i, item in enumerate(items):
        item_id, similarity = item
        lst_items.append(item)
    return lst_items



def categorical_recommend_items(user_id, df, embeddings, item_name_mapping, num_items):
    rated_items = get_rated_items(user_id, df)
    
    # print(f"User {user_id} has purchased:")
    categorical_show_images([(item_id, 0) for item_id in list(rated_items)[:5]], item_name_mapping, min(len(rated_items), 5))

    item_similarities = calculate_similarities(user_id, df, embeddings)
    recommended_items = sorted(item_similarities, key=lambda x: x[1], reverse=True)[:num_items]

    # print(f"\nRecommended items for user {user_id}:")
    return categorical_show_images(recommended_items, item_name_mapping, num_items, show_similarity=True)


@app.route('/categorywise_recommendation', methods=['GET'])
def Categorical_recommended_items():
    user_id = int(request.args.get('user_id'))
    selected_product_group_names = json.loads(request.args.get('category'))
    print(selected_product_group_names)
    # user_id = json.loads(request.args.get('grid'))['user_id']
    # selected_product_group_names = json.loads(request.args.get('grid'))['category']

    lst = categorical_recommend_items(user_id, GraphTravel_HM, embeddings, item_name_mapping, num_items=150)
    lst_items_id = []
    for i in lst:
           lst_items_id.append(i[0])

    # Filter article IDs and their category names based on selected product_group_names
    filtered_data = articles[
    (articles['product_group_name'].isin(selected_product_group_names)) &
    (articles['article_id'].isin(lst_items_id))
    ][['article_id', 'product_group_name','product_code', 'prod_name', 'product_type_no',
        'product_type_name', 'graphical_appearance_no',
        'graphical_appearance_name', 'colour_group_code', 'colour_group_name',
        'perceived_colour_value_id', 'perceived_colour_value_name',
        'perceived_colour_master_id', 'perceived_colour_master_name',
        'department_no', 'department_name', 'index_code', 'index_name',
        'index_group_no', 'index_group_name', 'section_no', 'section_name',
        'garment_group_no', 'garment_group_name', 'detail_desc']]

    # Convert the filtered data to a dictionary where keys are product_group_names and values are lists of article_ids
    result_dict = {}
    for _, row in filtered_data.iterrows():
        article_id = row['article_id']
        category_name = row['product_group_name']
        if category_name not in result_dict:
                result_dict[category_name] = []
        result_dict[category_name].append([
                                        {'article_id':article_id},
                                        {'product_code':row['product_code']},
                                        {'prod_name':row['prod_name']},
                                        {'probability':[float(i[1]) for i in lst if i[0] == article_id]}
                                        ])
            

    # Flatten the data and sort by probability
    flattened_data = []
    for category, items in result_dict.items():
        for item in items:
            article_id = item[0]['article_id']
            product_code = item[1]['product_code']
            prod_name = item[2]['prod_name']
            probability = item[3]['probability'][0]
            flattened_data.append({
                                    "article_id":article_id,
                                    "product_code":product_code,
                                    "product_name":prod_name,
                                    "Product_category":category,
                                    "Probability":probability,
                                    })

    sorted_data = sorted(flattened_data, key=lambda x: x["Probability"], reverse=True)

    dct = {'Category':sorted_data}
    return jsonify(dct)




@app.route('/search_by_article_id', methods=['GET'])
def search():
    user_id = int(request.args.get('user_id'))
    article_id_need = int(request.args.get('article_id'))

    lst_items = categorical_recommend_items(user_id, GraphTravel_HM, embeddings, item_name_mapping, num_items=1012)

    prob = None
    for article_id, probability in lst_items:
        if article_id == article_id_need:
            prob = probability
            break
    article_info = articles[articles['article_id'] == article_id_need]

    dct = {
        'article_id': article_id_need,
        'probability': float(prob),
        'product_code': float(article_info['product_code'].values[0]),
        'product_category':article_info['product_group_name'].values[0],
        'prod_name': article_info['prod_name'].values[0]
    }
    return jsonify(dct)



@app.route('/article_id', methods=['GET'])
def get_articles_id():
    list_id = [int(x) for x in GraphTravel_HM.article_id.unique()]
    formatted_data=[]
    for i in list_id:
        formatted_data.append({
            "article_id":i,
            "prod_name":articles[articles['article_id']==i].prod_name.values[0],
            "product_category":articles[articles['article_id']==i].product_group_name.values[0]
        })
    dct = {'Article_id':formatted_data}
    return jsonify(dct)

@app.route('/unique_product_category', methods=['GET'])
def get_unique_category():
    result_categories  = {'Unique_Product_categories' : list(articles['product_group_name'].unique())}
    return jsonify(result_categories)

@app.route('/history_records', methods=['GET'])
def get_history_records():
    user_id = int(request.args.get('user_id'))
    purchased_id = list(get_rated_items(user_id, GraphTravel_HM))
    filtered_data  = articles[articles['article_id'].isin(purchased_id)]
    
    result_dict = {}
    for _, row in filtered_data.iterrows():
        article_id = row['article_id']
        
        if article_id not in result_dict:
                result_dict[article_id] = []
        result_dict[article_id].append([
                                        {'article_id':article_id},
                                        {'product_code':row['product_code']},
                                        {'prod_name':row['prod_name']},
                                        {'product_category':row['product_group_name']}
                                        ])
    lst_values=[]
    for i in result_dict:
        lst_values.append(result_dict[i][0])
    dct = {'History':lst_values}
    # Convert the data to the desired format
    formatted_data = []
    for item_list in dct["History"]:
        formatted_item = {
            "article_id": item_list[0]["article_id"],
            "product_code": item_list[1]["product_code"],
            "prod_name": item_list[2]["prod_name"],
            'product_category':item_list[3]["product_category"]
        }
        formatted_data.append(formatted_item)
    dct = {'History':formatted_data}
    return jsonify(dct)

# Run the app if this script is executed directly
if __name__ == '__main__':
    app.run()















