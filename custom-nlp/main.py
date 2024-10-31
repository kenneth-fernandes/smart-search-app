# main.py

import argparse
import os
from src.api import create_app
from src.intent_model import train_model, save_model, load_model

# Set up Flask app
app = create_app()


def main():
    # Parse command-line arguments
    parser = argparse.ArgumentParser(description="Run the Smart Search NLP application.")
    parser.add_argument('--train', action='store_true', help="Train the intent model")
    args = parser.parse_args()

    model_path = "model/intent_model.pkl"

    # Train the model if --train flag is provided or model file doesn't exist
    if args.train or not os.path.exists(model_path):
        print("Training the model...")
        dataset_path = "data/intent_classification.csv"
        model = train_model(dataset_path)
        save_model(model, model_path)
        print("Model trained and saved.")
    else:
        print("Model is already trained. Loading the existing model...")
        load_model()  # Loads the model without arguments

    # Start the Flask app
    app.run(debug=True, host='0.0.0.0', port=8080)  # Ensures Docker compatibility


if __name__ == '__main__':
    main()
