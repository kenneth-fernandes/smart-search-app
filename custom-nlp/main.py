# main.py

import argparse
from src.api import create_app
from src.intent_model import train_model, save_model

# Set up Flask app
app = create_app()


def main():
    # Parse command-line arguments
    parser = argparse.ArgumentParser(description="Run the Smart Search NLP application.")
    parser.add_argument('--train', action='store_true', help="Train the intent model")
    args = parser.parse_args()

    # If --train flag is provided, train the model
    if args.train:
        dataset_path = "data/intent_classification.csv"
        model = train_model(dataset_path)
        save_model(model, 'model/intent_model.pkl')

    # Start the Flask app
    app.run(debug=True)


if __name__ == '__main__':
    main()
