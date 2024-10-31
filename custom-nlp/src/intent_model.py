# intent_model.py

import pickle
import os
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline


def train_model(data_path):
    # Load and preprocess the dataset
    df = pd.read_csv(data_path)
    x_train, x_test, y_train, y_test = train_test_split(df['text'], df['intent'], test_size=0.2, random_state=42)
    # Model pipeline with TF-IDF and Naive Bayes
    model = make_pipeline(TfidfVectorizer(), MultinomialNB())
    model.fit(x_train, y_train)
    accuracy = model.score(x_test, y_test)
    print("Model Accuracy:", accuracy)
    return model


def save_model(model, path):
    with open(path, 'wb') as f:
        pickle.dump(model, f)
    print(f"Model saved to {path}.")


def load_model(path=None):
    model_path = path or os.getenv("MODEL_PATH", "model/intent_model.pkl")
    try:
        with open(model_path, 'rb') as f:
            model = pickle.load(f)
        print(f"Model loaded from {model_path}.")
        return model
    except FileNotFoundError:
        print(f"Model file not found at {model_path}. Please train and save the model.")
        return None
    except Exception as e:
        print(f"An error occurred while loading the model: {e}")
        return None
