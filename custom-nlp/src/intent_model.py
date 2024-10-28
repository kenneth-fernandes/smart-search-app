import pickle
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline
import pandas as pd


def train_model(data_path):
    df = pd.read_csv(data_path)
    x_train, x_test, y_train, y_test = train_test_split(df['text'], df['intent'], test_size=0.2, random_state=42)
    model = make_pipeline(TfidfVectorizer(), MultinomialNB())
    model.fit(x_train, y_train)
    accuracy = model.score(x_test, y_test)
    print("Model Accuracy:", accuracy)
    return model


def save_model(model, path):
    with open(path, 'wb') as f:
        pickle.dump(model, f)


def load_model(path):
    try:
        with open(path, 'rb') as f:
            model = pickle.load(f)
        return model
    except FileNotFoundError:
        print(f"Model file not found at {path}. Please train and save the model.")
        return None
    except Exception as e:
        print(f"An error occurred while loading the model: {e}")
        return None
