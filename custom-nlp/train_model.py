import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline
import pickle

# Load dataset
df = pd.read_csv('data/intent_classification.csv')  # Make sure the CSV path is correct

# Split dataset into train and test sets
X_train, X_test, y_train, y_test = train_test_split(df['text'], df['intent'], test_size=0.2, random_state=42)

# Create a pipeline with TF-IDF and Naive Bayes
model = make_pipeline(TfidfVectorizer(), MultinomialNB())

# Train the model
model.fit(X_train, y_train)

# Save the trained model
with open('model/intent_model.pkl', 'wb') as f:
    pickle.dump(model, f)

print("Model trained and saved to model/intent_model.pkl")
